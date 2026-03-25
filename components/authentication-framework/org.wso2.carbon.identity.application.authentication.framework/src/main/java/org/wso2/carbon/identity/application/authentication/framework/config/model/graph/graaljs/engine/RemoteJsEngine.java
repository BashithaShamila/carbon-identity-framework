/*
 * Copyright (c) 2026, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.AuthGraphNode;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.JsGraphBuilder;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextData;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.EvaluateRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.EvaluateResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ExecuteCallbackRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ExecuteCallbackResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.HostFunctionDefinition;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.SerializedValue;
import org.wso2.carbon.identity.application.authentication.framework.config.model.StepConfig;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.JsWrapperFactoryProvider;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedIdPData;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Remote JavaScript engine that communicates with a GraalJS sidecar via
 * pluggable transport.
 * Each instance represents a session with the sidecar.
 * <p>
 * Host function calls from the sidecar are routed back to IS via the callback
 * server.
 * <p>
 * This implementation is decoupled from specific transport mechanisms
 * through the RemoteEngineTransport and CallbackServer abstractions.
 */
public class RemoteJsEngine implements JsEngine, CallbackServer.HostFunctionHandler {

    private static final Log log = LogFactory.getLog(RemoteJsEngine.class);

    private final RemoteEngineTransport transport;
    private final String sessionId;
    private final AuthenticationContext authContext;
    // CRITICAL FIX: Use ConcurrentHashMap to prevent race conditions during
    // concurrent access
    // from multiple threads (e.g., main execution thread + callback handler
    // threads)
    private final Map<String, Object> bindings = new ConcurrentHashMap<>();
    private final Map<String, Object> hostFunctions = new ConcurrentHashMap<>();
    private final Map<String, Object> hostFunctionRefs = new ConcurrentHashMap<>();
    private JsGraphBuilder graphBuilder;
    // Session-scoped cache for proxied objects (e.g., User objects from getUsersWithClaimValues)
    // Key: reference_id (UUID), Value: actual object
    private final Map<String, Object> proxyObjectCache = new ConcurrentHashMap<>();
    // Accumulated dynamicallyBuiltBaseNode across gRPC callbacks.
    // In local mode, the dynamicallyBuiltBaseNode ThreadLocal accumulates nodes
    // across
    // host function calls within a single callback execution (same thread). In
    // remote mode,
    // each gRPC callback runs on a separate thread, so we persist the value here
    // between
    // setupThreadContext/clearThreadContext pairs to replicate local mode behavior.
    private volatile AuthGraphNode accumulatedDynamicBaseNode;
    private boolean closed = false;

