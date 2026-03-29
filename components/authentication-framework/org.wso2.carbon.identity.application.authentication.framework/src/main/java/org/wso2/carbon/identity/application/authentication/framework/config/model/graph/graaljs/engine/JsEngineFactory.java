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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.DEFAULT_ENGINE_SELECTION_MODE;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.DEFAULT_GRAALJS_SCRIPT_STATEMENTS_LIMIT;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.DEFAULT_GRPC_TARGET;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.GRAALJS_DYNAMIC_ROUTING_FIELD;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.GRAALJS_DYNAMIC_ROUTING_REMOTE_VALUES;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.GRAALJS_ENGINE_SELECTION_MODE;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.GRAALJS_ENGINE_TYPE;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.GRAALJS_GRPC_TARGET;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AdaptiveAuthentication.GRAALJS_SCRIPT_STATEMENTS_LIMIT;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_FUNC_SELECT_ACR_FROM;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.JS_LOG;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.POLYGLOT_LANGUAGE;

/**
 * Factory for creating JavaScript engines.
 * Supports both local (in-JVM) and remote (sidecar) execution modes.
 * <p>
 * Engine selection can be configured in deployment.toml:
 * <pre>
 * [authentication.adaptive.graaljs]
 * engine_selection_mode = "static"    # "static" or "dynamic"
 * engine_type = "REMOTE"             # For static: "LOCAL" or "REMOTE"
 * grpc_target = "localhost:50051"
 * dynamic_routing_field = "serviceProviderName"   # For dynamic mode
 * dynamic_routing_remote_values = "app1,app2"     # For dynamic mode
 * </pre>
 */
public class JsEngineFactory {

    private static final Log log = LogFactory.getLog(JsEngineFactory.class);

    private static final String SELECTION_MODE_DYNAMIC = "dynamic";
    private static final String ENGINE_TYPE_LOCAL = "LOCAL";

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

    // Static mode engine type - defaults to REMOTE (sidecar via gRPC)
    private ExecutionMode staticEngineType = ExecutionMode.REMOTE;

    // Engine selection mode: "static" or "dynamic"
    private String engineSelectionMode = DEFAULT_ENGINE_SELECTION_MODE;

    // Default gRPC target for remote engine (host:port)
    private String grpcTarget = DEFAULT_GRPC_TARGET;

    // For dynamic mode: the AuthenticationContext field to route on
    private String dynamicRoutingField = "";

    // For dynamic mode: field values that route to REMOTE engine (all others go LOCAL)
    private Set<String> dynamicRoutingRemoteValues = Collections.emptySet();

