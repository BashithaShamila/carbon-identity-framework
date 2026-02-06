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

/**
 * MIGRATION NOTICE:
 * ================
 * This gRPC implementation has been MOVED to: com.wso2.identity.asgardeo.scope.service jar
 * Package: com.wso2.identity.asgardeo.scope.service.graaljs.transport
 *
 * Reason: To avoid dependency conflicts, the gRPC transport implementation is now provided
 * as a separate OSGi bundle that registers itself with the TransportFactory at runtime.
 *
 * The implementation is automatically registered when the com.wso2.identity.asgardeo.scope.service
 * bundle is active, and is available through the standard TransportFactory.createTransport() API.
 *
 * DO NOT restore this file - use the new location instead.
 *
 * See: com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportImpl
 */
@Deprecated
public class GrpcTransportImpl {
    private GrpcTransportImpl() {
        throw new UnsupportedOperationException(
            "This class has been moved to com.wso2.identity.asgardeo.scope.service.graaljs.transport package. " +
            "The implementation is now provided via OSGi service registration."
        );
    }
}
