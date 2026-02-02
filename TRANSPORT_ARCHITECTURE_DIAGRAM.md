# Transport Architecture - Visual Diagrams

## Current State: Two-Channel Architecture with Hardcoded Selection

```
┌─────────────────────────────────────────────────────────────────────────┐
│                         Identity Server (IS)                            │
│                                                                         │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                    JsEngineFactory                                │  │
│  │                                                                   │  │
│  │  ❌ PROBLEM: Hardcoded if-else                                   │  │
│  │                                                                   │  │
│  │  if (transportType == GRPC) {                                    │  │
│  │      transport = new GrpcTransportImpl(...)                      │  │
│  │      callbackServer = new GrpcCallbackServerImpl(...)            │  │
│  │  } else {                                                         │  │
│  │      transport = new UdsTransportImpl(...)                       │  │
│  │      callbackServer = new UdsCallbackServerImpl(...)             │  │
│  │  }                                                                │  │
│  │                                                                   │  │
│  │  ❌ Adding HTTP/WebSocket requires modifying this code           │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                             │                                           │
│                             ▼                                           │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                    RemoteJsEngine                                 │  │
│  │                 (Already Clean ✅)                                │  │
│  │                                                                   │  │
│  │  RemoteEngineTransport transport;  // Interface ✅                │  │
│  │  CallbackServer callbackServer;    // Interface ✅                │  │
│  │                                                                   │  │
│  │  // Business logic only, no transport coupling                   │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│         │                                          │                    │
│         │ Channel 1: Requests                      │ Channel 2:         │
│         │ (Evaluate, ExecuteCallback)              │ Callbacks          │
│         ▼                                          ▼                    │
└─────────┼──────────────────────────────────────────┼────────────────────┘
          │                                          │
          │ UDS/gRPC                                 │ UDS/gRPC
          │                                          │
┌─────────▼──────────────────────────────────────────▼────────────────────┐
│                       GraalJS Sidecar                                   │
│                                                                         │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                  ServerTransport                                  │  │
│  │              (Already Clean ✅)                                   │  │
│  │                                                                   │  │
│  │  UdsServerTransport | GrpcServerTransport                        │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                             │                                           │
│                             ▼                                           │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                 JsEngineServiceImpl                               │  │
│  │                                                                   │  │
│  │  ❌ PROBLEM: Hardcoded HostCallbackClient                        │  │
│  │                                                                   │  │
│  │  callbackClient = new HostCallbackClient(socketPath, sessionId); │  │
│  │                                                                   │  │
│  │  ❌ Always creates UDS client, can't use gRPC/HTTP               │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                             │                                           │
│                             │ Callbacks to IS                           │
│                             │                                           │
└─────────────────────────────┼───────────────────────────────────────────┘
                              │
                              │ Hardcoded UDS
                              │
                        [Back to IS]
```

---

## Proposed State: Factory Pattern with Pluggable Transports

