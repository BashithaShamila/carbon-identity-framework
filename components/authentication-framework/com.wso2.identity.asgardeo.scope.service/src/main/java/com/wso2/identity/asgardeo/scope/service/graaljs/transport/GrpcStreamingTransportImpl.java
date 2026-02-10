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

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.CallbackServer;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.ProtobufSerializer;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteEngineTransport;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertyRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertyResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertySetRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertySetResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.EvaluateRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.EvaluateResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ExecuteCallbackRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ExecuteCallbackResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.HostFunctionRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.HostFunctionResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.SerializedValue;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.StreamMessage;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.grpc.JsEngineStreamingServiceGrpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Bidirectional streaming gRPC transport implementation.
 * Implements both RemoteEngineTransport (for sending requests) and CallbackServer
 * (for receiving callbacks) over a single bidirectional gRPC stream.
 * <p>
 * This eliminates the need for a separate callback server and dynamic port,
 * making the communication Istio/service-mesh friendly with mTLS support.
 * <p>
 * Thread model:
 * - IS HTTP thread calls sendEvaluate()/sendExecuteCallback() and blocks on CompletableFuture
 * - gRPC event thread receives StreamMessage via onNext()
 * - Callback executor handles host function/context property requests
 * - All sends to the stream are synchronized via streamLock
 */
public class GrpcStreamingTransportImpl implements RemoteEngineTransport, CallbackServer {

    private static final Log log = LogFactory.getLog(GrpcStreamingTransportImpl.class);

    private final String grpcTarget;
    private final int requestTimeout;
    private final GrpcConnectionManager connectionManager;
    private final String correlationId;
    private final Map<String, HostFunctionHandler> sessionHandlers = new ConcurrentHashMap<>();
    private final ExecutorService callbackExecutor = Executors.newCachedThreadPool();

    private JsEngineStreamingServiceGrpc.JsEngineStreamingServiceStub asyncStub;

    public GrpcStreamingTransportImpl(String grpcTarget) {
        this(grpcTarget, 30);
    }

    public GrpcStreamingTransportImpl(String grpcTarget, int requestTimeout) {
        this.grpcTarget = grpcTarget;
        this.requestTimeout = requestTimeout;
        this.connectionManager = GrpcConnectionManager.getInstance();
        this.correlationId = UUID.randomUUID().toString();
        log.info("[GrpcStreaming] Created streaming transport for target: " + grpcTarget +
                ", timeout: " + requestTimeout + "s, correlationId: " + correlationId);
    }

    // ============ RemoteEngineTransport Methods ============