    /**
     * Create a new remote JavaScript engine.
     *
     * @param transport      The transport layer for communicating with the remote
     *                       engine.
     * @param callbackServer The callback server (unused in streaming mode, kept for API compat).
     * @param authContext    The authentication context for this session.
     */
    public RemoteJsEngine(RemoteEngineTransport transport, CallbackServer callbackServer,
            AuthenticationContext authContext) {
        this.transport = transport;
        this.authContext = authContext;
        this.sessionId = UUID.randomUUID().toString();
        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] Created with session: " + sessionId +
                    ", transport: " + transport.getClass().getSimpleName() +
                    ", SP: " + (authContext != null ? authContext.getServiceProviderName() : "null"));
        }
    }

    /**
     * Set the graph builder reference for this engine.
     * This is needed so that gRPC callback threads can set the currentBuilder
     * ThreadLocal,
     * which is required by static methods like JsGraphBuilder.addLongWaitProcess().
     *
     * @param graphBuilder The JsGraphBuilder instance to use for callback thread
     *                     context.
     */
    public void setGraphBuilder(JsGraphBuilder graphBuilder) {
        this.graphBuilder = graphBuilder;
        log.debug("[RemoteJsEngine] Graph builder set: " +
                (graphBuilder != null ? graphBuilder.getClass().getSimpleName() : "null"));
    }

    /**
     * Get the accumulated dynamicallyBuiltBaseNode value.
     * This is the value accumulated across gRPC callbacks, replicating
     * the local mode ThreadLocal behavior across threads.
     *
     * @return The accumulated AuthGraphNode, or null if none was built.
     */
    public AuthGraphNode getAccumulatedDynamicBaseNode() {
        return accumulatedDynamicBaseNode;
    }

    /**
     * Reset the accumulated dynamicallyBuiltBaseNode.
     * Should be called before a new callback evaluation cycle begins.
     */
    public void resetAccumulatedDynamicBaseNode() {
        this.accumulatedDynamicBaseNode = null;
    }

    @Override
    public EvaluationResult evaluate(String script, String sourceIdentifier, Map<String, Object> initialBindings) {
        long startTime = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] evaluate() called, session: " + sessionId + ", sourceId: " + sourceIdentifier);
        }

        try {
            // Phase 1: Connect and setup
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Ensuring connection to remote engine");
            }
            ensureConnected();
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Connection established");
            }
            long tConnectDone = System.currentTimeMillis();

            // Phase 2: Build request (protobuf serialization)
            // Apply initial bindings
            if (initialBindings != null) {
                bindings.putAll(initialBindings);
            }

            // Build the request
            EvaluateRequest.Builder requestBuilder = EvaluateRequest.newBuilder()
                    .setSessionId(sessionId)
                    .setScript(script)
                    .setSourceIdentifier(sourceIdentifier != null ? sourceIdentifier : "script");

            // Serialize bindings
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Serializing " + bindings.size() + " bindings, " +
                        hostFunctions.size() + " host functions");
            }
            for (Map.Entry<String, Object> entry : bindings.entrySet()) {
                // Skip "context" -- JsGraalAuthenticationContext is not ProtobufSerializer-compatible.
                // Context state is sent as structured ContextData and the sidecar accesses it
                // via DynamicContextProxy callbacks. Serializing it here causes a toString()
                // fallback with WARN log. If this binding is ever needed, implement a proper
                // toProto() conversion for JsGraalAuthenticationContext first.
                if (!RemoteEngineConstants.CONTEXT_BINDING_KEY.equals(entry.getKey()) && !hostFunctions.containsKey(entry.getKey())) {
                    requestBuilder.putBindings(entry.getKey(), ProtobufSerializer.toProto(entry.getValue()));
                }
            }

            // Add host function definitions so sidecar knows to call back
            for (String funcName : hostFunctions.keySet()) {
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Registering host function: " + funcName);
                }
                requestBuilder.addHostFunctions(
                        HostFunctionDefinition.newBuilder()
                                .setName(funcName)
                                .build());
            }

            // Add context data for context proxy reconstruction in sidecar
            if (authContext != null) {
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Adding context data, step: " + authContext.getCurrentStep() +
                            ", subject: "
                            + (authContext.getSubject() != null ? authContext.getSubject().getUserName() : "null"));
                }
                ContextData.Builder contextDataBuilder = ContextData.newBuilder()
                        .setSessionContextKey(
                                authContext.getContextIdentifier() != null ? authContext.getContextIdentifier() : "")
                        .setCurrentStep(authContext.getCurrentStep());

                AuthenticatedUser subject = authContext.getSubject();
                if (subject != null) {
                    contextDataBuilder.setUsername(subject.getUserName() != null ? subject.getUserName() : "");
                    contextDataBuilder.setUserStoreDomain(
                            subject.getUserStoreDomain() != null ? subject.getUserStoreDomain() : "");
                    contextDataBuilder
                            .setTenantDomain(subject.getTenantDomain() != null ? subject.getTenantDomain() : "");
                }
                requestBuilder.setContextData(contextDataBuilder.build());
            } else {
                log.warn("[RemoteJsEngine] No authContext available, context proxy will be empty");
            }
            long tRequestBuilt = System.currentTimeMillis();

            // Phase 3: Transport round-trip
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Sending evaluate request to remote engine...");
            }
            EvaluateResponse response = transport.sendEvaluate(requestBuilder.build(), this);
            long tResponseReceived = System.currentTimeMillis();
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Received response, success: " + response.getSuccess());
            }

            // Phase 4: Response processing
            EvaluationResult evalResult;
            if (response.getSuccess()) {
                // Update bindings from response
                Map<String, Object> updatedBindings = new HashMap<>();
                for (Map.Entry<String, SerializedValue> entry : response.getUpdatedBindingsMap().entrySet()) {
                    Object deserialized = ProtobufSerializer.fromProto(entry.getValue());
                    updatedBindings.put(entry.getKey(), deserialized);
                    bindings.put(entry.getKey(), deserialized);
                }

                Object result = ProtobufSerializer.fromProto(response.getResult());
                long tResponseProcessed = System.currentTimeMillis();

                long isElapsed = tResponseProcessed - startTime;
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Phase timing: connectSetup=" + (tConnectDone - startTime) +
                            "ms, requestBuild=" + (tRequestBuilt - tConnectDone) +
                            "ms, transportRoundTrip=" + (tResponseReceived - tRequestBuilt) +
                            "ms, responseProcess=" + (tResponseProcessed - tResponseReceived) +
                            "ms, total=" + isElapsed + "ms" +
                            ", sidecarReported=" + response.getElapsedMs() + "ms");
                }

                evalResult = EvaluationResult.builder()
                        .success(true)
                        .result(result)
                        .updatedBindings(updatedBindings)
                        .elapsedMs(isElapsed)
                        .build();
            } else {
                long tResponseProcessed = System.currentTimeMillis();
                long isElapsed = tResponseProcessed - startTime;
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Phase timing (error): connectSetup=" + (tConnectDone - startTime) +
                            "ms, requestBuild=" + (tRequestBuilt - tConnectDone) +
                            "ms, transportRoundTrip=" + (tResponseReceived - tRequestBuilt) +
                            "ms, responseProcess=" + (tResponseProcessed - tResponseReceived) +
                            "ms, total=" + isElapsed + "ms");
                }
                evalResult = EvaluationResult.failure(
                        response.getErrorMessage(),
                        response.getErrorType(),
                        isElapsed);
            }
            return evalResult;

        } catch (IOException e) {
            long isElapsed = System.currentTimeMillis() - startTime;
            log.error("IOException during remote evaluation", e);
            return EvaluationResult.failure("Remote engine communication failed: " + e.getMessage(),
                    "IOException", isElapsed);
        }
    }

    @Override
    public EvaluationResult executeCallback(String functionSource, Object[] arguments,
            Map<String, Object> callbackBindings, AuthenticationContext context) {
        long startTime = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] executeCallback() called, session: " + sessionId +
                    ", function length: " + (functionSource != null ? functionSource.length() : 0) +
                    ", args: " + (arguments != null ? arguments.length : 0));
        }

        try {
            // Phase 1: Connect and setup
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] executeCallback - ensuring connection to remote engine");
            }
            ensureConnected();
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] executeCallback - connection established");
            }
            long tConnectDone = System.currentTimeMillis();

            // Phase 2: Build request (protobuf serialization)
            // Apply callback bindings
            if (callbackBindings != null && !callbackBindings.isEmpty()) {
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Applying " + callbackBindings.size() + " callback bindings: " +
                            callbackBindings.keySet());
                }
                for (Map.Entry<String, Object> entry : callbackBindings.entrySet()) {
                    Object value = entry.getValue();
                    if (log.isDebugEnabled()) {
                        log.debug("[RemoteJsEngine] Callback binding: " + entry.getKey() + " = " +
                                (value != null ? value.getClass().getSimpleName() + ": " + value : "null"));
                    }
                    bindings.put(entry.getKey(), value);
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] No callback bindings provided (null or empty). " +
                            "callbackBindings=" + callbackBindings);
                }
            }

            // Build the request
            ExecuteCallbackRequest.Builder requestBuilder = ExecuteCallbackRequest.newBuilder()
                    .setSessionId(sessionId)
                    .setFunctionSource(functionSource);

            // Add context data for proxy object reconstruction
            if (context != null) {
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Adding context data, step: " + context.getCurrentStep() +
                            ", subject: " + (context.getSubject() != null ? context.getSubject().getUserName() : "null"));
                }
                ContextData.Builder contextDataBuilder = ContextData.newBuilder()
                        .setSessionContextKey(
                                context.getContextIdentifier() != null ? context.getContextIdentifier() : "")
                        .setCurrentStep(context.getCurrentStep());

                AuthenticatedUser subject = context.getSubject();
                if (subject != null) {
                    contextDataBuilder.setUsername(subject.getUserName() != null ? subject.getUserName() : "");
                    contextDataBuilder.setUserStoreDomain(
                            subject.getUserStoreDomain() != null ? subject.getUserStoreDomain() : "");
                    contextDataBuilder
                            .setTenantDomain(subject.getTenantDomain() != null ? subject.getTenantDomain() : "");
                }
                requestBuilder.setContextData(contextDataBuilder.build());
            }

            // Serialize arguments
            if (arguments != null) {
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Serializing " + arguments.length + " arguments");
                }
                for (int i = 0; i < arguments.length; i++) {
                    if (log.isDebugEnabled()) {
                        log.debug("[RemoteJsEngine] Arg[" + i + "] type: " +
                                (arguments[i] != null ? arguments[i].getClass().getName() : "null"));
                    }
                    // Replace JsGraalAuthenticationContext with a marker string instead of
                    // serializing the full object (which is not ProtobufSerializer-compatible
                    // and causes a toString() fallback with WARN log). The sidecar detects
                    // this marker via sv.getStringValue().contains("JsGraalAuthenticationContext")
                    // and substitutes its local DynamicContextProxy. We must preserve the
                    // argument position — skipping it would shift subsequent args (e.g.,
                    // httpGet's onSuccess(context, data) would receive (data, undefined)).
                    if (arguments[i] instanceof JsGraalAuthenticationContext) {
                        requestBuilder.addArguments(
                                ProtobufSerializer.toProto(RemoteEngineConstants.CONTEXT_PLACEHOLDER));
                        continue;
                    }
                    requestBuilder.addArguments(ProtobufSerializer.toProto(arguments[i]));
                }
            }

            // Serialize bindings (excluding host functions)
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Total bindings to serialize: " + bindings.size() +
                        ", keys: " + bindings.keySet());
            }
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Host functions (excluded from bindings): " + hostFunctions.keySet());
            }
            int bindingsAdded = 0;
            for (Map.Entry<String, Object> entry : bindings.entrySet()) {
                // Skip "context" -- JsGraalAuthenticationContext is not ProtobufSerializer-compatible.
                // Context state is sent as structured ContextData and the sidecar accesses it
                // via DynamicContextProxy callbacks. Serializing it here causes a toString()
                // fallback with WARN log. If this binding is ever needed, implement a proper
                // toProto() conversion for JsGraalAuthenticationContext first.
                if (!RemoteEngineConstants.CONTEXT_BINDING_KEY.equals(entry.getKey()) && !hostFunctions.containsKey(entry.getKey())) {
                    Object value = entry.getValue();
                    if (log.isDebugEnabled()) {
                        log.debug("[RemoteJsEngine] Serializing binding: " + entry.getKey() + " = " +
                                (value != null ? value.getClass().getSimpleName() + ": " + value : "null"));
                    }
                    requestBuilder.putBindings(entry.getKey(), ProtobufSerializer.toProto(value));
                    bindingsAdded++;
                }
            }
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Bindings serialized: " + bindingsAdded);
            }

            // Add host function definitions so sidecar knows to create stubs for callbacks
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Adding " + hostFunctions.size() + " host function definitions");
            }
            for (String funcName : hostFunctions.keySet()) {
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Adding host function: " + funcName);
                }
                requestBuilder.addHostFunctions(
                        HostFunctionDefinition.newBuilder()
                                .setName(funcName)
                                .build());
            }
            long tRequestBuilt = System.currentTimeMillis();

            // Phase 3: Transport round-trip
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Sending executeCallback request to remote engine...");
            }
            ExecuteCallbackResponse response = transport.sendExecuteCallback(requestBuilder.build(), this);
            long tResponseReceived = System.currentTimeMillis();

            // Phase 4: Response processing
            EvaluationResult evalResult;
            if (response.getSuccess()) {
                // Update bindings from response
                Map<String, Object> updatedBindings = new HashMap<>();
                for (Map.Entry<String, SerializedValue> entry : response.getUpdatedBindingsMap().entrySet()) {
                    Object deserialized = ProtobufSerializer.fromProto(entry.getValue());
                    updatedBindings.put(entry.getKey(), deserialized);
                    bindings.put(entry.getKey(), deserialized);
                }

                Object result = ProtobufSerializer.fromProto(response.getResult());
                long tResponseProcessed = System.currentTimeMillis();

                long isElapsed = tResponseProcessed - startTime;
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Phase timing: connectSetup=" + (tConnectDone - startTime) +
                            "ms, requestBuild=" + (tRequestBuilt - tConnectDone) +
                            "ms, transportRoundTrip=" + (tResponseReceived - tRequestBuilt) +
                            "ms, responseProcess=" + (tResponseProcessed - tResponseReceived) +
                            "ms, total=" + isElapsed + "ms" +
                            ", sidecarReported=" + response.getElapsedMs() + "ms");
                }

                evalResult = EvaluationResult.builder()
                        .success(true)
                        .result(result)
                        .updatedBindings(updatedBindings)
                        .elapsedMs(isElapsed)
                        .build();
            } else {
                long tResponseProcessed = System.currentTimeMillis();
                long isElapsed = tResponseProcessed - startTime;
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Phase timing (error): connectSetup=" + (tConnectDone - startTime) +
                            "ms, requestBuild=" + (tRequestBuilt - tConnectDone) +
                            "ms, transportRoundTrip=" + (tResponseReceived - tRequestBuilt) +
                            "ms, responseProcess=" + (tResponseProcessed - tResponseReceived) +
                            "ms, total=" + isElapsed + "ms");
                }
                evalResult = EvaluationResult.failure(
                        response.getErrorMessage(),
                        "ExecutionError",
                        isElapsed);
            }
            return evalResult;

        } catch (IOException e) {
            long isElapsed = System.currentTimeMillis() - startTime;
            log.error("IOException during remote callback execution", e);
            return EvaluationResult.failure("Remote callback execution failed: " + e.getMessage(),
                    "IOException", isElapsed);
        }
    }

    @Override
    public Map<String, Object> getBindings() {
        return new HashMap<>(bindings);
    }

    @Override
    public void putBinding(String name, Object value) {
        bindings.put(name, value);
    }

    @Override
    public void registerHostFunctions(Map<String, Object> functions) {
        if (functions != null) {
            hostFunctions.putAll(functions);
        }
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void close() {
        if (!closed) {
            closed = true;

            try {
                transport.close();
            } catch (IOException e) {
                log.debug("Error closing transport", e);
            }
            log.debug("RemoteJsEngine closed for session: " + sessionId);
        }
    }

    /**
     * Handle host function callback from sidecar.
     * This is called when the sidecar JavaScript invokes a host function.
     */
    @Override
    public Object invokeHostFunction(String functionName, Object... args) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] invokeHostFunction called: " + functionName + " with " +
                    (args != null ? args.length : 0) + " args, session: " + sessionId);
        }

        // Log raw argument details for debugging.
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Raw arg[" + i + "]: type=" +
                            (args[i] != null ? args[i].getClass().getName() : "null") +
                            ", value=" + (args[i] != null ? truncateForLog(args[i].toString()) : "null"));
                }
            }
        }

        Object hostFunc = hostFunctions.get(functionName);
        if (hostFunc == null) {
            log.error("[RemoteJsEngine] Unknown host function: " + functionName +
                    ", available: " + hostFunctions.keySet());
            throw new IllegalArgumentException("Unknown host function: " + functionName);
        }
        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] Found host function impl: " + hostFunc.getClass().getName());
        }

        // Set up thread-local context for the host function invocation.
        // This ensures proper tenant context is available.
        setupThreadContext();

        try {
            // Find the @HostAccess.Export method to invoke.
            for (java.lang.reflect.Method method : hostFunc.getClass().getMethods()) {
                if (method.isAnnotationPresent(org.graalvm.polyglot.HostAccess.Export.class)) {
                    if (log.isDebugEnabled()) {
                        log.debug("[RemoteJsEngine] Found @HostAccess.Export method: " + method.getName() +
                                ", params: " + method.getParameterCount() +
                                ", paramTypes: " + java.util.Arrays.toString(method.getParameterTypes()));
                    }

                    // Adapt arguments to match method parameter types.
                    Object[] adaptedArgs = adaptArgumentsForMethod(method, args);

                    // Log adapted arguments.
                    for (int i = 0; i < adaptedArgs.length; i++) {
                        if (log.isDebugEnabled()) {
                            log.debug("[RemoteJsEngine] Adapted arg[" + i + "]: type=" +
                                    (adaptedArgs[i] != null ? adaptedArgs[i].getClass().getName() : "null"));
                        }
                    }

                    if (log.isDebugEnabled()) {
                        log.debug("[RemoteJsEngine] Invoking method with " + adaptedArgs.length + " adapted args");
                    }
                    try {
                        Object result = method.invoke(hostFunc, adaptedArgs);
                        if (log.isDebugEnabled()) {
                            log.debug("[RemoteJsEngine] Method returned: " +
                                    (result != null ? result.getClass().getName() + "=" + result : "null"));
                        }
                        return result;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // Log the actual cause of the error.
                        Throwable cause = e.getCause();
                        log.error("[RemoteJsEngine] Host function '" + functionName + "' threw exception: " +
                                (cause != null ? cause.getClass().getName() + ": " + cause.getMessage()
                                        : e.getMessage()));
                        if (cause != null) {
                            log.error("[RemoteJsEngine] Root cause stack trace:", cause);
                        }
                        throw e;
                    }
                }
            }

            // Fallback: try to find a method matching common patterns.
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] No @HostAccess.Export found, trying interface methods...");
            }
            Class<?>[] hostInterfaces = hostFunc.getClass().getInterfaces();
            for (Class<?> iface : hostInterfaces) {
                for (java.lang.reflect.Method method : iface.getMethods()) {
                    if (!method.isDefault() && method.getParameterCount() <= args.length) {
                        try {
                            Object[] adaptedArgs = adaptArgumentsForMethod(method, args);
                            if (log.isDebugEnabled()) {
                                log.debug("[RemoteJsEngine] Trying method: " + iface.getName() + "." + method.getName());
                            }
                            return method.invoke(hostFunc, adaptedArgs);
                        } catch (IllegalArgumentException e) {
                            log.debug("[RemoteJsEngine] Method mismatch: " + method.getName());
                            // Try next method.
                        }
                    }
                }
            }

            throw new NoSuchMethodException("Could not find invokable method for: " + functionName);
        } finally {
            // Clean up thread context if needed.
            clearThreadContext();
        }
    }

    @Override
    public String storeObjectReference(Object obj) {
        String refId = UUID.randomUUID().toString();
        hostFunctionRefs.put(refId, obj);
        log.debug("[RemoteJsEngine] Stored object reference: " + refId +
                " -> " + (obj != null ? obj.getClass().getSimpleName() : "null"));
        return refId;
    }

    /**
     * Get a context property value for the dynamic context proxy.
     * This navigates the property path on the real JsGraalAuthenticationContext.
     * Also supports host function return references via "__hostref__" prefix.
     */
    @Override
    public Object getContextProperty(String propertyPath) throws Exception {
        log.debug("[RemoteJsEngine] getContextProperty called: " + propertyPath + ", session: " + sessionId);

        // Handle proxy object property access: "__proxyref__::<referenceId>::<property>"
        // This enables lazy loading of complex objects (e.g., User objects from getUsersWithClaimValues)
        if (propertyPath.startsWith(RemoteEngineConstants.PROXY_REF_PREFIX)) {
            return getProxyObjectProperty(propertyPath.substring(RemoteEngineConstants.PROXY_REF_PREFIX.length()));
        }

        // Handle host function return references: "__hostref__::<refId>::<property>"
        if (propertyPath.startsWith(RemoteEngineConstants.HOST_REF_PREFIX)) {
            return getHostRefProperty(propertyPath.substring(RemoteEngineConstants.HOST_REF_PREFIX.length()));
        }

        if (authContext == null) {
            log.warn("[RemoteJsEngine] No authContext available for property access");
            return null;
        }

        // Create the JsGraalAuthenticationContext wrapper and navigate the property path
        JsGraalAuthenticationContext jsContext = new JsGraalAuthenticationContext(authContext);
        String[] parts = propertyPath.split(RemoteEngineConstants.PATH_SEPARATOR);
        Object result = PropertyPathNavigator.navigatePath(parts, 0, jsContext);

        log.debug("[RemoteJsEngine] getContextProperty '" + propertyPath + "' = " +
                (result != null ? result.getClass().getSimpleName() : "null"));
        return result;
    }

    /**
     * Navigate a property path on a stored host function return reference.
     * Path format: "<refId>" or "<refId>::<property>::<subprop>..."
     */
    private Object getHostRefProperty(String path) {
        String[] parts = path.split(RemoteEngineConstants.PATH_SEPARATOR);
        String refId = parts[0];
        Object root = hostFunctionRefs.get(refId);

        if (root == null) {
            log.warn("[RemoteJsEngine] No host function ref found for ID: " + refId);
            return null;
        }

        Object result = PropertyPathNavigator.navigatePath(parts, 1, root);

        log.debug("[RemoteJsEngine] getHostRefProperty '" + path + "' = " +
                (result != null ? result.getClass().getSimpleName() : "null"));
        return result;
    }

    /**
     * Get a property from a cached proxy object.
     * Path format: "<referenceId>::<property>" or "<referenceId>::<property>::<nestedProperty>..."
     * Example: "abc-123::username" -> retrieves username from cached object with ID abc-123
     *
     * This enables lazy loading of complex objects. Instead of eagerly serializing all
     * properties (which causes timeouts for large result sets like getUsersWithClaimValues),
     * objects are cached and properties are fetched on-demand when accessed.
     */
    private Object getProxyObjectProperty(String path) {
        String[] parts = path.split(RemoteEngineConstants.PATH_SEPARATOR);
        String refId = parts[0];
        Object root = proxyObjectCache.get(refId);

        if (root == null) {
            log.warn("[RemoteJsEngine] No proxy object found for reference ID: " + refId);
            return null;
        }

        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] Retrieved proxy object for refId: " + refId +
                    ", type: " + root.getClass().getName());
        }

        Object result = PropertyPathNavigator.navigatePath(parts, 1, root);

        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] getProxyObjectProperty '" + path + "' = " +
                    (result != null ? result.getClass().getSimpleName() : "null"));
        }
        return result;
    }

    /**
     * Set a context property value (write-back from sidecar).
     * This navigates the property path and sets the value on the target object.
     * Supports paths like:
     * "steps::1::subject::claims::http://wso2.org/claims/email"
     * Also supports host function return references via "__hostref__" prefix.
     */
    @Override
    public boolean setContextProperty(String propertyPath, Object value) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] setContextProperty called: " + propertyPath + " = " +
                    (value != null ? value.getClass().getSimpleName() : "null") + ", session: " + sessionId);
        }

        // Handle host function return references
        if (propertyPath.startsWith(RemoteEngineConstants.HOST_REF_PREFIX)) {
            return setHostRefProperty(propertyPath.substring(RemoteEngineConstants.HOST_REF_PREFIX.length()), value);
        }

        if (authContext == null) {
            log.warn("[RemoteJsEngine] No authContext available for property write");
            return false;
        }

        // Navigate to the parent object and set the final property via PropertyPathNavigator
        String[] parts = propertyPath.split(RemoteEngineConstants.PATH_SEPARATOR);
        if (parts.length == 0) {
            return false;
        }

        JsGraalAuthenticationContext jsContext = new JsGraalAuthenticationContext(authContext);
        return PropertyPathNavigator.setProperty(parts, 0, jsContext, value);
    }

    /**
     * Set a property on a stored host function return reference.
     * Path format: "<refId>::<property>::<subprop>..."
     */
    private boolean setHostRefProperty(String path, Object value) {
        String[] parts = path.split(RemoteEngineConstants.PATH_SEPARATOR);
        if (parts.length < 2) {
            log.warn("[RemoteJsEngine] setHostRefProperty requires at least refId and property: " + path);
            return false;
        }

        String refId = parts[0];
        Object root = hostFunctionRefs.get(refId);
        if (root == null) {
            log.warn("[RemoteJsEngine] No host function ref found for ID: " + refId);
            return false;
        }

        return PropertyPathNavigator.setProperty(parts, 1, root, value);
    }

    /**
     * Set up thread-local context required for host function invocation.
     * This ensures tenant context, carbon context, and JS graph builder contexts
     * are properly set.
     */
    private void setupThreadContext() {
        if (authContext != null) {
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Setting up thread context for tenant: " + authContext.getTenantDomain() +
                        ", contextId: " + authContext.getContextIdentifier());
            }
            try {
                // Set Carbon context for the current thread.
                org.wso2.carbon.context.PrivilegedCarbonContext carbonContext = org.wso2.carbon.context.PrivilegedCarbonContext
                        .getThreadLocalCarbonContext();
                carbonContext.setTenantDomain(authContext.getTenantDomain());
                carbonContext.setTenantId(
                        org.wso2.carbon.identity.core.util.IdentityTenantUtil
                                .getTenantId(authContext.getTenantDomain()));

                // Set username if available.
                if (authContext.getSubject() != null) {
                    carbonContext.setUsername(authContext.getSubject().getUserName());
                }
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Thread context set - tenantDomain: " + carbonContext.getTenantDomain() +
                            ", tenantId: " + carbonContext.getTenantId() +
                            ", username: " + carbonContext.getUsername());
                }

                // Set JsGraalGraphBuilder thread-local contexts for host function callbacks.
                // This is critical for executeStep and other functions that need the context.
                JsGraalGraphBuilder.setContextForJsThreadLocal(authContext);
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Set contextForJs ThreadLocal with authContext: " +
                            authContext.getContextIdentifier());
                }

                // Set dynamicallyBuiltBaseNode from accumulated value across gRPC callbacks.
                // In local mode, this ThreadLocal starts null during callback execution and
                // accumulates nodes via executeStepInAsyncEvent across calls on the same
                // thread.
                // In remote mode, each gRPC callback runs on a separate thread, so we persist
                // the value in accumulatedDynamicBaseNode between setup/clear pairs to
                // replicate
                // the local mode single-thread accumulation behavior.
                if (accumulatedDynamicBaseNode != null) {
                    JsGraalGraphBuilder.setDynamicallyBuiltBaseNodeThreadLocal(accumulatedDynamicBaseNode);
                    if (log.isDebugEnabled()) {
                        log.debug("[RemoteJsEngine] Set dynamicallyBuiltBaseNode from accumulated: " +
                                accumulatedDynamicBaseNode.getClass().getSimpleName());
                    }
                } else {
                    log.debug("[RemoteJsEngine] dynamicallyBuiltBaseNode accumulated is null (initial state)");
                }

                // Set currentBuilder ThreadLocal for the gRPC callback thread.
                // This is required by static methods like JsGraphBuilder.addLongWaitProcess()
                // which are used by async host functions (e.g., updateUserPassword).
                if (graphBuilder != null) {
                    JsGraalGraphBuilder.setCurrentBuilderThreadLocal(graphBuilder);
                    if (log.isDebugEnabled()) {
                        log.debug("[RemoteJsEngine] Set currentBuilder ThreadLocal: " +
                                graphBuilder.getClass().getSimpleName());
                    }
                }

            } catch (Exception e) {
                log.warn("[RemoteJsEngine] Failed to set up thread context: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Clear thread-local context after host function invocation.
     */
    private void clearThreadContext() {
        // Save dynamicallyBuiltBaseNode before clearing, so it accumulates across
        // gRPC callbacks (replicating local mode's single-thread behavior).
        try {
            AuthGraphNode currentDynamicNode = JsGraalGraphBuilder.getDynamicallyBuiltBaseNodeThreadLocal();
            if (currentDynamicNode != null) {
                this.accumulatedDynamicBaseNode = currentDynamicNode;
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Saved dynamicallyBuiltBaseNode to accumulated: " +
                            currentDynamicNode.getClass().getSimpleName());
                }
            }
            JsGraalGraphBuilder.removeContextForJsThreadLocal();
            JsGraalGraphBuilder.removeDynamicallyBuiltBaseNodeThreadLocal();
            JsGraalGraphBuilder.removeCurrentBuilderThreadLocal();
            log.debug("[RemoteJsEngine] Cleared JsGraalGraphBuilder ThreadLocals");
        } catch (Exception e) {
            log.debug("[RemoteJsEngine] Error clearing thread context: " + e.getMessage());
        }
    }

    /**
     * Truncate a string for logging to avoid excessively long log messages.
     */
    private String truncateForLog(String value) {
        if (value == null) {
            return "null";
        }
        if (value.length() > 200) {
            return value.substring(0, 200) + "...[truncated]";
        }
        return value;
    }

    /**
     * Adapt arguments to match the method's parameter types.
     * This handles reconstruction of JsAuthenticationContext and type conversions.
     *
     * @param method The method to adapt arguments for.
     * @param args   The raw arguments from the sidecar.
     * @return Adapted arguments matching the method's parameter types.
     */
    private Object[] adaptArgumentsForMethod(java.lang.reflect.Method method, Object[] args) {
        Class<?>[] paramTypes = method.getParameterTypes();
        boolean isVarArgs = method.isVarArgs();

        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] adaptArgumentsForMethod: paramCount=" + paramTypes.length +
                    ", argsCount=" + (args != null ? args.length : 0) + ", isVarArgs=" + isVarArgs);
        }

        // For varargs methods, we need special handling.
        if (isVarArgs && args != null) {
            return adaptVarArgsMethod(method, paramTypes, args);
        }

        Object[] adaptedArgs = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            if (args == null || i >= args.length) {
                adaptedArgs[i] = null;
                continue;
            }

            Object arg = args[i];
            Class<?> paramType = paramTypes[i];

            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Adapting arg[" + i + "] from " +
                        (arg != null ? arg.getClass().getSimpleName() : "null") +
                        " to " + paramType.getSimpleName());
            }

            adaptedArgs[i] = adaptSingleArgument(arg, paramType);
        }

        return adaptedArgs;
    }

    /**
     * Handle varargs method argument adaptation.
     * Filters out null values from the varargs portion, since in remote mode
     * JavaScript undefined/null placeholder arguments get serialized as explicit
     * nulls
     * through the gRPC chain. In local GraalJS mode, these would either be omitted
     * or
     * handled differently by the type conversion system. Methods like
     * httpGet(String, Object...)
     * validate varargs with instanceof checks (e.g., params[0] instanceof Map), so
     * null
     * entries cause IllegalArgumentException.
     */
    private Object[] adaptVarArgsMethod(java.lang.reflect.Method method, Class<?>[] paramTypes, Object[] args) {
        int fixedParamCount = paramTypes.length - 1;
        Class<?> varArgType = paramTypes[fixedParamCount].getComponentType();

        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] Adapting varargs method: fixedParams=" + fixedParamCount +
                    ", varArgType=" + varArgType.getSimpleName() + ", totalArgs=" + args.length);
        }

        Object[] adaptedArgs = new Object[paramTypes.length];

        // Adapt fixed parameters.
        for (int i = 0; i < fixedParamCount && i < args.length; i++) {
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Adapting fixed arg[" + i + "] from " +
                        (args[i] != null ? args[i].getClass().getSimpleName() : "null") +
                        " to " + paramTypes[i].getSimpleName());
            }
            adaptedArgs[i] = adaptSingleArgument(args[i], paramTypes[i]);
        }

        // Collect remaining arguments into varargs array, filtering out null values.
        // Null values in varargs come from JavaScript undefined/null being serialized
        // through the gRPC chain. In local mode, GraalJS handles these differently
        // (e.g., not passing them as separate arguments). Filtering nulls ensures
        // the varargs array matches what the method expects.
        java.util.List<Object> nonNullVarArgs = new java.util.ArrayList<>();
        for (int i = fixedParamCount; i < args.length; i++) {
            if (args[i] != null) {
                Object adapted = adaptSingleArgument(args[i], varArgType);
                nonNullVarArgs.add(adapted);
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Adapting vararg[" + (i - fixedParamCount) + "] from " +
                            args[i].getClass().getSimpleName() + " to " + varArgType.getSimpleName());
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Skipping null vararg at index " + i +
                            " (undefined/null from remote serialization)");
                }
            }
        }

        if (!nonNullVarArgs.isEmpty()) {
            Object[] varArgs = (Object[]) java.lang.reflect.Array.newInstance(
                    varArgType, nonNullVarArgs.size());
            for (int i = 0; i < nonNullVarArgs.size(); i++) {
                varArgs[i] = nonNullVarArgs.get(i);
            }
            adaptedArgs[fixedParamCount] = varArgs;
        } else {
            // Empty varargs array.
            adaptedArgs[fixedParamCount] = java.lang.reflect.Array.newInstance(varArgType, 0);
        }

        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] Final varargs count: " + nonNullVarArgs.size() +
                    " (from " + (args.length - fixedParamCount) + " raw args)");
        }
        return adaptedArgs;
    }

    /**
     * Get the proxy object cache for this session.
     * Used by HostCallbackServer to set the ThreadLocal before serialization.
     */
    public Map<String, Object> getProxyObjectCache() {
        return proxyObjectCache;
    }

    /**
     * Adapt a single argument to the target parameter type.
     */
    private Object adaptSingleArgument(Object arg, Class<?> paramType) {
        if (arg == null) {
            return null;
        }

        // IMPORTANT: Check for context proxy marker from sidecar.
        // When the sidecar sends a DynamicContextProxy as an argument, it serializes it
        // as a Map
        // with special marker fields. We need to reconstruct the actual object from
        // stored authContext.
        if (arg instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) arg;
            if (Boolean.TRUE.equals(map.get(RemoteEngineConstants.IS_CONTEXT_PROXY))) {
                String proxyType = (String) map.get(RemoteEngineConstants.PROXY_TYPE_FIELD);
                String basePath = (String) map.get(RemoteEngineConstants.BASE_PATH_FIELD);
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Received context proxy marker: type=" + proxyType +
                            ", basePath=" + basePath);
                }

                // Reconstruct the actual object based on proxyType and basePath
                Object reconstructed = reconstructFromContextProxy(proxyType, basePath, paramType);
                if (reconstructed != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("[RemoteJsEngine] Reconstructed " + reconstructed.getClass().getSimpleName() +
                                " from context proxy marker");
                    }
                    return reconstructed;
                }
            }
        }

        // Handle JsAuthenticationContext - reconstruct from stored authContext.
        if (paramType.getSimpleName().contains("JsAuthenticationContext") ||
                paramType.getSimpleName().contains("JsGraalAuthenticationContext")) {
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Reconstructed JsGraalAuthenticationContext from stored authContext");
            }
            return new JsGraalAuthenticationContext(authContext);
        }

        // Handle Integer conversion.
        if (paramType == Integer.class || paramType == int.class) {
            if (arg instanceof Number) {
                return ((Number) arg).intValue();
            } else if (arg instanceof String) {
                try {
                    return Integer.parseInt((String) arg);
                } catch (NumberFormatException e) {
                    log.warn("[RemoteJsEngine] Could not parse Integer from: " + arg);
                    return arg;
                }
            }
        }

        // Handle Long conversion.
        if (paramType == Long.class || paramType == long.class) {
            if (arg instanceof Number) {
                return ((Number) arg).longValue();
            }
        }

        // Handle Double conversion.
        if (paramType == Double.class || paramType == double.class) {
            if (arg instanceof Number) {
                return ((Number) arg).doubleValue();
            }
        }

        // Handle Boolean conversion.
        if (paramType == Boolean.class || paramType == boolean.class) {
            if (arg instanceof Boolean) {
                return arg;
            } else if (arg instanceof String) {
                return Boolean.parseBoolean((String) arg);
            }
        }

        // Handle String conversion.
        if (paramType == String.class) {
            return arg.toString();
        }

        // Handle List type conversion.
        if (java.util.List.class.isAssignableFrom(paramType)) {
            if (arg instanceof java.util.List) {
                return arg;
            } else if (arg instanceof Object[]) {
                return java.util.Arrays.asList((Object[]) arg);
            }
        }

        // Handle array type conversion.
        if (paramType.isArray()) {
            if (arg instanceof java.util.List) {
                java.util.List<?> list = (java.util.List<?>) arg;
                Class<?> componentType = paramType.getComponentType();
                if (componentType == String.class) {
                    return list.toArray(new String[0]);
                } else {
                    return list.toArray();
                }
            }
        }

        // Handle Map to Object conversion (for varargs with map/object arguments).
        // Coerce whole-number Doubles to Integers inside Maps, since protobuf
        // deserializes
        // all numbers as Double but host function implementations expect Integer for
        // values
        // like max-age, port numbers, etc. (matching in-process GraalJS behavior).
        if (paramType == Object.class) {
            if (arg instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> mapArg = (Map<String, Object>) arg;
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Coercing number types in Map with " + mapArg.size() + " entries");
                }
                return coerceMapNumberTypes(mapArg);
            }
            return arg;
        }

        // Direct assignment for compatible types.
        return arg;
    }

    /**
     * Coerce whole-number Double values inside a Map to Integer.
     * Creates a new mutable HashMap to avoid issues with immutable/protobuf maps.
     * Protobuf deserializes all JS numbers as Double, but Java host functions
     * expect Integer for integer-valued options (e.g., max-age in setCookie).
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> coerceMapNumberTypes(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>(map);
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            Object val = entry.getValue();
            if (val instanceof Double) {
                double d = (Double) val;
                if (d == Math.floor(d) && !Double.isInfinite(d)) {
                    // Whole number — convert to Integer (or Long if out of int range)
                    if (d >= Integer.MIN_VALUE && d <= Integer.MAX_VALUE) {
                        entry.setValue((int) d);
                        if (log.isDebugEnabled()) {
                            log.debug("[RemoteJsEngine] Coerced " + entry.getKey() + ": " + d + " -> " + (int) d);
                        }
                    } else {
                        entry.setValue((long) d);
                    }
                }
            } else if (val instanceof Map) {
                entry.setValue(coerceMapNumberTypes((Map<String, Object>) val));
            }
        }
        return result;
    }

    /**
     * Reconstruct a context object from a proxy marker sent by the sidecar.
     * This handles nested properties like context.currentKnownSubject,
     * context.steps[1], etc.
     *
     * @param proxyType The type of proxy (e.g., "context", "authenticateduser",
     *                  "step")
     * @param basePath  The path to the property (e.g., "", "currentKnownSubject",
     *                  "steps::1")
     * @param paramType The expected parameter type from the method signature
     * @return The reconstructed object, or null if reconstruction fails
     */
    private Object reconstructFromContextProxy(String proxyType, String basePath, Class<?> paramType) {

        // If basePath is empty or null, return the full context
        if (basePath == null || basePath.isEmpty()) {
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Reconstructing root context");
            }
            return new JsGraalAuthenticationContext(authContext);
        }

        // Direct reconstruction for "authenticateduser" with path "steps::N::subject".
        // This bypasses proxy navigation which can fail due to JsStep.getSubject()
        // returning null
        // when the IdP data lookup doesn't match, causing createJsAuthenticatedUser to
        // throw.
        if ("authenticateduser".equals(proxyType) && basePath.matches("steps::\\d+::subject")) {
            String[] parts = basePath.split(RemoteEngineConstants.PATH_SEPARATOR);
            int stepNum = Integer.parseInt(parts[1]);
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Direct reconstruction of authenticateduser for step " + stepNum);
            }

            if (authContext.getSequenceConfig() != null) {
                // Find the authenticated IDP for this step
                String authenticatedIdp = null;
                for (StepConfig sc : authContext.getSequenceConfig().getStepMap().values()) {
                    if (sc.getOrder() == stepNum) {
                        authenticatedIdp = sc.getAuthenticatedIdP();
                        break;
                    }
                }

                if (authenticatedIdp != null) {
                    // Look up the user from authenticated IdP data (same logic as
                    // JsStep.getSubject())
                    AuthenticatedIdPData idPData = authContext.getCurrentAuthenticatedIdPs().get(authenticatedIdp);
                    if (idPData == null) {
                        idPData = authContext.getPreviousAuthenticatedIdPs().get(authenticatedIdp);
                    }
                    if (idPData != null && idPData.getUser() != null) {
                        Object result = JsWrapperFactoryProvider.getInstance().getWrapperFactory()
                                .createJsAuthenticatedUser(authContext, idPData.getUser(), stepNum, authenticatedIdp);
                        if (log.isDebugEnabled()) {
                            log.debug("[RemoteJsEngine] Directly reconstructed " +
                                    result.getClass().getSimpleName() + " for step " + stepNum);
                        }
                        return result;
                    }
                    log.warn("[RemoteJsEngine] No authenticated user found for IdP: " + authenticatedIdp +
                            " in step " + stepNum);
                } else {
                    log.warn("[RemoteJsEngine] No authenticated IdP found for step " + stepNum);
                }
            }
        }

        // Generic proxy navigation fallback for other proxy types/paths
        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] Navigating to nested property: " + basePath);
        }

        try {
            String[] pathParts = basePath.split(RemoteEngineConstants.PATH_SEPARATOR);
            Object current = new JsGraalAuthenticationContext(authContext);

            for (String part : pathParts) {
                if (current == null) {
                    log.warn("[RemoteJsEngine] Null encountered while navigating path at: " + part);
                    return null;
                }

                if (part.matches("\\d+")) {
                    // Numeric segment: try ProxyArray.get first, then reflection fallback
                    if (current instanceof org.graalvm.polyglot.proxy.ProxyArray) {
                        current = ((org.graalvm.polyglot.proxy.ProxyArray) current).get(Integer.parseInt(part));
                    } else {
                        // Reflection fallback for classloader issues where instanceof doesn't match
                        try {
                            java.lang.reflect.Method getMethod = current.getClass().getMethod("get", long.class);
                            current = getMethod.invoke(current, (long) Integer.parseInt(part));
                        } catch (NoSuchMethodException nsme) {
                            if (current instanceof org.graalvm.polyglot.proxy.ProxyObject) {
                                current = ((org.graalvm.polyglot.proxy.ProxyObject) current).getMember(part);
                            } else {
                                log.warn("[RemoteJsEngine] Cannot navigate numeric segment '" + part +
                                        "' on type: " + current.getClass().getName());
                                return null;
                            }
                        }
                    }
                } else if (current instanceof org.graalvm.polyglot.proxy.ProxyObject) {
                    current = ((org.graalvm.polyglot.proxy.ProxyObject) current).getMember(part);
                } else {
                    log.warn("[RemoteJsEngine] Cannot navigate to '" + part + "' on type: " +
                            current.getClass().getName());
                    return null;
                }
            }

            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Successfully navigated to: " + basePath +
                        ", result type: " + (current != null ? current.getClass().getSimpleName() : "null"));
            }
            return current;

        } catch (Exception e) {
            log.error("[RemoteJsEngine] Error navigating to property '" + basePath + "': " + e.getMessage(), e);
            return null;
        }
    }

    private void ensureConnected() throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] ensureConnected - transport: " + transport.getClass().getSimpleName() +
                    ", connected: " + transport.isConnected());
        }
        if (!transport.isConnected()) {
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Connecting transport");
            }
            transport.connect();
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Connected to remote engine successfully");
            }
        }
    }
}