```
┌─────────────────────────────────────────────────────────────────────────┐
│                         Identity Server (IS)                            │
│                                                                         │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │            Configuration (deployment.toml)                        │  │
│  │                                                                   │  │
│  │  [adaptive.javascript.remote]                                    │  │
│  │  transport_type = "GRPC"  # UDS | GRPC | HTTP | WEBSOCKET        │  │
│  │                                                                   │  │
│  │  [adaptive.javascript.remote.grpc]                               │  │
│  │  target = "localhost:50051"                                      │  │
│  │  callback_port = 50052                                           │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                             │ reads config                              │
│                             ▼                                           │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                 TransportFactory ⭐ NEW                          │  │
│  │                                                                   │  │
│  │  Registry: {                                                     │  │
│  │    "UDS"   → UdsTransportProvider                               │  │
│  │    "GRPC"  → GrpcTransportProvider                              │  │
│  │    "HTTP"  → HttpTransportProvider  // Easy to add ✅           │  │
│  │  }                                                                │  │
│  │                                                                   │  │
│  │  createTransport(config) {                                       │  │
│  │    provider = registry.get(config.type)                          │  │
│  │    return provider.createTransport(config)                       │  │
│  │  }                                                                │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                             │                                           │
│                             ▼                                           │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                   JsEngineFactory                                 │  │
│  │                                                                   │  │
│  │  ✅ CLEAN: No if-else chains                                     │  │
│  │                                                                   │  │
│  │  TransportConfig config = createConfig();                        │  │
│  │  TransportFactory factory = TransportFactory.getInstance();      │  │
│  │                                                                   │  │
│  │  RemoteEngineTransport transport =                               │  │
│  │      factory.createTransport(config);                            │  │
│  │  CallbackServer callbackServer =                                 │  │
│  │      factory.createCallbackServer(config);                       │  │
│  │                                                                   │  │
│  │  return new RemoteJsEngine(transport, callbackServer, ...);      │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                             │                                           │
│                             ▼                                           │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                    RemoteJsEngine                                 │  │
│  │                 (No Changes ✅)                                   │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│         │                                          │                    │
│         │ Request Channel                          │ Callback Channel   │
│         ▼                                          ▼                    │
└─────────┼──────────────────────────────────────────┼────────────────────┘
          │                                          │
          │ Any Transport: UDS/gRPC/HTTP/WebSocket   │
          │                                          │
┌─────────▼──────────────────────────────────────────▼────────────────────┐
│                       GraalJS Sidecar                                   │
│                                                                         │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │              ServerTransport (No Changes ✅)                      │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                             │                                           │
│                             ▼                                           │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │              CallbackClientFactory ⭐ NEW                        │  │
│  │                                                                   │  │
│  │  detectTransportType(address) {                                  │  │
│  │    if (address.startsWith("grpc://")) return GRPC               │  │
│  │    if (address.startsWith("http://")) return HTTP               │  │
│  │    if (address.startsWith("/")) return UDS                       │  │
│  │  }                                                                │  │
│  │                                                                   │  │
│  │  createClient(address, sessionId) {                              │  │
│  │    switch(detectTransportType(address)) {                        │  │
│  │      case UDS:  return new UdsCallbackClient(...)               │  │
│  │      case GRPC: return new GrpcCallbackClient(...)              │  │
│  │      case HTTP: return new HttpCallbackClient(...)  ✅           │  │
│  │    }                                                              │  │
│  │  }                                                                │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                             │                                           │
│                             ▼                                           │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                  JsEngineServiceImpl                              │  │
│  │                                                                   │  │
│  │  ✅ CLEAN: Uses factory                                          │  │
│  │                                                                   │  │
│  │  CallbackClient client =                                         │  │
│  │      CallbackClientFactory.createClient(                         │  │
│  │          request.getCallbackSocketPath(),                        │  │
│  │          request.getSessionId()                                  │  │
│  │      );                                                           │  │
│  │  client.connect();                                               │  │
│  │                                                                   │  │
│  │  // Works with any transport type! ✅                            │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Transport Provider Architecture

```
┌────────────────────────────────────────────────────────────────────┐
│                     TransportFactory                               │
│                                                                    │
│  ┌──────────────────────────────────────────────────────────────┐ │
│  │  Provider Registry (Map<String, TransportProvider>)          │ │
│  │                                                               │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │ │
│  │  │    "UDS"    │  │   "GRPC"    │  │   "HTTP"    │  ...    │ │
│  │  │      │      │  │      │      │  │      │      │         │ │
│  │  │      ▼      │  │      ▼      │  │      ▼      │         │ │
│  │  │  UdsProvider│  │ GrpcProvider│  │ HttpProvider│         │ │
│  │  └─────────────┘  └─────────────┘  └─────────────┘         │ │
│  └──────────────────────────────────────────────────────────────┘ │
│                                                                    │
│  createTransport(config) → calls provider.createTransport(config) │
│  createCallbackServer(config) → calls provider.createCallbackServer()│
└────────────────────────────────────────────────────────────────────┘
                                │
            ┌───────────────────┴───────────────────┐
            │                                       │
            ▼                                       ▼
