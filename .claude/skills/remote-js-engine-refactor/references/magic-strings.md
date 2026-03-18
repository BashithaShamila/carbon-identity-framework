# Magic Strings — Industry Alternatives

## Complete Magic String Inventory

| Magic String | Purpose | IS | Sidecar |
|---|---|---|---|
| `__JsGraalAuthenticationContext_placeholder__` | Non-serializable type marker | ✓ | ✓ |
| `__proxyref__::uuid::property` | Lazy proxy object reference path | ✓ | ✓ |
| `__hostref__::uuid::property` | Host function return reference path | ✓ | ✓ |
| `__keys__` | Member key enumeration trigger | ✓ | ✓ |
| `__isContextProxy` | Proxy metadata field in serialized objects | | ✓ |
| `__proxyType` | Proxy type identifier field | | ✓ |
| `__basePath` | Proxy base path field | | ✓ |
| `__isHostRef` | Host reference detection marker | | ✓ |
| `__hostRefId` | Host reference ID field | | ✓ |
| `__callbackContext` | Context binding key in GraalVM scope | | ✓ |
| `context` | Binding key excluded from serialization | ✓ | ✓ |
| `::` | Property path separator | ✓ | ✓ |
| `streaming://` | Transport type indicator | ✓ | |
| `"User"`, `"model."`, `"domain."` | Class name heuristics for proxy eligibility | ✓ | |
| `pojo` | Proxy type identifier value | ✓ | ✓ |

## Industry Alternatives — Ranked by Fit

### Option 1: Protocol Buffers oneof/enum (Best long-term for gRPC projects)

**Used by**: Google internal services, Envoy, gRPC ecosystem

Extend the proto contract rather than encode type info as magic strings:

```protobuf
// Instead of magic string "__proxyref__::uuid-123::username"
message PropertyAccessRequest {
  oneof target {
    string context_path = 1;        // "steps::0::subject"
    ProxyReference proxy_ref = 2;   // replaces __proxyref__
    HostReference  host_ref  = 3;   // replaces __hostref__
  }
  repeated string property_chain = 4; // ["username"] instead of "::" joined
}

message ProxyReference { string reference_id = 1; }
message HostReference  { string reference_id = 1; }

// Instead of "__JsGraalAuthenticationContext_placeholder__"
message SerializedValue {
  oneof value {
    // ... existing types ...
    TypePlaceholder placeholder = 10;
  }
}

message TypePlaceholder {
  PlaceholderType type       = 1;
  string          reference_id = 2;
}

enum PlaceholderType {
  AUTHENTICATION_CONTEXT = 0;
  PROXY_OBJECT           = 1;
  HOST_FUNCTION_RETURN   = 2;
}
```

**Pros**: Both IS and sidecar compile from the same `.proto` — contract stays in sync by
definition. No string parsing. `repeated string` replaces `::` splitting. Backward compatible.

**Risk**: Medium — requires proto schema change, regeneration, updates on both sides.

**When to choose**: This is the right long-term answer because you already use protobuf.
Magic strings exist because the current schema wasn't designed to express these concepts.

---

### Option 2: Shared Constants Module (Cheapest, do now)

**Used by**: Apache Kafka (protocol constants), Spring Framework (header names)

```java
// shared-constants artifact, depended on by both IS and sidecar
public enum PropertyPathPrefix {
    PROXY_REF("__proxyref__"),
    HOST_REF("__hostref__"),
    CONTEXT("context");

    private final String value;
    PropertyPathPrefix(String value) { this.value = value; }

    public static PropertyPathPrefix fromPath(String path) {
        for (PropertyPathPrefix p : values()) {
            if (path.startsWith(p.value)) return p;
        }
        return CONTEXT;
    }
}
```

**Pros**: Cheapest change. Both sides compile against same enum. Immediate readability win.

**Cons**: Still encoding enum variants as strings on the wire. Drift possible if one side
updates the JAR version and the other doesn't.

**Risk**: Very low — pure extraction, no behavior change.

**When to choose**: Do this as Phase 1 regardless of later direction.

---

### Option 3: Typed Reference System

**Used by**: Java RMI (remote object stubs), CORBA (IOR references),
gRPC service mesh (xDS resource references), Microsoft Orleans (grain references)

```java
public abstract class RemoteReference {
    private final String id;
    private final ReferenceType type;

    public enum ReferenceType {
        PROXY_OBJECT,   // cached Java object on IS side
        HOST_RETURN,    // host function return value
        CONTEXT_PATH    // navigation from auth context root
    }
}

public class ProxyObjectReference extends RemoteReference {
    private final String   objectId;       // UUID
    private final String[] propertyChain;  // ["username"] not "::username"
}
```

This is what your `__proxyref__` system already *is* — just encoded as strings.
Formalizing it as typed objects makes intent explicit and eliminates parsing.

**Risk**: Medium — need to update serialization on both sides, but logic stays the same.

---

### Option 4: Well-Known Type Registry / Annotation

**Used by**: Protobuf `google.protobuf.Any`, Kubernetes API (GVK), JSON-LD (`@type`)

Replaces the fragile class-name substring heuristics (`"User"`, `"model."`, `"domain."`):

```java
// Registry approach (low risk)
public class ProxyTypeRegistry {
    private static final Set<String> PROXY_ELIGIBLE = Set.of(
        "org.wso2.carbon.user.core.common.User",
        "org.wso2.carbon.identity.application.common.model.ClaimMapping"
        // explicit list — no surprises from new classes named "UserPreference"
    );

    public static boolean shouldProxy(Object obj) {
        return PROXY_ELIGIBLE.contains(obj.getClass().getName());
    }
}

// Annotation approach (medium risk)
@RemoteProxyEligible  // scanned at startup
public class User { ... }
```

**Why needed**: The current substring matching silently proxies any class whose name
contains "User", "model.", or "domain.". A new class `UserPreferenceDTO` would be
incorrectly proxied. Explicit registration removes the guesswork.

**Risk**: Low (registry), Medium (annotation scanning).

---

## Recommended Sequence

| When | Action |
|---|---|
| Now (Phase 1) | Shared constants — extract all 15+ strings into `RemoteEngineConstants.java` |
| With refactor (Phase 2–4) | Extend proto schema — replace `__proxyref__`/`__hostref__` with typed oneof; `TypePlaceholder` for context placeholder; `repeated string` for path |
| Later (Phase 3) | Type registry for proxy eligibility instead of substring matching |
