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

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Unified proxy type detection for the remote JS engine.
 * Consolidates proxy type detection patterns from:
 * - Serializer.shouldUseProxyPattern() — POJO proxy caching (edge case #11)
 * - ExternalCallbackHandler — callback response proxy detection
 * - GrpcStreamingTransportImpl — streaming response proxy detection
 *
 * Two distinct concerns:
 * 1. "Should this POJO use the lazy proxy pattern for serialization?" (shouldUseProxyPattern)
 *    - Used during serialization to avoid eagerly serializing complex domain objects.
 *    - Criteria: class name heuristics (.User, .model., .domain.) OR getter count > 3.
 *
 * 2. "Is this a JS wrapper proxy type?" (isJsWrapperProxy / getJsWrapperProxyType)
 *    - Used when returning host function results to the External.
 *    - All JsGraal* wrapper classes implement Proxy (ProxyObject or ProxyArray).
 */
public final class ProxyTypeResolver {

    /**
     * Static type name registry for known JS wrapper proxy classes.
     * Maps simple class name to the type name used on the wire (e.g., "authenticationcontext").
     * Built once; entries are never removed.
     */
    private static final Map<String, String> PROXY_TYPE_NAMES = new ConcurrentHashMap<>();

    static {
        // JsGraal* ProxyObject implementations (11 classes)
        PROXY_TYPE_NAMES.put("JsGraalAuthenticationContext", "authenticationcontext");
        PROXY_TYPE_NAMES.put("JsGraalAuthenticatedUser", "authenticateduser");
        PROXY_TYPE_NAMES.put("JsGraalServletRequest", "servletrequest");
        PROXY_TYPE_NAMES.put("JsGraalServletResponse", "servletresponse");
        PROXY_TYPE_NAMES.put("JsGraalStep", "step");
        PROXY_TYPE_NAMES.put("JsGraalClaims", "claims");
        PROXY_TYPE_NAMES.put("JsGraalRuntimeClaims", "runtimeclaims");
        PROXY_TYPE_NAMES.put("JsGraalParameters", "parameters");
        PROXY_TYPE_NAMES.put("JsGraalWritableParameters", "writableparameters");
        PROXY_TYPE_NAMES.put("JsGraalHeaders", "headers");
        PROXY_TYPE_NAMES.put("JsGraalCookie", "cookie");
        // JsGraalSteps implements ProxyArray (not ProxyObject)
        PROXY_TYPE_NAMES.put("JsGraalSteps", "steps");
    }

    /**
     * Cache for shouldUseProxyPattern results per class.
     * Avoids repeated class-name string operations.
     */
    private static final ConcurrentHashMap<Class<?>, Boolean> PROXY_PATTERN_CACHE = new ConcurrentHashMap<>();

    private ProxyTypeResolver() {
        // Utility class
    }

    /**
     * Check if an object should use the lazy proxy pattern instead of eager serialization.
     * Used by Serializer for POJO caching (edge case #11).
     *
     * Criteria: class name contains ".User", ".user.", ".model.", or ".domain."
     * (known domain object patterns with potentially expensive getter operations).
     *
     * Any POJO that doesn't match these patterns and isn't handled by an explicit
     * serialization path will hit the IllegalArgumentException in Serializer.toProto(),
     * failing fast so a handler can be added.
     *
     * Results are cached per class.
     *
     * @param obj The object to check
     * @return true if proxy pattern should be used, false otherwise
     */
    public static boolean shouldUseProxyPattern(Object obj) {
        if (obj == null) {
            return false;
        }

        Class<?> clazz = obj.getClass();
        return PROXY_PATTERN_CACHE.computeIfAbsent(clazz, ProxyTypeResolver::computeShouldUseProxyPattern);
    }

    private static boolean computeShouldUseProxyPattern(Class<?> clazz) {
        String className = clazz.getName();

        // Use proxy for domain objects that might have expensive operations.
        // Common patterns: User, AuthenticatedUser, custom domain objects.
        // Any unrecognized POJO that reaches Serializer.toProto() without matching
        // these patterns will hit the IllegalArgumentException at the bottom,
        // failing fast so a handler can be added.
        boolean matchesClassName = className.contains(".User") ||
                className.contains(".user.") ||
                className.contains(".model.") ||
                className.contains(".domain.");

        if (JsEngineFactory.isTracingEnabled()) {
            System.out.println("[ProxyTypeResolver] shouldUseProxyPattern: " + className +
                    " - matches class pattern = " + matchesClassName);
        }
        return matchesClassName;
    }

    /**
     * Check if a value is a JS wrapper proxy type.
     * Used by ExternalCallbackHandler and GrpcStreamingTransportImpl for callback/streaming
     * response serialization to determine if a host function return value should be
     * sent as a proxy reference rather than eagerly serialized.
     * <p>
     * All JsGraal* wrapper classes implement {@code org.graalvm.polyglot.proxy.Proxy}
     * (either ProxyObject or ProxyArray). This is a stable contract — not a string heuristic.
     *
     * @param value The value to check
     * @return true if the value is a JS wrapper proxy type
     */
    public static boolean isJsWrapperProxy(Object value) {
        if (value == null) {
            return false;
        }
        return value instanceof org.graalvm.polyglot.proxy.Proxy;
    }

    /**
     * Extract the proxy type name from a JS wrapper proxy.
     * Uses a static registry for known classes, falls back to lowercased simple name
     * for unregistered types.
     *
     * Examples:
     * - JsGraalAuthenticationContext -> "authenticationcontext"
     * - JsGraalServletRequest -> "servletrequest"
     * - JsGraalSteps -> "steps"
     * - SomeOtherClass -> "someotherclass"
     *
     * @param value The value to extract the proxy type from
     * @return The proxy type name (lowercased)
     */
    public static String getJsWrapperProxyType(Object value) {
        String simpleName = value.getClass().getSimpleName();
        String registeredType = PROXY_TYPE_NAMES.get(simpleName);
        if (registeredType != null) {
            return registeredType;
        }
        // Fallback for unregistered types: same behavior as original code
        // (strip JsGraal/Js prefix), ensuring wire compatibility
        if (simpleName.startsWith("JsGraal")) {
            return simpleName.substring(7).toLowerCase();
        } else if (simpleName.startsWith("Js")) {
            return simpleName.substring(2).toLowerCase();
        }
        return simpleName.toLowerCase();
    }
}
