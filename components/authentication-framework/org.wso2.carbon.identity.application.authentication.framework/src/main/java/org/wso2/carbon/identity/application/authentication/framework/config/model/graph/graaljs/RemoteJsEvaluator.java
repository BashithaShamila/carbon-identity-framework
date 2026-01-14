/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com).
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

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Client for communicating with the external GraalJS sidecar service.
 * This allows externalizing the GraalJS runtime to a separate
 * process/container.
 */
public class RemoteJsEvaluator {

    private static final Log log = LogFactory.getLog(RemoteJsEvaluator.class);

    // TODO: Make this configurable via identity.xml or deployment.toml
    private static final String SIDECAR_URL = "http://localhost:8090/eval";
    private static final int DEFAULT_STATEMENT_LIMIT = 5000;
    private static final int CONNECT_TIMEOUT_MS = 5000;
    private static final int READ_TIMEOUT_MS = 30000;

    private static RemoteJsEvaluator instance;

    public static synchronized RemoteJsEvaluator getInstance() {
        if (instance == null) {
            instance = new RemoteJsEvaluator();
        }
        return instance;
    }

    /**
     * Evaluate a script on the remote GraalJS sidecar.
     *
     * @param script        The JavaScript script to evaluate.
     * @param entryFunction The entry function to call (e.g., "onLoginRequest").
     * @return The evaluation result containing success status and collected
     *         commands.
     */
    public EvalResult evaluate(String script, String entryFunction) {
        return evaluate(script, entryFunction, DEFAULT_STATEMENT_LIMIT);
    }

