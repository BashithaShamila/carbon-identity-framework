[PERF] [1773985289899] SIDECAR STREAM_OPENED streamOpenTs=1773985289899
[PERF] [1773985289900] SIDECAR EVALUATE_REQUEST_RECEIVED session=32b4da4b-89d5-4d72-9700-abcee5392955 streamOpenTs=1773985289899 receivedTs=1773985289900 sinceStreamOpenMs=1
[PERF] [1773985289908] SIDECAR EVALUATE_HANDLE_START session=32b4da4b-89d5-4d72-9700-abcee5392955 streamOpenTs=1773985289899 handleStartTs=1773985289908 sinceStreamOpenMs=9
[PERF] [1773985289909] SIDECAR EVALUATE_ENGINE_START session=32b4da4b-89d5-4d72-9700-abcee5392955 handleStartTs=1773985289908 engineStartTs=1773985289909 setupMs=1
[DEBUG-SIDECAR] Host function 'executeStep' called with 2 args
[DEBUG-SIDECAR] Converting arg[0]: 1
[DEBUG-SIDECAR] Converted arg[0] to: Double
[DEBUG-SIDECAR] Converting arg[1]: {onSuccess: function (context) {
            // This is scope 1. Variables here die when prompt() is called.
            var user = context.currentKnownSubject;
            var username = user.username;
    ...<omitted>...
}}
[DEBUG-SIDECAR] Converting object with 1 members: [onSuccess]
[DEBUG-SIDECAR] Member 'onSuccess': isNull=false, canExecute=true, hasMembers=true
[DEBUG-SIDECAR] Member 'onSuccess' converted to type: String
[DEBUG-SIDECAR] Final map has 1 entries: [onSuccess]
[DEBUG-SIDECAR] Converted arg[1] to: HashMap
[DEBUG-SIDECAR] Invoking callback to IS for 'executeStep'
[PERF] [1773985289939] SIDECAR HOST_FN_CALLBACK_START session=32b4da4b-89d5-4d72-9700-abcee5392955 function=executeStep startTs=1773985289939
[PERF] [1773985289940] SIDECAR HOST_FN_CALLBACK_SENT session=32b4da4b-89d5-4d72-9700-abcee5392955 function=executeStep startTs=1773985289939 sentTs=1773985289940 sendMs=1
[PERF] [1773985289948] SIDECAR CALLBACK_RESPONSE_RECEIVED session=32b4da4b-89d5-4d72-9700-abcee5392955 type=HOST_FUNCTION_RESPONSE streamOpenTs=1773985289899 receivedTs=1773985289948 sinceStreamOpenMs=49
[PERF] [1773985289948] SIDECAR HOST_FN_CALLBACK_RESPONSE session=32b4da4b-89d5-4d72-9700-abcee5392955 function=executeStep success=true startTs=1773985289939 sentTs=1773985289940 responseTs=1773985289948 waitMs=8 totalRoundtripMs=9
[DEBUG-SIDECAR] Callback returned: null
[PERF] [1773985289951] SIDECAR EVALUATE_ENGINE_DONE session=32b4da4b-89d5-4d72-9700-abcee5392955 engineStartTs=1773985289909 engineEndTs=1773985289951 engineMs=42
[PERF] [1773985289951] SIDECAR EVALUATE_RESPONSE_SENT session=32b4da4b-89d5-4d72-9700-abcee5392955 success=true handleStartTs=1773985289908 engineStartTs=1773985289909 engineEndTs=1773985289951 parseEndTs=1773985289951 sentTs=1773985289951 setupMs=1 engineMs=42 parseMs=0 sendMs=0 totalMs=43 streamLifetimeMs=52
[PERF] [1773985298765] SIDECAR STREAM_OPENED streamOpenTs=1773985298765
[PERF] [1773985298766] SIDECAR EXEC_CALLBACK_REQUEST_RECEIVED session=bec9f7b3-a973-4149-8123-66506549efe1 streamOpenTs=1773985298765 receivedTs=1773985298766 sinceStreamOpenMs=1
[PERF] [1773985298766] SIDECAR EXEC_CALLBACK_HANDLE_START session=bec9f7b3-a973-4149-8123-66506549efe1 streamOpenTs=1773985298765 handleStartTs=1773985298766 sinceStreamOpenMs=1
[PERF] [1773985298766] SIDECAR EXEC_CALLBACK_ENGINE_START session=bec9f7b3-a973-4149-8123-66506549efe1 handleStartTs=1773985298766 engineStartTs=1773985298766 setupMs=0
[PERF] [1773985298790] SIDECAR CTX_PROP_CALLBACK_START session=bec9f7b3-a973-4149-8123-66506549efe1 path=currentKnownSubject startTs=1773985298790
[PERF] [1773985298791] SIDECAR CTX_PROP_CALLBACK_SENT session=bec9f7b3-a973-4149-8123-66506549efe1 path=currentKnownSubject startTs=1773985298790 sentTs=1773985298791 sendMs=1
[PERF] [1773985298793] SIDECAR CALLBACK_RESPONSE_RECEIVED session=bec9f7b3-a973-4149-8123-66506549efe1 type=CONTEXT_PROPERTY_RESPONSE streamOpenTs=1773985298765 receivedTs=1773985298793 sinceStreamOpenMs=28
[PERF] [1773985298794] SIDECAR CTX_PROP_CALLBACK_RESPONSE session=bec9f7b3-a973-4149-8123-66506549efe1 path=currentKnownSubject success=true startTs=1773985298790 sentTs=1773985298791 responseTs=1773985298794 waitMs=3 totalRoundtripMs=4
[PERF] [1773985298794] SIDECAR CTX_PROP_CALLBACK_START session=bec9f7b3-a973-4149-8123-66506549efe1 path=currentKnownSubject::username startTs=1773985298794
[PERF] [1773985298794] SIDECAR CTX_PROP_CALLBACK_SENT session=bec9f7b3-a973-4149-8123-66506549efe1 path=currentKnownSubject::username startTs=1773985298794 sentTs=1773985298794 sendMs=0
[PERF] [1773985298796] SIDECAR CALLBACK_RESPONSE_RECEIVED session=bec9f7b3-a973-4149-8123-66506549efe1 type=CONTEXT_PROPERTY_RESPONSE streamOpenTs=1773985298765 receivedTs=1773985298796 sinceStreamOpenMs=31
[PERF] [1773985298796] SIDECAR CTX_PROP_CALLBACK_RESPONSE session=bec9f7b3-a973-4149-8123-66506549efe1 path=currentKnownSubject::username success=true startTs=1773985298794 sentTs=1773985298794 responseTs=1773985298796 waitMs=2 totalRoundtripMs=2
[pool-1-thread-3] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] Starting Perf Test Flow for: employee1 | test-corr-7010
[DEBUG-SIDECAR] Host function 'httpPost' called with 4 args
[DEBUG-SIDECAR] Converting arg[0]: http://localhost:3500/dummyCreate
[DEBUG-SIDECAR] Converted arg[0] to: String
[DEBUG-SIDECAR] Converting arg[1]: {username: "employee1"}
[DEBUG-SIDECAR] Converting object with 1 members: [username]
[DEBUG-SIDECAR] Member 'username': isNull=false, canExecute=false, hasMembers=false
[DEBUG-SIDECAR] Member 'username' converted to type: String
[DEBUG-SIDECAR] Final map has 1 entries: [username]
[DEBUG-SIDECAR] Converted arg[1] to: HashMap
[DEBUG-SIDECAR] Converting arg[2]: {Accept: "application/json"}
[DEBUG-SIDECAR] Converting object with 1 members: [Accept]
[DEBUG-SIDECAR] Member 'Accept': isNull=false, canExecute=false, hasMembers=false
[DEBUG-SIDECAR] Member 'Accept' converted to type: String
[DEBUG-SIDECAR] Final map has 1 entries: [Accept]
[DEBUG-SIDECAR] Converted arg[2] to: HashMap
[DEBUG-SIDECAR] Converting arg[3]: {onSuccess: function(context, createResponse) {
                    
                    // The Yield happens here. Local scope is destroyed.
                    prompt("genericForm", {
                     ...<omitted>...
}, onFail: function(context, data) {
                    Log.error("Dummy user creation failed");
                }}
[DEBUG-SIDECAR] Converting object with 2 members: [onSuccess, onFail]
[DEBUG-SIDECAR] Member 'onSuccess': isNull=false, canExecute=true, hasMembers=true
[DEBUG-SIDECAR] Member 'onSuccess' converted to type: String
[DEBUG-SIDECAR] Member 'onFail': isNull=false, canExecute=true, hasMembers=true
[DEBUG-SIDECAR] Member 'onFail' converted to type: String
[DEBUG-SIDECAR] Final map has 2 entries: [onFail, onSuccess]
[DEBUG-SIDECAR] Converted arg[3] to: HashMap
[DEBUG-SIDECAR] Invoking callback to IS for 'httpPost'
[PERF] [1773985298800] SIDECAR HOST_FN_CALLBACK_START session=bec9f7b3-a973-4149-8123-66506549efe1 function=httpPost startTs=1773985298800
[PERF] [1773985298800] SIDECAR HOST_FN_CALLBACK_SENT session=bec9f7b3-a973-4149-8123-66506549efe1 function=httpPost startTs=1773985298800 sentTs=1773985298800 sendMs=0
[PERF] [1773985298805] SIDECAR CALLBACK_RESPONSE_RECEIVED session=bec9f7b3-a973-4149-8123-66506549efe1 type=HOST_FUNCTION_RESPONSE streamOpenTs=1773985298765 receivedTs=1773985298805 sinceStreamOpenMs=40
[PERF] [1773985298805] SIDECAR HOST_FN_CALLBACK_RESPONSE session=bec9f7b3-a973-4149-8123-66506549efe1 function=httpPost success=true startTs=1773985298800 sentTs=1773985298800 responseTs=1773985298805 waitMs=5 totalRoundtripMs=5
[DEBUG-SIDECAR] Callback returned: null
[PERF] [1773985298807] SIDECAR EXEC_CALLBACK_ENGINE_DONE session=bec9f7b3-a973-4149-8123-66506549efe1 engineStartTs=1773985298766 engineEndTs=1773985298807 engineMs=41
[PERF] [1773985298808] SIDECAR EXEC_CALLBACK_RESPONSE_SENT session=bec9f7b3-a973-4149-8123-66506549efe1 success=true handleStartTs=1773985298766 engineStartTs=1773985298766 engineEndTs=1773985298807 parseEndTs=1773985298807 sentTs=1773985298808 setupMs=0 engineMs=41 parseMs=0 sendMs=1 totalMs=42 streamLifetimeMs=43
[PERF] [1773985299449] SIDECAR STREAM_OPENED streamOpenTs=1773985299449
[PERF] [1773985299450] SIDECAR EXEC_CALLBACK_REQUEST_RECEIVED session=a71a9452-2b2b-4e4f-ae44-81612e2ccdb3 streamOpenTs=1773985299449 receivedTs=1773985299450 sinceStreamOpenMs=1
[PERF] [1773985299450] SIDECAR EXEC_CALLBACK_HANDLE_START session=a71a9452-2b2b-4e4f-ae44-81612e2ccdb3 streamOpenTs=1773985299449 handleStartTs=1773985299450 sinceStreamOpenMs=1
[PERF] [1773985299450] SIDECAR EXEC_CALLBACK_ENGINE_START session=a71a9452-2b2b-4e4f-ae44-81612e2ccdb3 handleStartTs=1773985299450 engineStartTs=1773985299450 setupMs=0
[DEBUG-SIDECAR] Host function 'prompt' called with 3 args
[DEBUG-SIDECAR] Converting arg[0]: genericForm
[DEBUG-SIDECAR] Converted arg[0] to: String
[DEBUG-SIDECAR] Converting arg[1]: {inputs: [{id: "dummy-wait", label: "Wait"}]}
[DEBUG-SIDECAR] Converting object with 1 members: [inputs]
[DEBUG-SIDECAR] Member 'inputs': isNull=false, canExecute=false, hasMembers=true
[DEBUG-SIDECAR] Converting object with 2 members: [id, label]
[DEBUG-SIDECAR] Member 'id': isNull=false, canExecute=false, hasMembers=false
[DEBUG-SIDECAR] Member 'id' converted to type: String
[DEBUG-SIDECAR] Member 'label': isNull=false, canExecute=false, hasMembers=false
[DEBUG-SIDECAR] Member 'label' converted to type: String
[DEBUG-SIDECAR] Final map has 2 entries: [id, label]
[DEBUG-SIDECAR] Member 'inputs' converted to type: Object[]
[DEBUG-SIDECAR] Final map has 1 entries: [inputs]
[DEBUG-SIDECAR] Converted arg[1] to: HashMap
[DEBUG-SIDECAR] Converting arg[2]: {onSuccess: function(context) {
                            // SCOPE 2: We have crossed the network boundary! 
                            // We must re-fetch 'username' from the hydrated context.
          ...<omitted>...
}, onFail: function(context, data) {
                            // Re-fetch here as well if needed
                            var safeUsername = context.currentKnownSubject ? context.currentKnownSubject.u...<omitted>...
}}
[DEBUG-SIDECAR] Converting object with 2 members: [onSuccess, onFail]
[DEBUG-SIDECAR] Member 'onSuccess': isNull=false, canExecute=true, hasMembers=true
[DEBUG-SIDECAR] Member 'onSuccess' converted to type: String
[DEBUG-SIDECAR] Member 'onFail': isNull=false, canExecute=true, hasMembers=true
[DEBUG-SIDECAR] Member 'onFail' converted to type: String
[DEBUG-SIDECAR] Final map has 2 entries: [onFail, onSuccess]
[DEBUG-SIDECAR] Converted arg[2] to: HashMap
[DEBUG-SIDECAR] Invoking callback to IS for 'prompt'
[PERF] [1773985299463] SIDECAR HOST_FN_CALLBACK_START session=a71a9452-2b2b-4e4f-ae44-81612e2ccdb3 function=prompt startTs=1773985299463
[PERF] [1773985299463] SIDECAR HOST_FN_CALLBACK_SENT session=a71a9452-2b2b-4e4f-ae44-81612e2ccdb3 function=prompt startTs=1773985299463 sentTs=1773985299463 sendMs=0
[PERF] [1773985299468] SIDECAR CALLBACK_RESPONSE_RECEIVED session=a71a9452-2b2b-4e4f-ae44-81612e2ccdb3 type=HOST_FUNCTION_RESPONSE streamOpenTs=1773985299449 receivedTs=1773985299468 sinceStreamOpenMs=19
[PERF] [1773985299468] SIDECAR HOST_FN_CALLBACK_RESPONSE session=a71a9452-2b2b-4e4f-ae44-81612e2ccdb3 function=prompt success=true startTs=1773985299463 sentTs=1773985299463 responseTs=1773985299468 waitMs=5 totalRoundtripMs=5
[DEBUG-SIDECAR] Callback returned: null
[PERF] [1773985299470] SIDECAR EXEC_CALLBACK_ENGINE_DONE session=a71a9452-2b2b-4e4f-ae44-81612e2ccdb3 engineStartTs=1773985299450 engineEndTs=1773985299470 engineMs=20
[PERF] [1773985299470] SIDECAR EXEC_CALLBACK_RESPONSE_SENT session=a71a9452-2b2b-4e4f-ae44-81612e2ccdb3 success=true handleStartTs=1773985299450 engineStartTs=1773985299450 engineEndTs=1773985299470 parseEndTs=1773985299470 sentTs=1773985299470 setupMs=0 engineMs=20 parseMs=0 sendMs=0 totalMs=20 streamLifetimeMs=21
[PERF] [1773985305951] SIDECAR STREAM_OPENED streamOpenTs=1773985305951
[PERF] [1773985305952] SIDECAR EXEC_CALLBACK_REQUEST_RECEIVED session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a streamOpenTs=1773985305951 receivedTs=1773985305952 sinceStreamOpenMs=1
[PERF] [1773985305952] SIDECAR EXEC_CALLBACK_HANDLE_START session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a streamOpenTs=1773985305951 handleStartTs=1773985305952 sinceStreamOpenMs=1
[PERF] [1773985305952] SIDECAR EXEC_CALLBACK_ENGINE_START session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a handleStartTs=1773985305952 engineStartTs=1773985305952 setupMs=0
[PERF] [1773985305957] SIDECAR CTX_PROP_CALLBACK_START session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a path=currentKnownSubject startTs=1773985305957
[PERF] [1773985305957] SIDECAR CTX_PROP_CALLBACK_SENT session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a path=currentKnownSubject startTs=1773985305957 sentTs=1773985305957 sendMs=0
[PERF] [1773985305959] SIDECAR CALLBACK_RESPONSE_RECEIVED session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a type=CONTEXT_PROPERTY_RESPONSE streamOpenTs=1773985305951 receivedTs=1773985305959 sinceStreamOpenMs=8
[PERF] [1773985305959] SIDECAR CTX_PROP_CALLBACK_RESPONSE session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a path=currentKnownSubject success=true startTs=1773985305957 sentTs=1773985305957 responseTs=1773985305959 waitMs=2 totalRoundtripMs=2
[PERF] [1773985305962] SIDECAR CTX_PROP_CALLBACK_START session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a path=currentKnownSubject::username startTs=1773985305962
[PERF] [1773985305962] SIDECAR CTX_PROP_CALLBACK_SENT session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a path=currentKnownSubject::username startTs=1773985305962 sentTs=1773985305962 sendMs=0
[PERF] [1773985305964] SIDECAR CALLBACK_RESPONSE_RECEIVED session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a type=CONTEXT_PROPERTY_RESPONSE streamOpenTs=1773985305951 receivedTs=1773985305964 sinceStreamOpenMs=13
[PERF] [1773985305964] SIDECAR CTX_PROP_CALLBACK_RESPONSE session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a path=currentKnownSubject::username success=true startTs=1773985305962 sentTs=1773985305962 responseTs=1773985305964 waitMs=2 totalRoundtripMs=2
[DEBUG-SIDECAR] Host function 'httpPost' called with 4 args
[DEBUG-SIDECAR] Converting arg[0]: http://localhost:3500/dummyClaims
[DEBUG-SIDECAR] Converted arg[0] to: String
[DEBUG-SIDECAR] Converting arg[1]: {username: "employee1"}
[DEBUG-SIDECAR] Converting object with 1 members: [username]
[DEBUG-SIDECAR] Member 'username': isNull=false, canExecute=false, hasMembers=false
[DEBUG-SIDECAR] Member 'username' converted to type: String
[DEBUG-SIDECAR] Final map has 1 entries: [username]
[DEBUG-SIDECAR] Converted arg[1] to: HashMap
[DEBUG-SIDECAR] Converting arg[2]: {Accept: "application/json"}
[DEBUG-SIDECAR] Converting object with 1 members: [Accept]
[DEBUG-SIDECAR] Member 'Accept': isNull=false, canExecute=false, hasMembers=false
[DEBUG-SIDECAR] Member 'Accept' converted to type: String
[DEBUG-SIDECAR] Final map has 1 entries: [Accept]
[DEBUG-SIDECAR] Converted arg[2] to: HashMap
[DEBUG-SIDECAR] Converting arg[3]: {onSuccess: function(context, claimData) {
                                    
                                    // Map the claims safely
                                    rehydratedUser.localClaims["ht...<omitted>...
}, onFail: function(context, data) {
                                    Log.error("Dummy claims fetch failed for: " + safeUsername);
                                }}
[DEBUG-SIDECAR] Converting object with 2 members: [onSuccess, onFail]
[DEBUG-SIDECAR] Member 'onSuccess': isNull=false, canExecute=true, hasMembers=true
[DEBUG-SIDECAR] Member 'onSuccess' converted to type: String
[DEBUG-SIDECAR] Member 'onFail': isNull=false, canExecute=true, hasMembers=true
[DEBUG-SIDECAR] Member 'onFail' converted to type: String
[DEBUG-SIDECAR] Final map has 2 entries: [onFail, onSuccess]
[DEBUG-SIDECAR] Converted arg[3] to: HashMap
[DEBUG-SIDECAR] Invoking callback to IS for 'httpPost'
[PERF] [1773985305965] SIDECAR HOST_FN_CALLBACK_START session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a function=httpPost startTs=1773985305965
[PERF] [1773985305966] SIDECAR HOST_FN_CALLBACK_SENT session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a function=httpPost startTs=1773985305965 sentTs=1773985305966 sendMs=1
[PERF] [1773985305968] SIDECAR CALLBACK_RESPONSE_RECEIVED session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a type=HOST_FUNCTION_RESPONSE streamOpenTs=1773985305951 receivedTs=1773985305968 sinceStreamOpenMs=17
[PERF] [1773985305968] SIDECAR HOST_FN_CALLBACK_RESPONSE session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a function=httpPost success=true startTs=1773985305965 sentTs=1773985305966 responseTs=1773985305968 waitMs=2 totalRoundtripMs=3
[DEBUG-SIDECAR] Callback returned: null
[PERF] [1773985305969] SIDECAR EXEC_CALLBACK_ENGINE_DONE session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a engineStartTs=1773985305952 engineEndTs=1773985305969 engineMs=17
[PERF] [1773985305969] SIDECAR EXEC_CALLBACK_RESPONSE_SENT session=6181a24e-1b99-4e5a-b6bc-9f38dd60918a success=true handleStartTs=1773985305952 engineStartTs=1773985305952 engineEndTs=1773985305969 parseEndTs=1773985305969 sentTs=1773985305969 setupMs=0 engineMs=17 parseMs=0 sendMs=0 totalMs=17 streamLifetimeMs=18
[PERF] [1773985306789] SIDECAR STREAM_OPENED streamOpenTs=1773985306789
[PERF] [1773985306791] SIDECAR EXEC_CALLBACK_REQUEST_RECEIVED session=4c2698cf-b617-43af-ba05-3a15d3af6a12 streamOpenTs=1773985306789 receivedTs=1773985306791 sinceStreamOpenMs=2
[PERF] [1773985306792] SIDECAR EXEC_CALLBACK_HANDLE_START session=4c2698cf-b617-43af-ba05-3a15d3af6a12 streamOpenTs=1773985306789 handleStartTs=1773985306792 sinceStreamOpenMs=3
[PERF] [1773985306792] SIDECAR EXEC_CALLBACK_ENGINE_START session=4c2698cf-b617-43af-ba05-3a15d3af6a12 handleStartTs=1773985306792 engineStartTs=1773985306792 setupMs=0
[pool-1-thread-3] ERROR org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - PolyglotException during callback execution (streaming)
ReferenceError: rehydratedUser is not defined
	at <js> :anonymous(Unnamed:4:166-179)
	at org.graalvm.polyglot.Value.execute(Value.java:880)
	at org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl.handleExecuteCallback(JsEngineServiceImpl.java:732)
	at org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport$JsEngineStreamingServiceImpl$1.handleExecuteCallback(GrpcStreamingServerTransport.java:400)
	at org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport$JsEngineStreamingServiceImpl$1.lambda$onNext$1(GrpcStreamingServerTransport.java:216)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:829)
[PERF] [1773985306802] SIDECAR EXEC_CALLBACK_ENGINE_DONE session=4c2698cf-b617-43af-ba05-3a15d3af6a12 engineStartTs=1773985306792 engineEndTs=1773985306802 engineMs=10
[PERF] [1773985306804] SIDECAR EXEC_CALLBACK_RESPONSE_SENT session=4c2698cf-b617-43af-ba05-3a15d3af6a12 success=false handleStartTs=1773985306792 engineStartTs=1773985306792 engineEndTs=1773985306802 parseEndTs=1773985306802 sentTs=1773985306804 setupMs=0 engineMs=10 parseMs=0 sendMs=2 totalMs=12 streamLifetimeMs=15
