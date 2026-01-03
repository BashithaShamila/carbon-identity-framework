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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.GenericSerializableJsFunction;

import java.util.Map;

/**
 * Represents a callback function registered in the WASM JavaScript runtime.
 * The callback is identified by a unique ID and can be invoked via the
 * WasmRuntime.
 * This approach is used because JavaScript functions cannot be serialized
 * across
 * the WASM boundary - instead, we register them in JavaScript and pass the ID
 * to Java.
 */
public class WasmCallback implements GenericSerializableJsFunction<WasmRuntime> {

    private static final long serialVersionUID = 1L;
    private static final Log LOG = LogFactory.getLog(WasmCallback.class);

    private final String callbackId;
    private String functionName;

    /**
     * Constructs a WasmCallback with the given callback ID.
     *
     * @param callbackId The unique identifier for this callback in the JavaScript
     *                   registry.
     */
    public WasmCallback(String callbackId) {
        this.callbackId = callbackId;
    }

    /**
     * Gets the callback ID.
     *
     * @return The callback ID.
     */
    public String getCallbackId() {
        return callbackId;
    }

    @Override
    public String getSource() {
        // For WasmCallback, returns the callback ID as the source identifier
        return "WasmCallback:" + callbackId;
    }

    @Override
    public void setSource(String source) {
        // Source cannot be set for WasmCallback - the callback ID is immutable
    }

    /**
     * Gets the function name.
     *
     * @return The function name.
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * Sets the function name.
     *
     * @param name The function name.
     */
    public void setFunctionName(String name) {
        this.functionName = name;
    }

    @Override
    public boolean isFunction() {
        return true;
    }

    @Override
    public void setFunction(boolean function) {
        // Always a function
    }

    /**
     * Applies this callback function using the provided WASM runtime.
     * This invokes the JavaScript function registered under the callback ID.
     *
     * @param runtime The WASM runtime to use for execution.
     * @param params  The parameters to pass to the function.
     * @return The result of the function execution.
     */
    @Override
    public Object apply(WasmRuntime runtime, Object... params) {
        if (runtime == null) {
            throw new IllegalArgumentException("WasmRuntime cannot be null");
        }

        if (callbackId == null || callbackId.isEmpty()) {
            LOG.error("Cannot invoke callback: callback ID is null or empty");
            return null;
        }

        LOG.info("Invoking WasmCallback with ID: " + callbackId);

        // Invoke the callback via the __invokeCallback function in JavaScript
        return runtime.invokeCallback(callbackId, params);
    }

    /**
     * Creates a WasmCallback from a serialized representation.
     *
     * @param value The value to convert (typically a Map with callbackId).
     * @return The WasmCallback, or null if conversion is not possible.
     */
    public static WasmCallback fromSerializedForm(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof WasmCallback) {
            return (WasmCallback) value;
        }

        if (value instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) value;
            String type = (String) map.get("__type__");
            if ("WasmCallback".equals(type)) {
                String callbackId = (String) map.get("callbackId");
                if (callbackId != null && !callbackId.isEmpty()) {
                    return new WasmCallback(callbackId);
                }
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "WasmCallback{callbackId='" + callbackId + "'}";
    }
}
