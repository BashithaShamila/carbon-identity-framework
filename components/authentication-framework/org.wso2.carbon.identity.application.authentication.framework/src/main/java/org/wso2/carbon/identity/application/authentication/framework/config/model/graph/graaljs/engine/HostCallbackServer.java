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
import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import org.wso2.carbon.identity.application.authentication.framework.JsFunctionRegistry;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.HostFunctionRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.HostFunctionResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.SerializedValue;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.internal.FrameworkServiceDataHolder;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Server that receives host function callbacks from GraalJS sidecar.
 * When the sidecar executes JavaScript that calls host functions (executeStep,
 * sendError, etc.),
 * it sends a callback request to this server.
 */
public class HostCallbackServer implements Closeable {

    private static final Log log = LogFactory.getLog(HostCallbackServer.class);

    // Message type constants matching UdsClient
    private static final int HOST_FUNCTION_REQUEST = 5;
    private static final int HOST_FUNCTION_RESPONSE = 6;

    private final String socketPath;
    private AFUNIXServerSocket serverSocket;
    private ExecutorService executor;
    private AtomicBoolean running = new AtomicBoolean(false);
    private Thread acceptThread;

    // Map of session ID to host function handlers
    private final Map<String, HostFunctionHandler> sessionHandlers = new ConcurrentHashMap<>();

    // Singleton instance per IS process
    private static HostCallbackServer instance;
    private static final Object lock = new Object();

    /**
     * Get or create the singleton instance.
     */
    public static HostCallbackServer getInstance() {
        synchronized (lock) {
            if (instance == null) {
                String socketPath = "/tmp/graaljs-callback-" + UUID.randomUUID().toString().substring(0, 8) + ".sock";
                instance = new HostCallbackServer(socketPath);
                try {
                    instance.start();
                } catch (IOException e) {
                    log.error("Failed to start host callback server", e);
                    instance = null;
                }
            }
            return instance;
        }
    }

    /**
     * Get the socket path for the callback server.
     */
    public static String getCallbackSocketPath() {
        HostCallbackServer server = getInstance();
        return server != null ? server.socketPath : null;
    }

    private HostCallbackServer(String socketPath) {
        this.socketPath = socketPath;
    }

    /**
     * Register a handler for a session.
     * The handler will receive host function calls for this session.
     *
     * @param sessionId Session ID.
     * @param handler   Handler for host function calls.
     */
    public void registerHandler(String sessionId, HostFunctionHandler handler) {
        sessionHandlers.put(sessionId, handler);
    }

    /**
     * Unregister handler for a session.
     *
     * @param sessionId Session ID.
     */
    public void unregisterHandler(String sessionId) {
        sessionHandlers.remove(sessionId);
    }

    /**
     * Start the callback server.
     */
    public void start() throws IOException {
        if (running.get()) {
            return;
        }

        // Delete existing socket file if present
        File socketFile = new File(socketPath);
        if (socketFile.exists()) {
            socketFile.delete();
        }

        AFUNIXSocketAddress address = AFUNIXSocketAddress.of(socketFile);
        serverSocket = AFUNIXServerSocket.newInstance();
        serverSocket.bind(address);
        running.set(true);

        executor = Executors.newCachedThreadPool();

        acceptThread = new Thread(this::acceptConnections, "HostCallbackServer-Accept");
        acceptThread.setDaemon(true);
        acceptThread.start();

        log.info("Host callback server started at: " + socketPath);
    }

    private void acceptConnections() {
        log.info("[HostCallbackServer] Starting accept loop...");
        while (running.get()) {
            try {
                log.info("[HostCallbackServer] Waiting for callback connection...");
                AFUNIXSocket clientSocket = serverSocket.accept();
                log.info("[HostCallbackServer] Accepted callback connection from sidecar");
                executor.submit(() -> handleClient(clientSocket));
            } catch (IOException e) {
                if (running.get()) {
                    log.debug("Error accepting callback connection", e);
                }
            }
        }
    }

