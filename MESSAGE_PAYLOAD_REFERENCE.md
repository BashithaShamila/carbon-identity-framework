# Message Payload Reference Guide

## Complete Payload Structures for Externalized GraalJS Communication

This document provides detailed payload structures for every message type exchanged between WSO2 Identity Server and the GraalJS sidecar during the admin user login flow.

---

## Protocol Buffers Schema Summary

### Message Type IDs
```
1  = EVALUATE_REQUEST
2  = EVALUATE_RESPONSE
3  = EXECUTE_CALLBACK_REQUEST
4  = EXECUTE_CALLBACK_RESPONSE
5  = HOST_FUNCTION_REQUEST
6  = HOST_FUNCTION_RESPONSE
7  = CONTEXT_PROPERTY_REQUEST
8  = CONTEXT_PROPERTY_RESPONSE
9  = CONTEXT_PROPERTY_SET_REQUEST
10 = CONTEXT_PROPERTY_SET_RESPONSE
```

### Wire Format
```
[1 byte: message_type] [4 bytes: length (big-endian)] [N bytes: protobuf payload]
```

---

## Phase 1: Script Initialization

### 1.1 EvaluateRequest (IS → Sidecar)

**Message Type**: `1`
**Size**: 5160 bytes
**Channel**: UDS Channel 1 (`/tmp/graaljs-sidecar.sock`)
**Thread (IS)**: `[3b8f8ed5-5f11-4f6a-91ed-b1bc69d380c2]`
**Thread (Sidecar)**: `[pool-1-thread-1]`

```protobuf
message EvaluateRequest {
  string session_id = "c114ae58-4006-441a-be6a-35c1f88825ce";

  string script = """
    /*
     * Copyright (c) 2018, WSO2 Inc. ...
     */

    var internalRequire = (function () {
      // Module system implementation
      // ...
    })();

    var require = internalRequire();

    // Secrets proxy setup
    var secrets = new Proxy({}, {
      get: function(target, prop) {
        return getSecretByName(prop);
      }
    });

    // USER SCRIPT STARTS HERE
    var rolesToStepUp = ['admin', 'manager'];

    var onLoginRequest = function (context) {
      executeStep(1, {
        onSuccess: function (context) {
          var user = context.currentKnownSubject;
          // Admin OR Manager → Step 2
          if (hasAnyOfTheRolesV2(context, rolesToStepUp)) {
            Log.info(user.username + ' has admin/manager role. Executing Step 2');
            executeStep(2, {
              onSuccess: function (context) {
                var user = context.currentKnownSubject;
                // Manager only → Step 3
                if (hasAnyOfTheRolesV2(context, ['manager'])) {
                  Log.info(user.username + ' is a manager. Triggering heap exhaustion');
                  const N = 1000000000;
                  new Array(N + 1).join('x');
                  Log.info(user.username + ' is a manager. Executing Step 3');
                  executeStep(3);
                }
              }
            });
          }
        }
      });
    };

    onLoginRequest(context);
  """;

  string source_identifier = "adaptive-script";

  int32 statement_limit = 0;  // 0 = unlimited

  map<string, SerializedValue> bindings = {};  // Empty initially

  repeated HostFunctionDefinition host_functions = [
    {name: "doAssociationWithLocalUser"},
    {name: "terminateUserSession"},
    {name: "assignUserRoles"},
    {name: "callChoreo"},
    {name: "checkMicrosoftEmailVerification"},
    {name: "getUserSessions"},
    {name: "callElastic"},
    {name: "hasAnyOfTheRolesV2"},
    {name: "removeAssociatedLocalUser"},
    {name: "assignUserRolesV2"},
    {name: "resolveMultiAttributeLoginIdentifier"},
    {name: "getUsersWithClaimValues"},
    {name: "getCookieValue"},
    {name: "callAnalytics"},
    {name: "isAnyOfTheRolesAssignedToUser"},
    {name: "updateUserPassword"},
    {name: "getUniqueUserWithClaimValues"},
    {name: "hasRole"},
    {name: "getMaskedValue"},
    {name: "getSecretByName"},
    {name: "promptIdentifierForStep"},
    {name: "httpGet"},
    {name: "getAuthenticatedApplications"},
    {name: "checkSessionExistence"},
    {name: "httpPost"},
    {name: "sendError"},
    {name: "removeUserRoles"},
    {name: "sendEmail"},
    {name: "getValueFromDecodedAssertion"},
    {name: "loadLocalLibrary"},
    {name: "getAssociatedLocalUser"},
    {name: "removeUserRolesV2"},
    {name: "filterAuthenticators"},
    {name: "setCookie"},
    {name: "hasAnyOfTheRoles"},
    {name: "publishToAnalytics"},
    {name: "isMemberOfAnyOfGroups"},
    {name: "executeStep"},
    {name: "prompt"}
  ];

  string callback_socket_path = "/tmp/graaljs-callback-41ebd525.sock";

  ContextData context_data = {
    session_context_key: "8cff7b3a-0640-444e-93ab-4f69c50b85fc",
    current_step: 0,
    username: "",
    user_store_domain: "",
    tenant_domain: "",
    roles: [],
    claims: {}
  };
}
```

