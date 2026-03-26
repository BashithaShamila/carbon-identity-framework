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
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.ResourceLimits;
import org.graalvm.polyglot.Value;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticatedUser;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsLogger;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsParameters;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsServletRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsServletResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticatedUser;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.handler.sequence.impl.GraalSelectAcrFromFunction;
import org.wso2.carbon.identity.core.util.IdentityUtil;

import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.DEFAULT_GRAALJS_SCRIPT_STATEMENTS_LIMIT;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.GRAALJS_SCRIPT_STATEMENTS_LIMIT;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_FUNC_SELECT_ACR_FROM;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_LOG;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.POLYGLOT_LANGUAGE;

/**
 * Factory for creating JavaScript engines.
 * Supports both local (in-JVM) and remote (sidecar) execution modes.
 * <p>
 * Current implementation defaults to REMOTE mode for all users.
 * Future versions will support dynamic routing based on user/tenant
 * configuration.
 */
public class JsEngineFactory {

    private static final Log log = LogFactory.getLog(JsEngineFactory.class);

    /**
     * Execution mode for JavaScript engine.
     */
    public enum ExecutionMode {
        /**
         * Execute JavaScript locally in the same JVM using GraalVM Polyglot.
         */
        LOCAL,
        /**
         * Execute JavaScript in a remote sidecar process via gRPC.
         */
        REMOTE
    }

    // Current execution mode - defaults to REMOTE (sidecar via gRPC)
    private static ExecutionMode currentMode = ExecutionMode.REMOTE;

    // Default gRPC target for remote engine (host:port)
    private static String grpcTarget = "localhost:50051";

    // Statement limit for local engine
    private static int javascriptResourceLimit = DEFAULT_GRAALJS_SCRIPT_STATEMENTS_LIMIT;

    // Singleton instance
    private static final JsEngineFactory INSTANCE = new JsEngineFactory();

    private JsEngineFactory() {
        initializeFromConfig();
    }

    /**
     * Get the singleton instance.
     *
     * @return JsEngineFactory instance.
     */
    public static JsEngineFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Create a JavaScript engine for the given authentication context.
     *
     * @param authenticationContext The authentication context.
     * @return A JsEngine instance configured for the current execution mode.
     */
    public JsEngine createEngine(AuthenticationContext authenticationContext) {
        // For now, all users use the same mode (REMOTE)
        // Future: Add logic for dynamic routing based on user/tenant
        if (currentMode == ExecutionMode.LOCAL) {
            return createLocalEngine(authenticationContext);
        } else {
            return createRemoteEngine(authenticationContext);
        }
    }

    /**
     * Create a local (in-JVM) JavaScript engine.
     *
     * @param authenticationContext The authentication context.
     * @return LocalJsEngine instance.
     */
    public LocalJsEngine createLocalEngine(AuthenticationContext authenticationContext) {
        Context context = Context.newBuilder(POLYGLOT_LANGUAGE)
                .allowHostAccess(getHostAccess())
                .resourceLimits(getResourceLimits())
                .option("engine.WarnInterpreterOnly", "false")
                .build();

        // Set up default bindings
        Value bindings = context.getBindings(POLYGLOT_LANGUAGE);
        bindings.putMember(JS_FUNC_SELECT_ACR_FROM, new GraalSelectAcrFromFunction());
        bindings.putMember(JS_LOG, new JsLogger());

        return new LocalJsEngine(context);
    }

    /**
     * Create a remote (sidecar) JavaScript engine.
     * Uses the transport factory to create appropriate transport and callback server implementations.
     *
     * @param authenticationContext The authentication context.
     * @return RemoteJsEngine instance.
     */
    public RemoteJsEngine createRemoteEngine(AuthenticationContext authenticationContext) {
        // Create transport configuration based on current settings
        TransportConfig config = createTransportConfig();

        // Use factory to create transport and callback server
        TransportFactory factory = TransportFactory.getInstance();
        RemoteEngineTransport transport = factory.createTransport(config);
        CallbackServer callbackServer = factory.createCallbackServer(config);

        log.debug("[JsEngineFactory] Created remote engine with transport: " + config.getType());
        return new RemoteJsEngine(transport, callbackServer, authenticationContext);
    }

