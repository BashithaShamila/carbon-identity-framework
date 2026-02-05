# Visual Communication Flow Diagrams

## Admin User Login - Complete Communication Flow

This document provides visual representations of the communication flow between WSO2 Identity Server and the externalized GraalJS sidecar during an admin user login.

---

## Overview: Two-Channel Architecture

```
┌────────────────────────────────────────────────────────────────────────────┐
│                                                                            │
│                        WSO2 Identity Server                                │
│                                                                            │
│  ┌─────────────────────────────────┐    ┌───────────────────────────┐    │
│  │      RemoteJsEngine             │    │  HostCallbackServer       │    │
│  │      + UdsClient                │    │  (Callback Receiver)      │    │
│  │                                 │    │                           │    │
│  │  Sessions:                      │    │  Listening:               │    │
│  │  • c114ae58...                  │    │  /tmp/graaljs-callback-   │    │
│  │  • 24706e5a...                  │    │  41ebd525.sock            │    │
│  │  • 446a38ef...                  │    │                           │    │
│  └────────────┬────────────────────┘    └────────────┬──────────────┘    │
│               │                                      │                    │
│               │ CHANNEL 1                            │ CHANNEL 2          │
│               │ (Request/Response)                   │ (Callbacks)        │
│               │                                      │                    │
└───────────────┼──────────────────────────────────────┼────────────────────┘
                │                                      │
                │ UDS Socket                           │ UDS Socket
                │ /tmp/graaljs-sidecar.sock           │ Connect Back
                │                                      │
┌───────────────▼──────────────────────────────────────▼────────────────────┐
│                                                                            │
│                        GraalJS Sidecar Process                             │
│                                                                            │
│  ┌─────────────────────────────────┐    ┌───────────────────────────┐    │
│  │   UdsServerTransport            │    │  HostCallbackClient       │    │
│  │   (Request Receiver)            │    │  (Callback Sender)        │    │
│  │                                 │    │                           │    │
│  │   Listening:                    │    │  Connects to:             │    │
│  │   /tmp/graaljs-sidecar.sock    │    │  /tmp/graaljs-callback-   │    │
│  │                                 │    │  41ebd525.sock            │    │
│  └────────────┬────────────────────┘    └────────────┬──────────────┘    │
│               │                                      │                    │
│               └──────────────┬───────────────────────┘                    │
│                              │                                            │
│                    ┌─────────▼─────────┐                                  │
│                    │ JsEngineService   │                                  │
│                    │ + GraalVM Context │                                  │
│                    │ (Heap: 512MB)     │                                  │
│                    └───────────────────┘                                  │
│                                                                            │
└────────────────────────────────────────────────────────────────────────────┘
```

---

## Phase 1: Initial Script Evaluation

### Sequence Diagram

