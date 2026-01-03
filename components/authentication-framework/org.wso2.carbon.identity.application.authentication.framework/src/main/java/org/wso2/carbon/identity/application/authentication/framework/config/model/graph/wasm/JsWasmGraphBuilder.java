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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.AsyncProcess;
import org.wso2.carbon.identity.application.authentication.framework.AuthenticationDecisionEvaluator;
import org.wso2.carbon.identity.application.authentication.framework.JsFunctionRegistry;
import org.wso2.carbon.identity.application.authentication.framework.config.model.StepConfig;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.AuthGraphNode;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.BaseSerializableJsFunction;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.DynamicDecisionNode;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.FailNode;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.GenericSerializableJsFunction;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.JSExecutionMonitorData;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.JSExecutionSupervisor;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.JsGraphBuilder;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.LongWaitNode;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.ShowPromptNode;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.StepConfigGraphNode;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.internal.FrameworkServiceDataHolder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.PROP_EXECUTION_SUPERVISOR_RESULT;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.AUTHENTICATION_OPTIONS;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.AUTHENTICATOR_PARAMS;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_FUNC_ON_LOGIN_REQUEST;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.PROP_CURRENT_NODE;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.STEP_OPTIONS;

/**
 * WASM-based Graph Builder for Authentication Scripts.
 * This builder translates authentication graph configuration to runtime model
 * using WASM/QuickJS execution.
 * Mirrors the functionality of JsGraalGraphBuilder while using the WASM
 * runtime.
 */
public class JsWasmGraphBuilder extends JsGraphBuilder {

    private static final Log LOG = LogFactory.getLog(JsWasmGraphBuilder.class);
    private static final Gson GSON = new GsonBuilder().create();

    protected WasmRuntime runtime;
    private String currentScriptPrelude; // Top-level variable declarations to pass to callbacks