    // Statement limit for local engine
    private int javascriptResourceLimit = DEFAULT_GRAALJS_SCRIPT_STATEMENTS_LIMIT;

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
     * In static mode, all requests use the configured engine type.
     * In dynamic mode, the engine is selected per-request based on a field in the AuthenticationContext.
     *
     * @param authenticationContext The authentication context.
     * @return A JsEngine instance configured for the resolved execution mode.
     */
    public JsEngine createEngine(AuthenticationContext authenticationContext) {

        ExecutionMode resolvedMode = resolveExecutionMode(authenticationContext);

        if (resolvedMode == ExecutionMode.LOCAL) {
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

        if (log.isDebugEnabled()) {
            log.debug("[JsEngineFactory] Created remote engine with transport: " + config.getType());
        }
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
     * Get the current static execution mode.
     *
     * @return Current ExecutionMode.
     */
    public static ExecutionMode getCurrentMode() {

        return INSTANCE.staticEngineType;
    }

    /**
     * Get the gRPC target address.
     *
     * @return gRPC target string (host:port).
     */
    public static String getGrpcTarget() {

        return INSTANCE.grpcTarget;
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
     * @return The default ExecutionMode (static engine type).
     */
    public ExecutionMode getDefaultMode() {

        return staticEngineType;
    }

    /**
     * Resolve the execution mode for a given authentication context.
     * In static mode, returns the configured engine type.
     * In dynamic mode, inspects the configured AuthenticationContext field.
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
     * In static mode, returns the configured engine type.
     * In dynamic mode, inspects the configured AuthenticationContext field and checks
     * if its value is in the remote-routing set.
     *
     * @param authenticationContext The authentication context (may be null).
     * @return The resolved ExecutionMode.
     */
    private ExecutionMode resolveExecutionMode(AuthenticationContext authenticationContext) {

        // Static mode: always return the configured engine type
        if (!SELECTION_MODE_DYNAMIC.equalsIgnoreCase(engineSelectionMode)) {
            return staticEngineType;
        }

        // Dynamic mode: route based on an AuthenticationContext field value
        if (authenticationContext == null) {
            if (log.isDebugEnabled()) {
                log.debug("[JsEngineFactory] Dynamic mode: authenticationContext is null, falling back to LOCAL");
            }
            return ExecutionMode.LOCAL;
        }

        String fieldValue = extractRoutingFieldValue(authenticationContext);
        if (fieldValue != null && dynamicRoutingRemoteValues.contains(fieldValue)) {
            if (log.isDebugEnabled()) {
                log.debug("[JsEngineFactory] Dynamic routing: field '" + dynamicRoutingField +
                        "' = '" + fieldValue + "' matched remote values -> REMOTE");
            }
            return ExecutionMode.REMOTE;
        }

        if (log.isDebugEnabled()) {
            log.debug("[JsEngineFactory] Dynamic routing: field '" + dynamicRoutingField +
                    "' = '" + fieldValue + "' not in remote values -> LOCAL");
        }
        return ExecutionMode.LOCAL;
    }

    /**
     * Extract the value of the configured routing field from the AuthenticationContext.
     * Supports a fixed set of known fields to avoid reflection.
     *
     * @param authenticationContext The authentication context.
     * @return The field value, or null if the field is unknown or the value is null.
     */
    private String extractRoutingFieldValue(AuthenticationContext authenticationContext) {

        if (dynamicRoutingField == null || dynamicRoutingField.isEmpty()) {
            return null;
        }

        switch (dynamicRoutingField) {
            case "serviceProviderName":
                return authenticationContext.getServiceProviderName();
            case "tenantDomain":
                return authenticationContext.getTenantDomain();
            case "requestType":
                return authenticationContext.getRequestType();
            case "callerPath":
                return authenticationContext.getCallerPath();
            default:
                log.warn("[JsEngineFactory] Unknown dynamic routing field: '" + dynamicRoutingField +
                        "'. Supported fields: serviceProviderName, tenantDomain, requestType, callerPath");
                return null;
        }
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

        // Read engine selection mode
        String selectionMode = IdentityUtil.getProperty(GRAALJS_ENGINE_SELECTION_MODE);
        if (selectionMode != null && !selectionMode.isEmpty()) {
            engineSelectionMode = selectionMode.trim().toLowerCase();
        }

        // Read static engine type
        String engineType = IdentityUtil.getProperty(GRAALJS_ENGINE_TYPE);
        if (engineType != null && !engineType.isEmpty()) {
            if (ENGINE_TYPE_LOCAL.equalsIgnoreCase(engineType.trim())) {
                staticEngineType = ExecutionMode.LOCAL;
            } else {
                staticEngineType = ExecutionMode.REMOTE;
            }
        }

        // Read gRPC target
        String target = IdentityUtil.getProperty(GRAALJS_GRPC_TARGET);
        if (target != null && !target.isEmpty()) {
            grpcTarget = target.trim();
        }

        // Read dynamic routing config
        String routingField = IdentityUtil.getProperty(GRAALJS_DYNAMIC_ROUTING_FIELD);
        if (routingField != null && !routingField.isEmpty()) {
            dynamicRoutingField = routingField.trim();
        }

        String remoteValues = IdentityUtil.getProperty(GRAALJS_DYNAMIC_ROUTING_REMOTE_VALUES);
        if (remoteValues != null && !remoteValues.isEmpty()) {
            Set<String> values = new HashSet<>();
            for (String val : remoteValues.split(",")) {
                String trimmed = val.trim();
                if (!trimmed.isEmpty()) {
                    values.add(trimmed);
                }
            }
            dynamicRoutingRemoteValues = Collections.unmodifiableSet(values);
        }

        // Log final configuration
        if (SELECTION_MODE_DYNAMIC.equalsIgnoreCase(engineSelectionMode)) {
            log.info("JsEngineFactory initialized. SelectionMode: dynamic, RoutingField: " +
                    dynamicRoutingField + ", RemoteValues: " + dynamicRoutingRemoteValues +
                    ", gRPC Target: " + grpcTarget);
        } else {
            log.info("JsEngineFactory initialized. SelectionMode: static, EngineType: " +
                    staticEngineType + ", gRPC Target: " + grpcTarget);
        }
    }
}
