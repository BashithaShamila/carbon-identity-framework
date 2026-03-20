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
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.SerializedProxyObject;
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
 * (for receiving callbacks) over a per-session bidirectional gRPC stream.
 * <p>
 * Each sendEvaluate()/sendExecuteCallback() call opens its own HTTP/2 stream.
 * This gives each session its own lock, its own stream lifecycle, and avoids
 * contention between concurrent sessions. The stream closes after the response
 * is received (between async authentication steps).
 * <p>
 * Thread model:
 * - IS HTTP thread calls sendEvaluate()/sendExecuteCallback() and blocks on CompletableFuture
 * - gRPC event thread receives StreamMessage via onNext()
 * - Callback executor handles host function/context property requests
 * - All sends to a session's stream are synchronized via that session's lock
 */
public class GrpcStreamingTransportImpl implements RemoteEngineTransport, CallbackServer {

    private static final Log log = LogFactory.getLog(GrpcStreamingTransportImpl.class);

    private final String grpcTarget;
    private final int requestTimeout;
    private final GrpcConnectionManager connectionManager;
    private final String correlationId;
    private final Map<String, HostFunctionHandler> sessionHandlers = new ConcurrentHashMap<>();
    private final ExecutorService callbackExecutor = Executors.newCachedThreadPool();

    public GrpcStreamingTransportImpl(String grpcTarget) {
        this(grpcTarget, 5);
    }