    @Override
    public EvaluateResponse sendEvaluate(EvaluateRequest request) throws IOException {
        String sessionId = request.getSessionId();
        log.info("[GrpcStreaming] sendEvaluate() - session: " + sessionId +
                ", script length: " + request.getScript().length());

        ensureStub();

        CompletableFuture<EvaluateResponse> evalFuture = new CompletableFuture<>();
        final Object lock = new Object();

        StreamObserver<StreamMessage> outboundStream = asyncStub.executeScript(
                createResponseObserver(sessionId, evalFuture, null, lock));

        // Send the evaluate request
        StreamMessage streamMsg = StreamMessage.newBuilder()
                .setSessionId(sessionId)
                .setEvaluateRequest(request)
                .build();

        synchronized (lock) {
            outboundStream.onNext(streamMsg);
        }
        log.info("[GrpcStreaming] Sent EvaluateRequest on stream, session: " + sessionId);

        // Store outbound stream for callback responses
        streamRegistry.put(sessionId, new StreamContext(outboundStream, lock));

        try {
            EvaluateResponse response = evalFuture.get(requestTimeout, TimeUnit.SECONDS);
            log.info("[GrpcStreaming] Received EvaluateResponse, session: " + sessionId +
                    ", success: " + response.getSuccess());
            return response;
        } catch (TimeoutException e) {
            throw new IOException("Evaluate request timed out after " + requestTimeout + "s", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Evaluate request interrupted", e);
        } catch (ExecutionException e) {
            throw new IOException("Evaluate request failed: " + e.getCause().getMessage(), e.getCause());
        } finally {
            streamRegistry.remove(sessionId);
            synchronized (lock) {
                try {
                    outboundStream.onCompleted();
                } catch (Exception e) {
                    log.debug("[GrpcStreaming] Error completing stream: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public ExecuteCallbackResponse sendExecuteCallback(ExecuteCallbackRequest request) throws IOException {
        String sessionId = request.getSessionId();
        log.info("[GrpcStreaming] sendExecuteCallback() - session: " + sessionId +
                ", function length: " + request.getFunctionSource().length());

        ensureStub();

        CompletableFuture<ExecuteCallbackResponse> callbackFuture = new CompletableFuture<>();
        final Object lock = new Object();

        StreamObserver<StreamMessage> outboundStream = asyncStub.executeScript(
                createResponseObserver(sessionId, null, callbackFuture, lock));

        // Send the execute callback request
        StreamMessage streamMsg = StreamMessage.newBuilder()
                .setSessionId(sessionId)
                .setExecuteCallbackRequest(request)
                .build();

        synchronized (lock) {
            outboundStream.onNext(streamMsg);
        }
        log.info("[GrpcStreaming] Sent ExecuteCallbackRequest on stream, session: " + sessionId);

        // Store outbound stream for callback responses
        streamRegistry.put(sessionId, new StreamContext(outboundStream, lock));

        try {
            ExecuteCallbackResponse response = callbackFuture.get(requestTimeout, TimeUnit.SECONDS);
            log.info("[GrpcStreaming] Received ExecuteCallbackResponse, session: " + sessionId +
                    ", success: " + response.getSuccess());
            return response;
        } catch (TimeoutException e) {
            throw new IOException("ExecuteCallback request timed out after " + requestTimeout + "s", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("ExecuteCallback request interrupted", e);
        } catch (ExecutionException e) {
            throw new IOException("ExecuteCallback failed: " + e.getCause().getMessage(), e.getCause());
        } finally {
            streamRegistry.remove(sessionId);
            synchronized (lock) {
                try {
                    outboundStream.onCompleted();
                } catch (Exception e) {
                    log.debug("[GrpcStreaming] Error completing stream: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void connect() throws IOException {
        log.info("[GrpcStreaming] connect() to " + grpcTarget);
        ensureStub();
        log.info("[GrpcStreaming] Connected successfully to: " + grpcTarget);
    }

    @Override
    public boolean isConnected() {
        boolean channelOk = connectionManager.isClientChannelConnected();
        boolean stubOk = asyncStub != null;
        return channelOk && stubOk;
    }

    @Override
    public void close() throws IOException {
        log.info("[GrpcStreaming] close() - clearing stub, correlationId: " + correlationId);
        asyncStub = null;
    }

    // ============ CallbackServer Methods ============

    @Override
    public void registerHandler(String sessionId, HostFunctionHandler handler) {
        log.info("[GrpcStreaming] registerHandler for session: " + sessionId);
        sessionHandlers.put(sessionId, handler);
    }

    @Override
    public void unregisterHandler(String sessionId) {
        log.info("[GrpcStreaming] unregisterHandler for session: " + sessionId);
        sessionHandlers.remove(sessionId);
    }

    @Override
    public String getCallbackAddress() {
        // In streaming mode, there's no separate callback address - it's the same stream
        return "streaming://" + grpcTarget;
    }

    @Override
    public void start() throws IOException {
        // No-op: streaming doesn't need a separate callback server
        log.info("[GrpcStreaming] start() - no separate callback server needed in streaming mode");
    }

    // ============ Private Helpers ============

    /** Registry of active streams keyed by session ID, for sending callback responses. */
    private final Map<String, StreamContext> streamRegistry = new ConcurrentHashMap<>();

    /** Holds stream observer and its synchronization lock. */
    private static class StreamContext {
        final StreamObserver<StreamMessage> outbound;
        final Object lock;

        StreamContext(StreamObserver<StreamMessage> outbound, Object lock) {
            this.outbound = outbound;
            this.lock = lock;
        }
    }

    /**
     * Creates a StreamObserver that handles incoming messages from the sidecar.
     * Routes to the appropriate handler based on message type.
     */
    private StreamObserver<StreamMessage> createResponseObserver(
            String sessionId,
            CompletableFuture<EvaluateResponse> evalFuture,
            CompletableFuture<ExecuteCallbackResponse> callbackFuture,
            Object streamLock) {

        return new StreamObserver<StreamMessage>() {
            @Override
            public void onNext(StreamMessage message) {
                log.info("[GrpcStreaming] Received message type: " + message.getPayloadCase() +
                        ", session: " + message.getSessionId());

                switch (message.getPayloadCase()) {
                    case EVALUATE_RESPONSE:
                        if (evalFuture != null) {
                            evalFuture.complete(message.getEvaluateResponse());
                        }
                        break;

                    case EXECUTE_CALLBACK_RESPONSE:
                        if (callbackFuture != null) {
                            callbackFuture.complete(message.getExecuteCallbackResponse());
                        }
                        break;

                    case HOST_FUNCTION_REQUEST:
                        callbackExecutor.submit(() ->
                                handleHostFunctionRequest(message.getSessionId(),
                                        message.getHostFunctionRequest(), streamLock));
                        break;

                    case CONTEXT_PROPERTY_REQUEST:
                        callbackExecutor.submit(() ->
                                handleContextPropertyRequest(message.getSessionId(),
                                        message.getContextPropertyRequest(), streamLock));
                        break;

                    case CONTEXT_PROPERTY_SET_REQUEST:
                        callbackExecutor.submit(() ->
                                handleContextPropertySetRequest(message.getSessionId(),
                                        message.getContextPropertySetRequest(), streamLock));
                        break;

                    default:
                        log.warn("[GrpcStreaming] Unexpected message type: " + message.getPayloadCase());
                }
            }

            @Override
            public void onError(Throwable t) {
                log.error("[GrpcStreaming] Stream error, session: " + sessionId, t);
                IOException ex = new IOException("Stream error: " + t.getMessage(), t);
                if (evalFuture != null && !evalFuture.isDone()) {
                    evalFuture.completeExceptionally(ex);
                }
                if (callbackFuture != null && !callbackFuture.isDone()) {
                    callbackFuture.completeExceptionally(ex);
                }
            }

            @Override
            public void onCompleted() {
                log.info("[GrpcStreaming] Stream completed, session: " + sessionId);
                if (evalFuture != null && !evalFuture.isDone()) {
                    evalFuture.completeExceptionally(
                            new IOException("Stream completed without response"));
                }
                if (callbackFuture != null && !callbackFuture.isDone()) {
                    callbackFuture.completeExceptionally(
                            new IOException("Stream completed without response"));
                }
            }
        };
    }

    /**
     * Handle a host function request from the sidecar.
     * Invokes the registered handler and sends the response back on the stream.
     */
    private void handleHostFunctionRequest(String sessionId, HostFunctionRequest request,
                                            Object streamLock) {
        String functionName = request.getFunctionName();
        log.info("[GrpcStreaming] handleHostFunction: " + functionName + ", session: " + sessionId);

        StreamContext ctx = streamRegistry.get(sessionId);
        if (ctx == null) {
            log.error("[GrpcStreaming] No stream context for session: " + sessionId);
            return;
        }

        try {
            HostFunctionHandler handler = sessionHandlers.get(sessionId);
            if (handler == null) {
                log.error("[GrpcStreaming] No handler for session: " + sessionId);
                sendOnStream(ctx, StreamMessage.newBuilder()
                        .setSessionId(sessionId)
                        .setHostFunctionResponse(HostFunctionResponse.newBuilder()
                                .setSuccess(false)
                                .setErrorMessage("No handler for session: " + sessionId)
                                .build())
                        .build());
                return;
            }

            // Deserialize arguments
            List<Object> args = new ArrayList<>();
            for (SerializedValue sv : request.getArgumentsList()) {
                args.add(ProtobufSerializer.fromProto(sv));
            }

            // Invoke host function
            Object result = handler.invokeHostFunction(functionName, args.toArray());
            log.info("[GrpcStreaming] Host function " + functionName + " returned: " +
                    (result != null ? result.getClass().getSimpleName() : "null"));

            // Send response back on stream
            sendOnStream(ctx, StreamMessage.newBuilder()
                    .setSessionId(sessionId)
                    .setHostFunctionResponse(HostFunctionResponse.newBuilder()
                            .setSuccess(true)
                            .setResult(ProtobufSerializer.toProto(result))
                            .build())
                    .build());

        } catch (Exception e) {
            log.error("[GrpcStreaming] Error in host function " + functionName, e);
            sendOnStream(ctx, StreamMessage.newBuilder()
                    .setSessionId(sessionId)
                    .setHostFunctionResponse(HostFunctionResponse.newBuilder()
                            .setSuccess(false)
                            .setErrorMessage(e.getMessage() != null ? e.getMessage() : e.getClass().getName())
                            .build())
                    .build());
        }
    }

    /**
     * Handle a context property request from the sidecar.
     */
    private void handleContextPropertyRequest(String sessionId, ContextPropertyRequest request,
                                               Object streamLock) {
        String propertyPath = request.getPropertyPath();
        log.info("[GrpcStreaming] handleContextProperty: " + propertyPath + ", session: " + sessionId);

        StreamContext ctx = streamRegistry.get(sessionId);
        if (ctx == null) {
            log.error("[GrpcStreaming] No stream context for session: " + sessionId);
            return;
        }

        try {
            HostFunctionHandler handler = sessionHandlers.get(sessionId);
            if (handler == null) {
                sendOnStream(ctx, StreamMessage.newBuilder()
                        .setSessionId(sessionId)
                        .setContextPropertyResponse(ContextPropertyResponse.newBuilder()
                                .setSuccess(false)
                                .setErrorMessage("No handler for session: " + sessionId)
                                .build())
                        .build());
                return;
            }

            Object value = handler.getContextProperty(propertyPath);

            ContextPropertyResponse.Builder responseBuilder = ContextPropertyResponse.newBuilder()
                    .setSuccess(true);

            // Handle __keys__ special path - value is the member keys array
            if (propertyPath.endsWith("::__keys__") || "__keys__".equals(propertyPath)) {
                if (value != null) {
                    extractMemberKeys(value, responseBuilder);
                }
                sendOnStream(ctx, StreamMessage.newBuilder()
                        .setSessionId(sessionId)
                        .setContextPropertyResponse(responseBuilder.build())
                        .build());
                return;
            }

            if (value != null) {
                boolean isProxy = isProxyType(value);
                responseBuilder.setIsProxy(isProxy);

                if (isProxy) {
                    responseBuilder.setProxyType(getProxyType(value));
                    if (value instanceof org.graalvm.polyglot.proxy.ProxyObject) {
                        Object keys = ((org.graalvm.polyglot.proxy.ProxyObject) value).getMemberKeys();
                        extractMemberKeys(keys, responseBuilder);
                    }
                } else {
                    responseBuilder.setValue(ProtobufSerializer.toProto(value));
                }
            }

            sendOnStream(ctx, StreamMessage.newBuilder()
                    .setSessionId(sessionId)
                    .setContextPropertyResponse(responseBuilder.build())
                    .build());

        } catch (Exception e) {
            log.error("[GrpcStreaming] Error getting context property: " + propertyPath, e);
            sendOnStream(ctx, StreamMessage.newBuilder()
                    .setSessionId(sessionId)
                    .setContextPropertyResponse(ContextPropertyResponse.newBuilder()
                            .setSuccess(false)
                            .setErrorMessage(e.getMessage())
                            .build())
                    .build());
        }
    }

    /**
     * Handle a context property set request from the sidecar.
     */
    private void handleContextPropertySetRequest(String sessionId, ContextPropertySetRequest request,
                                                  Object streamLock) {
        String propertyPath = request.getPropertyPath();
        log.info("[GrpcStreaming] handleContextPropertySet: " + propertyPath + ", session: " + sessionId);

        StreamContext ctx = streamRegistry.get(sessionId);
        if (ctx == null) {
            log.error("[GrpcStreaming] No stream context for session: " + sessionId);
            return;
        }

        try {
            HostFunctionHandler handler = sessionHandlers.get(sessionId);
            if (handler == null) {
                sendOnStream(ctx, StreamMessage.newBuilder()
                        .setSessionId(sessionId)
                        .setContextPropertySetResponse(ContextPropertySetResponse.newBuilder()
                                .setSuccess(false)
                                .setErrorMessage("No handler for session: " + sessionId)
                                .build())
                        .build());
                return;
            }

            Object javaValue = ProtobufSerializer.fromProto(request.getValue());
            boolean success = handler.setContextProperty(propertyPath, javaValue);

            sendOnStream(ctx, StreamMessage.newBuilder()
                    .setSessionId(sessionId)
                    .setContextPropertySetResponse(ContextPropertySetResponse.newBuilder()
                            .setSuccess(success)
                            .build())
                    .build());

        } catch (Exception e) {
            log.error("[GrpcStreaming] Error setting context property: " + propertyPath, e);
            sendOnStream(ctx, StreamMessage.newBuilder()
                    .setSessionId(sessionId)
                    .setContextPropertySetResponse(ContextPropertySetResponse.newBuilder()
                            .setSuccess(false)
                            .setErrorMessage(e.getMessage())
                            .build())
                    .build());
        }
    }

    /** Thread-safe send on stream. */
    private void sendOnStream(StreamContext ctx, StreamMessage message) {
        synchronized (ctx.lock) {
            try {
                ctx.outbound.onNext(message);
            } catch (Exception e) {
                log.error("[GrpcStreaming] Error sending on stream: " + e.getMessage());
            }
        }
    }

    private void ensureStub() {
        if (asyncStub == null) {
            ManagedChannel channel = connectionManager.getClientChannel(grpcTarget);
            asyncStub = JsEngineStreamingServiceGrpc.newStub(channel);
            log.info("[GrpcStreaming] Created async stub for target: " + grpcTarget);
        }
    }

    private boolean isProxyType(Object value) {
        if (value == null) return false;
        String className = value.getClass().getName();
        return className.contains("JsGraal") ||
               className.contains("JsServlet") ||
               className.contains("JsStep") ||
               className.contains("JsAuthenticated") ||
               className.contains("JsWritable") ||
               value instanceof org.graalvm.polyglot.proxy.ProxyObject;
    }

    private String getProxyType(Object value) {
        String className = value.getClass().getSimpleName();
        if (className.startsWith("JsGraal")) {
            return className.substring(7).toLowerCase();
        } else if (className.startsWith("Js")) {
            return className.substring(2).toLowerCase();
        }
        return className.toLowerCase();
    }

    private void extractMemberKeys(Object keys, ContextPropertyResponse.Builder responseBuilder) {
        if (keys instanceof String[]) {
            for (String key : (String[]) keys) {
                responseBuilder.addMemberKeys(key);
            }
        } else if (keys instanceof Object[]) {
            for (Object key : (Object[]) keys) {
                if (key instanceof String[]) {
                    for (String s : (String[]) key) {
                        responseBuilder.addMemberKeys(s);
                    }
                } else {
                    responseBuilder.addMemberKeys(String.valueOf(key));
                }
            }
        } else if (keys instanceof org.graalvm.polyglot.proxy.ProxyArray) {
            org.graalvm.polyglot.proxy.ProxyArray proxyArray =
                    (org.graalvm.polyglot.proxy.ProxyArray) keys;
            long size = proxyArray.getSize();
            for (long i = 0; i < size; i++) {
                Object element = proxyArray.get(i);
                if (element instanceof String[]) {
                    for (String s : (String[]) element) {
                        responseBuilder.addMemberKeys(s);
                    }
                } else if (element instanceof Object[]) {
                    for (Object o : (Object[]) element) {
                        responseBuilder.addMemberKeys(String.valueOf(o));
                    }
                } else {
                    responseBuilder.addMemberKeys(String.valueOf(element));
                }
            }
        }
    }
}
