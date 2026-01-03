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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.JsGenericSerializer;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmAuthenticatedUser;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.wasm.JsWasmAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.exception.FrameworkException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serializer for WASM-based JavaScript execution.
 * Handles serialization and deserialization of JavaScript objects for
 * persistence across requests.
 */
public class WasmSerializer implements JsGenericSerializer<WasmRuntime> {

    private static final Log LOG = LogFactory.getLog(WasmSerializer.class);
    private static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .create();

    private static WasmSerializer instance;

    /**
     * Gets the singleton instance of WasmSerializer.
     *
     * @return The WasmSerializer instance.
     */
    public static synchronized WasmSerializer getInstance() {
        if (instance == null) {
            instance = new WasmSerializer();
        }
        return instance;
    }

    /**
     * Converts a JavaScript value to a serializable Java object.
     *
     * @param value The JavaScript value to convert.
     * @return A serializable Java object.
     */
    @Override
    public Object toJsSerializable(Object value) {
        if (value == null) {
            return null;
        }

        // Handle WASM wrapper types
        if (value instanceof JsWasmAuthenticatedUser) {
            return ((JsWasmAuthenticatedUser) value).toMap();
        }
        if (value instanceof JsWasmAuthenticationContext) {
            return ((JsWasmAuthenticationContext) value).toMap();
        }

        // Handle primitive types
        if (value instanceof String || value instanceof Number || value instanceof Boolean) {
            return value;
        }

        // Handle serializable function
        if (value instanceof WasmSerializableJsFunction) {
            Map<String, Object> funcMap = new HashMap<>();
            WasmSerializableJsFunction func = (WasmSerializableJsFunction) value;
            funcMap.put("__type__", "WasmSerializableJsFunction");
            funcMap.put("source", func.getSource());
            funcMap.put("isFunction", func.isFunction());
            return funcMap;
        }

        // Handle Map types
        if (value instanceof Map) {
            Map<String, Object> result = new HashMap<>();
            ((Map<?, ?>) value).forEach((k, v) -> result.put(String.valueOf(k), toJsSerializable(v)));
            return result;
        }

        // Handle List types
        if (value instanceof List) {
            List<Object> result = new ArrayList<>();
            ((List<?>) value).forEach(v -> result.add(toJsSerializable(v)));
            return result;
        }

        // Handle arrays
        if (value.getClass().isArray()) {
            Object[] array = (Object[]) value;
            List<Object> result = new ArrayList<>();
            for (Object o : array) {
                result.add(toJsSerializable(o));
            }
            return result;
        }

        // Try JSON serialization as fallback
        try {
            String json = GSON.toJson(value);
            return GSON.fromJson(json, Map.class);
        } catch (Exception e) {
            LOG.debug("Could not serialize value of type: " + value.getClass().getName(), e);
            return value.toString();
        }
    }

    /**
     * Converts a serialized value back to a JavaScript-compatible object.
     *
     * @param value   The serialized value.
     * @param runtime The WASM runtime.
     * @return A JavaScript-compatible object.
     * @throws FrameworkException If deserialization fails.
     */
    @Override
    public Object fromJsSerializable(Object value, WasmRuntime runtime) throws FrameworkException {
        if (value == null) {
            return null;
        }

        // Handle primitive types
        if (value instanceof String || value instanceof Number || value instanceof Boolean) {
            return value;
        }

        // Handle Map types (check for special types)
        if (value instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) value;
            String type = (String) map.get("__type__");

            if ("WasmSerializableJsFunction".equals(type)) {
                String source = (String) map.get("source");
                Boolean isFunction = (Boolean) map.get("isFunction");
                return new WasmSerializableJsFunction(source, isFunction != null && isFunction);
            }

            // Regular map
            Map<String, Object> result = new HashMap<>();
            map.forEach((k, v) -> {
                try {
                    result.put(String.valueOf(k), fromJsSerializable(v, runtime));
                } catch (FrameworkException e) {
                    LOG.error("Error deserializing map value", e);
                }
            });
            return result;
        }

        // Handle List types
        if (value instanceof List) {
            List<Object> result = new ArrayList<>();
            for (Object v : (List<?>) value) {
                result.add(fromJsSerializable(v, runtime));
            }
            return result;
        }

        return value;
    }

    /**
     * Converts a JSON string to a Java object.
     *
     * @param json The JSON string.
     * @return The Java object.
     */
    public Object fromJson(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }

        try {
            JsonElement element = JsonParser.parseString(json);
            if (element.isJsonNull()) {
                return null;
            } else if (element.isJsonPrimitive()) {
                if (element.getAsJsonPrimitive().isBoolean()) {
                    return element.getAsBoolean();
                } else if (element.getAsJsonPrimitive().isNumber()) {
                    return element.getAsNumber();
                } else {
                    return element.getAsString();
                }
            } else if (element.isJsonObject()) {
                return GSON.fromJson(element, Map.class);
            } else if (element.isJsonArray()) {
                return GSON.fromJson(element, List.class);
            }
        } catch (Exception e) {
            LOG.debug("Could not parse JSON: " + json, e);
        }

        return json;
    }

    /**
     * Converts a Java object to a JSON string.
     *
     * @param value The Java object.
     * @return The JSON string.
     */
    public String toJson(Object value) {
        if (value == null) {
            return "null";
        }
        return GSON.toJson(value);
    }
}
