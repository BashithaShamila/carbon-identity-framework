# Architecture Reference

## Current System Layout

```
┌─────────────── IS SIDE ────────────────┐     ┌──── SIDECAR ────┐
│                                        │     │                  │
│  Adaptive Auth Script Context          │     │  GraalVM Engine  │
│         │                              │     │       │          │
│  RemoteJsEngine (1720 lines)           │     │  JsEngineService │
│    ├─ evaluate()                       │     │    (1689 lines)  │
│    ├─ invokeHostFunction()             │     │    ├─ deserialize │
│    ├─ adaptSingleArgument() ◄──┐       │     │    ├─ serialize  │
│    ├─ getContextProperty()     │       │     │    ├─ DynamicCtx │
│    ├─ getProxyObjectProperty() │       │     │    │   Proxy     │
│    └─ setContextProperty()     │       │     │    └─ HostFunc   │
│         │                      │       │     │       Stub       │
│  GraalSerializer (167 lines)   │       │     │                  │
│    └─ toJsSerializableInternal │       │     └──────────────────┘
│         │                      │       │
│  ProtobufSerializer (435 lines)│       │
│    ├─ toProto()  ──────────────┘       │
│    ├─ fromProto()                      │
│    └─ shouldUseProxyPattern()          │
│         │                              │
│  GrpcStreamingTransportImpl (818 lines)│
│    ├─ handleHostFunctionRequest()      │
│    ├─ handleContextPropertyRequest()   │
│    └─ handleContextPropertySetRequest()│
└────────────────────────────────────────┘
```

## Conversion Logic Locations (5 files, 13 sites)

| What | Where | Lines |
|---|---|---|
| Java → Proto serialization | `ProtobufSerializer.toProto()` | L131–340 |
| Pre-conversion (GraalVM types) | `GraalSerializer.toJsSerializableInternal()` | L56–127 |
| Proto → Java deserialization | `ProtobufSerializer.fromProto()` | L348–402 |
| Argument type coercion (IS) | `RemoteJsEngine.adaptSingleArgument()` | L1408–1499 |
| GraalVM Value → Proto (sidecar) | `JsEngineServiceImpl.serializeValue()` | L925–983 |
| Proto → GraalVM Value (sidecar) | `JsEngineServiceImpl.deserializeValue()` | L985–1048 |
| GraalVM Value → Java (sidecar) | `HostFunctionStub.convertToJava()` | L1168–1294 |
| Property path navigation (IS) | `RemoteJsEngine.getContextProperty()` + `getProxyObjectProperty()` + `getHostRefProperty()` | L699–928 (~230 lines, near-duplicate) |
| Property path setting (IS) | `RemoteJsEngine.setContextProperty()` + `setHostRefProperty()` | L938–1165 (~227 lines, near-duplicate) |
| Proxy type detection | `ProtobufSerializer.shouldUseProxyPattern()` + `GrpcStreamingTransportImpl.isProxyType()` | 2 places, different patterns |

## File Summary

- **RemoteJsEngine.java** — 1720 lines, IS side orchestrator. Contains adaptSingleArgument,
  all path navigation, and invokeHostFunction dispatch.
- **JsEngineServiceImpl.java** — 1689 lines, sidecar orchestrator. Contains serialize/deserialize,
  DynamicContextProxy, HostFunctionStub.
- **ProtobufSerializer.java** — 435 lines. toProto / fromProto / shouldUseProxyPattern /
  ThreadLocal proxy cache lifecycle.
- **GraalSerializer.java** — 167 lines. Pre-serialization GraalVM type normalization.
- **GrpcStreamingTransportImpl.java** — 818 lines. Stream mechanics + conversion (to be slimmed).
