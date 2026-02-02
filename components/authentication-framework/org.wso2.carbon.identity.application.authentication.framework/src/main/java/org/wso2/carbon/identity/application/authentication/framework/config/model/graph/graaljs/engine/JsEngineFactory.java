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
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.impl.GrpcCallbackServerImpl;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.impl.GrpcTransportImpl;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.impl.UdsCallbackServerImpl;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.impl.UdsTransportImpl;
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
         * Execute JavaScript in a remote sidecar process via UDS/RPC.
         */
        REMOTE
    }

    /**
     * Transport type for remote JavaScript engine communication.
     */
    public enum TransportType {
        /**
         * Unix Domain Socket transport.
         */
        UDS,
        /**
         * gRPC transport.
         */
        GRPC
    }

    // Current execution mode - defaults to LOCAL (using Graal.js directly in IS)
    private static ExecutionMode currentMode = ExecutionMode.REMOTE;

    // Current transport type for remote mode - defaults to UDS
    private static TransportType currentTransportType = TransportType.UDS;

    // Default socket path for sidecar communication
    private static String sidecarSocketPath = "/tmp/graaljs-sidecar.sock";

    // Default gRPC target for remote engine (host:port)
    private static String grpcTarget = "localhost:50051";

    // Default gRPC callback server port (0 for automatic selection)
    private static int grpcCallbackPort = 0;

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
        // For now, always use UDS (default working implementation)
        // Future: Switch based on currentTransportType configuration
        return TransportConfig.forUds(sidecarSocketPath);

        // Future implementation when other transports are ready:
        /*
        switch (currentTransportType) {
            case GRPC:
                return TransportConfig.forGrpc(grpcTarget, grpcCallbackPort);
            case HTTP:
                return TransportConfig.forHttp(httpEndpoint, httpCallbackPort);
            case WEBSOCKET:
                return TransportConfig.forWebSocket(wsUrl, wsCallbackPort);
            case UDS:
            default:
                return TransportConfig.forUds(sidecarSocketPath);
        }
        */
    }

    /**
     * Create a UDS transport for remote engine communication.
     * NOTE: This method is deprecated. Use TransportFactory instead.
     *
     * @return UdsTransportImpl instance.
     * @deprecated Use TransportFactory.createTransport() instead.
     */
    @Deprecated
    private RemoteEngineTransport createUdsTransport() {
        return new UdsTransportImpl(sidecarSocketPath);
    }

    /**
     * Create a UDS callback server for receiving host function callbacks.
     * NOTE: This method is deprecated. Use TransportFactory instead.
     *
     * @return UdsCallbackServerImpl instance wrapping the singleton HostCallbackServer.
     * @deprecated Use TransportFactory.createCallbackServer() instead.
     */
    @Deprecated
    private CallbackServer createUdsCallbackServer() {
        return new UdsCallbackServerImpl();
    }

    /**
     * Create a gRPC transport for remote engine communication.
     * NOTE: This method is deprecated. Use TransportFactory instead.
     *
     * @return GrpcTransportImpl instance.
     * @deprecated Use TransportFactory.createTransport() instead.
     */
    @Deprecated
    private RemoteEngineTransport createGrpcTransport() {
        return new GrpcTransportImpl(grpcTarget);
    }

    /**
     * Create a gRPC callback server for receiving host function callbacks.
     * NOTE: This method is deprecated. Use TransportFactory instead.
     *
     * @return GrpcCallbackServerImpl instance.
     * @deprecated Use TransportFactory.createCallbackServer() instead.
     */
    @Deprecated
    private CallbackServer createGrpcCallbackServer() {
        return new GrpcCallbackServerImpl(grpcCallbackPort);
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
     * Set the execution mode.
     * Note: In future, this will be deprecated in favor of dynamic routing.
     *
     * @param mode The execution mode to set.
     */
    public static void setCurrentMode(ExecutionMode mode) {
        currentMode = mode;
        log.info("JavaScript engine execution mode set to: " + mode);
    }

    /**
     * Get the sidecar socket path.
     *
     * @return Socket path string.
     */
    public static String getSidecarSocketPath() {
        return sidecarSocketPath;
    }

    /**
     * Set the sidecar socket path.
     *
     * @param socketPath The socket path.
     */
    public static void setSidecarSocketPath(String socketPath) {
        sidecarSocketPath = socketPath;
    }

    /**
     * Get the current transport type.
     *
     * @return Current TransportType.
     */
    public static TransportType getCurrentTransportType() {
        return currentTransportType;
    }

    /**
     * Set the transport type for remote engine communication.
     *
     * @param transportType The transport type to set.
     */
    public static void setCurrentTransportType(TransportType transportType) {
        currentTransportType = transportType;
        log.info("JavaScript engine transport type set to: " + transportType);
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
     * Set the gRPC target address.
     *
     * @param target The gRPC target (host:port).
     */
    public static void setGrpcTarget(String target) {
        grpcTarget = target;
    }

    /**
     * Get the gRPC callback server port.
     *
     * @return gRPC callback port.
     */
    public static int getGrpcCallbackPort() {
        return grpcCallbackPort;
    }

    /**
     * Set the gRPC callback server port.
     *
     * @param port The callback server port (0 for automatic selection).
     */
    public static void setGrpcCallbackPort(int port) {
        grpcCallbackPort = port;
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

        // Future: Read execution mode, transport type, and connection settings from config
        if (currentMode == ExecutionMode.REMOTE) {
            if (currentTransportType == TransportType.GRPC) {
                log.info("JsEngineFactory initialized. Mode: " + currentMode +
                        ", Transport: " + currentTransportType + ", gRPC Target: " + grpcTarget);
            } else {
                log.info("JsEngineFactory initialized. Mode: " + currentMode +
                        ", Transport: " + currentTransportType + ", Socket: " + sidecarSocketPath);
            }
        } else {
            log.info("JsEngineFactory initialized. Mode: " + currentMode);
        }
    }
}