**Payload Breakdown**:
- **session_id**: Unique ID for this JavaScript execution session
- **script**: Full script including framework code + user script (4160 chars)
- **source_identifier**: Name for debugging/logging
- **statement_limit**: Max statements (0 = unlimited)
- **bindings**: Global variables (empty at initialization)
- **host_functions**: 39 Java functions callable from JavaScript
- **callback_socket_path**: Where sidecar sends host function calls
- **context_data**: Authentication context metadata

---

### 1.2 HostFunctionRequest - executeStep (Sidecar → IS)

**Message Type**: `5`
**Size**: ~300 bytes
**Channel**: UDS Channel 2 (`/tmp/graaljs-callback-41ebd525.sock`)
**Thread (Sidecar)**: `[pool-1-thread-1]` (executing JS)
**Thread (IS)**: `[Anonymous Callback Handler]`

```protobuf
message HostFunctionRequest {
  string session_id = "c114ae58-4006-441a-be6a-35c1f88825ce";

  string function_name = "executeStep";

  repeated SerializedValue arguments = [
    // Argument 0: Step number
    SerializedValue {
      double_value: 1.0
    },

    // Argument 1: Options map
    SerializedValue {
      map_value: {
        entries: {
          "onSuccess": SerializedValue {
            function_value: {
              source: "function (context) {\n\
                var user = context.currentKnownSubject;\n\
                // Admin OR Manager → Step 2\n\
                if (hasAnyOfTheRolesV2(context, rolesToStepUp)) {\n\
                  Log.info(user.username + ' has admin/manager role. Executing Step 2');\n\
                  executeStep(2, {\n\
                    onSuccess: function (context) {\n\
                      var user = context.currentKnownSubject;\n\
                      // Manager only → Step 3\n\
                      if (hasAnyOfTheRolesV2(context, ['manager'])) {\n\
                        Log.info(user.username + ' is a manager. Triggering heap exhaustion');\n\
                        const N = 1000000000;\n\
                        new Array(N + 1).join('x');\n\
                        Log.info(user.username + ' is a manager. Executing Step 3');\n\
                        executeStep(3);\n\
                      }\n\
                    }\n\
                  });\n\
                }\n\
              }",
              name: "onSuccess"
            }
          }
        }
      }
    }
  ];
}
```

**Payload Breakdown**:
- **session_id**: Links to current execution session
- **function_name**: Name of Java host function to invoke
- **arguments[0]**: Step number as Double (1.0)
- **arguments[1]**: Options map containing:
  - **onSuccess**: Serialized JavaScript function source code
    - This function will be executed later when Step 1 completes
    - Contains the entire callback logic (role checks, Step 2, Step 3)

**Java Processing**:
```java
HostCallbackServer receives request:
├─ Deserialize: Double(1.0) → Integer(1)
├─ Deserialize: Map{onSuccess: "<function source>"}
├─ Find method: JsGraalStepExecuter.executeStep(int, Object[])
├─ Invoke: executeStep(1, {onSuccess: GraalSerializableJsFunction})
└─ Store callback for later execution
```

---

### 1.3 HostFunctionResponse - executeStep (IS → Sidecar)

**Message Type**: `6`
**Size**: ~20 bytes
**Channel**: UDS Channel 2

```protobuf
message HostFunctionResponse {
  bool success = true;

  SerializedValue result = {
    null_value: ""  // executeStep returns void
  };

  string error_message = "";
}
```

