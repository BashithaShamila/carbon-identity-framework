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

import io.grpc.stub.StreamObserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.graalvm.polyglot.proxy.ProxyObject;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.Serializer;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.ProxyTypeResolver;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteEngineConstants;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertyRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertyResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertySetRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.ContextPropertySetResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.HostFunctionRequest;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.HostFunctionResponse;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.SerializedProxyObject;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.SerializedValue;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto.StreamMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Handles External callbacks received on the bidirectional gRPC stream:
 * host function invocations, context property reads, and context property writes.
 * <p>
 * Thread model: Methods are invoked inline on the IS HTTP thread (Thread A) from the message loop.
 * Each method sends its response back on the bidirectional stream via synchronized write.
 * <p>
 * This class is stateless — all session context (engine, stream, lock) is passed per-call.
 */
class ExternalCallbackHandler {

    private static final Log log = LogFactory.getLog(ExternalCallbackHandler.class);

    /**
     * Handle a host function request from the External.
     * Deserializes arguments, invokes the host function via the handler,
     * serializes the result, and sends the response back on the stream.
     * <p>
     * CRITICAL: Preserves the proxy cache ThreadLocal set-before/clear-after pattern
     * for lazy-loading complex objects (e.g., User arrays).
     *
     * @param sessionId  The session identifier.
     * @param request    The host function request from the External.
     * @param handler    The host function handler for this session.
     * @param outbound   The outbound stream observer for sending responses.
     * @param streamLock The lock object for synchronized stream writes.
     */
    void handleHostFunction(String sessionId, HostFunctionRequest request,
                            RemoteJsEngine handler,
                            StreamObserver<StreamMessage> outbound, Object streamLock) {

        String functionName = request.getFunctionName();
        long hfStart = System.currentTimeMillis();
        System.out.println("[PERF] [" + hfStart + "] IS HOST_FN_HANDLE_START session=" + sessionId +
                " fn=" + functionName + " handleStartTs=" + hfStart);
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] handleHostFunction: " + functionName + ", session: " + sessionId);
        }

        try {
            // Deserialize arguments
            List<Object> args = new ArrayList<>();
            for (SerializedValue sv : request.getArgumentsList()) {
                args.add(Serializer.fromProto(sv));
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
            if (result != null && ProxyTypeResolver.isJsWrapperProxy(result)) {
                String refId = handler.storeObjectReference(result);
                String proxyType = ProxyTypeResolver.getJsWrapperProxyType(result);
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
                // CRITICAL: Set proxy cache ThreadLocal before serialization.
                // This enables lazy-loading proxy pattern for complex objects (e.g., User arrays).
                Map<String, Object> proxyCache = handler.getProxyObjectCache();
                System.out.println("[GrpcStreaming] Setting proxy cache ThreadLocal - cache size: " +
                        (proxyCache != null ? proxyCache.size() : "NULL"));
                Serializer.setSessionProxyCache(proxyCache);
                try {
                    serializedResult = Serializer.toProto(result);
                } finally {
                    Serializer.clearSessionProxyCache();
                }
            }

            // Send response back on stream
            long hfSerEnd = System.currentTimeMillis();
            sendOnStream(outbound, streamLock, StreamMessage.newBuilder()
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
            sendOnStream(outbound, streamLock, StreamMessage.newBuilder()
                    .setSessionId(sessionId)
                    .setHostFunctionResponse(HostFunctionResponse.newBuilder()
                            .setSuccess(false)
                            .setErrorMessage(e.getMessage() != null ? e.getMessage() : e.getClass().getName())
                            .build())
                    .build());
        }
    }

    /**
     * Handle a context property read request from the External.
     * Resolves the property path via the handler, determines if the value is a proxy type,
     * and sends the appropriate response (proxy metadata with member keys, or serialized value).
     * <p>
     * Handles the special {@code __keys__} path for member key enumeration.
     *
     * @param sessionId  The session identifier.
     * @param request    The context property request from the External.
     * @param handler    The host function handler for this session.
     * @param outbound   The outbound stream observer for sending responses.
     * @param streamLock The lock object for synchronized stream writes.
     */
    void handleContextProperty(String sessionId, ContextPropertyRequest request,
                               RemoteJsEngine handler,
                               StreamObserver<StreamMessage> outbound, Object streamLock) {

        String propertyPath = request.getPropertyPath();
        long cpStart = System.currentTimeMillis();
        System.out.println("[PERF] [" + cpStart + "] IS CTX_PROP_HANDLE_START session=" + sessionId +
                " path=" + propertyPath + " handleStartTs=" + cpStart);
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] handleContextProperty: " + propertyPath + ", session: " + sessionId);
        }

        try {
            long cpExecStart = System.currentTimeMillis();
            Object value = handler.getContextProperty(propertyPath);
            long cpExecEnd = System.currentTimeMillis();

            ContextPropertyResponse.Builder responseBuilder = ContextPropertyResponse.newBuilder()
                    .setSuccess(true);

            // Handle __keys__ special path - value is the member keys array
            if (propertyPath.endsWith(RemoteEngineConstants.PATH_SEPARATOR +
                    RemoteEngineConstants.KEYS_PROPERTY) ||
                    RemoteEngineConstants.KEYS_PROPERTY.equals(propertyPath)) {
                if (value != null) {
                    MemberKeyExtractor.extractTo(value, responseBuilder);
                }
                sendOnStream(outbound, streamLock, StreamMessage.newBuilder()
                        .setSessionId(sessionId)
                        .setContextPropertyResponse(responseBuilder.build())
                        .build());
                return;
            }

            if (value != null) {
                boolean isProxy = ProxyTypeResolver.isJsWrapperProxy(value);
                responseBuilder.setIsProxy(isProxy);

                if (isProxy) {
                    responseBuilder.setProxyType(ProxyTypeResolver.getJsWrapperProxyType(value));
                    if (value instanceof ProxyObject) {
                        Object keys = ((ProxyObject) value).getMemberKeys();
                        MemberKeyExtractor.extractTo(keys, responseBuilder);
                    }
                } else {
                    responseBuilder.setValue(Serializer.toProto(value));
                }
            }

            sendOnStream(outbound, streamLock, StreamMessage.newBuilder()
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
            sendOnStream(outbound, streamLock, StreamMessage.newBuilder()
                    .setSessionId(sessionId)
                    .setContextPropertyResponse(ContextPropertyResponse.newBuilder()
                            .setSuccess(false)
                            .setErrorMessage(e.getMessage())
                            .build())
                    .build());
        }
    }

    /**
     * Handle a context property write request from the External.
     * Deserializes the value from protobuf and delegates to the handler for property setting.
     *
     * @param sessionId  The session identifier.
     * @param request    The context property set request from the External.
     * @param handler    The host function handler for this session.
     * @param outbound   The outbound stream observer for sending responses.
     * @param streamLock The lock object for synchronized stream writes.
     */
    void handleContextPropertySet(String sessionId, ContextPropertySetRequest request,
                                  RemoteJsEngine handler,
                                  StreamObserver<StreamMessage> outbound, Object streamLock) {

        String propertyPath = request.getPropertyPath();
        long cpsStart = System.currentTimeMillis();
        System.out.println("[PERF] [" + cpsStart + "] IS CTX_PROP_SET_HANDLE_START session=" + sessionId +
                " path=" + propertyPath + " handleStartTs=" + cpsStart);
        if (log.isDebugEnabled()) {
            log.debug("[GrpcStreaming] handleContextPropertySet: " + propertyPath + ", session: " + sessionId);
        }

        try {
            long cpsDeserStart = System.currentTimeMillis();
            Object javaValue = Serializer.fromProto(request.getValue());
            long cpsExecStart = System.currentTimeMillis();
            boolean success = handler.setContextProperty(propertyPath, javaValue);
            long cpsExecEnd = System.currentTimeMillis();

            sendOnStream(outbound, streamLock, StreamMessage.newBuilder()
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
            sendOnStream(outbound, streamLock, StreamMessage.newBuilder()
                    .setSessionId(sessionId)
                    .setContextPropertySetResponse(ContextPropertySetResponse.newBuilder()
                            .setSuccess(false)
                            .setErrorMessage(e.getMessage())
                            .build())
                    .build());
        }
    }

    /**
     * Thread-safe send on the bidirectional stream.
     *
     * @param outbound The outbound stream observer.
     * @param lock     The lock object for synchronized access.
     * @param message  The message to send.
     */
    private void sendOnStream(StreamObserver<StreamMessage> outbound, Object lock, StreamMessage message) {

        synchronized (lock) {
            try {
                outbound.onNext(message);
            } catch (Exception e) {
                log.error("[GrpcStreaming] Error sending on stream: " + e.getMessage(), e);
            }
        }
    }
}
