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

    private static volatile GrpcCallbackServerImpl callbackServerInstance;
    private static final Object lock = new Object();
    private static int transportInstanceCount = 0;

    @Override
    public RemoteEngineTransport createTransport(TransportConfig config) {
        transportInstanceCount++;
        log.info("[GrpcTransportProvider] ========== createTransport() CALLED ==========");
        log.info("[GrpcTransportProvider] Transport instance #" + transportInstanceCount);
        log.info("[GrpcTransportProvider] Config details - grpcTarget: " + config.getGrpcTarget() +
                ", callbackPort: " + config.getCallbackPort());
        log.info("[GrpcTransportProvider] Thread: " + Thread.currentThread().getName() +
                ", ID: " + Thread.currentThread().getId());

        String grpcTarget = config.getGrpcTarget();
        if (grpcTarget == null || grpcTarget.isEmpty()) {
            log.error("[GrpcTransportProvider] ERROR: gRPC target is null or empty!");
            throw new IllegalArgumentException("gRPC target is required for GRPC transport");
        }

        GrpcTransportImpl transport = new GrpcTransportImpl(grpcTarget);
        log.info("[GrpcTransportProvider] Created GrpcTransportImpl for target: " + grpcTarget);
        log.info("[GrpcTransportProvider] ========== createTransport() COMPLETED ==========");
        return transport;
    }

    @Override
    public CallbackServer createCallbackServer(TransportConfig config) {
        log.info("[GrpcTransportProvider] ========== createCallbackServer() CALLED ==========");
        log.info("[GrpcTransportProvider] Config details - callbackPort: " + config.getCallbackPort() +
                ", grpcTarget: " + config.getGrpcTarget());
        log.info("[GrpcTransportProvider] Thread: " + Thread.currentThread().getName() +
                ", ID: " + Thread.currentThread().getId());
        log.info("[GrpcTransportProvider] Current singleton instance: " +
                (callbackServerInstance == null ? "NULL" : "EXISTS (hashCode=" +
                        System.identityHashCode(callbackServerInstance) + ")"));

        // Return singleton instance to ensure all sessions share the same callback server
        if (callbackServerInstance == null) {
            log.info("[GrpcTransportProvider] Singleton is NULL, acquiring lock...");
            synchronized (lock) {
                log.info("[GrpcTransportProvider] Lock acquired, double-checking...");
                if (callbackServerInstance == null) {
                    int callbackPort = config.getCallbackPort();
                    log.info("[GrpcTransportProvider] Creating NEW GrpcCallbackServerImpl on port: " + callbackPort);
                    callbackServerInstance = new GrpcCallbackServerImpl(callbackPort);
                    log.info("[GrpcTransportProvider] NEW singleton created, hashCode=" +
                            System.identityHashCode(callbackServerInstance));
                } else {
                    log.info("[GrpcTransportProvider] Another thread already created singleton");
                }
            }
        } else {
            log.info("[GrpcTransportProvider] Returning existing singleton instance");
        }

        log.info("[GrpcTransportProvider] Returning callbackServer hashCode=" +
                System.identityHashCode(callbackServerInstance));
        log.info("[GrpcTransportProvider] ========== createCallbackServer() COMPLETED ==========");
        return callbackServerInstance;
    }
}
