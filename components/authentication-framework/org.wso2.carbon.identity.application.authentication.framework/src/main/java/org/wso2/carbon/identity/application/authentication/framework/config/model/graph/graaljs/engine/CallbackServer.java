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
 * When the remote JavaScript engine executes host functions (like executeStep, sendError, etc.),
 * it sends callback requests to this server. The server routes these callbacks to the appropriate
 * handler based on session ID.
 * <p>
 * Implementations can use different transport mechanisms (Unix Domain Sockets, gRPC, HTTP, etc.)
 * for receiving callbacks while maintaining the same handler contract.
 */
public interface CallbackServer extends Closeable {

    /**
     * Register a handler for a specific session.
     * The handler will receive all host function callbacks for the registered session.
     *
     * @param sessionId The session ID to associate with this handler.
     * @param handler   The handler that will process host function invocations.
     */
    void registerHandler(String sessionId, HostFunctionHandler handler);

    /**
     * Unregister the handler for a specific session.
     * After this call, the session will no longer receive callbacks.
     *
     * @param sessionId The session ID to unregister.
     */
    void unregisterHandler(String sessionId);

    /**
     * Get the callback address that remote engines should use to connect back to this server.
     * <p>
     * The format of the address depends on the transport implementation:
     * - UDS: Socket file path (e.g., "/tmp/graaljs-callback.sock")
     * - gRPC: Host and port (e.g., "localhost:50051")
     * - HTTP: URL (e.g., "http://localhost:8080/callback")
     *
     * @return The callback address string.
     */
    String getCallbackAddress();

    /**
     * Start the callback server.
     * This method should be idempotent - calling it multiple times should not start multiple servers.
     *
     * @throws IOException If server startup fails.
     */
    void start() throws IOException;

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
    }
}
