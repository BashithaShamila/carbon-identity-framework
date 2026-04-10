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
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.ResourceLimits;
import org.graalvm.polyglot.Value;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticatedUser;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsParameters;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsServletRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsServletResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticatedUser;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.server.GrpcTransportProvider;
import org.wso2.carbon.identity.application.authentication.framework.internal.FrameworkServiceDataHolder;
import org.wso2.carbon.identity.core.util.IdentityUtil;

import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.DEFAULT_ENGINE_MODE;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.DEFAULT_GRAALJS_SCRIPT_STATEMENTS_LIMIT;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.DEFAULT_GRPC_TARGET;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.GRAALJS_ENGINE_MODE;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.GRAALJS_GRPC_TARGET;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.GRAALJS_SCRIPT_STATEMENTS_LIMIT;
/**
 * Factory for creating JavaScript engines.
 * Supports LOCAL (in-JVM), REMOTE (External via gRPC), and HYBRID (per-request routing) modes.
 * <p>
 * Engine mode can be configured in deployment.toml:
 * <pre>
 * [authentication.adaptive.graaljs]
 * engine_mode = "REMOTE"              # "LOCAL", "REMOTE", or "HYBRID"
 * grpc_target = "localhost:50051"
 * </pre>
 * <p>
 * In HYBRID mode, the engine selection is delegated to a {@link ScriptEngineModeResolver}
 * OSGi service, which can be customized by dropping a bundle into the dropins folder.
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
         * Execute JavaScript in a remote External process via gRPC.
         */
        REMOTE
    }

    /**
     * Engine mode configuration value.
     */
    public enum EngineMode {
        /**
         * All requests use the local in-JVM GraalJS engine.
         */
        LOCAL,
        /**
         * All requests use the remote External engine via gRPC.
         */
        REMOTE,
        /**
         * Per-request routing delegated to a {@link ScriptEngineModeResolver} OSGi service.
         */
        HYBRID
    }

    // Configured engine mode.
    private EngineMode engineMode = EngineMode.REMOTE;

    // Default gRPC target for remote engine (host:port).
    private String grpcTarget = DEFAULT_GRPC_TARGET;

    // Statement limit for local engine.
    private int javascriptResourceLimit = DEFAULT_GRAALJS_SCRIPT_STATEMENTS_LIMIT;

    // Lazy singleton holder — JsEngineFactory is only created when getInstance() is first called.
    // This ensures initializeFromConfig() runs after IdentityUtil has been populated.
    private static volatile JsEngineFactory instance;

    private JsEngineFactory() {

        initializeFromConfig();
    }

    /**
     * Get the singleton instance. Uses lazy initialization to ensure config is available.
     *
     * @return JsEngineFactory instance.
     */
    public static JsEngineFactory getInstance() {

        if (instance == null) {
            synchronized (JsEngineFactory.class) {
                if (instance == null) {
                    instance = new JsEngineFactory();
                }
            }
        }
        return instance;
    }

    /**
     * Create a JavaScript engine for the given authentication context.
     * This is only called from remote execution code paths.
     *
     * @param authenticationContext The authentication context.
     * @return A JsEngine instance configured for remote execution.
     */
    public JsEngine createEngine(AuthenticationContext authenticationContext) {

        return createRemoteEngine(authenticationContext);
    }

    /**
     * Create a remote (External) JavaScript engine.
     * Uses the embedded gRPC transport provider directly within the same bundle.
     *
     * @param authenticationContext The authentication context.
     * @return RemoteJsEngine instance.
     */
    public RemoteJsEngine createRemoteEngine(AuthenticationContext authenticationContext) {

        RemoteEngineTransport transport = GrpcTransportProvider.getOrCreateTransport(grpcTarget);

        if (log.isDebugEnabled()) {
            log.debug("[JsEngineFactory] Created remote engine with transport: " +
                    transport.getClass().getSimpleName() + ", target: " + grpcTarget);
        }
        return new RemoteJsEngine(transport, authenticationContext);
    }

    /**
     * Get the configured engine mode.
     *
     * @return Current EngineMode.
     */
    public EngineMode getEngineMode() {

        return engineMode;
    }

    /**
     * Get the current static execution mode.
     *
     * @return Current ExecutionMode based on engine mode (LOCAL for LOCAL, REMOTE for REMOTE/HYBRID).
     */
    public static ExecutionMode getCurrentMode() {

        if (getInstance().engineMode == EngineMode.LOCAL) {
            return ExecutionMode.LOCAL;
        }
        return ExecutionMode.REMOTE;
    }

    /**
     * Get the gRPC target address.
     *
     * @return gRPC target string (host:port).
     */
    public static String getGrpcTarget() {

        return getInstance().grpcTarget;
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

        if (engineMode == EngineMode.LOCAL) {
            return ExecutionMode.LOCAL;
        }
        return ExecutionMode.REMOTE;
    }

    /**
     * Resolve the execution mode for a given authentication context.
     * For LOCAL/REMOTE modes, returns the configured mode directly.
     * For HYBRID mode, delegates to the {@link ScriptEngineModeResolver} OSGi service.
     * This is the public API that callers (e.g., JsGraalGraphBuilder) should use
     * to determine which code path (local vs remote) to follow.
     *
     * @param authenticationContext The authentication context (may be null).
     * @return The resolved ExecutionMode for this request.
     */
    public ExecutionMode resolveMode(AuthenticationContext authenticationContext) {

        return resolveExecutionMode(authenticationContext);
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

    /**
     * Resolve which execution mode to use for a given authentication context.
     * <ul>
     *   <li>LOCAL mode: always returns LOCAL.</li>
     *   <li>REMOTE mode: always returns REMOTE.</li>
     *   <li>HYBRID mode: delegates to the {@link ScriptEngineModeResolver} OSGi service.
     *       Falls back to LOCAL if no resolver is available.</li>
     * </ul>
     *
     * @param authenticationContext The authentication context (may be null).
     * @return The resolved ExecutionMode.
     */
    private ExecutionMode resolveExecutionMode(AuthenticationContext authenticationContext) {

        switch (engineMode) {
            case LOCAL:
                return ExecutionMode.LOCAL;
            case REMOTE:
                return ExecutionMode.REMOTE;
            case HYBRID:
                return resolveHybridMode(authenticationContext);
            default:
                return ExecutionMode.REMOTE;
        }
    }

    /**
     * Resolve execution mode in HYBRID mode by delegating to the OSGi resolver service.
     *
     * @param authenticationContext The authentication context (may be null).
     * @return The resolved ExecutionMode.
     */
    private ExecutionMode resolveHybridMode(AuthenticationContext authenticationContext) {

        ScriptEngineModeResolver resolver =
                FrameworkServiceDataHolder.getInstance().getScriptEngineModeResolver();

        if (resolver == null) {
            log.warn("[JsEngineFactory] HYBRID mode configured but no ScriptEngineModeResolver " +
                    "OSGi service found. Falling back to LOCAL.");
            return ExecutionMode.LOCAL;
        }

        ExecutionMode resolved = resolver.resolve(authenticationContext);
        if (log.isDebugEnabled()) {
            log.debug("[JsEngineFactory] HYBRID mode resolved to: " + resolved +
                    " for SP: " + (authenticationContext != null ?
                    authenticationContext.getServiceProviderName() : "null"));
        }
        return resolved;
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

        // Read engine mode (LOCAL, REMOTE, or HYBRID)
        String mode = IdentityUtil.getProperty(GRAALJS_ENGINE_MODE);
        if (mode != null && !mode.isEmpty()) {
            try {
                engineMode = EngineMode.valueOf(mode.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("Unknown engine mode: '" + mode + "'. Defaulting to " + DEFAULT_ENGINE_MODE);
                engineMode = EngineMode.valueOf(DEFAULT_ENGINE_MODE);
            }
        } else {
            engineMode = EngineMode.valueOf(DEFAULT_ENGINE_MODE);
        }

        // Read gRPC target
        String target = IdentityUtil.getProperty(GRAALJS_GRPC_TARGET);
        if (target != null && !target.isEmpty()) {
            grpcTarget = target.trim();
        }

        log.info("JsEngineFactory initialized. EngineMode: " + engineMode +
                ", gRPC Target: " + grpcTarget);
    }
}
