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

/**
 * Configuration for remote JavaScript engine transport.
 * Immutable value object that holds transport-specific configuration.
 * <p>
 * Supports multiple transport types:
 * - UDS (Unix Domain Sockets)
 * - GRPC (gRPC over TCP/IP)
 * - HTTP (REST API)
 * - WEBSOCKET (WebSocket connections)
 * <p>
 * Use the Builder or static factory methods to create instances.
 */
public class TransportConfig {

    private final String type;          // Transport type: "UDS", "GRPC", "HTTP", "WEBSOCKET"
    private final String socketPath;    // For UDS: socket file path
    private final String grpcTarget;    // For gRPC: host:port
    private final int callbackPort;     // For gRPC/HTTP: callback server port
    private final String httpUrl;       // For HTTP: base URL
    private final String wsUrl;         // For WebSocket: WebSocket URL

    private TransportConfig(Builder builder) {
        this.type = builder.type;
        this.socketPath = builder.socketPath;
        this.grpcTarget = builder.grpcTarget;
        this.callbackPort = builder.callbackPort;
        this.httpUrl = builder.httpUrl;
        this.wsUrl = builder.wsUrl;
    }

    /**
     * Create configuration for UDS transport.
     *
     * @param socketPath Path to Unix Domain Socket file.
     * @return TransportConfig instance.
     */
    public static TransportConfig forUds(String socketPath) {
        return new Builder("UDS")
                .socketPath(socketPath)
                .build();
    }

    /**
     * Create configuration for gRPC transport.
     *
     * @param grpcTarget gRPC target address (host:port).
     * @param callbackPort Port for callback server.
     * @return TransportConfig instance.
     */
    public static TransportConfig forGrpc(String grpcTarget, int callbackPort) {
        return new Builder("GRPC")
                .grpcTarget(grpcTarget)
                .callbackPort(callbackPort)
                .build();
    }

    /**
     * Create configuration for HTTP transport.
     *
     * @param httpUrl HTTP endpoint URL.
     * @param callbackPort Port for callback server.
     * @return TransportConfig instance.
     */
    public static TransportConfig forHttp(String httpUrl, int callbackPort) {
        return new Builder("HTTP")
                .httpUrl(httpUrl)
                .callbackPort(callbackPort)
                .build();
    }

    /**
     * Create configuration for WebSocket transport.
     *
     * @param wsUrl WebSocket URL.
     * @param callbackPort Port for callback server.
     * @return TransportConfig instance.
     */
    public static TransportConfig forWebSocket(String wsUrl, int callbackPort) {
        return new Builder("WEBSOCKET")
                .wsUrl(wsUrl)
                .callbackPort(callbackPort)
                .build();
    }

    // Getters

    public String getType() {
        return type;
    }

    public String getSocketPath() {
        return socketPath;
    }

    public String getGrpcTarget() {
        return grpcTarget;
    }

    public int getCallbackPort() {
        return callbackPort;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public String getWsUrl() {
        return wsUrl;
    }

    /**
     * Builder for TransportConfig.
     */
    public static class Builder {
        private final String type;
        private String socketPath;
        private String grpcTarget;
        private int callbackPort = 0;
        private String httpUrl;
        private String wsUrl;

        /**
         * Create a builder for the specified transport type.
         *
         * @param type Transport type (UDS, GRPC, HTTP, WEBSOCKET).
         */
        public Builder(String type) {
            this.type = type;
        }

        public Builder socketPath(String socketPath) {
            this.socketPath = socketPath;
            return this;
        }

        public Builder grpcTarget(String grpcTarget) {
            this.grpcTarget = grpcTarget;
            return this;
        }

        public Builder callbackPort(int callbackPort) {
            this.callbackPort = callbackPort;
            return this;
        }

        public Builder httpUrl(String httpUrl) {
            this.httpUrl = httpUrl;
            return this;
        }

        public Builder wsUrl(String wsUrl) {
            this.wsUrl = wsUrl;
            return this;
        }

        public TransportConfig build() {
            return new TransportConfig(this);
        }
    }

    @Override
    public String toString() {
        return "TransportConfig{" +
                "type='" + type + '\'' +
                ", socketPath='" + socketPath + '\'' +
                ", grpcTarget='" + grpcTarget + '\'' +
                ", callbackPort=" + callbackPort +
                ", httpUrl='" + httpUrl + '\'' +
                ", wsUrl='" + wsUrl + '\'' +
                '}';
    }
}
