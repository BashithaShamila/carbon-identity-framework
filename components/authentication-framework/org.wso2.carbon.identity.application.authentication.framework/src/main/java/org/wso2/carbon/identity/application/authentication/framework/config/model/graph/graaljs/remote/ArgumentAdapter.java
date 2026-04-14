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

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.StepConfig;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.JsWrapperFactoryProvider;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedIdPData;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adapts arguments from External (protobuf-deserialized) format to Java types
 * expected by host function method signatures.
 * <p>
 * Handles:
 * <ul>
 *   <li>Context proxy marker reconstruction (External sends Map with __isContextProxy__ flag)</li>
 *   <li>Authenticated user direct reconstruction (bypasses proxy navigation for steps::N::subject)</li>
 *   <li>Primitive type coercion (protobuf Double to Integer/Long/Boolean/String)</li>
 *   <li>VarArgs method adaptation with null filtering</li>
 *   <li>Map number type coercion (protobuf deserializes all numbers as Double)</li>
 * </ul>
 */
class ArgumentAdapter {

    private static final Log log = LogFactory.getLog(ArgumentAdapter.class);

    private final AuthenticationContext authContext;

    ArgumentAdapter(AuthenticationContext authContext) {
        this.authContext = authContext;
    }

    /**
     * Adapt arguments to match the method's parameter types.
     * This handles reconstruction of JsAuthenticationContext and type conversions.
     *
     * @param method The method to adapt arguments for.
     * @param args   The raw arguments from the External.
     * @return Adapted arguments matching the method's parameter types.
     */
    Object[] adaptArgumentsForMethod(Method method, Object[] args) {
        Class<?>[] paramTypes = method.getParameterTypes();
        boolean isVarArgs = method.isVarArgs();

        if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
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

            if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
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
    private Object[] adaptVarArgsMethod(Method method, Class<?>[] paramTypes, Object[] args) {
        int fixedParamCount = paramTypes.length - 1;
        Class<?> varArgType = paramTypes[fixedParamCount].getComponentType();

        if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] Adapting varargs method: fixedParams=" + fixedParamCount +
                    ", varArgType=" + varArgType.getSimpleName() + ", totalArgs=" + args.length);
        }

        Object[] adaptedArgs = new Object[paramTypes.length];

        // Adapt fixed parameters.
        for (int i = 0; i < fixedParamCount && i < args.length; i++) {
            if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
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
        List<Object> nonNullVarArgs = new ArrayList<>();
        for (int i = fixedParamCount; i < args.length; i++) {
            if (args[i] != null) {
                Object adapted = adaptSingleArgument(args[i], varArgType);
                nonNullVarArgs.add(adapted);
                if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Adapting vararg[" + (i - fixedParamCount) + "] from " +
                            args[i].getClass().getSimpleName() + " to " + varArgType.getSimpleName());
                }
            } else {
                if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Skipping null vararg at index " + i +
                            " (undefined/null from remote serialization)");
                }
            }
        }

        if (!nonNullVarArgs.isEmpty()) {
            Object[] varArgs = (Object[]) Array.newInstance(varArgType, nonNullVarArgs.size());
            for (int i = 0; i < nonNullVarArgs.size(); i++) {
                varArgs[i] = nonNullVarArgs.get(i);
            }
            adaptedArgs[fixedParamCount] = varArgs;
        } else {
            // Empty varargs array.
            adaptedArgs[fixedParamCount] = Array.newInstance(varArgType, 0);
        }

        if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] Final varargs count: " + nonNullVarArgs.size() +
                    " (from " + (args.length - fixedParamCount) + " raw args)");
        }
        return adaptedArgs;
    }

    /**
     * Adapt a single argument to the target parameter type.
     */
    @SuppressWarnings("unchecked")
    Object adaptSingleArgument(Object arg, Class<?> paramType) {
        if (arg == null) {
            return null;
        }

        // IMPORTANT: Check for context proxy marker from External.
        // When the External sends a DynamicContextProxy as an argument, it serializes it
        // as a Map
        // with special marker fields. We need to reconstruct the actual object from
        // stored authContext.
        if (arg instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) arg;
            if (Boolean.TRUE.equals(map.get(RemoteEngineConstants.IS_CONTEXT_PROXY))) {
                String proxyType = (String) map.get(RemoteEngineConstants.PROXY_TYPE_FIELD);
                String basePath = (String) map.get(RemoteEngineConstants.BASE_PATH_FIELD);
                if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Received context proxy marker: type=" + proxyType +
                            ", basePath=" + basePath);
                }

                // Reconstruct the actual object based on proxyType and basePath
                Object reconstructed = reconstructFromContextProxy(proxyType, basePath, paramType);
                if (reconstructed != null) {
                    if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
                        log.debug("[RemoteJsEngine] Reconstructed " + reconstructed.getClass().getSimpleName() +
                                " from context proxy marker");
                    }
                    return reconstructed;
                }
            }
        }

        // Handle JsAuthenticationContext - reconstruct from stored authContext.
        // JsAuthenticationContext is the abstract base; JsGraalAuthenticationContext extends it.
        // All host function methods declare the parameter as the base type.
        if (JsAuthenticationContext.class.isAssignableFrom(paramType)) {
            if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
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
        if (List.class.isAssignableFrom(paramType)) {
            if (arg instanceof List) {
                return arg;
            } else if (arg instanceof Object[]) {
                return Arrays.asList((Object[]) arg);
            }
        }

        // Handle array type conversion.
        if (paramType.isArray()) {
            if (arg instanceof List) {
                List<?> list = (List<?>) arg;
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
                Map<String, Object> mapArg = (Map<String, Object>) arg;
                if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
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
    Map<String, Object> coerceMapNumberTypes(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>(map);
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            Object val = entry.getValue();
            if (val instanceof Double) {
                double d = (Double) val;
                if (d == Math.floor(d) && !Double.isInfinite(d)) {
                    // Whole number — convert to Integer (or Long if out of int range)
                    if (d >= Integer.MIN_VALUE && d <= Integer.MAX_VALUE) {
                        entry.setValue((int) d);
                        if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
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
     * Reconstruct a context object from a proxy marker sent by the External.
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
    Object reconstructFromContextProxy(String proxyType, String basePath, Class<?> paramType) {

        // If basePath is empty or null, return the full context
        if (basePath == null || basePath.isEmpty()) {
            if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Reconstructing root context");
            }
            return new JsGraalAuthenticationContext(authContext);
        }

        // Direct reconstruction for "authenticateduser" with path "steps::N::subject".
        // This bypasses proxy navigation which can fail due to JsStep.getSubject()
        // returning null
        // when the IdP data lookup doesn't match, causing createJsAuthenticatedUser to
        // throw.
        if ("authenticateduser".equals(proxyType) && isStepSubjectPath(basePath)) {
            String[] parts = basePath.split(RemoteEngineConstants.PATH_SEPARATOR);
            int stepNum = Integer.parseInt(parts[1]);
            if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
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
                        if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
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

        // Generic proxy navigation fallback for other proxy types/paths.
        // Delegates to PropertyPathNavigator which handles ProxyObject, ProxyArray,
        // Map, and reflection getter traversal (including the OSGi classloader fallback).
        if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] Navigating to nested property: " + basePath);
        }

        try {
            String[] pathParts = basePath.split(RemoteEngineConstants.PATH_SEPARATOR);
            Object root = new JsGraalAuthenticationContext(authContext);
            Object result = PropertyPathNavigator.navigatePath(pathParts, 0, root);

            if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Successfully navigated to: " + basePath +
                        ", result type: " + (result != null ? result.getClass().getSimpleName() : "null"));
            }
            return result;

        } catch (Exception e) {
            log.error("[RemoteJsEngine] Error navigating to property '" + basePath + "': " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Check if basePath matches the pattern "steps::{digit}::subject".
     * Replaces basePath.matches("steps::\\d+::subject") to avoid regex compilation per call.
     */
    private static boolean isStepSubjectPath(String basePath) {
        String[] parts = basePath.split(RemoteEngineConstants.PATH_SEPARATOR);
        return parts.length == 3 &&
                "steps".equals(parts[0]) &&
                PropertyPathNavigator.isNumeric(parts[1]) &&
                "subject".equals(parts[2]);
    }
}