```
IS [3b8f8ed5]         UDS Ch1         Sidecar [pool-1]         UDS Ch2

    │                    │                   │                     │
    │─ Connect ─────────>│                   │                     │
    │                    │                   │                     │
    │═══════════════════════════════════════════════════════════════
    │                    │                   │                     │
    │                    │                   │                     │
    │ EvaluateRequest    │                   │                     │
    │ ─────────────────> │ ─────────────────>│                     │
    │                    │   [Type: 1]       │                     │
    │                    │   [Size: 5160]    │                     │
    │                    │                   │                     │
    │                    │                   │  Create GraalVM     │
    │                    │                   │  Context            │
    │                    │                   │                     │
    │                    │                   │  Register 39 Host   │
    │                    │                   │  Functions          │
    │                    │                   │                     │
    │                    │                   │  Evaluate Script:   │
    │                    │                   │  onLoginRequest()   │
    │                    │                   │                     │
    │                    │                   │  ┌─────────────┐    │
    │                    │                   │  │ JavaScript  │    │
    │                    │                   │  │ Executes:   │    │
    │                    │                   │  │             │    │
    │                    │                   │  │ executeStep │    │
    │                    │                   │  │   (1, {     │    │
    │                    │                   │  │   onSuccess │    │
    │                    │                   │  │   })        │    │
    │                    │                   │  └─────────────┘    │
    │                    │                   │                     │
    │                    │                   │  Need to call       │
    │                    │                   │  host function      │
    │                    │                   │                     │
    │                    │                   │ ─ Connect ────────> │
    │                    │                   │                     │
    │                    │                   │ HostFunctionReq     │
    │                    │                   │ ──────────────────> │
    │                    │                   │   [Type: 5]         │
    │                    │                   │   executeStep       │
    │                    │                   │                     │
    │ <──────────────────────────────────────────────────────────── │
    │   HOST_FUNCTION_REQUEST                                       │
    │   • function_name: "executeStep"                              │
    │   • args: [1.0, {onSuccess: "<function source>"}]            │
    │                                                               │
    │  Process in HostCallbackServer:                               │
    │  • Deserialize args                                           │
    │  • Find session handler                                       │
    │  • Invoke JsGraalStepExecuter.executeStep(1, ...)            │
    │  • Store onSuccess callback                                   │
    │  • Return: void                                               │
    │                                                               │
    │ HOST_FUNCTION_RESPONSE                                        │
    │ ────────────────────────────────────────────────────────────> │
    │                    │                   │   [Type: 6]         │
    │                    │                   │   success: true     │
    │                    │                   │                     │
    │                    │                   │  Continue JS        │
    │                    │                   │  execution          │
    │                    │                   │                     │
    │                    │                   │ ContextPropertyReq  │
    │                    │                   │ ──────────────────> │
    │                    │                   │   [Type: 7]         │
    │                    │                   │   __keys__          │
    │                    │                   │                     │
    │ <──────────────────────────────────────────────────────────── │
    │                                                               │
    │  Return: null                                                 │
    │                                                               │
    │ ────────────────────────────────────────────────────────────> │
    │                    │                   │   [Type: 8]         │
    │                    │                   │                     │
    │                    │                   │  JS execution       │
    │                    │                   │  complete           │
    │                    │                   │                     │
    │                    │                   │ Close callback      │
    │                    │                   │ connection          │
    │                    │                   │                     │
    │ EvaluateResponse   │                   │                     │
    │ <──────────────────│<──────────────────│                     │
    │   [Type: 2]        │                   │                     │
    │   success: true    │                   │                     │
    │   elapsed_ms: 554  │                   │                     │
    │   bindings: {...}  │                   │                     │
    │                    │                   │                     │

Time: 554ms
Messages: 5 (1 Evaluate + 1 HostFunction + 1 ContextProperty + 2 Responses)
```

---

## Phase 2: Step 1 Success Callback (After User Login)

### Detailed Call Flow

