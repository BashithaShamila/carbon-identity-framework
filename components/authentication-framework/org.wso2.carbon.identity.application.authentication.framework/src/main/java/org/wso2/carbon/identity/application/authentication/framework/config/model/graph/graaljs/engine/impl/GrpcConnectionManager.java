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

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.impl;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Singleton manager for gRPC client channels and callback server.
 * <p>
 * Manages the lifecycle of:
 * - ManagedChannel for client connections to remote JS engine
 * - gRPC Server for receiving host function callbacks
 * <p>
 * Uses double-checked locking pattern for thread-safe lazy initialization.
 */
public class GrpcConnectionManager {

    private static final Log log = LogFactory.getLog(GrpcConnectionManager.class);

    // Singleton instance
    private static volatile GrpcConnectionManager instance;
    private static final Object lock = new Object();

    // Client channel for requests to remote JS engine
    private ManagedChannel clientChannel;
    private String grpcTarget;
    private int channelIdleTimeout = 180; // seconds

    // Server for receiving callbacks
    private Server callbackServer;
    private int callbackPort = 50052; // default callback port
    private volatile boolean callbackServerStarted = false;

    /**
     * Private constructor - use getInstance()
     */
    private GrpcConnectionManager() {
        // Configuration will be loaded from deployment.toml or system properties
        loadConfiguration();
    }

    /**
     * Get singleton instance with double-checked locking.
     *
     * @return GrpcConnectionManager instance
     */
    public static GrpcConnectionManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new GrpcConnectionManager();
                }
            }
        }
        return instance;
    }

    /**
     * Get or create the client channel for communicating with remote JS engine.
     *
     * @param target gRPC target (e.g., "localhost:50051")
     * @return ManagedChannel instance
     */
    public synchronized ManagedChannel getClientChannel(String target) {
        if (this.grpcTarget == null || !this.grpcTarget.equals(target)) {
            // Target changed or first initialization
            if (clientChannel != null) {
                log.info("[GrpcConnectionManager] Target changed, shutting down old channel");
                shutdownClientChannel();
            }
            this.grpcTarget = target;
        }

        if (clientChannel == null || clientChannel.isShutdown() || clientChannel.isTerminated()) {
            log.info("[GrpcConnectionManager] Creating new gRPC client channel to: " + target);
            clientChannel = ManagedChannelBuilder.forTarget(target)
                    .usePlaintext() // For development - use TLS in production
                    .idleTimeout(channelIdleTimeout, TimeUnit.SECONDS)
                    .build();
            log.info("[GrpcConnectionManager] gRPC client channel created successfully");
        }

        return clientChannel;
    }

    /**
     * Check if client channel is connected and ready.
     *
     * @return true if channel exists and not shutdown/terminated
     */
    public boolean isClientChannelConnected() {
        return clientChannel != null && !clientChannel.isShutdown() && !clientChannel.isTerminated();
    }

    /**
     * Get the callback server instance.
     * Creates and starts the server if not already started.
     *
     * @param callbackServiceImpl The callback service implementation to register
     * @param port Port to bind the server (0 for automatic port selection)
     * @return Server instance
     * @throws IOException if server startup fails
     */
    public synchronized Server getCallbackServer(io.grpc.BindableService callbackServiceImpl, int port)
            throws IOException {
        if (callbackServer == null || callbackServer.isShutdown() || callbackServer.isTerminated()) {
            this.callbackPort = port;
            log.info("[GrpcConnectionManager] Creating gRPC callback server on port: " + port);

            callbackServer = ServerBuilder.forPort(port)
                    .addService(callbackServiceImpl)
                    .build();

            callbackServer.start();
            callbackServerStarted = true;

            // Get actual port if 0 was specified
            int actualPort = callbackServer.getPort();
            this.callbackPort = actualPort;

            log.info("[GrpcConnectionManager] gRPC callback server started on port: " + actualPort);

            // Shutdown hook for graceful cleanup
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                log.info("[GrpcConnectionManager] Shutting down gRPC callback server via shutdown hook");
                shutdownCallbackServer();
            }));
        }

        return callbackServer;
    }

    /**
     * Check if callback server is started and running.
     *
     * @return true if server is started and not shutdown
     */
    public boolean isCallbackServerStarted() {
        return callbackServerStarted && callbackServer != null &&
               !callbackServer.isShutdown() && !callbackServer.isTerminated();
    }

    /**
     * Get the callback server port.
     *
     * @return Port number
     */
    public int getCallbackPort() {
        return callbackPort;
    }

    /**
     * Get the callback server address for clients to connect to.
     *
     * @return Address in format "host:port"
     */
    public String getCallbackAddress() {
        // In production, this should return the actual hostname/IP
        // For now, using localhost
        return "localhost:" + callbackPort;
    }

    /**
     * Shutdown the client channel gracefully.
     */
    public synchronized void shutdownClientChannel() {
        if (clientChannel != null) {
            log.info("[GrpcConnectionManager] Shutting down gRPC client channel");
            try {
                clientChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.warn("[GrpcConnectionManager] Interrupted while shutting down client channel", e);
                clientChannel.shutdownNow();
                Thread.currentThread().interrupt();
            }
            clientChannel = null;
        }
    }

    /**
     * Shutdown the callback server gracefully.
     */
    public synchronized void shutdownCallbackServer() {
        if (callbackServer != null) {
            log.info("[GrpcConnectionManager] Shutting down gRPC callback server");
            try {
                callbackServer.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.warn("[GrpcConnectionManager] Interrupted while shutting down callback server", e);
                callbackServer.shutdownNow();
                Thread.currentThread().interrupt();
            }
            callbackServer = null;
            callbackServerStarted = false;
        }
    }

    /**
     * Shutdown all gRPC resources.
     */
    public synchronized void shutdown() {
        log.info("[GrpcConnectionManager] Shutting down all gRPC resources");
        shutdownClientChannel();
        shutdownCallbackServer();
    }

    /**
     * Load configuration from deployment.toml or system properties.
     */
    private void loadConfiguration() {
        // Future: Load from IdentityUtil.getProperty() or similar
        // For now, using defaults

        // Check system properties for override
        String idleTimeoutStr = System.getProperty("graaljs.grpc.channel.idle.timeout");
        if (idleTimeoutStr != null) {
            try {
                channelIdleTimeout = Integer.parseInt(idleTimeoutStr);
            } catch (NumberFormatException e) {
                log.warn("[GrpcConnectionManager] Invalid idle timeout value: " + idleTimeoutStr);
            }
        }

        String callbackPortStr = System.getProperty("graaljs.grpc.callback.port");
        if (callbackPortStr != null) {
            try {
                callbackPort = Integer.parseInt(callbackPortStr);
            } catch (NumberFormatException e) {
                log.warn("[GrpcConnectionManager] Invalid callback port value: " + callbackPortStr);
            }
        }

        log.info("[GrpcConnectionManager] Configuration loaded - IdleTimeout: " + channelIdleTimeout +
                "s, CallbackPort: " + callbackPort);
    }

    /**
     * Set channel idle timeout (for testing/configuration).
     *
     * @param seconds Timeout in seconds
     */
    public void setChannelIdleTimeout(int seconds) {
        this.channelIdleTimeout = seconds;
    }

    /**
     * Set callback port (for testing/configuration).
     *
     * @param port Port number
     */
    public void setCallbackPort(int port) {
        this.callbackPort = port;
    }
}