┌──────────────────────┐              ┌──────────────────────┐
│  TransportProvider   │              │  TransportProvider   │
│     Interface        │              │     Interface        │
│                      │              │                      │
│  • createTransport   │              │  • createTransport   │
│  • createCallback    │              │  • createCallback    │
│    Server            │              │    Server            │
└──────────────────────┘              └──────────────────────┘
            │                                       │
    ┌───────┴────────┐                    ┌────────┴────────┐
    │                │                    │                 │
    ▼                ▼                    ▼                 ▼
┌──────────┐  ┌──────────────┐    ┌──────────┐  ┌──────────────┐
│   Uds    │  │     Uds      │    │  Grpc    │  │    Grpc      │
│Transport │  │CallbackServer│    │Transport │  │CallbackServer│
│   Impl   │  │     Impl     │    │   Impl   │  │     Impl     │
└──────────┘  └──────────────┘    └──────────┘  └──────────────┘
```

---

## Address-Based Auto-Detection Flow

```
┌─────────────────────────────────────────────────────────────────┐
│            Callback Address from IS Request                     │
│                                                                 │
│  Examples:                                                      │
│    • /tmp/graaljs-callback.sock                                │
│    • grpc://localhost:50052                                    │
│    • http://localhost:8080/callback                            │
│    • ws://localhost:8080/callback                              │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│         CallbackClientFactory.detectTransportType()             │
│                                                                 │
│  if (address.startsWith("grpc://") || address.matches(hostport))│
│      return "GRPC"                                              │
│                                                                 │
│  else if (address.startsWith("http://"))                       │
│      return "HTTP"                                              │
│                                                                 │
│  else if (address.startsWith("ws://"))                         │
│      return "WEBSOCKET"                                         │
│                                                                 │
│  else if (address.startsWith("/") || address.startsWith("file://"))│
│      return "UDS"                                               │
│                                                                 │
│  else                                                           │
│      return "UDS"  // default for backward compatibility       │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│          CallbackClientFactory.createClient()                   │
│                                                                 │
│  switch (type) {                                                │
│    case "UDS":                                                  │
│      return new UdsCallbackClient(socketPath, sessionId)       │
│                                                                 │
│    case "GRPC":                                                 │
│      return new GrpcCallbackClient(grpcTarget)                 │
│                                                                 │
│    case "HTTP":                                                 │
│      return new HttpCallbackClient(httpUrl)                    │
│                                                                 │
│    case "WEBSOCKET":                                            │
│      return new WebSocketCallbackClient(wsUrl)                 │
│  }                                                              │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│              CallbackClient Interface                           │
│                                                                 │
│  • invokeHostFunction(request)                                 │
│  • getContextProperty(request)                                 │
│  • setContextProperty(request)                                 │
│  • connect()                                                    │
│  • isConnected()                                                │
│  • close()                                                      │
└─────────────────────────────────────────────────────────────────┘
```

---

## Adding New Transport: Step-by-Step

```
┌─────────────────────────────────────────────────────────────────┐
│  STEP 1: Implement IS-Side Interfaces                          │
│                                                                 │
│  class HttpTransportImpl implements RemoteEngineTransport {    │
│    // Implement sendEvaluate(), sendExecuteCallback(), etc.    │
│  }                                                              │
│                                                                 │
│  class HttpCallbackServerImpl implements CallbackServer {      │
│    // Implement start(), registerHandler(), etc.               │
│  }                                                              │
└─────────────────────────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│  STEP 2: Create Provider and Register                          │
│                                                                 │
│  class HttpTransportProvider implements TransportProvider {    │
│    RemoteEngineTransport createTransport(config) { ... }       │
│    CallbackServer createCallbackServer(config) { ... }         │
│  }                                                              │
│                                                                 │
│  // In TransportFactory constructor:                           │
│  registerProvider("HTTP", new HttpTransportProvider());        │
└─────────────────────────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│  STEP 3: Update Configuration Support                          │
│                                                                 │
│  // In JsEngineFactory.createTransportConfig():                │
│  case HTTP:                                                     │
│    return TransportConfig.forHttp(httpEndpoint);               │
└─────────────────────────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│  STEP 4: Implement Sidecar-Side                                │
│                                                                 │
│  class HttpServerTransport implements ServerTransport {        │
│    // Implement start(), stop(), etc.                          │
│  }                                                              │
│                                                                 │
│  class HttpCallbackClient implements CallbackClient {          │
│    // Implement invokeHostFunction(), etc.                     │
│  }                                                              │
└─────────────────────────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│  STEP 5: Register in Sidecar Factory                           │
│                                                                 │
│  // In CallbackClientFactory.createClient():                   │
│  case "HTTP":                                                   │
│    return new HttpCallbackClient(callbackAddress);             │
│                                                                 │
│  // In Main.parseArgsAndStart():                               │
│  else if ("http".equals(transport)) {                          │
│    startHttp(port, ...);                                       │
│  }                                                              │
└─────────────────────────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│  DONE! No changes to RemoteJsEngine or business logic ✅       │
└─────────────────────────────────────────────────────────────────┘
```

---

## Bidirectional vs Two-Channel Architecture

### **Current: Two-Channel (Separate connections)**

```
┌─────────────────────────────────────────────────────────────────┐
│                      Identity Server                            │
│                                                                 │
│  ┌────────────────┐                    ┌──────────────────┐    │
│  │    Request     │ ───────────────────→ Receive Callbacks │    │
│  │   Transport    │     Channel 1       │   (Callback      │    │
│  │                │    (UDS/gRPC)       │    Server)       │    │
│  │                │                     │                  │    │
│  │                │                     │  Listening on    │    │
│  │                │                     │  separate socket/│    │
│  │                │                     │  port            │    │
│  └────────────────┘                    └──────────────────┘    │
└───────┬──────────────────────────────────────────┬──────────────┘
        │                                          │
        │ sendEvaluate()                           │ invokeHostFunction()
        │ sendExecuteCallback()                    │ getContextProperty()
        │                                          │
        ▼                                          ▲
