# Runnable Test Scripts for External Sidecar Validation

Copy each script directly into your test application configuration.

---

## Quick Sanity Check (Run First)

### QS-1: Basic Log and Context Access
```javascript
var onLoginRequest = function(context) {
    Log.info("QS-1: Starting test");
    Log.info("QS-1: Username = " + context.currentKnownSubject.username);
    Log.info("QS-1: Tenant = " + context.getTenantDomain());
    Log.info("QS-1: Test completed successfully");
};
```
**Expected Result:** 3 log lines with username and tenant info
**Failure Mode:** If context access fails differently in external sidecar

---

### QS-2: Simple executeStep
```javascript
var onLoginRequest = function(context) {
    Log.info("QS-2: Before step");
    executeStep(1, {
        onSuccess: function(context) {
            Log.info("QS-2: After step success");
        },
        onFail: function(context) {
            Log.error("QS-2: Step failed");
        }
    });
};
```
**Expected Result:** Both log lines, no errors
**Failure Mode:** Callback not invoked, timeout, or error

---

### QS-3: HTTP Success
```javascript
var onLoginRequest = function(context) {
    Log.info("QS-3: Starting HTTP test");

    httpPost("http://localhost:3000/test",
        {"user": context.currentKnownSubject.username},
        {"Content-Type": "application/json"},
        {
            onSuccess: function(context, response) {
                Log.info("QS-3: HTTP success, got: " +
                    (response.id ? response.id : "empty response"));
            },
            onFail: function(context, error) {
                Log.error("QS-3: HTTP failed: " + error);
            }
        });
};
```
**Expected Result:** Success log with response data
**Failure Mode:** HTTP callback not invoked, serialization error

---

### QS-4: Closure in Synchronous Nesting
```javascript
var onLoginRequest = function(context) {
    var username = context.currentKnownSubject.username;
    Log.info("QS-4: Starting with user: " + username);

    httpPost("http://localhost:3000/api1",
        {"user": username},
        {"Content-Type": "application/json"},
        {
            onSuccess: function(context, r1) {
                // Closure to username should work here
                Log.info("QS-4: Step 1, user still: " + username);

                httpPost("http://localhost:3000/api2",
                    {"prev": r1.id},
                    {"Content-Type": "application/json"},
                    {
                        onSuccess: function(context, r2) {
                            // Deep closure should work
                            Log.info("QS-4: Step 2, original user: " + username);
                            Log.info("QS-4: SUCCESS - Closure works in nested calls");
                        }
                    });
            }
        });
};
```
**Expected Result:** Both nested logs show same username
**Failure Mode:** username becomes undefined in nested callback
**Why it works:** All callbacks execute in same sidecar context before returning to IS

---

### QS-5: Async Boundary with Re-fetch (Correct Pattern)
```javascript
var onLoginRequest = function(context) {
    Log.info("QS-5: Starting");

    httpPost("http://localhost:3000/step1",
        {"user": context.currentKnownSubject.username},
        {},
        {
            onSuccess: function(context, r1) {
                Log.info("QS-5: Step 1 done");

                prompt("testPrompt", {
                    "inputs": [{"id": "test", "label": "Test"}]
                }, {
                    onSuccess: function(context) {
                        // RE-FETCH after async boundary
                        var username = context.currentKnownSubject.username;
                        Log.info("QS-5: After prompt, re-fetched user: " + username);

                        httpPost("http://localhost:3000/step2",
                            {"user": username},
                            {},
                            {
                                onSuccess: function(context, r2) {
                                    Log.info("QS-5: Step 2 done - SUCCESS");
                                }
                            });
                    }
                });
            }
        });
};
```
**Expected Result:** All 4 logs succeed
**Failure Mode:** After prompt, username becomes undefined
**Note:** This is the CORRECT pattern for async code

---

