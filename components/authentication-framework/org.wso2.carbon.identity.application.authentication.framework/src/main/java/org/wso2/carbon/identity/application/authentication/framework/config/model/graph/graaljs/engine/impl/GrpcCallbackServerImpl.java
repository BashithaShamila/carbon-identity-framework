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

import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.CallbackServer;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.ProtobufSerializer;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertyRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertyResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertySetRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertySetResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.HostFunctionRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.HostFunctionResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.SerializedValue;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.grpc.HostCallbackServiceGrpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * gRPC implementation of CallbackServer.
 * <p>
 * Runs a gRPC server that receives host function invocations from the remote JavaScript engine.
 * Uses the HostCallbackServiceGrpc service definition to handle three types of callbacks:
 * - invokeHostFunction: Execute host functions (executeStep, sendError, etc.)
 * - getContextProperty: Get context property values for dynamic proxy
 * - setContextProperty: Set context property values (write-back)
 * <p>
 * This implementation is thread-safe and supports multiple concurrent sessions.
 */
public class GrpcCallbackServerImpl implements CallbackServer {

    private static final Log log = LogFactory.getLog(GrpcCallbackServerImpl.class);

    private final GrpcConnectionManager connectionManager;
    private final Map<String, HostFunctionHandler> sessionHandlers = new ConcurrentHashMap<>();
    private final HostCallbackServiceImpl serviceImpl;
    private Server server;
    private int port;

    /**
     * Create a new gRPC callback server.
     * Uses default port from GrpcConnectionManager.
     */
    public GrpcCallbackServerImpl() {
        this(GrpcConnectionManager.getInstance().getCallbackPort());
    }

    /**
     * Create a new gRPC callback server with specific port.
     *
     * @param port Port to bind (0 for automatic port selection)
     */
    public GrpcCallbackServerImpl(int port) {
        this.connectionManager = GrpcConnectionManager.getInstance();
        this.serviceImpl = new HostCallbackServiceImpl();
        this.port = port;
        log.debug("[GrpcCallbackServerImpl] Created with port: " + port);
    }

    @Override
    public void registerHandler(String sessionId, HostFunctionHandler handler) {
        log.debug("[GrpcCallbackServerImpl] Registering handler for session: " + sessionId);
        sessionHandlers.put(sessionId, handler);
    }

    @Override
    public void unregisterHandler(String sessionId) {
        log.debug("[GrpcCallbackServerImpl] Unregistering handler for session: " + sessionId);
        sessionHandlers.remove(sessionId);
    }

    @Override
    public String getCallbackAddress() {
        return connectionManager.getCallbackAddress();
    }

    @Override
    public void start() throws IOException {
        if (!connectionManager.isCallbackServerStarted()) {
            log.info("[GrpcCallbackServerImpl] Starting gRPC callback server");
            server = connectionManager.getCallbackServer(serviceImpl, port);
            port = server.getPort(); // Update with actual port if 0 was used
            log.info("[GrpcCallbackServerImpl] gRPC callback server started on port: " + port);
        } else {
            log.debug("[GrpcCallbackServerImpl] gRPC callback server already started");
        }
    }

    @Override
    public void close() throws IOException {
        log.debug("[GrpcCallbackServerImpl] Close called (server lifecycle managed by GrpcConnectionManager)");
        // Note: We don't close the shared server here - it's managed by GrpcConnectionManager
        // This allows multiple RemoteJsEngine instances to share the same callback server
    }

    /**
     * gRPC service implementation for host callbacks.
     * Implements the HostCallbackService gRPC service.
     */
    private class HostCallbackServiceImpl extends HostCallbackServiceGrpc.HostCallbackServiceImplBase {

        @Override
        public void invokeHostFunction(HostFunctionRequest request,
                                        StreamObserver<HostFunctionResponse> responseObserver) {
            String sessionId = request.getSessionId();
            String functionName = request.getFunctionName();

            log.info("[GrpcCallbackServer] invokeHostFunction: " + functionName +
                    ", session: " + sessionId + ", args: " + request.getArgumentsCount());

            try {
                // Get handler for session
                HostFunctionHandler handler = sessionHandlers.get(sessionId);
                if (handler == null) {
                    String errorMsg = "No handler registered for session: " + sessionId;
                    log.error("[GrpcCallbackServer] " + errorMsg);
                    responseObserver.onNext(HostFunctionResponse.newBuilder()
                            .setSuccess(false)
                            .setErrorMessage(errorMsg)
                            .build());
                    responseObserver.onCompleted();
                    return;
                }

                // Deserialize arguments
                List<Object> args = new ArrayList<>();
                for (SerializedValue sv : request.getArgumentsList()) {
                    Object deserializedArg = ProtobufSerializer.fromProto(sv);
                    args.add(deserializedArg);
                }

                // Invoke handler
                Object result = handler.invokeHostFunction(functionName, args.toArray());

                // Build response
                HostFunctionResponse response = HostFunctionResponse.newBuilder()
                        .setSuccess(true)
                        .setResult(ProtobufSerializer.toProto(result))
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();

                log.info("[GrpcCallbackServer] invokeHostFunction completed successfully");

            } catch (Exception e) {
                log.error("[GrpcCallbackServer] Error invoking host function: " + functionName, e);
                responseObserver.onNext(HostFunctionResponse.newBuilder()
                        .setSuccess(false)
                        .setErrorMessage(e.getMessage())
                        .build());
                responseObserver.onCompleted();
            }
        }

