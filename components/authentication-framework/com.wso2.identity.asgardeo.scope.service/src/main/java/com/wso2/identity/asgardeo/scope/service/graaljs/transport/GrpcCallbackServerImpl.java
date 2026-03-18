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

package com.wso2.identity.asgardeo.scope.service.graaljs.transport;

// ============================================================================
// STALE CODE - OLD UNIDIRECTIONAL 2-CHANNEL gRPC APPROACH
// ============================================================================
// This class ran a SEPARATE gRPC server (port 50052) to receive host function
// callbacks from the sidecar via unary RPCs (HostCallbackService).
//
// In the old approach:
//   IS → Sidecar: unary RPCs on port 50051 (via GrpcTransportImpl)
//   Sidecar → IS: unary RPCs on port 50052 (via this GrpcCallbackServerImpl)
//
// Replaced by: GrpcStreamingTransportImpl which handles callbacks inline on
// the same bidirectional stream (port 50051 only, no separate callback server).
//
// TODO: Remove entirely during transport layer refactoring.
// ============================================================================

/*
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

public class GrpcCallbackServerImpl implements CallbackServer {

    private static final Log log = LogFactory.getLog(GrpcCallbackServerImpl.class);

    private final GrpcConnectionManager connectionManager;
    private final Map<String, HostFunctionHandler> sessionHandlers = new ConcurrentHashMap<>();
    private final HostCallbackServiceImpl serviceImpl;
    private Server server;
    private int port;

    public GrpcCallbackServerImpl() {
        this(GrpcConnectionManager.getInstance().getCallbackPort());
    }

    public GrpcCallbackServerImpl(int port) {
        log.info("[GrpcCallbackServerImpl] ========== CONSTRUCTOR ==========");
        log.info("[GrpcCallbackServerImpl] Creating callback server with port: " + port);
        log.info("[GrpcCallbackServerImpl] Instance hashCode: " + System.identityHashCode(this));
        this.connectionManager = GrpcConnectionManager.getInstance();
        this.serviceImpl = new HostCallbackServiceImpl();
        this.port = port;
        log.info("[GrpcCallbackServerImpl] sessionHandlers map hashCode: " + System.identityHashCode(sessionHandlers));
        log.info("[GrpcCallbackServerImpl] ========== CONSTRUCTOR COMPLETED ==========");
    }

    @Override
    public void registerHandler(String sessionId, HostFunctionHandler handler) {
        log.info("[GrpcCallbackServerImpl] ========== registerHandler() ==========");
        log.info("[GrpcCallbackServerImpl] Registering handler for session: " + sessionId);
        log.info("[GrpcCallbackServerImpl] Handler type: " + (handler != null ? handler.getClass().getName() : "NULL"));
        log.info("[GrpcCallbackServerImpl] CallbackServer instance hashCode: " + System.identityHashCode(this));
        log.info("[GrpcCallbackServerImpl] sessionHandlers map hashCode: " + System.identityHashCode(sessionHandlers));
        log.info("[GrpcCallbackServerImpl] BEFORE - registered sessions: " + sessionHandlers.keySet());
        log.info("[GrpcCallbackServerImpl] BEFORE - session count: " + sessionHandlers.size());

        sessionHandlers.put(sessionId, handler);

        log.info("[GrpcCallbackServerImpl] AFTER - registered sessions: " + sessionHandlers.keySet());
        log.info("[GrpcCallbackServerImpl] AFTER - session count: " + sessionHandlers.size());
        log.info("[GrpcCallbackServerImpl] Handler registered successfully for session: " + sessionId);
        log.info("[GrpcCallbackServerImpl] ========== registerHandler() COMPLETED ==========");
    }

    @Override
    public void unregisterHandler(String sessionId) {
        log.info("[GrpcCallbackServerImpl] ========== unregisterHandler() ==========");
        log.info("[GrpcCallbackServerImpl] Unregistering handler for session: " + sessionId);
        log.info("[GrpcCallbackServerImpl] CallbackServer instance hashCode: " + System.identityHashCode(this));
        log.info("[GrpcCallbackServerImpl] BEFORE - registered sessions: " + sessionHandlers.keySet());

        HostFunctionHandler removed = sessionHandlers.remove(sessionId);

        log.info("[GrpcCallbackServerImpl] Removed handler: " + (removed != null ? "YES" : "NO (was not registered)"));
        log.info("[GrpcCallbackServerImpl] AFTER - registered sessions: " + sessionHandlers.keySet());
        log.info("[GrpcCallbackServerImpl] ========== unregisterHandler() COMPLETED ==========");
    }

    @Override
    public String getCallbackAddress() {
        String address = connectionManager.getCallbackAddress();
        log.info("[GrpcCallbackServerImpl] getCallbackAddress() returning: " + address);
        return address;
    }

    @Override
    public void start() throws IOException {
        log.info("[GrpcCallbackServerImpl] ========== start() ==========");
        log.info("[GrpcCallbackServerImpl] CallbackServer instance hashCode: " + System.identityHashCode(this));
        log.info("[GrpcCallbackServerImpl] isCallbackServerStarted: " + connectionManager.isCallbackServerStarted());

        if (!connectionManager.isCallbackServerStarted()) {
            log.info("[GrpcCallbackServerImpl] Starting NEW gRPC callback server on port: " + port);
            server = connectionManager.getCallbackServer(serviceImpl, port);
            port = server.getPort();
            log.info("[GrpcCallbackServerImpl] gRPC callback server started successfully!");
            log.info("[GrpcCallbackServerImpl] Actual port: " + port);
            log.info("[GrpcCallbackServerImpl] Callback address: " + getCallbackAddress());
        } else {
            log.info("[GrpcCallbackServerImpl] gRPC callback server ALREADY started - reusing existing");
            log.info("[GrpcCallbackServerImpl] Current registered sessions: " + sessionHandlers.keySet());
        }
        log.info("[GrpcCallbackServerImpl] ========== start() COMPLETED ==========");
    }

    @Override
    public void close() throws IOException {
        log.info("[GrpcCallbackServerImpl] ========== close() ==========");
        log.info("[GrpcCallbackServerImpl] Close called - server lifecycle managed by GrpcConnectionManager");
        log.info("[GrpcCallbackServerImpl] Current registered sessions: " + sessionHandlers.keySet());
        log.info("[GrpcCallbackServerImpl] ========== close() COMPLETED ==========");
    }

    private class HostCallbackServiceImpl extends HostCallbackServiceGrpc.HostCallbackServiceImplBase {

        @Override
        public void invokeHostFunction(HostFunctionRequest request,
                                        StreamObserver<HostFunctionResponse> responseObserver) {
            String sessionId = request.getSessionId();
            String functionName = request.getFunctionName();

            log.info("[GrpcCallbackServer] ========== invokeHostFunction() RECEIVED ==========");
            log.info("[GrpcCallbackServer] Function: " + functionName);
            log.info("[GrpcCallbackServer] Session: " + sessionId);
            log.info("[GrpcCallbackServer] Arguments count: " + request.getArgumentsCount());
            log.info("[GrpcCallbackServer] CallbackServer hashCode: " +
                    System.identityHashCode(GrpcCallbackServerImpl.this));
            log.info("[GrpcCallbackServer] sessionHandlers map hashCode: " + System.identityHashCode(sessionHandlers));
            log.info("[GrpcCallbackServer] Currently registered sessions: " + sessionHandlers.keySet());
            log.info("[GrpcCallbackServer] Session count: " + sessionHandlers.size());

            for (int i = 0; i < request.getArgumentsCount(); i++) {
                SerializedValue sv = request.getArguments(i);
                log.info("[GrpcCallbackServer] Argument[" + i + "] valueCase: " + sv.getValueCase());
                if (sv.getValueCase() == SerializedValue.ValueCase.STRING_VALUE) {
                    String strVal = sv.getStringValue();
                    log.info("[GrpcCallbackServer] Argument[" + i + "] string preview: " +
                            strVal.substring(0, Math.min(100, strVal.length())) + "...");
                }
            }

            try {
                log.info("[GrpcCallbackServer] Looking up handler for session: " + sessionId);
                HostFunctionHandler handler = sessionHandlers.get(sessionId);

                if (handler == null) {
                    String errorMsg = "No handler registered for session: " + sessionId;
                    log.error("[GrpcCallbackServer] ==========================================");
                    log.error("[GrpcCallbackServer] HANDLER NOT FOUND!");
                    log.error("[GrpcCallbackServer] Requested session: " + sessionId);
                    log.error("[GrpcCallbackServer] Available sessions: " + sessionHandlers.keySet());
                    log.error("[GrpcCallbackServer] Session count: " + sessionHandlers.size());
                    log.error("[GrpcCallbackServer] ==========================================");
                    responseObserver.onNext(HostFunctionResponse.newBuilder()
                            .setSuccess(false)
                            .setErrorMessage(errorMsg)
                            .build());
                    responseObserver.onCompleted();
                    return;
                }

                log.info("[GrpcCallbackServer] Handler FOUND for session: " + sessionId);
                log.info("[GrpcCallbackServer] Handler type: " + handler.getClass().getName());

                log.info("[GrpcCallbackServer] Deserializing " + request.getArgumentsCount() + " arguments...");
                List<Object> args = new ArrayList<>();
                for (int i = 0; i < request.getArgumentsCount(); i++) {
                    SerializedValue sv = request.getArguments(i);
                    Object deserializedArg = ProtobufSerializer.fromProto(sv);
                    args.add(deserializedArg);
                    log.info("[GrpcCallbackServer] Deserialized arg[" + i + "]: " +
                            (deserializedArg != null ? deserializedArg.getClass().getSimpleName() : "null"));
                }

                log.info("[GrpcCallbackServer] >>> Invoking handler." + functionName +
                        "() with " + args.size() + " args");
                Object result = handler.invokeHostFunction(functionName, args.toArray());
                log.info("[GrpcCallbackServer] <<< Handler returned: " +
                        (result != null ? result.getClass().getSimpleName() : "null"));

                log.info("[GrpcCallbackServer] Serializing result to proto...");
                HostFunctionResponse response = HostFunctionResponse.newBuilder()
                        .setSuccess(true)
                        .setResult(ProtobufSerializer.toProto(result))
                        .build();

                log.info("[GrpcCallbackServer] Sending response back to sidecar...");
                responseObserver.onNext(response);
                responseObserver.onCompleted();

                log.info("[GrpcCallbackServer] ========== invokeHostFunction() COMPLETED SUCCESSFULLY ==========");

            } catch (Exception e) {
                log.error("[GrpcCallbackServer] ========== invokeHostFunction() FAILED ==========");
                log.error("[GrpcCallbackServer] Exception type: " + e.getClass().getName());
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
            String proxyType = request.getProxyType();

            log.info("[GrpcCallbackServer] ========== getContextProperty() RECEIVED ==========");
            log.info("[GrpcCallbackServer] PropertyPath: " + propertyPath);
            log.info("[GrpcCallbackServer] ProxyType: " + proxyType);
            log.info("[GrpcCallbackServer] Session: " + sessionId);
            log.info("[GrpcCallbackServer] Currently registered sessions: " + sessionHandlers.keySet());

            try {
                HostFunctionHandler handler = sessionHandlers.get(sessionId);
                if (handler == null) {
                    String errorMsg = "No handler registered for session: " + sessionId;
                    log.error("[GrpcCallbackServer] HANDLER NOT FOUND for getContextProperty!");
                    log.error("[GrpcCallbackServer] Requested session: " + sessionId);
                    log.error("[GrpcCallbackServer] Available sessions: " + sessionHandlers.keySet());
                    responseObserver.onNext(ContextPropertyResponse.newBuilder()
                            .setSuccess(false)
                            .setErrorMessage(errorMsg)
                            .build());
                    responseObserver.onCompleted();
                    return;
                }

                log.info("[GrpcCallbackServer] Handler found, getting property: " + propertyPath);

                Object value = handler.getContextProperty(propertyPath);
                log.info("[GrpcCallbackServer] Property value type: " +
                        (value != null ? value.getClass().getName() : "null"));

                ContextPropertyResponse.Builder responseBuilder = ContextPropertyResponse.newBuilder()
                        .setSuccess(true);

                if (value != null) {
                    boolean isProxy = isProxyType(value);
                    responseBuilder.setIsProxy(isProxy);
                    log.info("[GrpcCallbackServer] Value isProxy: " + isProxy);

                    if (isProxy) {
                        String valueProxyType = getProxyType(value);
                        responseBuilder.setProxyType(valueProxyType);
                        log.info("[GrpcCallbackServer] Setting proxyType: " + valueProxyType);

                        if (value instanceof org.graalvm.polyglot.proxy.ProxyObject) {
                            Object keys = ((org.graalvm.polyglot.proxy.ProxyObject) value).getMemberKeys();
                            if (keys instanceof String[]) {
                                String[] keyArray = (String[]) keys;
                                log.info("[GrpcCallbackServer] Adding " + keyArray.length + " member keys");
                                for (String key : keyArray) {
                                    responseBuilder.addMemberKeys(key);
                                }
                            }
                        }
                    } else {
                        log.info("[GrpcCallbackServer] Serializing non-proxy value to proto...");
                        responseBuilder.setValue(ProtobufSerializer.toProto(value));
                    }
                } else {
                    log.info("[GrpcCallbackServer] Property value is null");
                }

                responseObserver.onNext(responseBuilder.build());
                responseObserver.onCompleted();
                log.info("[GrpcCallbackServer] ========== getContextProperty() COMPLETED ==========");

            } catch (Exception e) {
                log.error("[GrpcCallbackServer] ========== getContextProperty() FAILED ==========");
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

            log.info("[GrpcCallbackServer] ========== setContextProperty() RECEIVED ==========");
            log.info("[GrpcCallbackServer] PropertyPath: " + propertyPath);
            log.info("[GrpcCallbackServer] Session: " + sessionId);
            log.info("[GrpcCallbackServer] Value valueCase: " + request.getValue().getValueCase());
            log.info("[GrpcCallbackServer] Currently registered sessions: " + sessionHandlers.keySet());

            try {
                HostFunctionHandler handler = sessionHandlers.get(sessionId);
                if (handler == null) {
                    String errorMsg = "No handler registered for session: " + sessionId;
                    log.error("[GrpcCallbackServer] HANDLER NOT FOUND for setContextProperty!");
                    log.error("[GrpcCallbackServer] Requested session: " + sessionId);
                    log.error("[GrpcCallbackServer] Available sessions: " + sessionHandlers.keySet());
                    responseObserver.onNext(ContextPropertySetResponse.newBuilder()
                            .setSuccess(false)
                            .setErrorMessage(errorMsg)
                            .build());
                    responseObserver.onCompleted();
                    return;
                }

                log.info("[GrpcCallbackServer] Handler found, deserializing value...");

                Object javaValue = ProtobufSerializer.fromProto(request.getValue());
                log.info("[GrpcCallbackServer] Deserialized value type: " +
                        (javaValue != null ? javaValue.getClass().getSimpleName() : "null"));

                log.info("[GrpcCallbackServer] Setting property: " + propertyPath);
                boolean success = handler.setContextProperty(propertyPath, javaValue);
                log.info("[GrpcCallbackServer] Set property result: " + success);

                ContextPropertySetResponse response = ContextPropertySetResponse.newBuilder()
                        .setSuccess(success)
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
                log.info("[GrpcCallbackServer] ========== setContextProperty() COMPLETED ==========");

            } catch (Exception e) {
                log.error("[GrpcCallbackServer] ========== setContextProperty() FAILED ==========");
                log.error("[GrpcCallbackServer] Error setting context property: " + propertyPath, e);
                responseObserver.onNext(ContextPropertySetResponse.newBuilder()
                        .setSuccess(false)
                        .setErrorMessage(e.getMessage())
                        .build());
                responseObserver.onCompleted();
            }
        }

        private boolean isProxyType(Object value) {
            if (value == null) {
                log.info("[GrpcCallbackServer] isProxyType: value is null, returning false");
                return false;
            }
            String className = value.getClass().getName();
            boolean result = className.contains("JsGraal") ||
                   className.contains("JsServlet") ||
                   className.contains("JsStep") ||
                   className.contains("JsAuthenticated") ||
                   className.contains("JsWritable") ||
                   value instanceof org.graalvm.polyglot.proxy.ProxyObject;
            log.info("[GrpcCallbackServer] isProxyType: className=" + className + ", result=" + result);
            return result;
        }

        private String getProxyType(Object value) {
            String className = value.getClass().getSimpleName();
            String result;
            if (className.startsWith("JsGraal")) {
                result = className.substring(7).toLowerCase();
            } else if (className.startsWith("Js")) {
                result = className.substring(2).toLowerCase();
            } else {
                result = className.toLowerCase();
            }
            log.info("[GrpcCallbackServer] getProxyType: className=" + className + ", result=" + result);
            return result;
        }
    }
}
*/
