# Edge Cases Reference

These 13 edge cases span both IS and sidecar. Any refactor that relocates conversion logic
must verify all 13 still hold. Use this as a checklist before and after each migration.

## The 13 Edge Cases

| # | Edge Case | Where | Why It Exists |
|---|---|---|---|
| 1 | `__JsGraalAuthenticationContext_placeholder__` | RemoteJsEngine L415, JsEngineServiceImpl L354 | Context object can't be serialized — replaced with magic string marker on both sides |
| 2 | `__proxyref__::uuid::property path` format | RemoteJsEngine L704, JsEngineServiceImpl L1030 | Lazy proxy pattern for large objects (100+ users); avoids serializing entire graph |
| 3 | `__hostref__::uuid::property path` format | RemoteJsEngine L709, JsEngineServiceImpl L1115 | Host function return value lazy access; value is held on IS side, accessed by path |
| 4 | Double→Integer coercion | RemoteJsEngine `adaptSingleArgument()` | JS Number is always Double; Java methods that expect Integer fail without explicit cast |
| 5 | varargs null filtering | RemoteJsEngine `adaptVarArgsMethod()` L1335 | JS `undefined`→`null` in varargs breaks Java method dispatch |
| 6 | ProxyArray index access | RemoteJsEngine L742–759 | Numeric path segments need `.get(index)` not `.getMember()`; mixing them throws |
| 7 | OSGi classloader instanceof fallback | RemoteJsEngine L974–982 | `instanceof` fails across OSGi bundles; must fall back to class-name string comparison |
| 8 | ThreadLocal proxy cache lifecycle | ProtobufSerializer L60–78, GrpcStreamingTransportImpl L531–548 | Must `set()` before serialize, `clear()` after, or cache leaks across thread-pool reuse |
| 9 | Bean introspection fallback | ProtobufSerializer L307–332 | When proxy pattern not applicable, reflects all getters to serialize — slow but correct |
| 10 | AbstractJSObjectWrapper unwrapping | ProtobufSerializer L257–268 | HTTP response bodies are wrapped in JSObject; must unwrap before serialization |
| 11 | Class name pattern matching for proxy | ProtobufSerializer L97–100 (`"User"`, `"model."`, `"domain."`) | Heuristic to decide lazy vs eager serialization; substring-based and fragile |
| 12 | `__keys__` special property | RemoteJsEngine L732, GrpcStreamingTransportImpl L620 | `Object.keys()` in JS triggers member-key enumeration; needs dedicated branch |
| 13 | DynamicContextProxy cache with ConcurrentHashMap | JsEngineServiceImpl (sidecar) | `ConcurrentHashMap` cannot store null values; null must be treated as a cache miss |

## Per-Phase Risk Mapping

### Phase 1 (Constants extraction)
- Affects: all 13 (string values referenced everywhere)
- Risk mitigation: search-and-replace only; values don't change

### Phase 2 (PropertyPathNavigator)
- Primarily affects: #2, #3, #6, #12
- Must preserve: prefix detection order, numeric index branch, `__keys__` branch

### Phase 3 (ProxyTypeResolver)
- Primarily affects: #11
- Opportunity: replace substring heuristic with explicit registry (see magic-strings.md)

### Phase 4 (StreamMessageAdapter)
- Affects: #1, #4, #5, #8, #9, #10
- ThreadLocal lifecycle (#8) must move as a unit — never split set/clear across classes

### Phase 5 (Sidecar ValueConverter)
- Affects: #1, #2, #3, #7, #13
- OSGi fallback (#7) must stay in IS side; sidecar doesn't have this constraint

## Testing Checklist After Each Phase

- [ ] Context placeholder round-trips correctly (edge case #1)
- [ ] Large user list triggers lazy proxy, not full serialization (edge case #2)
- [ ] Host function return value accessible by path (edge case #3)
- [ ] Integer-typed Java method callable from JS with numeric argument (edge case #4)
- [ ] Varargs method callable with undefined/null JS arguments (edge case #5)
- [ ] Array element accessible by numeric path segment (edge case #6)
- [ ] Cross-bundle instanceof check doesn't throw ClassCastException (edge case #7)
- [ ] No ThreadLocal leaks under thread pool reuse (edge case #8)
- [ ] Non-proxy object serializes via bean introspection fallback (edge case #9)
- [ ] HTTP response body accessible from JS context (edge case #10)
- [ ] User/model/domain objects use proxy pattern (edge case #11)
- [ ] `Object.keys()` works on context proxy (edge case #12)
- [ ] Null return from host function doesn't poison the DynamicContextProxy cache (edge case #13)