---

### 1.4 ContextPropertyRequest - __keys__ (Sidecar → IS)

**Message Type**: `7`
**Size**: ~50 bytes
**Channel**: UDS Channel 2

```protobuf
message ContextPropertyRequest {
  string session_id = "c114ae58-4006-441a-be6a-35c1f88825ce";

  string property_path = "__keys__";

  string proxy_type = "context";
}
```

**Why This Happens**: JavaScript introspects the `context` object to see what properties it has. This is part of the dynamic proxy mechanism.

---

### 1.5 ContextPropertyResponse - __keys__ (IS → Sidecar)

**Message Type**: `8`
**Size**: ~30 bytes
**Channel**: UDS Channel 2

```protobuf
message ContextPropertyResponse {
  bool success = true;

  SerializedValue value = {
    null_value: ""
  };

  string error_message = "";

  bool is_proxy = false;

  string proxy_type = "";

  repeated string member_keys = [];
}
```

---

### 1.6 EvaluateResponse (Sidecar → IS)

**Message Type**: `2`
**Size**: ~500 bytes
**Channel**: UDS Channel 1
**Elapsed Time**: 554ms

```protobuf
message EvaluateResponse {
  bool success = true;

  int64 elapsed_ms = 554;

  SerializedValue result = {
    null_value: ""  // onLoginRequest returns void
  };

  map<string, SerializedValue> updated_bindings = {
    "rolesToStepUp": SerializedValue {
      array_value: {
        elements: [
          SerializedValue {string_value: "admin"},
          SerializedValue {string_value: "manager"}
        ]
      }
    },

    "context": SerializedValue {
      map_value: {
        entries: {}
      }
    },

    "secrets": SerializedValue {
      map_value: {
        entries: {}
      }
    }
  };

  string error_message = "";
  string error_type = "";
}
```

