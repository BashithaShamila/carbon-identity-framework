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

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.server;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.JsEngineFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.RemoteEngineTransport;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.RemoteJsEngine;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.proto.EvaluateRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.proto.EvaluateResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.proto.ExecuteCallbackRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.proto.ExecuteCallbackResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.proto.StreamMessage;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.proto.grpc.JsEngineStreamingServiceGrpc;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * Bidirectional streaming gRPC transport implementation.
 * Manages per-session HTTP/2 stream lifecycle for script evaluation and callback execution.
 * <p>
 * Each {@link #sendEvaluate(EvaluateRequest, RemoteJsEngine)} /
 * {@link #sendExecuteCallback(ExecuteCallbackRequest, RemoteJsEngine)} call opens its own
 * HTTP/2 stream. This gives each session its own lock, its own stream lifecycle, and avoids
 * contention between concurrent sessions. The stream closes after the response is received.
 * <p>
 * External callback handling (host functions, context property get/set) is delegated to
 * {@link ExternalCallbackHandler}, keeping this class focused on stream mechanics.
 * <p>
 * Thread model:
 * <ul>
 *   <li>IS HTTP thread (Thread A) calls sendEvaluate()/sendExecuteCallback(), sends the initial
 *       request, then enters a message loop polling a BlockingQueue</li>
 *   <li>gRPC event thread receives StreamMessage via onNext() and enqueues to the BlockingQueue</li>
 *   <li>Thread A polls the queue, dispatches callbacks to {@link ExternalCallbackHandler} inline
 *       (same thread), and returns when a terminal response arrives</li>
 *   <li>All stream writes happen on Thread A — no concurrent writer contention</li>
 * </ul>
 */
public class GrpcStreamingTransportImpl implements RemoteEngineTransport {

    private static final Log log = LogFactory.getLog(GrpcStreamingTransportImpl.class);

    /**
     * Sentinel message used to unblock the message loop when the stream terminates
     * via onError() or onCompleted(). Identity-compared (==) in the message loop.
     */
    private static final StreamMessage STREAM_TERMINATED_SENTINEL = StreamMessage.getDefaultInstance();

    private final String grpcTarget;
    private final int requestTimeout;
    private final GrpcConnectionManager connectionManager;
    private final String correlationId;
    private final ExternalCallbackHandler callbackHandler = new ExternalCallbackHandler();

    public GrpcStreamingTransportImpl(String grpcTarget) {
        this(grpcTarget, 600);
    }

    public GrpcStreamingTransportImpl(String grpcTarget, int requestTimeout) {
        this.grpcTarget = grpcTarget;
        this.requestTimeout = requestTimeout;
        this.connectionManager = GrpcConnectionManager.getInstance();
        this.correlationId = UUID.randomUUID().toString();
        if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
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
    public EvaluateResponse sendEvaluate(EvaluateRequest request, RemoteJsEngine handler) throws IOException {
        String sessionId = request.getSessionId();
        long t0 = System.currentTimeMillis();
        if (JsEngineFactory.isTracingEnabled()) {
            System.out.println("[PERF] [" + t0 + "] IS EVALUATE_START session=" + sessionId +
                    " startTs=" + t0 + " scriptLen=" + request.getScript().length());
        }
        if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] sendEvaluate() - session: " + sessionId +
                    ", script length: " + request.getScript().length());
        }

        JsEngineStreamingServiceGrpc.JsEngineStreamingServiceStub stub = getStub();

        BlockingQueue<StreamMessage> messageQueue = new LinkedBlockingQueue<>();
        AtomicReference<Throwable> streamError = new AtomicReference<>();
        final Object streamLock = new Object();

        long t1 = System.currentTimeMillis();
        StreamObserver<StreamMessage> outboundStream = stub.executeScript(
                createResponseObserver(sessionId, messageQueue, streamError, t0));
        long t2 = System.currentTimeMillis();
        if (JsEngineFactory.isTracingEnabled()) {
            System.out.println("[PERF] [" + t2 + "] IS STREAM_OPENED session=" + sessionId +
                    " startTs=" + t0 + " stubReadyTs=" + t1 + " streamOpenedTs=" + t2 +
                    " openMs=" + (t2 - t1) + " sinceStartMs=" + (t2 - t0));
        }

        // Send the evaluate request (sessionId kept in protobuf for debugging/tracing)
        StreamMessage streamMsg = StreamMessage.newBuilder()
                .setSessionId(sessionId)
                .setEvaluateRequest(request)
                .build();

        synchronized (streamLock) {
            outboundStream.onNext(streamMsg);
        }
        long t3 = System.currentTimeMillis();
        if (JsEngineFactory.isTracingEnabled()) {
            System.out.println("[PERF] [" + t3 + "] IS EVALUATE_SENT session=" + sessionId +
                    " startTs=" + t0 + " streamOpenedTs=" + t2 + " sentTs=" + t3 +
                    " sendMs=" + (t3 - t2) + " sinceStartMs=" + (t3 - t0));
        }
        if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] Sent EvaluateRequest on stream, session: " + sessionId);
        }

        try {
            long deadlineNanos = System.nanoTime() + TimeUnit.SECONDS.toNanos(requestTimeout);
            StreamMessage terminalMsg = processMessageLoop(
                    messageQueue, streamError, sessionId, handler, outboundStream, streamLock, deadlineNanos);

            EvaluateResponse response = terminalMsg.getEvaluateResponse();
            long t4 = System.currentTimeMillis();
            if (JsEngineFactory.isTracingEnabled()) {
                System.out.println("[PERF] [" + t4 + "] IS EVALUATE_RESPONSE session=" + sessionId +
                        " success=" + response.getSuccess() +
                        " startTs=" + t0 + " sentTs=" + t3 + " responseTs=" + t4 +
                        " waitMs=" + (t4 - t3) + " totalMs=" + (t4 - t0));
            }
            if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
                log.debug("[GrpcStreaming] Received EvaluateResponse, session: " + sessionId +
                        ", success: " + response.getSuccess());
            }
            return response;
        } catch (IOException e) {
            long tErr = System.currentTimeMillis();
            if (e.getMessage() != null && e.getMessage().startsWith("Request timed out")) {
                if (JsEngineFactory.isTracingEnabled()) {
                    System.out.println("[PERF] [" + tErr + "] IS EVALUATE_TIMEOUT session=" +
                            sessionId + " startTs=" + t0 + " sentTs=" + t3 +
                            " timeoutTs=" + tErr + " timeoutMs=" + (tErr - t0));
                }
            } else {
                if (JsEngineFactory.isTracingEnabled()) {
                    System.out.println("[PERF] [" + tErr + "] IS EVALUATE_ERROR session=" +
                            sessionId + " error=" + e.getMessage() +
                            " startTs=" + t0 + " errorTs=" + tErr + " totalMs=" + (tErr - t0));
                }
            }
            throw e;
        } finally {
            synchronized (streamLock) {
                try {
                    outboundStream.onCompleted();
                } catch (Exception e) {
                    if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
                        log.debug("[GrpcStreaming] Error completing stream: " + e.getMessage());
                    }
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
                                                       RemoteJsEngine handler) throws IOException {
        String sessionId = request.getSessionId();
        long t0 = System.currentTimeMillis();
        if (JsEngineFactory.isTracingEnabled()) {
            System.out.println("[PERF] [" + t0 + "] IS EXEC_CALLBACK_START session=" + sessionId +
                    " startTs=" + t0 + " fnLen=" + request.getFunctionSource().length());
        }
        if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] sendExecuteCallback() - session: " + sessionId +
                    ", function length: " + request.getFunctionSource().length());
        }

        JsEngineStreamingServiceGrpc.JsEngineStreamingServiceStub stub = getStub();

        BlockingQueue<StreamMessage> messageQueue = new LinkedBlockingQueue<>();
        AtomicReference<Throwable> streamError = new AtomicReference<>();
        final Object streamLock = new Object();

        long t1 = System.currentTimeMillis();
        StreamObserver<StreamMessage> outboundStream = stub.executeScript(
                createResponseObserver(sessionId, messageQueue, streamError, t0));
        long t2 = System.currentTimeMillis();
        if (JsEngineFactory.isTracingEnabled()) {
            System.out.println("[PERF] [" + t2 + "] IS EXEC_CALLBACK_STREAM_OPENED session=" + sessionId +
                    " startTs=" + t0 + " stubReadyTs=" + t1 + " streamOpenedTs=" + t2 +
                    " openMs=" + (t2 - t1) + " sinceStartMs=" + (t2 - t0));
        }

        // Send the execute callback request (sessionId kept in protobuf for debugging/tracing)
        StreamMessage streamMsg = StreamMessage.newBuilder()
                .setSessionId(sessionId)
                .setExecuteCallbackRequest(request)
                .build();

        synchronized (streamLock) {
            outboundStream.onNext(streamMsg);
        }
        long t3 = System.currentTimeMillis();
        if (JsEngineFactory.isTracingEnabled()) {
            System.out.println("[PERF] [" + t3 + "] IS EXEC_CALLBACK_SENT session=" + sessionId +
                    " startTs=" + t0 + " streamOpenedTs=" + t2 + " sentTs=" + t3 +
                    " sendMs=" + (t3 - t2) + " sinceStartMs=" + (t3 - t0));
        }
        if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] Sent ExecuteCallbackRequest on stream, session: " + sessionId);
        }

        try {
            long deadlineNanos = System.nanoTime() + TimeUnit.SECONDS.toNanos(requestTimeout);
            StreamMessage terminalMsg = processMessageLoop(
                    messageQueue, streamError, sessionId, handler, outboundStream, streamLock, deadlineNanos);

            ExecuteCallbackResponse response = terminalMsg.getExecuteCallbackResponse();
            long t4 = System.currentTimeMillis();
            if (JsEngineFactory.isTracingEnabled()) {
                System.out.println("[PERF] [" + t4 + "] IS EXEC_CALLBACK_RESPONSE session=" + sessionId +
                        " success=" + response.getSuccess() +
                        " startTs=" + t0 + " sentTs=" + t3 + " responseTs=" + t4 +
                        " waitMs=" + (t4 - t3) + " totalMs=" + (t4 - t0));
            }
            if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
                log.debug("[GrpcStreaming] Received ExecuteCallbackResponse, session: " + sessionId +
                        ", success: " + response.getSuccess());
            }
            return response;
        } catch (IOException e) {
            long tErr = System.currentTimeMillis();
            if (e.getMessage() != null && e.getMessage().startsWith("Request timed out")) {
                if (JsEngineFactory.isTracingEnabled()) {
                    System.out.println("[PERF] [" + tErr + "] IS EXEC_CALLBACK_TIMEOUT session=" +
                            sessionId + " startTs=" + t0 + " sentTs=" + t3 +
                            " timeoutTs=" + tErr + " timeoutMs=" + (tErr - t0));
                }
            } else {
                if (JsEngineFactory.isTracingEnabled()) {
                    System.out.println("[PERF] [" + tErr + "] IS EXEC_CALLBACK_ERROR session=" +
                            sessionId + " error=" + e.getMessage() +
                            " startTs=" + t0 + " errorTs=" + tErr + " totalMs=" + (tErr - t0));
                }
            }
            throw e;
        } finally {
            synchronized (streamLock) {
                try {
                    outboundStream.onCompleted();
                } catch (Exception e) {
                    if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
                        log.debug("[GrpcStreaming] Error completing stream: " + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void connect() throws IOException {
        if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] connect() to " + grpcTarget);
        }
        // Verify channel pool is initialized by requesting a channel
        connectionManager.getClientChannel(grpcTarget);
        if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] Connected successfully to: " + grpcTarget);
        }
    }

    @Override
    public boolean isConnected() {
        return connectionManager.isClientChannelConnected();
    }

    @Override
    public void close() throws IOException {
        if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] close() called - singleton transport, channel pool remains active, " +
                    "correlationId: " + correlationId);
        }
        // Don't shutdown channel pool - this is a singleton transport shared across all sessions.
        // Per-session cleanup is handled by stream onCompleted() in finally.
    }

    // ============ Message Loop ============

    /**
     * Polls the message queue, dispatches callback messages to {@link ExternalCallbackHandler} inline
     * on the calling thread (Thread A), and returns when a terminal response
     * (EvaluateResponse or ExecuteCallbackResponse) is received.
     * <p>
     * Timeout is deadline-based: the total time across ALL callbacks + sidecar execution
     * must not exceed {@code deadlineNanos}. Each poll uses the remaining time until the deadline.
     * Uses monotonic {@code System.nanoTime()} to be immune to wall-clock adjustments (NTP).
     *
     * @param messageQueue The queue populated by the gRPC response observer.
     * @param streamError  Holds any error set by onError()/onCompleted().
     * @param sessionId    Session identifier for logging.
     * @param handler      The host function handler for this session.
     * @param outbound     The outbound stream observer for sending callback responses.
     * @param streamLock   Lock for synchronized stream writes.
     * @param deadlineNanos Monotonic nanoTime deadline by which a terminal response must arrive.
     * @return The terminal StreamMessage containing EvaluateResponse or ExecuteCallbackResponse.
     * @throws IOException On timeout, interruption, or stream error.
     */
    private StreamMessage processMessageLoop(
            BlockingQueue<StreamMessage> messageQueue,
            AtomicReference<Throwable> streamError,
            String sessionId,
            RemoteJsEngine handler,
            StreamObserver<StreamMessage> outbound,
            Object streamLock,
            long deadlineNanos) throws IOException {

        while (true) {
            long remainingNanos = deadlineNanos - System.nanoTime();
            if (remainingNanos <= 0) {
                throw new IOException("Request timed out after " + requestTimeout + "s");
            }

            StreamMessage msg;
            try {
                msg = messageQueue.poll(remainingNanos, NANOSECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Request interrupted", e);
            }

            if (msg == null) {
                // poll() returned null — deadline elapsed
                throw new IOException("Request timed out after " + requestTimeout + "s");
            }

            // Check for stream termination sentinel from onError()/onCompleted()
            if (msg == STREAM_TERMINATED_SENTINEL) {
                Throwable err = streamError.get();
                throw new IOException(
                        err != null ? "Stream error: " + err.getMessage() : "Stream terminated unexpectedly", err);
            }

            switch (msg.getPayloadCase()) {
                case EVALUATE_RESPONSE:
                case EXECUTE_CALLBACK_RESPONSE:
                    // Terminal response — return to caller
                    return msg;

                case HOST_FUNCTION_REQUEST:
                    callbackHandler.handleHostFunction(sessionId,
                            msg.getHostFunctionRequest(), handler, outbound, streamLock);
                    break;

                case CONTEXT_PROPERTY_REQUEST:
                    callbackHandler.handleContextProperty(sessionId,
                            msg.getContextPropertyRequest(), handler, outbound, streamLock);
                    break;

                case CONTEXT_PROPERTY_SET_REQUEST:
                    callbackHandler.handleContextPropertySet(sessionId,
                            msg.getContextPropertySetRequest(), handler, outbound, streamLock);
                    break;

                default:
                    log.warn("[GrpcStreaming] Unexpected message type in loop: " + msg.getPayloadCase());
            }

            // NOTE: Do NOT check streamError here. If the External sent a terminal response
            // followed by onCompleted() while Thread A was processing a callback, both the
            // response and the SENTINEL are already in the queue. Checking streamError here
            // would throw immediately without dequeuing the terminal response, causing a
            // misleading "Stream completed without response" error. Instead, let the loop
            // poll the queue — it will find either the terminal response (and return it) or
            // the SENTINEL (and throw with the proper error context).
        }
    }

    // ============ Stream Observer Factory ============

    /**
     * Creates a StreamObserver that enqueues all incoming messages into the BlockingQueue.
     * Thread A (the IS HTTP thread) polls this queue in {@link #processMessageLoop}.
     * <p>
     * onError()/onCompleted() signal stream termination by setting the error reference
     * and offering a sentinel message to unblock the queue poll.
     */
    private StreamObserver<StreamMessage> createResponseObserver(
            String sessionId,
            BlockingQueue<StreamMessage> messageQueue,
            AtomicReference<Throwable> streamError,
            long streamStartTime) {

        return new StreamObserver<StreamMessage>() {
            @Override
            public void onNext(StreamMessage message) {
                long now = System.currentTimeMillis();
                if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
                    log.debug("[GrpcStreaming] Received message type: " + message.getPayloadCase() +
                            ", session: " + message.getSessionId());
                }

                if (JsEngineFactory.isTracingEnabled()) {
                    switch (message.getPayloadCase()) {
                        case EVALUATE_RESPONSE:
                            System.out.println("[PERF] [" + now + "] IS EVALUATE_RESPONSE_ARRIVED session=" +
                                    sessionId + " streamStartTs=" + streamStartTime +
                                    " arrivedTs=" + now +
                                    " sinceStreamStartMs=" + (now - streamStartTime));
                            break;

                        case EXECUTE_CALLBACK_RESPONSE:
                            System.out.println("[PERF] [" + now + "] IS EXEC_CALLBACK_RESPONSE_ARRIVED session=" +
                                    sessionId + " streamStartTs=" + streamStartTime +
                                    " arrivedTs=" + now +
                                    " sinceStreamStartMs=" + (now - streamStartTime));
                            break;

                        case HOST_FUNCTION_REQUEST:
                            System.out.println("[PERF] [" + now + "] IS HOST_FN_REQUEST_RECEIVED session=" +
                                    sessionId + " fn=" + message.getHostFunctionRequest().getFunctionName() +
                                    " streamStartTs=" + streamStartTime +
                                    " receivedTs=" + now +
                                    " sinceStreamStartMs=" + (now - streamStartTime));
                            break;

                        case CONTEXT_PROPERTY_REQUEST:
                            System.out.println("[PERF] [" + now + "] IS CTX_PROP_REQUEST_RECEIVED session=" +
                                    sessionId + " path=" + message.getContextPropertyRequest().getPropertyPath() +
                                    " streamStartTs=" + streamStartTime +
                                    " receivedTs=" + now +
                                    " sinceStreamStartMs=" + (now - streamStartTime));
                            break;

                        case CONTEXT_PROPERTY_SET_REQUEST:
                            System.out.println("[PERF] [" + now + "] IS CTX_PROP_SET_REQUEST_RECEIVED session=" +
                                    sessionId + " path=" + message.getContextPropertySetRequest().getPropertyPath() +
                                    " streamStartTs=" + streamStartTime +
                                    " receivedTs=" + now +
                                    " sinceStreamStartMs=" + (now - streamStartTime));
                            break;

                        default:
                            log.warn("[GrpcStreaming] Unexpected message type: " + message.getPayloadCase());
                    }
                }
                // All messages go into the queue — Thread A dispatches
                messageQueue.offer(message);
            }

            @Override
            public void onError(Throwable t) {
                long errTs = System.currentTimeMillis();
                if (JsEngineFactory.isTracingEnabled()) {
                    System.out.println("[PERF] [" + errTs + "] IS STREAM_ERROR session=" +
                            sessionId + " streamStartTs=" + streamStartTime +
                            " errorTs=" + errTs + " error=" + t.getMessage() +
                            " sinceStreamStartMs=" + (errTs - streamStartTime));
                }
                log.error("[GrpcStreaming] Stream error, session: " + sessionId, t);
                streamError.set(t);
                messageQueue.offer(STREAM_TERMINATED_SENTINEL);
            }

            @Override
            public void onCompleted() {
                long completedTs = System.currentTimeMillis();
                if (JsEngineFactory.isTracingEnabled()) {
                    System.out.println("[PERF] [" + completedTs + "] IS STREAM_COMPLETED session=" +
                            sessionId + " streamStartTs=" + streamStartTime +
                            " completedTs=" + completedTs +
                            " sinceStreamStartMs=" + (completedTs - streamStartTime));
                }
                if (JsEngineFactory.isTracingEnabled() && log.isDebugEnabled()) {
                    log.debug("[GrpcStreaming] Stream completed, session: " + sessionId);
                }
                // If the message loop hasn't received a terminal response yet,
                // this signals an abnormal completion.
                streamError.compareAndSet(null, new IOException("Stream completed without response"));
                messageQueue.offer(STREAM_TERMINATED_SENTINEL);
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
