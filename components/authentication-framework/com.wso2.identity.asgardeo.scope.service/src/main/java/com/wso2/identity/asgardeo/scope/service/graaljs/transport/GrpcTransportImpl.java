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
// This class was part of the old unidirectional gRPC transport that used:
//   - Separate unary RPCs for evaluate/executeCallback (IS → Sidecar)
//   - A separate callback server on port 50052 (Sidecar → IS via GrpcCallbackServerImpl)
//
// Replaced by: GrpcStreamingTransportImpl (single bidirectional stream on port 50051)
// The streaming approach handles both requests and callbacks on the same stream,
// eliminating the need for a separate callback channel.
//
// TODO: Remove entirely during transport layer refactoring.
// ============================================================================

/*
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

public class GrpcTransportImpl implements RemoteEngineTransport {

    private static final Log log = LogFactory.getLog(GrpcTransportImpl.class);

    private final String grpcTarget;
    private final int requestTimeout;
    private final GrpcConnectionManager connectionManager;
    private JsEngineServiceGrpc.JsEngineServiceBlockingStub blockingStub;

    private final String correlationId;

    public GrpcTransportImpl(String grpcTarget) {
        this(grpcTarget, 30);
    }

    public GrpcTransportImpl(String grpcTarget, int requestTimeout) {
        this.grpcTarget = grpcTarget;
        this.requestTimeout = requestTimeout;
        this.connectionManager = GrpcConnectionManager.getInstance();
        this.correlationId = UUID.randomUUID().toString();
        log.info("[GrpcTransportImpl] ========== CONSTRUCTOR ==========");
        log.info("[GrpcTransportImpl] Created for target: " + grpcTarget +
                ", timeout: " + requestTimeout + "s, correlationId: " + correlationId);
        log.info("[GrpcTransportImpl] Instance hashCode: " + System.identityHashCode(this));
    }

    @Override
    public EvaluateResponse sendEvaluate(EvaluateRequest request) throws IOException {
        log.info("[GrpcTransportImpl] ========== sendEvaluate() CALLED ==========");
        log.info("[GrpcTransportImpl] Session: " + request.getSessionId());
        log.info("[GrpcTransportImpl] Target: " + grpcTarget + ", Timeout: " + requestTimeout + "s");
        log.info("[GrpcTransportImpl] CorrelationId: " + correlationId);
        log.info("[GrpcTransportImpl] Script length: " + request.getScript().length() + " chars");
        log.info("[GrpcTransportImpl] Script preview: " +
                request.getScript().substring(0, Math.min(200, request.getScript().length())) + "...");
        log.info("[GrpcTransportImpl] Bindings count: " + request.getBindingsCount());
        log.info("[GrpcTransportImpl] Bindings keys: " + request.getBindingsMap().keySet());
        log.info("[GrpcTransportImpl] Host functions count: " + request.getHostFunctionsCount());
        for (int i = 0; i < request.getHostFunctionsCount(); i++) {
            log.info("[GrpcTransportImpl] Host function[" + i + "]: " + request.getHostFunctions(i).getName());
        }
        log.info("[GrpcTransportImpl] Callback socket path: " + request.getCallbackSocketPath());
        log.info("[GrpcTransportImpl] Source identifier: " + request.getSourceIdentifier());
        log.info("[GrpcTransportImpl] Statement limit: " + request.getStatementLimit());
        if (request.hasContextData()) {
            log.info("[GrpcTransportImpl] ContextData present:");
            log.info("[GrpcTransportImpl]   - sessionContextKey: " + request.getContextData().getSessionContextKey());
            log.info("[GrpcTransportImpl]   - currentStep: " + request.getContextData().getCurrentStep());
            log.info("[GrpcTransportImpl]   - username: " + request.getContextData().getUsername());
            log.info("[GrpcTransportImpl]   - userStoreDomain: " + request.getContextData().getUserStoreDomain());
            log.info("[GrpcTransportImpl]   - tenantDomain: " + request.getContextData().getTenantDomain());
            log.info("[GrpcTransportImpl]   - roles: " + request.getContextData().getRolesList());
            log.info("[GrpcTransportImpl]   - claims count: " + request.getContextData().getClaimsCount());
        } else {
            log.info("[GrpcTransportImpl] ContextData: NOT PRESENT");
        }

        try {
            ensureStub();

            JsEngineServiceGrpc.JsEngineServiceBlockingStub stubWithDeadline =
                    blockingStub.withDeadlineAfter(requestTimeout, TimeUnit.SECONDS);

            log.info("[GrpcTransportImpl] >>> Sending gRPC Evaluate request to sidecar at " + grpcTarget + " ...");
            long startTime = System.currentTimeMillis();
            EvaluateResponse response = stubWithDeadline.evaluate(request);
            long elapsed = System.currentTimeMillis() - startTime;

            log.info("[GrpcTransportImpl] <<< Received gRPC Evaluate response");
            log.info("[GrpcTransportImpl] ========== sendEvaluate() RESPONSE ==========");
            log.info("[GrpcTransportImpl] Network elapsed: " + elapsed + "ms");
            log.info("[GrpcTransportImpl] Response success: " + response.getSuccess());
            log.info("[GrpcTransportImpl] Sidecar elapsed (from response): " + response.getElapsedMs() + "ms");
            if (!response.getSuccess()) {
                log.error("[GrpcTransportImpl] ERROR in response - message: " + response.getErrorMessage());
                log.error("[GrpcTransportImpl] ERROR in response - type: " + response.getErrorType());
            }
            log.info("[GrpcTransportImpl] Updated bindings count: " + response.getUpdatedBindingsCount());
            log.info("[GrpcTransportImpl] Updated bindings keys: " + response.getUpdatedBindingsMap().keySet());
            log.info("[GrpcTransportImpl] Result valueCase: " + response.getResult().getValueCase());
            log.info("[GrpcTransportImpl] ========== sendEvaluate() COMPLETED ==========");

            return response;

        } catch (StatusRuntimeException e) {
            String errorMsg = "gRPC evaluate request failed: " + e.getStatus().getDescription();
            log.error("[GrpcTransportImpl] StatusRuntimeException during evaluate");
            log.error("[GrpcTransportImpl] Status code: " + e.getStatus().getCode());
            log.error("[GrpcTransportImpl] Status description: " + e.getStatus().getDescription());
            log.error("[GrpcTransportImpl] Full status: " + e.getStatus(), e);
            throw new IOException(errorMsg, e);
        } catch (Exception e) {
            String errorMsg = "Unexpected error during evaluate request: " + e.getMessage();
            log.error("[GrpcTransportImpl] Exception type: " + e.getClass().getName());
            log.error("[GrpcTransportImpl] " + errorMsg, e);
            throw new IOException(errorMsg, e);
        }
    }

    @Override
    public ExecuteCallbackResponse sendExecuteCallback(ExecuteCallbackRequest request) throws IOException {
        log.info("[GrpcTransportImpl] ========== sendExecuteCallback() CALLED ==========");
        log.info("[GrpcTransportImpl] Session: " + request.getSessionId());
        log.info("[GrpcTransportImpl] Target: " + grpcTarget + ", Timeout: " + requestTimeout + "s");
        log.info("[GrpcTransportImpl] CorrelationId: " + correlationId);
        log.info("[GrpcTransportImpl] Function source length: " + request.getFunctionSource().length() + " chars");
        log.info("[GrpcTransportImpl] Function source preview: " +
                request.getFunctionSource().substring(0, Math.min(200, request.getFunctionSource().length())) + "...");
        log.info("[GrpcTransportImpl] Arguments count: " + request.getArgumentsCount());
        for (int i = 0; i < request.getArgumentsCount(); i++) {
            log.info("[GrpcTransportImpl] Argument[" + i + "] valueCase: " + request.getArguments(i).getValueCase());
        }
        log.info("[GrpcTransportImpl] Bindings count: " + request.getBindingsCount());
        log.info("[GrpcTransportImpl] Bindings keys: " + request.getBindingsMap().keySet());
        log.info("[GrpcTransportImpl] Host functions count: " + request.getHostFunctionsCount());
        for (int i = 0; i < request.getHostFunctionsCount(); i++) {
            log.info("[GrpcTransportImpl] Host function[" + i + "]: " + request.getHostFunctions(i).getName());
        }
        log.info("[GrpcTransportImpl] Callback socket path: " + request.getCallbackSocketPath());
        if (request.hasContextData()) {
            log.info("[GrpcTransportImpl] ContextData present:");
            log.info("[GrpcTransportImpl]   - sessionContextKey: " + request.getContextData().getSessionContextKey());
            log.info("[GrpcTransportImpl]   - currentStep: " + request.getContextData().getCurrentStep());
            log.info("[GrpcTransportImpl]   - username: " + request.getContextData().getUsername());
        } else {
            log.info("[GrpcTransportImpl] ContextData: NOT PRESENT");
        }

        try {
            ensureStub();

            JsEngineServiceGrpc.JsEngineServiceBlockingStub stubWithDeadline =
                    blockingStub.withDeadlineAfter(requestTimeout, TimeUnit.SECONDS);

            log.info("[GrpcTransportImpl] >>> Sending gRPC ExecuteCallback to " + grpcTarget + " ...");
            long startTime = System.currentTimeMillis();
            ExecuteCallbackResponse response = stubWithDeadline.executeCallback(request);
            long elapsed = System.currentTimeMillis() - startTime;

            log.info("[GrpcTransportImpl] <<< Received gRPC ExecuteCallback response");
            log.info("[GrpcTransportImpl] ========== sendExecuteCallback() RESPONSE ==========");
            log.info("[GrpcTransportImpl] Network elapsed: " + elapsed + "ms");
            log.info("[GrpcTransportImpl] Response success: " + response.getSuccess());
            log.info("[GrpcTransportImpl] Sidecar elapsed (from response): " + response.getElapsedMs() + "ms");
            if (!response.getSuccess()) {
                log.error("[GrpcTransportImpl] ERROR in response - message: " + response.getErrorMessage());
            }
            log.info("[GrpcTransportImpl] Updated bindings count: " + response.getUpdatedBindingsCount());
            log.info("[GrpcTransportImpl] Updated bindings keys: " + response.getUpdatedBindingsMap().keySet());
            log.info("[GrpcTransportImpl] Result valueCase: " + response.getResult().getValueCase());
            log.info("[GrpcTransportImpl] ========== sendExecuteCallback() COMPLETED ==========");

            return response;

        } catch (StatusRuntimeException e) {
            String errorMsg = "gRPC executeCallback request failed: " + e.getStatus().getDescription();
            log.error("[GrpcTransportImpl] StatusRuntimeException during executeCallback");
            log.error("[GrpcTransportImpl] Status code: " + e.getStatus().getCode());
            log.error("[GrpcTransportImpl] Status description: " + e.getStatus().getDescription());
            log.error("[GrpcTransportImpl] Full status: " + e.getStatus(), e);
            throw new IOException(errorMsg, e);
        } catch (Exception e) {
            String errorMsg = "Unexpected error during executeCallback request: " + e.getMessage();
            log.error("[GrpcTransportImpl] Exception type: " + e.getClass().getName());
            log.error("[GrpcTransportImpl] " + errorMsg, e);
            throw new IOException(errorMsg, e);
        }
    }

    @Override
    public void connect() throws IOException {
        log.info("[GrpcTransportImpl] ========== connect() CALLED ==========");
        log.info("[GrpcTransportImpl] Connecting to gRPC target: " + grpcTarget);
        log.info("[GrpcTransportImpl] CorrelationId: " + correlationId);
        try {
            ensureStub();
            log.info("[GrpcTransportImpl] Connected successfully to: " + grpcTarget);
            log.info("[GrpcTransportImpl] ========== connect() COMPLETED ==========");
        } catch (Exception e) {
            String errorMsg = "Failed to connect to gRPC target: " + grpcTarget;
            log.error("[GrpcTransportImpl] " + errorMsg, e);
            throw new IOException(errorMsg, e);
        }
    }

    @Override
    public boolean isConnected() {
        boolean channelConnected = connectionManager.isClientChannelConnected();
        boolean stubExists = blockingStub != null;
        boolean result = channelConnected && stubExists;
        log.info("[GrpcTransportImpl] isConnected() - channelConnected: " + channelConnected +
                ", stubExists: " + stubExists + ", result: " + result);
        return result;
    }

    @Override
    public void close() throws IOException {
        log.info("[GrpcTransportImpl] ========== close() CALLED ==========");
        log.info("[GrpcTransportImpl] Closing gRPC transport for correlationId: " + correlationId);
        blockingStub = null;
        log.info("[GrpcTransportImpl] Blocking stub set to null (shared channel NOT closed)");
        log.info("[GrpcTransportImpl] ========== close() COMPLETED ==========");
    }

    private void ensureStub() {
        log.info("[GrpcTransportImpl] ensureStub() - current stub: " + (blockingStub == null ? "NULL" : "EXISTS"));
        if (blockingStub == null) {
            log.info("[GrpcTransportImpl] Initializing NEW blocking stub for target: " + grpcTarget);

            ManagedChannel channel = connectionManager.getClientChannel(grpcTarget);
            log.info("[GrpcTransportImpl] Got channel from connectionManager, channel state: " +
                    (channel.isShutdown() ? "SHUTDOWN" : channel.isTerminated() ? "TERMINATED" : "ACTIVE"));

            blockingStub = JsEngineServiceGrpc.newBlockingStub(channel);
            log.info("[GrpcTransportImpl] Created JsEngineService blocking stub");

            Metadata metadata = new Metadata();
            Metadata.Key<String> correlationIdKey = Metadata.Key.of("Correlation-ID", ASCII_STRING_MARSHALLER);
            metadata.put(correlationIdKey, correlationId);

            blockingStub = blockingStub.withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata));

            log.info("[GrpcTransportImpl] Blocking stub initialized with correlationId: " + correlationId);
        } else {
            log.info("[GrpcTransportImpl] Reusing existing blocking stub");
        }
    }

    public String getGrpcTarget() {
        return grpcTarget;
    }

    public String getCorrelationId() {
        return correlationId;
    }
}
*/
