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

    // Thread-local session proxy cache for storing complex objects
    // This is set per-session by RemoteJsEngine and used during serialization
    private static final ThreadLocal<Map<String, Object>> sessionProxyCache = new ThreadLocal<>();

    private ProtobufSerializer() {
        // Utility class
    }

    /**
     * Set the session proxy cache for the current thread.
     * This should be called by RemoteJsEngine before serialization.
     *
     * @param cache The proxy object cache for this session.
     */
    public static void setSessionProxyCache(Map<String, Object> cache) {
        sessionProxyCache.set(cache);
    }

    /**
     * Clear the session proxy cache for the current thread.
     */
    public static void clearSessionProxyCache() {
        sessionProxyCache.remove();
    }

    /**
     * Get the session proxy cache for the current thread.
     *
     * @return The proxy cache, or null if not set.
     */
    private static Map<String, Object> getSessionProxyCache() {
        return sessionProxyCache.get();
    }

    // Proxy pattern detection delegated to ProxyTypeResolver.shouldUseProxyPattern()

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
            System.out.println("[ProtobufSerializer] Serializing List with " + listValue.size() + " elements");
            SerializedArray.Builder arrayBuilder = SerializedArray.newBuilder();
            for (int i = 0; i < listValue.size(); i++) {
                Object element = listValue.get(i);
                System.out.println("[ProtobufSerializer] List element[" + i + "] type: " +
                        (element != null ? element.getClass().getName() : "null"));
                arrayBuilder.addElements(toProto(element));
            }
            return SerializedValue.newBuilder()
                    .setArrayValue(arrayBuilder.build())
                    .build();
        }

        // Handle Java arrays (e.g., String[] from request params)
        if (serializable.getClass().isArray()) {
            Object[] arrayValue;
            if (serializable instanceof Object[]) {
                arrayValue = (Object[]) serializable;
            } else {
                // Primitive arrays - convert to Object[]
                int length = java.lang.reflect.Array.getLength(serializable);
                arrayValue = new Object[length];
                for (int i = 0; i < length; i++) {
                    arrayValue[i] = java.lang.reflect.Array.get(serializable, i);
                }
            }
            SerializedArray.Builder arrayBuilder = SerializedArray.newBuilder();
            for (Object element : arrayValue) {
                arrayBuilder.addElements(toProto(element));
            }
            return SerializedValue.newBuilder()
                    .setArrayValue(arrayBuilder.build())
                    .build();
        }

        // Handle ProxyArray (e.g., from JsGraalParameters.processParameterMember)
        if (serializable instanceof org.graalvm.polyglot.proxy.ProxyArray) {
            org.graalvm.polyglot.proxy.ProxyArray proxyArray =
                    (org.graalvm.polyglot.proxy.ProxyArray) serializable;
            SerializedArray.Builder arrayBuilder = SerializedArray.newBuilder();
            long size = proxyArray.getSize();
            for (long i = 0; i < size; i++) {
                arrayBuilder.addElements(toProto(proxyArray.get(i)));
            }
            return SerializedValue.newBuilder()
                    .setArrayValue(arrayBuilder.build())
                    .build();
        }

        // Handle AbstractJSObjectWrapper types (JsGraalWritableParameters, JsGraalParameters, etc.)
        // These wrap Maps but don't implement the Map interface, so they fall through the Map check above.
        // Extract the wrapped Map and serialize it directly. This is critical for callback arguments
        // like the httpGet response data (JsGraalWritableParameters wrapping the HTTP JSON response).
        if (serializable instanceof org.wso2.carbon.identity.application.authentication.framework
                .config.model.graph.js.AbstractJSObjectWrapper) {
            Object wrapped = ((org.wso2.carbon.identity.application.authentication.framework
                    .config.model.graph.js.AbstractJSObjectWrapper<?>) serializable).getWrapped();
            if (wrapped instanceof Map) {
                return toProto(wrapped);
            }
        }


        if (ProxyTypeResolver.isJsWrapperProxy(serializable)) {
            Map<String, Object> cache = getSessionProxyCache();
            if (cache != null) {
                String referenceId = java.util.UUID.randomUUID().toString();
                cache.put(referenceId, serializable);

                String proxyType = ProxyTypeResolver.getJsWrapperProxyType(serializable);
                System.out.println("Created explicit proxy marker for nested JS Wrapper: " +
                            serializable.getClass().getName() + " with referenceId: " + referenceId);
//                if (log.isDebugEnabled()) {
//                    log.debug("Created explicit proxy marker for nested JS Wrapper: " +
//                            serializable.getClass().getName() + " with referenceId: " + referenceId);
//                }

                return SerializedValue.newBuilder()
                        .setProxyObject(SerializedProxyObject.newBuilder()
                                .setType(proxyType)
                                .setReferenceId(referenceId)
                                .build())
                        .build();
            } else {
                log.warn("Proxy cache not set, cannot create proxy marker for JS Wrapper: " +
                        serializable.getClass().getName());
            }
        }

        // Generic POJO handling: Use LAZY PROXY pattern instead of eager introspection.
        // This is CRITICAL for arrays of complex objects (e.g., getUsersWithClaimValues
        // returning 100 User objects). Eagerly introspecting all getters triggers
        // expensive operations (database calls) and causes timeouts.
        //
        // Instead, we create a proxy marker and cache the object. When the sidecar
        // accesses a property (e.g., users[i].username), it sends a callback to fetch
        // only that property on-demand.
        Map<String, Object> cache = getSessionProxyCache();
        boolean shouldProxy = ProxyTypeResolver.shouldUseProxyPattern(serializable);
        System.out.println("[ProtobufSerializer] POJO check for " + serializable.getClass().getName() +
                " - cache=" + (cache != null ? "available" : "NULL") +
                " shouldProxy=" + shouldProxy);

        if (cache != null && shouldProxy) {
            // Create a unique reference ID for this object
            String referenceId = java.util.UUID.randomUUID().toString();

            // Store the actual object in the session cache
            cache.put(referenceId, serializable);

            System.out.println("[ProtobufSerializer] ✓ Created proxy marker for " +
                    serializable.getClass().getName() + " with referenceId: " + referenceId);
            if (log.isDebugEnabled()) {
                log.debug("Created proxy marker for " + serializable.getClass().getName() +
                        " with referenceId: " + referenceId);
            }

            // Return a proxy marker instead of eagerly serializing all properties
            return SerializedValue.newBuilder()
                    .setProxyObject(SerializedProxyObject.newBuilder()
                            .setType(RemoteEngineConstants.PROXY_TYPE_POJO)
                            .setReferenceId(referenceId)
                            .build())
                    .build();
        }

        // Fallback: If no cache is set (old behavior), use bean introspection
        // This path should rarely be taken in remote execution mode
        try {
            Map<String, Object> beanMap = new HashMap<>();
            for (java.lang.reflect.Method m : serializable.getClass().getMethods()) {
                if (m.getParameterCount() == 0 && m.getName().startsWith("get") &&
                        !"getClass".equals(m.getName())) {
                    String prop = Character.toLowerCase(m.getName().charAt(3)) +
                            m.getName().substring(4);
                    try {
                        Object propVal = m.invoke(serializable);
                        beanMap.put(prop, propVal);
                    } catch (Exception e) {
                        // ignore inaccessible property
                    }
                }
            }
            if (!beanMap.isEmpty()) {
                log.warn("FALLBACK: Serialized POJO via eager bean introspection (no proxy cache available): " +
                        serializable.getClass().getName() + " -> " + beanMap.size() + " properties");
                return toProto(beanMap);
            }
        } catch (Exception e) {
            log.debug("POJO introspection failed for " +
                    serializable.getClass().getName() + ": " + e.getMessage());
        }

        // Fallback: convert to string
        log.warn("Falling back to toString() serialization for type: " +
                serializable.getClass().getName() + " = " + serializable);
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
                proxyMap.put(RemoteEngineConstants.PROXY_TYPE_FIELD, proxy.getType());
                proxyMap.put(RemoteEngineConstants.REFERENCE_ID_FIELD, proxy.getReferenceId());
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