### QS-6: Async Boundary with Closure Dependency (Known Failure)
```javascript
var onLoginRequest = function(context) {
    var userData = context.currentKnownSubject;  // Closure
    Log.info("QS-6: Starting with user: " + userData.username);

    httpPost("http://localhost:3000/step1", {}, {}, {
        onSuccess: function(context, r1) {
            prompt("testPrompt", {"inputs": []}, {
                onSuccess: function(context) {
                    httpPost("http://localhost:3000/step2", {}, {}, {
                        onSuccess: function(context, r2) {
                            // Try to use closure userData
                            // Should fail with ReferenceError: userData is not defined
                            Log.info("QS-6: User from closure: " + userData.username);
                        },
                        onFail: function(context, error) {
                            Log.error("QS-6: Expected failure: " + error);
                        }
                    });
                }
            });
        }
    });
};
```
**Expected Result:** ReferenceError in both default pack AND external sidecar
**Failure Mode:** If they fail DIFFERENTLY, there's a divergence
**Note:** This should fail IDENTICALLY in both - don't fix it

---

## Core Functionality Tests

### CF-1: Multiple Context Property Access
```javascript
var onLoginRequest = function(context) {
    var startTime = new Date().getTime();

    Log.info("CF-1: Username = " + context.currentKnownSubject.username);
    Log.info("CF-1: Store domain = " + context.currentKnownSubject.userStoreDomain);
    Log.info("CF-1: Tenant = " + context.currentKnownSubject.tenantDomain);
    Log.info("CF-1: Current step = " + context.getCurrentStep());
    Log.info("CF-1: Context ID = " + context.getContextIdentifier());

    executeStep(1, {
        onSuccess: function(context) {
            Log.info("CF-1: After step, current step = " + context.getCurrentStep());
            Log.info("CF-1: After step, username = " + context.currentKnownSubject.username);
        }
    });
};
```
**Test for:** All context properties work identically

---

### CF-2: Setting Custom Claims
```javascript
var onLoginRequest = function(context) {
    var user = context.currentKnownSubject;

    if (!user.localClaims) {
        user.localClaims = {};
    }

    user.localClaims["http://wso2.org/claims/custom"] = "custom_value";
    user.localClaims["http://wso2.org/claims/timestamp"] = "" + new Date().getTime();

    Log.info("CF-2: Set custom claim to: " + user.localClaims["http://wso2.org/claims/custom"]);

    executeStep(1, {
        onSuccess: function(context) {
            var user2 = context.currentKnownSubject;
            Log.info("CF-2: After step, claim is: " + user2.localClaims["http://wso2.org/claims/custom"]);
        }
    });
};
```
**Test for:** Claim mutation, persistence

---

### CF-3: Object/Array Serialization
```javascript
var onLoginRequest = function(context) {
    var testObj = {
        str: "hello",
        num: 42,
        bool: true,
        decimal: 3.14,
        arr: [1, 2, 3],
        nested: {
            key: "value",
            arr: ["a", "b", "c"]
        },
        nullVal: null
    };

    Log.info("CF-3: Sending object with " + Object.keys(testObj).length + " keys");

    httpPost("http://localhost:3000/echo-complex",
        testObj,
        {"Content-Type": "application/json"},
        {
            onSuccess: function(context, response) {
                Log.info("CF-3: Received back: " + JSON.stringify(response));

                if (response.nested && response.nested.arr) {
                    Log.info("CF-3: Nested array length: " + response.nested.arr.length);
                }
            }
        });
};
```
**Test for:** Type preservation, nested objects, arrays

---

### CF-4: Iteration and Loops
```javascript
var onLoginRequest = function(context) {
    var claims = context.currentKnownSubject.localClaims;
    var count = 0;

    Log.info("CF-4: Iterating claims");

    for (var claimKey in claims) {
        count++;
        if (count <= 3) {  // Log first 3
            Log.info("CF-4: Claim " + count + ": " + claimKey);
        }
    }

    Log.info("CF-4: Total claims: " + count);
};
```
**Test for:** Object iteration, counting

---

## Error Handling Tests

### EH-1: onFail Callback
```javascript
var onLoginRequest = function(context) {
    Log.info("EH-1: Starting with bad URL");

    httpPost("http://localhost:9999/nonexistent",  // Bad port
        {"test": "data"},
        {},
        {
            onSuccess: function(context, response) {
                Log.error("EH-1: Should not reach success");
            },
            onFail: function(context, error) {
                Log.info("EH-1: Got expected error: " + error);
            }
        });
};
```
**Test for:** Error callback invocation