**Payload Breakdown**:
- **success**: true = script executed without errors
- **elapsed_ms**: 554ms total execution time
- **result**: null (onLoginRequest doesn't return anything)
- **updated_bindings**: Global variables created/modified during execution
  - **rolesToStepUp**: The array defined at script top level
  - **context**: Empty map (proxy, not serialized)
  - **secrets**: Empty map (proxy)
- These bindings will be sent back in subsequent callback requests

---

## Phase 2: Step 1 Success Callback

### 2.1 ExecuteCallbackRequest (IS → Sidecar)

**Message Type**: `3`
**Size**: ~2000 bytes
**Channel**: UDS Channel 1
**Thread (IS)**: `[68450b78-6b08-4ae5-8ac5-e526483436ff]`
**Thread (Sidecar)**: `[pool-1-thread-2]`

```protobuf
message ExecuteCallbackRequest {
  string session_id = "24706e5a-5ac7-4e75-b0aa-0b4ffe055808";  // NEW SESSION

  string function_source = "function (context) {\n\
    var user = context.currentKnownSubject;\n\
    // Admin OR Manager → Step 2\n\
    if (hasAnyOfTheRolesV2(context, rolesToStepUp)) {\n\
      Log.info(user.username + ' has admin/manager role. Executing Step 2');\n\
      executeStep(2, {\n\
        onSuccess: function (context) {\n\
          var user = context.currentKnownSubject;\n\
          // Manager only → Step 3\n\
          if (hasAnyOfTheRolesV2(context, ['manager'])) {\n\
            Log.info(user.username + ' is a manager. Triggering heap exhaustion');\n\
            const N = 1000000000;\n\
            new Array(N + 1).join('x');\n\
            Log.info(user.username + ' is a manager. Executing Step 3');\n\
            executeStep(3);\n\
          }\n\
        }\n\
      });\n\
    }\n\
  }";

  repeated SerializedValue arguments = [
    SerializedValue {
      string_value: "org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext@2344e1ed"
      // This is a PROXY REFERENCE - not the actual object
      // Sidecar will callback to IS when JS accesses properties
    }
  ];

  map<string, SerializedValue> bindings = {
    "rolesToStepUp": SerializedValue {
      array_value: {
        elements: [
          SerializedValue {string_value: "admin"},
          SerializedValue {string_value: "manager"}
        ]
      }
    },

    "context": SerializedValue {
      map_value: {
        entries: {}
      }
    },

    "secrets": SerializedValue {
      map_value: {
        entries: {}
      }
    }
  };

  ContextData context_data = {
    session_context_key: "8cff7b3a-0640-444e-93ab-4f69c50b85fc",
    current_step: 1,  // ← Updated
    username: "admin1",  // ← User now authenticated
    user_store_domain: "PRIMARY",
    tenant_domain: "carbon.super",
    roles: [],
    claims: {}
  };

  string callback_socket_path = "/tmp/graaljs-callback-41ebd525.sock";

  repeated HostFunctionDefinition host_functions = [
    // ... all 40 functions (includes "fail" now)
  ];
}
```

**Key Changes from EvaluateRequest**:
- **Message Type**: ExecuteCallbackRequest (not EvaluateRequest)
- **function_source**: Just the callback function (not full script)
- **session_id**: New session ID
- **current_step**: Now 1 (was 0)
- **username**: "admin1" (was empty)
- **bindings**: Passed from previous execution

---

### 2.2 ContextPropertyRequest - currentKnownSubject (Sidecar → IS)

**Message Type**: `7`
**Size**: ~70 bytes
**Channel**: UDS Channel 2

```protobuf
message ContextPropertyRequest {
  string session_id = "24706e5a-5ac7-4e75-b0aa-0b4ffe055808";

  string property_path = "currentKnownSubject";

  string proxy_type = "context";
}
```

**JavaScript Context**:
```javascript
// Currently executing:
var user = context.currentKnownSubject;
//                   ↑ This property access triggers the callback
```

---

### 2.3 ContextPropertyResponse - currentKnownSubject (IS → Sidecar)

**Message Type**: `8`
**Size**: ~80 bytes
**Channel**: UDS Channel 2

```protobuf
message ContextPropertyResponse {
  bool success = true;

  SerializedValue value = null;  // Not serialized because is_proxy = true

  string error_message = "";

  bool is_proxy = true;  // ← This is a NESTED PROXY

  string proxy_type = "authenticateduser";  // ← Type for creating nested proxy

  repeated string member_keys = [];
}
```

**What This Means**:
- `context.currentKnownSubject` is itself a complex object
- Instead of serializing it, IS tells sidecar: "Create a proxy"
- Future access to `user.username` will trigger another callback

---

### 2.4 HostFunctionRequest - hasAnyOfTheRolesV2 (Sidecar → IS)

**Message Type**: `5`
**Size**: ~150 bytes
**Channel**: UDS Channel 2

```protobuf
message HostFunctionRequest {
  string session_id = "24706e5a-5ac7-4e75-b0aa-0b4ffe055808";

  string function_name = "hasAnyOfTheRolesV2";

  repeated SerializedValue arguments = [
    // Argument 0: context (proxy)
    SerializedValue {
      map_value: {
        entries: {}
      }
    },

    // Argument 1: roles array
    SerializedValue {
      array_value: {
        elements: [
          SerializedValue {string_value: "admin"},
          SerializedValue {string_value: "manager"}
        ]
      }
    }
  ];
}
```

**Java Processing**:
```java
HostCallbackServer:
├─ Deserialize: HashMap → JsGraalAuthenticationContext (reconstructed)
├─ Deserialize: ArrayList → List<String>
├─ Setup Thread Context:
│   ├─ Tenant: carbon.super
│   ├─ Tenant ID: -1234
│   ├─ Context ID: 8cff7b3a-0640-444e-93ab-4f69c50b85fc
│   └─ Username: admin1
├─ Invoke: HasAnyOfTheRolesV2FunctionImpl.hasAnyOfTheRolesV2(context, roles)
├─ Logic:
│   ├─ Get user "admin1" from context
│   ├─ Get user roles: ["admin", "Internal/everyone"]
│   ├─ Check if ANY of ["admin", "manager"] in user roles
│   └─ Result: true (user has "admin")
└─ Return: Boolean(true)
```

---

### 2.5 HostFunctionResponse - hasAnyOfTheRolesV2 (IS → Sidecar)

**Message Type**: `6`
**Size**: ~20 bytes
**Channel**: UDS Channel 2

```protobuf
message HostFunctionResponse {
  bool success = true;

  SerializedValue result = {
    bool_value: true  // ← User has admin or manager role
  };

  string error_message = "";
}
```

---

### 2.6 ContextPropertyRequest - currentKnownSubject.username (Sidecar → IS)

**Message Type**: `7`
**Size**: ~80 bytes
**Channel**: UDS Channel 2

```protobuf
message ContextPropertyRequest {
  string session_id = "24706e5a-5ac7-4e75-b0aa-0b4ffe055808";

  string property_path = "currentKnownSubject.username";

  string proxy_type = "authenticateduser";
}
```

**JavaScript Context**:
```javascript
// Currently executing:
Log.info(user.username + ' has admin/manager role...');
//           ↑ This property access triggers the callback
```

---

### 2.7 ContextPropertyResponse - username (IS → Sidecar)

**Message Type**: `8`
**Size**: ~40 bytes
**Channel**: UDS Channel 2

```protobuf
message ContextPropertyResponse {
  bool success = true;

  SerializedValue value = {
    string_value: "admin1"  // ← The username
  };

  string error_message = "";

  bool is_proxy = false;  // ← Simple string, not a proxy

  string proxy_type = "";

  repeated string member_keys = [];
}
```

**JavaScript Now Has**:
```javascript
user.username = "admin1"
// Can now execute: Log.info("admin1 has admin/manager role...")
```

---

### 2.8 HostFunctionRequest - executeStep(2) (Sidecar → IS)

**Message Type**: `5`
**Size**: ~800 bytes
**Channel**: UDS Channel 2

```protobuf
message HostFunctionRequest {
  string session_id = "24706e5a-5ac7-4e75-b0aa-0b4ffe055808";

  string function_name = "executeStep";

  repeated SerializedValue arguments = [
    SerializedValue {
      double_value: 2.0  // ← Step 2
    },

    SerializedValue {
      map_value: {
        entries: {
          "onSuccess": SerializedValue {
            function_value: {
              source: "function (context) {\n\
                var user = context.currentKnownSubject;\n\
                // Manager only → Step 3\n\
                if (hasAnyOfTheRolesV2(context, ['manager'])) {\n\
                  Log.info(user.username + ' is a manager. Triggering heap exhaustion');\n\
                  const N = 1000000000;\n\
                  new Array(N + 1).join('x');\n\
                  Log.info(user.username + ' is a manager. Executing Step 3');\n\
                  executeStep(3);\n\
                }\n\
              }",
              name: "onSuccess"
            }
          }
        }
      }
    }
  ];
}
```

---

### 2.9 ExecuteCallbackResponse (Sidecar → IS)

**Message Type**: `4`
**Size**: ~400 bytes
**Channel**: UDS Channel 1
**Elapsed Time**: 72ms

```protobuf
message ExecuteCallbackResponse {
  bool success = true;

  int64 elapsed_ms = 72;

  SerializedValue result = {
    null_value: ""
  };

  map<string, SerializedValue> updated_bindings = {
    "rolesToStepUp": SerializedValue {
      array_value: {
        elements: [
          SerializedValue {string_value: "admin"},
          SerializedValue {string_value: "manager"}
        ]
      }
    },

    "context": SerializedValue {
      map_value: {entries: {}}
    },

    "secrets": SerializedValue {
      map_value: {entries: {}}
    }
  };

  string error_message = "";
}
```

---

## Phase 3: Step 2 Success Callback

### 3.1 ExecuteCallbackRequest (IS → Sidecar)

**Message Type**: `3`
**Size**: ~1500 bytes
**Channel**: UDS Channel 1
**Thread (IS)**: `[ee45bcf6-6a64-47cf-a752-9a16e0fb7190]`
**Thread (Sidecar)**: `[pool-1-thread-3]`

```protobuf
message ExecuteCallbackRequest {
  string session_id = "446a38ef-220f-4091-ac14-bdb1caa3de09";  // NEW SESSION

  string function_source = "function (context) {\n\
    var user = context.currentKnownSubject;\n\
    // Manager only → Step 3\n\
    if (hasAnyOfTheRolesV2(context, ['manager'])) {\n\
      Log.info(user.username + ' is a manager. Triggering heap exhaustion');\n\
      const N = 1000000000;\n\
      new Array(N + 1).join('x');\n\
      Log.info(user.username + ' is a manager. Executing Step 3');\n\
      executeStep(3);\n\
    }\n\
  }";

  repeated SerializedValue arguments = [
    SerializedValue {
      string_value: "org.wso2.carbon...JsGraalAuthenticationContext@5bbee6aa"
    }
  ];

  map<string, SerializedValue> bindings = {
    "rolesToStepUp": SerializedValue {
      array_value: {
        elements: [
          SerializedValue {string_value: "admin"},
          SerializedValue {string_value: "manager"}
        ]
      }
    },
    "context": SerializedValue {map_value: {entries: {}}},
    "secrets": SerializedValue {map_value: {entries: {}}}
  };

  ContextData context_data = {
    session_context_key: "8cff7b3a-0640-444e-93ab-4f69c50b85fc",
    current_step: 2,  // ← Now at Step 2
    username: "admin1",
    user_store_domain: "PRIMARY",
    tenant_domain: "carbon.super",
    roles: [],
    claims: {}
  };

  string callback_socket_path = "/tmp/graaljs-callback-41ebd525.sock";

  repeated HostFunctionDefinition host_functions = [
    // ... all 40 functions
  ];
}
```

---

### 3.2 HostFunctionRequest - hasAnyOfTheRolesV2 (Sidecar → IS)

**Message Type**: `5`
**Size**: ~100 bytes
**Channel**: UDS Channel 2

```protobuf
message HostFunctionRequest {
  string session_id = "446a38ef-220f-4091-ac14-bdb1caa3de09";

  string function_name = "hasAnyOfTheRolesV2";

  repeated SerializedValue arguments = [
    SerializedValue {
      map_value: {entries: {}}  // context proxy
    },

    SerializedValue {
      array_value: {
        elements: [
          SerializedValue {string_value: "manager"}  // ← Only checking "manager"
        ]
      }
    }
  ];
}
```

**Java Processing**:
```java
HasAnyOfTheRolesV2FunctionImpl:
├─ User: admin1
├─ User Roles: ["admin", "Internal/everyone"]
├─ Requested Roles: ["manager"]
├─ Check: Does "manager" exist in user roles?
└─ Result: false ← User does NOT have "manager" role
```

---

### 3.3 HostFunctionResponse - hasAnyOfTheRolesV2 (IS → Sidecar)

**Message Type**: `6`
**Size**: ~20 bytes
**Channel**: UDS Channel 2

```protobuf
message HostFunctionResponse {
  bool success = true;

  SerializedValue result = {
    bool_value: false  // ← User is NOT a manager
  };

  string error_message = "";
}
```

**JavaScript Result**:
```javascript
if (hasAnyOfTheRolesV2(context, ['manager'])) {
  // ↑ Evaluates to false
  // This block DOES NOT EXECUTE:
  // • No log statement
  // • No heap exhaustion
  // • No executeStep(3)
}
// Function returns immediately
```

---

### 3.4 ExecuteCallbackResponse (Sidecar → IS)

**Message Type**: `4`
**Size**: ~400 bytes
**Channel**: UDS Channel 1
**Elapsed Time**: 21ms

```protobuf
message ExecuteCallbackResponse {
  bool success = true;

  int64 elapsed_ms = 21;

  SerializedValue result = {
    null_value: ""
  };

  map<string, SerializedValue> updated_bindings = {
    "rolesToStepUp": SerializedValue {
      array_value: {
        elements: [
          SerializedValue {string_value: "admin"},
          SerializedValue {string_value: "manager"}
        ]
      }
    },
    "context": SerializedValue {map_value: {entries: {}}},
    "secrets": SerializedValue {map_value: {entries: {}}}
  };

  string error_message = "";
}
```

---

## SerializedValue Type Reference

### Primitive Types

```protobuf
// String
SerializedValue {
  string_value: "admin1"
}

// Integer
SerializedValue {
  int_value: 42
}

// Double/Float
SerializedValue {
  double_value: 1.0
}

// Boolean
SerializedValue {
  bool_value: true
}

// Null
SerializedValue {
  null_value: ""
}
```

### Complex Types

```protobuf
// Array
SerializedValue {
  array_value: {
    elements: [
      SerializedValue {string_value: "admin"},
      SerializedValue {string_value: "manager"},
      SerializedValue {int_value: 42}
    ]
  }
}

// Map/Object
SerializedValue {
  map_value: {
    entries: {
      "key1": SerializedValue {string_value: "value1"},
      "key2": SerializedValue {int_value: 123},
      "nested": SerializedValue {
        map_value: {
          entries: {
            "innerKey": SerializedValue {bool_value: true}
          }
        }
      }
    }
  }
}

// Function
SerializedValue {
  function_value: {
    source: "function(x) { return x * 2; }",
    name: "doubler"
  }
}

// Proxy Object
SerializedValue {
  proxy_object: {
    type: "authenticateduser",
    reference_id: "user-12345",
    cached_properties: {}
  }
}
```

---

## Message Size Summary

```
┌─────────────────────────────────────┬──────────┬──────────────┐
│ Message Type                        │ Size     │ Frequency    │
├─────────────────────────────────────┼──────────┼──────────────┤
│ EvaluateRequest                     │ ~5KB     │ 1 per script │
│ EvaluateResponse                    │ ~500B    │ 1 per script │
│ ExecuteCallbackRequest              │ ~2KB     │ 1 per step   │
│ ExecuteCallbackResponse             │ ~400B    │ 1 per step   │
│ HostFunctionRequest                 │ 50-800B  │ Per call     │
│ HostFunctionResponse                │ ~20B     │ Per call     │
│ ContextPropertyRequest              │ ~50B     │ Per access   │
│ ContextPropertyResponse             │ ~80B     │ Per access   │
└─────────────────────────────────────┴──────────┴──────────────┘
```

---

## Performance Analysis

### Latency Breakdown

```
Phase 1 (554ms):
├─ Network: ~4ms (UDS send + receive)
├─ GraalVM Context Creation: ~100ms
├─ Script Parsing: ~50ms
├─ Script Execution: ~390ms
└─ Callbacks: ~10ms

Phase 2 (72ms):
├─ Network: ~4ms
├─ GraalVM Context Creation: ~20ms
├─ Callback Execution: ~40ms
└─ Host Function Calls: ~8ms

Phase 3 (21ms):
├─ Network: ~3ms
├─ GraalVM Context Creation: ~10ms
├─ Callback Execution: ~6ms
└─ Host Function Calls: ~2ms
```

### Optimization Opportunities

```
1. Context Reuse:
   ├─ Current: New context per execution
   ├─ Proposed: Context pooling
   └─ Savings: ~100ms per callback

2. Script Caching:
   ├─ Current: Parse script every time
   ├─ Proposed: Pre-compiled script cache
   └─ Savings: ~50ms per evaluation

3. Binding Compression:
   ├─ Current: Full bindings in every request
   ├─ Proposed: Delta updates
   └─ Savings: ~200 bytes per request

4. Callback Connection Reuse:
   ├─ Current: New connection per execution
   ├─ Proposed: Keep-alive connection
   └─ Savings: ~2ms per callback
```

---

## Error Handling

### Sidecar Crash (Heap Exhaustion)

If the manager scenario executed (user has "manager" role):

```protobuf
// Step 2 callback would trigger heap exhaustion in sidecar

ExecuteCallbackResponse {
  bool success = false;

  int64 elapsed_ms = 0;  // Crash before completion

  SerializedValue result = {null_value: ""};

  map<string, SerializedValue> updated_bindings = {};

  string error_message = "OutOfMemoryError: Java heap space";
}
```

**IS Behavior**:
- Receives error response (or connection close)
- Logs error
- Returns authentication failure to user
- Next authentication creates new sidecar connection
- **No impact on other users** (process isolation)

---

## Conclusion

This reference guide documents every message payload exchanged during the admin user login flow. Key observations:

1. **Lazy Loading**: Context objects use proxies to avoid large serialization
2. **Function Serialization**: Callbacks stored as source code, re-evaluated later
3. **Stateless Sessions**: New session per phase, bindings passed explicitly
4. **Efficient Protocol**: Protocol Buffers over UDS = minimal overhead
5. **Crash Isolation**: Sidecar failures don't affect IS or other sessions

**Total Overhead**: ~650ms for complete 3-step authentication with role checks.

---

**Document Generated**: 2026-02-04
**Protocol Version**: 1.0
**Transport**: Unix Domain Sockets (UDS)
**Serialization**: Protocol Buffers (protobuf3)
