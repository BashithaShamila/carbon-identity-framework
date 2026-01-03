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

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm;

import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * JavaScript wrapper for AuthenticationContext for WASM-based execution.
 * Provides controlled access to authentication context data via
 * JavaScript-compatible interface.
 */
public class JsWasmAuthenticationContext extends JsAuthenticationContext {

    /**
     * Constructs a JsWasmAuthenticationContext with the given authentication
     * context.
     *
     * @param wrapped The authentication context to wrap.
     */
    public JsWasmAuthenticationContext(AuthenticationContext wrapped) {
        super(wrapped);
    }

    /**
     * Converts this wrapper to a Map for JSON serialization.
     *
     * @return Map representation of the authentication context.
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        AuthenticationContext ctx = getContext();
        if (ctx != null) {
            map.put("currentStep", ctx.getCurrentStep());
            map.put("tenantDomain", ctx.getTenantDomain());
            map.put("serviceProviderName", ctx.getServiceProviderName());
            map.put("requestType", ctx.getRequestType());
            map.put("retrying", ctx.isRetrying());
            if (ctx.getSubject() != null) {
                JsWasmAuthenticatedUser user = new JsWasmAuthenticatedUser(ctx, ctx.getSubject());
                Map<String, Object> userMap = user.toMap();
                map.put("subject", userMap);
                // Also expose as currentKnownSubject for script compatibility
                map.put("currentKnownSubject", userMap);
            }
        }
        return map;
    }
}
