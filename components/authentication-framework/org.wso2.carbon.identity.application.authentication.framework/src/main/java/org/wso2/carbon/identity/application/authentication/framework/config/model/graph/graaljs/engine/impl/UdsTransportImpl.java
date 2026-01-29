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

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteEngineTransport;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.UdsClient;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.EvaluateRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.EvaluateResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ExecuteCallbackRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ExecuteCallbackResponse;

import java.io.IOException;

/**
 * Unix Domain Socket implementation of RemoteEngineTransport.
 * <p>
 * This implementation wraps the existing UdsClient to provide transport abstraction
 * while maintaining backward compatibility with the current UDS-based communication protocol.
 * <p>
 * Uses length-prefixed Protocol Buffer messages over Unix Domain Sockets for
 * reliable, low-latency communication with the GraalJS sidecar process.
 */
public class UdsTransportImpl implements RemoteEngineTransport {

    private static final Log log = LogFactory.getLog(UdsTransportImpl.class);

    private final UdsClient client;
    private final String socketPath;

    /**
     * Create a new UDS transport.
     *
     * @param socketPath Path to the Unix Domain Socket.
     */
    public UdsTransportImpl(String socketPath) {
        this.socketPath = socketPath;
        this.client = new UdsClient(socketPath);
        log.debug("UdsTransportImpl created for socket: " + socketPath);
    }

    @Override
    public EvaluateResponse sendEvaluate(EvaluateRequest request) throws IOException {
        log.debug("[UdsTransportImpl] Sending evaluate request");
        return client.sendEvaluate(request);
    }

    @Override
    public ExecuteCallbackResponse sendExecuteCallback(ExecuteCallbackRequest request) throws IOException {
        log.debug("[UdsTransportImpl] Sending executeCallback request");
        return client.sendExecuteCallback(request);
    }

    @Override
    public void connect() throws IOException {
        log.debug("[UdsTransportImpl] Connecting to socket: " + socketPath);
        client.connect();
    }

    @Override
    public boolean isConnected() {
        return client.isConnected();
    }

    @Override
    public void close() throws IOException {
        log.debug("[UdsTransportImpl] Closing transport");
        client.close();
    }
}
