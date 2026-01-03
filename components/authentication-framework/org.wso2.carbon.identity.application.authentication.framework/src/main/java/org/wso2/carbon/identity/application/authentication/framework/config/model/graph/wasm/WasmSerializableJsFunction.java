/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com).
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

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.wasm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.GenericSerializableJsFunction;

/**
 * Serializable JavaScript function representation for WASM-based execution.
 * This class stores JavaScript function source code that can be serialized and
 * later executed via the WASM runtime.
 */
public class WasmSerializableJsFunction implements GenericSerializableJsFunction<WasmRuntime> {

    private static final long serialVersionUID = 1L;
    private static final Log LOG = LogFactory.getLog(WasmSerializableJsFunction.class);

    private String source;
    private boolean isFunction;
    private String functionName;
    private String[] parameterNames;
    private String scriptPrelude; // Top-level variable declarations from original script

    /**
     * Default constructor for serialization.
     */
    public WasmSerializableJsFunction() {
    }

    /**
     * Constructs a WasmSerializableJsFunction with the given source.
     *
     * @param source The JavaScript source code.
     */
    public WasmSerializableJsFunction(String source) {
        this(source, true);
    }

    /**
     * Constructs a WasmSerializableJsFunction with the given source and function
     * flag.
     *
     * @param source     The JavaScript source code.
     * @param isFunction Whether this represents a function.
     */
    public WasmSerializableJsFunction(String source, boolean isFunction) {
        this.source = source;
        this.isFunction = isFunction;
        if (isFunction) {
            parseFunctionDetails();
        }
    }

