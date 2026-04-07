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
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.AuthGraphNode;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.JsGraphBuilder;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;

/**
 * Manages thread-local context setup and cleanup for gRPC callback threads.
 * <p>
 * In remote mode, each gRPC callback runs on a separate thread. This class
 * replicates the thread-local state (CarbonContext, tenant domain, graph builder)
 * that local mode gets naturally from single-threaded execution.
 * <p>
 * Also manages the accumulated dynamicallyBuiltBaseNode, which persists graph nodes
 * across gRPC round-trips to replicate local mode's single-thread accumulation behavior.
 */
class ThreadContextManager {

    private static final Log log = LogFactory.getLog(ThreadContextManager.class);

    private final AuthenticationContext authContext;
    private JsGraphBuilder graphBuilder;

    // Accumulated dynamicallyBuiltBaseNode across gRPC callbacks.
    // In local mode, the dynamicallyBuiltBaseNode ThreadLocal accumulates nodes
    // across host function calls within a single callback execution (same thread).
    // In remote mode, each gRPC callback runs on a separate thread, so we persist
    // the value here between setup/clear pairs to replicate local mode behavior.
    private volatile AuthGraphNode accumulatedDynamicBaseNode;

    ThreadContextManager(AuthenticationContext authContext) {
        this.authContext = authContext;
    }

    /**
     * Set the graph builder reference.
     * This is needed so that gRPC callback threads can set the currentBuilder
     * ThreadLocal, which is required by static methods like JsGraphBuilder.addLongWaitProcess().
     *
     * @param graphBuilder The JsGraphBuilder instance to use for callback thread context.
     */
    void setGraphBuilder(JsGraphBuilder graphBuilder) {
        this.graphBuilder = graphBuilder;
        log.debug("[RemoteJsEngine] Graph builder set: " +
                (graphBuilder != null ? graphBuilder.getClass().getSimpleName() : "null"));
    }

    /**
     * Get the accumulated dynamicallyBuiltBaseNode value.
     * This is the value accumulated across gRPC callbacks, replicating
     * the local mode ThreadLocal behavior across threads.
     *
     * @return The accumulated AuthGraphNode, or null if none was built.
     */
    AuthGraphNode getAccumulatedDynamicBaseNode() {
        return accumulatedDynamicBaseNode;
    }

    /**
     * Reset the accumulated dynamicallyBuiltBaseNode.
     * Should be called before a new callback evaluation cycle begins.
     */
    void resetAccumulatedDynamicBaseNode() {
        this.accumulatedDynamicBaseNode = null;
    }

    /**
     * Set up thread-local context required for host function invocation.
     * This ensures tenant context, carbon context, and JS graph builder contexts
     * are properly set.
     */
    void setup() {
        if (authContext == null) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("[RemoteJsEngine] Setting up thread context for tenant: " + authContext.getTenantDomain() +
                    ", contextId: " + authContext.getContextIdentifier());
        }
        try {
            // Use startTenantFlow to push a clean CarbonContext frame.
            // This ensures endTenantFlow in clear() restores the previous state,
            // preventing tenant context leaks on pooled gRPC threads.
            org.wso2.carbon.context.PrivilegedCarbonContext.startTenantFlow();
            org.wso2.carbon.context.PrivilegedCarbonContext carbonContext =
                    org.wso2.carbon.context.PrivilegedCarbonContext.getThreadLocalCarbonContext();
            carbonContext.setTenantDomain(authContext.getTenantDomain(), true);

            // Set username if available.
            if (authContext.getSubject() != null) {
                carbonContext.setUsername(authContext.getSubject().getUserName());
            }
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Thread context set - tenantDomain: " + carbonContext.getTenantDomain() +
                        ", tenantId: " + carbonContext.getTenantId() +
                        ", username: " + carbonContext.getUsername());
            }

            // Set JsGraalGraphBuilder thread-local contexts for host function callbacks.
            // This is critical for executeStep and other functions that need the context.
            JsGraalGraphBuilder.setContextForJsThreadLocal(authContext);
            if (log.isDebugEnabled()) {
                log.debug("[RemoteJsEngine] Set contextForJs ThreadLocal with authContext: " +
                        authContext.getContextIdentifier());
            }

            // Set dynamicallyBuiltBaseNode from accumulated value across gRPC callbacks.
            // In local mode, this ThreadLocal starts null during callback execution and
            // accumulates nodes via executeStepInAsyncEvent across calls on the same
            // thread.
            // In remote mode, each gRPC callback runs on a separate thread, so we persist
            // the value in accumulatedDynamicBaseNode between setup/clear pairs to
            // replicate
            // the local mode single-thread accumulation behavior.
            if (accumulatedDynamicBaseNode != null) {
                JsGraalGraphBuilder.setDynamicallyBuiltBaseNodeThreadLocal(accumulatedDynamicBaseNode);
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Set dynamicallyBuiltBaseNode from accumulated: " +
                            accumulatedDynamicBaseNode.getClass().getSimpleName());
                }
            } else {
                log.debug("[RemoteJsEngine] dynamicallyBuiltBaseNode accumulated is null (initial state)");
            }

            // Set currentBuilder ThreadLocal for the gRPC callback thread.
            // This is required by static methods like JsGraphBuilder.addLongWaitProcess()
            // which are used by async host functions (e.g., updateUserPassword).
            if (graphBuilder != null) {
                JsGraalGraphBuilder.setCurrentBuilderThreadLocal(graphBuilder);
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Set currentBuilder ThreadLocal: " +
                            graphBuilder.getClass().getSimpleName());
                }
            }

        } catch (Exception e) {
            log.warn("[RemoteJsEngine] Failed to set up thread context: " + e.getMessage(), e);
        }
    }

    /**
     * Clear thread-local context after host function invocation.
     */
    void clear() {
        // Save dynamicallyBuiltBaseNode before clearing, so it accumulates across
        // gRPC callbacks (replicating local mode's single-thread behavior).
        try {
            AuthGraphNode currentDynamicNode = JsGraalGraphBuilder.getDynamicallyBuiltBaseNodeThreadLocal();
            if (currentDynamicNode != null) {
                this.accumulatedDynamicBaseNode = currentDynamicNode;
                if (log.isDebugEnabled()) {
                    log.debug("[RemoteJsEngine] Saved dynamicallyBuiltBaseNode to accumulated: " +
                            currentDynamicNode.getClass().getSimpleName());
                }
            }
            JsGraalGraphBuilder.removeContextForJsThreadLocal();
            JsGraalGraphBuilder.removeDynamicallyBuiltBaseNodeThreadLocal();
            JsGraalGraphBuilder.removeCurrentBuilderThreadLocal();
            log.debug("[RemoteJsEngine] Cleared JsGraalGraphBuilder ThreadLocals");
        } catch (Exception e) {
            log.debug("[RemoteJsEngine] Error clearing thread context: " + e.getMessage());
        } finally {
            // Pop the CarbonContext frame pushed by setup(), restoring the previous
            // thread state. This prevents tenant context leaks on pooled gRPC threads.
            try {
                org.wso2.carbon.context.PrivilegedCarbonContext.endTenantFlow();
            } catch (Exception e) {
                log.debug("[RemoteJsEngine] Error ending tenant flow: " + e.getMessage());
            }
        }
    }
}
