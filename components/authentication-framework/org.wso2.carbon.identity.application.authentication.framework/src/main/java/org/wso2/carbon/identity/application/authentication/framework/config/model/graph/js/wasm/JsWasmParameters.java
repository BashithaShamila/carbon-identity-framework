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

import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsParameters;

import java.util.Map;

/**
 * JavaScript wrapper for Parameters for WASM-based execution.
 * Provides controlled access to request parameters via JavaScript-compatible
 * interface.
 */
public class JsWasmParameters extends JsParameters {

    /**
     * Constructs a JsWasmParameters with the given parameters map.
     *
     * @param wrapped The parameters map.
     */
    public JsWasmParameters(Map<String, Object> wrapped) {
        super(wrapped);
    }
}