```
┌──────────────────────────────────────────────────────────────────────────┐
│  EVENT: User "admin1" successfully authenticated at Step 1              │
└──────────────────────────────────────────────────────────────────────────┘

IS [68450b78]         UDS Ch1         Sidecar [pool-2]         UDS Ch2

    │                    │                   │                     │
    │  New Session:      │                   │                     │
    │  24706e5a...       │                   │                     │
    │                    │                   │                     │
    │ ExecuteCallback    │                   │                     │
    │ Request            │                   │                     │
    │ ─────────────────> │ ─────────────────>│                     │
    │   [Type: 3]        │                   │                     │
    │                    │                   │                     │
    │  Payload:          │                   │                     │
    │  • function_source:│                   │                     │
    │    "function(ctx){ │                   │                     │
    │      var user =    │                   │                     │
    │      ctx.current   │                   │                     │
    │      KnownSubject; │                   │                     │
    │      if(hasAny...)"│                   │                     │
    │  • arguments: [ctx]│                   │                     │
    │  • bindings:       │                   │                     │
    │    rolesToStepUp   │                   │                     │
    │  • context_data:   │                   │                     │
    │    step: 1         │                   │                     │
    │    user: admin1    │                   │                     │
    │                    │                   │                     │
    │                    │                   │  Create new         │
    │                    │                   │  GraalVM context    │
    │                    │                   │                     │
    │                    │                   │  Apply bindings:    │
    │                    │                   │  • rolesToStepUp    │
    │                    │                   │  • context (proxy)  │
    │                    │                   │                     │
    │                    │                   │  Evaluate function  │
    │                    │                   │                     │
    │                    │                   │  ┌─────────────┐    │
    │                    │                   │  │ JS: var user│    │
    │                    │                   │  │ = context.  │    │
    │                    │                   │  │ currentKnown│    │
    │                    │                   │  │ Subject;    │    │
    │                    │                   │  └─────────────┘    │
    │                    │                   │                     │
    │                    │                   │  Need property      │
    │                    │                   │  access             │
    │                    │                   │                     │
    │                    │                   │ ContextPropertyReq  │
    │                    │                   │ ──────────────────> │
    │                    │                   │   [Type: 7]         │
    │                    │                   │   currentKnown      │
    │                    │                   │   Subject           │
    │                    │                   │                     │
    │ <──────────────────────────────────────────────────────────── │
    │   CONTEXT_PROPERTY_REQUEST                                    │
    │   • property_path: "currentKnownSubject"                      │
    │   • proxy_type: "context"                                     │
    │                                                               │
    │  Process in HostCallbackServer:                               │
    │  • Get auth context                                           │
    │  • Access: context.getCurrentKnownSubject()                   │
    │  • Returns: JsGraalAuthenticatedUser (proxy object)          │
    │                                                               │
    │ CONTEXT_PROPERTY_RESPONSE                                     │
    │ ────────────────────────────────────────────────────────────> │
    │                    │                   │   [Type: 8]         │
    │                    │                   │   is_proxy: true    │
    │                    │                   │   proxy_type:       │
    │                    │                   │   "authenticateduser"
    │                    │                   │                     │
    │                    │                   │  Create nested      │
    │                    │                   │  proxy for user     │
    │                    │                   │                     │
    │                    │                   │ ContextPropertyReq  │
    │                    │                   │ ──────────────────> │
    │                    │                   │   [Type: 7]         │
    │                    │                   │   __keys__          │
    │                    │                   │                     │
    │ <──────────────────────────────────────────────────────────── │
    │  Return: null                                                 │
    │ ────────────────────────────────────────────────────────────> │
    │                    │                   │                     │
    │                    │                   │  ┌─────────────┐    │
    │                    │                   │  │ JS: if(     │    │
    │                    │                   │  │ hasAnyOf    │    │
    │                    │                   │  │ TheRolesV2( │    │
    │                    │                   │  │ context,    │    │
    │                    │                   │  │ rolesToStepUp))│  │
    │                    │                   │  └─────────────┘    │
    │                    │                   │                     │
    │                    │                   │ HostFunctionReq     │
    │                    │                   │ ──────────────────> │
    │                    │                   │   [Type: 5]         │
    │                    │                   │   hasAnyOfThe       │
    │                    │                   │   RolesV2           │
    │                    │                   │                     │
    │ <──────────────────────────────────────────────────────────── │
    │   HOST_FUNCTION_REQUEST                                       │
    │   • function: "hasAnyOfTheRolesV2"                            │
    │   • args: [context, ["admin", "manager"]]                     │
    │                                                               │
    │  Process in HostCallbackServer:                               │
    │  • Setup thread context (tenant, user)                        │
    │  • Reconstruct JsGraalAuthenticationContext                   │
    │  • Invoke: HasAnyOfTheRolesV2FunctionImpl                     │
    │  • Check user "admin1" roles                                  │
    │  • User has ["admin", "Internal/everyone"]                    │
    │  • Matches "admin" in ["admin", "manager"]                    │
    │  • Return: true                                               │
    │                                                               │
    │ HOST_FUNCTION_RESPONSE                                        │
    │ ────────────────────────────────────────────────────────────> │
    │                    │                   │   [Type: 6]         │
    │                    │                   │   result:           │
    │                    │                   │   bool_value: true  │
    │                    │                   │                     │
    │                    │                   │  ┌─────────────┐    │
    │                    │                   │  │ JS: Log.info│    │
    │                    │                   │  │ (user.      │    │
    │                    │                   │  │ username +  │    │
    │                    │                   │  │ ' has ...')│    │
    │                    │                   │  └─────────────┘    │
    │                    │                   │                     │
    │                    │                   │  Need username      │
    │                    │                   │                     │
    │                    │                   │ ContextPropertyReq  │
    │                    │                   │ ──────────────────> │
    │                    │                   │   [Type: 7]         │
    │                    │                   │   currentKnown      │
    │                    │                   │   Subject.username  │
    │                    │                   │                     │
    │ <──────────────────────────────────────────────────────────── │
    │  Get: user.username                                           │
    │  Return: "admin1"                                             │
    │ ────────────────────────────────────────────────────────────> │
    │                    │                   │   [Type: 8]         │
    │                    │                   │   string_value:     │
    │                    │                   │   "admin1"          │
    │                    │                   │                     │
    │                    │                   │  Log output:        │
    │                    │                   │  "admin1 has        │
    │                    │                   │  admin/manager      │
    │                    │                   │  role. Executing    │
    │                    │                   │  Step 2"            │
    │                    │                   │                     │
    │                    │                   │  ┌─────────────┐    │
    │                    │                   │  │ JS:         │    │
    │                    │                   │  │ executeStep │    │
    │                    │                   │  │   (2, {     │    │
    │                    │                   │  │   onSuccess │    │
    │                    │                   │  │   })        │    │
    │                    │                   │  └─────────────┘    │
    │                    │                   │                     │
    │                    │                   │ HostFunctionReq     │
    │                    │                   │ ──────────────────> │
    │                    │                   │   [Type: 5]         │
    │                    │                   │   executeStep       │
    │                    │                   │                     │
    │ <──────────────────────────────────────────────────────────── │
    │   HOST_FUNCTION_REQUEST                                       │
    │   • function: "executeStep"                                   │
    │   • args: [2.0, {onSuccess: "<function source>"}]            │
    │                                                               │
    │  Process:                                                     │
    │  • Store Step 2 onSuccess callback                            │
    │  • Return: void                                               │
    │                                                               │
    │ HOST_FUNCTION_RESPONSE                                        │
    │ ────────────────────────────────────────────────────────────> │
    │                    │                   │   [Type: 6]         │
    │                    │                   │                     │
    │                    │                   │  Callback function  │
    │                    │                   │  execution complete │
    │                    │                   │                     │
    │ ExecuteCallback    │                   │                     │
    │ Response           │                   │                     │
    │ <──────────────────│<──────────────────│                     │
    │   [Type: 4]        │                   │                     │
    │   success: true    │                   │                     │
    │   elapsed_ms: 72   │                   │                     │
    │   bindings: {...}  │                   │                     │
    │                    │                   │                     │

Time: 72ms
Messages: 9 (1 Execute + 2 ContextProperty + 2 HostFunction + 4 Responses)
Host Function Calls:
  1. hasAnyOfTheRolesV2(context, ["admin", "manager"]) → true
  2. executeStep(2, {onSuccess: ...}) → void
```

