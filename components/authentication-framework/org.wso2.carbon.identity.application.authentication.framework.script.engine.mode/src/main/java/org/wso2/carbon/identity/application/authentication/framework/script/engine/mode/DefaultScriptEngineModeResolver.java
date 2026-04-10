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

package org.wso2.carbon.identity.application.authentication.framework.script.engine.mode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.JsEngineFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.ScriptEngineModeResolver;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;

/**
 * Default implementation of {@link ScriptEngineModeResolver}.
 * <p>
 * Routes requests to REMOTE engine based on the service provider name.
 * All other requests are routed to the LOCAL engine.
 * <p>
 * This implementation can be replaced by dropping a custom OSGi bundle into the
 * server's dropins folder with a higher service ranking.
 */
public class DefaultScriptEngineModeResolver implements ScriptEngineModeResolver {

    private static final Log log = LogFactory.getLog(DefaultScriptEngineModeResolver.class);

    @Override
    public JsEngineFactory.ExecutionMode resolve(AuthenticationContext authenticationContext) {

        if (authenticationContext == null) {
            if (log.isDebugEnabled()) {
                log.debug("[DefaultScriptEngineModeResolver] AuthenticationContext is null, falling back to LOCAL");
            }
            return JsEngineFactory.ExecutionMode.LOCAL;
        }

        String spName = authenticationContext.getServiceProviderName();
        String tenantDomain = authenticationContext.getTenantDomain();

        // Example routing logic: route specific SPs to REMOTE engine.
        // This can be extended to implement more complex routing based on
        // tenant domain, request type, SP properties, or any other field
        // available in the AuthenticationContext.
        if (log.isDebugEnabled()) {
            log.debug("[DefaultScriptEngineModeResolver] Resolving engine mode for SP: " + spName +
                    ", tenant: " + tenantDomain);
        }

        // Default: all requests go to LOCAL engine.
        // Override this class or deploy a custom resolver to route specific requests to REMOTE.
        return JsEngineFactory.ExecutionMode.LOCAL;
    }
}
