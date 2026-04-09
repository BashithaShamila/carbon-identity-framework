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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.graalvm.polyglot.HostAccess;
import org.wso2.carbon.identity.application.authentication.framework.AuthenticationDecisionEvaluator;
import org.wso2.carbon.identity.application.authentication.framework.JsFunctionRegistry;
import org.wso2.carbon.identity.application.authentication.framework.config.model.StepConfig;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.AuthGraphNode;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.BaseSerializableJsFunction;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.DynamicDecisionNode;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.FailNode;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.GenericSerializableJsFunction;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.JSExecutionMonitorData;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.EvaluationResult;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.JsEngine;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.JsEngineFactory;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.internal.FrameworkServiceDataHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_AUTH_FAILURE;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_FUNC_EXECUTE_STEP;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_FUNC_GET_SECRET_BY_NAME;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_FUNC_LOAD_FUNC_LIB;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_FUNC_ON_LOGIN_REQUEST;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_FUNC_SEND_ERROR;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_FUNC_SHOW_PROMPT;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.PROP_CURRENT_NODE;

/**
 * Remote execution graph builder for adaptive authentication.
 * This builder handles script evaluation and callback execution via the external GraalJS sidecar
 * process over gRPC, while inheriting all graph-building logic (executeStep, sendError, showPrompt,
 * addEventListeners, infuse, etc.) from the parent JsGraalGraphBuilder.
 * <p>
 * Instances are created by JsGraalGraphBuilderFactory when execution mode is REMOTE.
 * Each login session gets its own builder instance (not thread safe, discarded after each build).
 */
public class RemoteJsGraalGraphBuilder extends JsGraalGraphBuilder {

    private static final Log log = LogFactory.getLog(RemoteJsGraalGraphBuilder.class);

    /**
     * Constructs the remote builder for initial script evaluation (createWith path).
     *
     * @param authenticationContext current authentication context.
     * @param stepConfigMap         The Step map from the service provider configuration.
     */
    public RemoteJsGraalGraphBuilder(AuthenticationContext authenticationContext,
                                     Map<Integer, StepConfig> stepConfigMap) {

        super(authenticationContext, stepConfigMap, null);
    }

    /**
     * Constructs the remote builder for callback evaluation (getScriptEvaluator path).
     *
     * @param authenticationContext current authentication context.
     * @param stepConfigMap         The Step map from the service provider configuration.
     * @param currentNode           Current authentication graph node.
     */
    public RemoteJsGraalGraphBuilder(AuthenticationContext authenticationContext,
                                     Map<Integer, StepConfig> stepConfigMap,
                                     AuthGraphNode currentNode) {

        super(authenticationContext, stepConfigMap, null, currentNode);
    }

