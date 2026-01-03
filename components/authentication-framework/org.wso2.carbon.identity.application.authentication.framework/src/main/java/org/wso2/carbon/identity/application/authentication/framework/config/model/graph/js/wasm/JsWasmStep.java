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

import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsStep;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;

/**
 * JavaScript wrapper for Step for WASM-based execution.
 * Provides controlled access to authentication step data via
 * JavaScript-compatible interface.
 */
public class JsWasmStep extends JsStep {

    /**
     * Constructs a JsWasmStep with the given context and step details.
     *
     * @param context                    The authentication context.
     * @param step                       The step number.
     * @param authenticatedIdp           The authenticated identity provider.
     * @param authenticatedAuthenticator The authenticated authenticator.
     */
    public JsWasmStep(AuthenticationContext context, int step, String authenticatedIdp,
            String authenticatedAuthenticator) {
        super(context, step, authenticatedIdp, authenticatedAuthenticator);
    }
}
