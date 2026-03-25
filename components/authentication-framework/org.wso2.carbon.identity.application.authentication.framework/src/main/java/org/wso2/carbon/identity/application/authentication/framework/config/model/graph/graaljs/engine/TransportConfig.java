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
 * Currently supports gRPC transport. New transport types can be added
 * by extending the Builder and registering a TransportProvider via OSGi.
 */
public class TransportConfig {

    private final String type;          // Transport type: "GRPC"
    private final String grpcTarget;    // For gRPC: host:port

    private TransportConfig(Builder builder) {
        this.type = builder.type;
        this.grpcTarget = builder.grpcTarget;
    }

    /**
     * Create configuration for gRPC transport.
     *
     * @param grpcTarget gRPC target address (host:port).
     * @return TransportConfig instance.
     */
    public static TransportConfig forGrpc(String grpcTarget) {
        return new Builder("GRPC")
                .grpcTarget(grpcTarget)
                .build();
    }

    // Getters

    public String getType() {
        return type;
    }

    public String getGrpcTarget() {
        return grpcTarget;
    }

    /**
     * Builder for TransportConfig.
     */
    public static class Builder {
        private final String type;
        private String grpcTarget;

        /**
         * Create a builder for the specified transport type.
         *
         * @param type Transport type (e.g., "GRPC").
         */
        public Builder(String type) {
            this.type = type;
        }

        public Builder grpcTarget(String grpcTarget) {
            this.grpcTarget = grpcTarget;
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
                ", grpcTarget='" + grpcTarget + '\'' +
                '}';
    }
}