---

### EH-2: Multiple Failure Paths
```javascript
var onLoginRequest = function(context) {
    executeStep(1, {
        onSuccess: function(context) {
            httpPost("http://localhost:9999/fail", {}, {}, {
                onSuccess: function(context, r) {
                    Log.info("EH-2: Unexpected success");
                },
                onFail: function(context, error) {
                    Log.info("EH-2: HTTP failed as expected");
                }
            });
        },
        onFail: function(context) {
            Log.error("EH-2: Step failed");
        }
    });
};
```
**Test for:** Nested error handling

---

### EH-3: Step Failure
```javascript
var onLoginRequest = function(context) {
    executeStep(99,  // Non-existent step
        {
            onSuccess: function(context) {
                Log.error("EH-3: Should not succeed");
            },
            onFail: function(context) {
                Log.info("EH-3: Step failed as expected");
            }
        });
};
```
**Test for:** Step failure handling

---

## Concurrency Tests (Run with Multiple Users)

### CC-1: Session Isolation
```javascript
var onLoginRequest = function(context) {
    var sessionId = context.getContextIdentifier();
    var username = context.currentKnownSubject.username;

    Log.info("CC-1: Session " + sessionId + " for user " + username);

    httpPost("http://localhost:3000/test",
        {"session": sessionId, "user": username},
        {},
        {
            onSuccess: function(context, response) {
                Log.info("CC-1: Session " + sessionId + " got response " + response.id);
            }
        });
};
```
**How to test:**
1. Open 3 different browsers
2. Login as different users simultaneously
3. Check that each session logs correctly
4. Verify responses correspond to correct sessions
**Failure mode:** Session data mixed up, wrong user gets response

---

### CC-2: Binding Isolation
```javascript
var sessionCounter = {};

var onLoginRequest = function(context) {
    var sid = context.getContextIdentifier();

    if (!sessionCounter[sid]) {
        sessionCounter[sid] = 0;
    }

    sessionCounter[sid]++;

    Log.info("CC-2: Session " + sid + " count: " + sessionCounter[sid]);

    executeStep(1, {
        onSuccess: function(context) {
            sessionCounter[sid]++;
            Log.info("CC-2: After step, session " + sid + " count: " + sessionCounter[sid]);
        }
    });
};
```
**Failure mode:** Counter corrupted, shared state visible across sessions

---

## Stress Tests

### ST-1: Many Sequential Callbacks (Deep Nesting)
```javascript
var onLoginRequest = function(context) {
    var depth = 0;

    function chainCall() {
        if (depth >= 5) {
            Log.info("ST-1: Completed 5 levels");
            return;
        }

        depth++;
        var currentDepth = depth;

        httpPost("http://localhost:3000/ping",
            {"depth": currentDepth},
            {},
            {
                onSuccess: function(context, response) {
                    Log.info("ST-1: Level " + currentDepth + " done");
                    chainCall();
                },
                onFail: function(context, error) {
                    Log.error("ST-1: Level " + currentDepth + " failed");
                }
            });
    }

    chainCall();
};
```
**Test for:** Performance degradation, max nesting depth

---

### ST-2: Large Payload
```javascript
var onLoginRequest = function(context) {
    var largeArray = [];

    for (var i = 0; i < 100; i++) {
        largeArray.push({
            id: i,
            data: "item_" + i,
            nested: {
                value: i * 2,
                tags: ["tag1", "tag2", "tag3"]
            }
        });
    }

    Log.info("ST-2: Sending " + largeArray.length + " items");

    httpPost("http://localhost:3000/bulk",
        {"items": largeArray},
        {"Content-Type": "application/json"},
        {
            onSuccess: function(context, response) {
                Log.info("ST-2: Success, received " + response.processed + " items");
            }
        });
};
```
**Test for:** Serialization limits, payload handling

---

## Edge Cases

### EC-1: Empty Callback
```javascript
var onLoginRequest = function(context) {
    Log.info("EC-1: Before step");

    executeStep(1, {
        onSuccess: function(context) {
            // Intentionally empty
        }
    });

    Log.info("EC-1: After step returns");
};
```
**Test for:** Empty callback handling

