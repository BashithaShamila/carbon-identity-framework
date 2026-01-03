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
import org.wso2.carbon.identity.application.authentication.framework.config.model.StepConfig;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.AuthGraphNode;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.JsBaseGraphBuilder;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.JsGenericGraphBuilderFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.JsGenericSerializer;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.exception.FrameworkException;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory to create a WASM-based JavaScript sequence builder.
 * This factory creates and manages WasmRuntime instances for evaluating
 * authentication scripts.
 */
public class JsWasmGraphBuilderFactory implements JsGenericGraphBuilderFactory<WasmRuntime> {

    private static final Log LOG = LogFactory.getLog(JsWasmGraphBuilderFactory.class);
    private static final String JS_BINDING_CURRENT_CONTEXT = "JS_WASM_BINDING_CURRENT_CONTEXT";

    private WasmSerializer serializer;

    /**
     * Initializes the factory.
     */
    public void init() {
        this.serializer = new WasmSerializer();
        LOG.debug("JsWasmGraphBuilderFactory initialized");
    }

    /**
     * Creates a new WasmRuntime for the given authentication context.
     * Note: WasmRuntime cannot be persisted in AuthenticationContext because it's
     * not serializable.
     * A new runtime is created for each authentication step.
     *
     * @param authenticationContext The authentication context.
     * @return A new WasmRuntime instance.
     */
    @Override
    public WasmRuntime createEngine(AuthenticationContext authenticationContext) {
        WasmRuntime runtime = new WasmRuntime();

        // Register host functions for JavaScript callbacks
        registerHostFunctions(runtime, authenticationContext);

        return runtime;
    }

    /**
     * Registers host functions that can be called from JavaScript.
     */
    private void registerHostFunctions(WasmRuntime runtime, AuthenticationContext context) {
        // Register logging function
        runtime.registerHostFunction("log", (name, args) -> {
            if (args != null && args.length > 0) {
                LOG.info("[JS] " + args[0]);
            }
            return null;
        });

        // Register error logging function
        runtime.registerHostFunction("logError", (name, args) -> {
            if (args != null && args.length > 0) {
                LOG.error("[JS] " + args[0]);
            }
            return null;
        });

        // Register debug logging function
        runtime.registerHostFunction("logDebug", (name, args) -> {
            if (args != null && args.length > 0) {
                LOG.debug("[JS] " + args[0]);
            }
            return null;
        });
    }

    /**
     * Creates a JsWasmGraphBuilder for building authentication graphs.
     *
     * @param authenticationContext The authentication context.
     * @param stepConfigMap         The step configuration map.
     * @return A new JsWasmGraphBuilder instance.
     */
    public JsWasmGraphBuilder createBuilder(AuthenticationContext authenticationContext,
            Map<Integer, StepConfig> stepConfigMap) {
        return new JsWasmGraphBuilder(authenticationContext, stepConfigMap, createEngine(authenticationContext));
    }

    /**
     * Creates a JsWasmGraphBuilder with an existing current node.
     *
     * @param authenticationContext The authentication context.
     * @param stepConfigMap         The step configuration map.
     * @param currentNode           The current authentication graph node.
     * @return A new JsWasmGraphBuilder instance.
     */
    public JsWasmGraphBuilder createBuilder(AuthenticationContext authenticationContext,
            Map<Integer, StepConfig> stepConfigMap, AuthGraphNode currentNode) {
        return new JsWasmGraphBuilder(authenticationContext, stepConfigMap,
                createEngine(authenticationContext), currentNode);
    }

    /**
     * Restores the current context from the authentication context.
     *
     * @param authContext The authentication context.
     * @param runtime     The WASM runtime.
     * @throws FrameworkException If restoration fails.
     */
    @SuppressWarnings("unchecked")
    public static void restoreCurrentContext(AuthenticationContext authContext, WasmRuntime runtime)
            throws FrameworkException {
        Map<String, Object> map = (Map<String, Object>) authContext.getProperty(JS_BINDING_CURRENT_CONTEXT);
        if (map != null) {
            // For WASM runtime, we'll need to re-inject any saved context values
            // This is handled differently than GraalJS since we're using a different
            // execution model
            LOG.debug("Restoring WASM context with " + map.size() + " bindings");
        }
    }

    /**
     * Persists the current context to the authentication context.
     *
     * @param authContext The authentication context.
     * @param runtime     The WASM runtime.
     */
    public static void persistCurrentContext(AuthenticationContext authContext, WasmRuntime runtime) {
        Map<String, Object> persistableMap = new HashMap<>();
        // For WASM runtime, we persist any context that needs to survive across
        // requests
        // This is handled differently than GraalJS
        authContext.setProperty(JS_BINDING_CURRENT_CONTEXT, persistableMap);
    }

    @Override
    public JsGenericSerializer<WasmRuntime> getJsUtil() {
        if (serializer == null) {
            serializer = new WasmSerializer();
        }
        return serializer;
    }

    @Override
    public JsBaseGraphBuilder getCurrentBuilder() {
        return JsWasmGraphBuilder.getCurrentBuilder();
    }
}
