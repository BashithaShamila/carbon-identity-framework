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

import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticatedUser;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;

import java.util.HashMap;
import java.util.Map;

/**
 * JavaScript wrapper for AuthenticatedUser for WASM-based execution.
 * Provides controlled access to AuthenticatedUser data via
 * JavaScript-compatible interface.
 */
public class JsWasmAuthenticatedUser extends JsAuthenticatedUser {

    /**
     * Constructs a JsWasmAuthenticatedUser with the given authenticated user.
     *
     * @param wrappedUser The authenticated user to wrap.
     */
    public JsWasmAuthenticatedUser(AuthenticatedUser wrappedUser) {
        super(wrappedUser);
    }

    /**
     * Constructs a JsWasmAuthenticatedUser with context and authenticated user.
     *
     * @param context     The authentication context.
     * @param wrappedUser The authenticated user to wrap.
     */
    public JsWasmAuthenticatedUser(AuthenticationContext context, AuthenticatedUser wrappedUser) {
        super(context, wrappedUser);
    }

    /**
     * Constructs a JsWasmAuthenticatedUser with step and IdP information.
     *
     * @param context     The authentication context.
     * @param wrappedUser The authenticated user to wrap.
     * @param step        The authentication step.
     * @param idp         The identity provider.
     */
    public JsWasmAuthenticatedUser(AuthenticationContext context, AuthenticatedUser wrappedUser, int step, String idp) {
        super(context, wrappedUser, step, idp);
    }

    /**
     * Converts this wrapper to a Map for JSON serialization.
     *
     * @return Map representation of the authenticated user.
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        AuthenticatedUser user = getWrapped();
        if (user != null) {
            map.put("username", user.getUserName());
            map.put("userStoreDomain", user.getUserStoreDomain());
            map.put("tenantDomain", user.getTenantDomain());
            map.put("authenticatedSubjectIdentifier", user.getAuthenticatedSubjectIdentifier());
            map.put("federatedUser", user.isFederatedUser());
        }
        return map;
    }
}
