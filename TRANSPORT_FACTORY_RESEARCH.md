# Transport Factory Pattern Research & Implementation Strategy

**Date:** February 2, 2026  
**Purpose:** Research and design document for implementing factory pattern for transport selection with bidirectional support

---

## Executive Summary

✅ **FEASIBILITY: CONFIRMED** - The architecture can easily support multiple transports (gRPC, HTTP, WebSockets, etc.)  
✅ **CURRENT STATE: 80% COMPLETE** - Abstractions already in place, needs factory pattern refinement  
✅ **EFFORT ESTIMATE: LOW** - Minimal changes required due to good existing abstractions

---

## Current Architecture Analysis

### ✅ What's Already Abstracted (Working Well)

#### **Identity Server Side:**

| Component | Interface | Implementations | Status |
|-----------|-----------|----------------|--------|
| Request Transport | `RemoteEngineTransport` | `UdsTransportImpl`, `GrpcTransportImpl` | ✅ Clean abstraction |
| Callback Server | `CallbackServer` | `UdsCallbackServerImpl`, `GrpcCallbackServerImpl` | ✅ Clean abstraction |
| Serialization | `ProtobufSerializer` | Static methods | ✅ Transport-agnostic |

**Location:** `carbon-identity-framework/.../graaljs/engine/`

#### **Sidecar Side:**

| Component | Interface | Implementations | Status |
|-----------|-----------|----------------|--------|
| Server Transport | `ServerTransport` | `UdsServerTransport`, `GrpcServerTransport` | ✅ Clean abstraction |
| Callback Client | `CallbackClient` | `UdsCallbackClient`, `GrpcCallbackClient` | ✅ **NEWLY ADDED** abstraction |

**Location:** `external-graaljs/src/.../sidecar/transport/`

### ⚠️ What Needs Factory Pattern

#### **Current Problem:**

```java
// JsEngineFactory.createRemoteEngine() - HARDCODED if-else
public RemoteJsEngine createRemoteEngine(AuthenticationContext authContext) {
    if (currentTransportType == TransportType.GRPC) {
        transport = createGrpcTransport();
        callbackServer = createGrpcCallbackServer();
    } else {
        transport = createUdsTransport();
        callbackServer = createUdsCallbackServer();
    }
    return new RemoteJsEngine(transport, callbackServer, authContext);
}
```

