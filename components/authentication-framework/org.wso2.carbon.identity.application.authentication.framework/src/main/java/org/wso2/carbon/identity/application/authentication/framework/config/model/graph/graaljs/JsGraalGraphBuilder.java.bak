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

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.wso2.carbon.identity.application.authentication.framework.AsyncProcess;
import org.wso2.carbon.identity.application.authentication.framework.AuthenticationDecisionEvaluator;
import org.wso2.carbon.identity.application.authentication.framework.JsFunctionRegistry;
import org.wso2.carbon.identity.application.authentication.framework.config.model.StepConfig;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.AuthGraphNode;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.AuthenticationGraph;
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
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.EvaluationResult;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.JsEngine;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.JsEngineFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.internal.FrameworkServiceDataHolder;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.PROP_EXECUTION_SUPERVISOR_RESULT;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.AUTHENTICATION_OPTIONS;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.AUTHENTICATOR_PARAMS;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_AUTH_FAILURE;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_FUNC_EXECUTE_STEP;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_FUNC_GET_SECRET_BY_NAME;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_FUNC_LOAD_FUNC_LIB;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_FUNC_ON_LOGIN_REQUEST;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_FUNC_SEND_ERROR;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_FUNC_SHOW_PROMPT;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.POLYGLOT_LANGUAGE;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.POLYGLOT_SOURCE;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.PROP_CURRENT_NODE;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.STEP_OPTIONS;

/**
 * Translate the authentication graph config to runtime model.
 * This is not thread safe. Should be discarded after each build.
 * <p>
 * Since Nashorn is deprecated in JDK 11 and onwards. We are introducing GraalJS
 * engine.
 */
public class JsGraalGraphBuilder extends JsGraphBuilder {

    private static final Log log = LogFactory.getLog(JsGraalGraphBuilder.class);
    protected Context context;

    /**
     * Set the authentication context in the thread-local for JavaScript execution.
     * This method is exposed for remote JS engine callbacks to set up context.
     *
     * @param context The authentication context to set.
     */
    public static void setContextForJsThreadLocal(AuthenticationContext context) {
        contextForJs.set(context);
    }

    /**
     * Get the authentication context from the thread-local.
     *
     * @return The authentication context, or null if not set.
     */
    public static AuthenticationContext getContextForJsThreadLocal() {
        return contextForJs.get();
    }

    /**
     * Remove the authentication context from the thread-local.
     */
    public static void removeContextForJsThreadLocal() {
        contextForJs.remove();
    }

    /**
     * Set the dynamically built base node in the thread-local.
     * This method is exposed for remote JS engine callbacks.
     *
     * @param node The AuthGraphNode to set as the base node.
     */
    public static void setDynamicallyBuiltBaseNodeThreadLocal(AuthGraphNode node) {
        dynamicallyBuiltBaseNode.set(node);
    }

    /**
     * Get the dynamically built base node from the thread-local.
     *
     * @return The AuthGraphNode, or null if not set.
     */
    public static AuthGraphNode getDynamicallyBuiltBaseNodeThreadLocal() {
        return dynamicallyBuiltBaseNode.get();
    }

    /**
     * Remove the dynamically built base node from the thread-local.
     */
    public static void removeDynamicallyBuiltBaseNodeThreadLocal() {
        dynamicallyBuiltBaseNode.remove();
    }

    /**
     * Set the current builder in the thread-local.
     * This method is exposed for remote JS engine callbacks.
     *
     * @param builder The JsGraphBuilder to set.
     */
    public static void setCurrentBuilderThreadLocal(JsGraphBuilder builder) {
        currentBuilder.set(builder);
    }

    /**
     * Get the current builder from the thread-local.
     *
     * @return The JsGraphBuilder, or null if not set.
     */
    public static JsGraphBuilder getCurrentBuilderThreadLocal() {
        return currentBuilder.get();
    }

    /**
     * Remove the current builder from the thread-local.
     */
    public static void removeCurrentBuilderThreadLocal() {
        currentBuilder.remove();
    }

    private static final String REMOVE_FUNCTIONS = "var quit=function(){Log.error('quit function is restricted.')};" +
            "var exit=function(){Log.error('exit function is restricted.')};" +
            "var print=function(){Log.error('print function is restricted.')};" +
            "var echo=function(){Log.error('echo function is restricted.')};" +
            "var readFully=function(){Log.error('readFully function is restricted.')};" +
            "var readLine=function(){Log.error('readLine function is restricted.')};" +
            "var load=function(){Log.error('load function is restricted.')};" +
            "var loadWithNewGlobal=function(){Log.error('loadWithNewGlobal function is restricted.')};" +
            "var $ARG=null;var $ENV=null;var $EXEC=null;" +
            "var $OPTIONS=null;var $OUT=null;var $ERR=null;var $EXIT=null;" +
            "Object.defineProperty(this, 'engine', {});";

