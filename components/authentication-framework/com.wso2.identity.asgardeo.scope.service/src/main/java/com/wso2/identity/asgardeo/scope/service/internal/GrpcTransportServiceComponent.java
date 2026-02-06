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

package com.wso2.identity.asgardeo.scope.service.internal;

import com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.TransportFactory;

/**
 * OSGi service component for registering gRPC transport provider with TransportFactory.
 * <p>
 * This component automatically registers the gRPC transport implementation when the bundle
 * is activated, making it available to the authentication framework for remote JavaScript
 * engine communication.
 */
@Component(
    name = "com.wso2.identity.asgardeo.scope.service.graaljs.transport.component",
    immediate = true
)
public class GrpcTransportServiceComponent {

    private static final Log log = LogFactory.getLog(GrpcTransportServiceComponent.class);

    @Activate
    protected void activate(ComponentContext context) {
        try {
            // Register gRPC transport provider with TransportFactory
            TransportFactory factory = TransportFactory.getInstance();
            factory.registerProvider("GRPC", new GrpcTransportProvider());

            log.info("[GrpcTransportServiceComponent] gRPC transport provider registered successfully");
        } catch (Exception e) {
            log.error("[GrpcTransportServiceComponent] Error registering gRPC transport provider", e);
        }
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        log.info("[GrpcTransportServiceComponent] gRPC transport service component deactivated");
        // Note: We don't unregister the provider on deactivation to avoid breaking existing connections
        // The framework will handle failures gracefully if the provider becomes unavailable
    }
}