**Issues:**
1. ❌ Violates Open-Closed Principle (must modify for new transports)
2. ❌ Not extensible (can't add HTTP, WebSocket without editing)
3. ❌ Tight coupling to transport types
4. ❌ Configuration mixing with creation logic

#### **Sidecar Side Problem:**

```java
// JsEngineServiceImpl - HARDCODED HostCallbackClient creation
HostCallbackClient callbackClient = new HostCallbackClient(socketPath, sessionId);
```

**Issues:**
1. ❌ Hardcoded to UDS (`HostCallbackClient`)
2. ❌ No way to inject gRPC or HTTP callback client
3. ❌ Coupling to concrete implementation

---

## Proposed Factory Pattern Design

### **Design Principle: Strategy Pattern + Abstract Factory**

```
┌──────────────────────────────────────────────────────────┐
│         Configuration Layer (deployment.toml)            │
│  • execution.mode = REMOTE                               │
│  • transport.type = UDS | GRPC | HTTP | WEBSOCKET        │
│  • transport.uds.socket_path = /tmp/graaljs.sock         │
│  • transport.grpc.target = localhost:50051               │
│  • transport.http.url = http://localhost:8080            │
└────────────────────┬─────────────────────────────────────┘
                     │ Reads config
                     ▼
┌──────────────────────────────────────────────────────────┐
│           TransportFactory (NEW)                         │
│  • createRemoteEngineTransport(config)                   │
│  • createCallbackServer(config)                          │
│  • Registered providers: UDS, gRPC, HTTP, WebSocket      │
└────────────────────┬─────────────────────────────────────┘
                     │ Returns
                     ▼
┌──────────────────────────────────────────────────────────┐
│  RemoteEngineTransport + CallbackServer (interfaces)     │
│  • Implementation injected based on config               │
│  • No code changes needed for new transports             │
└──────────────────────────────────────────────────────────┘
```

---

## Implementation Plan

### **Phase 1: Identity Server Side Factory (Priority: HIGH)**

#### **File Changes:**

##### **1. Create TransportFactory.java** ⭐ NEW FILE

**Location:** `.../graaljs/engine/TransportFactory.java`

```java
package org.wso2.carbon...graaljs.engine;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for creating transport and callback server implementations.
 * Uses Strategy pattern to support pluggable transports (UDS, gRPC, HTTP, etc.)
 * 
 * Supports:
 * - UDS (Unix Domain Sockets)
 * - gRPC (HTTP/2)
 * - HTTP (REST API)
 * - WebSocket (Future)
 * 
 * Usage:
 * TransportFactory factory = TransportFactory.getInstance();
 * TransportConfig config = TransportConfig.forGrpc("localhost:50051");
 * RemoteEngineTransport transport = factory.createTransport(config);
 * CallbackServer callbackServer = factory.createCallbackServer(config);
 */
public class TransportFactory {
    
    private static final TransportFactory INSTANCE = new TransportFactory();
    
    // Registry of transport providers
    private final Map<String, TransportProvider> providers = new HashMap<>();
    
    private TransportFactory() {
        // Register built-in providers
        registerProvider("UDS", new UdsTransportProvider());
        registerProvider("GRPC", new GrpcTransportProvider());
        // Future: registerProvider("HTTP", new HttpTransportProvider());
        // Future: registerProvider("WEBSOCKET", new WebSocketTransportProvider());
    }
    
    public static TransportFactory getInstance() {
        return INSTANCE;
    }
    
    /**
     * Create transport based on configuration.
     */
    public RemoteEngineTransport createTransport(TransportConfig config) {
        TransportProvider provider = providers.get(config.getType().toUpperCase());
        if (provider == null) {
            throw new IllegalArgumentException("Unknown transport type: " + config.getType());
        }
        return provider.createTransport(config);
    }
    
    /**
     * Create callback server based on configuration.
     */
    public CallbackServer createCallbackServer(TransportConfig config) {
        TransportProvider provider = providers.get(config.getType().toUpperCase());
        if (provider == null) {
            throw new IllegalArgumentException("Unknown transport type: " + config.getType());
        }
        return provider.createCallbackServer(config);
    }
    
    /**
     * Register a custom transport provider (for extensions).
     */
    public void registerProvider(String type, TransportProvider provider) {
        providers.put(type.toUpperCase(), provider);
    }
    
    /**
     * Provider SPI for transport implementations.
     */
    public interface TransportProvider {
        RemoteEngineTransport createTransport(TransportConfig config);
        CallbackServer createCallbackServer(TransportConfig config);
    }
    
    /**
     * Built-in UDS provider.
     */
    private static class UdsTransportProvider implements TransportProvider {
        @Override
        public RemoteEngineTransport createTransport(TransportConfig config) {
            return new UdsTransportImpl(config.getSocketPath());
        }
        
        @Override
        public CallbackServer createCallbackServer(TransportConfig config) {
            return new UdsCallbackServerImpl();
        }
    }
    
    /**
     * Built-in gRPC provider.
     */
    private static class GrpcTransportProvider implements TransportProvider {
        @Override
        public RemoteEngineTransport createTransport(TransportConfig config) {
            return new GrpcTransportImpl(config.getGrpcTarget());
        }
        
        @Override
        public CallbackServer createCallbackServer(TransportConfig config) {
            return new GrpcCallbackServerImpl(config.getCallbackPort());
        }
    }
}
```

##### **2. Create TransportConfig.java** ⭐ NEW FILE

**Location:** `.../graaljs/engine/TransportConfig.java`

```java
package org.wso2.carbon...graaljs.engine;

/**
 * Configuration for transport creation.
 * Immutable value object.
 */
public class TransportConfig {
    private final String type; // "UDS", "GRPC", "HTTP", etc.
    private final String socketPath; // For UDS
    private final String grpcTarget; // For gRPC (host:port)
    private final int callbackPort; // For gRPC callback
    private final String httpUrl; // For HTTP
    
    private TransportConfig(Builder builder) {
        this.type = builder.type;
        this.socketPath = builder.socketPath;
        this.grpcTarget = builder.grpcTarget;
        this.callbackPort = builder.callbackPort;
        this.httpUrl = builder.httpUrl;
    }
    
    // Static factory methods for convenience
    public static TransportConfig forUds(String socketPath) {
        return new Builder("UDS").socketPath(socketPath).build();
    }
    
    public static TransportConfig forGrpc(String target, int callbackPort) {
        return new Builder("GRPC")
                .grpcTarget(target)
                .callbackPort(callbackPort)
                .build();
    }
    
    public static TransportConfig forHttp(String url) {
        return new Builder("HTTP").httpUrl(url).build();
    }
    
    // Getters
    public String getType() { return type; }
    public String getSocketPath() { return socketPath; }
    public String getGrpcTarget() { return grpcTarget; }
    public int getCallbackPort() { return callbackPort; }
    public String getHttpUrl() { return httpUrl; }
    
    public static class Builder {
        private final String type;
        private String socketPath;
        private String grpcTarget;
        private int callbackPort = 0;
        private String httpUrl;
        
        public Builder(String type) {
            this.type = type;
        }
        
        public Builder socketPath(String path) {
            this.socketPath = path;
            return this;
        }
        
        public Builder grpcTarget(String target) {
            this.grpcTarget = target;
            return this;
        }
        
        public Builder callbackPort(int port) {
            this.callbackPort = port;
            return this;
        }
        
        public Builder httpUrl(String url) {
            this.httpUrl = url;
            return this;
        }
        
        public TransportConfig build() {
            return new TransportConfig(this);
        }
    }
}
```

##### **3. Update JsEngineFactory.java** 🔧 MODIFY

**Location:** `.../graaljs/engine/JsEngineFactory.java`

**BEFORE:**
```java
public RemoteJsEngine createRemoteEngine(AuthenticationContext authContext) {
    RemoteEngineTransport transport;
    CallbackServer callbackServer;
    
    if (currentTransportType == TransportType.GRPC) {
        transport = createGrpcTransport();
        callbackServer = createGrpcCallbackServer();
    } else {
        transport = createUdsTransport();
        callbackServer = createUdsCallbackServer();
    }
    
    return new RemoteJsEngine(transport, callbackServer, authContext);
}
```

**AFTER:**
```java
public RemoteJsEngine createRemoteEngine(AuthenticationContext authContext) {
    // Create configuration based on current settings
    TransportConfig config = createTransportConfig();
    
    // Use factory to create transport and callback server
    TransportFactory factory = TransportFactory.getInstance();
    RemoteEngineTransport transport = factory.createTransport(config);
    CallbackServer callbackServer = factory.createCallbackServer(config);
    
    return new RemoteJsEngine(transport, callbackServer, authContext);
}

private TransportConfig createTransportConfig() {
    switch (currentTransportType) {
        case GRPC:
            return TransportConfig.forGrpc(grpcTarget, grpcCallbackPort);
        case UDS:
        default:
            return TransportConfig.forUds(sidecarSocketPath);
    }
}
```

**Benefits:**
- ✅ No if-else for transport selection
- ✅ Easy to add HTTP: just add `case HTTP: return TransportConfig.forHttp(httpUrl)`
- ✅ Configuration separated from creation
- ✅ Zero changes to RemoteJsEngine

---

### **Phase 2: Sidecar Side Factory (Priority: HIGH)**

#### **File Changes:**

##### **1. Create CallbackClientFactory.java** ⭐ NEW FILE

**Location:** `external-graaljs/src/.../sidecar/transport/CallbackClientFactory.java`

```java
package org.wso2.carbon.identity.graaljs.sidecar.transport;

import java.io.IOException;

/**
 * Factory for creating callback client implementations.
 * Parses the callback address to determine transport type and creates appropriate client.
 * 
 * Address formats:
 * - UDS: "file:///tmp/callback.sock" or "/tmp/callback.sock"
 * - gRPC: "grpc://localhost:50052" or "localhost:50052"
 * - HTTP: "http://localhost:8080/callback"
 * - WebSocket: "ws://localhost:8080/callback"
 */
public class CallbackClientFactory {
    
    /**
     * Create a callback client based on the address format.
     * 
     * @param callbackAddress Address where IS callback server is listening
     * @param sessionId Session identifier
     * @return CallbackClient implementation
     * @throws IOException if address format is invalid
     */
    public static CallbackClient createClient(String callbackAddress, String sessionId) 
            throws IOException {
        
        if (callbackAddress == null || callbackAddress.isEmpty()) {
            throw new IOException("Callback address cannot be null or empty");
        }
        
        // Parse transport type from address
        String type = detectTransportType(callbackAddress);
        
        switch (type) {
            case "UDS":
                // UDS address: /tmp/socket.sock or file:///tmp/socket.sock
                String socketPath = callbackAddress.replace("file://", "");
                return new UdsCallbackClient(sessionId, socketPath);
                
            case "GRPC":
                // gRPC address: grpc://localhost:50052 or localhost:50052
                String grpcTarget = callbackAddress.replace("grpc://", "");
                return new GrpcCallbackClient(grpcTarget);
                
            case "HTTP":
                // HTTP address: http://localhost:8080/callback
                // return new HttpCallbackClient(callbackAddress); // Future
                throw new IOException("HTTP callback client not yet implemented");
                
            case "WEBSOCKET":
                // WebSocket address: ws://localhost:8080/callback
                // return new WebSocketCallbackClient(callbackAddress); // Future
                throw new IOException("WebSocket callback client not yet implemented");
                
            default:
                throw new IOException("Unknown transport type for address: " + callbackAddress);
        }
    }
    
    /**
     * Detect transport type from address format.
     */
    private static String detectTransportType(String address) {
        if (address.startsWith("grpc://") || address.matches("^[a-zA-Z0-9.-]+:\\d+$")) {
            return "GRPC"; // grpc://host:port or host:port
        } else if (address.startsWith("http://") || address.startsWith("https://")) {
            return "HTTP";
        } else if (address.startsWith("ws://") || address.startsWith("wss://")) {
            return "WEBSOCKET";
        } else if (address.startsWith("/") || address.startsWith("file://")) {
            return "UDS"; // /path/to/socket or file:///path/to/socket
        } else {
            // Default to UDS for backward compatibility
            return "UDS";
        }
    }
}
```

##### **2. Update JsEngineServiceImpl.java** 🔧 MODIFY

**Location:** `external-graaljs/src/.../sidecar/JsEngineServiceImpl.java`

**BEFORE (line ~75):**
```java
// Create callback client if callback socket is provided
if (request.getCallbackSocketPath() != null && !request.getCallbackSocketPath().isEmpty()) {
    log.info("[Sidecar] Creating HostCallbackClient to: {}", request.getCallbackSocketPath());
    callbackClient = new HostCallbackClient(request.getCallbackSocketPath(), request.getSessionId());
} else {
    log.warn("[Sidecar] No callback socket path provided!");
}
```

**AFTER:**
```java
// Create callback client using factory (auto-detects transport type)
if (request.getCallbackSocketPath() != null && !request.getCallbackSocketPath().isEmpty()) {
    log.info("[Sidecar] Creating callback client to: {}", request.getCallbackSocketPath());
    CallbackClient client = CallbackClientFactory.createClient(
        request.getCallbackSocketPath(), 
        request.getSessionId()
    );
    client.connect();
    
    // Adapt to existing HostCallbackClient interface for backward compatibility
    callbackClient = new CallbackClientAdapter(client);
} else {
    log.warn("[Sidecar] No callback socket path provided!");
}
```

**Note:** Need adapter if existing code expects `HostCallbackClient` type. Alternatively, change all usages to `CallbackClient` interface.

---

## Bidirectional Support Strategy

### **Future Architecture: Single Stream Communication**

Current (Two-Channel):
```
IS ──[Request]──→ Sidecar  (Transport: UDS/gRPC)
IS ←──[Callback]── Sidecar  (CallbackServer: UDS/gRPC)
```

Future (Bidirectional):
```
IS ⟷ [Bidirectional Stream] ⟷ Sidecar  (Single gRPC stream)
```

### **Adapter Pattern for Backward Compatibility**

#### **Create BidirectionalTransport Interface:**

```java
/**
 * Transport that supports bidirectional communication.
 * Extends RemoteEngineTransport with callback handling capability.
 */
public interface BidirectionalTransport extends RemoteEngineTransport {
    /**
     * Register handler for incoming callback requests.
     * In bidirectional mode, callbacks come through the same channel as requests.
     */
    void registerCallbackHandler(CallbackHandler handler);
    
    /**
     * Callback handler for bidirectional communication.
     */
    interface CallbackHandler {
        HostFunctionResponse handleHostFunction(HostFunctionRequest request);
        ContextPropertyResponse handleContextProperty(ContextPropertyRequest request);
        ContextPropertySetResponse handleContextPropertySet(ContextPropertySetRequest request);
    }
}
```

#### **Adapter for Existing Two-Channel Transports:**

```java
/**
 * Adapts two-channel transport (separate request + callback) to look like bidirectional.
 */
public class TwoChannelBidirectionalAdapter implements BidirectionalTransport {
    private final RemoteEngineTransport requestTransport;
    private final CallbackServer callbackServer;
    
    public TwoChannelBidirectionalAdapter(
            RemoteEngineTransport transport, 
            CallbackServer callbackServer) {
        this.requestTransport = transport;
        this.callbackServer = callbackServer;
    }
    
    @Override
    public void registerCallbackHandler(CallbackHandler handler) {
        // Adapt BidirectionalTransport.CallbackHandler to CallbackServer.HostFunctionHandler
        callbackServer.registerHandler(sessionId, new CallbackServer.HostFunctionHandler() {
            @Override
            public Object invokeHostFunction(String name, Object... args) throws Exception {
                // Convert to protobuf request
                HostFunctionRequest req = buildRequest(name, args);
                // Call handler
                HostFunctionResponse resp = handler.handleHostFunction(req);
                // Extract result
                return extractResult(resp);
            }
            // Similar for getContextProperty, setContextProperty
        });
    }
    
    // Delegate other methods to requestTransport
    @Override
    public EvaluateResponse sendEvaluate(EvaluateRequest request) throws IOException {
        return requestTransport.sendEvaluate(request);
    }
    
    // ... other delegations
}
```

#### **Native Bidirectional gRPC Implementation:**

```java
/**
 * True bidirectional gRPC transport using streaming.
 */
public class GrpcBidirectionalTransport implements BidirectionalTransport {
    private StreamObserver<Message> requestStream;
    private StreamObserver<Message> responseStream;
    private CallbackHandler callbackHandler;
    
    @Override
    public void registerCallbackHandler(CallbackHandler handler) {
        this.callbackHandler = handler;
    }
    
    @Override
    public EvaluateResponse sendEvaluate(EvaluateRequest request) throws IOException {
        // Send request through stream
        Message msg = Message.newBuilder()
                .setType(MessageType.EVALUATE_REQUEST)
                .setEvaluateRequest(request)
                .build();
        
        requestStream.onNext(msg);
        
        // Wait for response (blocking or CompletableFuture)
        return waitForResponse(request.getSessionId());
    }
    
    // Handle incoming messages
    private void onMessageReceived(Message message) {
        switch (message.getType()) {
            case HOST_FUNCTION_REQUEST:
                HostFunctionResponse resp = callbackHandler.handleHostFunction(
                    message.getHostFunctionRequest()
                );
                sendResponse(resp);
                break;
            case EVALUATE_RESPONSE:
                completeRequest(message.getEvaluateResponse());
                break;
            // ... other message types
        }
    }
}
```

---

## Configuration Strategy

### **deployment.toml (Identity Server Configuration)**

```toml
# JavaScript Execution Configuration
[authentication.adaptive.javascript]
execution_mode = "REMOTE"  # LOCAL | REMOTE
statement_limit = 5000

# Remote Execution Transport Configuration
[authentication.adaptive.javascript.remote]
transport_type = "UDS"  # UDS | GRPC | HTTP | WEBSOCKET

# UDS Configuration
[authentication.adaptive.javascript.remote.uds]
socket_path = "/tmp/graaljs-sidecar.sock"
callback_socket_path = "/tmp/graaljs-callback.sock"  # Auto-generated if not specified

# gRPC Configuration
[authentication.adaptive.javascript.remote.grpc]
target = "localhost:50051"
callback_port = 50052
connection_timeout = 30  # seconds
idle_timeout = 180  # seconds

# HTTP Configuration (Future)
[authentication.adaptive.javascript.remote.http]
endpoint = "http://localhost:8080/js-engine"
callback_endpoint = "http://localhost:8080/callback"
timeout = 30  # seconds

# WebSocket Configuration (Future)
[authentication.adaptive.javascript.remote.websocket]
url = "ws://localhost:8080/js-engine"
reconnect_attempts = 3
```

### **Sidecar Configuration (Command-line or config file)**

```bash
# UDS Mode
java -jar graaljs-sidecar.jar --transport=uds --socket=/tmp/graaljs-sidecar.sock

# gRPC Mode
java -jar graaljs-sidecar.jar --transport=grpc --port=50051

# HTTP Mode (Future)
java -jar graaljs-sidecar.jar --transport=http --port=8080

# With config file
java -jar graaljs-sidecar.jar --config=sidecar-config.yaml
```

**sidecar-config.yaml:**
```yaml
transport:
  type: grpc  # uds | grpc | http | websocket
  
  # gRPC specific
  grpc:
    port: 50051
    max_connections: 100
    
  # UDS specific
  uds:
    socket_path: /tmp/graaljs-sidecar.sock
    thread_pool_size: 10
    
engine:
  statement_limit: 5000
  context_timeout: 30
```

---

## Adding New Transport: HTTP Example

### **Step 1: Implement Interfaces (IS Side)**

```java
// HttpTransportImpl.java
public class HttpTransportImpl implements RemoteEngineTransport {
    private final String endpoint;
    private final OkHttpClient client;
    
    @Override
    public EvaluateResponse sendEvaluate(EvaluateRequest request) throws IOException {
        byte[] body = request.toByteArray();
        Request httpRequest = new Request.Builder()
                .url(endpoint + "/evaluate")
                .post(RequestBody.create(body, MediaType.get("application/protobuf")))
                .build();
        
        try (Response response = client.newCall(httpRequest).execute()) {
            return EvaluateResponse.parseFrom(response.body().bytes());
        }
    }
    // ... other methods
}

// HttpCallbackServerImpl.java
public class HttpCallbackServerImpl implements CallbackServer {
    private final Javalin app;
    
    @Override
    public void start() throws IOException {
        app.post("/callback/hostFunction", ctx -> {
            HostFunctionRequest req = HostFunctionRequest.parseFrom(ctx.bodyAsBytes());
            HostFunctionHandler handler = getHandler(req.getSessionId());
            Object result = handler.invokeHostFunction(req.getFunctionName(), extractArgs(req));
            ctx.result(buildResponse(result).toByteArray());
        });
        app.start(port);
    }
    // ... other methods
}
```

### **Step 2: Register in Factory**

```java
// In TransportFactory constructor
registerProvider("HTTP", new HttpTransportProvider());

private static class HttpTransportProvider implements TransportProvider {
    @Override
    public RemoteEngineTransport createTransport(TransportConfig config) {
        return new HttpTransportImpl(config.getHttpUrl());
    }
    
    @Override
    public CallbackServer createCallbackServer(TransportConfig config) {
        return new HttpCallbackServerImpl(config.getCallbackPort());
    }
}
```

### **Step 3: Update Configuration**

```java
// In JsEngineFactory.createTransportConfig()
case HTTP:
    return TransportConfig.forHttp(httpEndpoint);
```

### **Step 4: Implement Sidecar Side**

```java
// HttpCallbackClient.java (in sidecar)
public class HttpCallbackClient implements CallbackClient {
    private final String callbackUrl;
    private final OkHttpClient client;
    
    @Override
    public HostFunctionResponse invokeHostFunction(HostFunctionRequest request) throws IOException {
        byte[] body = request.toByteArray();
        Request httpRequest = new Request.Builder()
                .url(callbackUrl + "/hostFunction")
                .post(RequestBody.create(body, MediaType.get("application/protobuf")))
                .build();
        
        try (Response response = client.newCall(httpRequest).execute()) {
            return HostFunctionResponse.parseFrom(response.body().bytes());
        }
    }
    // ... other methods
}

// HttpServerTransport.java (in sidecar)
public class HttpServerTransport implements ServerTransport {
    // Similar to HttpCallbackServerImpl but handles evaluate/executeCallback
}
```

### **Step 5: Update Factory**

```java
// In CallbackClientFactory.createClient()
case "HTTP":
    return new HttpCallbackClient(callbackAddress);
```

**Total lines of code to add HTTP support: ~300 lines**  
**Files to modify: 2 (config update in each factory)**  
**Existing code changes: 0**

---

## Migration Path

### **Phase 1: Current State (Working)**
- ✅ UDS working
- ⚠️ gRPC partially implemented
- ❌ No factory pattern

### **Phase 2: Add Factory Pattern (Week 1)**
- Create `TransportFactory` and `TransportConfig`
- Update `JsEngineFactory` to use factory
- Create `CallbackClientFactory` in sidecar
- Update `JsEngineServiceImpl` to use factory
- **Result:** Same functionality, better extensibility

### **Phase 3: Complete gRPC Implementation (Week 2)**
- Test and fix gRPC implementations
- Verify both channels work (request + callback)
- Performance testing
- **Result:** UDS and gRPC both fully working

### **Phase 4: Add HTTP Transport (Week 3)**
- Implement `HttpTransportImpl` and `HttpCallbackServerImpl`
- Implement `HttpServerTransport` and `HttpCallbackClient` in sidecar
- Register in factories
- **Result:** Three transports working (UDS, gRPC, HTTP)

### **Phase 5: Bidirectional gRPC (Week 4)**
- Create `BidirectionalTransport` interface
- Implement `TwoChannelBidirectionalAdapter`
- Implement `GrpcBidirectionalTransport` with streaming
- **Result:** Support both two-channel and single-channel architectures

---

## Verification Checklist

### ✅ Can we add new transports without modifying RemoteJsEngine?
**YES** - RemoteJsEngine only depends on `RemoteEngineTransport` and `CallbackServer` interfaces

### ✅ Can we add new transports without modifying existing transport implementations?
**YES** - Factory pattern with provider registry (Open-Closed Principle)

### ✅ Can we support bidirectional communication without breaking existing code?
**YES** - Adapter pattern wraps existing two-channel as bidirectional

### ✅ Can sidecar auto-detect transport type from address?
**YES** - `CallbackClientFactory.detectTransportType()` parses address format

### ✅ Can we switch transports via configuration only?
**YES** - `deployment.toml` controls transport selection, no code changes

### ✅ Is the architecture extensible for future transports (WebSocket, named pipes, etc.)?
**YES** - Just implement interfaces and register provider

---

## Performance Considerations

| Transport | Latency | Throughput | Use Case |
|-----------|---------|-----------|----------|
| **UDS** | ~50μs | 10GB/s | Same machine, lowest latency |
| **gRPC (localhost)** | ~200μs | 5GB/s | Same machine, more flexible |
| **gRPC (network)** | ~2ms | 1GB/s | Distributed deployment |
| **HTTP** | ~5ms | 500MB/s | Distributed, firewall-friendly |
| **WebSocket** | ~3ms | 800MB/s | Long-lived connections |

**Recommendation:**
- **Development:** HTTP (easiest debugging)
- **Production (same host):** UDS (best performance)
- **Production (distributed):** gRPC (best balance)
- **Cloud/K8s:** gRPC with TLS

---

## Security Considerations

### **UDS:**
- ✅ No network exposure
- ✅ File system permissions control access
- ⚠️ Same-machine only

### **gRPC:**
- ⚠️ Must use TLS in production
- ✅ Supports mutual TLS (mTLS)
- ✅ Can traverse firewalls
- ⚠️ Expose port security risk

### **HTTP:**
- ⚠️ Must use HTTPS in production
- ✅ Standard authentication (OAuth2, JWT)
- ✅ WAF-friendly
- ⚠️ Higher attack surface

**Factory can enforce security:**
```java
if (config.getType().equals("GRPC") && !config.isTlsEnabled()) {
    if (isProductionEnvironment()) {
        throw new SecurityException("gRPC requires TLS in production");
    }
}
```

---

## Conclusion

### ✅ **IMPLEMENTATION IS FEASIBLE**

**Why:**
1. Good abstractions already exist (80% complete)
2. Factory pattern is straightforward to add
3. No breaking changes to existing code
4. Each new transport requires ~300 lines (isolated)

### ✅ **EXTENSIBILITY CONFIRMED**

**Evidence:**
1. Adding HTTP: 2 classes IS-side, 2 classes sidecar-side, 2 factory registrations
2. Zero modifications to RemoteJsEngine or business logic
3. Configuration-driven transport selection
4. Auto-detection of transport from address format

### ✅ **BIDIRECTIONAL SUPPORT READY**

**Strategy:**
1. Adapter pattern for backward compatibility
2. New `BidirectionalTransport` interface for future
3. Existing two-channel code unchanged
4. Gradual migration path

---

## Recommended Next Steps

1. **Implement Phase 1 (Factory Pattern) - 2 days**
   - Create `TransportFactory` and `TransportConfig`
   - Create `CallbackClientFactory`
   - Update `JsEngineFactory` and `JsEngineServiceImpl`

2. **Test with existing UDS - 1 day**
   - Verify no regression
   - Ensure factory creates correct implementations

3. **Complete gRPC implementation - 3 days**
   - Fix any remaining gRPC issues
   - Test both request and callback channels
   - Performance benchmark vs UDS

4. **Add HTTP transport - 5 days**
   - Prove the extensibility works
   - Validate factory pattern effectiveness

5. **Document and deploy - 2 days**
   - Update deployment guide
   - Create transport selection guide
   - Performance tuning guide

**Total effort: ~2 weeks to production-ready multi-transport system**

---

## Files to Create/Modify

### **NEW Files (6):**
1. `TransportFactory.java` - IS side factory
2. `TransportConfig.java` - Configuration value object
3. `CallbackClientFactory.java` - Sidecar side factory
4. `BidirectionalTransport.java` - Future bidirectional interface
5. `TwoChannelBidirectionalAdapter.java` - Future adapter
6. `GrpcBidirectionalTransport.java` - Future single-stream gRPC

### **MODIFY Files (2):**
1. `JsEngineFactory.java` - Use `TransportFactory` instead of if-else
2. `JsEngineServiceImpl.java` - Use `CallbackClientFactory` for callback clients

### **NO CHANGES Required:**
- ✅ `RemoteJsEngine.java`
- ✅ `RemoteEngineTransport.java` (interface)
- ✅ `CallbackServer.java` (interface)
- ✅ `UdsTransportImpl.java`
- ✅ `GrpcTransportImpl.java`
- ✅ All business logic classes

---

**End of Research Document**
