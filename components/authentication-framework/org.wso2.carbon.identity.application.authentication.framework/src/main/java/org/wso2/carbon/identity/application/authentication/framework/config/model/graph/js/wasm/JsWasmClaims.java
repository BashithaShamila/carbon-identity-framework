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

import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsClaims;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;

/**
 * JavaScript wrapper for Claims for WASM-based execution.
 * Provides controlled access to user claims via JavaScript-compatible
 * interface.
 */
public class JsWasmClaims extends JsClaims {

    /**
     * Constructs a JsWasmClaims with step and IdP context.
     *
     * @param context              The authentication context.
     * @param step                 The authentication step.
     * @param idp                  The identity provider.
     * @param isRemoteClaimRequest Whether this is for remote claims.
     */
    public JsWasmClaims(AuthenticationContext context, int step, String idp, boolean isRemoteClaimRequest) {
        super(context, step, idp, isRemoteClaimRequest);
    }

    /**
     * Constructs a JsWasmClaims with authenticated user context.
     *
     * @param context              The authentication context.
     * @param authenticatedUser    The authenticated user.
     * @param isRemoteClaimRequest Whether this is for remote claims.
     */
    public JsWasmClaims(AuthenticationContext context, AuthenticatedUser authenticatedUser,
            boolean isRemoteClaimRequest) {
        super(context, authenticatedUser, isRemoteClaimRequest);
    }
}
