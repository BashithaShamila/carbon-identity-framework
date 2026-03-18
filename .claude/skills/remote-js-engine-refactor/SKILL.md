---
name: remote-js-engine-refactor
description: >
  Expert guidance for safely refactoring cross-process JavaScript engine conversion layers,
  magic string protocols, and serialization/deserialization pipelines in Java/GraalVM systems.
  Use this skill whenever the user is working on: refactoring RemoteJsEngine, JsEngineServiceImpl,
  ProtobufSerializer, GrpcStreamingTransport, or similar cross-process type-conversion code;
  replacing magic strings / protocol markers with typed alternatives; analyzing edge cases in
  remote JS evaluation pipelines; designing StreamMessageAdapter, ValueConverter, or PropertyPathNavigator
  abstractions; or choosing between protobuf oneof, shared constants, typed reference systems, or
  type registries for cross-process contracts. Also trigger when the user mentions "__proxyref__",
  "__hostref__", sidecar GraalVM engines, adaptive authentication script contexts, or asks about
  safe incremental refactoring of large conversion-heavy codebases.
---

# Remote JS Engine Refactor Skill

You are an expert in safely refactoring cross-process Java/GraalVM JavaScript evaluation pipelines.
Your core principle: **no big-bang rewrites** — extract layers bottom-up without changing behavior.

## Architecture Overview

Read `references/architecture.md` for the full current architecture, file-by-file breakdown, and
the 13 critical edge cases that must be preserved during any refactor.

## Refactoring Phases

### Phase 1 — Extract Constants (Zero risk, ~1 day)

Create `RemoteEngineConstants.java` centralizing all magic strings:

```java
public final class RemoteEngineConstants {
    public static final String CONTEXT_PLACEHOLDER =
        "__JsGraalAuthenticationContext_placeholder__";
    public static final String PROXY_REF_PREFIX   = "__proxyref__";
    public static final String HOST_REF_PREFIX    = "__hostref__";
    public static final String KEYS_PROPERTY      = "__keys__";
    public static final String PATH_SEPARATOR     = "::";
    public static final String CONTEXT_BINDING_KEY = "context";
    public static final String STREAMING_PREFIX   = "streaming://";
    // ... all 15+ magic strings
}
```

**Why first**: Both IS and sidecar reference these. Centralizing them immediately surfaces
any drift between the two sides and makes subsequent phases dramatically safer.

### Phase 2 — PropertyPathNavigator (Low risk, 2–3 days)

Consolidate the 5 duplicated path navigation methods (~300 lines of near-duplicate code):

```java
public class PropertyPathNavigator {
    // Replaces: getContextProperty, getProxyObjectProperty, getHostRefProperty,
    //           setContextProperty, setHostRefProperty
    public Object resolveProperty(String fullPath, Object root,
                                  Map<String, Object> proxyCache) {
        // 1. Detect prefix (__proxyref__, __hostref__, or plain context path)
        // 2. Split by "::"
        // 3. Navigate ProxyObject / ProxyArray / Map / reflection
        // 4. Handle __keys__ enumeration
        // 5. Handle numeric array indices via .get(index) not .getMember()
    }
}
```

See `references/edge-cases.md` — items #2, #3, #6, #12 are all exercised in this navigator.

### Phase 3 — Unify Type Detection (Low risk, ~1 day)

```java
public class ProxyTypeResolver {
    // Consolidates: shouldUseProxyPattern() + isProxyType() + getProxyType()
    // Single source of truth for class-name patterns and proxy eligibility
}
```

See `references/magic-strings.md` for the industry comparison of alternatives
(proto oneof, shared enum module, typed reference system, type registry).

### Phase 4 — StreamMessageAdapter / HostCallbackAdapter (Medium risk, 3–5 days)

Separate "what goes on the wire" from "how it gets there":

```
RemoteJsEngine
  └── StreamMessageAdapter (NEW)
        ├── prepareEvaluateRequest(script, bindings, context)
        ├── prepareCallbackRequest(functionSource, args, bindings)
        ├── processEvaluateResponse(response)
        └── processCallbackResponse(response)
  └── HostCallbackAdapter (NEW)
        ├── handleHostFunction(request, handler)
        ├── handleContextProperty(request, handler)
        └── handleContextPropertySet(request, handler)
GrpcStreamingTransport — slimmed to stream mechanics only, no conversion
```

**Key constraint**: Migrate one method at a time with the existing test flow passing
after each move. Do not rewrite conversion logic — only relocate it.

### Phase 5 — Sidecar Adapter (Medium risk, 3–5 days, do last)

```
JsEngineServiceImpl (orchestration only)
  └── ValueConverter (NEW)
        ├── toGraalValue(SerializedValue, Context)
        ├── toProtoValue(GraalVM Value)
        └── toJavaObject(GraalVM Value)
  └── ProxyFactory (NEW)
        ├── createContextProxy(...)
        └── createHostFunctionStub(...)
```

Only start Phase 5 after IS-side phases are stable and tested.

## What NOT to Refactor

| Do Not Touch | Reason |
|---|---|
| `adaptSingleArgument()` / `adaptVarArgsMethod()` | 90+ lines of battle-tested Double→Integer coercion, OSGi classloader workarounds, null filtering. Extract as-is, never rewrite. |
| Proxy cache ThreadLocal lifecycle | The set-before / clear-after pattern in GrpcStreamingTransportImpl L531-548 is correct and fragile. Move as a unit. |
| `DynamicContextProxy` inner class | Deeply coupled to sidecar GraalVM context. Extract to its own file but do not change behavior. |
| `__proxyref__` / `__hostref__` prefix system | The lazy-loading pattern works. Just centralize the constants (Phase 1). |

## Magic String Replacement Strategy

When advising on replacing magic strings with typed alternatives, always compare options:

1. **Proto oneof/enum** (best long-term for gRPC projects) — extend the `.proto` schema so
   IS and sidecar share a compiled contract. Replaces string parsing with typed fields.
   Risk: Medium. Requires proto regeneration on both sides.

2. **Shared constants module** (cheapest, do now) — Java enum with `fromPath()` factory,
   shared as a JAR. Still strings on the wire but eliminates in-code duplication.
   Risk: Very low.

3. **Typed reference objects** — formalize `ProxyObjectReference`, `HostReference` etc.
   as typed classes instead of encoded strings. What the system already does, made explicit.
   Risk: Medium.

4. **Type registry / annotation** — replace class-name substring heuristics with an
   explicit registry or `@RemoteProxyEligible` annotation scanned at startup.
   Risk: Low (registry), Medium (annotation).

Read `references/magic-strings.md` for full comparison with industry examples.

## Edge Cases — Always Check Before Any Change

Before touching any conversion code, consult `references/edge-cases.md`.
The 13 edge cases span both IS and sidecar and several have subtle interdependencies.
Any refactor that moves conversion logic must verify all 13 still hold.

## Recommended Conversation Flow

1. Ask which phase the user wants to tackle (or which file they're in).
2. Surface the relevant edge cases from `references/edge-cases.md`.
3. Propose the extraction pattern with before/after code snippets.
4. Remind the user to run their existing manual test flow after each method migration.
5. Flag the "Do NOT refactor" items if the user approaches them.
