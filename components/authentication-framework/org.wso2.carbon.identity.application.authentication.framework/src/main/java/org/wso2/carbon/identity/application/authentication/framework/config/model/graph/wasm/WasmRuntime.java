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

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.wasm;

import com.dylibso.chicory.log.SystemLogger;
import com.dylibso.chicory.runtime.HostFunction;
import com.dylibso.chicory.runtime.ImportValues;
import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.runtime.Memory;
import com.dylibso.chicory.wasi.WasiOptions;
import com.dylibso.chicory.wasi.WasiPreview1;
import com.dylibso.chicory.wasm.Parser;
import com.dylibso.chicory.wasm.WasmModule;
import com.dylibso.chicory.wasm.types.ValueType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * WasmRuntime manages the Chicory WASM instance for executing JavaScript via
 * QuickJS compiled to WASM (Javy plugin).
 * This provides a sandboxed JavaScript execution environment for adaptive
 * authentication scripts.
 * 
 * Based on the quickjs4j reference implementation.
 */
public class WasmRuntime implements AutoCloseable {

    private static final Log LOG = LogFactory.getLog(WasmRuntime.class);
    private static final String WASM_RESOURCE_PATH = "/wasm/javy_quickjs4j_plugin.wasm";
    private static final Gson GSON = new GsonBuilder().create();
    private static final int ALIGNMENT = 1;

    private final Map<String, BiFunction<String, Object[], Object>> hostFunctions;
    private final ByteArrayOutputStream stdout;
    private final ByteArrayOutputStream stderr;

    private Instance instance;
    private Memory memory;
    private WasiPreview1 wasi;
    private boolean initialized = false;

    // Cached export functions
    private Object compileSrcExport;
    private Object invokeExport;
    private Object canonicalAbiReallocExport;
    private Object canonicalAbiFreeExport;

    /**
     * Constructs a new WasmRuntime instance.
     * Loads the WASM binary and initializes the runtime.
     */
    public WasmRuntime() {
        this(new HashMap<>());
    }

    /**
     * Constructs a new WasmRuntime instance with custom host functions.
     *
     * @param hostFunctions Map of host function names to their implementations.
     */
    public WasmRuntime(Map<String, BiFunction<String, Object[], Object>> hostFunctions) {
        this.hostFunctions = new ConcurrentHashMap<>(hostFunctions);
        this.stdout = new ByteArrayOutputStream();
        this.stderr = new ByteArrayOutputStream();

        try {
            initializeRuntime();
        } catch (Exception e) {
            LOG.warn("Failed to initialize WASM runtime: " + e.getMessage() +
                    ". JavaScript evaluation will use fallback mode.", e);
        }
    }

    /**
     * Initializes the Chicory runtime with proper host imports.
     */
    private void initializeRuntime() throws Exception {
        // Load WASM binary
        byte[] wasmBytes;
        try (InputStream wasmStream = getClass().getResourceAsStream(WASM_RESOURCE_PATH)) {
            if (wasmStream == null) {
                throw new IOException("Could not find WASM resource: " + WASM_RESOURCE_PATH);
            }
            wasmBytes = wasmStream.readAllBytes();
            LOG.debug("Loaded WASM binary: " + wasmBytes.length + " bytes");
        }

        // Parse the WASM module
        WasmModule module = Parser.parse(wasmBytes);

        // Create WASI with stdout/stderr capture
        WasiOptions wasiOpts = WasiOptions.builder()
                .withStdout(stdout)
                .withStderr(stderr)
                .build();
        wasi = WasiPreview1.builder()
                .withOptions(wasiOpts)
                .withLogger(new SystemLogger())
                .build();

        // Create the chicory.invoke host function
        // This is the critical host import that the Javy plugin uses to call back into
        // Java
        HostFunction invokeFn = new HostFunction(
                "chicory",
                "invoke",
                List.of(
                        ValueType.I32, // module_name_ptr
                        ValueType.I32, // module_name_len
                        ValueType.I32, // func_name_ptr
                        ValueType.I32, // func_name_len
                        ValueType.I32, // args_ptr
                        ValueType.I32 // args_len
                ),
                List.of(ValueType.I32), // return: wide_ptr to result
                (inst, args) -> invokeBuiltin(inst, args));

        // Build the instance with all imports
        instance = Instance.builder(module)
                .withImportValues(
                        ImportValues.builder()
                                .addFunction(wasi.toHostFunctions())
                                .addFunction(invokeFn)
                                .build())
                .build();

        memory = instance.memory();

        // Initialize the QuickJS runtime
        initializeQuickJsRuntime();

        initialized = true;
        LOG.info("WASM runtime initialized successfully with Chicory");
    }

