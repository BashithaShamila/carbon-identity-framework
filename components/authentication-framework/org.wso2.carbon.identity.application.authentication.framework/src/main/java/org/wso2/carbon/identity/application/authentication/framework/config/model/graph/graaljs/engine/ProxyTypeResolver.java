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

/**
 * Unified proxy type detection for the remote JS engine.
 * Consolidates proxy type detection patterns from:
 * - ProtobufSerializer.shouldUseProxyPattern() — POJO proxy caching (edge case #11)
 * - HostCallbackServer.isProxyType() / getProxyType() — callback response proxy detection
 * - GrpcStreamingTransportImpl.isProxyType() / getProxyType() — streaming response proxy detection
 *
 * Two distinct concerns:
 * 1. "Should this POJO use the lazy proxy pattern for serialization?" (shouldUseProxyPattern)
 *    - Used during serialization to avoid eagerly serializing complex domain objects.
 *    - Criteria: class name heuristics (.User, .model., .domain.) OR getter count > 3.
 *
 * 2. "Is this a JS wrapper proxy type?" (isJsWrapperProxy / getJsWrapperProxyType)
 *    - Used when returning host function results to the External.
 *    - Criteria: class name contains JsGraal, JsServlet, JsStep, JsAuthenticated, JsWritable,
 *      or object implements ProxyObject.
 */
public final class ProxyTypeResolver {

    private ProxyTypeResolver() {
        // Utility class
    }

    /**
     * Check if an object should use the lazy proxy pattern instead of eager serialization.
     * Used by ProtobufSerializer for POJO caching (edge case #11).
     *
     * Criteria:
     * - Class name contains ".User", ".user.", ".model.", or ".domain."
     * - OR the class has more than 3 getter methods (likely a complex domain object)
     *
     * @param obj The object to check
     * @return true if proxy pattern should be used, false otherwise
     */
    public static boolean shouldUseProxyPattern(Object obj) {
        if (obj == null) {
            return false;
        }

        Class<?> clazz = obj.getClass();
        String className = clazz.getName();

        // Use proxy for domain objects that might have expensive operations
        // Common patterns: User, AuthenticatedUser, custom domain objects
        boolean matchesClassName = className.contains(".User") ||
                className.contains(".user.") ||
                className.contains(".model.") ||
                className.contains(".domain.");

        if (matchesClassName) {
            System.out.println("[ProxyTypeResolver] shouldUseProxyPattern: " + className +
                    " - matches class pattern = true");
            return true;
        }

        // Use proxy for objects with many getters (likely complex domain objects)
        int getterCount = 0;
        for (java.lang.reflect.Method m : clazz.getMethods()) {
            if (m.getParameterCount() == 0 && m.getName().startsWith("get") &&
                    !"getClass".equals(m.getName())) {
                getterCount++;
            }
        }

        boolean hasEnoughGetters = getterCount > 3;
        System.out.println("[ProxyTypeResolver] shouldUseProxyPattern: " + className +
                " - getterCount=" + getterCount + " > 3? " + hasEnoughGetters);

        // If object has more than 3 getters, it's likely complex enough for proxy
        return hasEnoughGetters;
    }

    /**
     * Check if a value is a JS wrapper proxy type (JsGraal*, JsServlet*, JsStep*, etc.).
     * Used by HostCallbackServer and GrpcStreamingTransportImpl for callback/streaming
     * response serialization to determine if a host function return value should be
     * sent as a proxy reference rather than eagerly serialized.
     *
     * @param value The value to check
     * @return true if the value is a JS wrapper proxy type
     */
    public static boolean isJsWrapperProxy(Object value) {
        if (value == null) {
            return false;
        }
        String className = value.getClass().getName();
        return className.contains("JsGraal") ||
                className.contains("JsServlet") ||
                className.contains("JsStep") ||
                className.contains("JsAuthenticated") ||
                className.contains("JsWritable") ||
                value instanceof org.graalvm.polyglot.proxy.ProxyObject;
    }

    /**
     * Extract the proxy type name from a JS wrapper proxy.
     * Strips the "JsGraal" or "Js" prefix and lowercases the result.
     *
     * Examples:
     * - JsGraalAuthenticationContext -> "authenticationcontext"
     * - JsGraalServletRequest -> "servletrequest"
     * - JsStep -> "step"
     * - SomeOtherClass -> "someotherclass"
     *
     * @param value The value to extract the proxy type from
     * @return The proxy type name (lowercased)
     */
    public static String getJsWrapperProxyType(Object value) {
        String className = value.getClass().getSimpleName();
        if (className.startsWith("JsGraal")) {
            return className.substring(7).toLowerCase();
        } else if (className.startsWith("Js")) {
            return className.substring(2).toLowerCase();
        }
        return className.toLowerCase();
    }
}
