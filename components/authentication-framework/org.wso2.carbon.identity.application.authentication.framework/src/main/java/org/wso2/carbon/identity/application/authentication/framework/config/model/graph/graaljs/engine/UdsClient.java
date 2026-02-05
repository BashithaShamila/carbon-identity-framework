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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.EvaluateRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.EvaluateResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ExecuteCallbackRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ExecuteCallbackResponse;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Unix Domain Socket client for communicating with the GraalJS sidecar.
 * Uses junixsocket for cross-platform UDS support.
 * <p>
 * Protocol: Length-prefixed Protocol Buffers messages.
 * Each message is prefixed with a 4-byte big-endian length.
 */
public class UdsClient implements Closeable {

    private static final Log log = LogFactory.getLog(UdsClient.class);

    private AFUNIXSocket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private final String socketPath;
    private boolean connected = false;

    /**
     * Create a new UDS client.
     *
     * @param socketPath Path to the Unix domain socket.
     */
    public UdsClient(String socketPath) {
        this.socketPath = socketPath;
    }

    /**
     * Connect to the sidecar.
     *
     * @throws IOException If connection fails.
     */
    public void connect() throws IOException {
        if (connected) {
            return;
        }

        File socketFile = new File(socketPath);
        AFUNIXSocketAddress address = AFUNIXSocketAddress.of(socketFile);
        socket = AFUNIXSocket.newInstance();
        socket.connect(address);
        outputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = new DataInputStream(socket.getInputStream());
        connected = true;

        log.debug("Connected to GraalJS sidecar at: " + socketPath);
    }

    /**
     * Check if connected to the sidecar.
     *
     * @return true if connected.
     */
    public boolean isConnected() {
        return connected && socket != null && socket.isConnected();
    }

    /**
     * Send an evaluate request and receive response.
     *
     * @param request The evaluation request.
     * @return The evaluation response.
     * @throws IOException If communication fails.
     */
    public EvaluateResponse sendEvaluate(EvaluateRequest request) throws IOException {
        ensureConnected();

        log.info("[UdsClient] EvaluateRequest: " + request);


        // Write request (length-prefixed)
        byte[] requestBytes = request.toByteArray();
        writeMessage(MessageType.EVALUATE_REQUEST, requestBytes);

        // Read response (length-prefixed)
        byte[] responseBytes = readMessage();
        EvaluateResponse response = EvaluateResponse.parseFrom(responseBytes);

        log.info("[UdsClient] EvaluateResponse: " + response);

        return response;
    }

    /**
     * Send an execute callback request and receive response.
     *
     * @param request The callback execution request.
     * @return The callback execution response.
     * @throws IOException If communication fails.
     */
    public ExecuteCallbackResponse sendExecuteCallback(ExecuteCallbackRequest request) throws IOException {
        ensureConnected();


        log.info("[UdsClient] ExecuteCallbackRequest: " + request);

        // Write request (length-prefixed)
        byte[] requestBytes = request.toByteArray();
        writeMessage(MessageType.EXECUTE_CALLBACK_REQUEST, requestBytes);

        // Read response (length-prefixed)
        byte[] responseBytes = readMessage();
        ExecuteCallbackResponse response = ExecuteCallbackResponse.parseFrom(responseBytes);

        log.info("[UdsClient] ExecuteCallbackResponse: " + response);

        return response;
    }

    /**
     * Write a length-prefixed message.
     *
     * @param type    Message type.
     * @param message Message bytes.
     * @throws IOException If write fails.
     */
    private void writeMessage(MessageType type, byte[] message) throws IOException {
        // Write message type (1 byte)
        outputStream.writeByte(type.getValue());
        // Write message length (4 bytes, big-endian)
        outputStream.writeInt(message.length);
        // Write message body
        outputStream.write(message);
        outputStream.flush();
    }

    /**
     * Read a length-prefixed message.
     *
     * @return Message bytes.
     * @throws IOException If read fails.
     */
    private byte[] readMessage() throws IOException {
        // Read message type (1 byte) - we can ignore for now as we know what to expect
        int type = inputStream.readByte();
        // Read message length (4 bytes, big-endian)
        int length = inputStream.readInt();
        // Read message body
        byte[] message = new byte[length];
        inputStream.readFully(message);
        return message;
    }

    private void ensureConnected() throws IOException {
        if (!isConnected()) {
            connect();
        }
    }

    @Override
    public void close() throws IOException {
        connected = false;
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                log.debug("Error closing output stream", e);
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.debug("Error closing input stream", e);
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                log.debug("Error closing socket", e);
            }
        }
        log.debug("UDS client closed");
    }

    /**
     * Message types for the protocol.
     */
    public enum MessageType {
        EVALUATE_REQUEST(1),
        EVALUATE_RESPONSE(2),
        EXECUTE_CALLBACK_REQUEST(3),
        EXECUTE_CALLBACK_RESPONSE(4),
        HOST_FUNCTION_REQUEST(5),
        HOST_FUNCTION_RESPONSE(6),
        CONTEXT_PROPERTY_REQUEST(7),
        CONTEXT_PROPERTY_RESPONSE(8),
        CONTEXT_PROPERTY_SET_REQUEST(9),
        CONTEXT_PROPERTY_SET_RESPONSE(10);

        private final int value;

        MessageType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static MessageType fromValue(int value) {
            for (MessageType type : values()) {
                if (type.value == value) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown message type: " + value);
        }
    }
}
