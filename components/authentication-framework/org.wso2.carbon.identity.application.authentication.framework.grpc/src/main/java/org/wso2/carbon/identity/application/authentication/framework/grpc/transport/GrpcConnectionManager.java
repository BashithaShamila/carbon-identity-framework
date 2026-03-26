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

package org.wso2.carbon.identity.application.authentication.framework.grpc.transport;

import io.grpc.ChannelCredentials;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.TlsChannelCredentials;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteEngineConstants;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

    // Client channel pool for requests to remote JS engine
    private ManagedChannel[] channelPool;
    private final AtomicInteger channelIndex = new AtomicInteger(0);
    private int channelPoolSize = 4; // default pool size, configurable via graaljs.grpc.channel.pool.size
    private String grpcTarget;
    private int channelIdleTimeout = 180; // seconds

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
            if (channelPool != null) {
                log.info("[GrpcConnectionManager] Target changed, shutting down old channel pool");
                shutdownClientChannel();
            }
            this.grpcTarget = target;
        }

        if (channelPool == null) {
            log.info("[GrpcConnectionManager] Creating gRPC client channel pool of size " +
                    channelPoolSize + " to: " + target +
                    ", mTLS: " + RemoteEngineConstants.MTLS_ENABLED);
            channelPool = new ManagedChannel[channelPoolSize];
            for (int i = 0; i < channelPoolSize; i++) {
                channelPool[i] = createChannel(target);
            }
            log.info("[GrpcConnectionManager] gRPC client channel pool created successfully (" +
                    channelPoolSize + " channels)");
        }

        // Round-robin channel selection (mask sign bit to handle integer overflow)
        int index = (channelIndex.getAndIncrement() & 0x7FFFFFFF) % channelPoolSize;
        return channelPool[index];
    }

    /**
     * Check if client channel is connected and ready.
     *
     * @return true if channel exists and not shutdown/terminated
     */
    public boolean isClientChannelConnected() {
        if (channelPool == null) {
            return false;
        }
        for (ManagedChannel ch : channelPool) {
            if (ch != null && !ch.isShutdown() && !ch.isTerminated()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Shutdown the client channel gracefully.
     */
    public synchronized void shutdownClientChannel() {
        if (channelPool != null) {
            log.info("[GrpcConnectionManager] Shutting down gRPC client channel pool (" +
                    channelPool.length + " channels)");
            for (int i = 0; i < channelPool.length; i++) {
                if (channelPool[i] != null) {
                    try {
                        channelPool[i].shutdown().awaitTermination(5, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        log.warn("[GrpcConnectionManager] Interrupted while shutting down channel " +
                                i, e);
                        channelPool[i].shutdownNow();
                        Thread.currentThread().interrupt();
                    }
                }
            }
            channelPool = null;
            channelIndex.set(0);
        }
    }

    /**
     * Shutdown all gRPC resources.
     */
    public synchronized void shutdown() {
        log.info("[GrpcConnectionManager] Shutting down all gRPC resources");
        shutdownClientChannel();
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

        String poolSizeStr = System.getProperty("graaljs.grpc.channel.pool.size");
        if (poolSizeStr != null) {
            try {
                int size = Integer.parseInt(poolSizeStr);
                if (size > 0) {
                    channelPoolSize = size;
                }
            } catch (NumberFormatException e) {
                log.warn("[GrpcConnectionManager] Invalid channel pool size value: " + poolSizeStr);
            }
        }

        log.info("[GrpcConnectionManager] Configuration loaded - ChannelPoolSize: " + channelPoolSize +
                ", IdleTimeout: " + channelIdleTimeout + "s");
    }

    /**
     * Create a single ManagedChannel to the given target.
     * Uses mTLS when {@link RemoteEngineConstants#MTLS_ENABLED} is true,
     * otherwise falls back to plaintext.
     *
     * @param target gRPC target (e.g., "localhost:50051")
     * @return ManagedChannel instance
     */
    private ManagedChannel createChannel(String target) {
        if (RemoteEngineConstants.MTLS_ENABLED) {
            try {
                String carbonHome = System.getProperty("carbon.home");
                if (carbonHome == null) {
                    throw new IllegalStateException("carbon.home system property is not set. " +
                            "Cannot locate mTLS certificates.");
                }
                File certDir = new File(carbonHome, RemoteEngineConstants.MTLS_CERT_DIR);
                File clientCert = new File(certDir, RemoteEngineConstants.MTLS_CLIENT_CERT);
                File clientKey = new File(certDir, RemoteEngineConstants.MTLS_CLIENT_KEY);
                File caCert = new File(certDir, RemoteEngineConstants.MTLS_CA_CERT);

                System.out.println("[GrpcConnectionManager] mTLS enabled - loading certs from: " +
                        certDir.getAbsolutePath());
                System.out.println("[GrpcConnectionManager]   client cert: " + clientCert.getAbsolutePath() +
                        " (exists=" + clientCert.exists() + ")");
                System.out.println("[GrpcConnectionManager]   client key:  " + clientKey.getAbsolutePath() +
                        " (exists=" + clientKey.exists() + ")");
                System.out.println("[GrpcConnectionManager]   CA cert:     " + caCert.getAbsolutePath() +
                        " (exists=" + caCert.exists() + ")");

                ChannelCredentials credentials = TlsChannelCredentials.newBuilder()
                        .keyManager(clientCert, clientKey)
                        .trustManager(caCert)
                        .build();

                return Grpc.newChannelBuilder(target, credentials)
                        .idleTimeout(channelIdleTimeout, TimeUnit.SECONDS)
                        .build();

            } catch (IOException e) {
                throw new RuntimeException("[GrpcConnectionManager] Failed to initialize mTLS channel. " +
                        "Ensure cert files exist in " + RemoteEngineConstants.MTLS_CERT_DIR, e);
            }
        } else {
            return ManagedChannelBuilder.forTarget(target)
                    .usePlaintext()
                    .idleTimeout(channelIdleTimeout, TimeUnit.SECONDS)
                    .build();
        }
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
     * Set channel pool size (for testing/configuration).
     * Must be called before getClientChannel() to take effect.
     *
     * @param size Pool size (must be greater than 0)
     */
    public void setChannelPoolSize(int size) {
        if (size > 0) {
            this.channelPoolSize = size;
        }
    }

    /**
     * Get the current channel pool size.
     *
     * @return Pool size
     */
    public int getChannelPoolSize() {
        return channelPoolSize;
    }
}
