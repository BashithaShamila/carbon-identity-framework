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

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.server;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.CallbackServer;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteEngineTransport;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.EvaluateRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.EvaluateResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ExecuteCallbackRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ExecuteCallbackResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.StreamMessage;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.grpc.JsEngineStreamingServiceGrpc;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Bidirectional streaming gRPC transport implementation.
 * Manages per-session HTTP/2 stream lifecycle for script evaluation and callback execution.
 * <p>
 * Each {@link #sendEvaluate(EvaluateRequest, HostFunctionHandler)} /
 * {@link #sendExecuteCallback(ExecuteCallbackRequest, HostFunctionHandler)} call opens its own
 * HTTP/2 stream. This gives each session its own lock, its own stream lifecycle, and avoids
 * contention between concurrent sessions. The stream closes after the response is received.
 * <p>
 * External callback handling (host functions, context property get/set) is delegated to
 * {@link ExternalCallbackHandler}, keeping this class focused on stream mechanics.
 * <p>
 * Thread model:
 * <ul>
 *   <li>IS HTTP thread calls sendEvaluate()/sendExecuteCallback() and blocks on CompletableFuture</li>
 *   <li>gRPC event thread receives StreamMessage via onNext() and dispatches to callback executor</li>
 *   <li>Callback executor invokes {@link ExternalCallbackHandler} methods</li>
 *   <li>All sends to a session's stream are synchronized via that session's lock</li>
 * </ul>
 */
public class GrpcStreamingTransportImpl implements RemoteEngineTransport, CallbackServer {

    private static final Log log = LogFactory.getLog(GrpcStreamingTransportImpl.class);

    private final String grpcTarget;
    private final int requestTimeout;
    private final GrpcConnectionManager connectionManager;
    private final String correlationId;
    private final ExecutorService callbackExecutor = Executors.newCachedThreadPool();
    private final ExternalCallbackHandler callbackHandler = new ExternalCallbackHandler();

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
        throw new UnsupportedOperationException(
                "Use sendEvaluate(request, handler) in bidirectional streaming mode.");
    }

    @Override
    public EvaluateResponse sendEvaluate(EvaluateRequest request, HostFunctionHandler handler) throws IOException {
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

        // Outbound stream reference for closure capture — set immediately after stream creation.
        // This replaces the old streamRegistry map that routed by sessionId.
        AtomicReference<StreamObserver<StreamMessage>> outboundRef = new AtomicReference<>();

        long t1 = System.currentTimeMillis();
        StreamObserver<StreamMessage> outboundStream = stub.executeScript(
                createResponseObserver(sessionId, evalFuture, null, handler, outboundRef, lock, t0));
        outboundRef.set(outboundStream);
        long t2 = System.currentTimeMillis();
        System.out.println("[PERF] [" + t2 + "] IS STREAM_OPENED session=" + sessionId +
                " startTs=" + t0 + " stubReadyTs=" + t1 + " streamOpenedTs=" + t2 +
                " openMs=" + (t2 - t1) + " sinceStartMs=" + (t2 - t0));

        // Send the evaluate request (sessionId kept in protobuf for debugging/tracing)
        StreamMessage streamMsg = StreamMessage.newBuilder()
                .setSessionId(sessionId)
                .setEvaluateRequest(request)
                .build();

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
        throw new UnsupportedOperationException(
                "Use sendExecuteCallback(request, handler) in bidirectional streaming mode.");
    }

    @Override
    public ExecuteCallbackResponse sendExecuteCallback(ExecuteCallbackRequest request,
                                                       HostFunctionHandler handler) throws IOException {
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

        // Outbound stream reference for closure capture — set immediately after stream creation.
        AtomicReference<StreamObserver<StreamMessage>> outboundRef = new AtomicReference<>();

        long t1 = System.currentTimeMillis();
        StreamObserver<StreamMessage> outboundStream = stub.executeScript(
                createResponseObserver(sessionId, null, callbackFuture, handler, outboundRef, lock, t0));
        outboundRef.set(outboundStream);
        long t2 = System.currentTimeMillis();
        System.out.println("[PERF] [" + t2 + "] IS EXEC_CALLBACK_STREAM_OPENED session=" + sessionId +
                " startTs=" + t0 + " stubReadyTs=" + t1 + " streamOpenedTs=" + t2 +
                " openMs=" + (t2 - t1) + " sinceStartMs=" + (t2 - t0));

        // Send the execute callback request (sessionId kept in protobuf for debugging/tracing)
        StreamMessage streamMsg = StreamMessage.newBuilder()
                .setSessionId(sessionId)
                .setExecuteCallbackRequest(request)
                .build();

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
        // Per-session cleanup is handled by stream onCompleted() in finally.
    }

    // ============ Stream Observer Factory ============

    /**
     * Creates a StreamObserver that handles incoming messages from the External.
     * Routes terminal responses (evaluate/callback) to their CompletableFuture.
     * Routes External callbacks (host function, context property) to {@link ExternalCallbackHandler}
     * via the callback executor.
     * <p>
     * The handler and outbound stream reference are captured in the closure, eliminating the need
     * for session-based lookup maps (sessionHandlers, streamRegistry) from the two-channel design.
     */
    private StreamObserver<StreamMessage> createResponseObserver(
            String sessionId,
            CompletableFuture<EvaluateResponse> evalFuture,
            CompletableFuture<ExecuteCallbackResponse> callbackFuture,
            HostFunctionHandler handler,
            AtomicReference<StreamObserver<StreamMessage>> outboundRef,
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
                                callbackHandler.handleHostFunction(sessionId,
                                        message.getHostFunctionRequest(),
                                        handler, outboundRef.get(), streamLock));
                        break;

                    case CONTEXT_PROPERTY_REQUEST:
                        System.out.println("[PERF] [" + now + "] IS CTX_PROP_REQUEST_RECEIVED session=" +
                                sessionId + " path=" + message.getContextPropertyRequest().getPropertyPath() +
                                " streamStartTs=" + streamStartTime +
                                " receivedTs=" + now +
                                " sinceStreamStartMs=" + (now - streamStartTime));
                        callbackExecutor.submit(() ->
                                callbackHandler.handleContextProperty(sessionId,
                                        message.getContextPropertyRequest(),
                                        handler, outboundRef.get(), streamLock));
                        break;

                    case CONTEXT_PROPERTY_SET_REQUEST:
                        System.out.println("[PERF] [" + now + "] IS CTX_PROP_SET_REQUEST_RECEIVED session=" +
                                sessionId + " path=" + message.getContextPropertySetRequest().getPropertyPath() +
                                " streamStartTs=" + streamStartTime +
                                " receivedTs=" + now +
                                " sinceStreamStartMs=" + (now - streamStartTime));
                        callbackExecutor.submit(() ->
                                callbackHandler.handleContextPropertySet(sessionId,
                                        message.getContextPropertySetRequest(),
                                        handler, outboundRef.get(), streamLock));
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

    // ============ Channel Management ============

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
}