    /**
     * Parses the function source to extract function name and parameters.
     */
    private void parseFunctionDetails() {
        if (source == null || source.isEmpty()) {
            return;
        }

        // Extract function name if present
        String trimmed = source.trim();
        if (trimmed.startsWith("function")) {
            int parenStart = trimmed.indexOf('(');
            if (parenStart > 8) {
                this.functionName = trimmed.substring(8, parenStart).trim();
            }

            int parenEnd = trimmed.indexOf(')');
            if (parenStart >= 0 && parenEnd > parenStart) {
                String params = trimmed.substring(parenStart + 1, parenEnd).trim();
                if (!params.isEmpty()) {
                    this.parameterNames = params.split("\\s*,\\s*");
                }
            }
        } else if (trimmed.contains("=>")) {
            // Arrow function
            int arrowIndex = trimmed.indexOf("=>");
            String beforeArrow = trimmed.substring(0, arrowIndex).trim();
            if (beforeArrow.startsWith("(") && beforeArrow.endsWith(")")) {
                String params = beforeArrow.substring(1, beforeArrow.length() - 1).trim();
                if (!params.isEmpty()) {
                    this.parameterNames = params.split("\\s*,\\s*");
                }
            } else if (!beforeArrow.isEmpty()) {
                // Single parameter without parentheses
                this.parameterNames = new String[] { beforeArrow };
            }
        }
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean isFunction() {
        return isFunction;
    }

    @Override
    public void setFunction(boolean isFunction) {
        this.isFunction = isFunction;
    }

    /**
     * Gets the function name if available.
     *
     * @return The function name, or null if not a named function.
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * Gets the parameter names of the function.
     *
     * @return The parameter names array, or null if no parameters.
     */
    public String[] getParameterNames() {
        return parameterNames;
    }

    /**
     * Sets the script prelude (top-level variable declarations from original
     * script).
     *
     * @param prelude The prelude to set.
     */
    public void setScriptPrelude(String prelude) {
        this.scriptPrelude = prelude;
    }

    /**
     * Applies this function using the provided WASM runtime.
     *
     * @param runtime The WASM runtime to use for execution.
     * @param params  The parameters to pass to the function.
     * @return The result of the function execution.
     */
    @Override
    public Object apply(WasmRuntime runtime, Object... params) {
        if (runtime == null) {
            throw new IllegalArgumentException("WasmRuntime cannot be null");
        }

        // Check for [native code] - QuickJS WASM limitation where function source is
        // not preserved
        if (source != null && source.contains("[native code]")) {
            LOG.warn("Cannot execute callback: QuickJS WASM does not preserve function source code. " +
                    "The callback will be skipped and authentication will proceed with default flow. " +
                    "Source: " + source);
            // Return null to indicate no callback action - let framework use default
            // behavior
            return null;
        }

        if (!isFunction) {
            // Not a function, just evaluate the source
            return runtime.evalJs(source);
        }

        // Build the invocation script with all necessary built-in functions
        // The callback may reference these functions from its original scope
        StringBuilder script = new StringBuilder();

        // Define built-in functions that callbacks typically use
        script.append("var executeStep = function(stepId, options) {\n");
        script.append("  return __host_call('executeStep', [stepId, options]);\n");
        script.append("};\n");

        script.append("var hasAnyOfTheRolesV2 = function(context, roles) {\n");
        script.append("  return __host_call('hasAnyOfTheRolesV2', [context, roles]);\n");
        script.append("};\n");

        script.append("var hasRole = function(subject, roleName) {\n");
        script.append("  return __host_call('hasRole', [subject, roleName]);\n");
        script.append("};\n");

        script.append("var Log = {\n");
        script.append("  info: function(msg) { __host_call('logInfo', [msg]); },\n");
        script.append("  warn: function(msg) { __host_call('logWarn', [msg]); },\n");
        script.append("  error: function(msg) { __host_call('logError', [msg]); },\n");
        script.append("  debug: function(msg) { __host_call('logDebug', [msg]); }\n");
        script.append("};\n\n");

        // Define __fn for nested callback serialization
        // This is needed when callbacks contain nested onSuccess/onFail functions
        script.append("var __sourceRegistry = {};\n");
        script.append("var __sourceIdCounter = 0;\n");
        script.append("var __fn = function(source) {\n");
        script.append("  var id = '__src_' + (++__sourceIdCounter);\n");
        script.append("  __sourceRegistry[id] = source;\n");
        script.append("  return {\n");
        script.append("    __type__: 'WasmSerializableJsFunction',\n");
        script.append("    source: source,\n");
        script.append("    isFunction: true\n");
        script.append("  };\n");
        script.append("};\n\n");

        // Include script prelude (top-level variable declarations from original script)
        if (scriptPrelude != null && !scriptPrelude.isEmpty()) {
            script.append("// Script prelude - top-level variables\n");
            script.append(scriptPrelude);
            script.append("\n");
        }

        // Now execute the function
        script.append("(").append(source).append(")");

        if (params != null && params.length > 0) {
            script.append("(");
            for (int i = 0; i < params.length; i++) {
                if (i > 0) {
                    script.append(", ");
                }
                script.append(serializeParam(params[i]));
            }
            script.append(")");
        } else {
            script.append("()");
        }

        return runtime.evalJs(script.toString());
    }

    /**
     * Serializes a parameter for JavaScript consumption.
     * Uses depth-limited serialization to avoid circular reference issues.
     */
    private String serializeParam(Object param) {
        if (param == null) {
            return "null";
        }

        // Log parameter type for debugging
        LOG.info("serializeParam: Serializing parameter of type: " + param.getClass().getName());

        if (param instanceof String) {
            return "\"" + escapeString((String) param) + "\"";
        }
        if (param instanceof Number || param instanceof Boolean) {
            return param.toString();
        }

        // Handle ANY JsAuthenticationContext (including JsNashornAuthenticationContext,
        // JsWasmAuthenticationContext, etc.)
        if (param instanceof org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext) {
            try {
                org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext jsContext = (org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext) param;
                org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext ctx = jsContext
                        .getContext();

                java.util.Map<String, Object> map = new java.util.HashMap<>();
                if (ctx != null) {
                    map.put("currentStep", ctx.getCurrentStep());
                    map.put("tenantDomain", ctx.getTenantDomain());
                    map.put("serviceProviderName", ctx.getServiceProviderName());
                    map.put("requestType", ctx.getRequestType());
                    map.put("retrying", ctx.isRetrying());

                    // Get the current known subject
                    org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser subject = ctx
                            .getSubject();
                    if (subject != null) {
                        java.util.Map<String, Object> userMap = new java.util.HashMap<>();
                        userMap.put("username", subject.getUserName());
                        userMap.put("userStoreDomain", subject.getUserStoreDomain());
                        userMap.put("tenantDomain", subject.getTenantDomain());
                        userMap.put("authenticatedSubjectIdentifier", subject.getAuthenticatedSubjectIdentifier());
                        map.put("subject", userMap);
                        map.put("currentKnownSubject", userMap);
                    }
                }
                LOG.info("serializeParam: Serialized JsAuthenticationContext with keys: " + map.keySet());
                return serializeMapSafe(map, 5);
            } catch (Exception e) {
                LOG.warn("Error serializing JsAuthenticationContext, using empty object", e);
                return "{}";
            }
        }

        // Handle JsWasmAuthenticatedUser
        if (param instanceof org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmAuthenticatedUser) {
            try {
                java.util.Map<String, Object> map = ((org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmAuthenticatedUser) param)
                        .toMap();
                return serializeMapSafe(map, 5);
            } catch (Exception e) {
                LOG.warn("Error serializing JsWasmAuthenticatedUser, using empty object", e);
                return "{}";
            }
        }

        if (param instanceof java.util.Map) {
            // Safely serialize maps with depth limit to avoid circular references
            try {
                return serializeMapSafe((java.util.Map<?, ?>) param, 5);
            } catch (Exception e) {
                LOG.warn("Error serializing map parameter, using empty object", e);
                return "{}";
            }
        }
        // For other complex objects, use a simple representation to avoid circular refs
        try {
            // Try JSON but with timeout protection
            return new com.google.gson.Gson().toJson(param);
        } catch (StackOverflowError e) {
            LOG.warn("Circular reference detected in parameter serialization, using empty object");
            return "{}";
        } catch (Exception e) {
            LOG.warn("Error serializing parameter: " + e.getMessage());
            return "{}";
        }
    }

    /**
     * Safely serializes a map with a maximum depth to prevent circular references.
     */
    private String serializeMapSafe(java.util.Map<?, ?> map, int maxDepth) {
        if (maxDepth <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (java.util.Map.Entry<?, ?> entry : map.entrySet()) {
            if (!first) {
                sb.append(",");
            }
            first = false;
            sb.append("\"").append(escapeString(String.valueOf(entry.getKey()))).append("\":");
            Object val = entry.getValue();
            if (val == null) {
                sb.append("null");
            } else if (val instanceof String) {
                sb.append("\"").append(escapeString((String) val)).append("\"");
            } else if (val instanceof Number || val instanceof Boolean) {
                sb.append(val.toString());
            } else if (val instanceof java.util.Map) {
                sb.append(serializeMapSafe((java.util.Map<?, ?>) val, maxDepth - 1));
            } else {
                sb.append("\"").append(escapeString(val.toString())).append("\"");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Escapes special characters in a string for JavaScript.
     */
    private String escapeString(String str) {
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    /**
     * Converts an object to a WasmSerializableJsFunction if possible.
     *
     * @param value The value to convert.
     * @return The WasmSerializableJsFunction, or null if conversion is not
     *         possible.
     */
    public static WasmSerializableJsFunction toSerializableForm(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof WasmSerializableJsFunction) {
            return (WasmSerializableJsFunction) value;
        }

        if (value instanceof String) {
            return new WasmSerializableJsFunction((String) value, false);
        }

        // Handle Map format from JavaScript serialization
        // {__type__: 'WasmSerializableJsFunction', source: '...', isFunction: true}
        if (value instanceof java.util.Map) {
            java.util.Map<?, ?> map = (java.util.Map<?, ?>) value;
            String type = (String) map.get("__type__");
            if ("WasmSerializableJsFunction".equals(type)) {
                String source = (String) map.get("source");
                Object isFunctionObj = map.get("isFunction");
                boolean isFunction = isFunctionObj instanceof Boolean ? (Boolean) isFunctionObj : true;
                if (source != null && !source.isEmpty()) {
                    return new WasmSerializableJsFunction(source, isFunction);
                }
            }
        }

        // For other types, try to extract source if available
        try {
            if (value.getClass().getName().contains("Function")) {
                // Attempt to get the source via toString or similar
                String source = value.toString();
                if (source != null && (source.contains("function") || source.contains("=>"))) {
                    return new WasmSerializableJsFunction(source, true);
                }
            }
        } catch (Exception e) {
            LOG.debug("Could not convert value to serializable function: " + value, e);
        }

        return null;
    }

    @Override
    public String toString() {
        return "WasmSerializableJsFunction{" +
                "functionName='" + functionName + '\'' +
                ", isFunction=" + isFunction +
                ", source='" + (source != null && source.length() > 50 ? source.substring(0, 50) + "..." : source)
                + '\'' +
                '}';
    }
}
