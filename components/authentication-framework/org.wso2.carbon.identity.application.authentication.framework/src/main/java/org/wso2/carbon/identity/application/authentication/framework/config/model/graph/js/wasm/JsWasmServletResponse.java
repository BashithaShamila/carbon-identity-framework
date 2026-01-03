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

import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsServletResponse;
import org.wso2.carbon.identity.application.authentication.framework.context.TransientObjectWrapper;

import javax.servlet.http.HttpServletResponse;

/**
 * JavaScript wrapper for Servlet Response for WASM-based execution.
 * Provides controlled access to HTTP response data via JavaScript-compatible
 * interface.
 */
public class JsWasmServletResponse extends JsServletResponse {

    /**
     * Constructs a JsWasmServletResponse with the given response wrapper.
     *
     * @param wrapped The transient object wrapper containing the HTTP response.
     */
    public JsWasmServletResponse(TransientObjectWrapper<HttpServletResponse> wrapped) {
        super(wrapped);
    }
}
