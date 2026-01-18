/*
 * Copyright (c) 2026, WSO2 LLC. (http://www.wso2.com).
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

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine;

import com.google.protobuf.ByteString;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.GraalSerializableJsFunction;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.GraalSerializer;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.SerializedArray;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.SerializedFunction;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.SerializedMap;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.SerializedProxyObject;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.SerializedValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serializer for converting Java objects to/from Protocol Buffers
 * SerializedValue.
 * Leverages existing GraalSerializer patterns for JavaScript value conversion.
 */
public class ProtobufSerializer {

    private static final Log log = LogFactory.getLog(ProtobufSerializer.class);

    private ProtobufSerializer() {
        // Utility class
    }

    /**
     * Convert a Java object to Protocol Buffers SerializedValue.
     *
     * @param value The Java object to serialize.
     * @return SerializedValue protobuf message.
     */
    public static SerializedValue toProto(Object value) {
        if (value == null) {
            return SerializedValue.newBuilder()
                    .setNullValue(ByteString.EMPTY)
                    .build();
        }

        // First convert using GraalSerializer if needed
        Object serializable = GraalSerializer.toJsSerializableInternal(value);

        if (serializable == null) {
            return SerializedValue.newBuilder()
                    .setNullValue(ByteString.EMPTY)
                    .build();
        }

        if (serializable instanceof String) {
            return SerializedValue.newBuilder()
                    .setStringValue((String) serializable)
                    .build();
        }

        if (serializable instanceof Integer) {
            return SerializedValue.newBuilder()
                    .setIntValue(((Integer) serializable).longValue())
                    .build();
        }

        if (serializable instanceof Long) {
            return SerializedValue.newBuilder()
                    .setIntValue((Long) serializable)
                    .build();
        }

        if (serializable instanceof Double) {
            return SerializedValue.newBuilder()
                    .setDoubleValue((Double) serializable)
                    .build();
        }

        if (serializable instanceof Float) {
            return SerializedValue.newBuilder()
                    .setDoubleValue(((Float) serializable).doubleValue())
                    .build();
        }

        if (serializable instanceof Boolean) {
            return SerializedValue.newBuilder()
                    .setBoolValue((Boolean) serializable)
                    .build();
        }

        if (serializable instanceof GraalSerializableJsFunction) {
            GraalSerializableJsFunction jsFunc = (GraalSerializableJsFunction) serializable;
            return SerializedValue.newBuilder()
                    .setFunctionValue(SerializedFunction.newBuilder()
                            .setSource(jsFunc.getSource())
                            .setName(jsFunc.getName() != null ? jsFunc.getName() : "")
                            .build())
                    .build();
        }

        if (serializable instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> mapValue = (Map<String, Object>) serializable;
            SerializedMap.Builder mapBuilder = SerializedMap.newBuilder();
            for (Map.Entry<String, Object> entry : mapValue.entrySet()) {
                mapBuilder.putEntries(entry.getKey(), toProto(entry.getValue()));
            }
            return SerializedValue.newBuilder()
                    .setMapValue(mapBuilder.build())
                    .build();
        }

        if (serializable instanceof List) {
            @SuppressWarnings("unchecked")
            List<Object> listValue = (List<Object>) serializable;
            SerializedArray.Builder arrayBuilder = SerializedArray.newBuilder();
            for (Object element : listValue) {
                arrayBuilder.addElements(toProto(element));
            }
            return SerializedValue.newBuilder()
                    .setArrayValue(arrayBuilder.build())
                    .build();
        }

        // Fallback: convert to string
        return SerializedValue.newBuilder()
                .setStringValue(serializable.toString())
                .build();
    }

    /**
     * Convert a Protocol Buffers SerializedValue to Java object.
     *
     * @param sv The SerializedValue to deserialize.
     * @return Java object.
     */
    public static Object fromProto(SerializedValue sv) {
        if (sv == null) {
            return null;
        }

        switch (sv.getValueCase()) {
            case STRING_VALUE:
                return sv.getStringValue();

            case INT_VALUE:
                return sv.getIntValue();

            case DOUBLE_VALUE:
                return sv.getDoubleValue();

            case BOOL_VALUE:
                return sv.getBoolValue();

            case NULL_VALUE:
                return null;

            case MAP_VALUE:
                Map<String, Object> map = new HashMap<>();
                for (Map.Entry<String, SerializedValue> entry : sv.getMapValue().getEntriesMap().entrySet()) {
                    map.put(entry.getKey(), fromProto(entry.getValue()));
                }
                return map;

            case ARRAY_VALUE:
                List<Object> list = new ArrayList<>();
                for (SerializedValue element : sv.getArrayValue().getElementsList()) {
                    list.add(fromProto(element));
                }
                return list;

            case FUNCTION_VALUE:
                SerializedFunction func = sv.getFunctionValue();
                return new GraalSerializableJsFunction(func.getSource(), true);

            case PROXY_OBJECT:
                // For proxy objects, return a placeholder map with type info
                SerializedProxyObject proxy = sv.getProxyObject();
                Map<String, Object> proxyMap = new HashMap<>();
                proxyMap.put("__proxyType", proxy.getType());
                proxyMap.put("__referenceId", proxy.getReferenceId());
                for (Map.Entry<String, SerializedValue> entry : proxy.getCachedPropertiesMap().entrySet()) {
                    proxyMap.put(entry.getKey(), fromProto(entry.getValue()));
                }
                return proxyMap;

            default:
                log.warn("Unknown SerializedValue case: " + sv.getValueCase());
                return null;
        }
    }

    /**
     * Serialize a map of bindings to protobuf map.
     *
     * @param bindings The bindings map.
     * @return Map of string to SerializedValue.
     */
    public static Map<String, SerializedValue> toProtoMap(Map<String, Object> bindings) {
        Map<String, SerializedValue> result = new HashMap<>();
        if (bindings != null) {
            for (Map.Entry<String, Object> entry : bindings.entrySet()) {
                result.put(entry.getKey(), toProto(entry.getValue()));
            }
        }
        return result;
    }

    /**
     * Deserialize a protobuf map to Java bindings map.
     *
     * @param protoMap The protobuf map.
     * @return Java bindings map.
     */
    public static Map<String, Object> fromProtoMap(Map<String, SerializedValue> protoMap) {
        Map<String, Object> result = new HashMap<>();
        if (protoMap != null) {
            for (Map.Entry<String, SerializedValue> entry : protoMap.entrySet()) {
                result.put(entry.getKey(), fromProto(entry.getValue()));
            }
        }
        return result;
    }
}