        @Override
        public void getContextProperty(ContextPropertyRequest request,
                                        StreamObserver<ContextPropertyResponse> responseObserver) {
            String sessionId = request.getSessionId();
            String propertyPath = request.getPropertyPath();

            log.debug("[GrpcCallbackServer] getContextProperty: " + propertyPath + ", session: " + sessionId);

            try {
                // Get handler for session
                HostFunctionHandler handler = sessionHandlers.get(sessionId);
                if (handler == null) {
                    String errorMsg = "No handler registered for session: " + sessionId;
                    log.error("[GrpcCallbackServer] " + errorMsg);
                    responseObserver.onNext(ContextPropertyResponse.newBuilder()
                            .setSuccess(false)
                            .setErrorMessage(errorMsg)
                            .build());
                    responseObserver.onCompleted();
                    return;
                }

                // Get property value
                Object value = handler.getContextProperty(propertyPath);

                // Build response
                ContextPropertyResponse.Builder responseBuilder = ContextPropertyResponse.newBuilder()
                        .setSuccess(true);

                if (value != null) {
                    // Check if value is a proxy type
                    boolean isProxy = isProxyType(value);
                    responseBuilder.setIsProxy(isProxy);

                    if (isProxy) {
                        responseBuilder.setProxyType(getProxyType(value));
                        // Add member keys if available
                        if (value instanceof org.graalvm.polyglot.proxy.ProxyObject) {
                            Object keys = ((org.graalvm.polyglot.proxy.ProxyObject) value).getMemberKeys();
                            if (keys instanceof String[]) {
                                for (String key : (String[]) keys) {
                                    responseBuilder.addMemberKeys(key);
                                }
                            }
                        }
                    } else {
                        responseBuilder.setValue(ProtobufSerializer.toProto(value));
                    }
                }

                responseObserver.onNext(responseBuilder.build());
                responseObserver.onCompleted();

            } catch (Exception e) {
                log.error("[GrpcCallbackServer] Error getting context property: " + propertyPath, e);
                responseObserver.onNext(ContextPropertyResponse.newBuilder()
                        .setSuccess(false)
                        .setErrorMessage(e.getMessage())
                        .build());
                responseObserver.onCompleted();
            }
        }

        @Override
        public void setContextProperty(ContextPropertySetRequest request,
                                        StreamObserver<ContextPropertySetResponse> responseObserver) {
            String sessionId = request.getSessionId();
            String propertyPath = request.getPropertyPath();

            log.info("[GrpcCallbackServer] setContextProperty: " + propertyPath + ", session: " + sessionId);

            try {
                // Get handler for session
                HostFunctionHandler handler = sessionHandlers.get(sessionId);
                if (handler == null) {
                    String errorMsg = "No handler registered for session: " + sessionId;
                    log.error("[GrpcCallbackServer] " + errorMsg);
                    responseObserver.onNext(ContextPropertySetResponse.newBuilder()
                            .setSuccess(false)
                            .setErrorMessage(errorMsg)
                            .build());
                    responseObserver.onCompleted();
                    return;
                }

                // Deserialize value
                Object javaValue = ProtobufSerializer.fromProto(request.getValue());

                // Set property
                boolean success = handler.setContextProperty(propertyPath, javaValue);

                // Build response
                ContextPropertySetResponse response = ContextPropertySetResponse.newBuilder()
                        .setSuccess(success)
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();

            } catch (Exception e) {
                log.error("[GrpcCallbackServer] Error setting context property: " + propertyPath, e);
                responseObserver.onNext(ContextPropertySetResponse.newBuilder()
                        .setSuccess(false)
                        .setErrorMessage(e.getMessage())
                        .build());
                responseObserver.onCompleted();
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
         * Get the proxy type name for a value.
         */
        private String getProxyType(Object value) {
            String className = value.getClass().getSimpleName();
            if (className.startsWith("JsGraal")) {
                return className.substring(7).toLowerCase();
            } else if (className.startsWith("Js")) {
                return className.substring(2).toLowerCase();
            }
            return className.toLowerCase();
        }
    }
}
