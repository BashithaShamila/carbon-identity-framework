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
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.POLYGLOT_LANGUAGE;
import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.JSAttributes.POLYGLOT_SOURCE;

/**
 * Local in-JVM JavaScript engine implementation using GraalVM Polyglot Context.
 * This is the existing execution model wrapped in the JsEngine interface.
 */
public class LocalJsEngine implements JsEngine {

    private static final Log log = LogFactory.getLog(LocalJsEngine.class);

    private final Context context;
    private boolean closed = false;

    /**
     * Create a new LocalJsEngine wrapping the given Polyglot Context.
     *
     * @param context The GraalVM Polyglot Context to use for JavaScript execution.
     */
    public LocalJsEngine(Context context) {
        this.context = context;
    }

    @Override
    public EvaluationResult evaluate(String script, String sourceIdentifier, Map<String, Object> bindings) {
        long startTime = System.currentTimeMillis();

        try {
            Value jsBindings = context.getBindings(POLYGLOT_LANGUAGE);

            // Apply bindings
            if (bindings != null) {
                bindings.forEach(jsBindings::putMember);
            }

            // Evaluate the script
            Value result = context.eval(
                    Source.newBuilder(POLYGLOT_LANGUAGE, script,
                            sourceIdentifier != null ? sourceIdentifier : POLYGLOT_SOURCE)
                            .build());

            // Extract updated bindings
            Map<String, Object> updatedBindings = extractBindings(jsBindings);

            long elapsed = System.currentTimeMillis() - startTime;
            return EvaluationResult.builder()
                    .success(true)
                    .result(result)
                    .updatedBindings(updatedBindings)
                    .elapsedMs(elapsed)
                    .build();

        } catch (PolyglotException e) {
            long elapsed = System.currentTimeMillis() - startTime;
            log.error("PolyglotException during script evaluation", e);
            return EvaluationResult.failure(e.getMessage(), "PolyglotException", elapsed);
        } catch (IOException e) {
            long elapsed = System.currentTimeMillis() - startTime;
            log.error("IOException during script evaluation", e);
            return EvaluationResult.failure(e.getMessage(), "IOException", elapsed);
        }
    }

    @Override
    public EvaluationResult executeCallback(String functionSource, Object[] arguments,
            Map<String, Object> bindings, AuthenticationContext authContext) {
        long startTime = System.currentTimeMillis();

        try {
            Value jsBindings = context.getBindings(POLYGLOT_LANGUAGE);

            // Restore bindings
            if (bindings != null) {
                bindings.forEach(jsBindings::putMember);
            }

            // Evaluate the function source to get a callable function
            Value function = context.eval(
                    Source.newBuilder(POLYGLOT_LANGUAGE, "(" + functionSource + ")",
                            POLYGLOT_SOURCE).build());

            if (!function.canExecute()) {
                return EvaluationResult
                        .failure("Function source is not executable", "InvalidFunction",
                        System.currentTimeMillis() - startTime);
            }

            // Execute the function with arguments
            Value result = function.execute(arguments);

            // Extract updated bindings
            Map<String, Object> updatedBindings = extractBindings(jsBindings);

            long elapsed = System.currentTimeMillis() - startTime;
            return EvaluationResult.builder()
                    .success(true)
                    .result(result)
                    .updatedBindings(updatedBindings)
                    .elapsedMs(elapsed)
                    .build();

        } catch (PolyglotException e) {
            long elapsed = System.currentTimeMillis() - startTime;
            log.error("PolyglotException during callback execution", e);
            return EvaluationResult.failure(e.getMessage(), "PolyglotException", elapsed);
        } catch (IOException e) {
            long elapsed = System.currentTimeMillis() - startTime;
            log.error("IOException during callback execution", e);
            return EvaluationResult.failure(e.getMessage(), "IOException", elapsed);
        }
    }

    @Override
    public Map<String, Object> getBindings() {
        Value jsBindings = context.getBindings(POLYGLOT_LANGUAGE);
        return extractBindings(jsBindings);
    }

    @Override
    public void putBinding(String name, Object value) {
        context.getBindings(POLYGLOT_LANGUAGE).putMember(name, value);
    }

    @Override
    public void registerHostFunctions(Map<String, Object> hostFunctions) {
        Value jsBindings = context.getBindings(POLYGLOT_LANGUAGE);
        if (hostFunctions != null) {
            hostFunctions.forEach(jsBindings::putMember);
        }
    }

    @Override
    public String getSessionId() {
        // Local engine doesn't have session ID
        return null;
    }

    @Override
    public void close() {
        if (!closed) {
            context.close();
            closed = true;
        }
    }

    /**
     * Get the underlying Polyglot Context.
     * Used for backward compatibility with existing code.
     *
     * @return The Polyglot Context.
     */
    public Context getContext() {
        return context;
    }

    private Map<String, Object> extractBindings(Value jsBindings) {
        Map<String, Object> result = new HashMap<>();
        for (String key : jsBindings.getMemberKeys()) {
            Value binding = jsBindings.getMember(key);
            // Skip host functions and Log object
            if (!((binding.isHostObject() && binding.canExecute()) || key.equals("Log"))) {
                result.put(key, binding);
            }
        }
        return result;
    }
}
