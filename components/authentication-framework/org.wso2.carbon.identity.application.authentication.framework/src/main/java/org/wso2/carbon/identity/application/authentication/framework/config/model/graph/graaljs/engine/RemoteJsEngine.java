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
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder;
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
 * Remote JavaScript engine that communicates with a GraalJS sidecar via pluggable transport.
 * Each instance represents a session with the sidecar.
 * <p>
 * Host function calls from the sidecar are routed back to IS via the callback server.
 * <p>
 * This implementation is decoupled from specific transport mechanisms (UDS, gRPC, etc.)
 * through the RemoteEngineTransport and CallbackServer abstractions.
 */
public class RemoteJsEngine implements JsEngine, CallbackServer.HostFunctionHandler {

    private static final Log log = LogFactory.getLog(RemoteJsEngine.class);

    private final RemoteEngineTransport transport;
    private final CallbackServer callbackServer;
    private final String sessionId;
    private final AuthenticationContext authContext;
    // CRITICAL FIX: Use ConcurrentHashMap to prevent race conditions during concurrent access
    // from multiple threads (e.g., main execution thread + callback handler threads)
    private final Map<String, Object> bindings = new ConcurrentHashMap<>();
    private final Map<String, Object> hostFunctions = new ConcurrentHashMap<>();
    private boolean closed = false;
    private boolean handlerRegistered = false;

    /**
     * Create a new remote JavaScript engine.
     *
     * @param transport      The transport layer for communicating with the remote engine.
     * @param callbackServer The callback server for receiving host function invocations.
     * @param authContext    The authentication context for this session.
     */
    public RemoteJsEngine(RemoteEngineTransport transport, CallbackServer callbackServer,
                          AuthenticationContext authContext) {
        this.transport = transport;
        this.callbackServer = callbackServer;
        this.authContext = authContext;
        this.sessionId = UUID.randomUUID().toString();
        log.info("[RemoteJsEngine] Created with session: " + sessionId +
                ", transport: " + transport.getClass().getSimpleName() +
                ", callbackServer: " + callbackServer.getClass().getSimpleName() +
                ", SP: " + (authContext != null ? authContext.getServiceProviderName() : "null"));
    }

