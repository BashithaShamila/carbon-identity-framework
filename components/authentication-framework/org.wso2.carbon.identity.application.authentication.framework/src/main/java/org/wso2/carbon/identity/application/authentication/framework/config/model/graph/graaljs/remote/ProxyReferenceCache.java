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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Session-scoped cache for proxy object references and host function return references.
 * <p>
 * Manages two types of references:
 * <ul>
 *   <li><b>Proxy objects</b>: Complex objects cached for lazy property loading
 *       (e.g., User arrays from getUsersWithClaimValues). Accessed via "__proxyref__" prefix.</li>
 *   <li><b>Host function return refs</b>: Objects returned by host functions that need
 *       property access from the External. Accessed via "__hostref__" prefix.</li>
 * </ul>
 */
class ProxyReferenceCache {

    private static final Log log = LogFactory.getLog(ProxyReferenceCache.class);

    // Session-scoped cache for proxied objects (e.g., User objects from getUsersWithClaimValues)
    // Key: reference_id (UUID), Value: actual object
    private final Map<String, Object> proxyObjectCache = new ConcurrentHashMap<>();

    // Host function return references
    // Key: reference_id (UUID), Value: object returned by host function
    private final Map<String, Object> hostFunctionRefs = new ConcurrentHashMap<>();

    /**
     * Store a complex object reference for later property access.
     * Used when host functions return complex objects that need to be accessed
     * via dynamic proxy on the External.
     *
     * @param obj The object to store.
     * @return A unique reference ID.
     */
    String storeObjectReference(Object obj) {
        String refId = UUID.randomUUID().toString();
        hostFunctionRefs.put(refId, obj);
        log.debug("[RemoteJsEngine] Stored object reference: " + refId +
                " -> " + (obj != null ? obj.getClass().getSimpleName() : "null"));
        return refId;
    }

    /**
     * Get a property from a cached proxy object.
     * Path format: "&lt;referenceId&gt;::&lt;property&gt;" or
     * "&lt;referenceId&gt;::&lt;property&gt;::&lt;nestedProperty&gt;..."
     * <p>
     * This enables lazy loading of complex objects. Instead of eagerly serializing all
     * properties (which causes timeouts for large result sets like getUsersWithClaimValues),
     * objects are cached and properties are fetched on-demand when accessed.
     */
    Object getProxyObjectProperty(String path) {
        String[] parts = path.split(RemoteEngineConstants.PATH_SEPARATOR);
        String refId = parts[0];
        Object root = proxyObjectCache.get(refId);

        if (root == null) {
            log.warn("[RemoteJsEngine] No proxy object found for reference ID: " + refId);
            return null;
        }

        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] Retrieved proxy object for refId: " + refId +
                    ", type: " + root.getClass().getName());
        }

        Object result = PropertyPathNavigator.navigatePath(parts, 1, root);

        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] getProxyObjectProperty '" + path + "' = " +
                    (result != null ? result.getClass().getSimpleName() : "null"));
        }
        return result;
    }

    /**
     * Navigate a property path on a stored host function return reference.
     * Path format: "&lt;refId&gt;" or "&lt;refId&gt;::&lt;property&gt;::&lt;subprop&gt;..."
     */
    Object getHostRefProperty(String path) {
        String[] parts = path.split(RemoteEngineConstants.PATH_SEPARATOR);
        String refId = parts[0];
        Object root = hostFunctionRefs.get(refId);

        if (root == null) {
            log.warn("[RemoteJsEngine] No host function ref found for ID: " + refId);
            return null;
        }

        Object result = PropertyPathNavigator.navigatePath(parts, 1, root);

        log.debug("[RemoteJsEngine] getHostRefProperty '" + path + "' = " +
                (result != null ? result.getClass().getSimpleName() : "null"));
        return result;
    }

    /**
     * Set a property on a stored host function return reference.
     * Path format: "&lt;refId&gt;::&lt;property&gt;::&lt;subprop&gt;..."
     */
    boolean setHostRefProperty(String path, Object value) {
        String[] parts = path.split(RemoteEngineConstants.PATH_SEPARATOR);
        if (parts.length < 2) {
            log.warn("[RemoteJsEngine] setHostRefProperty requires at least refId and property: " + path);
            return false;
        }

        String refId = parts[0];
        Object root = hostFunctionRefs.get(refId);
        if (root == null) {
            log.warn("[RemoteJsEngine] No host function ref found for ID: " + refId);
            return false;
        }

        return PropertyPathNavigator.setProperty(parts, 1, root, value);
    }

    /**
     * Set a property on a cached proxy object.
     * Path format: "&lt;referenceId&gt;::&lt;property&gt;" or
     * "&lt;referenceId&gt;::&lt;property&gt;::&lt;nestedProperty&gt;..."
     *
     * @param path  The path containing referenceId and property segments.
     * @param value The value to set.
     * @return true if the property was successfully set, false otherwise.
     */
    boolean setProxyObjectProperty(String path, Object value) {

        String[] parts = path.split(RemoteEngineConstants.PATH_SEPARATOR);
        if (parts.length < 2) {
            log.warn("[RemoteJsEngine] setProxyObjectProperty requires at least refId and property: " + path);
            return false;
        }

        String refId = parts[0];
        Object root = proxyObjectCache.get(refId);
        if (root == null) {
            log.warn("[RemoteJsEngine] No proxy object found for reference ID: " + refId);
            return false;
        }

        return PropertyPathNavigator.setProperty(parts, 1, root, value);
    }

    /**
     * Get the proxy object cache map.
     * Used by transport layer to set ThreadLocal before serialization.
     *
     * @return The proxy object cache.
     */
    Map<String, Object> getCache() {
        return proxyObjectCache;
    }
}