    /**
     * Create transport configuration based on current execution mode and transport type.
     *
     * @return TransportConfig instance.
     */
    private TransportConfig createTransportConfig() {
        // gRPC transport via org.wso2.carbon.identity.application.authentication.framework.grpc OSGi bundle
        return TransportConfig.forGrpc(grpcTarget);
    }

    /**
     * Get the current execution mode.
     *
     * @return Current ExecutionMode.
     */
    public static ExecutionMode getCurrentMode() {
        return currentMode;
    }

    /**
     * Get the gRPC target address.
     *
     * @return gRPC target string (host:port).
     */
    public static String getGrpcTarget() {
        return grpcTarget;
    }

    /**
     * Set the JavaScript statement limit.
     *
     * @param limit The statement limit.
     */
    public void setStatementLimit(int limit) {
        javascriptResourceLimit = limit;
    }

    /**
     * Get the default execution mode.
     *
     * @return The default ExecutionMode.
     */
    public ExecutionMode getDefaultMode() {
        return currentMode;
    }

    /**
     * Get the resource limits for local engine execution.
     *
     * @return ResourceLimits instance.
     */
    public ResourceLimits getResourceLimits() {
        ResourceLimits.Builder resourceLimitsBuilder = ResourceLimits.newBuilder();
        resourceLimitsBuilder.statementLimit(javascriptResourceLimit, null);
        return resourceLimitsBuilder.build();
    }

    /**
     * Get the host access configuration for local engine.
     *
     * @return HostAccess instance.
     */
    public HostAccess getHostAccess() {
        return HostAccess.newBuilder(HostAccess.EXPLICIT)
                .allowListAccess(true)
                .targetTypeMapping(Value.class, JsAuthenticationContext.class,
                        (v) -> v.asProxyObject() instanceof JsAuthenticationContext,
                        (v) -> (JsAuthenticationContext) v.asProxyObject())
                .targetTypeMapping(Value.class, JsAuthenticatedUser.class,
                        (v) -> v.asProxyObject() instanceof JsGraalAuthenticatedUser,
                        (v) -> (JsAuthenticatedUser) v.asProxyObject())
                .targetTypeMapping(Value.class, JsServletRequest.class,
                        (v) -> v.asProxyObject() instanceof JsServletRequest,
                        (v) -> (JsServletRequest) v.asProxyObject())
                .targetTypeMapping(Value.class, JsServletResponse.class,
                        (v) -> v.asProxyObject() instanceof JsServletResponse,
                        (v) -> (JsServletResponse) v.asProxyObject())
                .targetTypeMapping(Value.class, JsParameters.class,
                        (v) -> v.asProxyObject() instanceof JsParameters,
                        (v) -> (JsParameters) v.asProxyObject())
                .build();
    }

    private void initializeFromConfig() {
        // Read statement limit from config
        String statementLimit = IdentityUtil.getProperty(GRAALJS_SCRIPT_STATEMENTS_LIMIT);
        if (statementLimit != null) {
            try {
                javascriptResourceLimit = Integer.parseInt(statementLimit);
            } catch (NumberFormatException e) {
                log.warn("Error parsing script statement limit. Defaulting to " +
                        DEFAULT_GRAALJS_SCRIPT_STATEMENTS_LIMIT, e);
                javascriptResourceLimit = DEFAULT_GRAALJS_SCRIPT_STATEMENTS_LIMIT;
            }
        }

        if (currentMode == ExecutionMode.REMOTE) {
            log.info("JsEngineFactory initialized. Mode: " + currentMode +
                    ", gRPC Target: " + grpcTarget);
        } else {
            log.info("JsEngineFactory initialized. Mode: " + currentMode);
        }
    }
}