---

## Phase 3: Step 2 Success Callback (No Manager Access)

### Simplified Flow

```
IS [ee45bcf6]         UDS Ch1         Sidecar [pool-3]         UDS Ch2

    │                    │                   │                     │
    │  New Session:      │                   │                     │
    │  446a38ef...       │                   │                     │
    │                    │                   │                     │
    │ ExecuteCallback    │                   │                     │
    │ Request            │                   │                     │
    │ ─────────────────> │ ─────────────────>│                     │
    │   [Type: 3]        │                   │                     │
    │   Step 2 callback  │                   │                     │
    │                    │                   │                     │
    │                    │                   │  Create context     │
    │                    │                   │  Apply bindings     │
    │                    │                   │                     │
    │                    │                   │  Execute callback   │
    │                    │                   │                     │
    │                    │                   │ ContextPropertyReq  │
    │                    │                   │ ──────────────────> │
    │ <─────────────────────────────────────────────────────────── │
    │  currentKnownSubject                                          │
    │ ────────────────────────────────────────────────────────────> │
    │  Return: Proxy(user)                                          │
    │                    │                   │                     │
    │                    │                   │ ContextPropertyReq  │
    │                    │                   │ ──────────────────> │
    │ <─────────────────────────────────────────────────────────── │
    │  __keys__                                                     │
    │ ────────────────────────────────────────────────────────────> │
    │                    │                   │                     │
    │                    │                   │ HostFunctionReq     │
    │                    │                   │ ──────────────────> │
    │ <─────────────────────────────────────────────────────────── │
    │  hasAnyOfTheRolesV2(context, ["manager"])                     │
    │                                                               │
    │  Check: User "admin1" roles = ["admin", "Internal/everyone"] │
    │  Does NOT match "manager"                                     │
    │  Return: false                                                │
    │                                                               │
    │ ────────────────────────────────────────────────────────────> │
    │                    │                   │   result: false     │
    │                    │                   │                     │
    │                    │                   │  ┌─────────────┐    │
    │                    │                   │  │ JS: if(false│    │
    │                    │                   │  │ {...}       │    │
    │                    │                   │  │             │    │
    │                    │                   │  │ Condition   │    │
    │                    │                   │  │ FAILS       │    │
    │                    │                   │  │             │    │
    │                    │                   │  │ Skip:       │    │
    │                    │                   │  │ • Log       │    │
    │                    │                   │  │ • Heap      │    │
    │                    │                   │  │   exhaustion│    │
    │                    │                   │  │ • Step 3    │    │
    │                    │                   │  └─────────────┘    │
    │                    │                   │                     │
    │                    │                   │  Return from        │
    │                    │                   │  callback           │
    │                    │                   │                     │
    │ ExecuteCallback    │                   │                     │
    │ Response           │                   │                     │
    │ <──────────────────│<──────────────────│                     │
    │   [Type: 4]        │                   │                     │
    │   success: true    │                   │                     │
    │   elapsed_ms: 21   │                   │                     │
    │   bindings: {...}  │                   │                     │
    │                    │                   │                     │

Time: 21ms
Messages: 5 (1 Execute + 2 ContextProperty + 1 HostFunction + 1 Response)
Host Function Calls:
  1. hasAnyOfTheRolesV2(context, ["manager"]) → false
Result: No Step 3 execution (user is not manager)
```

