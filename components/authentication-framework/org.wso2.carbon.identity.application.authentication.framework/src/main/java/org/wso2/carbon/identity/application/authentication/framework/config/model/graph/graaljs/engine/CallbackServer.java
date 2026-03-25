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

import java.io.Closeable;
import java.io.IOException;

/**
 * Abstraction for callback server that receives host function invocations from remote JavaScript engine.
 * <p>
 * In bidirectional streaming mode, callbacks are handled inline on the same stream.
 * The HostFunctionHandler interface defines the contract for processing these callbacks.
 */
public interface CallbackServer extends Closeable {

    /**
     * Stop the callback server and release all resources.
     * This method should be idempotent.
     *
     * @throws IOException If server shutdown fails.
     */
    @Override
    void close() throws IOException;

    /**
     * Handler interface for processing host function callbacks from remote JavaScript engine.
     * <p>
     * Implementations should handle:
     * - Host function invocations (executeStep, sendError, fail, etc.)
     * - Context property reads (for dynamic context proxy)
     * - Context property writes (for state modifications)
     */
    interface HostFunctionHandler {

        /**
         * Invoke a host function.
         * Called when the remote JavaScript engine calls a registered host function.
         *
         * @param functionName Name of the function to invoke (e.g., "executeStep", "sendError").
         * @param args         Arguments passed from JavaScript.
         * @return Result of the function invocation.
         * @throws Exception If the invocation fails.
         */
        Object invokeHostFunction(String functionName, Object... args) throws Exception;

        /**
         * Get a context property value for the dynamic context proxy.
         * Called when JavaScript accesses properties on the context object.
         *
         * @param propertyPath Property path to access (e.g., "request.params", "currentKnownSubject.username").
         * @return The property value, or null if not found.
         * @throws Exception If property access fails.
         */
        default Object getContextProperty(String propertyPath) throws Exception {
            return null;
        }

        /**
         * Set a context property value (write-back from remote JavaScript engine).
         * Called when JavaScript modifies properties on the context object.
         *
         * @param propertyPath Property path to modify (e.g., "currentKnownSubject.localClaims.email").
         * @param value        The value to set.
         * @return true if the property was successfully set, false otherwise.
         * @throws Exception If property write fails.
         */
        default boolean setContextProperty(String propertyPath, Object value) throws Exception {
            return false;
        }

        /**
         * Store a complex object reference for later property access via the context proxy path.
         * Used when host functions return complex objects (e.g., JsGraalAuthenticatedUser) that
         * cannot be serialized as primitives and need to be accessed via dynamic proxy on the sidecar.
         *
         * @param obj The complex object to store.
         * @return A unique reference ID for the stored object, or null if not supported.
         */
        default String storeObjectReference(Object obj) {
            return null;
        }
    }
}