┌─────────────────────────────────────────────────┴──────────┐
│                    GraalJS Sidecar                          │
│                                                             │
│  ┌──────────────┐                    ┌─────────────────┐   │
│  │   Server     │ ←─────────────────  Callback Client  │   │
│  │  Transport   │    Channel 1        │                 │   │
│  │              │                     │   Connects to   │   │
│  │  Listening   │                     │   IS callback   │   │
│  │  on socket/  │                     │   server on     │   │
│  │  port        │                     │   Channel 2     │   │
│  └──────────────┘                    └─────────────────┘   │
└─────────────────────────────────────────────────────────────┘

Pros:
✅ Simple to implement (separate concerns)
✅ Each channel can use different transport (UDS + UDS, gRPC + gRPC)
✅ No complex streaming logic

Cons:
❌ Two connections to manage
❌ Double the overhead (sockets, ports)
❌ More complex lifecycle management
```

### **Future: Bidirectional (Single stream)**

```
┌─────────────────────────────────────────────────────────────────┐
│                      Identity Server                            │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │         BidirectionalTransport                          │   │
│  │                                                          │   │
│  │  • sendEvaluate() ────────────────────────────────┐     │   │
│  │  • registerCallbackHandler() ◄────────────┐       │     │   │
│  │                                            │       │     │   │
│  │          Single gRPC Stream                │       │     │   │
│  │      (Bidirectional Messages)              │       │     │   │
│  └────────────────────────────────────────────┼───────┼─────┘   │
└───────────────────────────────────────────────┼───────┼─────────┘
                                                │       │
                                 Requests ──────┘       └─── Callbacks
                                 Responses ─────────────────→
                                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                    GraalJS Sidecar                              │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │         BidirectionalServerTransport                    │   │
