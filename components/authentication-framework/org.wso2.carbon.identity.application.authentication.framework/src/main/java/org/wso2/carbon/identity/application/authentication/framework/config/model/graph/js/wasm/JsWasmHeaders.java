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

import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.CommonJsHeaders;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * JavaScript wrapper for HTTP Headers for WASM-based execution.
 * Provides controlled access to HTTP headers via JavaScript-compatible
 * interface.
 */
public class JsWasmHeaders extends CommonJsHeaders {

    /**
     * Constructs a JsWasmHeaders with the given request headers.
     *
     * @param wrapped  The headers map.
     * @param response The HTTP response.
     */
    public JsWasmHeaders(Map<String, String> wrapped, HttpServletResponse response) {
        super(wrapped, response);
    }
}