---

### EC-2: Null and Undefined Values
```javascript
var onLoginRequest = function(context) {
    var obj = {
        nullField: null,
        undefinedField: undefined,
        emptyString: "",
        zero: 0,
        falsy: false
    };

    Log.info("EC-2: Sending object with null/undefined");

    httpPost("http://localhost:3000/nulltest",
        obj,
        {},
        {
            onSuccess: function(context, response) {
                Log.info("EC-2: Received: " + JSON.stringify(response));
            }
        });
};
```
**Test for:** Null/undefined handling in serialization

---

### EC-3: Special Characters in Strings
```javascript
var onLoginRequest = function(context) {
    var specialData = {
        quotes: 'He said "hello"',
        backslash: "path\\to\\file",
        newline: "line1\nline2",
        tab: "col1\tcol2",
        unicode: "こんにちは 你好 مرحبا",
        emoji: "✓ ✗ ★"
    };

    Log.info("EC-3: Sending special chars");

    httpPost("http://localhost:3000/special",
        specialData,
        {},
        {
            onSuccess: function(context, response) {
                Log.info("EC-3: Received back correctly");
            }
        });
};
```
**Test for:** String escaping, Unicode handling

---

### EC-4: Very Long String
```javascript
var onLoginRequest = function(context) {
    var longString = "";
    for (var i = 0; i < 1000; i++) {
        longString += "x";
    }

    Log.info("EC-4: Sending string of length " + longString.length);

    httpPost("http://localhost:3000/longstring",
        {"data": longString},
        {},
        {
            onSuccess: function(context, response) {
                Log.info("EC-4: Success, response length: " + response.length);
            }
        });
};
```
**Test for:** Large string handling

---

## Performance Baseline Tests

### PB-1: Callback Latency (Single Call)
```javascript
var onLoginRequest = function(context) {
    var startTime = new Date().getTime();
    Log.info("PB-1: Start time: " + startTime);

    httpPost("http://localhost:3000/ping",
        {},
        {},
        {
            onSuccess: function(context, response) {
                var endTime = new Date().getTime();
                var latency = endTime - startTime;
                Log.info("PB-1: Latency: " + latency + "ms");
            }
        });
};
```
**Comparison:** External should be ~2-5x slower due to network

---

### PB-2: Multiple Sequential Calls
```javascript
var onLoginRequest = function(context) {
    var startTime = new Date().getTime();

    function call(num) {
        httpPost("http://localhost:3000/ping",
            {"num": num},
            {},
            {
                onSuccess: function(context, r) {
                    if (num === 1) {
                        var now = new Date().getTime();
                        Log.info("PB-2: Call " + num + " latency: " + (now - startTime) + "ms");
                    }
                    if (num < 3) {
                        call(num + 1);
                    } else {
                        var endTime = new Date().getTime();
                        Log.info("PB-2: Total time for 3 calls: " + (endTime - startTime) + "ms");
                    }
                }
            });
    }

    call(1);
};
```
**Comparison:** Document cumulative latency

---

## Test Results Template

For each test, record:

```
TEST: [Script name]
Date: [When]
Environment: [Default Pack / External Sidecar]

EXECUTION:
- Duration: [X ms]
- Status: [PASS / FAIL / ERROR]
- Error message: [if any]

LOGS:
[Full log output]

OBSERVATIONS:
[Any deviations from expected?]
```

---

## Quick Analysis Checklist

After running all tests, check:

- [ ] All QS tests (1-6) behave identically
- [ ] All CF tests pass in both
- [ ] All EH tests pass in both
- [ ] CC tests show no session contamination
- [ ] ST tests complete without crashes
- [ ] EC tests handle edge cases
- [ ] PB tests show external ~2-5x slower (acceptable)
- [ ] No new error types in external sidecar
- [ ] No callbacks mysteriously fail to invoke
- [ ] Context state preserved correctly

**If all pass:** Your external sidecar is working correctly and ready for production.

**If divergences found:** They indicate real bugs to fix in the external implementation.