│  │                                                          │   │
│  │  • receiveRequest() ────────────────────────────────┐   │   │
│  │  • sendCallback() ──────────────────────────────┐   │   │   │
│  │                                                  │   │   │   │
│  │          Single gRPC Stream                      │   │   │   │
│  │      (Bidirectional Messages)                    │   │   │   │
│  └──────────────────────────────────────────────────┼───┼───┘   │
└─────────────────────────────────────────────────────┼───┼───────┘
                                                      │   │
                              Receives Requests ◄─────┘   └────► Sends Callbacks

Pros:
✅ Single connection (less overhead)
✅ Simpler connection management
✅ Lower latency (no connection setup for callbacks)
✅ Better for high-frequency callback scenarios

Cons:
❌ More complex implementation (message routing)
❌ Requires streaming support (gRPC, WebSocket, HTTP/2)
❌ Not possible with simple HTTP REST
```

### **Best of Both: Adapter Pattern**

```
┌─────────────────────────────────────────────────────────────────┐
│  TwoChannelBidirectionalAdapter                                 │
│                                                                 │
│  Wraps existing:                                                │
│    • RemoteEngineTransport (requests)                           │
│    • CallbackServer (callbacks)                                 │
│                                                                 │
│  Implements:                                                    │
│    • BidirectionalTransport interface                           │
│                                                                 │
│  ┌────────────────────────┐   ┌──────────────────────────┐    │
│  │  sendEvaluate()        │──→│ transport.sendEvaluate() │    │
│  ├────────────────────────┤   └──────────────────────────┘    │
│  │                        │                                     │
│  │  registerCallback      │   ┌──────────────────────────┐    │
│  │  Handler()             │──→│ callbackServer.register  │    │
│  │                        │   │ Handler()                │    │
│  └────────────────────────┘   └──────────────────────────┘    │
│                                                                 │
│  Result: Two-channel transports work as bidirectional! ✅      │
└─────────────────────────────────────────────────────────────────┘
```

---

## Transport Comparison Matrix

```
┌─────────────┬──────────┬───────────┬──────────┬──────────┬────────────┐
│  Feature    │   UDS    │   gRPC    │   HTTP   │WebSocket │ Named Pipe │
├─────────────┼──────────┼───────────┼──────────┼──────────┼────────────┤
│ Latency     │ ~50μs    │  ~200μs   │  ~5ms    │  ~3ms    │  ~100μs    │
├─────────────┼──────────┼───────────┼──────────┼──────────┼────────────┤
│ Throughput  │ 10 GB/s  │  5 GB/s   │ 500 MB/s │ 800 MB/s │  8 GB/s    │
├─────────────┼──────────┼───────────┼──────────┼──────────┼────────────┤
│ Network?    │    ✗     │     ✓     │    ✓     │    ✓     │     ✗      │
├─────────────┼──────────┼───────────┼──────────┼──────────┼────────────┤
│ Firewall OK │    ✗     │     ✓     │    ✓     │    ✓     │     ✗      │
├─────────────┼──────────┼───────────┼──────────┼──────────┼────────────┤
│ Streaming   │    ✗     │     ✓     │    ✗     │    ✓     │     ✗      │
├─────────────┼──────────┼───────────┼──────────┼──────────┼────────────┤
│ Platform    │Unix/Linux│    All    │   All    │   All    │  Windows   │
├─────────────┼──────────┼───────────┼──────────┼──────────┼────────────┤
│ TLS Support │    ✗     │     ✓     │    ✓     │    ✓     │     ✗      │
├─────────────┼──────────┼───────────┼──────────┼──────────┼────────────┤
│ Debug Easy  │    ⚠️     │     ⚠️     │    ✓     │    ⚠️     │     ⚠️      │
├─────────────┼──────────┼───────────┼──────────┼──────────┼────────────┤
│ Production  │   Best   │   Best    │   Good   │   Good   │  Windows   │
│ (same host) │          │           │          │          │   Only     │
├─────────────┼──────────┼───────────┼──────────┼──────────┼────────────┤
│ Production  │    ✗     │   Best    │   Good   │   Good   │     ✗      │
│ (network)   │          │  (w/TLS)  │ (w/HTTPS)│ (w/WSS)  │            │
└─────────────┴──────────┴───────────┴──────────┴──────────┴────────────┘