---

## Message Type Breakdown

### Channel 1 (Request Channel)

```
┌────────────────────────────────────────────────────────────────────┐
│  Message Type 1: EVALUATE_REQUEST                                  │
├────────────────────────────────────────────────────────────────────┤
│  Direction: IS → Sidecar                                           │
│  Purpose: Evaluate JavaScript script                               │
│  Frequency: Once per script initialization                         │
│  Payload Size: ~5KB (includes full script)                         │
│                                                                    │
│  Contains:                                                         │
│  • session_id                                                      │
│  • script (full source code)                                       │
│  • source_identifier                                               │
│  • bindings (variables)                                            │
│  • host_functions (39 definitions)                                 │
│  • callback_socket_path                                            │
│  • context_data (step, user, tenant)                               │
└────────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────────┐
│  Message Type 2: EVALUATE_RESPONSE                                 │
├────────────────────────────────────────────────────────────────────┤
│  Direction: Sidecar → IS                                           │
│  Purpose: Return evaluation result                                 │
│  Frequency: Once per EVALUATE_REQUEST                              │
│  Payload Size: ~500 bytes                                          │
│                                                                    │
│  Contains:                                                         │
│  • success (boolean)                                               │
│  • elapsed_ms                                                      │
│  • result (SerializedValue)                                        │
│  • updated_bindings (modified global vars)                         │
│  • error_message (if failed)                                       │
└────────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────────┐
│  Message Type 3: EXECUTE_CALLBACK_REQUEST                          │
├────────────────────────────────────────────────────────────────────┤
│  Direction: IS → Sidecar                                           │
│  Purpose: Execute a JavaScript callback function                   │
│  Frequency: Once per step completion                               │
│  Payload Size: ~2KB (includes function source)                     │
│                                                                    │
│  Contains:                                                         │
│  • session_id                                                      │
│  • function_source (callback function code)                        │
│  • arguments (context, etc.)                                       │
│  • bindings (from previous execution)                              │
│  • context_data (current step, user)                               │
│  • callback_socket_path                                            │
│  • host_functions                                                  │
└────────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────────┐
│  Message Type 4: EXECUTE_CALLBACK_RESPONSE                         │
├────────────────────────────────────────────────────────────────────┤
│  Direction: Sidecar → IS                                           │
│  Purpose: Return callback execution result                         │
│  Frequency: Once per EXECUTE_CALLBACK_REQUEST                      │
│  Payload Size: ~400 bytes                                          │
│                                                                    │
│  Contains:                                                         │
│  • success                                                         │
│  • elapsed_ms                                                      │
│  • result                                                          │
│  • updated_bindings                                                │
│  • error_message                                                   │
└────────────────────────────────────────────────────────────────────┘
```

### Channel 2 (Callback Channel)