    /**
     * Constructs the builder with the given authentication context.
     *
     * @param authenticationContext current authentication context.
     * @param stepConfigMap         The Step map from the service provider
     *                              configuration.
     * @param context               Polyglot Context.
     */
    public JsGraalGraphBuilder(AuthenticationContext authenticationContext, Map<Integer, StepConfig> stepConfigMap,
            Context context) {

        this.authenticationContext = authenticationContext;
        this.context = context;
        stepNamedMap = stepConfigMap.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Constructs the builder with the given authentication context.
     *
     * @param authenticationContext current authentication context.
     * @param stepConfigMap         The Step map from the service provider
     *                              configuration.
     * @param context               polyglot context.
     * @param currentNode           Current authentication graph node.
     */
    public JsGraalGraphBuilder(AuthenticationContext authenticationContext, Map<Integer, StepConfig> stepConfigMap,
            Context context, AuthGraphNode currentNode) {

        this.authenticationContext = authenticationContext;
        this.context = context;
        this.currentNode = currentNode;
        stepNamedMap = stepConfigMap.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Creates the graph with the given Script and step map.
     *
     * @param script the Dynamic authentication script.
     */
    @Override
    public JsGraalGraphBuilder createWith(String script) {

        // Check if we should use remote execution via sidecar.
        JsEngineFactory jsEngineFactory = JsEngineFactory.getInstance();
        if (jsEngineFactory.getDefaultMode() == JsEngineFactory.ExecutionMode.REMOTE) {
            log.info("[createWith] Using REMOTE execution mode via sidecar");
            return createWithRemote(script);
        }

        log.info("[createWith] Using LOCAL execution mode");
        try {
            currentBuilder.set(this);
            Value bindings = context.getBindings(POLYGLOT_LANGUAGE);

            bindings.putMember(JS_FUNC_EXECUTE_STEP, new JsGraalStepExecuter());
            bindings.putMember(JS_FUNC_SEND_ERROR, new SendErrorFunctionImpl());
            bindings.putMember(JS_FUNC_SHOW_PROMPT, new JsGraalPromptExecutorImpl());
            bindings.putMember(JS_FUNC_LOAD_FUNC_LIB, new JsGraalLoadExecutorImpl());
            bindings.putMember(JS_FUNC_GET_SECRET_BY_NAME, new JsGraalGetSecretImpl());
            JsFunctionRegistry jsFunctionRegistrar = FrameworkServiceDataHolder.getInstance().getJsFunctionRegistry();
            if (jsFunctionRegistrar != null) {
                Map<String, Object> functionMap = jsFunctionRegistrar
                        .getSubsystemFunctionsMap(JsFunctionRegistry.Subsystem.SEQUENCE_HANDLER);
                functionMap.forEach(bindings::putMember);
            }
            currentBuilder.set(this);
            context.eval(Source
                    .newBuilder(POLYGLOT_LANGUAGE,
                            FrameworkServiceDataHolder.getInstance().getCodeForRequireFunction(),
                            POLYGLOT_SOURCE)
                    .build());
            context.eval(Source
                    .newBuilder(POLYGLOT_LANGUAGE,
                            FrameworkServiceDataHolder.getInstance().getCodeForSecretsFunction(),
                            POLYGLOT_SOURCE)
                    .build());

            String identifier = UUID.randomUUID().toString();
            Optional<JSExecutionMonitorData> optionalScriptExecutionData;

            try {
                startScriptExecutionMonitor(identifier, authenticationContext);
                context.eval(Source.newBuilder(POLYGLOT_LANGUAGE, script, POLYGLOT_SOURCE).build());

                Value onLoginRequestFn = bindings.getMember(JS_FUNC_ON_LOGIN_REQUEST);
                if (onLoginRequestFn == null) {
                    log.error("Could not find the entry function " + JS_FUNC_ON_LOGIN_REQUEST + " \n" + script);
                    result.setBuildSuccessful(false);
                    result.setErrorReason("Error in executing the Javascript. " + JS_FUNC_ON_LOGIN_REQUEST +
                            " function is not defined.");
                    return this;
                }
                onLoginRequestFn.executeVoid(new JsGraalAuthenticationContext(authenticationContext));
            } finally {
                optionalScriptExecutionData = Optional.ofNullable(endScriptExecutionMonitor(identifier));
            }
            optionalScriptExecutionData.ifPresent(
                    scriptExecutionData ->
                            storeAuthScriptExecutionMonitorData(authenticationContext,
                            scriptExecutionData));
            log.info("[createWith] About to persist context bindings for SP: " +
                    authenticationContext.getServiceProviderName() +
                    ", contextId: " + authenticationContext.getContextIdentifier() +
                    ", authContext hashCode: " + System.identityHashCode(authenticationContext));
            JsGraalGraphBuilderFactory.persistCurrentContext(authenticationContext, context);
            log.info("[createWith] Bindings persisted successfully");
        } catch (PolyglotException e) {
            result.setBuildSuccessful(false);
            result.setErrorReason(
                    "Error in executing the Javascript. " + JS_FUNC_ON_LOGIN_REQUEST + " reason, " + e.getMessage());
            if (log.isDebugEnabled()) {
                log.debug("Error in executing the Javascript.", e);
            }
        } catch (IOException e) {
            result.setBuildSuccessful(false);
            result.setErrorReason("Error in building  the Javascript source" + e.getMessage());
            if (log.isDebugEnabled()) {
                log.debug("Error in building the Javascript source", e);
            }
        } finally {
            clearCurrentBuilder(context);
        }
        return this;
    }

    /**
     * Creates the graph with the given Script using remote sidecar execution.
     * This method sends the script to the sidecar for evaluation and processes
     * callback results.
     *
     * @param script the Dynamic authentication script.
     * @return This builder.
     */
    @SuppressWarnings("unchecked")
    private JsGraalGraphBuilder createWithRemote(String script) {

        try (JsEngine jsEngine = JsEngineFactory.getInstance().createEngine(authenticationContext)) {
            currentBuilder.set(this);
            contextForJs.set(authenticationContext);

            // Set graph builder on remote engine so gRPC callback threads can set currentBuilder ThreadLocal.
            if (jsEngine instanceof RemoteJsEngine) {
                ((RemoteJsEngine) jsEngine).setGraphBuilder(this);
            }

            log.info("[createWithRemote] Starting for SP: " + authenticationContext.getServiceProviderName() +
                    ", contextId: " + authenticationContext.getContextIdentifier());

            // Register host functions that the sidecar can call back.
            Map<String, Object> hostFunctions = new HashMap<>();
            hostFunctions.put(JS_FUNC_EXECUTE_STEP, new JsGraalStepExecuter());
            hostFunctions.put(JS_FUNC_SEND_ERROR, new SendErrorFunctionImpl());
            hostFunctions.put(JS_FUNC_SHOW_PROMPT, new JsGraalPromptExecutorImpl());
            hostFunctions.put(JS_FUNC_LOAD_FUNC_LIB, new JsGraalLoadExecutorImpl());
            hostFunctions.put(JS_FUNC_GET_SECRET_BY_NAME, new JsGraalGetSecretImpl());

            JsFunctionRegistry jsFunctionRegistrar = FrameworkServiceDataHolder.getInstance().getJsFunctionRegistry();
            if (jsFunctionRegistrar != null) {
                hostFunctions.putAll(jsFunctionRegistrar.getSubsystemFunctionsMap(
                        JsFunctionRegistry.Subsystem.SEQUENCE_HANDLER));
            }
            jsEngine.registerHostFunctions(hostFunctions);
            log.info("[createWithRemote] Registered " + hostFunctions.size() + " host functions: " +
                    hostFunctions.keySet());

            // Build the complete script including require function, secrets, and main
            // script.
            String completeScript = FrameworkServiceDataHolder.getInstance().getCodeForRequireFunction() +
                    "\n" +
                    FrameworkServiceDataHolder.getInstance().getCodeForSecretsFunction() +
                    "\n" +
                    script +
                    "\n" +
                    // Call onLoginRequest with context placeholder - sidecar will inject actual
                    // context.
                    JS_FUNC_ON_LOGIN_REQUEST + "(context);";

            log.info("[createWithRemote] Sending script (length: " + completeScript.length() +
                    ") to sidecar for evaluation");

            // Build initial bindings (context will be created by sidecar from ContextData).
            Map<String, Object> initialBindings = new HashMap<>();

            String identifier = UUID.randomUUID().toString();
            Optional<JSExecutionMonitorData> optionalScriptExecutionData = Optional.empty();

            try {
                startScriptExecutionMonitor(identifier, authenticationContext);

                // Evaluate script remotely.
                EvaluationResult evalResult = jsEngine.evaluate(
                        completeScript, "adaptive-script", initialBindings);

                if (!evalResult.isSuccess()) {
                    log.error("[createWithRemote] Script evaluation failed: " + evalResult.getErrorMessage());
                    result.setBuildSuccessful(false);
                    result.setErrorReason("Error in executing the Javascript. " + evalResult.getErrorMessage());
                    return this;
                }

                log.info("[createWithRemote] Script evaluation successful, elapsed: " +
                        evalResult.getElapsedMs() + "ms");

                // Update bindings from sidecar response.
                if (evalResult.getUpdatedBindings() != null) {
                    log.info("[createWithRemote] Updating bindings from sidecar: " +
                            evalResult.getUpdatedBindings().keySet());
                    for (Map.Entry<String, Object> entry : evalResult.getUpdatedBindings().entrySet()) {
                        // Convert binding to serializable form for persistence.
                        Object value = entry.getValue();
                        if (value instanceof String) {
                            String strValue = (String) value;
                            // Check if it's a function source.
                            if (strValue.trim().startsWith("function") || strValue.contains("=>")) {
                                value = new GraalSerializableJsFunction(strValue);
                            }
                        }
                        jsEngine.putBinding(entry.getKey(), value);
                    }
                }

            } finally {
                optionalScriptExecutionData = Optional.ofNullable(endScriptExecutionMonitor(identifier));
            }

            optionalScriptExecutionData.ifPresent(
                    scriptExecutionData ->
                            storeAuthScriptExecutionMonitorData(authenticationContext,
                            scriptExecutionData));

            log.info("[createWithRemote] Script execution completed for SP: " +
                    authenticationContext.getServiceProviderName());

            // Persist bindings for later callback execution.
            // Note: With remote execution, we persist the updated bindings from sidecar.
            Map<String, Object> persistableBindings = jsEngine.getBindings();
            authenticationContext.setProperty("JS_BINDING_CURRENT_CONTEXT", persistableBindings);
            log.info("[createWithRemote] Persisted " + persistableBindings.size() + " bindings");

        } catch (Exception e) {
            log.error("[createWithRemote] Error during remote script evaluation", e);
            result.setBuildSuccessful(false);
            result.setErrorReason("Error in remote JavaScript execution: " + e.getMessage());
        } finally {
            currentBuilder.remove();
            contextForJs.remove();
        }

        return this;
    }

    @Override
    public AuthenticationDecisionEvaluator getScriptEvaluator(BaseSerializableJsFunction fn) {

        return null;
    }

    private static void attachEventListeners(Map<String, Object> eventsMap, AuthGraphNode currentNode) {

        if (eventsMap == null) {
            return;
        }
        DynamicDecisionNode decisionNode = new DynamicDecisionNode();
        addEventListeners(decisionNode, eventsMap);
        if (!decisionNode.getGenericFunctionMap().isEmpty()) {
            attachToLeaf(currentNode, decisionNode);
        }
    }

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
     * Adds all the event listeners to the decision node.
     *
     * @param eventsMap Map of events and event handler functions, which is handled
     *                  by this execution.
     * @return created Dynamic Decision node.
     */
    private static void addEventListeners(DynamicDecisionNode decisionNode, Map<String, Object> eventsMap) {

        if (eventsMap == null) {
            return;
        }
        log.info("[addEventListeners] Received eventsMap with " + eventsMap.size() + " entries");
        eventsMap.forEach((key, value) -> {
            log.info("[addEventListeners] Processing event: " + key + ", value type: " +
                    (value != null ? value.getClass().getName() : "null") +
                    ", isValue: " + (value instanceof Value) +
                    ", isMap: " + (value instanceof Map));

            // Check if it's a PolyglotMapAndFunction - detect by class name since it's not instanceof Value
            if (value != null && value.getClass().getName().contains("PolyglotMapAndFunction")) {
                log.info("[addEventListeners] Detected PolyglotMapAndFunction for event: " + key);
                try {
                    // Try to convert to Value using current Context
                    Context currentContext = Context.getCurrent();
                    if (currentContext != null) {
                        Value valueAsValue = currentContext.asValue(value);
                        if (valueAsValue.canExecute()) {
                            log.info("[addEventListeners] Successfully converted to executable Value");
                            GraalSerializableJsFunction jsFunction = 
                                GraalSerializableJsFunction.toSerializableForm(valueAsValue);
                            if (jsFunction != null) {
                                decisionNode.addGenericFunction(key, jsFunction);
                                String eventMessage = "[addEventListeners] Successfully serialized " +
                                    "PolyglotMapAndFunction for event: " + key;
                                log.info(eventMessage);
                                return; // Skip further processing
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("[addEventListeners] Failed to convert PolyglotMapAndFunction to Value", e);
                }
            }

            if (value instanceof GraalSerializableJsFunction) {
                decisionNode.addGenericFunction(key, (GraalSerializableJsFunction) value);
            } else if (value instanceof String) {
                // Handle function source code sent directly as String from sidecar.
                String source = (String) value;
                if (source.trim().startsWith("function") || source.contains("=>")) {
                    GraalSerializableJsFunction jsFunction = new GraalSerializableJsFunction(source);
                    decisionNode.addGenericFunction(key, jsFunction);
                    if (log.isDebugEnabled()) {
                        log.debug("Created GraalSerializableJsFunction from String source for event: " + key);
                    }
                } else {
                    log.error("Event handler : " + key + " is a String but doesn't look like a function: "
                            + source);
                }
            } else if (value instanceof Map) {
                // Handle function references from remote sidecar - they may come as Maps with
                // source code.
                Map<?, ?> funcMap = (Map<?, ?>) value;
                Object sourceObj = funcMap.get("source");
                if (sourceObj != null) {
                    String source = sourceObj.toString();
                    GraalSerializableJsFunction jsFunction = new GraalSerializableJsFunction(source);
                    decisionNode.addGenericFunction(key, jsFunction);
                    if (log.isDebugEnabled()) {
                        log.debug("Created GraalSerializableJsFunction from Map source for event: " + key);
                    }
                } else {
                    // Try to find any string value that looks like function source.
                    String funcSource = findFunctionSourceInMap(funcMap);
                    if (funcSource != null) {
                        GraalSerializableJsFunction jsFunction = new GraalSerializableJsFunction(funcSource);
                        decisionNode.addGenericFunction(key, jsFunction);
                        log.info("Created GraalSerializableJsFunction from Map value for event: " + key);
                    } else {
                        log.error("Event handler : " + key + " is a Map but has no usable function source. Keys: " +
                                funcMap.keySet() + ", values: " + funcMap.values());
                    }
                }
            } else {
                // Try original path for local GraalVM Value objects.
                GraalSerializableJsFunction jsFunction = GraalSerializableJsFunction.toSerializableForm(value);
                if (jsFunction != null) {
                    decisionNode.addGenericFunction(key, jsFunction);
                } else {
                    log.error("Event handler : " + key + " is not a function, type: " +
                            (value != null ? value.getClass().getName() : "null") + ", value: " + value);
                }
            }
        });
    }

    /**
     * Finds function source code within a Map by looking for string values that
     * look like functions.
     *
     * @param map The map to search.
     * @return The function source if found, null otherwise.
     */
    private static String findFunctionSourceInMap(Map<?, ?> map) {
        for (Object val : map.values()) {
            if (val instanceof String) {
                String str = (String) val;
                if (str.trim().startsWith("function") || str.contains("=>")) {
                    return str;
                }
            }
        }
        return null;
    }

    private static void addHandlers(ShowPromptNode showPromptNode, Map<String, Object> handlersMap) {

        if (handlersMap == null) {
            return;
        }
        handlersMap.forEach((key, value) -> {
            if (value instanceof GraalSerializableJsFunction) {
                showPromptNode.addGenericHandler(key, (GraalSerializableJsFunction) value);
            } else if (value instanceof String) {
                // Handle function source code sent directly as String from sidecar.
                String source = (String) value;
                if (source.trim().startsWith("function") || source.contains("=>")) {
                    GraalSerializableJsFunction jsFunction = new GraalSerializableJsFunction(source);
                    showPromptNode.addGenericHandler(key, jsFunction);
                    if (log.isDebugEnabled()) {
                        log.debug("Created GraalSerializableJsFunction from String source for handler: " + key);
                    }
                } else {
                    log.error("Handler : " + key + " is a String but doesn't look like a function: " + source);
                }
            } else if (value instanceof Map) {
                // Handle function references from remote sidecar - they may come as Maps with
                // source code.
                Map<?, ?> funcMap = (Map<?, ?>) value;
                Object sourceObj = funcMap.get("source");
                if (sourceObj != null) {
                    String source = sourceObj.toString();
                    GraalSerializableJsFunction jsFunction = new GraalSerializableJsFunction(source);
                    showPromptNode.addGenericHandler(key, jsFunction);
                    if (log.isDebugEnabled()) {
                        log.debug("Created GraalSerializableJsFunction from Map source for handler: " + key);
                    }
                } else {
                    // Try to find any string value that looks like function source.
                    String funcSource = findFunctionSourceInMap(funcMap);
                    if (funcSource != null) {
                        GraalSerializableJsFunction jsFunction = new GraalSerializableJsFunction(funcSource);
                        showPromptNode.addGenericHandler(key, jsFunction);
                        log.info("Created GraalSerializableJsFunction from Map value for handler: " + key);
                    } else {
                        log.error("Handler : " + key + " is a Map but has no usable function source. Keys: " +
                                funcMap.keySet() + ", values: " + funcMap.values());
                    }
                }
            } else {
                // Try original path for local GraalVM Value objects.
                GraalSerializableJsFunction jsFunction = GraalSerializableJsFunction.toSerializableForm(value);
                if (jsFunction != null) {
                    showPromptNode.addGenericHandler(key, jsFunction);
                } else {
                    log.error("Handler : " + key + " is not a function, type: " +
                            (value != null ? value.getClass().getName() : "null") + ", value: " + value);
                }
            }
        });
    }

    /**
     * Adds the step given by step ID tp the authentication graph.
     *
     * @param params params
     */
    @SuppressWarnings("unchecked")
    @HostAccess.Export
    public void executeStepInAsyncEvent(int stepId, Object... params) {

        AuthenticationContext context = contextForJs.get();
        AuthGraphNode currentNode = dynamicallyBuiltBaseNode.get();

        if (log.isDebugEnabled()) {
            log.debug("Execute Step on async event. Step ID : " + stepId);
        }
        AuthenticationGraph graph = context.getSequenceConfig().getAuthenticationGraph();
        if (graph == null) {
            log.error("The graph happens to be null on the sequence config. Can not execute step : " + stepId);
            return;
        }

        StepConfig stepConfig = graph.getStepMap().get(stepId);
        if (stepConfig == null) {
            if (log.isDebugEnabled()) {
                log.error("The stepConfig of the step ID : " + stepId + " is null");
            }
            return;
        }
        // Inorder to keep original stepConfig as a backup in AuthenticationGraph.
        StepConfig clonedStepConfig = new StepConfig(stepConfig);
        StepConfig stepConfigFromContext = null;
        if (MapUtils.isNotEmpty(context.getSequenceConfig().getStepMap())) {
            stepConfigFromContext = context.getSequenceConfig().getStepMap().values()
                    .stream()
                    .filter(contextStepConfig -> (stepConfig.getOrder() == contextStepConfig.getOrder()))
                    .findFirst()
                    .orElse(null);
        }
        clonedStepConfig.applyStateChangesToNewObjectFromContextStepMap(stepConfigFromContext);
        if (log.isDebugEnabled()) {
            log.debug("Found step for the Step ID : " + stepId + ", Step Config " + clonedStepConfig);
        }
        StepConfigGraphNode newNode = wrap(clonedStepConfig);
        if (currentNode == null) {
            if (log.isDebugEnabled()) {
                log.debug("Setting a new node at the first time. Node : " + newNode.getName());
            }
            dynamicallyBuiltBaseNode.set(newNode);
        } else {
            attachToLeaf(currentNode, newNode);
        }

        if (params.length > 0) {
            // if there is only one param, it is assumed to be the event listeners
            if (params[params.length - 1] instanceof Map) {
                attachEventListeners((Map<String, Object>) params[params.length - 1], newNode);
            } else {
                log.error("Invalid argument and hence ignored. Last argument should be a Map of event listeners.");
            }
        }

        if (params.length == 2) {
            // There is an argument with options present
            if (params[0] instanceof Map) {
                Map<String, Object> options = (Map<String, Object>) params[0];
                handleOptionsAsyncEvent(options, clonedStepConfig, context.getSequenceConfig().getStepMap());
            }
        }
    }

    /**
     * Executes the given script in an async event.
     */
    public class JsGraalStepExecuterInAsyncEvent implements StepExecutor {

        @HostAccess.Export
        public void executeStep(Integer stepId, Object... parameterMap) {

            JsGraalGraphBuilder.this.executeStepInAsyncEvent(stepId, parameterMap);
        }
    }

    @Override
    public void addLongWaitProcessInternal(AsyncProcess asyncProcess, Map<String, Object> parameterMap) {

        LongWaitNode newNode = new LongWaitNode(asyncProcess);

        if (parameterMap != null) {
            addEventListeners(newNode, parameterMap);
        }
        if (this.currentNode == null) {
            this.result.setStartNode(newNode);
        } else {
            attachToLeaf(this.currentNode, newNode);
        }

        this.currentNode = newNode;
    }

    /**
     * @param templateId Identifier of the template.
     * @param parameters Parameters.
     * @param handlers   Handlers to run before and after the prompt.
     * @param callbacks  Callbacks to run after the prompt.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addPromptInternal(String templateId, Map<String, Object> parameters, Map<String, Object> handlers,
            Map<String, Object> callbacks) {

        ShowPromptNode newNode = new ShowPromptNode();
        newNode.setTemplateId(templateId);
        newNode.setParameters(parameters);

        JsGraalGraphBuilder currentBuilder = getCurrentBuilder();
        if (currentBuilder.currentNode == null) {
            currentBuilder.result.setStartNode(newNode);
        } else {
            attachToLeaf(currentBuilder.currentNode, newNode);
        }

        currentBuilder.currentNode = newNode;
        addEventListeners(newNode, callbacks);
        addHandlers(newNode, handlers);
    }

    public AuthenticationDecisionEvaluator getScriptEvaluator(GenericSerializableJsFunction fn) {

        return new JsBasedEvaluator((GraalSerializableJsFunction) fn);
    }

    public static void clearCurrentBuilder(Context context) {

        context.close();
        clearCurrentBuilder();
    }

    /**
     * Adds the step given by step ID tp the authentication graph.
     *
     * @param stepId Step Id
     * @param params params
     */
    @SuppressWarnings("unchecked")
    @HostAccess.Export
    public void executeStep(int stepId, Object... params) {

        StepConfig stepConfig;
        stepConfig = stepNamedMap.get(stepId);

        if (stepConfig == null) {
            log.error("Given Authentication Step :" + stepId + " is not in Environment");
            return;
        }
        StepConfigGraphNode newNode = wrap(stepConfig);
        if (currentNode == null) {
            result.setStartNode(newNode);
        } else {
            attachToLeaf(currentNode, newNode);
        }
        currentNode = newNode;
        if (params.length > 0) {
            // if there are any params provided, last one is assumed to be the event
            // listeners
            if (params[params.length - 1] instanceof Map) {
                attachEventListeners((Map<String, Object>) params[params.length - 1]);
            } else {
                log.error("Invalid argument and hence ignored. Last argument should be a Map of event listeners.");
            }
        }
        if (params.length == 2) {
            // There is an argument with options present
            if (params[0] instanceof Map) {
                Map<String, Object> options = (Map<String, Object>) params[0];
                handleOptions(options, stepConfig);
            }
        }
    }

    /**
     * Executes the given script.
     */
    public class JsGraalStepExecuter implements StepExecutor {

        @HostAccess.Export
        public void executeStep(Integer stepId, Object... parameterMap) {

            JsGraalGraphBuilder.this.executeStep(stepId, parameterMap);
        }
    }

    /**
     * Handle options within executeStepInAsyncEvent function. This method will
     * update step configs through context.
     *
     * @param options       Map of authenticator options.
     * @param stepConfig    Current stepConfig.
     * @param stepConfigMap Map of stepConfigs get from the context object.
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
        } else {
            log.debug("Authenticator options not provided or invalid, hence proceeding without filtering");
        }

        Object authenticatorParams = options.get(AUTHENTICATOR_PARAMS);
        if (authenticatorParams instanceof Map) {
            authenticatorParamsOptions((Map<String, Object>) authenticatorParams, stepConfig);
        } else {
            log.debug("Authenticator params not provided or invalid, hence proceeding without setting params");
        }

        Object stepOptions = options.get(STEP_OPTIONS);
        if (stepOptions instanceof Map) {
            handleStepOptions(stepConfig, (Map<String, String>) stepOptions, stepConfigMap);
        } else {
            log.debug("Step options not provided or invalid, hence proceeding without handling");
        }
    }

    @HostAccess.Export
    public static void sendErrorAsync(String url, Map<String, Object> parameterMap) {

        FailNode newNode = createFailNode(url, parameterMap, true);

        AuthGraphNode currentNode = dynamicallyBuiltBaseNode.get();
        if (currentNode == null) {
            dynamicallyBuiltBaseNode.set(newNode);
        } else {
            attachToLeaf(currentNode, newNode);
        }
    }

    /**
     * Implementation of the SendErrorFunction interface as an adaptor for
     * sendErrorAsync function.
     */
    public static class SendErrorAsyncFunctionImpl implements SendErrorFunction {

        @HostAccess.Export
        public void sendError(String url, Map<String, Object> parameterMap) {
            sendErrorAsync(url, parameterMap);
        }
    }

    /**
     * Implementation of the SendErrorFunction interface as an adaptor for sendError
     * function.
     */
    public class SendErrorFunctionImpl implements SendErrorFunction {

        @HostAccess.Export
        public void sendError(String url, Map<String, Object> parameterMap) {
            JsGraalGraphBuilder.this.sendError(url, parameterMap);
        }
    }

    private static FailNode createFailNode(String url, Map<String, Object> parameterMap, boolean isShowErrorPage) {

        FailNode failNode = new FailNode();
        if (isShowErrorPage && StringUtils.isNotBlank(url)) {
            failNode.setErrorPageUri(url);
        }
        // setShowErrorPage is set to true as sendError function redirects to a specific
        // error page.
        failNode.setShowErrorPage(isShowErrorPage);

        parameterMap.forEach((key, value) -> failNode.getFailureData().put(key, String.valueOf(value)));
        return failNode;
    }

    /**
     * Javascript based Decision Evaluator implementation.
     * This is used to create the Authentication Graph structure dynamically on the
     * fly while the authentication flow
     * is happening.
     * The graph is re-organized based on last execution of the decision.
     */
    public class JsBasedEvaluator implements AuthenticationDecisionEvaluator {

        private static final long serialVersionUID = 6853505881096840344L;
        private final GraalSerializableJsFunction jsFunction;

        public JsBasedEvaluator(GraalSerializableJsFunction jsFunction) {

            this.jsFunction = jsFunction;
        }

        @Override
        @HostAccess.Export
        public Object evaluate(AuthenticationContext authenticationContext, Object... params) {

            JsGraalGraphBuilder graphBuilder = JsGraalGraphBuilder.this;
            Object result = null;
            if (jsFunction == null) {
                return null;
            }
            if (!jsFunction.isFunction()) {
                return jsFunction.getSource();
            }

            // Check if we should use remote execution via sidecar.
            JsEngineFactory jsEngineFactory = JsEngineFactory.getInstance();
            if (jsEngineFactory.getDefaultMode() == JsEngineFactory.ExecutionMode.REMOTE) {
                result = evaluateRemote(authenticationContext, params);
            } else {
                result = evaluateLocal(authenticationContext, params);
            }
            return result;
        }

        /**
         * Execute JavaScript in a remote sidecar process.
         * This provides isolation from the main JVM.
         *
         * @param authenticationContext The authentication context.
         * @param params                The parameters to pass to the function.
         * @return The result of the execution.
         */
        @SuppressWarnings("unchecked")
        private Object evaluateRemote(AuthenticationContext authenticationContext, Object... params) {

            JsGraalGraphBuilder graphBuilder = JsGraalGraphBuilder.this;
            Object result = null;

            try (JsEngine jsEngine = JsEngineFactory.getInstance().createEngine(authenticationContext)) {
                currentBuilder.set(graphBuilder);
                JsGraalGraphBuilder.contextForJs.set(authenticationContext);

                // Set graph builder on remote engine so gRPC callback threads can set currentBuilder ThreadLocal.
                if (jsEngine instanceof RemoteJsEngine) {
                    ((RemoteJsEngine) jsEngine).setGraphBuilder(graphBuilder);
                }

                // Log context info for debugging.
                log.info("[evaluateRemote] Starting for SP: " + authenticationContext.getServiceProviderName() +
                        ", contextId: " + authenticationContext.getContextIdentifier() +
                        ", step: " + authenticationContext.getCurrentStep() +
                        ", authContext hashCode: " + System.identityHashCode(authenticationContext));

                // Get persisted bindings from authentication context (variables like
                // rolesToStepUp).
                Map<String, Object> persistedBindings = (Map<String, Object>) authenticationContext
                        .getProperty("JS_BINDING_CURRENT_CONTEXT");
                if (persistedBindings != null) {
                    log.info("[evaluateRemote] Found " + persistedBindings.size() +
                            " persisted bindings: " + persistedBindings.keySet());
                    // Log each binding value for debugging.
                    for (Map.Entry<String, Object> entry : persistedBindings.entrySet()) {
                        log.info("[evaluateRemote] Binding: " + entry.getKey() + " = " +
                                (entry.getValue() != null
                                        ? entry.getValue().getClass().getSimpleName() + ": " + entry.getValue()
                                        : "null"));
                    }
                } else {
                    log.info("[evaluateRemote] No persisted bindings found in authContext. " +
                            "Property keys: " + authenticationContext.getProperties().keySet());
                    persistedBindings = new HashMap<>();
                }

                // Register host functions that the sidecar can call back.
                Map<String, Object> hostFunctions = new HashMap<>();
                hostFunctions.put(JS_FUNC_EXECUTE_STEP, new JsGraalStepExecuterInAsyncEvent());
                hostFunctions.put(JS_FUNC_SEND_ERROR, new SendErrorAsyncFunctionImpl());
                hostFunctions.put(JS_AUTH_FAILURE, new FailAuthenticationFunctionImpl());
                hostFunctions.put(JS_FUNC_SHOW_PROMPT, new JsGraalPromptExecutorImpl());
                hostFunctions.put(JS_FUNC_LOAD_FUNC_LIB, new JsGraalLoadExecutorImpl());
                hostFunctions.put(JS_FUNC_GET_SECRET_BY_NAME, new JsGraalGetSecretImpl());

                JsFunctionRegistry jsFunctionRegistrar = FrameworkServiceDataHolder.getInstance()
                        .getJsFunctionRegistry();
                if (jsFunctionRegistrar != null) {
                    Map<String, Object> functionMap = jsFunctionRegistrar
                            .getSubsystemFunctionsMap(JsFunctionRegistry.Subsystem.SEQUENCE_HANDLER);
                    hostFunctions.putAll(functionMap);
                }
                jsEngine.registerHostFunctions(hostFunctions);

                String identifier = UUID.randomUUID().toString();
                Optional<JSExecutionMonitorData> optionalScriptExecutionData = Optional
                        .ofNullable(retrieveAuthScriptExecutionMonitorData(authenticationContext));
                try {
                    startScriptExecutionMonitor(identifier, authenticationContext,
                            optionalScriptExecutionData.orElse(null));

                    // Reset accumulated dynamic base node before callback execution.
                    // This ensures a clean slate for each callback cycle, matching local mode
                    // where dynamicallyBuiltBaseNode starts null at callback entry.
                    if (jsEngine instanceof RemoteJsEngine) {
                        ((RemoteJsEngine) jsEngine).resetAccumulatedDynamicBaseNode();
                    }

                    // Execute the callback function in the sidecar with persisted bindings
                    EvaluationResult evalResult = jsEngine.executeCallback(
                            jsFunction.getSource(),
                            params,
                            persistedBindings, // Pass persisted bindings for variables like rolesToStepUp
                            authenticationContext);

                    if (evalResult.isSuccess()) {
                        result = evalResult.getResult();

                        // Re-persist updated bindings so next callback sees changes
                        // (e.g., dynamicFlag set in step 1 callback is available in step 2)
                        Map<String, Object> updatedBindings = jsEngine.getBindings();
                        authenticationContext.setProperty("JS_BINDING_CURRENT_CONTEXT", updatedBindings);
                        log.info("[evaluateRemote] Re-persisted " + updatedBindings.size() +
                                " bindings after callback");

                        if (log.isDebugEnabled()) {
                            log.debug("Remote JS execution succeeded for SP: " +
                                    authenticationContext.getServiceProviderName() +
                                    ", elapsed: " + evalResult.getElapsedMs() + "ms");
                        }
                    } else {
                        log.error("Remote JS execution failed for SP: " +
                                authenticationContext.getServiceProviderName() +
                                ", error: " + evalResult.getErrorMessage());
                        AuthGraphNode executingNode = (AuthGraphNode) authenticationContext
                                .getProperty(PROP_CURRENT_NODE);
                        // Create a proper FailNode with error information so user sees error page
                        FailNode failNode = new FailNode();
                        failNode.setShowErrorPage(true);
                        failNode.getFailureData().put("errorCode", "18013");
                        failNode.getFailureData().put("errorMessage",
                                "Script execution failed: " + evalResult.getErrorMessage());
                        failNode.getFailureData().put("errorType",
                                evalResult.getErrorType() != null ? evalResult.getErrorType() : "ScriptError");
                        attachToLeaf(executingNode, failNode);
                    }
                } finally {
                    optionalScriptExecutionData = Optional.ofNullable(endScriptExecutionMonitor(identifier));
                }
                optionalScriptExecutionData.ifPresent(
                        scriptExecutionData ->
                                storeAuthScriptExecutionMonitorData(authenticationContext,
                                scriptExecutionData));

                // Propagate accumulated dynamicallyBuiltBaseNode from gRPC threads to main thread.
                // In remote mode, gRPC callbacks built dynamic nodes (via executeStepInAsyncEvent,
                // failAsync, sendErrorAsync) on separate threads. The accumulated value was
                // persisted in RemoteJsEngine across those calls. Set it on the main thread's
                // ThreadLocal so canInfuse/infuse work the same as in local mode.
                if (jsEngine instanceof RemoteJsEngine) {
                    AuthGraphNode accumulated = ((RemoteJsEngine) jsEngine).getAccumulatedDynamicBaseNode();
                    if (accumulated != null) {
                        dynamicallyBuiltBaseNode.set(accumulated);
                        log.info("[evaluateRemote] Propagated accumulated dynamicBaseNode to main thread: " +
                                accumulated.getClass().getSimpleName());
                    }
                }

                AuthGraphNode executingNode = (AuthGraphNode) authenticationContext.getProperty(PROP_CURRENT_NODE);
                if (canInfuse(executingNode)) {
                    infuse(executingNode, dynamicallyBuiltBaseNode.get());
                }

            } catch (Throwable e) {
                // We need to catch all errors here, then log and handle.
                log.error("Error in remote JavaScript execution for service provider : " +
                        authenticationContext.getServiceProviderName() + ", Javascript Fragment : \n" +
                        jsFunction.getSource(), e);
                AuthGraphNode executingNode = (AuthGraphNode) authenticationContext.getProperty(PROP_CURRENT_NODE);
                // Create a proper FailNode with error information so user sees error page
                FailNode failNode = new FailNode();
                failNode.setShowErrorPage(true);
                failNode.getFailureData().put("errorCode", "18013");
                String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getName();
                failNode.getFailureData().put("errorMessage", "Script execution error: " + errorMessage);
                failNode.getFailureData().put("errorType", e.getClass().getSimpleName());
                attachToLeaf(executingNode, failNode);
            } finally {
                contextForJs.remove();
                dynamicallyBuiltBaseNode.remove();
            }
            return result;
        }

        /**
         * Execute JavaScript locally in the same JVM.
         * This is the original execution path.
         *
         * @param authenticationContext The authentication context.
         * @param params                The parameters to pass to the function.
         * @return The result of the execution.
         */
        private Object evaluateLocal(AuthenticationContext authenticationContext, Object... params) {

            JsGraalGraphBuilder graphBuilder = JsGraalGraphBuilder.this;
            Object result = null;
            try {
                currentBuilder.set(graphBuilder);
                JsGraalGraphBuilderFactory.restoreCurrentContext(authenticationContext, context);
                Context context = getContext();
                Value bindings = context.getBindings(POLYGLOT_LANGUAGE);

                bindings.putMember(JS_FUNC_EXECUTE_STEP, new JsGraalStepExecuterInAsyncEvent());
                bindings.putMember(JS_FUNC_SEND_ERROR, new SendErrorAsyncFunctionImpl());
                bindings.putMember(JS_AUTH_FAILURE, new FailAuthenticationFunctionImpl());
                bindings.putMember(JS_FUNC_SHOW_PROMPT, new JsGraalPromptExecutorImpl());
                bindings.putMember(JS_FUNC_LOAD_FUNC_LIB, new JsGraalLoadExecutorImpl());
                bindings.putMember(JS_FUNC_GET_SECRET_BY_NAME, new JsGraalGetSecretImpl());
                /*
                 * TODO: Need to improve the JsSerializable implementation to persist this
                 * function in the context
                 * without re-evaluating.
                 */
                context.eval(Source
                        .newBuilder(POLYGLOT_LANGUAGE,
                                FrameworkServiceDataHolder.getInstance().getCodeForSecretsFunction(),
                                POLYGLOT_SOURCE)
                        .build());
                JsFunctionRegistry jsFunctionRegistrar = FrameworkServiceDataHolder.getInstance()
                        .getJsFunctionRegistry();
                if (jsFunctionRegistrar != null) {
                    Map<String, Object> functionMap = jsFunctionRegistrar
                            .getSubsystemFunctionsMap(JsFunctionRegistry.Subsystem.SEQUENCE_HANDLER);
                    functionMap.forEach(bindings::putMember);
                }
                removeDefaultFunctions(context);
                JsGraalGraphBuilder.contextForJs.set(authenticationContext);

                String identifier = UUID.randomUUID().toString();
                Optional<JSExecutionMonitorData> optionalScriptExecutionData = Optional
                        .ofNullable(retrieveAuthScriptExecutionMonitorData(authenticationContext));
                try {
                    startScriptExecutionMonitor(identifier, authenticationContext,
                            optionalScriptExecutionData.orElse(null));
                    result = jsFunction.apply(context, params);
                } finally {
                    optionalScriptExecutionData = Optional.ofNullable(endScriptExecutionMonitor(identifier));
                }
                optionalScriptExecutionData.ifPresent(
                        scriptExecutionData ->
                                storeAuthScriptExecutionMonitorData(authenticationContext,
                                scriptExecutionData));

                JsGraalGraphBuilderFactory.persistCurrentContext(authenticationContext, context);

                AuthGraphNode executingNode = (AuthGraphNode) authenticationContext.getProperty(PROP_CURRENT_NODE);
                if (canInfuse(executingNode)) {
                    infuse(executingNode, dynamicallyBuiltBaseNode.get());
                }

            } catch (Throwable e) {
                // We need to catch all the javascript errors here, then log and handle.
                log.error("Error in executing the javascript for service provider : " +
                        authenticationContext.getServiceProviderName() + ", Javascript Fragment : \n" +
                        jsFunction.getSource(), e);
                AuthGraphNode executingNode = (AuthGraphNode) authenticationContext.getProperty(PROP_CURRENT_NODE);
                // Create a proper FailNode with error information so user sees error page
                FailNode failNode = new FailNode();
                failNode.setShowErrorPage(true);
                failNode.getFailureData().put("errorCode", "18013");
                String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getName();
                failNode.getFailureData().put("errorMessage", "Script execution error: " + errorMessage);
                failNode.getFailureData().put("errorType", e.getClass().getSimpleName());
                attachToLeaf(executingNode, failNode);
            } finally {
                contextForJs.remove();
                dynamicallyBuiltBaseNode.remove();
                clearCurrentBuilder(context);
            }
            return result;
        }
    }

    private Context getContext() {

        return this.context;
    }

    /**
     * Adds a function to show a prompt in Javascript code.
     *
     * @param templateId Identifier of the template
     * @param parameters parameters
     */
    @SuppressWarnings("unchecked")
    @HostAccess.Export
    public void addShowPrompt(String templateId, Object... parameters) {

        ShowPromptNode newNode = new ShowPromptNode();
        newNode.setTemplateId(templateId);

        if (parameters.length == 2) {
            newNode.setData((Map<String, Serializable>) GraalSerializer.getInstance().toJsSerializable(parameters[0]));
        }
        if (currentNode == null) {
            result.setStartNode(newNode);
        } else {
            attachToLeaf(currentNode, newNode);
        }

        currentNode = newNode;
        if (parameters.length > 0) {
            if (parameters[parameters.length - 1] instanceof Map) {
                addEventListeners(newNode, (Map<String, Object>) parameters[parameters.length - 1]);
            } else {
                log.error("Invalid argument and hence ignored. Last argument should be a Map of event listeners.");
            }

        }
    }

    /**
     * GraalJS specific prompt implementation
     */
    public class JsGraalPromptExecutorImpl implements PromptExecutor {

        @HostAccess.Export
        public void prompt(String templateId, Object... parameters) {

            JsGraalGraphBuilder.this.addShowPrompt(templateId, parameters);
        }
    }

    private boolean canInfuse(AuthGraphNode executingNode) {

        return executingNode instanceof DynamicDecisionNode && dynamicallyBuiltBaseNode.get() != null;
    }

    @HostAccess.Export
    public static void failAsync(Object... parameters) {

        Map<String, Object> parameterMap;

        if (parameters.length == 1) {
            parameterMap = (Map<String, Object>) parameters[0];
        } else {
            parameterMap = Collections.EMPTY_MAP;
        }

        FailNode newNode = createFailNode(StringUtils.EMPTY, parameterMap, false);

        AuthGraphNode currentNode = dynamicallyBuiltBaseNode.get();
        if (currentNode == null) {
            dynamicallyBuiltBaseNode.set(newNode);
        } else {
            attachToLeaf(currentNode, newNode);
        }
    }

    /**
     * Fail function implementation for GraalJS.
     */
    public static class FailAuthenticationFunctionImpl implements FailAuthenticationFunction {

        @HostAccess.Export
        public void fail(Object... parameters) {

            failAsync(parameters);
        }
    }

    private void removeDefaultFunctions(Context context) throws IOException {

        context.eval(Source.newBuilder(POLYGLOT_LANGUAGE, REMOVE_FUNCTIONS, POLYGLOT_SOURCE).build());
    }

    private JSExecutionSupervisor getJSExecutionSupervisor() {

        return FrameworkServiceDataHolder.getInstance().getJsExecutionSupervisor();
    }

    private void storeAuthScriptExecutionMonitorData(AuthenticationContext context,
            JSExecutionMonitorData jsExecutionMonitorData) {

        context.setProperty(PROP_EXECUTION_SUPERVISOR_RESULT, jsExecutionMonitorData);
    }

    private JSExecutionMonitorData retrieveAuthScriptExecutionMonitorData(AuthenticationContext context) {

        JSExecutionMonitorData jsExecutionMonitorData;
        Object storedResult = context.getProperty(PROP_EXECUTION_SUPERVISOR_RESULT);
        if (storedResult != null) {
            jsExecutionMonitorData = (JSExecutionMonitorData) storedResult;
        } else {
            jsExecutionMonitorData = new JSExecutionMonitorData(0L, 0L);
        }
        return jsExecutionMonitorData;
    }

    private void startScriptExecutionMonitor(String identifier, AuthenticationContext context,
            JSExecutionMonitorData previousExecutionResult) {

        JSExecutionSupervisor jsExecutionSupervisor = getJSExecutionSupervisor();
        if (jsExecutionSupervisor == null) {
            return;
        }
        getJSExecutionSupervisor().monitor(identifier, context.getServiceProviderName(), context.getTenantDomain(),
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
        return getJSExecutionSupervisor().completed(identifier);
    }

}