    /**
     * Evaluate a script on the remote GraalJS sidecar with custom statement limit.
     *
     * @param script         The JavaScript script to evaluate.
     * @param entryFunction  The entry function to call.
     * @param statementLimit Maximum statements allowed.
     * @return The evaluation result.
     */
    public EvalResult evaluate(String script, String entryFunction, int statementLimit) {
        try {
            // Build request body: line1=limit, line2=entryFunction, line3+=script
            String requestBody = statementLimit + "\n" + entryFunction + "\n" + script;

            URL url = new URL(SIDECAR_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            conn.setConnectTimeout(CONNECT_TIMEOUT_MS);
            conn.setReadTimeout(READ_TIMEOUT_MS);
            conn.setDoOutput(true);

            // Send request
            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestBody.getBytes(StandardCharsets.UTF_8));
            }

            // Read response
            int responseCode = conn.getResponseCode();
            String responseBody;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            responseCode >= 400 ? conn.getErrorStream() : conn.getInputStream(),
                            StandardCharsets.UTF_8))) {
                responseBody = reader.lines().collect(Collectors.joining("\n"));
            }

            if (responseCode >= 400) {
                log.error("Sidecar returned error: " + responseCode + " - " + responseBody);
                return EvalResult.error("Sidecar error: " + responseCode);
            }

            // Parse JSON response (simple parsing without external library)
            return parseResponse(responseBody);

        } catch (IOException e) {
            log.error("Failed to communicate with GraalJS sidecar at " + SIDECAR_URL, e);
            return EvalResult.error("Sidecar communication failed: " + e.getMessage());
        }
    }

    /**
     * Check if the sidecar is healthy.
     */
    public boolean isHealthy() {
        try {
            URL url = new URL(SIDECAR_URL.replace("/eval", "/health"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(CONNECT_TIMEOUT_MS);
            conn.setReadTimeout(READ_TIMEOUT_MS);
            return conn.getResponseCode() == 200;
        } catch (IOException e) {
            log.warn("GraalJS sidecar health check failed", e);
            return false;
        }
    }

    /**
     * Execute a callback function on the remote GraalJS sidecar.
     * 
     * @param callbackSource The JavaScript callback function source code.
     * @param bindings       Script-defined variables to pass to callback.
     * @param contextData    Serialized context data (user, step, etc.).
     * @return The callback result containing commands.
     */
    public EvalResult executeCallback(String callbackSource, Map<String, Object> bindings,
            CallbackContextData contextData) {
        try {
            // Build JSON request
            StringBuilder json = new StringBuilder("{");
            json.append("\"source\":\"").append(escapeJsonString(callbackSource)).append("\",");
            json.append("\"bindings\":").append(serializeBindings(bindings)).append(",");
            json.append("\"context\":").append(contextData.toJson());
            json.append("}");

            String callbackUrl = SIDECAR_URL.replace("/eval", "/exec-callback");
            URL url = new URL(callbackUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(CONNECT_TIMEOUT_MS);
            conn.setReadTimeout(READ_TIMEOUT_MS);
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            InputStream is = responseCode >= 400 ? conn.getErrorStream() : conn.getInputStream();
            String response = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));

            if (log.isDebugEnabled()) {
                log.debug("POC: Callback response: " + response);
            }

            return parseResponse(response);

        } catch (IOException e) {
            log.error("Failed to execute callback on sidecar", e);
            return EvalResult.error("Sidecar callback failed: " + e.getMessage());
        }
    }

    private String escapeJsonString(String s) {
        if (s == null)
            return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private String serializeBindings(Map<String, Object> bindings) {
        if (bindings == null || bindings.isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        int i = 0;
        for (Map.Entry<String, Object> entry : bindings.entrySet()) {
            if (i++ > 0)
                sb.append(",");
            sb.append("\"").append(entry.getKey()).append("\":");
            sb.append(serializeValue(entry.getValue()));
        }
        sb.append("}");
        return sb.toString();
    }

    private String serializeValue(Object val) {
        if (val == null)
            return "null";
        if (val instanceof String)
            return "\"" + escapeJsonString((String) val) + "\"";
        if (val instanceof Number || val instanceof Boolean)
            return val.toString();
        if (val instanceof List) {
            List<?> list = (List<?>) val;
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++) {
                if (i > 0)
                    sb.append(",");
                sb.append(serializeValue(list.get(i)));
            }
            sb.append("]");
            return sb.toString();
        }
        return "\"" + escapeJsonString(val.toString()) + "\"";
    }

    /**
     * Data class to hold callback context information.
     */
    public static class CallbackContextData {
        private String username;
        private String userStoreDomain;
        private int currentStep;
        private List<String> roles;

        public CallbackContextData() {
            this.roles = new ArrayList<>();
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setUserStoreDomain(String domain) {
            this.userStoreDomain = domain;
        }

        public void setCurrentStep(int step) {
            this.currentStep = step;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles != null ? roles : new ArrayList<>();
        }

        public String toJson() {
            StringBuilder sb = new StringBuilder("{");
            sb.append("\"currentKnownSubject\":{");
            sb.append("\"username\":\"").append(username != null ? username : "").append("\",");
            sb.append("\"userStoreDomain\":\"").append(userStoreDomain != null ? userStoreDomain : "PRIMARY")
                    .append("\",");
            sb.append("\"roles\":[");
            for (int i = 0; i < roles.size(); i++) {
                if (i > 0)
                    sb.append(",");
                sb.append("\"").append(roles.get(i)).append("\"");
            }
            sb.append("]");
            sb.append("},");
            sb.append("\"currentStep\":").append(currentStep);
            sb.append("}");
            return sb.toString();
        }
    }

    /**
     * Simple JSON parser for sidecar response.
     * Format: {"success":true,"elapsed":123,"commands":[...],"bindings":{...}}
     */
    private EvalResult parseResponse(String json) {
        EvalResult result = new EvalResult();

        // Parse success
        result.success = json.contains("\"success\":true");

        // Parse error if present
        Pattern errorPattern = Pattern.compile("\"error\":\"([^\"]+)\"");
        Matcher errorMatcher = errorPattern.matcher(json);
        if (errorMatcher.find()) {
            result.error = unescapeJson(errorMatcher.group(1));
        }

        // Parse elapsed time
        Pattern elapsedPattern = Pattern.compile("\"elapsed\":(\\d+)");
        Matcher elapsedMatcher = elapsedPattern.matcher(json);
        if (elapsedMatcher.find()) {
            result.elapsedMs = Long.parseLong(elapsedMatcher.group(1));
        }

        // Parse commands array
        result.commands = parseCommands(json);

        // Parse bindings object
        result.bindings = parseBindings(json);

        return result;
    }

    /**
     * Parse bindings object from JSON.
     * Format: "bindings":{"varName":"value",...}
     */
    private Map<String, Object> parseBindings(String json) {
        Map<String, Object> bindings = new HashMap<>();

        int start = json.indexOf("\"bindings\":{");
        if (start == -1) {
            return bindings;
        }

        start += "\"bindings\":{".length();

        // Find matching closing brace
        int depth = 1;
        int end = start;
        for (int i = start; i < json.length() && depth > 0; i++) {
            char c = json.charAt(i);
            if (c == '{')
                depth++;
            else if (c == '}') {
                depth--;
                if (depth == 0) {
                    end = i;
                    break;
                }
            }
        }

        String bindingsContent = json.substring(start, end);
        if (bindingsContent.isEmpty()) {
            return bindings;
        }

        // Parse key-value pairs including arrays
        int pos = 0;
        while (pos < bindingsContent.length()) {
            // Skip whitespace and commas
            while (pos < bindingsContent.length() &&
                    (bindingsContent.charAt(pos) == ' ' || bindingsContent.charAt(pos) == ',' ||
                            bindingsContent.charAt(pos) == '\n' || bindingsContent.charAt(pos) == '\r')) {
                pos++;
            }
            if (pos >= bindingsContent.length())
                break;

            // Find key (starts with ")
            if (bindingsContent.charAt(pos) != '"') {
                pos++;
                continue;
            }
            pos++; // skip opening quote
            int keyEnd = bindingsContent.indexOf('"', pos);
            if (keyEnd == -1)
                break;
            String key = bindingsContent.substring(pos, keyEnd);
            pos = keyEnd + 1;

            // Skip colon and whitespace
            while (pos < bindingsContent.length() &&
                    (bindingsContent.charAt(pos) == ':' || bindingsContent.charAt(pos) == ' ')) {
                pos++;
            }
            if (pos >= bindingsContent.length())
                break;

            char valueStart = bindingsContent.charAt(pos);

            if (valueStart == '[') {
                // Array - parse it as ArrayList for Java interop
                int arrayEnd = findMatchingBracket(bindingsContent, pos, '[', ']');
                String arrayContent = bindingsContent.substring(pos + 1, arrayEnd);
                List<Object> list = parseJsonArray(arrayContent);
                bindings.put(key, list);
                pos = arrayEnd + 1;
            } else if (valueStart == '"') {
                // String value
                pos++;
                StringBuilder sb = new StringBuilder();
                while (pos < bindingsContent.length()) {
                    char c = bindingsContent.charAt(pos);
                    if (c == '\\' && pos + 1 < bindingsContent.length()) {
                        char next = bindingsContent.charAt(pos + 1);
                        if (next == 'n') {
                            sb.append('\n');
                            pos += 2;
                        } else if (next == '"') {
                            sb.append('"');
                            pos += 2;
                        } else if (next == '\\') {
                            sb.append('\\');
                            pos += 2;
                        } else {
                            sb.append(c);
                            pos++;
                        }
                    } else if (c == '"') {
                        pos++;
                        break;
                    } else {
                        sb.append(c);
                        pos++;
                    }
                }
                bindings.put(key, sb.toString());
            } else if (valueStart == 't' || valueStart == 'f') {
                // boolean
                if (bindingsContent.substring(pos).startsWith("true")) {
                    bindings.put(key, true);
                    pos += 4;
                } else if (bindingsContent.substring(pos).startsWith("false")) {
                    bindings.put(key, false);
                    pos += 5;
                }
            } else if (valueStart == 'n') {
                bindings.put(key, null);
                pos += 4;
            } else if (Character.isDigit(valueStart) || valueStart == '-') {
                int numEnd = pos;
                while (numEnd < bindingsContent.length() &&
                        (Character.isDigit(bindingsContent.charAt(numEnd)) ||
                                bindingsContent.charAt(numEnd) == '.' || bindingsContent.charAt(numEnd) == '-')) {
                    numEnd++;
                }
                String numStr = bindingsContent.substring(pos, numEnd);
                try {
                    if (numStr.contains(".")) {
                        bindings.put(key, Double.parseDouble(numStr));
                    } else {
                        bindings.put(key, Long.parseLong(numStr));
                    }
                } catch (NumberFormatException e) {
                    bindings.put(key, numStr);
                }
                pos = numEnd;
            } else {
                pos++;
            }
        }

        // Log each binding with its type for debugging
        for (Map.Entry<String, Object> e : bindings.entrySet()) {
            Object val = e.getValue();
            String typeName = val == null ? "null" : val.getClass().getName();
            log.info("POC: Binding '" + e.getKey() + "' type=" + typeName + " value=" + val);
        }
        log.info("POC: Parsed " + bindings.size() + " bindings: " + bindings.keySet());
        return bindings;
    }

    private int findMatchingBracket(String s, int start, char open, char close) {
        int depth = 0;
        for (int i = start; i < s.length(); i++) {
            if (s.charAt(i) == open)
                depth++;
            else if (s.charAt(i) == close) {
                depth--;
                if (depth == 0)
                    return i;
            }
        }
        return s.length();
    }

    private List<Object> parseJsonArray(String arrayContent) {
        List<Object> list = new ArrayList<>();
        int pos = 0;

        while (pos < arrayContent.length()) {
            while (pos < arrayContent.length() &&
                    (arrayContent.charAt(pos) == ' ' || arrayContent.charAt(pos) == ',')) {
                pos++;
            }
            if (pos >= arrayContent.length())
                break;

            char c = arrayContent.charAt(pos);
            if (c == '"') {
                pos++;
                StringBuilder sb = new StringBuilder();
                while (pos < arrayContent.length()) {
                    char ch = arrayContent.charAt(pos);
                    if (ch == '\\' && pos + 1 < arrayContent.length()) {
                        sb.append(arrayContent.charAt(pos + 1));
                        pos += 2;
                    } else if (ch == '"') {
                        pos++;
                        break;
                    } else {
                        sb.append(ch);
                        pos++;
                    }
                }
                list.add(sb.toString());
            } else if (Character.isDigit(c) || c == '-') {
                int numEnd = pos;
                while (numEnd < arrayContent.length() &&
                        (Character.isDigit(arrayContent.charAt(numEnd)) ||
                                arrayContent.charAt(numEnd) == '.' || arrayContent.charAt(numEnd) == '-')) {
                    numEnd++;
                }
                String numStr = arrayContent.substring(pos, numEnd);
                try {
                    if (numStr.contains("."))
                        list.add(Double.parseDouble(numStr));
                    else
                        list.add(Long.parseLong(numStr));
                } catch (NumberFormatException e) {
                    list.add(numStr);
                }
                pos = numEnd;
            } else {
                pos++;
            }
        }
        return list;
    }

    /**
     * Parse commands array from JSON.
     */
    private List<Command> parseCommands(String json) {
        List<Command> commands = new ArrayList<>();

        // Find commands array
        int start = json.indexOf("\"commands\":[");
        if (start == -1)
            return commands;

        start += "\"commands\":[".length();
        int depth = 1;
        int cmdStart = start;

        for (int i = start; i < json.length() && depth > 0; i++) {
            char c = json.charAt(i);
            if (c == '{') {
                if (depth == 1)
                    cmdStart = i;
                depth++;
            } else if (c == '}') {
                depth--;
                if (depth == 1) {
                    String cmdJson = json.substring(cmdStart, i + 1);
                    commands.add(parseCommand(cmdJson));
                }
            } else if (c == ']' && depth == 1) {
                break;
            }
        }

        return commands;
    }

    /**
     * Parse a single command object.
     */
    private Command parseCommand(String json) {
        Command cmd = new Command();

        // Parse type
        Pattern typePattern = Pattern.compile("\"type\":\"([^\"]+)\"");
        Matcher typeMatcher = typePattern.matcher(json);
        if (typeMatcher.find()) {
            cmd.type = typeMatcher.group(1);
        }

        // Parse stepId for executeStep
        Pattern stepIdPattern = Pattern.compile("\"stepId\":(\\d+)");
        Matcher stepIdMatcher = stepIdPattern.matcher(json);
        if (stepIdMatcher.find()) {
            cmd.stepId = Integer.parseInt(stepIdMatcher.group(1));
        }

        // Parse options (simplified - just store as string for now)
        int optStart = json.indexOf("\"options\":");
        if (optStart != -1) {
            cmd.optionsJson = json.substring(optStart + "\"options\":".length());
            // Find matching brace
            int braceDepth = 0;
            int end = 0;
            for (int i = 0; i < cmd.optionsJson.length(); i++) {
                char c = cmd.optionsJson.charAt(i);
                if (c == '{')
                    braceDepth++;
                else if (c == '}') {
                    braceDepth--;
                    if (braceDepth == 0) {
                        end = i + 1;
                        break;
                    }
                }
            }
            if (end > 0) {
                cmd.optionsJson = cmd.optionsJson.substring(0, end);
            }
        }

        return cmd;
    }

    private String unescapeJson(String s) {
        return s.replace("\\\"", "\"")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\\\", "\\");
    }

    /**
     * Result of script evaluation.
     */
    public static class EvalResult {
        public boolean success;
        public String error;
        public long elapsedMs;
        public List<Command> commands = new ArrayList<>();
        public Map<String, Object> bindings = new HashMap<>();

        public static EvalResult error(String message) {
            EvalResult r = new EvalResult();
            r.success = false;
            r.error = message;
            return r;
        }
    }

    /**
     * A command collected from script execution (e.g., executeStep, sendError).
     */
    public static class Command {
        public String type;
        public int stepId;
        public String optionsJson;
        public String url;
        public String message;

        // Callback function sources extracted from options
        public String onSuccessSource;
        public String onFailSource;

        /**
         * Parse callback function sources from optionsJson.
         */
        public void parseCallbacks() {
            if (optionsJson == null || optionsJson.isEmpty()) {
                return;
            }

            // Extract onSuccess function source
            onSuccessSource = extractFunctionSource(optionsJson, "onSuccess");
            onFailSource = extractFunctionSource(optionsJson, "onFail");

            if (log.isDebugEnabled()) {
                log.debug("Parsed callbacks - onSuccess: " + (onSuccessSource != null) +
                        ", onFail: " + (onFailSource != null));
            }
        }

        private String extractFunctionSource(String json, String key) {
            // Pattern to find "key":"<function source>"
            String searchKey = "\"" + key + "\":\"";
            int start = json.indexOf(searchKey);
            if (start == -1) {
                return null;
            }

            start += searchKey.length();

            // Find the end quote (handling escaped characters)
            StringBuilder result = new StringBuilder();
            boolean escaped = false;
            for (int i = start; i < json.length(); i++) {
                char c = json.charAt(i);
                if (escaped) {
                    // Handle escape sequences properly
                    switch (c) {
                        case 'n':
                            result.append('\n');
                            break;
                        case 'r':
                            result.append('\r');
                            break;
                        case 't':
                            result.append('\t');
                            break;
                        case '"':
                            result.append('"');
                            break;
                        case '\\':
                            result.append('\\');
                            break;
                        default:
                            // Unknown escape, preserve both chars
                            result.append('\\').append(c);
                    }
                    escaped = false;
                } else if (c == '\\') {
                    escaped = true;
                } else if (c == '"') {
                    break;
                } else {
                    result.append(c);
                }
            }

            String source = result.toString();
            return source.isEmpty() ? null : source;
        }

        @Override
        public String toString() {
            return "Command{type='" + type + "', stepId=" + stepId +
                    ", hasOnSuccess=" + (onSuccessSource != null) +
                    ", hasOnFail=" + (onFailSource != null) + "}";
        }
    }
}