    /**
     * Constructs the builder with the given authentication context.
     *
     * @param authenticationContext Current authentication context.
     * @param stepConfigMap         The Step map from the service provider
     *                              configuration.
     * @param runtime               WASM Runtime.
     */
    public JsWasmGraphBuilder(AuthenticationContext authenticationContext, Map<Integer, StepConfig> stepConfigMap,
            WasmRuntime runtime) {
        this.authenticationContext = authenticationContext;
        this.runtime = runtime;
        stepNamedMap = stepConfigMap.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Constructs the builder with the given authentication context.
     *
     * @param authenticationContext Current authentication context.
     * @param stepConfigMap         The Step map from the service provider
     *                              configuration.
     * @param runtime               WASM Runtime.
     * @param currentNode           Current authentication graph node.
     */
    public JsWasmGraphBuilder(AuthenticationContext authenticationContext, Map<Integer, StepConfig> stepConfigMap,
            WasmRuntime runtime, AuthGraphNode currentNode) {
        this.authenticationContext = authenticationContext;
        this.runtime = runtime;
        this.currentNode = currentNode;
        stepNamedMap = stepConfigMap.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Creates the graph with the given Script and step map.
     *
     * @param script The Dynamic authentication script.
     */
    @Override
    public JsWasmGraphBuilder createWith(String script) {
        try {
            currentBuilder.set(this);

            // Register built-in functions
            registerBuiltInFunctions();

            // Register custom functions from the function registry
            registerCustomFunctions();

            // Prepare the context object for JavaScript
            JsWasmAuthenticationContext jsContext = new JsWasmAuthenticationContext(authenticationContext);
            String contextJson = GSON.toJson(jsContext.toMap());

            // Extract top-level variable declarations to pass to callbacks
            this.currentScriptPrelude = extractScriptPrelude(script);
            LOG.info("createWith: Extracted script prelude: " +
                    (currentScriptPrelude.length() > 100 ? currentScriptPrelude.substring(0, 100) + "..."
                            : currentScriptPrelude));

            // Build the full script with context injection and function wrapper
            String fullScript = buildExecutionScript(script, contextJson);

            String identifier = UUID.randomUUID().toString();
            Optional<JSExecutionMonitorData> optionalScriptExecutionData;

            try {
                startScriptExecutionMonitor(identifier, authenticationContext);

                // Execute the script via WASM runtime
                Object result = runtime.evalJs(fullScript);

                if (result instanceof Map) {
                    // Process the result to build the authentication graph
                    processScriptResult((Map<String, Object>) result);
                }

            } finally {
                optionalScriptExecutionData = Optional.ofNullable(endScriptExecutionMonitor(identifier));
            }

            optionalScriptExecutionData
                    .ifPresent(scriptExecutionData -> storeAuthScriptExecutionMonitorData(authenticationContext,
                            scriptExecutionData));

            JsWasmGraphBuilderFactory.persistCurrentContext(authenticationContext, runtime);

        } catch (Exception e) {
            result.setBuildSuccessful(false);
            result.setErrorReason("Error in executing the WASM JavaScript. " + JS_FUNC_ON_LOGIN_REQUEST +
                    " reason, " + e.getMessage());
            LOG.error("Error in executing the WASM JavaScript for service provider : " +
                    authenticationContext.getServiceProviderName() + ", Javascript Fragment : \\n" +
                    (e.getMessage() != null ? e.getMessage() : "Unknown error"), e);
        } finally {
            clearCurrentBuilder();
        }
        return this;
    }

    /**
     * Builds the execution script with context injection.
     * Uses a callback registry pattern to preserve function references across the
     * WASM boundary.
     */
    private String buildExecutionScript(String userScript, String contextJson) {
        StringBuilder sb = new StringBuilder();

        // Define the context object
        sb.append("var __context = ").append(contextJson).append(";\n\n");

        // Source registry - stores function sources before they become function objects
        // This works around QuickJS WASM limitation where toString() returns [native
        // code]
        sb.append("var __sourceRegistry = {};\n");
        sb.append("var __sourceIdCounter = 0;\n\n");

        // Function to create a "tagged" function with its source preserved
        // The source is captured as a string BEFORE the function is compiled
        sb.append("var __fn = function(source) {\n");
        sb.append("  var id = '__src_' + (++__sourceIdCounter);\n");
        sb.append("  __sourceRegistry[id] = source;\n");
        sb.append("  // Create the function from source and tag it with the registry ID\n");
        sb.append("  var fn = eval('(' + source + ')');\n");
        sb.append("  fn.__sourceId = id;\n");
        sb.append("  return fn;\n");
        sb.append("};\n\n");

        // Helper function to serialize options, using source registry for functions
        sb.append("var __serializeOptions = function(opts) {\n");
        sb.append("  if (!opts) return opts;\n");
        sb.append("  var serialized = {};\n");
        sb.append("  for (var key in opts) {\n");
        sb.append("    if (opts.hasOwnProperty(key)) {\n");
        sb.append("      var val = opts[key];\n");
        sb.append("      if (typeof val === 'function') {\n");
        sb.append("        // Check if function has a source ID (created via __fn)\n");
        sb.append("        var source;\n");
        sb.append("        if (val.__sourceId && __sourceRegistry[val.__sourceId]) {\n");
        sb.append("          source = __sourceRegistry[val.__sourceId];\n");
        sb.append("        } else {\n");
        sb.append("          // Fallback to toString (will show [native code] warning)\n");
        sb.append("          source = val.toString();\n");
        sb.append("        }\n");
        sb.append("        serialized[key] = {\n");
        sb.append("          __type__: 'WasmSerializableJsFunction',\n");
        sb.append("          source: source,\n");
        sb.append("          isFunction: true\n");
        sb.append("        };\n");
        sb.append("      } else if (typeof val === 'object' && val !== null) {\n");
        sb.append("        serialized[key] = __serializeOptions(val);\n");
        sb.append("      } else {\n");
        sb.append("        serialized[key] = val;\n");
        sb.append("      }\n");
        sb.append("    }\n");
        sb.append("  }\n");
        sb.append("  return serialized;\n");
        sb.append("};\n\n");

        // Define built-in functions
        sb.append("var executeStep = function(stepId, options) {\n");
        sb.append("  return __host_call('executeStep', [stepId, __serializeOptions(options)]);\n");
        sb.append("};\n");

        sb.append("var sendError = function(url, params) {\n");
        sb.append("  return __host_call('sendError', [url, params]);\n");
        sb.append("};\n");

        sb.append("var fail = function(params) {\n");
        sb.append("  return __host_call('fail', [params]);\n");
        sb.append("};\n");

        sb.append("var prompt = function(templateId, data, handlers) {\n");
        sb.append("  return __host_call('prompt', [templateId, data, __serializeOptions(handlers)]);\n");
        sb.append("};\n");

        sb.append("var Log = {\n");
        sb.append("  info: function(msg) { __host_call('log', [msg]); },\n");
        sb.append("  error: function(msg) { __host_call('logError', [msg]); },\n");
        sb.append("  debug: function(msg) { __host_call('logDebug', [msg]); }\n");
        sb.append("};\n\n");

        // Add hasRole function for role-based MFA
        sb.append("var hasRole = function(subject, roleName) {\n");
        sb.append("  return __host_call('hasRole', [subject, roleName]);\n");
        sb.append("};\n\n");

        // Preprocess user script to wrap callbacks with __fn()
        String processedScript = preprocessCallbacks(userScript);

        // Add the processed user script
        sb.append(processedScript).append("\n");

        // Call the onLoginRequest function if it exists
        sb.append("if (typeof onLoginRequest === 'function') {\n");
        sb.append("  onLoginRequest(__context);\n");
        sb.append("}\n");

        // Return any accumulated state
        sb.append("JSON.stringify({success: true});\n");

        return sb.toString();
    }

    /**
     * Preprocesses callback functions in the user script to use the __fn() wrapper.
     * This transforms patterns like:
     * onSuccess: function(context) { ... }
     * Into:
     * onSuccess: __fn("function(context) { ... }")
     * 
     * This preserves the function source as a string literal before QuickJS
     * compiles it.
     */
    private String preprocessCallbacks(String script) {
        if (script == null || script.isEmpty()) {
            return script;
        }

        // Log the actual user script for debugging
        LOG.info("preprocessCallbacks: Input script (first 500 chars): " +
                script.substring(0, Math.min(500, script.length())));

        // Pattern to match callback property with inline function
        // Matches various formats:
        // - onSuccess: function(context) { ... }
        // - onSuccess : function (context) { ... }
        // Uses balanced brace matching for proper extraction
        StringBuilder result = new StringBuilder();
        String remaining = script;

        // More flexible pattern to find callbacks
        // Matches: onSuccess/onFail/onComplete followed by : and function keyword
        java.util.regex.Pattern callbackStart = java.util.regex.Pattern.compile(
                "(on(?:Success|Fail|Complete))\\s*:\\s*function\\s*\\(");

        java.util.regex.Matcher matcher = callbackStart.matcher(remaining);
        int lastEnd = 0;
        int matchCount = 0;
        int transformedCount = 0;

        while (matcher.find()) {
            matchCount++;
            LOG.info("preprocessCallbacks: Found callback match #" + matchCount +
                    " at position " + matcher.start() + ": '" + matcher.group() + "'");

            // Add everything before this match
            result.append(remaining, lastEnd, matcher.start());

            // Find the complete function body with balanced braces
            String callbackName = matcher.group(1);
            int funcStart = matcher.start() + callbackName.length();
            // Skip the ": " part
            while (funcStart < remaining.length() &&
                    (remaining.charAt(funcStart) == ':' || remaining.charAt(funcStart) == ' ')) {
                funcStart++;
            }

            LOG.info("preprocessCallbacks: funcStart=" + funcStart +
                    ", char at funcStart='" + (funcStart < remaining.length() ? remaining.charAt(funcStart) : "EOF")
                    + "'");

            int bodyEnd = findFunctionEnd(remaining, funcStart);
            LOG.info("preprocessCallbacks: findFunctionEnd returned bodyEnd=" + bodyEnd);

            if (bodyEnd > funcStart) {
                String functionSource = remaining.substring(funcStart, bodyEnd);
                LOG.info("preprocessCallbacks: Found function source (length=" + functionSource.length() + "): " +
                        functionSource.substring(0, Math.min(100, functionSource.length())) + "...");

                // IMPORTANT: Recursively preprocess nested callbacks inside this function
                // This ensures that inner onSuccess/onFail callbacks are also transformed
                String preprocessedSource = preprocessNestedCallbacks(functionSource);
                LOG.info("preprocessCallbacks: After nested preprocessing, length=" + preprocessedSource.length());

                // Escape the preprocessed source for use as a string literal
                String escapedSource = escapeForJsString(preprocessedSource);
                result.append(callbackName).append(": __fn(\"").append(escapedSource).append("\")");
                lastEnd = bodyEnd;
                matcher.region(lastEnd, remaining.length());
                transformedCount++;
            } else {
                // Couldn't find function end, keep original
                LOG.warn("preprocessCallbacks: Could not find function end! bodyEnd=" + bodyEnd + ", funcStart="
                        + funcStart);
                result.append(matcher.group());
                lastEnd = matcher.end();
            }
        }

        // Add any remaining content
        result.append(remaining.substring(lastEnd));

        if (matchCount == 0) {
            LOG.warn("preprocessCallbacks: No callback patterns found in script! " +
                    "Script may use different format (arrow functions, different names, etc.)");
        }

        LOG.info("Preprocessed script for callbacks. Original length: " + script.length() +
                ", Processed length: " + result.length() +
                ", Matches found: " + matchCount + ", Successfully transformed: " + transformedCount);

        return result.toString();
    }

    /**
     * Recursively preprocesses nested callbacks inside a function body.
     * This transforms inner onSuccess/onFail/onComplete functions to use __fn()
     * wrapper.
     */
    private String preprocessNestedCallbacks(String functionSource) {
        if (functionSource == null || functionSource.isEmpty()) {
            return functionSource;
        }

        StringBuilder result = new StringBuilder();

        // Pattern to match nested callback property with inline function
        java.util.regex.Pattern callbackStart = java.util.regex.Pattern.compile(
                "(on(?:Success|Fail|Complete))\\s*:\\s*function\\s*\\(");

        java.util.regex.Matcher matcher = callbackStart.matcher(functionSource);
        int lastEnd = 0;

        while (matcher.find()) {
            // Add everything before this match
            result.append(functionSource, lastEnd, matcher.start());

            // Find the complete function body with balanced braces
            String callbackName = matcher.group(1);
            int funcStart = matcher.start() + callbackName.length();
            // Skip the ": " part to get to "function"
            while (funcStart < functionSource.length() &&
                    (functionSource.charAt(funcStart) == ':' || functionSource.charAt(funcStart) == ' ')) {
                funcStart++;
            }

            int bodyEnd = findFunctionEnd(functionSource, funcStart);

            if (bodyEnd > funcStart) {
                String nestedFunctionSource = functionSource.substring(funcStart, bodyEnd);

                // RECURSIVELY preprocess any deeper nested callbacks
                String preprocessedNested = preprocessNestedCallbacks(nestedFunctionSource);

                // Escape for use as a string literal
                String escapedSource = escapeForJsString(preprocessedNested);
                result.append(callbackName).append(": __fn(\"").append(escapedSource).append("\")");
                lastEnd = bodyEnd;
                matcher.region(lastEnd, functionSource.length());
            } else {
                // Couldn't find function end, keep original
                result.append(matcher.group());
                lastEnd = matcher.end();
            }
        }

        // Add any remaining content
        result.append(functionSource.substring(lastEnd));

        return result.toString();
    }

    /**
     * Finds the end of a function body by counting balanced braces.
     * Handles string literals and comments to avoid counting braces inside them.
     */
    private int findFunctionEnd(String script, int start) {
        int braceCount = 0;
        boolean foundOpenBrace = false;
        boolean inString = false;
        char stringChar = 0;
        boolean inSingleLineComment = false;
        boolean inMultiLineComment = false;

        for (int i = start; i < script.length(); i++) {
            char c = script.charAt(i);
            char nextChar = (i + 1 < script.length()) ? script.charAt(i + 1) : 0;
            char prevChar = (i > 0) ? script.charAt(i - 1) : 0;

            // Handle end of single-line comment
            if (inSingleLineComment) {
                if (c == '\n' || c == '\r') {
                    inSingleLineComment = false;
                }
                continue;
            }

            // Handle end of multi-line comment
            if (inMultiLineComment) {
                if (c == '*' && nextChar == '/') {
                    inMultiLineComment = false;
                    i++; // Skip the '/'
                }
                continue;
            }

            // Detect start of comments (only if not in string)
            if (!inString) {
                if (c == '/' && nextChar == '/') {
                    inSingleLineComment = true;
                    i++; // Skip the second '/'
                    continue;
                }
                if (c == '/' && nextChar == '*') {
                    inMultiLineComment = true;
                    i++; // Skip the '*'
                    continue;
                }
            }

            // Handle string literals
            if ((c == '"' || c == '\'' || c == '`') && prevChar != '\\') {
                if (!inString) {
                    inString = true;
                    stringChar = c;
                } else if (c == stringChar) {
                    inString = false;
                }
                continue;
            }

            if (inString) {
                continue;
            }

            // Count braces
            if (c == '{') {
                braceCount++;
                foundOpenBrace = true;
            } else if (c == '}') {
                braceCount--;
                if (foundOpenBrace && braceCount == 0) {
                    LOG.debug("findFunctionEnd: Found matching brace at position " + i);
                    return i + 1; // Include the closing brace
                }
            }
        }

        LOG.warn("findFunctionEnd: Could not find matching braces. Final braceCount=" + braceCount +
                ", foundOpenBrace=" + foundOpenBrace);
        return -1; // Could not find matching braces
    }

    /**
     * Escapes a string for use inside a JavaScript string literal.
     */
    private String escapeForJsString(String source) {
        return source
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    /**
     * Extracts top-level variable declarations from the script.
     * These need to be passed to callbacks since they run in a new context.
     * Only extracts simple variable declarations (arrays, strings, numbers),
     * NOT function expressions.
     */
    private String extractScriptPrelude(String script) {
        if (script == null || script.isEmpty()) {
            return "";
        }

        StringBuilder prelude = new StringBuilder();

        // Split script into lines and process each line
        String[] lines = script.split("\n");
        for (String line : lines) {
            String trimmed = line.trim();

            // Only match simple var declarations that end with semicolon on same line
            // Skip function expressions (var x = function...)
            if (trimmed.startsWith("var ") &&
                    trimmed.endsWith(";") &&
                    !trimmed.contains("function")) {
                prelude.append(trimmed).append("\n");
            }

            // Stop when we reach the main function definition
            if (trimmed.contains("onLoginRequest") || trimmed.startsWith("function ")) {
                break;
            }
        }

        return prelude.toString();
    }

    /**
     * Registers built-in functions with the WASM runtime.
     */
    private void registerBuiltInFunctions() {
        // Register executeStep handler
        runtime.registerHostFunction("executeStep", (name, args) -> {
            if (args != null && args.length > 0) {
                int stepId = ((Number) args[0]).intValue();
                Map<String, Object> options = args.length > 1 && args[1] instanceof Map ? (Map<String, Object>) args[1]
                        : null;
                executeStepInternal(stepId, options);
            }
            return null;
        });

        // Register sendError handler
        runtime.registerHostFunction("sendError", (name, args) -> {
            if (args != null && args.length >= 2) {
                String url = (String) args[0];
                Map<String, Object> params = args[1] instanceof Map ? (Map<String, Object>) args[1] : null;
                sendError(url, params != null ? params : Collections.emptyMap());
            }
            return null;
        });

        // Register fail handler
        runtime.registerHostFunction("fail", (name, args) -> {
            if (args != null && args.length > 0 && args[0] instanceof Map) {
                fail((Map<String, Object>) args[0]);
            } else {
                fail();
            }
            return null;
        });

        // Register prompt handler
        runtime.registerHostFunction("prompt", (name, args) -> {
            if (args != null && args.length >= 1) {
                String templateId = (String) args[0];
                Map<String, Object> data = args.length > 1 && args[1] instanceof Map ? (Map<String, Object>) args[1]
                        : null;
                Map<String, Object> handlers = args.length > 2 && args[2] instanceof Map ? (Map<String, Object>) args[2]
                        : null;
                addShowPromptInternal(templateId, data, handlers);
            }
            return null;
        });
    }

    /**
     * Registers custom functions from the function registry.
     */
    private void registerCustomFunctions() {
        JsFunctionRegistry jsFunctionRegistrar = FrameworkServiceDataHolder.getInstance().getJsFunctionRegistry();
        if (jsFunctionRegistrar != null) {
            Map<String, Object> functionMap = jsFunctionRegistrar
                    .getSubsystemFunctionsMap(JsFunctionRegistry.Subsystem.SEQUENCE_HANDLER);

            functionMap.forEach((funcName, func) -> {
                LOG.info("registerCustomFunctions: Registering custom function: " + funcName +
                        " (type: " + func.getClass().getName() +
                        ", interfaces: " + java.util.Arrays.toString(func.getClass().getInterfaces()) + ")");
                runtime.registerHostFunction(funcName, (name, args) -> {
                    // Invoke the registered function
                    try {
                        LOG.info("registerCustomFunctions: Invoking " + funcName + " with " +
                                (args != null ? args.length : 0) + " args");
                        if (args != null && args.length > 0) {
                            for (int i = 0; i < args.length; i++) {
                                LOG.info("registerCustomFunctions: args[" + i + "] type: " +
                                        (args[i] != null ? args[i].getClass().getName() : "null") +
                                        ", value: " + (args[i] != null ? args[i].toString().substring(0,
                                                Math.min(100, args[i].toString().length())) : "null"));
                            }
                        }

                        // Create live context
                        JsWasmAuthenticationContext jsContext = new JsWasmAuthenticationContext(authenticationContext);
                        // Extract the second argument (args[1]) for the function
                        Object secondArg = (args != null && args.length > 1) ? args[1] : null;

                        LOG.info("registerCustomFunctions: Calling " + funcName +
                                " with jsContext and secondArg: " + secondArg +
                                ", func is BiFunction? " + (func instanceof java.util.function.BiFunction));

                        Object result = null;
                        if (func instanceof java.util.function.BiFunction) {
                            // BiFunction expects (JsAuthenticationContext, secondArg)
                            result = ((java.util.function.BiFunction) func).apply(jsContext, secondArg);
                        } else {
                            // Log all methods on the function class for debugging
                            LOG.info("registerCustomFunctions: Available methods on " + funcName + ":");
                            for (java.lang.reflect.Method method : func.getClass().getMethods()) {
                                if (!method.getDeclaringClass().equals(Object.class)) {
                                    LOG.info("  - " + method.getName() + "(" +
                                            java.util.Arrays.toString(method.getParameterTypes()) + ") -> " +
                                            method.getReturnType().getSimpleName());
                                }
                            }

                            // Try reflection for other functional interfaces
                            // Look for common method names: apply, hasAnyOfTheRolesV2, execute, etc
                            String[] methodNames = { "hasAnyOfTheRolesV2", "apply", "execute", "call", funcName };
                            for (String methodName : methodNames) {
                                for (java.lang.reflect.Method method : func.getClass().getMethods()) {
                                    if (methodName.equals(method.getName()) && method.getParameterCount() == 2) {
                                        LOG.info("registerCustomFunctions: Trying method: " + method);
                                        try {
                                            result = method.invoke(func, jsContext, secondArg);
                                            LOG.info("registerCustomFunctions: Method " + methodName + " returned: "
                                                    + result);
                                            break;
                                        } catch (Exception e) {
                                            LOG.warn("registerCustomFunctions: Method " + methodName + " failed: "
                                                    + e.getMessage());
                                        }
                                    }
                                }
                                if (result != null)
                                    break;
                            }
                        }

                        LOG.info("registerCustomFunctions: " + funcName + " returned: " + result);
                        return result;
                    } catch (Exception e) {
                        LOG.error("Error invoking custom function: " + funcName, e);
                    }
                    return null;
                });
            });
        }
    }

    /**
     * Processes the script result to build the authentication graph.
     */
    private void processScriptResult(Map<String, Object> resultMap) {
        // Process any steps or decisions that were recorded
        if (resultMap.containsKey("success") && Boolean.TRUE.equals(resultMap.get("success"))) {
            result.setBuildSuccessful(true);
        }
    }

    /**
     * Internal method to execute a step.
     */
    @SuppressWarnings("unchecked")
    private void executeStepInternal(int stepId, Map<String, Object> options) {
        StepConfig stepConfig = stepNamedMap.get(stepId);

        if (stepConfig == null) {
            LOG.error("Given Authentication Step :" + stepId + " is not in Environment");
            return;
        }

        StepConfigGraphNode newNode = wrap(stepConfig);
        if (currentNode == null) {
            result.setStartNode(newNode);
        } else {
            attachToLeaf(currentNode, newNode);
        }
        currentNode = newNode;

        if (options != null) {
            handleOptions(options, stepConfig);

            // Handle event listeners if present
            Map<String, Object> eventListeners = extractEventListeners(options);
            if (eventListeners != null && !eventListeners.isEmpty()) {
                attachEventListeners(eventListeners);
            }
        }
    }

    /**
     * Extracts event listeners from options.
     */
    private Map<String, Object> extractEventListeners(Map<String, Object> options) {
        // Look for onSuccess, onFail callbacks
        if (options.containsKey("onSuccess") || options.containsKey("onFail")) {
            return options;
        }
        return null;
    }

    /**
     * Attaches event listeners to the current node.
     */
    private void attachEventListeners(Map<String, Object> eventsMap) {
        if (eventsMap == null) {
            return;
        }
        DynamicDecisionNode decisionNode = new DynamicDecisionNode();
        addEventListeners(decisionNode, eventsMap);
        if (!decisionNode.getGenericFunctionMap().isEmpty()) {
            attachToLeaf(currentNode, decisionNode);
            currentNode = decisionNode;
        }
    }

    /**
     * Adds event listeners to a decision node.
     * Uses source-based serialization since WasmRuntime cannot persist across
     * requests.
     */
    private void addEventListeners(DynamicDecisionNode decisionNode, Map<String, Object> eventsMap) {
        if (eventsMap == null) {
            return;
        }
        eventsMap.forEach((key, value) -> {
            if (key.equals("onSuccess") || key.equals("onFail")) {
                LOG.info("addEventListeners: Processing callback '" + key + "' with value type: " +
                        (value != null ? value.getClass().getName() : "null"));
                if (value instanceof java.util.Map) {
                    LOG.info("addEventListeners: Callback map contents: " + value);
                }

                WasmSerializableJsFunction jsFunction = WasmSerializableJsFunction.toSerializableForm(value);
                if (jsFunction != null) {
                    String source = jsFunction.getSource();
                    LOG.info("addEventListeners: Created callback '" + key + "' with source length: " +
                            (source != null ? source.length() : 0));
                    if (source != null && source.length() < 200) {
                        LOG.info("addEventListeners: Callback source: " + source);
                    }
                    // Set the script prelude so callbacks have access to top-level variables
                    if (currentScriptPrelude != null && !currentScriptPrelude.isEmpty()) {
                        jsFunction.setScriptPrelude(currentScriptPrelude);
                        LOG.info("addEventListeners: Set script prelude for callback '" + key + "'");
                    }
                    decisionNode.addGenericFunction(key, jsFunction);
                } else {
                    LOG.error("Event handler : " + key + " is not a function : " + value);
                }
            }
        });
    }

    /**
     * Internal method to show a prompt.
     */
    @SuppressWarnings("unchecked")
    private void addShowPromptInternal(String templateId, Map<String, Object> data, Map<String, Object> handlers) {
        ShowPromptNode newNode = new ShowPromptNode();
        newNode.setTemplateId(templateId);
        if (data != null) {
            newNode.setParameters(data);
        }

        if (currentNode == null) {
            result.setStartNode(newNode);
        } else {
            attachToLeaf(currentNode, newNode);
        }

        currentNode = newNode;

        if (handlers != null) {
            addEventListeners(newNode, handlers);
        }
    }

    /**
     * Adds event listeners to a show prompt node.
     */
    private void addEventListeners(ShowPromptNode showPromptNode, Map<String, Object> eventsMap) {
        if (eventsMap == null) {
            return;
        }
        eventsMap.forEach((key, value) -> {
            WasmSerializableJsFunction jsFunction = WasmSerializableJsFunction.toSerializableForm(value);
            if (jsFunction != null) {
                showPromptNode.addGenericHandler(key, jsFunction);
            }
        });
    }

    @Override
    public AuthenticationDecisionEvaluator getScriptEvaluator(BaseSerializableJsFunction fn) {
        return null;
    }

    @Override
    public AuthenticationDecisionEvaluator getScriptEvaluator(GenericSerializableJsFunction fn) {
        if (fn instanceof WasmSerializableJsFunction) {
            return new JsWasmBasedEvaluator((WasmSerializableJsFunction) fn);
        }
        LOG.error("Unsupported function type for getScriptEvaluator: " +
                (fn != null ? fn.getClass().getName() : "null"));
        return null;
    }

    @Override
    public void addLongWaitProcessInternal(AsyncProcess asyncProcess, Map<String, Object> parameterMap) {
        LongWaitNode newNode = new LongWaitNode(asyncProcess);

        if (parameterMap != null) {
            DynamicDecisionNode decisionNode = new DynamicDecisionNode();
            addEventListeners(decisionNode, parameterMap);
            if (!decisionNode.getGenericFunctionMap().isEmpty()) {
                newNode.setDefaultEdge(decisionNode);
            }
        }

        if (this.currentNode == null) {
            this.result.setStartNode(newNode);
        } else {
            attachToLeaf(this.currentNode, newNode);
        }

        this.currentNode = newNode;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addPromptInternal(String templateId, Map<String, Object> parameters, Map<String, Object> handlers,
            Map<String, Object> callbacks) {
        ShowPromptNode newNode = new ShowPromptNode();
        newNode.setTemplateId(templateId);
        newNode.setParameters(parameters);

        JsWasmGraphBuilder currentBuilder = (JsWasmGraphBuilder) getCurrentBuilder();
        if (currentBuilder.currentNode == null) {
            currentBuilder.result.setStartNode(newNode);
        } else {
            attachToLeaf(currentBuilder.currentNode, newNode);
        }

        currentBuilder.currentNode = newNode;

        if (callbacks != null) {
            addEventListeners(newNode, callbacks);
        }

        if (handlers != null) {
            addHandlers(newNode, handlers);
        }
    }

    /**
     * Adds handlers to a show prompt node.
     */
    private void addHandlers(ShowPromptNode showPromptNode, Map<String, Object> handlersMap) {
        if (handlersMap == null) {
            return;
        }
        handlersMap.forEach((key, value) -> {
            WasmSerializableJsFunction jsFunction = WasmSerializableJsFunction.toSerializableForm(value);
            if (jsFunction != null) {
                showPromptNode.addGenericHandler(key, jsFunction);
            }
        });
    }

    /**
     * Handle options within executeStepInAsyncEvent function.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void handleOptionsAsyncEvent(Map<String, Object> options, StepConfig stepConfig,
            Map<Integer, StepConfig> stepConfigMap) {
        Object authenticationOptionsObj = options.get(AUTHENTICATION_OPTIONS);
        if (authenticationOptionsObj instanceof List) {
            List<Map<String, String>> authenticationOptionsList = (List<Map<String, String>>) authenticationOptionsObj;
            authenticationOptionsObj = IntStream
                    .range(0, authenticationOptionsList.size())
                    .boxed()
                    .collect(Collectors.toMap(String::valueOf, authenticationOptionsList::get));
        }

        if (authenticationOptionsObj instanceof Map) {
            filterOptions((Map<String, Map<String, String>>) authenticationOptionsObj, stepConfig);
        }

        Object authenticatorParams = options.get(AUTHENTICATOR_PARAMS);
        if (authenticatorParams instanceof Map) {
            authenticatorParamsOptions((Map<String, Object>) authenticatorParams, stepConfig);
        }

        Object stepOptions = options.get(STEP_OPTIONS);
        if (stepOptions instanceof Map) {
            handleStepOptions(stepConfig, (Map<String, String>) stepOptions, stepConfigMap);
        }
    }

    /**
     * Clears the current builder and closes the runtime.
     */
    public static void clearCurrentBuilder(WasmRuntime runtime) {
        if (runtime != null) {
            runtime.close();
        }
        clearCurrentBuilder();
    }

    /**
     * Gets the WASM runtime.
     */
    public WasmRuntime getRuntime() {
        return this.runtime;
    }

    // Execution monitoring methods
    private JSExecutionSupervisor getJSExecutionSupervisor() {
        return FrameworkServiceDataHolder.getInstance().getJsExecutionSupervisor();
    }

    private void storeAuthScriptExecutionMonitorData(AuthenticationContext context,
            JSExecutionMonitorData jsExecutionMonitorData) {
        context.setProperty(PROP_EXECUTION_SUPERVISOR_RESULT, jsExecutionMonitorData);
    }

    private JSExecutionMonitorData retrieveAuthScriptExecutionMonitorData(AuthenticationContext context) {
        Object storedResult = context.getProperty(PROP_EXECUTION_SUPERVISOR_RESULT);
        if (storedResult != null) {
            return (JSExecutionMonitorData) storedResult;
        }
        return new JSExecutionMonitorData(0L, 0L);
    }

    private void startScriptExecutionMonitor(String identifier, AuthenticationContext context,
            JSExecutionMonitorData previousExecutionResult) {
        JSExecutionSupervisor jsExecutionSupervisor = getJSExecutionSupervisor();
        if (jsExecutionSupervisor == null) {
            return;
        }
        jsExecutionSupervisor.monitor(identifier, context.getServiceProviderName(), context.getTenantDomain(),
                previousExecutionResult.getElapsedTime(), previousExecutionResult.getConsumedMemory());
    }

    private void startScriptExecutionMonitor(String identifier, AuthenticationContext context) {
        startScriptExecutionMonitor(identifier, context, new JSExecutionMonitorData(0L, 0L));
    }

    private JSExecutionMonitorData endScriptExecutionMonitor(String identifier) {
        JSExecutionSupervisor executionSupervisor = getJSExecutionSupervisor();
        if (executionSupervisor == null) {
            return null;
        }
        return executionSupervisor.completed(identifier);
    }

    /**
     * WASM-based Decision Evaluator implementation.
     */
    public class JsWasmBasedEvaluator implements AuthenticationDecisionEvaluator {

        private static final long serialVersionUID = 1L;
        private final WasmSerializableJsFunction jsFunction;

        public JsWasmBasedEvaluator(WasmSerializableJsFunction jsFunction) {
            this.jsFunction = jsFunction;
        }

        @Override
        public Object evaluate(AuthenticationContext authenticationContext, Object... params) {
            JsWasmGraphBuilder graphBuilder = JsWasmGraphBuilder.this;
            Object evaluationResult = null;

            if (jsFunction == null) {
                return null;
            }

            if (!jsFunction.isFunction()) {
                return jsFunction.getSource();
            }

            try {
                currentBuilder.set(graphBuilder);
                JsWasmGraphBuilderFactory.restoreCurrentContext(authenticationContext, runtime);

                // Register functions for async execution
                registerBuiltInFunctions();
                registerCustomFunctions();

                String identifier = UUID.randomUUID().toString();
                Optional<JSExecutionMonitorData> optionalScriptExecutionData = Optional
                        .ofNullable(retrieveAuthScriptExecutionMonitorData(authenticationContext));

                try {
                    startScriptExecutionMonitor(identifier, authenticationContext,
                            optionalScriptExecutionData.orElse(null));

                    // Execute the function
                    evaluationResult = jsFunction.apply(runtime, params);

                } finally {
                    optionalScriptExecutionData = Optional.ofNullable(endScriptExecutionMonitor(identifier));
                }

                optionalScriptExecutionData
                        .ifPresent(scriptExecutionData -> storeAuthScriptExecutionMonitorData(authenticationContext,
                                scriptExecutionData));

                JsWasmGraphBuilderFactory.persistCurrentContext(authenticationContext, runtime);

                AuthGraphNode executingNode = (AuthGraphNode) authenticationContext.getProperty(PROP_CURRENT_NODE);
                if (canInfuse(executingNode)) {
                    infuse(executingNode, dynamicallyBuiltBaseNode.get());
                }

            } catch (Throwable e) {
                LOG.error("Error in executing the WASM JavaScript for service provider : " +
                        authenticationContext.getServiceProviderName() + ", Javascript Fragment : \n" +
                        jsFunction.getSource(), e);
                AuthGraphNode executingNode = (AuthGraphNode) authenticationContext.getProperty(PROP_CURRENT_NODE);
                FailNode failNode = new FailNode();
                attachToLeaf(executingNode, failNode);
            } finally {
                contextForJs.remove();
                dynamicallyBuiltBaseNode.remove();
                clearCurrentBuilder();
            }

            return evaluationResult;
        }
    }

    private boolean canInfuse(AuthGraphNode executingNode) {
        return executingNode instanceof DynamicDecisionNode && dynamicallyBuiltBaseNode.get() != null;
    }

    /**
     * WASM Callback Evaluator - evaluates WasmCallback functions using the callback
     * registry.
     */
    public class JsWasmCallbackEvaluator implements AuthenticationDecisionEvaluator {

        private static final long serialVersionUID = 1L;
        private final WasmCallback wasmCallback;

        public JsWasmCallbackEvaluator(WasmCallback wasmCallback) {
            this.wasmCallback = wasmCallback;
        }

        @Override
        public Object evaluate(AuthenticationContext authenticationContext, Object... params) {
            JsWasmGraphBuilder graphBuilder = JsWasmGraphBuilder.this;
            Object evaluationResult = null;

            if (wasmCallback == null) {
                return null;
            }

            try {
                currentBuilder.set(graphBuilder);
                JsWasmGraphBuilderFactory.restoreCurrentContext(authenticationContext, runtime);

                // Register functions for async execution
                registerBuiltInFunctions();
                registerCustomFunctions();

                String identifier = UUID.randomUUID().toString();
                Optional<JSExecutionMonitorData> optionalScriptExecutionData = Optional
                        .ofNullable(retrieveAuthScriptExecutionMonitorData(authenticationContext));

                try {
                    startScriptExecutionMonitor(identifier, authenticationContext,
                            optionalScriptExecutionData.orElse(null));

                    LOG.info("JsWasmCallbackEvaluator: Invoking callback " + wasmCallback.getCallbackId());

                    // Execute the callback via the callback registry
                    evaluationResult = wasmCallback.apply(runtime, params);

                } finally {
                    optionalScriptExecutionData = Optional.ofNullable(endScriptExecutionMonitor(identifier));
                }

                optionalScriptExecutionData
                        .ifPresent(scriptExecutionData -> storeAuthScriptExecutionMonitorData(authenticationContext,
                                scriptExecutionData));

                JsWasmGraphBuilderFactory.persistCurrentContext(authenticationContext, runtime);

                AuthGraphNode executingNode = (AuthGraphNode) authenticationContext.getProperty(PROP_CURRENT_NODE);
                if (canInfuse(executingNode)) {
                    infuse(executingNode, dynamicallyBuiltBaseNode.get());
                }

            } catch (Throwable e) {
                LOG.error("Error in executing callback for service provider : " +
                        authenticationContext.getServiceProviderName() + ", Callback ID: " +
                        wasmCallback.getCallbackId(), e);
                AuthGraphNode executingNode = (AuthGraphNode) authenticationContext.getProperty(PROP_CURRENT_NODE);
                FailNode failNode = new FailNode();
                attachToLeaf(executingNode, failNode);
            } finally {
                contextForJs.remove();
                dynamicallyBuiltBaseNode.remove();
                clearCurrentBuilder();
            }

            return evaluationResult;
        }
    }
}