    public GrpcStreamingTransportImpl(String grpcTarget, int requestTimeout) {
        this.grpcTarget = grpcTarget;
        this.requestTimeout = requestTimeout;
        this.connectionManager = GrpcConnectionManager.getInstance();
        this.correlationId = UUID.randomUUID().toString();
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] Created streaming transport for target: " + grpcTarget +
                    ", timeout: " + requestTimeout + "s, correlationId: " + correlationId);
        }
    }

    // ============ RemoteEngineTransport Methods ============

    @Override
    public EvaluateResponse sendEvaluate(EvaluateRequest request) throws IOException {
        String sessionId = request.getSessionId();
        long t0 = System.currentTimeMillis();
        System.out.println("[PERF] [" + t0 + "] IS EVALUATE_START session=" + sessionId +
                " startTs=" + t0 + " scriptLen=" + request.getScript().length());
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] sendEvaluate() - session: " + sessionId +
                    ", script length: " + request.getScript().length());
        }

        JsEngineStreamingServiceGrpc.JsEngineStreamingServiceStub stub = getStub();

        CompletableFuture<EvaluateResponse> evalFuture = new CompletableFuture<>();
        final Object lock = new Object();

        long t1 = System.currentTimeMillis();
        StreamObserver<StreamMessage> outboundStream = stub.executeScript(
                createResponseObserver(sessionId, evalFuture, null, lock, t0));
        long t2 = System.currentTimeMillis();
        System.out.println("[PERF] [" + t2 + "] IS STREAM_OPENED session=" + sessionId +
                " startTs=" + t0 + " stubReadyTs=" + t1 + " streamOpenedTs=" + t2 +
                " openMs=" + (t2 - t1) + " sinceStartMs=" + (t2 - t0));

        // Send the evaluate request
        StreamMessage streamMsg = StreamMessage.newBuilder()
                .setSessionId(sessionId)
                .setEvaluateRequest(request)
                .build();

        // CRITICAL FIX: Register stream context BEFORE sending the request.
        streamRegistry.put(sessionId, new StreamContext(outboundStream, lock));

        synchronized (lock) {
            outboundStream.onNext(streamMsg);
        }
        long t3 = System.currentTimeMillis();
        System.out.println("[PERF] [" + t3 + "] IS EVALUATE_SENT session=" + sessionId +
                " startTs=" + t0 + " streamOpenedTs=" + t2 + " sentTs=" + t3 +
                " sendMs=" + (t3 - t2) + " sinceStartMs=" + (t3 - t0));
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] Sent EvaluateRequest on stream, session: " + sessionId);
        }

        try {
            EvaluateResponse response = evalFuture.get(requestTimeout, TimeUnit.SECONDS);
            long t4 = System.currentTimeMillis();
            System.out.println("[PERF] [" + t4 + "] IS EVALUATE_RESPONSE session=" + sessionId +
                    " success=" + response.getSuccess() +
                    " startTs=" + t0 + " sentTs=" + t3 + " responseTs=" + t4 +
                    " waitMs=" + (t4 - t3) + " totalMs=" + (t4 - t0));
            if (log.isDebugEnabled()) {
                log.debug("[GrpcStreaming] Received EvaluateResponse, session: " + sessionId +
                        ", success: " + response.getSuccess());
            }
            return response;
        } catch (TimeoutException e) {
            long tErr = System.currentTimeMillis();
            System.out.println("[PERF] [" + tErr + "] IS EVALUATE_TIMEOUT session=" +
                    sessionId + " startTs=" + t0 + " sentTs=" + t3 +
                    " timeoutTs=" + tErr + " timeoutMs=" + (tErr - t0));
            throw new IOException("Evaluate request timed out after " + requestTimeout + "s", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Evaluate request interrupted", e);
        } catch (ExecutionException e) {
            long tErr = System.currentTimeMillis();
            System.out.println("[PERF] [" + tErr + "] IS EVALUATE_ERROR session=" +
                    sessionId + " error=" + e.getCause().getMessage() +
                    " startTs=" + t0 + " errorTs=" + tErr + " totalMs=" + (tErr - t0));
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
        long t0 = System.currentTimeMillis();
        System.out.println("[PERF] [" + t0 + "] IS EXEC_CALLBACK_START session=" + sessionId +
                " startTs=" + t0 + " fnLen=" + request.getFunctionSource().length());
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] sendExecuteCallback() - session: " + sessionId +
                    ", function length: " + request.getFunctionSource().length());
        }

        JsEngineStreamingServiceGrpc.JsEngineStreamingServiceStub stub = getStub();

        CompletableFuture<ExecuteCallbackResponse> callbackFuture = new CompletableFuture<>();
        final Object lock = new Object();

        long t1 = System.currentTimeMillis();
        StreamObserver<StreamMessage> outboundStream = stub.executeScript(
                createResponseObserver(sessionId, null, callbackFuture, lock, t0));
        long t2 = System.currentTimeMillis();
        System.out.println("[PERF] [" + t2 + "] IS EXEC_CALLBACK_STREAM_OPENED session=" + sessionId +
                " startTs=" + t0 + " stubReadyTs=" + t1 + " streamOpenedTs=" + t2 +
                " openMs=" + (t2 - t1) + " sinceStartMs=" + (t2 - t0));

        // Send the execute callback request
        StreamMessage streamMsg = StreamMessage.newBuilder()
                .setSessionId(sessionId)
                .setExecuteCallbackRequest(request)
                .build();

        // CRITICAL FIX: Register stream context BEFORE sending the request.
        streamRegistry.put(sessionId, new StreamContext(outboundStream, lock));

        synchronized (lock) {
            outboundStream.onNext(streamMsg);
        }
        long t3 = System.currentTimeMillis();
        System.out.println("[PERF] [" + t3 + "] IS EXEC_CALLBACK_SENT session=" + sessionId +
                " startTs=" + t0 + " streamOpenedTs=" + t2 + " sentTs=" + t3 +
                " sendMs=" + (t3 - t2) + " sinceStartMs=" + (t3 - t0));
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] Sent ExecuteCallbackRequest on stream, session: " + sessionId);
        }

        try {
            ExecuteCallbackResponse response = callbackFuture.get(requestTimeout, TimeUnit.SECONDS);
            long t4 = System.currentTimeMillis();
            System.out.println("[PERF] [" + t4 + "] IS EXEC_CALLBACK_RESPONSE session=" + sessionId +
                    " success=" + response.getSuccess() +
                    " startTs=" + t0 + " sentTs=" + t3 + " responseTs=" + t4 +
                    " waitMs=" + (t4 - t3) + " totalMs=" + (t4 - t0));
            if (log.isDebugEnabled()) {
                log.debug("[GrpcStreaming] Received ExecuteCallbackResponse, session: " + sessionId +
                        ", success: " + response.getSuccess());
            }
            return response;
        } catch (TimeoutException e) {
            long tErr = System.currentTimeMillis();
            System.out.println("[PERF] [" + tErr + "] IS EXEC_CALLBACK_TIMEOUT session=" +
                    sessionId + " startTs=" + t0 + " sentTs=" + t3 +
                    " timeoutTs=" + tErr + " timeoutMs=" + (tErr - t0));
            throw new IOException("ExecuteCallback request timed out after " + requestTimeout + "s", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("ExecuteCallback request interrupted", e);
        } catch (ExecutionException e) {
            long tErr = System.currentTimeMillis();
            System.out.println("[PERF] [" + tErr + "] IS EXEC_CALLBACK_ERROR session=" +
                    sessionId + " error=" + e.getCause().getMessage() +
                    " startTs=" + t0 + " errorTs=" + tErr + " totalMs=" + (tErr - t0));
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
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] connect() to " + grpcTarget);
        }
        // Verify channel pool is initialized by requesting a channel
        connectionManager.getClientChannel(grpcTarget);
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] Connected successfully to: " + grpcTarget);
        }
    }

    @Override
    public boolean isConnected() {
        return connectionManager.isClientChannelConnected();
    }

    @Override
    public void close() throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] close() called - singleton transport, channel pool remains active, " +
                    "correlationId: " + correlationId);
        }
        // Don't shutdown channel pool - this is a singleton transport shared across all sessions.
        // Per-session cleanup is handled by unregisterHandler() and stream onCompleted() in finally.
    }

    // ============ CallbackServer Methods ============

    @Override
    public void registerHandler(String sessionId, HostFunctionHandler handler) {
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] registerHandler for session: " + sessionId);
        }
        sessionHandlers.put(sessionId, handler);
    }

    @Override
    public void unregisterHandler(String sessionId) {
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] unregisterHandler for session: " + sessionId);
        }
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
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] start() - no separate callback server needed in streaming mode");
        }
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
            Object streamLock,
            long streamStartTime) {

        return new StreamObserver<StreamMessage>() {
            @Override
            public void onNext(StreamMessage message) {
                long now = System.currentTimeMillis();
                if (log.isDebugEnabled()) {
                    log.debug("[GrpcStreaming] Received message type: " + message.getPayloadCase() +
                            ", session: " + message.getSessionId());
                }

                switch (message.getPayloadCase()) {
                    case EVALUATE_RESPONSE:
                        System.out.println("[PERF] [" + now + "] IS EVALUATE_RESPONSE_ARRIVED session=" +
                                sessionId + " streamStartTs=" + streamStartTime +
                                " arrivedTs=" + now +
                                " sinceStreamStartMs=" + (now - streamStartTime));
                        if (evalFuture != null) {
                            evalFuture.complete(message.getEvaluateResponse());
                        }
                        break;

                    case EXECUTE_CALLBACK_RESPONSE:
                        System.out.println("[PERF] [" + now + "] IS EXEC_CALLBACK_RESPONSE_ARRIVED session=" +
                                sessionId + " streamStartTs=" + streamStartTime +
                                " arrivedTs=" + now +
                                " sinceStreamStartMs=" + (now - streamStartTime));
                        if (callbackFuture != null) {
                            callbackFuture.complete(message.getExecuteCallbackResponse());
                        }
                        break;

                    case HOST_FUNCTION_REQUEST:
                        System.out.println("[PERF] [" + now + "] IS HOST_FN_REQUEST_RECEIVED session=" +
                                sessionId + " fn=" + message.getHostFunctionRequest().getFunctionName() +
                                " streamStartTs=" + streamStartTime +
                                " receivedTs=" + now +
                                " sinceStreamStartMs=" + (now - streamStartTime));
                        callbackExecutor.submit(() ->
                                handleHostFunctionRequest(message.getSessionId(),
                                        message.getHostFunctionRequest(), streamLock));
                        break;

                    case CONTEXT_PROPERTY_REQUEST:
                        System.out.println("[PERF] [" + now + "] IS CTX_PROP_REQUEST_RECEIVED session=" +
                                sessionId + " path=" + message.getContextPropertyRequest().getPropertyPath() +
                                " streamStartTs=" + streamStartTime +
                                " receivedTs=" + now +
                                " sinceStreamStartMs=" + (now - streamStartTime));
                        callbackExecutor.submit(() ->
                                handleContextPropertyRequest(message.getSessionId(),
                                        message.getContextPropertyRequest(), streamLock));
                        break;

                    case CONTEXT_PROPERTY_SET_REQUEST:
                        System.out.println("[PERF] [" + now + "] IS CTX_PROP_SET_REQUEST_RECEIVED session=" +
                                sessionId + " path=" + message.getContextPropertySetRequest().getPropertyPath() +
                                " streamStartTs=" + streamStartTime +
                                " receivedTs=" + now +
                                " sinceStreamStartMs=" + (now - streamStartTime));
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
                long errTs = System.currentTimeMillis();
                System.out.println("[PERF] [" + errTs + "] IS STREAM_ERROR session=" +
                        sessionId + " streamStartTs=" + streamStartTime +
                        " errorTs=" + errTs + " error=" + t.getMessage() +
                        " sinceStreamStartMs=" + (errTs - streamStartTime));
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
                long completedTs = System.currentTimeMillis();
                System.out.println("[PERF] [" + completedTs + "] IS STREAM_COMPLETED session=" +
                        sessionId + " streamStartTs=" + streamStartTime +
                        " completedTs=" + completedTs +
                        " sinceStreamStartMs=" + (completedTs - streamStartTime));
                if (log.isDebugEnabled()) {
                    log.debug("[GrpcStreaming] Stream completed, session: " + sessionId);
                }
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
        long hfStart = System.currentTimeMillis();
        System.out.println("[PERF] [" + hfStart + "] IS HOST_FN_HANDLE_START session=" + sessionId +
                " fn=" + functionName + " handleStartTs=" + hfStart);
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] handleHostFunction: " + functionName + ", session: " + sessionId);
        }

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
            long hfExecStart = System.currentTimeMillis();
            Object result = handler.invokeHostFunction(functionName, args.toArray());
            long hfExecEnd = System.currentTimeMillis();
            System.out.println("[PERF] [" + hfExecEnd + "] IS HOST_FN_EXECUTED session=" + sessionId +
                    " fn=" + functionName +
                    " handleStartTs=" + hfStart + " deserEndTs=" + hfExecStart +
                    " execStartTs=" + hfExecStart + " execEndTs=" + hfExecEnd +
                    " deserMs=" + (hfExecStart - hfStart) +
                    " execMs=" + (hfExecEnd - hfExecStart) +
                    " totalMs=" + (hfExecEnd - hfStart));
            if (log.isDebugEnabled()) {
                log.debug("[GrpcStreaming] Host function " + functionName + " returned: " +
                        (result != null ? result.getClass().getSimpleName() : "null"));
            }

            // Serialize result: use proxy object for complex types, primitive serialization otherwise
            SerializedValue serializedResult;
            if (result != null && isProxyType(result)) {
                String refId = handler.storeObjectReference(result);
                String proxyType = getProxyType(result);
                if (log.isDebugEnabled()) {
                    log.debug("[GrpcStreaming] Serializing complex result as proxy: type=" + proxyType +
                            ", refId=" + refId);
                }
                serializedResult = SerializedValue.newBuilder()
                        .setProxyObject(SerializedProxyObject.newBuilder()
                                .setType(proxyType)
                                .setReferenceId(refId != null ? refId : "")
                                .build())
                        .build();
            } else {
                // CRITICAL FIX: Set proxy cache ThreadLocal before serialization
                // This enables lazy-loading proxy pattern for complex objects (e.g., User arrays)
                if (handler instanceof org.wso2.carbon.identity.application.authentication.framework
                        .config.model.graph.graaljs.engine.RemoteJsEngine) {
                    org.wso2.carbon.identity.application.authentication.framework
                            .config.model.graph.graaljs.engine.RemoteJsEngine remoteEngine =
                            (org.wso2.carbon.identity.application.authentication.framework
                                    .config.model.graph.graaljs.engine.RemoteJsEngine) handler;
                    java.util.Map<String, Object> proxyCache = remoteEngine.getProxyObjectCache();
                    System.out.println("[GrpcStreaming] Setting proxy cache ThreadLocal - cache size: " +
                            (proxyCache != null ? proxyCache.size() : "NULL"));
                    ProtobufSerializer.setSessionProxyCache(proxyCache);
                }
                try {
                    serializedResult = ProtobufSerializer.toProto(result);
                } finally {
                    ProtobufSerializer.clearSessionProxyCache();
                }
            }

            // Send response back on stream
            long hfSerEnd = System.currentTimeMillis();
            sendOnStream(ctx, StreamMessage.newBuilder()
                    .setSessionId(sessionId)
                    .setHostFunctionResponse(HostFunctionResponse.newBuilder()
                            .setSuccess(true)
                            .setResult(serializedResult)
                            .build())
                    .build());
            long hfSentTs = System.currentTimeMillis();
            System.out.println("[PERF] [" + hfSentTs + "] IS HOST_FN_RESPONSE_SENT session=" +
                    sessionId + " fn=" + functionName +
                    " handleStartTs=" + hfStart + " execEndTs=" + hfExecEnd +
                    " serEndTs=" + hfSerEnd + " sentTs=" + hfSentTs +
                    " serMs=" + (hfSerEnd - hfExecEnd) + " sendMs=" + (hfSentTs - hfSerEnd) +
                    " totalHandleMs=" + (hfSentTs - hfStart));

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
        long cpStart = System.currentTimeMillis();
        System.out.println("[PERF] [" + cpStart + "] IS CTX_PROP_HANDLE_START session=" + sessionId +
                " path=" + propertyPath + " handleStartTs=" + cpStart);
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] handleContextProperty: " + propertyPath + ", session: " + sessionId);
        }

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

            long cpExecStart = System.currentTimeMillis();
            Object value = handler.getContextProperty(propertyPath);
            long cpExecEnd = System.currentTimeMillis();

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
            long cpSentTs = System.currentTimeMillis();
            System.out.println("[PERF] [" + cpSentTs + "] IS CTX_PROP_RESPONSE_SENT session=" +
                    sessionId + " path=" + propertyPath +
                    " handleStartTs=" + cpStart + " execStartTs=" + cpExecStart +
                    " execEndTs=" + cpExecEnd + " sentTs=" + cpSentTs +
                    " execMs=" + (cpExecEnd - cpExecStart) +
                    " serMs=" + (cpSentTs - cpExecEnd) +
                    " totalMs=" + (cpSentTs - cpStart));

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
        long cpsStart = System.currentTimeMillis();
        System.out.println("[PERF] [" + cpsStart + "] IS CTX_PROP_SET_HANDLE_START session=" + sessionId +
                " path=" + propertyPath + " handleStartTs=" + cpsStart);
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] handleContextPropertySet: " + propertyPath + ", session: " + sessionId);
        }

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

            long cpsDeserStart = System.currentTimeMillis();
            Object javaValue = ProtobufSerializer.fromProto(request.getValue());
            long cpsExecStart = System.currentTimeMillis();
            boolean success = handler.setContextProperty(propertyPath, javaValue);
            long cpsExecEnd = System.currentTimeMillis();

            sendOnStream(ctx, StreamMessage.newBuilder()
                    .setSessionId(sessionId)
                    .setContextPropertySetResponse(ContextPropertySetResponse.newBuilder()
                            .setSuccess(success)
                            .build())
                    .build());
            long cpsSentTs = System.currentTimeMillis();
            System.out.println("[PERF] [" + cpsSentTs + "] IS CTX_PROP_SET_RESPONSE_SENT session=" +
                    sessionId + " path=" + propertyPath +
                    " handleStartTs=" + cpsStart + " deserStartTs=" + cpsDeserStart +
                    " execStartTs=" + cpsExecStart + " execEndTs=" + cpsExecEnd +
                    " sentTs=" + cpsSentTs +
                    " deserMs=" + (cpsExecStart - cpsDeserStart) +
                    " execMs=" + (cpsExecEnd - cpsExecStart) +
                    " sendMs=" + (cpsSentTs - cpsExecEnd) +
                    " totalMs=" + (cpsSentTs - cpsStart));

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

    /**
     * Get a stub for a round-robin selected channel from the pool.
     * Stubs are lightweight wrappers, so creating one per-request is cheap.
     * This distributes streams across multiple TCP connections, avoiding
     * HTTP/2 flow control contention and head-of-line blocking.
     */
    private JsEngineStreamingServiceGrpc.JsEngineStreamingServiceStub getStub() {
        ManagedChannel channel = connectionManager.getClientChannel(grpcTarget);
        return JsEngineStreamingServiceGrpc.newStub(channel);
    }

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