```
┌────────────────────────────────────────────────────────────────────┐
│  Message Type 5: HOST_FUNCTION_REQUEST                             │
├────────────────────────────────────────────────────────────────────┤
│  Direction: Sidecar → IS (via callback channel)                   │
│  Purpose: Call a Java host function from JavaScript                │
│  Frequency: Each time JS calls a host function                     │
│  Payload Size: ~200 bytes                                          │
│                                                                    │
│  Contains:                                                         │
│  • session_id                                                      │
│  • function_name (e.g., "executeStep", "hasAnyOfTheRolesV2")      │
│  • arguments (serialized)                                          │
│                                                                    │
│  Examples:                                                         │
│  • executeStep(1, {onSuccess: ...})                                │
│  • hasAnyOfTheRolesV2(context, ["admin", "manager"])              │
│  • Log.info("message")                                             │
└────────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────────┐
│  Message Type 6: HOST_FUNCTION_RESPONSE                            │
├────────────────────────────────────────────────────────────────────┤
│  Direction: IS → Sidecar (via callback channel)                   │
│  Purpose: Return host function result                              │
│  Frequency: Once per HOST_FUNCTION_REQUEST                         │
│  Payload Size: ~50 bytes                                           │
│                                                                    │
│  Contains:                                                         │
│  • success                                                         │
│  • result (boolean, string, number, object, null)                  │
│  • error_message                                                   │
└────────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────────┐
│  Message Type 7: CONTEXT_PROPERTY_REQUEST                          │
├────────────────────────────────────────────────────────────────────┤
│  Direction: Sidecar → IS (via callback channel)                   │
│  Purpose: Lazy-load a property from context proxy                  │
│  Frequency: Each time JS accesses a context property               │
│  Payload Size: ~100 bytes                                          │
│                                                                    │
│  Contains:                                                         │
│  • session_id                                                      │
│  • property_path (e.g., "currentKnownSubject.username")           │
│  • proxy_type ("context", "authenticateduser", etc.)              │
│                                                                    │
│  Examples:                                                         │
│  • context.currentKnownSubject                                     │
│  • context.currentKnownSubject.username                            │
│  • context.__keys__                                                │
└────────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────────┐
│  Message Type 8: CONTEXT_PROPERTY_RESPONSE                         │
├────────────────────────────────────────────────────────────────────┤
│  Direction: IS → Sidecar (via callback channel)                   │
│  Purpose: Return context property value                            │
│  Frequency: Once per CONTEXT_PROPERTY_REQUEST                      │
│  Payload Size: ~100 bytes                                          │
│                                                                    │
│  Contains:                                                         │
│  • success                                                         │
│  • value (serialized, or null if proxy)                           │
│  • is_proxy (boolean - if true, value is another proxy)           │
│  • proxy_type (for nested proxy creation)                          │
│  • member_keys (available properties)                              │
└────────────────────────────────────────────────────────────────────┘
```

---

## Complete Login Flow Summary