    /**
     * Initializes the QuickJS runtime inside WASM.
     */
    private void initializeQuickJsRuntime() {
        try {
            // Call initialize_runtime if exported
            com.dylibso.chicory.runtime.ExportFunction initFunc = instance.export("initialize_runtime");
            if (initFunc != null) {
                initFunc.apply();
                LOG.debug("QuickJS runtime initialized");
            }
        } catch (Exception e) {
            LOG.debug("initialize_runtime not available or failed: " + e.getMessage());
        }
    }

    /**
     * Handles the chicory.invoke callback from JavaScript.
     * This is called when JavaScript code calls java_invoke().
     */
    private long[] invokeBuiltin(Instance inst, long... args) {
        String moduleName = readString((int) args[0], (int) args[1]);
        String funcName = readString((int) args[2], (int) args[3]);
        String argsString = readString((int) args[4], (int) args[5]);

        LOG.info("invokeBuiltin: Received call - " + moduleName + "." + funcName + " with args: " + argsString);

        // Look up the host function
        String key = moduleName + "." + funcName;
        BiFunction<String, Object[], Object> handler = hostFunctions.get(key);

        String resultJson;
        if (handler == null) {
            // Default handler - return null for unknown functions
            LOG.warn("Unknown host function called: " + key);
            resultJson = "null";
        } else {
            try {
                // Parse arguments
                Object[] parsedArgs = parseArgs(argsString);
                Object result = handler.apply(funcName, parsedArgs);
                resultJson = GSON.toJson(result);
            } catch (Exception e) {
                LOG.error("Error invoking host function: " + key, e);
                resultJson = "null";
            }
        }

        // Write the result back to WASM memory
        byte[] resultBytes = resultJson.getBytes(StandardCharsets.UTF_8);
        int resultPtr = allocateMemory(resultBytes.length);
        memory.write(resultPtr, resultBytes);

        // Return a wide pointer (ptr, len) packed into 8 bytes
        int widePtr = allocateMemory(8);
        memory.writeI32(widePtr, resultPtr);
        memory.writeI32(widePtr + 4, resultBytes.length);

        return new long[] { widePtr };
    }

    /**
     * Parses JSON arguments string into Object array.
     */
    private Object[] parseArgs(String argsString) {
        try {
            JsonElement element = JsonParser.parseString(argsString);
            if (element.isJsonArray()) {
                JsonArray arr = element.getAsJsonArray();
                Object[] result = new Object[arr.size()];
                for (int i = 0; i < arr.size(); i++) {
                    result[i] = GSON.fromJson(arr.get(i), Object.class);
                }
                return result;
            }
        } catch (Exception e) {
            LOG.warn("Failed to parse args: " + argsString, e);
        }
        return new Object[0];
    }

