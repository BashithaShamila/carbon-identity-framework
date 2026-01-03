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

import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.JsWrapperFactoryProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parameters that can be modified from the authentication script.
 * This wrapper is for WASM-based execution.
 */
public class JsWasmWritableParameters extends JsWasmParameters {

    public JsWasmWritableParameters(Map<String, Object> wrapped) {
        super(wrapped);
    }

    @Override
    public Object getMember(String name) {
        Object member = getWrapped().get(name);
        return processWritableParameterMember(member);
    }

    @SuppressWarnings("unchecked")
    private Object processWritableParameterMember(Object member) {
        if (member instanceof Map) {
            // Recursively wrap the Map and its contents
            Map<?, ?> originalMap = (Map<?, ?>) member;
            Map<String, Object> wrappedMap = new HashMap<>();
            for (Map.Entry<?, ?> entry : originalMap.entrySet()) {
                wrappedMap.put(String.valueOf(entry.getKey()), processWritableParameterMember(entry.getValue()));
            }
            return JsWrapperFactoryProvider.getInstance().getWrapperFactory().createJsWritableParameters(wrappedMap);
        } else if (member instanceof List) {
            // Return the list as-is for WASM
            return member;
        } else if (member != null && member.getClass().isArray()) {
            // Return the array as-is for WASM
            return member;
        }
        return member;
    }

    public boolean removeMember(String name) {
        removeMemberObject(name);
        return true;
    }

    public void setMember(String name, Object value) {
        getWrapped().put(name, value);
    }
}
