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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.CallbackServer;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.HostCallbackServer;

import java.io.IOException;

/**
 * Unix Domain Socket implementation of CallbackServer.
 * <p>
 * This implementation wraps the existing HostCallbackServer singleton to provide
 * callback server abstraction while maintaining backward compatibility with the
 * current UDS-based callback protocol.
 * <p>
 * Acts as an adapter between the CallbackServer interface and the legacy
 * HostCallbackServer implementation.
 */
public class UdsCallbackServerImpl implements CallbackServer {

    private static final Log log = LogFactory.getLog(UdsCallbackServerImpl.class);

    private final HostCallbackServer hostCallbackServer;

    /**
     * Create a new UDS callback server wrapper.
     * Gets or creates the singleton HostCallbackServer instance.
     */
    public UdsCallbackServerImpl() {
        this.hostCallbackServer = HostCallbackServer.getInstance();
        if (this.hostCallbackServer == null) {
            log.warn("[UdsCallbackServerImpl] HostCallbackServer getInstance returned null");
        } else {
            log.debug("[UdsCallbackServerImpl] Wrapped HostCallbackServer instance");
        }
    }

    @Override
    public void registerHandler(String sessionId, HostFunctionHandler handler) {
        log.debug("[UdsCallbackServerImpl] Registering handler for session: " + sessionId);
        if (hostCallbackServer != null) {
            // Wrap our CallbackServer.HostFunctionHandler to HostCallbackServer.HostFunctionHandler
            HostCallbackServer.HostFunctionHandler wrappedHandler = new HostCallbackServer.HostFunctionHandler() {
                @Override
                public Object invokeHostFunction(String functionName, Object... args) throws Exception {
                    return handler.invokeHostFunction(functionName, args);
                }

                @Override
                public Object getContextProperty(String propertyPath) throws Exception {
                    return handler.getContextProperty(propertyPath);
                }

                @Override
                public boolean setContextProperty(String propertyPath, Object value) throws Exception {
                    return handler.setContextProperty(propertyPath, value);
                }
            };
            hostCallbackServer.registerHandler(sessionId, wrappedHandler);
        }
    }

    @Override
    public void unregisterHandler(String sessionId) {
        log.debug("[UdsCallbackServerImpl] Unregistering handler for session: " + sessionId);
        if (hostCallbackServer != null) {
            hostCallbackServer.unregisterHandler(sessionId);
        }
    }

    @Override
    public String getCallbackAddress() {
        String address = HostCallbackServer.getCallbackSocketPath();
        log.debug("[UdsCallbackServerImpl] Callback address: " + address);
        return address;
    }

    @Override
    public void start() throws IOException {
        log.debug("[UdsCallbackServerImpl] Start called (HostCallbackServer already started by getInstance)");
        // HostCallbackServer is started automatically when getInstance() is called
        // No additional action needed
    }

    @Override
    public void close() throws IOException {
        log.debug("[UdsCallbackServerImpl] Close called (not closing singleton HostCallbackServer)");
        // Don't close the singleton HostCallbackServer as it may be used by other sessions
        // The singleton will be closed when the IS process shuts down
    }
}