    private void handleClient(AFUNIXSocket socket) {
        log.info("[HostCallbackServer] handleClient - processing callback connection");
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            while (!socket.isClosed() && running.get()) {
                try {
                    // Read message type
                    int messageType = input.readByte();
                    log.info("[HostCallbackServer] Received message type: " + messageType);
                    if (messageType != HOST_FUNCTION_REQUEST) {
                        log.warn("[HostCallbackServer] Unexpected message type: " + messageType);
                        continue;
                    }

                    // Read length and body
                    int length = input.readInt();
                    log.info("[HostCallbackServer] Reading " + length + " bytes for host function request");
                    byte[] messageBytes = new byte[length];
                    input.readFully(messageBytes);

                    // Process the request
                    HostFunctionRequest request = HostFunctionRequest.parseFrom(messageBytes);
                    log.info("[HostCallbackServer] Processing request for function: " + request.getFunctionName());
                    HostFunctionResponse response = processHostFunctionRequest(request);

                    // Write response
                    byte[] responseBytes = response.toByteArray();
                    log.info("[HostCallbackServer] Sending response: " + responseBytes.length +
                            " bytes, success: " + response.getSuccess());
                    output.writeByte(HOST_FUNCTION_RESPONSE);
                    output.writeInt(responseBytes.length);
                    output.write(responseBytes);
                    output.flush();

                } catch (java.io.EOFException e) {
                    log.info("[HostCallbackServer] Client connection closed (EOF)");
                    break;
                }
            }
        } catch (IOException e) {
            log.debug("Client callback connection error", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log.debug("Error closing callback socket", e);
            }
        }
    }

    private HostFunctionResponse processHostFunctionRequest(HostFunctionRequest request) {
        String sessionId = request.getSessionId();
        String functionName = request.getFunctionName();
        log.info("[HostCallbackServer] Processing host function request: " + functionName +
                ", session: " + sessionId + ", args count: " + request.getArgumentsCount());

        List<Object> args = new ArrayList<>();

        for (SerializedValue sv : request.getArgumentsList()) {
            Object deserializedArg = ProtobufSerializer.fromProto(sv);
            log.info("[HostCallbackServer] Deserialized arg type: " +
                    (deserializedArg != null ? deserializedArg.getClass().getName() : "null"));
            args.add(deserializedArg);
        }

        HostFunctionHandler handler = sessionHandlers.get(sessionId);
        if (handler == null) {
            log.error("[HostCallbackServer] No handler registered for session: " + sessionId +
                    ", available sessions: " + sessionHandlers.keySet());
            return HostFunctionResponse.newBuilder()
                    .setSuccess(false)
                    .setErrorMessage("No handler for session: " + sessionId)
                    .build();
        }
        log.info("[HostCallbackServer] Found handler: " + handler.getClass().getName());

        try {
            log.info("[HostCallbackServer] Invoking host function: " + functionName + " with " + args.size() + " args");
            Object result = handler.invokeHostFunction(functionName, args.toArray());
            log.info("[HostCallbackServer] Host function returned: " +
                    (result != null ? result.getClass().getName() : "null"));
            return HostFunctionResponse.newBuilder()
                    .setSuccess(true)
                    .setResult(ProtobufSerializer.toProto(result))
                    .build();
        } catch (Exception e) {
            log.error("[HostCallbackServer] Error invoking host function: " + functionName, e);
            return HostFunctionResponse.newBuilder()
                    .setSuccess(false)
                    .setErrorMessage(e.getMessage())
                    .build();
        }
    }

    @Override
    public void close() throws IOException {
        running.set(false);

        if (acceptThread != null) {
            acceptThread.interrupt();
        }

        if (executor != null) {
            executor.shutdownNow();
        }

        if (serverSocket != null) {
            serverSocket.close();
        }

        // Clean up socket file
        new File(socketPath).delete();

        log.info("Host callback server stopped");
    }

    /**
     * Interface for handling host function calls from sidecar.
     */
    public interface HostFunctionHandler {
        /**
         * Invoke a host function.
         *
         * @param functionName Name of the function (e.g., "executeStep", "sendError").
         * @param args         Arguments passed from JavaScript.
         * @return Result of the function call.
         * @throws Exception If invocation fails.
         */
        Object invokeHostFunction(String functionName, Object... args) throws Exception;
    }
}
