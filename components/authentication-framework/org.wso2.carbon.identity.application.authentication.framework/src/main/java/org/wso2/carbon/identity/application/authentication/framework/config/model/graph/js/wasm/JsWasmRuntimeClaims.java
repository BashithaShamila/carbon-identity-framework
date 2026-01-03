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

import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.base.JsBaseRuntimeClaims;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;

/**
 * JavaScript wrapper for Runtime Claims for WASM-based execution.
 * Provides controlled access to runtime claims via JavaScript-compatible
 * interface.
 */
public class JsWasmRuntimeClaims extends JsWasmClaims implements JsBaseRuntimeClaims {

    /**
     * Constructs a JsWasmRuntimeClaims with step and IdP context.
     *
     * @param context The authentication context.
     * @param step    The authentication step.
     * @param idp     The identity provider.
     */
    public JsWasmRuntimeClaims(AuthenticationContext context, int step, String idp) {
        super(context, step, idp, false);
    }

    /**
     * Constructs a JsWasmRuntimeClaims with authenticated user context.
     *
     * @param context The authentication context.
     * @param user    The authenticated user.
     */
    public JsWasmRuntimeClaims(AuthenticationContext context, AuthenticatedUser user) {
        super(context, user, false);
    }

    /**
     * Gets a runtime claim value.
     *
     * @param claimUri The claim URI.
     * @return The claim value.
     */
    @Override
    public Object getMember(String claimUri) {
        Object runtimeClaim = getRuntimeClaim(claimUri);
        if (runtimeClaim != null) {
            return runtimeClaim;
        }
        return super.getMember(claimUri);
    }

    /**
     * Checks if a runtime claim exists.
     *
     * @param claimUri The claim URI.
     * @return True if the claim exists.
     */
    @Override
    public boolean hasMember(String claimUri) {
        if (hasRuntimeClaim(claimUri)) {
            return true;
        }
        return super.hasMember(claimUri);
    }

    /**
     * Sets a runtime claim value.
     *
     * @param claimUri   The claim URI.
     * @param claimValue The claim value.
     * @return True if successful.
     */
    @Override
    public boolean setMemberObject(String claimUri, Object claimValue) {
        setRuntimeClaim(claimUri, claimValue);
        return true;
    }
}