    /**
     * Reads a string from WASM memory.
     */
    private String readString(int ptr, int len) {
        if (ptr == 0 || len <= 0) {
            return "";
        }
        byte[] bytes = memory.readBytes(ptr, len);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Allocates memory in WASM using canonical_abi_realloc.
     */
    private int allocateMemory(int size) {
        try {
            com.dylibso.chicory.runtime.ExportFunction reallocFunc = instance.export("canonical_abi_realloc");
            if (reallocFunc != null) {
                long[] result = reallocFunc.apply(0, 0, ALIGNMENT, size);
                return (int) result[0];
            }
        } catch (Exception e) {
            LOG.warn("canonical_abi_realloc failed: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Frees memory in WASM using canonical_abi_free.
     */
    private void freeMemory(int ptr, int size) {
        if (ptr == 0) {
            return;
        }
        try {
            com.dylibso.chicory.runtime.ExportFunction freeFunc = instance.export("canonical_abi_free");
            if (freeFunc != null) {
                freeFunc.apply(ptr, size, ALIGNMENT);
            }
        } catch (Exception e) {
            LOG.debug("canonical_abi_free failed: " + e.getMessage());
        }
    }

    /**
     * Registers a host function that can be called from JavaScript.
     * Uses default module name "wso2".
     *
     * @param funcName The function name (e.g., "executeStep")
     * @param handler  The function handler.
     */
    public void registerHostFunction(String funcName,
            BiFunction<String, Object[], Object> handler) {
        registerHostFunction("wso2", funcName, handler);
    }

    /**
     * Registers a host function that can be called from JavaScript.
     *
     * @param moduleName The module name (e.g., "authContext")
     * @param funcName   The function name (e.g., "getUser")
     * @param handler    The function handler.
     */
    public void registerHostFunction(String moduleName, String funcName,
            BiFunction<String, Object[], Object> handler) {
        hostFunctions.put(moduleName + "." + funcName, handler);
    }

    /**
     * Compiles JavaScript source code and returns the aggregated code pointer.
     * MUST be freed after use with freeCodePtr().
     */
    public int compileRaw(String jsCode) {
        if (!initialized) {
            throw new IllegalStateException("WASM runtime not initialized");
        }

        LOG.info("compileRaw: Starting compilation, code length: " + jsCode.length());

        byte[] jsBytes = jsCode.getBytes(StandardCharsets.UTF_8);
        int jsPtr = allocateMemory(jsBytes.length);
        LOG.info("compileRaw: Allocated memory at ptr: " + jsPtr);
        memory.write(jsPtr, jsBytes);

        try {
            com.dylibso.chicory.runtime.ExportFunction compileSrc = instance.export("compileSrc");
            if (compileSrc == null) {
                throw new RuntimeException("compileSrc function not found in WASM module");
            }

            // Clear output before compile
            clearOutput();

            LOG.info("compileRaw: Calling compileSrc WASM function");
            long[] result = compileSrc.apply(jsPtr, jsBytes.length);
            int aggregatedCodePtr = (int) result[0];
            LOG.info("compileRaw: compileSrc returned ptr: " + aggregatedCodePtr);

            // Check for compilation errors
            String compileError = getStderr();
            if (!compileError.isEmpty()) {
                LOG.error("JavaScript compilation error: " + compileError);
            }

            // Log the compiled code pointer for debugging
            if (aggregatedCodePtr != 0) {
                int bytecodePtr = memory.readInt(aggregatedCodePtr);
                int bytecodeLen = memory.readInt(aggregatedCodePtr + 4);
                LOG.info("compileRaw: Bytecode ptr=" + bytecodePtr + ", len=" + bytecodeLen);
            } else {
                LOG.error("Compilation returned null pointer");
            }

            return aggregatedCodePtr;
        } finally {
            freeMemory(jsPtr, jsBytes.length);
        }
    }

    /**
     * Compiles JavaScript source code to bytecode array.
     */
    public byte[] compile(String jsCode) {
        int codePtr = compileRaw(jsCode);
        try {
            int bytecodePtr = memory.readInt(codePtr);
            int bytecodeLen = memory.readInt(codePtr + 4);
            return memory.readBytes(bytecodePtr, bytecodeLen);
        } finally {
            freeCodePtr(codePtr);
        }
    }

    /**
     * Frees an aggregated code pointer and its bytecode.
     */
    private void freeCodePtr(int codePtr) {
        int bytecodePtr = memory.readInt(codePtr);
        int bytecodeLen = memory.readInt(codePtr + 4);
        freeMemory(bytecodePtr, bytecodeLen);
    }

    /**
     * Executes compiled bytecode from an aggregated code pointer.
     * This matches the quickjs4j pattern exactly.
     * Includes timeout protection to prevent infinite loops from blocking the
     * server.
     */
    public void exec(int codePtr) {
        if (!initialized) {
            throw new IllegalStateException("WASM runtime not initialized");
        }

        LOG.info("exec: Reading bytecode from codePtr: " + codePtr);

        // Read pointer and length from aggregated pointer
        int ptr = memory.readInt(codePtr);
        int codeLength = memory.readInt(codePtr + 4);

        LOG.info("exec: Bytecode ptr=" + ptr + ", len=" + codeLength);

        com.dylibso.chicory.runtime.ExportFunction invokeFunc = instance.export("invoke");
        if (invokeFunc == null) {
            throw new RuntimeException("invoke function not found in WASM module");
        }

        // Clear output before execution
        clearOutput();

        // Execute with timeout protection
        // This prevents infinite loops from blocking the server indefinitely
        final long EXECUTION_TIMEOUT_MS = 10000; // 10 seconds
        final int bytecodePtr = ptr;
        final int bytecodeLen = codeLength;

        Thread executionThread = Thread.currentThread();
        final boolean[] completed = { false };

        // Create a watchdog timer to interrupt if execution takes too long
        Thread watchdog = new Thread(() -> {
            try {
                Thread.sleep(EXECUTION_TIMEOUT_MS);
                if (!completed[0]) {
                    LOG.warn("exec: WASM execution timeout! Interrupting after " + EXECUTION_TIMEOUT_MS + "ms");
                    executionThread.interrupt();
                }
            } catch (InterruptedException ignored) {
                // Watchdog was cancelled, execution completed in time
            }
        }, "WASM-Watchdog");
        watchdog.setDaemon(true);
        watchdog.start();

        try {
            LOG.info("exec: Calling invoke WASM function NOW (timeout: " + EXECUTION_TIMEOUT_MS + "ms)");
            invokeFunc.apply(
                    bytecodePtr, // bytecode_ptr
                    bytecodeLen, // bytecode_len
                    0, // fn_name_ptr (0 for default)
                    0 // fn_name_len
            );
            completed[0] = true;
            LOG.info("exec: invoke completed successfully");

            // Check for output after execution
            String execStderr = getStderr();
            if (!execStderr.isEmpty()) {
                LOG.info("exec: WASM stderr: " + execStderr);
            }
            String execStdout = getStdout();
            if (!execStdout.isEmpty()) {
                LOG.info("exec: WASM stdout: " + execStdout);
            }
        } catch (Exception e) {
            completed[0] = true;
            if (Thread.interrupted() || e.getMessage() != null && e.getMessage().contains("interrupted")) {
                LOG.error("exec: WASM execution was interrupted due to timeout!");
                throw new RuntimeException("WASM execution timed out after " + EXECUTION_TIMEOUT_MS +
                        "ms - possible infinite loop detected. Script execution was terminated.", e);
            }
            LOG.error("exec: WASM invoke failed!", e);
            // Capture stderr to get the actual JS error
            String execStderr = getStderr();
            if (!execStderr.isEmpty()) {
                LOG.error("exec: WASM stderr on error: " + execStderr);
                throw new RuntimeException("WASM execution failed: " + execStderr, e);
            }
            throw e;
        } finally {
            completed[0] = true;
            watchdog.interrupt(); // Cancel the watchdog
        }
    }

    /**
     * Executes compiled bytecode.
     */
    public void exec(byte[] bytecode) {
        if (!initialized) {
            throw new IllegalStateException("WASM runtime not initialized");
        }

        // Write bytecode to memory
        int bytecodePtr = allocateMemory(bytecode.length);
        memory.write(bytecodePtr, bytecode);

        // Create wide pointer
        int widePtr = allocateMemory(8);
        memory.writeI32(widePtr, bytecodePtr);
        memory.writeI32(widePtr + 4, bytecode.length);

        try {
            com.dylibso.chicory.runtime.ExportFunction invokeFunc = instance.export("invoke");
            if (invokeFunc == null) {
                throw new RuntimeException("invoke function not found in WASM module");
            }

            invokeFunc.apply(bytecodePtr, bytecode.length, 0, 0);
        } finally {
            freeMemory(bytecodePtr, bytecode.length);
            freeMemory(widePtr, 8);
        }
    }

    /**
     * Evaluates JavaScript code directly.
     */
    public Object evalJs(String script) {
        if (!initialized) {
            LOG.warn("WASM runtime not initialized. Cannot evaluate JavaScript.");
            return null;
        }

        clearOutput();
        int codePtr = 0;

        try {
            // Build the full script with prelude
            String fullScript = buildFullScript(script);

            // Log the first 500 chars of script for debugging
            LOG.info("Compiling JavaScript (first 500 chars): " +
                    (fullScript.length() > 500 ? fullScript.substring(0, 500) + "..." : fullScript));

            // Compile and execute
            codePtr = compileRaw(fullScript);
            LOG.info("Compiled script to codePtr: " + codePtr);

            exec(codePtr);

            // Check for errors in stderr
            String error = getStderr();
            if (!error.isEmpty()) {
                LOG.error("JavaScript error: " + error);
                throw new RuntimeException("JavaScript error: " + error);
            }

            // The result should be in stdout or captured via host function
            String output = getStdout();
            if (!output.isEmpty()) {
                return parseJsonResult(output.trim());
            }

            return null;
        } catch (Exception e) {
            LOG.error("Error evaluating JavaScript: " + e.getMessage(), e);
            throw new RuntimeException("JavaScript evaluation failed: " + e.getMessage(), e);
        } finally {
            if (codePtr != 0) {
                freeCodePtr(codePtr);
            }
        }
    }

    /**
     * Builds the full JavaScript with prelude defining __host_call wrapper.
     * The java_invoke function is provided by the Javy WASM plugin as a global
     * function.
     */
    private String buildFullScript(String userScript) {
        StringBuilder sb = new StringBuilder();

        // Define __host_call as wrapper around java_invoke
        // java_invoke signature: java_invoke(moduleName, funcName, argsJson) ->
        // resultJson
        sb.append("var __host_call = function(funcName, args) {\n");
        sb.append("  try {\n");
        sb.append("    var result = java_invoke('wso2', funcName, JSON.stringify(args || []));\n");
        sb.append("    return result ? JSON.parse(result) : null;\n");
        sb.append("  } catch (e) {\n");
        sb.append("    return null;\n");
        sb.append("  }\n");
        sb.append("};\n\n");

        // Add user script
        sb.append(userScript);

        return sb.toString();
    }

    /**
     * Parses a JSON result string into a Java object.
     */
    private Object parseJsonResult(String json) {
        if (json == null || json.isEmpty() || json.equals("null") || json.equals("undefined")) {
            return null;
        }

        try {
            JsonElement element = JsonParser.parseString(json);
            if (element.isJsonNull()) {
                return null;
            } else if (element.isJsonPrimitive()) {
                if (element.getAsJsonPrimitive().isBoolean()) {
                    return element.getAsBoolean();
                } else if (element.getAsJsonPrimitive().isNumber()) {
                    return element.getAsNumber();
                } else {
                    return element.getAsString();
                }
            } else if (element.isJsonObject()) {
                return GSON.fromJson(element, Map.class);
            } else if (element.isJsonArray()) {
                return GSON.fromJson(element, java.util.List.class);
            }
            return json;
        } catch (Exception e) {
            return json;
        }
    }

    /**
     * Gets the stdout output from the WASM execution.
     */
    public String getStdout() {
        try {
            stdout.flush();
        } catch (IOException e) {
            // ignore
        }
        return stdout.toString(StandardCharsets.UTF_8);
    }

    /**
     * Gets the stderr output from the WASM execution.
     */
    public String getStderr() {
        try {
            stderr.flush();
        } catch (IOException e) {
            // ignore
        }
        return stderr.toString(StandardCharsets.UTF_8);
    }

    /**
     * Clears the stdout and stderr buffers.
     */
    private void clearOutput() {
        stdout.reset();
        stderr.reset();
    }

    /**
     * Checks if the runtime is properly initialized.
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Invokes a callback function registered in the JavaScript runtime.
     * This calls the __invokeCallback function defined in the JavaScript prelude.
     *
     * @param callbackId The unique ID of the callback to invoke.
     * @param params     The parameters to pass to the callback function.
     * @return The result of the callback execution.
     */
    public Object invokeCallback(String callbackId, Object... params) {
        if (!initialized) {
            LOG.warn("WASM runtime not initialized. Cannot invoke callback.");
            return null;
        }

        if (callbackId == null || callbackId.isEmpty()) {
            LOG.error("Cannot invoke callback: callback ID is null or empty");
            return null;
        }

        LOG.info("invokeCallback: Invoking callback with ID: " + callbackId);

        // Build script to invoke the callback
        StringBuilder script = new StringBuilder();

        // Serialize the parameters to JSON
        String argsJson = "[]";
        if (params != null && params.length > 0) {
            try {
                argsJson = GSON.toJson(params);
            } catch (StackOverflowError e) {
                LOG.warn("Circular reference detected in callback params, using empty array");
                argsJson = "[]";
            } catch (Exception e) {
                LOG.warn("Error serializing callback params: " + e.getMessage());
                argsJson = "[]";
            }
        }

        // Call __invokeCallback with the callback ID and args
        script.append("__invokeCallback('").append(callbackId).append("', '");
        script.append(escapeJsString(argsJson)).append("');\n");

        try {
            // Execute the callback invocation script
            // Note: We don't use evalJs here because it rebuilds the prelude and loses the
            // registry
            // Instead, we compile and execute directly
            clearOutput();
            int codePtr = compileRaw(script.toString());
            exec(codePtr);
            freeCodePtr(codePtr);

            String output = getStdout();
            String error = getStderr();

            if (!error.isEmpty()) {
                LOG.error("Callback invocation error: " + error);
            }

            if (!output.isEmpty()) {
                Object result = parseJsonResult(output.trim());
                if (result instanceof Map) {
                    Map<?, ?> resultMap = (Map<?, ?>) result;
                    if (resultMap.containsKey("error")) {
                        LOG.error("Callback execution error: " + resultMap.get("error"));
                        return null;
                    }
                    return resultMap.get("result");
                }
                return result;
            }

            return null;
        } catch (Exception e) {
            LOG.error("Error invoking callback: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Escapes a string for safe inclusion in JavaScript.
     */
    private String escapeJsString(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    @Override
    public void close() {
        if (wasi != null) {
            wasi.close();
        }
        hostFunctions.clear();
        instance = null;
        memory = null;
        initialized = false;
    }
}
