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

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.impl.UdsCallbackServerImpl;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.impl.UdsTransportImpl;

// GRPC imports removed - implementation moved to com.wso2.identity.asgardeo.scope.service jar
// import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.impl.GrpcCallbackServerImpl;
// import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.impl.GrpcTransportImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for creating transport and callback server implementations.
 * Uses Strategy pattern to support pluggable transports (UDS, gRPC, HTTP, WebSocket, etc.).
 * <p>
 * This factory decouples the engine layer from specific transport implementations,
 * allowing new transports to be added without modifying existing code.
 * <p>
 * Usage:
 * <pre>
 * TransportFactory factory = TransportFactory.getInstance();
 * TransportConfig config = TransportConfig.forUds("/tmp/socket.sock");
 * RemoteEngineTransport transport = factory.createTransport(config);
 * CallbackServer callbackServer = factory.createCallbackServer(config);
 * </pre>
 * <p>
 * To add a new transport:
 * 1. Implement RemoteEngineTransport and CallbackServer interfaces
 * 2. Create a TransportProvider implementation
 * 3. Register it: factory.registerProvider("NEWTYPE", new NewTransportProvider())
 */
public class TransportFactory {

    private static final Log log = LogFactory.getLog(TransportFactory.class);

    // Singleton instance
    private static final TransportFactory INSTANCE = new TransportFactory();

    // Registry of transport providers
    private final Map<String, TransportProvider> providers = new HashMap<>();

    /**
     * Private constructor - use getInstance().
     */
    private TransportFactory() {
        // Register built-in transport providers
        registerProvider("UDS", new UdsTransportProvider());

        // GRPC provider moved to com.wso2.identity.asgardeo.scope.service jar
        // It will be registered via OSGi service activation
        // registerProvider("GRPC", new GrpcTransportProvider());

        // Future transports can be registered here:
        // registerProvider("HTTP", new HttpTransportProvider());
        // registerProvider("WEBSOCKET", new WebSocketTransportProvider());

        log.info("[TransportFactory] Initialized with providers: " + providers.keySet());
    }

    /**
     * Get the singleton instance.
     *
     * @return TransportFactory instance.
     */
    public static TransportFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Create a transport implementation based on configuration.
     *
     * @param config Transport configuration.
     * @return RemoteEngineTransport implementation.
     * @throws IllegalArgumentException if transport type is not supported.
     */
    public RemoteEngineTransport createTransport(TransportConfig config) {
        String type = config.getType().toUpperCase();
        TransportProvider provider = providers.get(type);

        if (provider == null) {
            throw new IllegalArgumentException("Unsupported transport type: " + config.getType() +
                    ". Supported types: " + providers.keySet());
        }

        log.debug("[TransportFactory] Creating transport: " + type);
        return provider.createTransport(config);
    }

    /**
     * Create a callback server implementation based on configuration.
     *
     * @param config Transport configuration.
     * @return CallbackServer implementation.
     * @throws IllegalArgumentException if transport type is not supported.
     */
    public CallbackServer createCallbackServer(TransportConfig config) {
        String type = config.getType().toUpperCase();
        TransportProvider provider = providers.get(type);

        if (provider == null) {
            throw new IllegalArgumentException("Unsupported transport type: " + config.getType() +
                    ". Supported types: " + providers.keySet());
        }

        log.debug("[TransportFactory] Creating callback server: " + type);
        return provider.createCallbackServer(config);
    }

    /**
     * Register a custom transport provider.
     * This allows third-party extensions to add new transport types.
     *
     * @param type Transport type identifier (e.g., "HTTP", "WEBSOCKET").
     * @param provider TransportProvider implementation.
     */
    public void registerProvider(String type, TransportProvider provider) {
        providers.put(type.toUpperCase(), provider);
        log.info("[TransportFactory] Registered transport provider: " + type);
    }

    /**
     * Service Provider Interface for transport implementations.
     * Implement this interface to add a new transport type.
     */
    public interface TransportProvider {
        /**
         * Create a transport for communicating with remote JavaScript engine.
         *
         * @param config Transport configuration.
         * @return RemoteEngineTransport implementation.
         */
        RemoteEngineTransport createTransport(TransportConfig config);

        /**
         * Create a callback server for receiving callbacks from remote engine.
         *
         * @param config Transport configuration.
         * @return CallbackServer implementation.
         */
        CallbackServer createCallbackServer(TransportConfig config);
    }

    /**
     * Built-in provider for UDS (Unix Domain Socket) transport.
     */
    private static class UdsTransportProvider implements TransportProvider {

        @Override
        public RemoteEngineTransport createTransport(TransportConfig config) {
            String socketPath = config.getSocketPath();
            if (socketPath == null || socketPath.isEmpty()) {
                throw new IllegalArgumentException("Socket path is required for UDS transport");
            }
            return new UdsTransportImpl(socketPath);
        }

        @Override
        public CallbackServer createCallbackServer(TransportConfig config) {
            // UdsCallbackServerImpl wraps the singleton HostCallbackServer
            return new UdsCallbackServerImpl();
        }
    }

    /**
     * Built-in provider for gRPC transport.
     * MOVED to com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider
     * This provider is now registered via OSGi service activation.
     */
    /*
    private static class GrpcTransportProvider implements TransportProvider {

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
            int callbackPort = config.getCallbackPort();
            return new GrpcCallbackServerImpl(callbackPort);
        }
    }
    */

    /**
     * Example: HTTP transport provider (not implemented yet).
     * Uncomment when HttpTransportImpl and HttpCallbackServerImpl are available.
     */
    /*
    private static class HttpTransportProvider implements TransportProvider {

        @Override
        public RemoteEngineTransport createTransport(TransportConfig config) {
            String httpUrl = config.getHttpUrl();
            if (httpUrl == null || httpUrl.isEmpty()) {
                throw new IllegalArgumentException("HTTP URL is required for HTTP transport");
            }
            return new HttpTransportImpl(httpUrl);
        }

        @Override
        public CallbackServer createCallbackServer(TransportConfig config) {
            int callbackPort = config.getCallbackPort();
            return new HttpCallbackServerImpl(callbackPort);
        }
    }
    */
}
