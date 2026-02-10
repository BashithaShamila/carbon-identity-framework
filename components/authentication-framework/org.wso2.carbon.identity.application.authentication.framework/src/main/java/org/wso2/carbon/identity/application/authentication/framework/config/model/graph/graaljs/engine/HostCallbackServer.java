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
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertyRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertyResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertySetRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertySetResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.HostFunctionRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.HostFunctionResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.SerializedValue;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
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
    private static final int CONTEXT_PROPERTY_REQUEST = 7;
    private static final int CONTEXT_PROPERTY_RESPONSE = 8;
    private static final int CONTEXT_PROPERTY_SET_REQUEST = 9;
    private static final int CONTEXT_PROPERTY_SET_RESPONSE = 10;

    private final String socketPath;
    private AFUNIXServerSocket serverSocket;
    private ExecutorService executor;
    private AtomicBoolean running = new AtomicBoolean(false);
    private Thread acceptThread;

    // Map of session ID to host function handlers
    private final Map<String, CallbackServer.HostFunctionHandler> sessionHandlers = new ConcurrentHashMap<>();

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
    public void registerHandler(String sessionId, CallbackServer.HostFunctionHandler handler) {
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
                    log.debug("[HostCallbackServer] Received message type: " + messageType);

                    // Read length and body
                    int length = input.readInt();
                    log.debug("[HostCallbackServer] Reading " + length + " bytes");
                    byte[] messageBytes = new byte[length];
                    input.readFully(messageBytes);

                    // Dispatch based on message type
                    if (messageType == HOST_FUNCTION_REQUEST) {
                        HostFunctionRequest request = HostFunctionRequest.parseFrom(messageBytes);

                        log.debug("[HostCallbackServer] HostFunctionRequest: " + request);

                        log.info("[HostCallbackServer] Processing host function: " + request.getFunctionName());
                        HostFunctionResponse response = processHostFunctionRequest(request);

                        log.debug("[HostCallbackServer] HostFunctionResponse: " + response);


                        byte[] responseBytes = response.toByteArray();
                        output.writeByte(HOST_FUNCTION_RESPONSE);
                        output.writeInt(responseBytes.length);
                        output.write(responseBytes);
                        output.flush();

                    } else if (messageType == CONTEXT_PROPERTY_REQUEST) {
                        ContextPropertyRequest request = ContextPropertyRequest.parseFrom(messageBytes);

                        log.debug("[HostCallbackServer] ContextPropertyRequest: " + request);

                        log.debug("[HostCallbackServer] Processing context property: " + request.getPropertyPath());
                        ContextPropertyResponse response = processContextPropertyRequest(request);

                        log.debug("[HostCallbackServer] ContextPropertyResponse: " + response);


                        byte[] responseBytes = response.toByteArray();
                        output.writeByte(CONTEXT_PROPERTY_RESPONSE);
                        output.writeInt(responseBytes.length);
                        output.write(responseBytes);
                        output.flush();

                    } else if (messageType == CONTEXT_PROPERTY_SET_REQUEST) {
                        ContextPropertySetRequest request = ContextPropertySetRequest.parseFrom(messageBytes);

                        log.debug("[HostCallbackServer] ContextPropertySetRequest: " + request);

                        log.info("[HostCallbackServer] Processing context property SET: " +
                                request.getPropertyPath());
                        ContextPropertySetResponse response = processContextPropertySetRequest(request);

                        log.debug("[HostCallbackServer] ContextPropertySetResponse: " + response);


                        byte[] responseBytes = response.toByteArray();
                        output.writeByte(CONTEXT_PROPERTY_SET_RESPONSE);
                        output.writeInt(responseBytes.length);
                        output.write(responseBytes);
                        output.flush();

                    } else {
                        log.warn("[HostCallbackServer] Unknown message type: " + messageType);
                    }

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

        CallbackServer.HostFunctionHandler handler = sessionHandlers.get(sessionId);
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
            log.info("[HostCallbackServer] Invoking host function: " + functionName + " with " + args.size()
                    + " args");
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

    /**
     * Process a context property request from the sidecar.
     * This enables the dynamic context proxy to access any property from the real
     * JsGraalAuthenticationContext.
     */
    private ContextPropertyResponse processContextPropertyRequest(ContextPropertyRequest request) {
        String sessionId = request.getSessionId();
        String propertyPath = request.getPropertyPath();
        log.info("[HostCallbackServer] Processing context property request: " + propertyPath + ", session: "
                + sessionId + ", proxyType: " + request.getProxyType());

        CallbackServer.HostFunctionHandler handler = sessionHandlers.get(sessionId);
        if (handler == null) {
            log.error("[HostCallbackServer] No handler for session: " + sessionId);
            return ContextPropertyResponse.newBuilder()
                    .setSuccess(false)
                    .setErrorMessage("No handler for session: " + sessionId)
                    .build();
        }

        try {
            // Delegate to handler to get the property value
            Object value = handler.getContextProperty(propertyPath);

            log.info("[HostCallbackServer] Retrieved property '" + propertyPath + "' - type: " +
                    (value != null ? value.getClass().getSimpleName() : "null"));

            if (value == null) {
                return ContextPropertyResponse.newBuilder()
                        .setSuccess(true)
                        .build();
            }

            // Handle __keys__ special path - value is the member keys array
            if (propertyPath.endsWith("::__keys__") || "__keys__".equals(propertyPath)) {
                ContextPropertyResponse.Builder keysBuilder = ContextPropertyResponse.newBuilder()
                        .setSuccess(true);
                extractMemberKeys(value, keysBuilder);
                return keysBuilder.build();
            }

            // Determine if this is a proxy type that needs nested access
            boolean isProxy = isProxyType(value);
            String proxyType = isProxy ? getProxyType(value) : "";

            ContextPropertyResponse.Builder responseBuilder = ContextPropertyResponse.newBuilder()
                    .setSuccess(true)
                    .setIsProxy(isProxy)
                    .setProxyType(proxyType);

            // If it's a proxy type, we don't serialize the value - just indicate it's a
            // proxy
            // The sidecar will create a nested DynamicContextProxy for it
            if (!isProxy) {
                responseBuilder.setValue(ProtobufSerializer.toProto(value));
            }

            // Add member keys if this is a proxy type
            if (isProxy && value instanceof org.graalvm.polyglot.proxy.ProxyObject) {
                Object keys = ((org.graalvm.polyglot.proxy.ProxyObject) value).getMemberKeys();
                extractMemberKeys(keys, responseBuilder);
            }

            log.info("[HostCallbackServer] Returning context property response - success: true, isProxy: " +
                    isProxy + ", proxyType: " + proxyType);

            return responseBuilder.build();

        } catch (Exception e) {
            log.error("[HostCallbackServer] Error getting context property: " + propertyPath, e);
            return ContextPropertyResponse.newBuilder()
                    .setSuccess(false)
                    .setErrorMessage(e.getMessage())
                    .build();
        }
    }

    /**
     * Process a context property SET request from the sidecar (write-back).
     * This enables scripts to modify context properties like user.localClaims.
     */
    private ContextPropertySetResponse processContextPropertySetRequest(ContextPropertySetRequest request) {
        String sessionId = request.getSessionId();
        String propertyPath = request.getPropertyPath();
        SerializedValue value = request.getValue();

        log.info("[HostCallbackServer] Processing context property SET: " + propertyPath + ", session: "
                + sessionId);

        CallbackServer.HostFunctionHandler handler = sessionHandlers.get(sessionId);
        if (handler == null) {
            log.error("[HostCallbackServer] No handler for session: " + sessionId);
            return ContextPropertySetResponse.newBuilder()
                    .setSuccess(false)
                    .setErrorMessage("No handler for session: " + sessionId)
                    .build();
        }

        try {
            // Deserialize the value
            Object javaValue = ProtobufSerializer.fromProto(value);

            // Delegate to handler to set the property value
            boolean success = handler.setContextProperty(propertyPath, javaValue);

            if (success) {
                log.info("[HostCallbackServer] Successfully set property: " + propertyPath);
                return ContextPropertySetResponse.newBuilder()
                        .setSuccess(true)
                        .build();
            } else {
                log.warn("[HostCallbackServer] Handler returned false for set: " + propertyPath);
                return ContextPropertySetResponse.newBuilder()
                        .setSuccess(false)
                        .setErrorMessage("Property not writable: " + propertyPath)
                        .build();
            }

        } catch (Exception e) {
            log.error("[HostCallbackServer] Error setting context property: " + propertyPath, e);
            return ContextPropertySetResponse.newBuilder()
                    .setSuccess(false)
                    .setErrorMessage(e.getMessage())
                    .build();
        }
    }

    /**
     * Check if the value is a proxy type that needs nested access.
     */
    private boolean isProxyType(Object value) {
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
     * Extract member keys from various return types of getMemberKeys().
     */
    private void extractMemberKeys(Object keys, ContextPropertyResponse.Builder responseBuilder) {
        if (keys instanceof String[]) {
            for (String key : (String[]) keys) {
                responseBuilder.addMemberKeys(key);
            }
        } else if (keys instanceof Object[]) {
            for (Object key : (Object[]) keys) {
                responseBuilder.addMemberKeys(String.valueOf(key));
            }
        } else if (keys instanceof org.graalvm.polyglot.proxy.ProxyArray) {
            org.graalvm.polyglot.proxy.ProxyArray proxyArray =
                    (org.graalvm.polyglot.proxy.ProxyArray) keys;
            long size = proxyArray.getSize();
            for (long i = 0; i < size; i++) {
                responseBuilder.addMemberKeys(String.valueOf(proxyArray.get(i)));
            }
        }
    }

    /**
     * Get the proxy type name for a value.
     */
    private String getProxyType(Object value) {
        String className = value.getClass().getSimpleName();
        if (className.startsWith("JsGraal")) {
            return className.substring(7).toLowerCase(); // JsGraalServletRequest -> servletrequest
        } else if (className.startsWith("Js")) {
            return className.substring(2).toLowerCase(); // JsStep -> step
        }
        return className.toLowerCase();
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
     *
     * @deprecated Use {@link CallbackServer.HostFunctionHandler} instead.
     *             This interface is maintained for backward compatibility and extends
     *             the abstracted CallbackServer.HostFunctionHandler interface.
     */
    @Deprecated
    public interface HostFunctionHandler extends CallbackServer.HostFunctionHandler {
        // This interface is now empty and extends CallbackServer.HostFunctionHandler
        // for backward compatibility with existing code that references
        // HostCallbackServer.HostFunctionHandler directly.
    }
}
