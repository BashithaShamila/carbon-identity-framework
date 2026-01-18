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
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.SerializedValue;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Remote JavaScript engine that communicates with a GraalJS sidecar via UDS.
 * Each instance represents a session with the sidecar.
 * <p>
 * Host function calls from the sidecar are routed back to IS via
 * HostCallbackServer.
 */
public class RemoteJsEngine implements JsEngine, HostCallbackServer.HostFunctionHandler {

    private static final Log log = LogFactory.getLog(RemoteJsEngine.class);

    private final String socketPath;
    private final String sessionId;
    private final AuthenticationContext authContext;
    private UdsClient client;
    private Map<String, Object> bindings = new HashMap<>();
    private Map<String, Object> hostFunctions = new HashMap<>();
    private boolean closed = false;
    private boolean handlerRegistered = false;

    /**
     * Create a new remote JavaScript engine.
     *
     * @param socketPath  Path to the sidecar UDS socket.
     * @param authContext The authentication context for this session.
     */
    public RemoteJsEngine(String socketPath, AuthenticationContext authContext) {
        this.socketPath = socketPath;
        this.authContext = authContext;
        this.sessionId = UUID.randomUUID().toString();
        log.info("[RemoteJsEngine] Created with session: " + sessionId + ", socketPath: " + socketPath +
                ", SP: " + (authContext != null ? authContext.getServiceProviderName() : "null"));
    }

