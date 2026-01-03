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

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.wasm;

import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.JsWrapperBaseFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.CommonJsHeaders;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.base.JsBaseAuthenticatedUser;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.base.JsBaseAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.base.JsBaseClaims;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.base.JsBaseCookie;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.base.JsBaseParameters;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.base.JsBaseRuntimeClaims;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.base.JsBaseServletRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.base.JsBaseServletResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.base.JsBaseStep;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.base.JsBaseSteps;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmAuthenticatedUser;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmClaims;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmCookie;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmHeaders;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmParameters;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmRuntimeClaims;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmServletRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmServletResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmStep;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmSteps;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmWritableParameters;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.context.TransientObjectWrapper;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Factory to create JavaScript Object Wrappers for WASM-based execution.
 * Provides factory methods for creating wrapper objects that can be used with
 * the WASM-based JavaScript engine.
 */
public class JsWasmWrapperFactory implements JsWrapperBaseFactory {

    @Override
    public JsBaseAuthenticatedUser createJsAuthenticatedUser(AuthenticatedUser authenticatedUser) {
        return new JsWasmAuthenticatedUser(authenticatedUser);
    }

    @Override
    public JsBaseAuthenticatedUser createJsAuthenticatedUser(AuthenticationContext authenticationContext,
            AuthenticatedUser authenticatedUser) {
        return new JsWasmAuthenticatedUser(authenticationContext, authenticatedUser);
    }

    @Override
    public JsBaseAuthenticatedUser createJsAuthenticatedUser(AuthenticationContext context,
            AuthenticatedUser wrappedUser, int step, String idp) {
        return new JsWasmAuthenticatedUser(context, wrappedUser, step, idp);
    }

    @Override
    public JsBaseAuthenticationContext createJsAuthenticationContext(AuthenticationContext authenticationContext) {
        return new JsWasmAuthenticationContext(authenticationContext);
    }

    @Override
    public JsBaseCookie createJsCookie(Cookie cookie) {
        return new JsWasmCookie(cookie);
    }

    @Override
    @SuppressWarnings("unchecked")
    public JsBaseParameters createJsParameters(Map parameters) {
        return new JsWasmParameters((Map<String, Object>) parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public JsBaseParameters createJsWritableParameters(Map data) {
        return new JsWasmWritableParameters((Map<String, Object>) data);
    }

    @Override
    public JsBaseServletRequest createJsServletRequest(TransientObjectWrapper<HttpServletRequest> wrapped) {
        return new JsWasmServletRequest(wrapped);
    }

    @Override
    public JsBaseServletResponse createJsServletResponse(TransientObjectWrapper<HttpServletResponse> wrapped) {
        return new JsWasmServletResponse(wrapped);
    }

    @Override
    public JsBaseClaims createJsClaims(AuthenticationContext context, int step, String idp,
            boolean isRemoteClaimRequest) {
        return new JsWasmClaims(context, step, idp, isRemoteClaimRequest);
    }

    @Override
    public JsBaseClaims createJsClaims(AuthenticationContext context, AuthenticatedUser user,
            boolean isRemoteClaimRequest) {
        return new JsWasmClaims(context, user, isRemoteClaimRequest);
    }

    @Override
    public JsBaseRuntimeClaims createJsRuntimeClaims(AuthenticationContext context, int step, String idp) {
        return new JsWasmRuntimeClaims(context, step, idp);
    }

    @Override
    public JsBaseRuntimeClaims createJsRuntimeClaims(AuthenticationContext context, AuthenticatedUser user) {
        return new JsWasmRuntimeClaims(context, user);
    }

    @Override
    public JsBaseStep createJsStep(AuthenticationContext context, int step, String authenticatedIdp,
            String authenticatedAuthenticator) {
        return new JsWasmStep(context, step, authenticatedIdp, authenticatedAuthenticator);
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommonJsHeaders createJsHeaders(Map wrapped, HttpServletResponse response) {
        return new JsWasmHeaders((Map<String, String>) wrapped, response);
    }

    @Override
    public JsBaseSteps createJsSteps(AuthenticationContext context) {
        return new JsWasmSteps(context);
    }
}
