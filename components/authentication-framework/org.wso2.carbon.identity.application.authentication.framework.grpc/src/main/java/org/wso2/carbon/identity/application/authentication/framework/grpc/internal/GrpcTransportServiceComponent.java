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

package org.wso2.carbon.identity.application.authentication.framework.grpc.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteEngineTransport;
import org.wso2.carbon.identity.application.authentication.framework.grpc.transport.GrpcTransportProvider;
import org.wso2.carbon.identity.core.util.IdentityUtil;

/**
 * OSGi service component for registering gRPC transport as a RemoteEngineTransport service.
 * <p>
 * On activation, creates the gRPC streaming transport singleton and registers it
 * as a RemoteEngineTransport OSGi service, making it available to the authentication
 * framework for remote JavaScript engine communication.
 */
@Component(
    name = "org.wso2.carbon.identity.application.authentication.framework.grpc.transport.component",
    immediate = true
)
public class GrpcTransportServiceComponent {

    private static final Log log = LogFactory.getLog(GrpcTransportServiceComponent.class);
    private static final String GRPC_TARGET_CONFIG_KEY = "AdaptiveAuth.GraalJS.GrpcTarget";
    private static final String DEFAULT_GRPC_TARGET = "localhost:50051";

    @Activate
    protected void activate(ComponentContext context) {

        try {
            // Read gRPC target directly from config to avoid triggering
            // JsEngineFactory class loading before IdentityUtil is ready.
            String grpcTarget = IdentityUtil.getProperty(GRPC_TARGET_CONFIG_KEY);
            if (grpcTarget == null || grpcTarget.isEmpty()) {
                grpcTarget = DEFAULT_GRPC_TARGET;
            }
            RemoteEngineTransport transport = GrpcTransportProvider.getOrCreateTransport(grpcTarget);

            context.getBundleContext().registerService(
                    RemoteEngineTransport.class.getName(), transport, null);

            log.info("[GrpcTransportServiceComponent] gRPC transport registered as OSGi service. " +
                    "Target: " + grpcTarget);
        } catch (Exception e) {
            log.error("[GrpcTransportServiceComponent] Error registering gRPC transport", e);
        }
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {

        log.info("[GrpcTransportServiceComponent] gRPC transport service component deactivated");
    }
}