    @Override
    public EvaluationResult evaluate(String script, String sourceIdentifier, Map<String, Object> initialBindings) {
        long startTime = System.currentTimeMillis();
        log.info("[RemoteJsEngine] evaluate() called, session: " + sessionId + ", sourceId: " + sourceIdentifier);

        try {
            log.info("[RemoteJsEngine] Ensuring connection to remote engine");
            ensureConnected();
            log.info("[RemoteJsEngine] Connection established, registering handler...");
            ensureHandlerRegistered();
            log.info("[RemoteJsEngine] Handler registered: " + handlerRegistered);

            // Apply initial bindings
            if (initialBindings != null) {
                bindings.putAll(initialBindings);
            }

            // Build the request
            EvaluateRequest.Builder requestBuilder = EvaluateRequest.newBuilder()
                    .setSessionId(sessionId)
                    .setScript(script)
                    .setSourceIdentifier(sourceIdentifier != null ? sourceIdentifier : "script");

            // Set callback address for host function callbacks
            String callbackAddress = callbackServer.getCallbackAddress();
            log.info("[RemoteJsEngine] Callback address: " + callbackAddress);
            if (callbackAddress != null) {
                requestBuilder.setCallbackSocketPath(callbackAddress);
            }

            // Serialize bindings for initial script evaluation
            // Host functions are registered separately via HostFunctionDefinition
            // ProtobufSerializer.toProto() converts Java objects to protobuf SerializedValue
            log.info("[RemoteJsEngine] Serializing " + bindings.size() + " bindings, " +
                    hostFunctions.size() + " host functions");
            for (Map.Entry<String, Object> entry : bindings.entrySet()) {
                // Skip host function objects - they're registered via addHostFunctions() instead
                if (!hostFunctions.containsKey(entry.getKey())) {
                    requestBuilder.putBindings(entry.getKey(), ProtobufSerializer.toProto(entry.getValue()));
                }
            }

            // Add host function definitions so sidecar knows to call back
            for (String funcName : hostFunctions.keySet()) {
                log.info("[RemoteJsEngine] Registering host function: " + funcName);
                requestBuilder.addHostFunctions(
                        HostFunctionDefinition.newBuilder()
                                .setName(funcName)
                                .build());
            }

            // Add context data for context proxy reconstruction in sidecar
            if (authContext != null) {
                log.info("[RemoteJsEngine] Adding context data, step: " + authContext.getCurrentStep() +
                        ", subject: "
                        + (authContext.getSubject() != null ? authContext.getSubject().getUserName() : "null"));
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

            // Send request and get response (blocking)
            log.info("[RemoteJsEngine] Sending evaluate request to remote engine...");
            EvaluateResponse response = transport.sendEvaluate(requestBuilder.build());
            log.info("[RemoteJsEngine] Received response, success: " + response.getSuccess());

            if (response.getSuccess()) {
                // Update bindings from response
                Map<String, Object> updatedBindings = new HashMap<>();
                for (Map.Entry<String, SerializedValue> entry : response.getUpdatedBindingsMap().entrySet()) {
                    Object deserialized = ProtobufSerializer.fromProto(entry.getValue());
                    updatedBindings.put(entry.getKey(), deserialized);
                    bindings.put(entry.getKey(), deserialized);
                }

                Object result = ProtobufSerializer.fromProto(response.getResult());

                return EvaluationResult.builder()
                        .success(true)
                        .result(result)
                        .updatedBindings(updatedBindings)
                        .elapsedMs(response.getElapsedMs())
                        .build();
            } else {
                return EvaluationResult.failure(
                        response.getErrorMessage(),
                        response.getErrorType(),
                        response.getElapsedMs());
            }

        } catch (IOException e) {
            long elapsed = System.currentTimeMillis() - startTime;
            log.error("IOException during remote evaluation", e);
            return EvaluationResult.failure("Remote engine communication failed: " + e.getMessage(),
                    "IOException", elapsed);
        }
    }

    @Override
    public EvaluationResult executeCallback(String functionSource, Object[] arguments,
            Map<String, Object> callbackBindings, AuthenticationContext context) {
        long startTime = System.currentTimeMillis();
        log.info("[RemoteJsEngine] executeCallback() called, session: " + sessionId +
                ", function length: " + (functionSource != null ? functionSource.length() : 0) +
                ", args: " + (arguments != null ? arguments.length : 0));

        try {
            log.info("[RemoteJsEngine] executeCallback - ensuring connection to remote engine");
            ensureConnected();
            log.info("[RemoteJsEngine] executeCallback - connection OK, registering handler...");
            ensureHandlerRegistered();
            log.info("[RemoteJsEngine] executeCallback - handler registered: " + handlerRegistered);

            // Apply callback bindings
            // CRITICAL: callbackBindings come from AuthenticationContext and are already serialized
            // by GraalSerializer (through JsGraalGraphBuilderFactory.persistCurrentContext or LocalJsEngine).
            // They contain: HashMap, ArrayList, primitives, GraalSerializableJsFunction objects.
            // We store them as-is here, and later ProtobufSerializer.toProto() will handle them correctly
            // (it calls GraalSerializer.toJsSerializableInternal which is idempotent for already-serialized objects).
            if (callbackBindings != null && !callbackBindings.isEmpty()) {
                log.info("[RemoteJsEngine] Applying " + callbackBindings.size() + " callback bindings: " +
                        callbackBindings.keySet());
                for (Map.Entry<String, Object> entry : callbackBindings.entrySet()) {
                    Object value = entry.getValue();
                    log.info("[RemoteJsEngine] Callback binding: " + entry.getKey() + " = " +
                            (value != null ? value.getClass().getSimpleName() + ": " + value : "null"));

                    // Store binding - ConcurrentHashMap ensures thread-safe access
                    bindings.put(entry.getKey(), value);
                }
            } else {
                log.info("[RemoteJsEngine] No callback bindings provided (null or empty). " +
                        "callbackBindings=" + callbackBindings);
            }

            // Build the request
            ExecuteCallbackRequest.Builder requestBuilder = ExecuteCallbackRequest.newBuilder()
                    .setSessionId(sessionId)
                    .setFunctionSource(functionSource);

            // Set callback address
            String callbackAddress = callbackServer.getCallbackAddress();
            log.info("[RemoteJsEngine] executeCallback - callback address: " + callbackAddress);
            if (callbackAddress != null) {
                requestBuilder.setCallbackSocketPath(callbackAddress);
            }

            // Add context data for proxy object reconstruction
            if (context != null) {
                log.info("[RemoteJsEngine] Adding context data, step: " + context.getCurrentStep() +
                        ", subject: " + (context.getSubject() != null ? context.getSubject().getUserName() : "null"));
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
                log.info("[RemoteJsEngine] Serializing " + arguments.length + " arguments");
                for (int i = 0; i < arguments.length; i++) {
                    log.info("[RemoteJsEngine] Arg[" + i + "] type: " +
                            (arguments[i] != null ? arguments[i].getClass().getName() : "null"));
                    requestBuilder.addArguments(ProtobufSerializer.toProto(arguments[i]));
                }
            }

            // Serialize bindings (excluding host functions)
            // SERIALIZATION FLOW:
            // 1. bindings map contains objects already serialized by GraalSerializer (HashMap, ArrayList, etc.)
            // 2. ProtobufSerializer.toProto() will:
            //    a. Call GraalSerializer.toJsSerializableInternal() first (idempotent for already-serialized)
            //    b. Convert to protobuf SerializedValue for transport to sidecar
            // 3. Special handling: GraalSerializableJsFunction objects are serialized to SerializedFunction
            log.info("[RemoteJsEngine] Total bindings to serialize: " + bindings.size() +
                    ", keys: " + bindings.keySet());
            log.info("[RemoteJsEngine] Host functions (excluded from bindings): " + hostFunctions.keySet());
            int bindingsAdded = 0;
            for (Map.Entry<String, Object> entry : bindings.entrySet()) {
                if (!hostFunctions.containsKey(entry.getKey())) {
                    Object value = entry.getValue();
                    log.info("[RemoteJsEngine] Serializing binding: " + entry.getKey() + " = " +
                            (value != null ? value.getClass().getSimpleName() + ": " + value : "null"));
                    // ProtobufSerializer handles: primitives, String, Map, List, GraalSerializableJsFunction
                    requestBuilder.putBindings(entry.getKey(), ProtobufSerializer.toProto(value));
                    bindingsAdded++;
                }
            }
            log.info("[RemoteJsEngine] Bindings serialized: " + bindingsAdded);

            // Add host function definitions so sidecar knows to create stubs for callbacks
            log.info("[RemoteJsEngine] Adding " + hostFunctions.size() + " host function definitions");
            for (String funcName : hostFunctions.keySet()) {
                log.info("[RemoteJsEngine] Adding host function: " + funcName);
                requestBuilder.addHostFunctions(
                        HostFunctionDefinition.newBuilder()
                                .setName(funcName)
                                .build());
            }

            // Send request and get response (blocking)
            log.info("[RemoteJsEngine] Sending executeCallback request to remote engine...");
            ExecuteCallbackResponse response = transport.sendExecuteCallback(requestBuilder.build());
            log.info("[RemoteJsEngine] executeCallback response - success: " + response.getSuccess() +
                    ", elapsed: " + response.getElapsedMs() + "ms");

            if (response.getSuccess()) {
                // Update bindings from response
                Map<String, Object> updatedBindings = new HashMap<>();
                for (Map.Entry<String, SerializedValue> entry : response.getUpdatedBindingsMap().entrySet()) {
                    Object deserialized = ProtobufSerializer.fromProto(entry.getValue());
                    updatedBindings.put(entry.getKey(), deserialized);
                    bindings.put(entry.getKey(), deserialized);
                }

                Object result = ProtobufSerializer.fromProto(response.getResult());

                return EvaluationResult.builder()
                        .success(true)
                        .result(result)
                        .updatedBindings(updatedBindings)
                        .elapsedMs(response.getElapsedMs())
                        .build();
            } else {
                return EvaluationResult.failure(
                        response.getErrorMessage(),
                        "ExecutionError",
                        response.getElapsedMs());
            }

        } catch (IOException e) {
            long elapsed = System.currentTimeMillis() - startTime;
            log.error("IOException during remote callback execution", e);
            return EvaluationResult.failure("Remote callback execution failed: " + e.getMessage(),
                    "IOException", elapsed);
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

            // Unregister from callback server
            if (handlerRegistered) {
                callbackServer.unregisterHandler(sessionId);
            }

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
        log.info("[RemoteJsEngine] invokeHostFunction called: " + functionName + " with " +
                (args != null ? args.length : 0) + " args, session: " + sessionId);

        // Log raw argument details for debugging.
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                log.info("[RemoteJsEngine] Raw arg[" + i + "]: type=" +
                        (args[i] != null ? args[i].getClass().getName() : "null") +
                        ", value=" + (args[i] != null ? truncateForLog(args[i].toString()) : "null"));
            }
        }

        Object hostFunc = hostFunctions.get(functionName);
        if (hostFunc == null) {
            log.error("[RemoteJsEngine] Unknown host function: " + functionName +
                    ", available: " + hostFunctions.keySet());
            throw new IllegalArgumentException("Unknown host function: " + functionName);
        }
        log.info("[RemoteJsEngine] Found host function impl: " + hostFunc.getClass().getName());

        // Set up thread-local context for the host function invocation.
        // This ensures proper tenant context is available.
        setupThreadContext();

        try {
            // Find the @HostAccess.Export method to invoke.
            for (java.lang.reflect.Method method : hostFunc.getClass().getMethods()) {
                if (method.isAnnotationPresent(org.graalvm.polyglot.HostAccess.Export.class)) {
                    log.info("[RemoteJsEngine] Found @HostAccess.Export method: " + method.getName() +
                            ", params: " + method.getParameterCount() +
                            ", paramTypes: " + java.util.Arrays.toString(method.getParameterTypes()));

                    // Adapt arguments to match method parameter types.
                    Object[] adaptedArgs = adaptArgumentsForMethod(method, args);

                    // Log adapted arguments.
                    for (int i = 0; i < adaptedArgs.length; i++) {
                        log.info("[RemoteJsEngine] Adapted arg[" + i + "]: type=" +
                                (adaptedArgs[i] != null ? adaptedArgs[i].getClass().getName() : "null"));
                    }

                    log.info("[RemoteJsEngine] Invoking method with " + adaptedArgs.length + " adapted args");
                    try {
                        Object result = method.invoke(hostFunc, adaptedArgs);
                        log.info("[RemoteJsEngine] Method returned: " +
                                (result != null ? result.getClass().getName() + "=" + result : "null"));
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
            log.info("[RemoteJsEngine] No @HostAccess.Export found, trying interface methods...");
            Class<?>[] hostInterfaces = hostFunc.getClass().getInterfaces();
            for (Class<?> iface : hostInterfaces) {
                for (java.lang.reflect.Method method : iface.getMethods()) {
                    if (!method.isDefault() && method.getParameterCount() <= args.length) {
                        try {
                            Object[] adaptedArgs = adaptArgumentsForMethod(method, args);
                            log.info("[RemoteJsEngine] Trying method: " + iface.getName() + "." + method.getName());
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

    /**
     * Get a context property value for the dynamic context proxy.
     * This navigates the property path on the real JsGraalAuthenticationContext.
     */
    @Override
    public Object getContextProperty(String propertyPath) throws Exception {
        log.debug("[RemoteJsEngine] getContextProperty called: " + propertyPath + ", session: " + sessionId);

        if (authContext == null) {
            log.warn("[RemoteJsEngine] No authContext available for property access");
            return null;
        }

        // Create the JsGraalAuthenticationContext wrapper
        JsGraalAuthenticationContext jsContext = new JsGraalAuthenticationContext(authContext);

        // Navigate the property path: "request.params.foo" -> ["request", "params",
        // "foo"]
        String[] parts = propertyPath.split("\\.");
        Object current = jsContext;

        for (String part : parts) {
            if (current == null) {
                return null;
            }

            // Handle special case for array-like access (e.g., "steps.1")
            // JsGraalSteps implements ProxyArray, so we need to use get(long) for numeric indices
            if (part.matches("\\d+") && current instanceof org.graalvm.polyglot.proxy.ProxyArray) {
                // Numeric index access on ProxyArray (e.g., JsGraalSteps)
                int index = Integer.parseInt(part);
                org.graalvm.polyglot.proxy.ProxyArray proxyArray = (org.graalvm.polyglot.proxy.ProxyArray) current;
                current = proxyArray.get(index);
                log.debug("[RemoteJsEngine] Accessed array index " + index + " -> " +
                        (current != null ? current.getClass().getSimpleName() : "null"));
            } else if (part.matches("\\d+") && current instanceof org.graalvm.polyglot.proxy.ProxyObject) {
                // Numeric index access on ProxyObject - try getMember with string key
                org.graalvm.polyglot.proxy.ProxyObject proxy = (org.graalvm.polyglot.proxy.ProxyObject) current;
                current = proxy.getMember(part);
            } else if (current instanceof org.graalvm.polyglot.proxy.ProxyObject) {
                // Standard member access
                org.graalvm.polyglot.proxy.ProxyObject proxy = (org.graalvm.polyglot.proxy.ProxyObject) current;
                current = proxy.getMember(part);
                log.debug("[RemoteJsEngine] Accessed member '" + part + "' on proxy -> " +
                        (current != null ? current.getClass().getSimpleName() : "null"));
            } else if (current instanceof java.util.Map) {
                // Map access
                @SuppressWarnings("unchecked")
                java.util.Map<String, Object> map = (java.util.Map<String, Object>) current;
                current = map.get(part);
            } else {
                // Try reflection as last resort
                try {
                    String getterName = "get" + Character.toUpperCase(part.charAt(0)) + part.substring(1);
                    java.lang.reflect.Method getter = current.getClass().getMethod(getterName);
                    current = getter.invoke(current);
                } catch (NoSuchMethodException e) {
                    log.debug("[RemoteJsEngine] No getter for property: " + part);
                    return null;
                }
            }
        }

        log.debug("[RemoteJsEngine] getContextProperty '" + propertyPath + "' = " +
                (current != null ? current.getClass().getSimpleName() : "null"));
        return current;
    }

    /**
     * Set a context property value (write-back from sidecar).
     * This navigates the property path and sets the value on the target object.
     * Supports paths like: "currentKnownSubject.localClaims.email"
     */
    @Override
    public boolean setContextProperty(String propertyPath, Object value) throws Exception {
        log.info("[RemoteJsEngine] setContextProperty called: " + propertyPath + " = " +
                (value != null ? value.getClass().getSimpleName() : "null") + ", session: " + sessionId);

        if (authContext == null) {
            log.warn("[RemoteJsEngine] No authContext available for property write");
            return false;
        }

        // Navigate to the parent object and then set the final property
        String[] parts = propertyPath.split("\\.");
        if (parts.length == 0) {
            return false;
        }

        // Create the JsGraalAuthenticationContext wrapper
        JsGraalAuthenticationContext jsContext = new JsGraalAuthenticationContext(authContext);

        // Navigate to the parent (all parts except the last one)
        Object parent = jsContext;
        for (int i = 0; i < parts.length - 1; i++) {
            String part = parts[i];
            if (parent == null) {
                log.warn("[RemoteJsEngine] Null encountered at path segment: " + part);
                return false;
            }

            if (parent instanceof org.graalvm.polyglot.proxy.ProxyObject) {
                org.graalvm.polyglot.proxy.ProxyObject proxy = (org.graalvm.polyglot.proxy.ProxyObject) parent;
                parent = proxy.getMember(part);
            } else if (parent instanceof java.util.Map) {
                @SuppressWarnings("unchecked")
                java.util.Map<String, Object> map = (java.util.Map<String, Object>) parent;
                parent = map.get(part);
            } else {
                // Try reflection getter
                try {
                    String getterName = "get" + Character.toUpperCase(part.charAt(0)) + part.substring(1);
                    java.lang.reflect.Method getter = parent.getClass().getMethod(getterName);
                    parent = getter.invoke(parent);
                } catch (NoSuchMethodException e) {
                    log.warn("[RemoteJsEngine] Cannot navigate path segment: " + part);
                    return false;
                }
            }
        }

        // Now set the final property
        String finalPart = parts[parts.length - 1];

        if (parent == null) {
            log.warn("[RemoteJsEngine] Parent is null, cannot set: " + finalPart);
            return false;
        }

        // Try different ways to set the property
        if (parent instanceof org.graalvm.polyglot.proxy.ProxyObject) {
            // ProxyObject.putMember requires a org.graalvm.polyglot.Value, not Object
            // Our writable proxies (JsWritableParameters, JsClaims) implement putMember
            // using the underlying data structure, so use reflection to call it
            try {
                // Try to find and call a putMember method that accepts our value type
                java.lang.reflect.Method[] methods = parent.getClass().getMethods();
                for (java.lang.reflect.Method method : methods) {
                    if ("putMember".equals(method.getName()) && method.getParameterCount() == 2) {
                        Class<?>[] paramTypes = method.getParameterTypes();
                        if (paramTypes[0] == String.class && paramTypes[1].isAssignableFrom(value.getClass())) {
                            method.invoke(parent, finalPart, value);
                            log.info("[RemoteJsEngine] Successfully set property via putMember: " + propertyPath);
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                log.debug("[RemoteJsEngine] putMember invocation failed: " + e.getMessage());
            }
        }

        if (parent instanceof java.util.Map) {
            @SuppressWarnings("unchecked")
            java.util.Map<String, Object> map = (java.util.Map<String, Object>) parent;
            map.put(finalPart, value);
            log.info("[RemoteJsEngine] Successfully set property in map: " + propertyPath);
            return true;
        }

        // Try reflection setter
        try {
            String setterName = "set" + Character.toUpperCase(finalPart.charAt(0)) + finalPart.substring(1);
            java.lang.reflect.Method[] methods = parent.getClass().getMethods();
            for (java.lang.reflect.Method method : methods) {
                if (method.getName().equals(setterName) && method.getParameterCount() == 1) {
                    method.invoke(parent, value);
                    log.info("[RemoteJsEngine] Successfully set property via setter: " + propertyPath);
                    return true;
                }
            }
        } catch (Exception e) {
            log.debug("[RemoteJsEngine] Setter failed for: " + finalPart, e);
        }

        log.warn("[RemoteJsEngine] Could not set property: " + propertyPath);
        return false;
    }

    /**
     * Set up thread-local context required for host function invocation.
     * This ensures tenant context, carbon context, and JS graph builder contexts
     * are properly set.
     */
    private void setupThreadContext() {
        if (authContext != null) {
            log.info("[RemoteJsEngine] Setting up thread context for tenant: " + authContext.getTenantDomain() +
                    ", contextId: " + authContext.getContextIdentifier());
            try {
                // Set Carbon context for the current thread.
                org.wso2.carbon.context.PrivilegedCarbonContext carbonContext =
                        org.wso2.carbon.context.PrivilegedCarbonContext
                        .getThreadLocalCarbonContext();
                carbonContext.setTenantDomain(authContext.getTenantDomain());
                carbonContext.setTenantId(
                        org.wso2.carbon.identity.core.util.IdentityTenantUtil
                                .getTenantId(authContext.getTenantDomain()));

                // Set username if available.
                if (authContext.getSubject() != null) {
                    carbonContext.setUsername(authContext.getSubject().getUserName());
                }
                log.info("[RemoteJsEngine] Thread context set - tenantDomain: " + carbonContext.getTenantDomain() +
                        ", tenantId: " + carbonContext.getTenantId() +
                        ", username: " + carbonContext.getUsername());

                // Set JsGraalGraphBuilder thread-local contexts for host function callbacks.
                // This is critical for executeStep and other functions that need the context.
                JsGraalGraphBuilder.setContextForJsThreadLocal(authContext);
                log.info("[RemoteJsEngine] Set contextForJs ThreadLocal with authContext: " +
                        authContext.getContextIdentifier());

                // Get and set the current executing node from the authentication context.
                // This is used by executeStepInAsyncEvent to build the authentication graph.
                Object currentNode = authContext.getProperty("Adaptive.Auth.Current.Graph.Node");
                if (currentNode instanceof AuthGraphNode) {
                    JsGraalGraphBuilder.setDynamicallyBuiltBaseNodeThreadLocal((AuthGraphNode) currentNode);
                    log.info("[RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: " +
                            currentNode.getClass().getSimpleName());
                } else {
                    log.warn("[RemoteJsEngine] PROP_CURRENT_NODE not found or wrong type in authContext. " +
                            "Type: " + (currentNode != null ? currentNode.getClass().getName() : "null"));
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
        // Clear JsGraalGraphBuilder thread-local contexts.
        try {
            JsGraalGraphBuilder.removeContextForJsThreadLocal();
            JsGraalGraphBuilder.removeDynamicallyBuiltBaseNodeThreadLocal();
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

        log.info("[RemoteJsEngine] adaptArgumentsForMethod: paramCount=" + paramTypes.length +
                ", argsCount=" + (args != null ? args.length : 0) + ", isVarArgs=" + isVarArgs);

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

            log.info("[RemoteJsEngine] Adapting arg[" + i + "] from " +
                    (arg != null ? arg.getClass().getSimpleName() : "null") +
                    " to " + paramType.getSimpleName());

            adaptedArgs[i] = adaptSingleArgument(arg, paramType);
        }

        return adaptedArgs;
    }

    /**
     * Handle varargs method argument adaptation.
     */
    private Object[] adaptVarArgsMethod(java.lang.reflect.Method method, Class<?>[] paramTypes, Object[] args) {
        int fixedParamCount = paramTypes.length - 1;
        Class<?> varArgType = paramTypes[fixedParamCount].getComponentType();

        log.info("[RemoteJsEngine] Adapting varargs method: fixedParams=" + fixedParamCount +
                ", varArgType=" + varArgType.getSimpleName());

        Object[] adaptedArgs = new Object[paramTypes.length];

        // Adapt fixed parameters.
        for (int i = 0; i < fixedParamCount && i < args.length; i++) {
            log.info("[RemoteJsEngine] Adapting fixed arg[" + i + "] from " +
                    (args[i] != null ? args[i].getClass().getSimpleName() : "null") +
                    " to " + paramTypes[i].getSimpleName());
            adaptedArgs[i] = adaptSingleArgument(args[i], paramTypes[i]);
        }

        // Collect remaining arguments into varargs array.
        int varArgCount = args.length - fixedParamCount;
        if (varArgCount > 0) {
            Object[] varArgs = (Object[]) java.lang.reflect.Array.newInstance(varArgType, varArgCount);
            for (int i = 0; i < varArgCount; i++) {
                int srcIndex = fixedParamCount + i;
                log.info("[RemoteJsEngine] Adapting vararg[" + i + "] from " +
                        (args[srcIndex] != null ? args[srcIndex].getClass().getSimpleName() : "null") +
                        " to " + varArgType.getSimpleName());
                varArgs[i] = adaptSingleArgument(args[srcIndex], varArgType);
            }
            adaptedArgs[fixedParamCount] = varArgs;
        } else {
            // Empty varargs array.
            adaptedArgs[fixedParamCount] = java.lang.reflect.Array.newInstance(varArgType, 0);
        }

        return adaptedArgs;
    }

    /**
     * Adapt a single argument to the target parameter type.
     */
    private Object adaptSingleArgument(Object arg, Class<?> paramType) {
        if (arg == null) {
            return null;
        }

        // IMPORTANT: Check for context proxy marker from sidecar.
        // When the sidecar sends a DynamicContextProxy as an argument, it serializes it as a Map
        // with special marker fields. We need to reconstruct the actual object from stored authContext.
        if (arg instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) arg;
            if (Boolean.TRUE.equals(map.get("__isContextProxy"))) {
                String proxyType = (String) map.get("__proxyType");
                String basePath = (String) map.get("__basePath");
                log.info("[RemoteJsEngine] Received context proxy marker: type=" + proxyType +
                        ", basePath=" + basePath);

                // Reconstruct the actual object based on proxyType and basePath
                Object reconstructed = reconstructFromContextProxy(proxyType, basePath, paramType);
                if (reconstructed != null) {
                    log.info("[RemoteJsEngine] Reconstructed " + reconstructed.getClass().getSimpleName() +
                            " from context proxy marker");
                    return reconstructed;
                }
            }
        }

        // Handle JsAuthenticationContext - reconstruct from stored authContext.
        if (paramType.getSimpleName().contains("JsAuthenticationContext") ||
                paramType.getSimpleName().contains("JsGraalAuthenticationContext")) {
            log.info("[RemoteJsEngine] Reconstructed JsGraalAuthenticationContext from stored authContext");
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
        if (paramType == Object.class) {
            // Keep as-is for Object type - the method will handle it.
            return arg;
        }

        // Direct assignment for compatible types.
        return arg;
    }

    /**
     * Reconstruct a context object from a proxy marker sent by the sidecar.
     * This handles nested properties like context.currentKnownSubject, context.steps[1], etc.
     *
     * @param proxyType The type of proxy (e.g., "context", "authenticateduser", "step")
     * @param basePath  The path to the property (e.g., "", "currentKnownSubject", "steps.1")
     * @param paramType The expected parameter type from the method signature
     * @return The reconstructed object, or null if reconstruction fails
     */
    private Object reconstructFromContextProxy(String proxyType, String basePath, Class<?> paramType) {
        JsGraalAuthenticationContext jsContext = new JsGraalAuthenticationContext(authContext);

        // If basePath is empty or null, return the full context
        if (basePath == null || basePath.isEmpty()) {
            log.info("[RemoteJsEngine] Reconstructing root context");
            return jsContext;
        }

        // Navigate to the nested property
        log.info("[RemoteJsEngine] Navigating to nested property: " + basePath);

        try {
            // Split the path and navigate
            String[] pathParts = basePath.split("\\.");
            Object current = jsContext;

            for (String part : pathParts) {
                if (current == null) {
                    log.warn("[RemoteJsEngine] Null encountered while navigating path at: " + part);
                    return null;
                }

                // Check if this is an array index (for steps[n])
                if (part.matches("\\d+") && current instanceof org.graalvm.polyglot.proxy.ProxyArray) {
                    int index = Integer.parseInt(part);
                    current = ((org.graalvm.polyglot.proxy.ProxyArray) current).get(index);
                } else if (current instanceof org.graalvm.polyglot.proxy.ProxyObject) {
                    // Navigate using getMember for proxy objects
                    current = ((org.graalvm.polyglot.proxy.ProxyObject) current).getMember(part);
                } else {
                    log.warn("[RemoteJsEngine] Cannot navigate to '" + part + "' on type: " +
                            current.getClass().getName());
                    return null;
                }
            }

            log.info("[RemoteJsEngine] Successfully navigated to: " + basePath +
                    ", result type: " + (current != null ? current.getClass().getSimpleName() : "null"));
            return current;

        } catch (Exception e) {
            log.error("[RemoteJsEngine] Error navigating to property '" + basePath + "': " + e.getMessage(), e);
            return null;
        }
    }

    private void ensureConnected() throws IOException {
        log.info("[RemoteJsEngine] ensureConnected - transport: " + transport.getClass().getSimpleName() +
                ", connected: " + transport.isConnected());
        if (!transport.isConnected()) {
            log.info("[RemoteJsEngine] Connecting transport");
            transport.connect();
            log.info("[RemoteJsEngine] Connected to remote engine successfully");
        }
    }

    private void ensureHandlerRegistered() throws IOException {
        if (!handlerRegistered) {
            log.info("[RemoteJsEngine] Starting callback server if needed");
            callbackServer.start();
            log.info("[RemoteJsEngine] Registering handler with callback server: " +
                    callbackServer.getClass().getSimpleName());
            callbackServer.registerHandler(sessionId, this);
            handlerRegistered = true;
        }
    }
}