Legend:
✓ = Supported
✗ = Not Supported
⚠️ = Requires special tools
```

---

## Deployment Scenarios

### **Scenario 1: Development (Local Machine)**

```
┌────────────────────────────────────────────────────┐
│             Developer Laptop                       │
│                                                    │
│  ┌───────────────┐       ┌─────────────────┐     │
│  │  IS Process   │  UDS  │ Sidecar Process │     │
│  │               │◄─────►│                 │     │
│  │  Port 9443    │       │  (No ports)     │     │
│  └───────────────┘       └─────────────────┘     │
│                                                    │
│  Recommended: UDS (fastest, simplest)              │
│  Alternative: HTTP (easier debugging)              │
└────────────────────────────────────────────────────┘
```

### **Scenario 2: Production (Same Host)**

```
┌────────────────────────────────────────────────────┐
│           Production Server                        │
│                                                    │
│  ┌───────────────┐       ┌─────────────────┐     │
│  │  IS Process   │  UDS  │ Sidecar Process │     │
│  │               │◄─────►│                 │     │
│  │  Port 9443    │       │  (No ports)     │     │
│  └───────────────┘       └─────────────────┘     │
│                                                    │
│  Recommended: UDS (best performance)               │
│  Alternative: gRPC (if need metrics/monitoring)    │
└────────────────────────────────────────────────────┘
```

### **Scenario 3: Distributed (Sidecar on Different Host)**

```
┌────────────────────────┐      ┌────────────────────────┐
│    IS Server           │      │   Sidecar Server       │
│                        │      │                        │
│  ┌─────────────────┐  │      │  ┌─────────────────┐  │
│  │   IS Process    │  │ gRPC │  │ Sidecar Process │  │
│  │                 ├──┼──────┼─►│                 │  │
│  │   Port 9443     │  │w/TLS │  │   Port 50051    │  │
│  └─────────────────┘  │      │  └─────────────────┘  │
│                        │      │                        │
│  192.168.1.10          │      │  192.168.1.20          │
└────────────────────────┘      └────────────────────────┘

Recommended: gRPC with mTLS
Alternative: HTTPS (if firewall restrictions)
```

### **Scenario 4: Kubernetes/Cloud**

```
┌──────────────────────────────────────────────────────────┐
│                    Kubernetes Cluster                     │
│                                                           │
│  ┌───────────────────────────────────────────────────┐   │
│  │              IS Pod                                │   │
│  │                                                    │   │
│  │  ┌──────────────┐         ┌──────────────┐       │   │
│  │  │ IS Container │  UDS    │ Sidecar      │       │   │
│  │  │              │◄───────►│ Container    │       │   │
│  │  │ Port 9443    │         │ (shared vol) │       │   │
│  │  └──────────────┘         └──────────────┘       │   │
│  │                                                    │   │
│  │  Shared: /var/run/graaljs-sidecar.sock            │   │
│  └───────────────────────────────────────────────────┘   │
│                                                           │
│  Recommended: UDS via shared volume (Kubernetes sidecar) │
│  Alternative: gRPC localhost (if no volume sharing)      │
└──────────────────────────────────────────────────────────┘
```

---

**End of Diagrams**
