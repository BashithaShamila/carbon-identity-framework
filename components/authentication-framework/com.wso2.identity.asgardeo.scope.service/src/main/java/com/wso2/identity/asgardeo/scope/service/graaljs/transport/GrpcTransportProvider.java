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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.CallbackServer;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteEngineTransport;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.TransportConfig;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.TransportFactory;

/**
 * Provider for gRPC transport implementation.
 * <p>
 * This provider is registered with the TransportFactory via OSGi service activation,
 * making gRPC transport available to the authentication framework without requiring
 * compile-time dependencies on gRPC libraries in the framework jar.
 * <p>
 * Uses singleton pattern for callback server to ensure all RemoteJsEngine instances
 * share the same callback server instance and session handlers are properly registered.
 */
public class GrpcTransportProvider implements TransportFactory.TransportProvider {

    private static final Log log = LogFactory.getLog(GrpcTransportProvider.class);

    private static volatile GrpcStreamingTransportImpl streamingInstance;
    private static final Object lock = new Object();
    private static int transportInstanceCount = 0;

    @Override
    public RemoteEngineTransport createTransport(TransportConfig config) {
        transportInstanceCount++;
        if (log.isDebugEnabled()) {
            log.debug("[GrpcTransportProvider] ========== createTransport() CALLED ==========");
        }
        if (log.isDebugEnabled()) {
            log.debug("[GrpcTransportProvider] Transport instance #" + transportInstanceCount);
        }
        if (log.isDebugEnabled()) {
            log.debug("[GrpcTransportProvider] Config details - grpcTarget: " + config.getGrpcTarget());
        }

        String grpcTarget = config.getGrpcTarget();
        if (grpcTarget == null || grpcTarget.isEmpty()) {
            log.error("[GrpcTransportProvider] ERROR: gRPC target is null or empty!");
            throw new IllegalArgumentException("gRPC target is required for GRPC transport");
        }

        // Use singleton streaming transport (implements both transport and callback server)
        GrpcStreamingTransportImpl instance = getOrCreateStreamingInstance(grpcTarget);
        if (log.isDebugEnabled()) {
            log.debug("[GrpcTransportProvider] Returning streaming transport, hashCode=" +
                    System.identityHashCode(instance));
        }
        if (log.isDebugEnabled()) {
            log.debug("[GrpcTransportProvider] ========== createTransport() COMPLETED ==========");
        }
        return instance;
    }

    @Override
    public CallbackServer createCallbackServer(TransportConfig config) {
        if (log.isDebugEnabled()) {
            log.debug("[GrpcTransportProvider] ========== createCallbackServer() CALLED ==========");
        }
        if (log.isDebugEnabled()) {
            log.debug("[GrpcTransportProvider] Config details - grpcTarget: " + config.getGrpcTarget());
        }

        String grpcTarget = config.getGrpcTarget();
        if (grpcTarget == null || grpcTarget.isEmpty()) {
            log.error("[GrpcTransportProvider] ERROR: gRPC target is null or empty!");
            throw new IllegalArgumentException("gRPC target is required for GRPC transport");
        }

        // Return same streaming transport instance (it implements CallbackServer too)
        GrpcStreamingTransportImpl instance = getOrCreateStreamingInstance(grpcTarget);
        if (log.isDebugEnabled()) {
            log.debug("[GrpcTransportProvider] Returning streaming transport as callback server, hashCode=" +
                    System.identityHashCode(instance));
        }
        if (log.isDebugEnabled()) {
            log.debug("[GrpcTransportProvider] ========== createCallbackServer() COMPLETED ==========");
        }
        return instance;
    }

    private static GrpcStreamingTransportImpl getOrCreateStreamingInstance(String grpcTarget) {
        if (streamingInstance == null) {
            synchronized (lock) {
                if (streamingInstance == null) {
                    if (log.isDebugEnabled()) {
                        log.debug("[GrpcTransportProvider] Creating NEW GrpcStreamingTransportImpl for target: " +
                                grpcTarget);
                    }
                    streamingInstance = new GrpcStreamingTransportImpl(grpcTarget);
                    if (log.isDebugEnabled()) {
                        log.debug("[GrpcTransportProvider] NEW singleton created, hashCode=" +
                                System.identityHashCode(streamingInstance));
                    }
                }
            }
        }
        return streamingInstance;
    }
}