```
┌─────────────────────────────────────────────────────────────────────┐
│                                                                     │
│  PHASE 1: Script Initialization (554ms)                            │
│  ══════════════════════════════════════════                        │
│                                                                     │
│  IS → Sidecar:  EvaluateRequest                                    │
│                 ├─ Script: onLoginRequest(context)                 │
│                 ├─ Host Functions: 39                               │
│                 └─ Context: step=0, user=null                       │
│                                                                     │
│  Sidecar → IS:  executeStep(1, {onSuccess: <callback>})            │
│                 ↓                                                   │
│  IS:            Store callback, schedule Step 1                     │
│                                                                     │
│  Sidecar → IS:  EvaluateResponse (success, 554ms)                  │
│                 └─ Bindings: rolesToStepUp = ["admin", "manager"]  │
│                                                                     │
│  ► User sees login page (Step 1)                                   │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘

                              ⬇ User logs in as "admin1"

┌─────────────────────────────────────────────────────────────────────┐
│                                                                     │
│  PHASE 2: Step 1 Success Callback (72ms)                           │
│  ══════════════════════════════════════════                        │
│                                                                     │
│  IS → Sidecar:  ExecuteCallbackRequest                             │
│                 ├─ Function: Step 1 onSuccess callback             │
│                 ├─ Context: step=1, user="admin1"                  │
│                 └─ Bindings: rolesToStepUp = ["admin", "manager"]  │
│                                                                     │
│  Sidecar → IS:  context.currentKnownSubject (property access)      │
│  IS → Sidecar:  Return: Proxy(JsGraalAuthenticatedUser)            │
│                                                                     │
│  Sidecar → IS:  hasAnyOfTheRolesV2(context, ["admin", "manager"])  │
│  IS → Sidecar:  Return: true (user has "admin" role)               │
│                                                                     │
│  Sidecar → IS:  context.currentKnownSubject.username               │
│  IS → Sidecar:  Return: "admin1"                                   │
│                                                                     │
│  Sidecar:       Log.info("admin1 has admin/manager role...")       │
│                                                                     │
│  Sidecar → IS:  executeStep(2, {onSuccess: <callback>})            │
│                 ↓                                                   │
│  IS:            Store callback, schedule Step 2                     │
│                                                                     │
│  Sidecar → IS:  ExecuteCallbackResponse (success, 72ms)            │
│                                                                     │
│  ► User sees Step 2 (e.g., TOTP)                                   │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘

                          ⬇ User completes Step 2

┌─────────────────────────────────────────────────────────────────────┐
│                                                                     │
│  PHASE 3: Step 2 Success Callback (21ms)                           │
│  ══════════════════════════════════════════                        │
│                                                                     │
│  IS → Sidecar:  ExecuteCallbackRequest                             │
│                 ├─ Function: Step 2 onSuccess callback             │
│                 ├─ Context: step=2, user="admin1"                  │
│                 └─ Bindings: rolesToStepUp = ["admin", "manager"]  │
│                                                                     │
│  Sidecar → IS:  context.currentKnownSubject (property access)      │
│  IS → Sidecar:  Return: Proxy(JsGraalAuthenticatedUser)            │
│                                                                     │
│  Sidecar → IS:  hasAnyOfTheRolesV2(context, ["manager"])           │
│  IS → Sidecar:  Return: false (user does NOT have "manager" role)  │
│                                                                     │
│  Sidecar:       if (false) { ... }  ← Condition fails              │
│                 ↓                                                   │
│                 Skip:                                               │
│                 • Log statement                                     │
│                 • Heap exhaustion code                              │
│                 • executeStep(3)                                    │
│                                                                     │
│  Sidecar → IS:  ExecuteCallbackResponse (success, 21ms)            │
│                                                                     │
│  ► Authentication flow COMPLETE                                    │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘

═══════════════════════════════════════════════════════════════════════

TOTAL TIME: 647ms
TOTAL MESSAGES: 32 (16 request-response pairs)
SESSIONS: 3 (1 per phase)
CONTEXT: 1 (shared: 8cff7b3a-0640-444e-93ab-4f69c50b85fc)
RESULT: User "admin1" authenticated with 2-step auth (no Step 3)

═══════════════════════════════════════════════════════════════════════
```

---

## Key Technical Details

### 1. Stateless Sidecar
```
Each JavaScript execution:
├─ Creates NEW GraalVM Context
├─ No state persisted between calls
├─ Bindings passed in every request
└─ Enables crash isolation & scaling
```

### 2. Lazy Loading (Context Proxy)
```
context.currentKnownSubject:
├─ NOT serialized upfront
├─ Proxy reference sent instead
├─ JS access triggers callback
└─ Avoids serializing entire auth context
```

### 3. Function Serialization
```
Callbacks (onSuccess):
├─ Stored as SOURCE CODE
├─ Re-evaluated in sidecar
├─ Bindings available via closure
└─ Enables async execution
```

### 4. Session per Phase
```
Phase 1: c114ae58-4006-441a-be6a-35c1f88825ce
Phase 2: 24706e5a-5ac7-4e75-b0aa-0b4ffe055808
Phase 3: 446a38ef-220f-4091-ac14-bdb1caa3de09
         ↓
All share: 8cff7b3a-0640-444e-93ab-4f69c50b85fc
```

### 5. Thread Model
```
IS Threads:
├─ Request Handler Thread (per HTTP request)
├─ Callback Handler Thread (per callback connection)
└─ Thread-local context setup per callback

Sidecar Threads:
├─ Thread Pool (10 threads)
├─ One thread per JavaScript execution
└─ Isolated GraalVM contexts
```

---

**End of Visual Flow Diagrams**
