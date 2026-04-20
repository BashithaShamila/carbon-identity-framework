package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.server;

import io.grpc.ChannelCredentials;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.TlsChannelCredentials;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphEngineModeRouter;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote.RemoteEngineConstants;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Cleaner version of GrpcConnectionManager using immutable pool holder.
 */
public class GrpcConnectionManager {

    private static final Log log = LogFactory.getLog(GrpcConnectionManager.class);

    // Singleton
    private static volatile GrpcConnectionManager instance;
    private static final Object lock = new Object();

    // Immutable holder for pool state
    private static final class ChannelPool {
        final ManagedChannel[] channels;
        final String target;

        ChannelPool(ManagedChannel[] channels, String target) {
            this.channels = channels;
            this.target = target;
        }
    }

    private volatile ChannelPool pool;
    private final AtomicInteger channelIndex = new AtomicInteger(0);

    private int channelPoolSize = 4;
    private int channelIdleTimeout = 180;

    private GrpcConnectionManager() {
        loadConfiguration();
    }

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
     * lock-free channel selection
     */
    public ManagedChannel getClientChannel(String target) {
        ChannelPool current = pool;

        if (current != null && current.target.equals(target)) {
            int index = (channelIndex.getAndIncrement() & Integer.MAX_VALUE)
                    % current.channels.length;
            return current.channels[index];
        }

        return createOrReplacePool(target);
    }

    /**
     * synchronized pool creation/replacement
     */
    private synchronized ManagedChannel createOrReplacePool(String target) {
        ChannelPool current = pool;

        // Double check
        if (current != null && current.target.equals(target)) {
            return pick(current);
        }

        if (current != null) {
            log.info("[GrpcConnectionManager] Target changed, shutting down old pool");
            shutdown(current);
        }

        log.info("[GrpcConnectionManager] Creating channel pool of size " +
                channelPoolSize + " for target: " + target);

        ManagedChannel[] channels = new ManagedChannel[channelPoolSize];

        try {
            for (int i = 0; i < channels.length; i++) {
                channels[i] = createChannel(target);
            }
        } catch (RuntimeException e) {
            for (ManagedChannel ch : channels) {
                if (ch != null) {
                    ch.shutdownNow();
                }
            }
            throw e;
        }

        ChannelPool newPool = new ChannelPool(channels, target);
        pool = newPool;
        channelIndex.set(0);

        return newPool.channels[0];
    }

    private ManagedChannel pick(ChannelPool pool) {
        int index = (channelIndex.getAndIncrement() & Integer.MAX_VALUE)
                % pool.channels.length;
        return pool.channels[index];
    }

    /**
     * health check
     */
    public boolean isClientChannelConnected() {
        ChannelPool current = pool;
        if (current == null) {
            return false;
        }

        for (ManagedChannel ch : current.channels) {
            if (ch != null && !ch.isShutdown() && !ch.isTerminated()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Shutdown all channels
     */
    public synchronized void shutdown() {
        if (pool != null) {
            log.info("[GrpcConnectionManager] Shutting down all channels");
            shutdown(pool);
            pool = null;
            channelIndex.set(0);
        }
    }

    private void shutdown(ChannelPool pool) {
        for (int i = 0; i < pool.channels.length; i++) {
            ManagedChannel ch = pool.channels[i];
            if (ch != null) {
                try {
                    ch.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    ch.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Configuration loading
     */
    private void loadConfiguration() {

        String idleTimeoutStr = System.getProperty("graaljs.grpc.channel.idle.timeout");
        if (idleTimeoutStr != null) {
            try {
                channelIdleTimeout = Integer.parseInt(idleTimeoutStr);
            } catch (NumberFormatException e) {
                log.warn("Invalid idle timeout: " + idleTimeoutStr);
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
                log.warn("Invalid pool size: " + poolSizeStr);
            }
        }

        log.info("Config loaded - PoolSize: " + channelPoolSize +
                ", IdleTimeout: " + channelIdleTimeout);
    }

    /**
     * Channel creation (mTLS / plaintext)
     */
    private ManagedChannel createChannel(String target) {
        if (RemoteEngineConstants.MTLS_ENABLED) {
            try {
                String carbonHome = System.getProperty("carbon.home");
                if (carbonHome == null) {
                    throw new IllegalStateException("carbon.home not set");
                }

                File certDir = new File(carbonHome, RemoteEngineConstants.MTLS_CERT_DIR);
                File clientCert = new File(certDir, RemoteEngineConstants.MTLS_CLIENT_CERT);
                File clientKey = new File(certDir, RemoteEngineConstants.MTLS_CLIENT_KEY);
                File caCert = new File(certDir, RemoteEngineConstants.MTLS_CA_CERT);

                if (JsGraalGraphEngineModeRouter.isTracingEnabled()) {
                    System.out.println("[GrpcConnectionManager] Loading mTLS certs from: "
                            + certDir.getAbsolutePath());
                }

                ChannelCredentials credentials = TlsChannelCredentials.newBuilder()
                        .keyManager(clientCert, clientKey)
                        .trustManager(caCert)
                        .build();

                return Grpc.newChannelBuilder(target, credentials)
                        .idleTimeout(channelIdleTimeout, TimeUnit.SECONDS)
                        .build();

            } catch (IOException e) {
                throw new RuntimeException("Failed to init mTLS channel", e);
            }
        } else {
            return ManagedChannelBuilder.forTarget(target)
                    .usePlaintext()
                    .idleTimeout(channelIdleTimeout, TimeUnit.SECONDS)
                    .build();
        }
    }

    // Optional setters

    public void setChannelPoolSize(int size) {
        if (size > 0) {
            this.channelPoolSize = size;
        }
    }

    public void setChannelIdleTimeout(int seconds) {
        this.channelIdleTimeout = seconds;
    }

    public int getChannelPoolSize() {
        return channelPoolSize;
    }
}