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

    private static volatile GrpcCallbackServerImpl callbackServerInstance;
    private static final Object lock = new Object();

    @Override
    public RemoteEngineTransport createTransport(TransportConfig config) {
        String grpcTarget = config.getGrpcTarget();
        if (grpcTarget == null || grpcTarget.isEmpty()) {
            throw new IllegalArgumentException("gRPC target is required for GRPC transport");
        }
        return new GrpcTransportImpl(grpcTarget);
    }

    @Override
    public CallbackServer createCallbackServer(TransportConfig config) {
        // Return singleton instance to ensure all sessions share the same callback server
        if (callbackServerInstance == null) {
            synchronized (lock) {
                if (callbackServerInstance == null) {
                    int callbackPort = config.getCallbackPort();
                    callbackServerInstance = new GrpcCallbackServerImpl(callbackPort);
                }
            }
        }
        return callbackServerInstance;
    }
}
