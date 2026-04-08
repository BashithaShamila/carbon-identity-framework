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
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextData;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.EvaluateRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.EvaluateResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ExecuteCallbackRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ExecuteCallbackResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.HostFunctionDefinition;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.SerializedValue;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Remote JavaScript engine that communicates with a GraalJS External via
 * pluggable transport.
 * Each instance represents a session with the External.
 * <p>
 * Host function calls from the External are routed back to IS via the callback
 * server.
 * <p>
 * This implementation is decoupled from specific transport mechanisms
 * through the RemoteEngineTransport and CallbackServer abstractions.
 * <p>
 * Responsibilities are delegated to focused collaborators:
 * <ul>
 *   <li>{@link ThreadContextManager} — thread-local context setup/cleanup for gRPC callbacks</li>
 *   <li>{@link ArgumentAdapter} — External argument adaptation and type conversion</li>
 *   <li>{@link ProxyReferenceCache} — proxy object and host function return reference caching</li>
 * </ul>
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

    // Collaborators — each handles a single responsibility
    private final ThreadContextManager threadContextManager;
    private final ArgumentAdapter argumentAdapter;
    private final ProxyReferenceCache proxyReferenceCache;

    private boolean closed = false;

    /**
     * Create a new remote JavaScript engine.
     *
     * @param transport   The transport layer for communicating with the remote engine.
     * @param authContext The authentication context for this session.
     */
    public RemoteJsEngine(RemoteEngineTransport transport, AuthenticationContext authContext) {
        this.transport = transport;
        this.authContext = authContext;
        this.sessionId = UUID.randomUUID().toString();
        this.threadContextManager = new ThreadContextManager(authContext);
        this.argumentAdapter = new ArgumentAdapter(authContext);
        this.proxyReferenceCache = new ProxyReferenceCache();
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
        threadContextManager.setGraphBuilder(graphBuilder);
    }

    /**
     * Get the accumulated dynamicallyBuiltBaseNode value.
     * This is the value accumulated across gRPC callbacks, replicating
     * the local mode ThreadLocal behavior across threads.
     *
     * @return The accumulated AuthGraphNode, or null if none was built.
     */
    public AuthGraphNode getAccumulatedDynamicBaseNode() {
        return threadContextManager.getAccumulatedDynamicBaseNode();
    }

    /**
     * Reset the accumulated dynamicallyBuiltBaseNode.
     * Should be called before a new callback evaluation cycle begins.
     */
    public void resetAccumulatedDynamicBaseNode() {
        threadContextManager.resetAccumulatedDynamicBaseNode();
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
                // Context state is sent as structured ContextData and the External accesses it
                // via DynamicContextProxy callbacks. Serializing it here causes a toString()
                // fallback with WARN log. If this binding is ever needed, implement a proper
                // toProto() conversion for JsGraalAuthenticationContext first.
                if (!RemoteEngineConstants.CONTEXT_BINDING_KEY.equals(entry.getKey()) && !hostFunctions.containsKey(entry.getKey())) {
                    requestBuilder.putBindings(entry.getKey(), ProtobufSerializer.toProto(entry.getValue()));
                }
            }

            // Add host function definitions so External knows to call back
            for (String funcName : hostFunctions.keySet()) {
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Registering host function: " + funcName);
                }
                requestBuilder.addHostFunctions(
                        HostFunctionDefinition.newBuilder()
                                .setName(funcName)
                                .build());
            }

            // Add context data for context proxy reconstruction in External
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
                            ", ExternalReported=" + response.getElapsedMs() + "ms");
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
                    // and causes a toString() fallback with WARN log). The External detects
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
                // Context state is sent as structured ContextData and the External accesses it
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

            // Add host function definitions so External knows to create stubs for callbacks
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
                            ", ExternalReported=" + response.getElapsedMs() + "ms");
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

    // ======================== HostFunctionHandler Implementation ========================

    /**
     * Handle host function callback from External.
     * This is called when the External JavaScript invokes a host function.
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
        threadContextManager.setup();

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
                    Object[] adaptedArgs = argumentAdapter.adaptArgumentsForMethod(method, args);

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
                            Object[] adaptedArgs = argumentAdapter.adaptArgumentsForMethod(method, args);
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
            threadContextManager.clear();
        }
    }

    @Override
    public String storeObjectReference(Object obj) {
        return proxyReferenceCache.storeObjectReference(obj);
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
            return proxyReferenceCache.getProxyObjectProperty(
                    propertyPath.substring(RemoteEngineConstants.PROXY_REF_PREFIX.length()));
        }

        // Handle host function return references: "__hostref__::<refId>::<property>"
        if (propertyPath.startsWith(RemoteEngineConstants.HOST_REF_PREFIX)) {
            return proxyReferenceCache.getHostRefProperty(
                    propertyPath.substring(RemoteEngineConstants.HOST_REF_PREFIX.length()));
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
     * Set a context property value (write-back from External).
     * This navigates the property path and sets the value on the target object.
     * Supports paths like:
     * "steps::1::subject::claims::http://wso2.org/claims/email"
     * Also supports host function return references via "__hostref__" prefix and
     * proxy object references via "__proxyref__" prefix.
     */
    @Override
    public boolean setContextProperty(String propertyPath, Object value) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] setContextProperty called: " + propertyPath + " = " +
                    (value != null ? value.getClass().getSimpleName() : "null") + ", session: " + sessionId);
        }

        // Handle proxy object references (list elements from host function returns).
        if (propertyPath.startsWith(RemoteEngineConstants.PROXY_REF_PREFIX)) {
            return proxyReferenceCache.setProxyObjectProperty(
                    propertyPath.substring(RemoteEngineConstants.PROXY_REF_PREFIX.length()), value);
        }

        // Handle host function return references.
        if (propertyPath.startsWith(RemoteEngineConstants.HOST_REF_PREFIX)) {
            return proxyReferenceCache.setHostRefProperty(
                    propertyPath.substring(RemoteEngineConstants.HOST_REF_PREFIX.length()), value);
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
     * Get the proxy object cache for this session.
     * Used by HostCallbackServer to set the ThreadLocal before serialization.
     */
    public Map<String, Object> getProxyObjectCache() {
        return proxyReferenceCache.getCache();
    }

    // ======================== Private Helpers ========================

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
