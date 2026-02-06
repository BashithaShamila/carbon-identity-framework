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
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.MetadataUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteEngineTransport;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.EvaluateRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.EvaluateResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ExecuteCallbackRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ExecuteCallbackResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.grpc.JsEngineServiceGrpc;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

/**
 * gRPC implementation of RemoteEngineTransport.
 * <p>
 * Uses gRPC with blocking stubs for synchronous communication with the remote JavaScript engine.
 * Provides request/response communication via gRPC unary RPCs.
 * <p>
 * Features:
 * - Automatic connection management via GrpcConnectionManager
 * - Configurable deadline/timeout per request
 * - Metadata propagation (correlation IDs, session info)
 * - Graceful error handling and retry logic
 */
public class GrpcTransportImpl implements RemoteEngineTransport {

    private static final Log log = LogFactory.getLog(GrpcTransportImpl.class);

    private final String grpcTarget;
    private final int requestTimeout; // seconds
    private final GrpcConnectionManager connectionManager;
    private JsEngineServiceGrpc.JsEngineServiceBlockingStub blockingStub;

    // Correlation ID for request tracing
    private final String correlationId;

    /**
     * Create a new gRPC transport.
     *
     * @param grpcTarget gRPC target address (e.g., "localhost:50051")
     */
    public GrpcTransportImpl(String grpcTarget) {
        this(grpcTarget, 30); // 30 second default timeout
    }

    /**
     * Create a new gRPC transport with custom timeout.
     *
     * @param grpcTarget     gRPC target address (e.g., "localhost:50051")
     * @param requestTimeout Request timeout in seconds
     */
    public GrpcTransportImpl(String grpcTarget, int requestTimeout) {
        this.grpcTarget = grpcTarget;
        this.requestTimeout = requestTimeout;
        this.connectionManager = GrpcConnectionManager.getInstance();
        this.correlationId = UUID.randomUUID().toString();
        log.debug("[GrpcTransportImpl] Created for target: " + grpcTarget +
                ", timeout: " + requestTimeout + "s, correlationId: " + correlationId);
    }

    @Override
    public EvaluateResponse sendEvaluate(EvaluateRequest request) throws IOException {
        log.debug("[GrpcTransportImpl] Sending evaluate request for session: " + request.getSessionId());

        try {
            ensureStub();

            // Add deadline and metadata
            JsEngineServiceGrpc.JsEngineServiceBlockingStub stubWithDeadline =
                    blockingStub.withDeadlineAfter(requestTimeout, TimeUnit.SECONDS);

            // Call RPC
            long startTime = System.currentTimeMillis();
            EvaluateResponse response = stubWithDeadline.evaluate(request);
            long elapsed = System.currentTimeMillis() - startTime;

            log.debug("[GrpcTransportImpl] Evaluate request completed in " + elapsed + "ms, " +
                    "success: " + response.getSuccess());

            return response;

        } catch (StatusRuntimeException e) {
            String errorMsg = "gRPC evaluate request failed: " + e.getStatus().getDescription();
            log.error("[GrpcTransportImpl] " + errorMsg, e);
            throw new IOException(errorMsg, e);
        } catch (Exception e) {
            String errorMsg = "Unexpected error during evaluate request: " + e.getMessage();
            log.error("[GrpcTransportImpl] " + errorMsg, e);
            throw new IOException(errorMsg, e);
        }
    }

    @Override
    public ExecuteCallbackResponse sendExecuteCallback(ExecuteCallbackRequest request) throws IOException {
        log.debug("[GrpcTransportImpl] Sending executeCallback request for session: " + request.getSessionId());

        try {
            ensureStub();

            // Add deadline
            JsEngineServiceGrpc.JsEngineServiceBlockingStub stubWithDeadline =
                    blockingStub.withDeadlineAfter(requestTimeout, TimeUnit.SECONDS);

            // Call RPC
            long startTime = System.currentTimeMillis();
            ExecuteCallbackResponse response = stubWithDeadline.executeCallback(request);
            long elapsed = System.currentTimeMillis() - startTime;

            log.debug("[GrpcTransportImpl] ExecuteCallback request completed in " + elapsed + "ms, " +
                    "success: " + response.getSuccess());

            return response;

        } catch (StatusRuntimeException e) {
            String errorMsg = "gRPC executeCallback request failed: " + e.getStatus().getDescription();
            log.error("[GrpcTransportImpl] " + errorMsg, e);
            throw new IOException(errorMsg, e);
        } catch (Exception e) {
            String errorMsg = "Unexpected error during executeCallback request: " + e.getMessage();
            log.error("[GrpcTransportImpl] " + errorMsg, e);
            throw new IOException(errorMsg, e);
        }
    }

    @Override
    public void connect() throws IOException {
        log.debug("[GrpcTransportImpl] Connecting to gRPC target: " + grpcTarget);
        try {
            ensureStub();
            log.info("[GrpcTransportImpl] Connected successfully to: " + grpcTarget);
        } catch (Exception e) {
            String errorMsg = "Failed to connect to gRPC target: " + grpcTarget;
            log.error("[GrpcTransportImpl] " + errorMsg, e);
            throw new IOException(errorMsg, e);
        }
    }

    @Override
    public boolean isConnected() {
        return connectionManager.isClientChannelConnected() && blockingStub != null;
    }

    @Override
    public void close() throws IOException {
        log.debug("[GrpcTransportImpl] Closing gRPC transport");
        // Note: We don't close the shared channel here - it's managed by GrpcConnectionManager
        // This allows multiple RemoteJsEngine instances to share the same channel
        blockingStub = null;
    }

    /**
     * Ensure blocking stub is initialized with channel and interceptors.
     */
    private void ensureStub() {
        if (blockingStub == null) {
            log.debug("[GrpcTransportImpl] Initializing blocking stub");

            // Get channel from connection manager
            ManagedChannel channel = connectionManager.getClientChannel(grpcTarget);

            // Create blocking stub
            blockingStub = JsEngineServiceGrpc.newBlockingStub(channel);

            // Attach metadata interceptor for correlation ID
            Metadata metadata = new Metadata();
            Metadata.Key<String> correlationIdKey = Metadata.Key.of("Correlation-ID", ASCII_STRING_MARSHALLER);
            metadata.put(correlationIdKey, correlationId);

            blockingStub = blockingStub.withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata));

            log.debug("[GrpcTransportImpl] Blocking stub initialized with correlation ID: " + correlationId);
        }
    }

    /**
     * Get the gRPC target address.
     *
     * @return Target address
     */
    public String getGrpcTarget() {
        return grpcTarget;
    }

    /**
     * Get the correlation ID for this transport instance.
     *
     * @return Correlation ID
     */
    public String getCorrelationId() {
        return correlationId;
    }
}