    /**
     * Creates the graph with the given Script using remote External execution.
     * This method sends the script to the External sidecar for evaluation and processes
     * callback results including host function invocations (executeStep, sendError, etc.).
     *
     * @param script the Dynamic authentication script.
     * @return This builder.
     */
    @Override
    @SuppressWarnings("unchecked")
    public JsGraalGraphBuilder createWith(String script) {

        try (JsEngine jsEngine = JsEngineFactory.getInstance().createEngine(authenticationContext)) {
            currentBuilder.set(this);
            contextForJs.set(authenticationContext);

            if (log.isDebugEnabled()) {
                log.debug("[createWithRemote] Starting for SP: " + authenticationContext.getServiceProviderName() +
                        ", contextId: " + authenticationContext.getContextIdentifier());
            }

            // Register host functions that the External can call back.
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
            if (log.isDebugEnabled()) {
                log.debug("[createWithRemote] Registered " + hostFunctions.size() + " host functions: " +
                        hostFunctions.keySet());
            }

            // Build the complete script including require function, secrets, and main
            // script.
            String completeScript = FrameworkServiceDataHolder.getInstance().getCodeForRequireFunction() +
                    "\n" +
                    FrameworkServiceDataHolder.getInstance().getCodeForSecretsFunction() +
                    "\n" +
                    script +
                    "\n" +
                    // Call onLoginRequest with context placeholder - External will inject actual
                    // context.
                    JS_FUNC_ON_LOGIN_REQUEST + "(context);";

            if (log.isDebugEnabled()) {
                log.debug("[createWithRemote] Sending script (length: " + completeScript.length() +
                        ") to External for evaluation");
            }

            // Build initial bindings (context will be created by External from ContextData).
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

                if (log.isDebugEnabled()) {
                    log.debug("[createWithRemote] Script evaluation successful, elapsed: " +
                            evalResult.getElapsedMs() + "ms");
                }

                // Update bindings from External response.
                if (evalResult.getUpdatedBindings() != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("[createWithRemote] Updating bindings from External: " +
                                evalResult.getUpdatedBindings().keySet());
                    }
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

            if (log.isDebugEnabled()) {
                log.debug("[createWithRemote] Script execution completed for SP: " +
                        authenticationContext.getServiceProviderName());
            }

            // Persist bindings for later callback execution.
            // Note: With remote execution, we persist the updated bindings from External.
            Map<String, Object> persistableBindings = jsEngine.getBindings();
            authenticationContext.setProperty("JS_BINDING_CURRENT_CONTEXT", persistableBindings);
            if (log.isDebugEnabled()) {
                log.debug("[createWithRemote] Persisted " + persistableBindings.size() + " bindings");
            }

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

    @Override
    public AuthenticationDecisionEvaluator getScriptEvaluator(GenericSerializableJsFunction fn) {

        return new RemoteJsBasedEvaluator((GraalSerializableJsFunction) fn);
    }

    /**
     * Remote JavaScript Decision Evaluator implementation.
     * This handles callback execution (e.g., onSuccess/onFail after a step completes)
     * by sending the serialized function to the external sidecar for evaluation.
     * The graph is re-organized based on the execution result, exactly as the local evaluator does.
     */
    public class RemoteJsBasedEvaluator implements AuthenticationDecisionEvaluator {

        private static final long serialVersionUID = 6853505881096840345L;
        private final GraalSerializableJsFunction jsFunction;

        public RemoteJsBasedEvaluator(GraalSerializableJsFunction jsFunction) {

            this.jsFunction = jsFunction;
        }

        @Override
        @HostAccess.Export
        @SuppressWarnings("unchecked")
        public Object evaluate(AuthenticationContext authenticationContext, Object... params) {

            RemoteJsGraalGraphBuilder graphBuilder = RemoteJsGraalGraphBuilder.this;
            Object result = null;
            if (jsFunction == null) {
                return null;
            }
            if (!jsFunction.isFunction()) {
                return jsFunction.getSource();
            }

            try (JsEngine jsEngine = JsEngineFactory.getInstance().createEngine(authenticationContext)) {
                currentBuilder.set(graphBuilder);
                JsGraalGraphBuilder.contextForJs.set(authenticationContext);

                // Log context info for debugging.
                if (log.isDebugEnabled()) {
                    log.debug("[evaluateRemote] Starting for SP: " + authenticationContext.getServiceProviderName() +
                            ", contextId: " + authenticationContext.getContextIdentifier() +
                            ", step: " + authenticationContext.getCurrentStep() +
                            ", authContext hashCode: " + System.identityHashCode(authenticationContext));
                }

                // Get persisted bindings from authentication context (variables like
                // rolesToStepUp).
                Map<String, Object> persistedBindings = (Map<String, Object>) authenticationContext
                        .getProperty("JS_BINDING_CURRENT_CONTEXT");
                if (persistedBindings != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("[evaluateRemote] Found " + persistedBindings.size() +
                                " persisted bindings: " + persistedBindings.keySet());
                    }
                    // Log each binding value for debugging.
                    for (Map.Entry<String, Object> entry : persistedBindings.entrySet()) {
                        if (log.isDebugEnabled()) {
                            log.debug("[evaluateRemote] Binding: " + entry.getKey() + " = " +
                                    (entry.getValue() != null
                                            ? entry.getValue().getClass().getSimpleName() + ": " + entry.getValue()
                                            : "null"));
                        }
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("[evaluateRemote] No persisted bindings found in authContext. " +
                                "Property keys: " + authenticationContext.getProperties().keySet());
                    }
                    persistedBindings = new HashMap<>();
                }

                // Register host functions that the External can call back.
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

                    // Reset dynamicallyBuiltBaseNode before callback execution.
                    // Each callback cycle starts with a clean slate, matching local mode
                    // where dynamicallyBuiltBaseNode starts null at callback entry.
                    // Thread A runs callbacks inline now, so we clear the ThreadLocal directly.
                    dynamicallyBuiltBaseNode.remove();

                    // Execute the callback function in the External with persisted bindings
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
                        if (log.isDebugEnabled()) {
                            log.debug("[evaluateRemote] Re-persisted " + updatedBindings.size() +
                                    " bindings after callback");
                        }

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

                // dynamicallyBuiltBaseNode is already on Thread A -- callbacks ran inline
                // via the message loop, so no cross-thread propagation is needed.
                // canInfuse/infuse read the ThreadLocal directly.
                AuthGraphNode executingNode = (AuthGraphNode) authenticationContext.getProperty(PROP_CURRENT_NODE);
                if (canInfuse(executingNode)) {
                    infuse(executingNode, dynamicallyBuiltBaseNode.get());
                }

            } catch (Throwable e) {
                log.error("Error in remote JavaScript execution for service provider : " +
                        authenticationContext.getServiceProviderName() + ", Javascript Fragment : \n" +
                        jsFunction.getSource(), e);
                AuthGraphNode executingNode = (AuthGraphNode) authenticationContext.getProperty(PROP_CURRENT_NODE);
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
                clearCurrentBuilder();
            }
            return result;
        }
    }
}