    @Override
    public EvaluationResult evaluate(String script, String sourceIdentifier, Map<String, Object> initialBindings) {
        long startTime = System.currentTimeMillis();
        log.info("[RemoteJsEngine] evaluate() called, session: " + sessionId + ", sourceId: " + sourceIdentifier);

        try {
            log.info("[RemoteJsEngine] Ensuring connection to sidecar at: " + socketPath);
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

            // Set callback socket path for host function callbacks
            String callbackSocketPath = HostCallbackServer.getCallbackSocketPath();
            log.info("[RemoteJsEngine] Callback socket path: " + callbackSocketPath);
            if (callbackSocketPath != null) {
                requestBuilder.setCallbackSocketPath(callbackSocketPath);
            }

            // Serialize bindings (excluding host functions which are handled differently)
            log.info("[RemoteJsEngine] Serializing " + bindings.size() + " bindings, " +
                    hostFunctions.size() + " host functions");
            for (Map.Entry<String, Object> entry : bindings.entrySet()) {
                // Skip host function objects - they can't be serialized directly
                if (!hostFunctions.containsKey(entry.getKey())) {
                    requestBuilder.putBindings(entry.getKey(), ProtobufSerializer.toProto(entry.getValue()));
                }
            }

            // Add host function definitions so sidecar knows to call back
            for (String funcName : hostFunctions.keySet()) {
                log.info("[RemoteJsEngine] Registering host function: " + funcName);
                requestBuilder.addHostFunctions(
                        org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.HostFunctionDefinition
                                .newBuilder()
                                .setName(funcName)
                                .build());
            }

            // Send request and get response (blocking)
            log.info("[RemoteJsEngine] Sending evaluate request to sidecar...");
            EvaluateResponse response = client.sendEvaluate(requestBuilder.build());
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
            log.info("[RemoteJsEngine] executeCallback - ensuring connection to: " + socketPath);
            ensureConnected();
            log.info("[RemoteJsEngine] executeCallback - connection OK, registering handler...");
            ensureHandlerRegistered();
            log.info("[RemoteJsEngine] executeCallback - handler registered: " + handlerRegistered);

            // Apply callback bindings
            if (callbackBindings != null && !callbackBindings.isEmpty()) {
                log.info("[RemoteJsEngine] Applying " + callbackBindings.size() + " callback bindings: " +
                        callbackBindings.keySet());
                for (Map.Entry<String, Object> entry : callbackBindings.entrySet()) {
                    log.info("[RemoteJsEngine] Callback binding: " + entry.getKey() + " = " +
                            (entry.getValue() != null ?
                                    entry.getValue().getClass().getSimpleName() + ": " + entry.getValue() :
                                    "null"));
                    bindings.put(entry.getKey(), entry.getValue());
                }
            } else {
                log.info("[RemoteJsEngine] No callback bindings provided (null or empty). " +
                        "callbackBindings=" + callbackBindings);
            }

            // Build the request
            ExecuteCallbackRequest.Builder requestBuilder = ExecuteCallbackRequest.newBuilder()
                    .setSessionId(sessionId)
                    .setFunctionSource(functionSource);

            // Set callback socket path
            String callbackSocketPath = HostCallbackServer.getCallbackSocketPath();
            log.info("[RemoteJsEngine] executeCallback - callback socket: " + callbackSocketPath);
            if (callbackSocketPath != null) {
                requestBuilder.setCallbackSocketPath(callbackSocketPath);
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
            log.info("[RemoteJsEngine] Total bindings to serialize: " + bindings.size() +
                    ", keys: " + bindings.keySet());
            log.info("[RemoteJsEngine] Host functions (excluded from bindings): " + hostFunctions.keySet());
            int bindingsAdded = 0;
            for (Map.Entry<String, Object> entry : bindings.entrySet()) {
                if (!hostFunctions.containsKey(entry.getKey())) {
                    log.info("[RemoteJsEngine] Serializing binding: " + entry.getKey() + " = " +
                            (entry.getValue() != null ?
                                    entry.getValue().getClass().getSimpleName() + ": " + entry.getValue() :
                                    "null"));
                    requestBuilder.putBindings(entry.getKey(), ProtobufSerializer.toProto(entry.getValue()));
                    bindingsAdded++;
                }
            }
            log.info("[RemoteJsEngine] Bindings serialized: " + bindingsAdded);

            // Add host function definitions so sidecar knows to create stubs for callbacks
            log.info("[RemoteJsEngine] Adding " + hostFunctions.size() + " host function definitions");
            for (String funcName : hostFunctions.keySet()) {
                log.info("[RemoteJsEngine] Adding host function: " + funcName);
                requestBuilder.addHostFunctions(
                        org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.HostFunctionDefinition
                                .newBuilder()
                                .setName(funcName)
                                .build());
            }

            // Send request and get response (blocking)
            log.info("[RemoteJsEngine] Sending executeCallback request to sidecar...");
            ExecuteCallbackResponse response = client.sendExecuteCallback(requestBuilder.build());
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
                HostCallbackServer server = HostCallbackServer.getInstance();
                if (server != null) {
                    server.unregisterHandler(sessionId);
                }
            }

            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    log.debug("Error closing UDS client", e);
                }
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
                                (cause != null ? cause.getClass().getName() + ": " + cause.getMessage() : e.getMessage()));
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
     * Set up thread-local context required for host function invocation.
     * This ensures tenant context, carbon context, and JS graph builder contexts are properly set.
     */
    private void setupThreadContext() {
        if (authContext != null) {
            log.info("[RemoteJsEngine] Setting up thread context for tenant: " + authContext.getTenantDomain() +
                    ", contextId: " + authContext.getContextIdentifier());
            try {
                // Set Carbon context for the current thread.
                org.wso2.carbon.context.PrivilegedCarbonContext carbonContext =
                        org.wso2.carbon.context.PrivilegedCarbonContext.getThreadLocalCarbonContext();
                carbonContext.setTenantDomain(authContext.getTenantDomain());
                carbonContext.setTenantId(
                        org.wso2.carbon.identity.core.util.IdentityTenantUtil.getTenantId(authContext.getTenantDomain()));
                
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

        // Handle JsAuthenticationContext - reconstruct from stored authContext.
        if (paramType.getSimpleName().contains("JsAuthenticationContext") ||
                paramType.getSimpleName().contains("JsGraalAuthenticationContext")) {
            log.info("[RemoteJsEngine] Reconstructed JsGraalAuthenticationContext from stored authContext");
            return new org.wso2.carbon.identity.application.authentication.framework
                    .config.model.graph.js.graaljs.JsGraalAuthenticationContext(authContext);
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

    private void ensureConnected() throws IOException {
        log.info("[RemoteJsEngine] ensureConnected - client: " +
                (client != null ? "exists" : "null") +
                ", connected: " + (client != null && client.isConnected()));
        if (client == null || !client.isConnected()) {
            log.info("[RemoteJsEngine] Creating new UdsClient to: " + socketPath);
            client = new UdsClient(socketPath);
            client.connect();
            log.info("[RemoteJsEngine] Connected to sidecar successfully");
        }
    }

    private void ensureHandlerRegistered() {
        if (!handlerRegistered) {
            HostCallbackServer server = HostCallbackServer.getInstance();
            log.info("[RemoteJsEngine] Registering handler with HostCallbackServer, server: " +
                    (server != null ? "exists" : "null"));
            if (server != null) {
                server.registerHandler(sessionId, this);
                handlerRegistered = true;
            }
        }
    }
}
