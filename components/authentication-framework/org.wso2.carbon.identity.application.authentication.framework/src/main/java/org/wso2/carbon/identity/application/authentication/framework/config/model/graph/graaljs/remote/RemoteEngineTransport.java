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

import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.proto.EvaluateRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.proto.EvaluateResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.proto.ExecuteCallbackRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.proto.ExecuteCallbackResponse;

import java.io.Closeable;
import java.io.IOException;

/**
 * Abstraction for remote JavaScript engine transport layer.
 * Provides request/response communication between the Identity Server and the remote JavaScript execution engine.
 * <p>
 * Implementations can use different transport mechanisms (gRPC, HTTP, etc.)
 * while maintaining the same protocol for script evaluation and callback execution.
 * <p>
 * This interface decouples the RemoteJsEngine from specific transport implementations,
 * allowing per-instance transport selection and future extensibility.
 */
public interface RemoteEngineTransport extends Closeable {

    /**
     * Send an evaluation request to the remote JavaScript engine.
     *
     * @param request The evaluation request containing script and bindings.
     * @return The evaluation response with results and updated bindings.
     * @throws IOException If communication with the remote engine fails.
     */
    EvaluateResponse sendEvaluate(EvaluateRequest request) throws IOException;

    /**
     * Send a callback execution request to the remote JavaScript engine.
     * Used for executing serialized callback functions in later authentication steps.
     *
     * @param request The callback execution request containing function source and arguments.
     * @return The callback execution response with results and updated bindings.
     * @throws IOException If communication with the remote engine fails.
     */
    ExecuteCallbackResponse sendExecuteCallback(ExecuteCallbackRequest request) throws IOException;

    /**
     * Send an evaluation request along with the remote engine for this session.
     * In bidirectional streaming mode, the engine is captured in the stream closure
     * for handling callbacks (host functions, context property access) inline.
     *
     * @param request The evaluation request containing script and bindings.
     * @param engine  The remote engine for handling callbacks on this session.
     * @return The evaluation response with results and updated bindings.
     * @throws IOException If communication with the remote engine fails.
     */
    default EvaluateResponse sendEvaluate(EvaluateRequest request,
                                          RemoteJsEngine engine) throws IOException {

        return sendEvaluate(request);
    }

    /**
     * Send a callback execution request along with the remote engine for this session.
     * In bidirectional streaming mode, the engine is captured in the stream closure
     * for handling callbacks (host functions, context property access) inline.
     *
     * @param request The callback execution request containing function source and arguments.
     * @param engine  The remote engine for handling callbacks on this session.
     * @return The callback execution response with results and updated bindings.
     * @throws IOException If communication with the remote engine fails.
     */
    default ExecuteCallbackResponse sendExecuteCallback(ExecuteCallbackRequest request,
                                                        RemoteJsEngine engine) throws IOException {

        return sendExecuteCallback(request);
    }

    /**
     * Establish connection to the remote JavaScript engine.
     * This method should be idempotent - calling it multiple times should not create multiple connections.
     *
     * @throws IOException If connection establishment fails.
     */
    void connect() throws IOException;

    /**
     * Check if the transport is currently connected to the remote engine.
     *
     * @return true if connected and ready for communication, false otherwise.
     */
    boolean isConnected();

    /**
     * Close the transport connection and release any resources.
     * This method should be idempotent.
     *
     * @throws IOException If closing the connection fails.
     */
    @Override
    void close() throws IOException;
}
