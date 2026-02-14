External service "bashitha@bashitha external-graaljs % java -Xmx512m -jar /Users/bashitha/Downloads/product/external-graaljs/target/graaljs-sidecar-1.0.0-SNAPSHOT.jar grpc
[main] INFO org.wso2.carbon.identity.graaljs.sidecar.Main - [Main] Starting sidecar in gRPC mode
[SIDECAR-STARTUP] Starting GraalJS Sidecar in gRPC mode
[SIDECAR-STARTUP] Port: 50051
[SIDECAR-STARTUP] Statement limit: 5000, Thread pool size: 10
[main] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Started on port: 50051
[main] INFO org.wso2.carbon.identity.graaljs.sidecar.Main - [Main] Sidecar started on: localhost:50051
[SIDECAR-STARTUP] Sidecar listening on: localhost:50051
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] New stream opened
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: EVALUATE_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] handleEvaluate - session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Created streaming callback client
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Created with external delegate for session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] handleEvaluate (streaming) called
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] handleEvaluate (streaming) - session: dbe6dafe-263d-49b5-8c56-4ca4001db790, sourceId: adaptive-script
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Script length: 27878, bindings: 0, hostFunctions: 39
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: executeStep, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: sendError, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: fail, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: showPrompt, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: loadLocalLibrary, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getSecretByName, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: selectAcrFrom, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Creating DYNAMIC context proxy with data: username=, userStoreDomain=, tenantDomain=, step=0
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Bound DYNAMIC context proxy for session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Starting script evaluation (streaming)...
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] ========================================
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] ADAPTIVE SCRIPT STARTED
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] ========================================
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'serviceProviderName', full path: serviceProviderName
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: serviceProviderName, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'serviceProviderName' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] context.serviceProviderName: app1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'tenantDomain', full path: tenantDomain
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: tenantDomain, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'tenantDomain' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] context.tenantDomain: carbon.super
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'currentKnownSubject', full path: currentKnownSubject
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: currentKnownSubject, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'currentKnownSubject' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] context.currentKnownSubject: undefined
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'request', full path: request
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'request', type: servletrequest, keys: 4
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] context.request: EXISTS
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'ip', full path: request::ip
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::ip, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'ip' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] request.ip: 127.0.0.1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'headers', full path: request::headers
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::headers, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'headers', type: writableparameters, keys: 15
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'User-Agent', full path: request::headers::User-Agent
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::headers::User-Agent, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'User-Agent' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] request.headers[User-Agent]: not available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'Host', full path: request::headers::Host
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::headers::Host, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'Host' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] request.headers[Host]: not available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'params', full path: request::params
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::params, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'params', type: parameters, keys: 12
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'sessionDataKey', full path: request::params::sessionDataKey
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::params::sessionDataKey, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'sessionDataKey' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] request.params.sessionDataKey[0]: not available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'type', full path: request::params::type
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::params::type, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'type' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] request.params.type[0]: not available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'cookies', full path: request::cookies
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::cookies, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'cookies', type: writableparameters, keys: none
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'commonAuthId', full path: request::cookies::commonAuthId
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::cookies::commonAuthId, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'commonAuthId' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] request.cookies[commonAuthId]: not available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'response', full path: response
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: response, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'response', type: servletresponse, keys: 1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] context.response: EXISTS
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] dynamicFlag set to: 1
[DEBUG-SIDECAR] Host function 'executeStep' called with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'executeStep' called with 2 args
[DEBUG-SIDECAR] Converting arg[0]: 1
[DEBUG-SIDECAR] Converted arg[0] to: Double
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: Double
[DEBUG-SIDECAR] Converting arg[1]: {onSuccess: function(context) {
            Log.info('========================================');
            Log.info('[TEST] STEP 1 SUCCESS');
            Log.info('========================================...<omitted>...
}, onFail: function(context) {
            Log.info('[TEST] STEP 1 FAILED - user authentication failed');
        }}
[DEBUG-SIDECAR] Converting object with 2 members: [onSuccess, onFail]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting object with 2 members: [onSuccess, onFail]
[DEBUG-SIDECAR] Member 'onSuccess': isNull=false, canExecute=true, hasMembers=true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'onSuccess': isNull=false, canExecute=true, hasMembers=true, hasArrayElements=false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Extracted function source via getSourceLocation: function(context) {
            Log.info('======================================...
[DEBUG-SIDECAR] Member 'onSuccess' converted to type: String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'onSuccess' converted to: function(context) {
            Log.info('==================... (type: String)
[DEBUG-SIDECAR] Member 'onFail': isNull=false, canExecute=true, hasMembers=true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'onFail': isNull=false, canExecute=true, hasMembers=true, hasArrayElements=false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Extracted function source via getSourceLocation: function(context) {
            Log.info('[TEST] STEP 1 FAILED - user authentica...
[DEBUG-SIDECAR] Member 'onFail' converted to type: String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'onFail' converted to: function(context) {
            Log.info('[TEST] STEP 1 FAIL... (type: String)
[DEBUG-SIDECAR] Final map has 2 entries: [onFail, onSuccess]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Final map has 2 entries: [onFail, onSuccess]
[DEBUG-SIDECAR] Converted arg[1] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: HashMap
[DEBUG-SIDECAR] Invoking callback to IS for 'executeStep'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'executeStep' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'executeStep' with 2 args, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: executeStep, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: null
[DEBUG-SIDECAR] Callback returned: null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Script evaluation completed successfully (streaming)
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Evaluate completed in 708ms, success: true
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] New stream opened
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: EXECUTE_CALLBACK_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] handleExecuteCallback - session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Created streaming callback client
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Created with external delegate for session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] handleExecuteCallback (streaming) - session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Function source length: 21943, args: 1, bindings: 4
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: doAssociationWithLocalUser
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: doAssociationWithLocalUser, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: terminateUserSession
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: terminateUserSession, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: assignUserRoles
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: assignUserRoles, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: callChoreo
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: callChoreo, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: checkMicrosoftEmailVerification
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: checkMicrosoftEmailVerification, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getUserSessions
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getUserSessions, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: callElastic
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: callElastic, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: hasAnyOfTheRolesV2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: hasAnyOfTheRolesV2, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: removeAssociatedLocalUser
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: removeAssociatedLocalUser, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: assignUserRolesV2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: assignUserRolesV2, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: resolveMultiAttributeLoginIdentifier
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: resolveMultiAttributeLoginIdentifier, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getUsersWithClaimValues
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getUsersWithClaimValues, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getCookieValue
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getCookieValue, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: fail
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: fail, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: callAnalytics
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: callAnalytics, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: isAnyOfTheRolesAssignedToUser
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: isAnyOfTheRolesAssignedToUser, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: updateUserPassword
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: updateUserPassword, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getUniqueUserWithClaimValues
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getUniqueUserWithClaimValues, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: hasRole
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: hasRole, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getMaskedValue
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getMaskedValue, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getSecretByName
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getSecretByName, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: promptIdentifierForStep
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: promptIdentifierForStep, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: httpGet
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: httpGet, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getAuthenticatedApplications
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getAuthenticatedApplications, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: checkSessionExistence
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: checkSessionExistence, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: httpPost
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: httpPost, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: sendError
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: sendError, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: removeUserRoles
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: removeUserRoles, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: sendEmail
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: sendEmail, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getValueFromDecodedAssertion
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getValueFromDecodedAssertion, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: loadLocalLibrary
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: loadLocalLibrary, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getAssociatedLocalUser
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getAssociatedLocalUser, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: removeUserRolesV2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: removeUserRolesV2, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: filterAuthenticators
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: filterAuthenticators, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: setCookie
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: setCookie, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: hasAnyOfTheRoles
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: hasAnyOfTheRoles, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: publishToAnalytics
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: publishToAnalytics, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: isMemberOfAnyOfGroups
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: isMemberOfAnyOfGroups, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: executeStep
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: executeStep, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: prompt
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: prompt, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registered 41 host function stubs
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Creating DYNAMIC context proxy with data: username=admin1, userStoreDomain=PRIMARY, tenantDomain=carbon.super, step=1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] ========================================
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] STEP 1 SUCCESS
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] ========================================
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'steps', full path: steps
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'steps', type: steps, keys: none
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember '1', full path: steps::1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for '1', type: step, keys: 4
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'idp', full path: steps::1::idp
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::idp, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'idp' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] step.idp: LOCAL
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'authenticator', full path: steps::1::authenticator
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::authenticator, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'authenticator' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] step.authenticator: BasicAuthenticator
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'subject', full path: steps::1::subject
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'subject', type: authenticateduser, keys: 8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'uniqueId', full path: steps::1::subject::uniqueId
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::uniqueId, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'uniqueId' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.uniqueId: 9ed0e960-61fc-4e59-9106-6334c3282edc
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'username', full path: steps::1::subject::username
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::username, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'username' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.username: admin1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'userStoreDomain', full path: steps::1::subject::userStoreDomain
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::userStoreDomain, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'userStoreDomain' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.userStoreDomain: PRIMARY
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'localClaims', full path: steps::1::subject::localClaims
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::localClaims, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'localClaims', type: claims, keys: none
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'http://wso2.org/claims/givenname', full path: steps::1::subject::localClaims::http://wso2.org/claims/givenname
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/givenname, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'http://wso2.org/claims/givenname' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.localClaims[givenname]: admin1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'http://wso2.org/claims/lastname', full path: steps::1::subject::localClaims::http://wso2.org/claims/lastname
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/lastname, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'http://wso2.org/claims/lastname' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.localClaims[lastname]: 1admin
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'http://wso2.org/claims/emailaddress', full path: steps::1::subject::localClaims::http://wso2.org/claims/emailaddress
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/emailaddress, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'http://wso2.org/claims/emailaddress' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.localClaims[emailaddress]: bs@email.com
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'http://wso2.org/claims/mobile', full path: steps::1::subject::localClaims::http://wso2.org/claims/mobile
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/mobile, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'http://wso2.org/claims/mobile' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.localClaims[mobile]: not set
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'http://wso2.org/claims/country', full path: steps::1::subject::localClaims::http://wso2.org/claims/country
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/country, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'http://wso2.org/claims/country' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.localClaims[country]: not set
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'claims', full path: steps::1::subject::claims
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::claims, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'claims', type: runtimeclaims, keys: none
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] putMember 'steps::1::subject::claims::http://wso2.org/claims/testSessionClaim' = testValue123
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] setContextProperty 'steps::1::subject::claims::http://wso2.org/claims/testSessionClaim' (type: runtimeclaims), session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] setContextProperty: steps::1::subject::claims::http://wso2.org/claims/testSessionClaim, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertySetRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_SET_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_SET_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertySetResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.claims[testSessionClaim] (after set): testValue123
[DEBUG-SIDECAR] Host function 'isMemberOfAnyOfGroups' called with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'isMemberOfAnyOfGroups' called with 2 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@4f674667
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=authenticateduser, basePath=steps::1::subject
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=authenticateduser, basePath=steps::1::subject
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Converting arg[1]: (2)["admin", "Internal/everyone"]
[DEBUG-SIDECAR] Converted arg[1] to: Object[]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: Object[]
[DEBUG-SIDECAR] Invoking callback to IS for 'isMemberOfAnyOfGroups'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'isMemberOfAnyOfGroups' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'isMemberOfAnyOfGroups' with 2 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: isMemberOfAnyOfGroups, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: Boolean
[DEBUG-SIDECAR] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] isMemberOfAnyOfGroups(admin, Internal/everyone): true
[DEBUG-SIDECAR] Host function 'hasAnyOfTheRolesV2' called with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'hasAnyOfTheRolesV2' called with 2 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@64065fde
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=context, basePath=
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=context, basePath=
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Converting arg[1]: (3)["Internal/admin", "admin", "Application/admin"]
[DEBUG-SIDECAR] Converted arg[1] to: Object[]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: Object[]
[DEBUG-SIDECAR] Invoking callback to IS for 'hasAnyOfTheRolesV2'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'hasAnyOfTheRolesV2' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'hasAnyOfTheRolesV2' with 2 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: hasAnyOfTheRolesV2, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: Boolean
[DEBUG-SIDECAR] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] hasAnyOfTheRolesV2(admin): true
[DEBUG-SIDECAR] Host function 'hasAnyOfTheRolesV2' called with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'hasAnyOfTheRolesV2' called with 2 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@64065fde
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=context, basePath=
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=context, basePath=
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Converting arg[1]: ["manager"]
[DEBUG-SIDECAR] Converted arg[1] to: Object[]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: Object[]
[DEBUG-SIDECAR] Invoking callback to IS for 'hasAnyOfTheRolesV2'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'hasAnyOfTheRolesV2' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'hasAnyOfTheRolesV2' with 2 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: hasAnyOfTheRolesV2, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: Boolean
[DEBUG-SIDECAR] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] hasAnyOfTheRolesV2(manager): false
[DEBUG-SIDECAR] Host function 'hasAnyOfTheRolesV2' called with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'hasAnyOfTheRolesV2' called with 2 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@64065fde
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=context, basePath=
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=context, basePath=
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Converting arg[1]: (2)["admin", "manager"]
[DEBUG-SIDECAR] Converted arg[1] to: Object[]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: Object[]
[DEBUG-SIDECAR] Invoking callback to IS for 'hasAnyOfTheRolesV2'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'hasAnyOfTheRolesV2' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'hasAnyOfTheRolesV2' with 2 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: hasAnyOfTheRolesV2, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: Boolean
[DEBUG-SIDECAR] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] hasAnyOfTheRolesV2(admin,manager): true
[DEBUG-SIDECAR] Host function 'getUserSessions' called with 1 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'getUserSessions' called with 1 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@4f674667
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=authenticateduser, basePath=steps::1::subject
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=authenticateduser, basePath=steps::1::subject
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Invoking callback to IS for 'getUserSessions'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'getUserSessions' with 1 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'getUserSessions' with 1 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: getUserSessions, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: ArrayList
[DEBUG-SIDECAR] Callback returned: ArrayList
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: ArrayList
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] getUserSessions count: 0
[DEBUG-SIDECAR] Host function 'getUniqueUserWithClaimValues' called with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'getUniqueUserWithClaimValues' called with 2 args
[DEBUG-SIDECAR] Converting arg[0]: {http://wso2.org/claims/username: "admin1"}
[DEBUG-SIDECAR] Converting object with 1 members: [http://wso2.org/claims/username]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting object with 1 members: [http://wso2.org/claims/username]
[DEBUG-SIDECAR] Member 'http://wso2.org/claims/username': isNull=false, canExecute=false, hasMembers=false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'http://wso2.org/claims/username': isNull=false, canExecute=false, hasMembers=false, hasArrayElements=false
[DEBUG-SIDECAR] Member 'http://wso2.org/claims/username' converted to type: String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'http://wso2.org/claims/username' converted to: admin1... (type: String)
[DEBUG-SIDECAR] Final map has 1 entries: [http://wso2.org/claims/username]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Final map has 1 entries: [http://wso2.org/claims/username]
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Converting arg[1]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@64065fde
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=context, basePath=
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=context, basePath=
[DEBUG-SIDECAR] Converted arg[1] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: HashMap
[DEBUG-SIDECAR] Invoking callback to IS for 'getUniqueUserWithClaimValues'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'getUniqueUserWithClaimValues' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'getUniqueUserWithClaimValues' with 2 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: getUniqueUserWithClaimValues, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: String
[DEBUG-SIDECAR] Callback returned: String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] getUniqueUserWithClaimValues: undefined
[DEBUG-SIDECAR] Host function 'getUsersWithClaimValues' called with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'getUsersWithClaimValues' called with 2 args
[DEBUG-SIDECAR] Converting arg[0]: {http://wso2.org/claims/username: "admin1"}
[DEBUG-SIDECAR] Converting object with 1 members: [http://wso2.org/claims/username]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting object with 1 members: [http://wso2.org/claims/username]
[DEBUG-SIDECAR] Member 'http://wso2.org/claims/username': isNull=false, canExecute=false, hasMembers=false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'http://wso2.org/claims/username': isNull=false, canExecute=false, hasMembers=false, hasArrayElements=false
[DEBUG-SIDECAR] Member 'http://wso2.org/claims/username' converted to type: String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'http://wso2.org/claims/username' converted to: admin1... (type: String)
[DEBUG-SIDECAR] Final map has 1 entries: [http://wso2.org/claims/username]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Final map has 1 entries: [http://wso2.org/claims/username]
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Converting arg[1]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@64065fde
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=context, basePath=
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=context, basePath=
[DEBUG-SIDECAR] Converted arg[1] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: HashMap
[DEBUG-SIDECAR] Invoking callback to IS for 'getUsersWithClaimValues'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'getUsersWithClaimValues' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'getUsersWithClaimValues' with 2 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: getUsersWithClaimValues, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: ArrayList
[DEBUG-SIDECAR] Callback returned: ArrayList
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: ArrayList
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] getUsersWithClaimValues count: 1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'response', full path: response
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: response, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'response', type: servletresponse, keys: 1
[DEBUG-SIDECAR] Host function 'setCookie' called with 4 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'setCookie' called with 4 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@3256c84a
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=servletresponse, basePath=response
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=servletresponse, basePath=response
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Converting arg[1]: testAdaptiveCookie
[DEBUG-SIDECAR] Converted arg[1] to: String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: String
[DEBUG-SIDECAR] Converting arg[2]: cookieVal123
[DEBUG-SIDECAR] Converted arg[2] to: String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[2] to: String
[DEBUG-SIDECAR] Converting arg[3]: {max-age: 3600, path: "/", httpOnly: true, secure: true, sameSite: "LAX", encrypt: false, sign: false}
[DEBUG-SIDECAR] Converting object with 7 members: [max-age, path, httpOnly, secure, sameSite, encrypt, sign]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting object with 7 members: [max-age, path, httpOnly, secure, sameSite, encrypt, sign]
[DEBUG-SIDECAR] Member 'max-age': isNull=false, canExecute=false, hasMembers=false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'max-age': isNull=false, canExecute=false, hasMembers=false, hasArrayElements=false
[DEBUG-SIDECAR] Member 'max-age' converted to type: Double
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'max-age' converted to: 3600.0 (type: Double)
[DEBUG-SIDECAR] Member 'path': isNull=false, canExecute=false, hasMembers=false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'path': isNull=false, canExecute=false, hasMembers=false, hasArrayElements=false
[DEBUG-SIDECAR] Member 'path' converted to type: String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'path' converted to: /... (type: String)
[DEBUG-SIDECAR] Member 'httpOnly': isNull=false, canExecute=false, hasMembers=false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'httpOnly': isNull=false, canExecute=false, hasMembers=false, hasArrayElements=false
[DEBUG-SIDECAR] Member 'httpOnly' converted to type: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'httpOnly' converted to: true (type: Boolean)
[DEBUG-SIDECAR] Member 'secure': isNull=false, canExecute=false, hasMembers=false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'secure': isNull=false, canExecute=false, hasMembers=false, hasArrayElements=false
[DEBUG-SIDECAR] Member 'secure' converted to type: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'secure' converted to: true (type: Boolean)
[DEBUG-SIDECAR] Member 'sameSite': isNull=false, canExecute=false, hasMembers=false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'sameSite': isNull=false, canExecute=false, hasMembers=false, hasArrayElements=false
[DEBUG-SIDECAR] Member 'sameSite' converted to type: String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'sameSite' converted to: LAX... (type: String)
[DEBUG-SIDECAR] Member 'encrypt': isNull=false, canExecute=false, hasMembers=false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'encrypt': isNull=false, canExecute=false, hasMembers=false, hasArrayElements=false
[DEBUG-SIDECAR] Member 'encrypt' converted to type: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'encrypt' converted to: false (type: Boolean)
[DEBUG-SIDECAR] Member 'sign': isNull=false, canExecute=false, hasMembers=false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'sign': isNull=false, canExecute=false, hasMembers=false, hasArrayElements=false
[DEBUG-SIDECAR] Member 'sign' converted to type: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'sign' converted to: false (type: Boolean)
[DEBUG-SIDECAR] Final map has 7 entries: [path, encrypt, sameSite, max-age, sign, httpOnly, secure]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Final map has 7 entries: [path, encrypt, sameSite, max-age, sign, httpOnly, secure]
[DEBUG-SIDECAR] Converted arg[3] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[3] to: HashMap
[DEBUG-SIDECAR] Invoking callback to IS for 'setCookie'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'setCookie' with 4 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'setCookie' with 4 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: setCookie, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: null
[DEBUG-SIDECAR] Callback returned: null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] setCookie: SUCCESS
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'request', full path: request
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'request', type: servletrequest, keys: 4
[DEBUG-SIDECAR] Host function 'getCookieValue' called with 3 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'getCookieValue' called with 3 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@1673fb1b
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=servletrequest, basePath=request
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=servletrequest, basePath=request
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Converting arg[1]: testAdaptiveCookie
[DEBUG-SIDECAR] Converted arg[1] to: String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: String
[DEBUG-SIDECAR] Converting arg[2]: {decrypt: false, validateSignature: false}
[DEBUG-SIDECAR] Converting object with 2 members: [decrypt, validateSignature]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting object with 2 members: [decrypt, validateSignature]
[DEBUG-SIDECAR] Member 'decrypt': isNull=false, canExecute=false, hasMembers=false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'decrypt': isNull=false, canExecute=false, hasMembers=false, hasArrayElements=false
[DEBUG-SIDECAR] Member 'decrypt' converted to type: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'decrypt' converted to: false (type: Boolean)
[DEBUG-SIDECAR] Member 'validateSignature': isNull=false, canExecute=false, hasMembers=false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'validateSignature': isNull=false, canExecute=false, hasMembers=false, hasArrayElements=false
[DEBUG-SIDECAR] Member 'validateSignature' converted to type: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'validateSignature' converted to: false (type: Boolean)
[DEBUG-SIDECAR] Final map has 2 entries: [validateSignature, decrypt]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Final map has 2 entries: [validateSignature, decrypt]
[DEBUG-SIDECAR] Converted arg[2] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[2] to: HashMap
[DEBUG-SIDECAR] Invoking callback to IS for 'getCookieValue'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'getCookieValue' with 3 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'getCookieValue' with 3 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: getCookieValue, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: null
[DEBUG-SIDECAR] Callback returned: null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] getCookieValue(testAdaptiveCookie): not found
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] dynamicFlag in step1 callback: 1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] dynamicFlag === 1: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'params', full path: request::params
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::params, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'params', type: parameters, keys: 4
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'sessionDataKey', full path: request::params::sessionDataKey
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::params::sessionDataKey, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'sessionDataKey' = ArrayList
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] (post-step) request.params.sessionDataKey[0]: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'username', full path: request::params::username
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::params::username, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'username' = ArrayList
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] (post-step) request.params.username[0]: admin1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'headers', full path: response::headers
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: response::headers, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'headers', type: headers, keys: 3
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] putMember 'response::headers::X-Adaptive-Test' = script-executed
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] setContextProperty 'response::headers::X-Adaptive-Test' (type: headers), session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] setContextProperty: response::headers::X-Adaptive-Test, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertySetRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_SET_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_SET_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertySetResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] response.headers[X-Adaptive-Test]: SET
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] User has admin/manager role AND dynamicFlag === 1. Executing Step 2
[DEBUG-SIDECAR] Host function 'executeStep' called with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'executeStep' called with 2 args
[DEBUG-SIDECAR] Converting arg[0]: 2
[DEBUG-SIDECAR] Converted arg[0] to: Double
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: Double
[DEBUG-SIDECAR] Converting arg[1]: {onSuccess: function(context) {
                        Log.info('========================================');
                        Log.info('[TEST] STEP 2 SUCCESS');
                        Log.info('====...<omitted>...
}, onFail: function(context) {
                        Log.info('[TEST] STEP 2 FAILED');
                    }}
[DEBUG-SIDECAR] Converting object with 2 members: [onSuccess, onFail]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting object with 2 members: [onSuccess, onFail]
[DEBUG-SIDECAR] Member 'onSuccess': isNull=false, canExecute=true, hasMembers=true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'onSuccess': isNull=false, canExecute=true, hasMembers=true, hasArrayElements=false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Extracted function source via getSourceLocation: function(context) {
                        Log.info('==========================...
[DEBUG-SIDECAR] Member 'onSuccess' converted to type: String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'onSuccess' converted to: function(context) {
                        Log.info('======... (type: String)
[DEBUG-SIDECAR] Member 'onFail': isNull=false, canExecute=true, hasMembers=true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'onFail': isNull=false, canExecute=true, hasMembers=true, hasArrayElements=false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Extracted function source via getSourceLocation: function(context) {
                        Log.info('[TEST] STEP 2 FAILED');
  ...
[DEBUG-SIDECAR] Member 'onFail' converted to type: String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Member 'onFail' converted to: function(context) {
                        Log.info('[TEST]... (type: String)
[DEBUG-SIDECAR] Final map has 2 entries: [onFail, onSuccess]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Final map has 2 entries: [onFail, onSuccess]
[DEBUG-SIDECAR] Converted arg[1] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: HashMap
[DEBUG-SIDECAR] Invoking callback to IS for 'executeStep'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'executeStep' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'executeStep' with 2 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: executeStep, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: null
[DEBUG-SIDECAR] Callback returned: null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] ExecuteCallback completed in 338ms, success: true
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] New stream opened
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: EXECUTE_CALLBACK_REQUEST, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] handleExecuteCallback - session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Created streaming callback client
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Created with external delegate for session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] handleExecuteCallback (streaming) - session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Function source length: 2411, args: 1, bindings: 4
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: doAssociationWithLocalUser
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: doAssociationWithLocalUser, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: terminateUserSession
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: terminateUserSession, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: assignUserRoles
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: assignUserRoles, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: callChoreo
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: callChoreo, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: checkMicrosoftEmailVerification
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: checkMicrosoftEmailVerification, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getUserSessions
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getUserSessions, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: callElastic
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: callElastic, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: hasAnyOfTheRolesV2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: hasAnyOfTheRolesV2, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: removeAssociatedLocalUser
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: removeAssociatedLocalUser, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: assignUserRolesV2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: assignUserRolesV2, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: resolveMultiAttributeLoginIdentifier
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: resolveMultiAttributeLoginIdentifier, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getUsersWithClaimValues
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getUsersWithClaimValues, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getCookieValue
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getCookieValue, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: fail
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: fail, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: callAnalytics
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: callAnalytics, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: isAnyOfTheRolesAssignedToUser
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: isAnyOfTheRolesAssignedToUser, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: updateUserPassword
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: updateUserPassword, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getUniqueUserWithClaimValues
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getUniqueUserWithClaimValues, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: hasRole
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: hasRole, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getMaskedValue
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getMaskedValue, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getSecretByName
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getSecretByName, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: promptIdentifierForStep
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: promptIdentifierForStep, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: httpGet
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: httpGet, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getAuthenticatedApplications
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getAuthenticatedApplications, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: checkSessionExistence
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: checkSessionExistence, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: httpPost
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: httpPost, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: sendError
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: sendError, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: removeUserRoles
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: removeUserRoles, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: sendEmail
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: sendEmail, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getValueFromDecodedAssertion
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getValueFromDecodedAssertion, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: loadLocalLibrary
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: loadLocalLibrary, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: getAssociatedLocalUser
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getAssociatedLocalUser, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: removeUserRolesV2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: removeUserRolesV2, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: filterAuthenticators
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: filterAuthenticators, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: setCookie
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: setCookie, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: hasAnyOfTheRoles
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: hasAnyOfTheRoles, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: publishToAnalytics
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: publishToAnalytics, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: isMemberOfAnyOfGroups
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: isMemberOfAnyOfGroups, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: executeStep
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: executeStep, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registering host function stub: prompt
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: prompt, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Registered 41 host function stubs
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Creating DYNAMIC context proxy with data: username=admin1, userStoreDomain=PRIMARY, tenantDomain=carbon.super, step=2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] ========================================
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] STEP 2 SUCCESS
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] ========================================
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'steps', full path: steps
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'steps', type: steps, keys: none
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember '2', full path: steps::2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::2, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for '2', type: step, keys: 4
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'idp', full path: steps::2::idp
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::2::idp, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'idp' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] step2.idp: LOCAL
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'authenticator', full path: steps::2::authenticator
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::2::authenticator, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'authenticator' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] step2.authenticator: totp
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'subject', full path: steps::2::subject
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::2::subject, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'subject', type: authenticateduser, keys: 8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'username', full path: steps::2::subject::username
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::2::subject::username, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'username' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] step2.subject.username: admin1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] Step 2 callback: dynamicFlag = 2
[DEBUG-SIDECAR] Host function 'hasAnyOfTheRolesV2' called with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'hasAnyOfTheRolesV2' called with 2 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@24241dae
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=context, basePath=
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=context, basePath=
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Converting arg[1]: ["manager"]
[DEBUG-SIDECAR] Converted arg[1] to: Object[]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: Object[]
[DEBUG-SIDECAR] Invoking callback to IS for 'hasAnyOfTheRolesV2'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'hasAnyOfTheRolesV2' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'hasAnyOfTheRolesV2' with 2 args, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: hasAnyOfTheRolesV2, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: Boolean
[DEBUG-SIDECAR] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] hasAnyOfTheRolesV2(manager) in Step 2: false
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] Step 3 skipped. Manager: false, dynamicFlag: 2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] ========================================
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] ALL ACTIVE TESTS COMPLETED
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] ========================================
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] ExecuteCallback completed in 29ms, success: true
" ,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

Identity Server Log "ashitha@bashitha bin % sh wso2server.sh run -Dosgi.clean=true
JAVA_HOME environment variable is set to /Library/Java/JavaVirtualMachines/temurin-11.jdk/Contents/Home
CARBON_HOME environment variable is set to /Users/bashitha/Downloads/product/wso2is-7.2.1-SNAPSHOT
Using Java memory options: -Xms256m -Xmx1024m
[2026-02-10 12:32:06,957] []  INFO {org.ops4j.pax.logging.spi.support.EventAdminConfigurationNotifier} - Sending Event Admin notification (configuration successful) to org/ops4j/pax/logging/Configuration
[2026-02-10 12:32:07,078] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - Starting WSO2 Carbon...
[2026-02-10 12:32:07,079] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - Operating System : Mac OS X 26.2, aarch64
[2026-02-10 12:32:07,079] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - Java Home        : /Library/Java/JavaVirtualMachines/temurin-11.jdk/Contents/Home
[2026-02-10 12:32:07,079] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - Java Version     : 11.0.29
[2026-02-10 12:32:07,079] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - Java VM          : OpenJDK 64-Bit Server VM 11.0.29+7,Eclipse Adoptium
[2026-02-10 12:32:07,079] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - Carbon Home      : /Users/bashitha/Downloads/product/wso2is-7.2.1-SNAPSHOT
[2026-02-10 12:32:07,079] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - Java Temp Dir    : /Users/bashitha/Downloads/product/wso2is-7.2.1-SNAPSHOT/tmp
[2026-02-10 12:32:07,079] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - User             : bashitha, en-LK, Asia/Colombo
[2026-02-10 12:32:09,888] []  INFO {org.apache.jasper.servlet.TldScanner} - At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
[2026-02-10 12:32:12,584] []  INFO {org.wso2.carbon.registry.indexing.solr.SolrClient} - Default Embedded Solr Server Initialized
[2026-02-10 12:32:13,044] []  INFO {org.apache.axis2.transport.mail.MailTransportSender} - MAILTO Sender started
[2026-02-10 12:32:13,117] []  INFO {org.wso2.carbon.core.init.CarbonServerManager} - Repository       : /Users/bashitha/Downloads/product/wso2is-7.2.1-SNAPSHOT/repository/deployment/server/
[2026-02-10 12:32:13,136] []  INFO {org.wso2.carbon.core.multitenancy.eager.TenantLoadingConfig} - Using tenant lazy loading policy...
[2026-02-10 12:32:13,142] []  INFO {org.wso2.carbon.core.internal.permission.update.PermissionUpdater} - Permission cache updated for tenant -1234
[2026-02-10 12:32:15,278] []  WARN {org.wso2.carbon.identity.auth.service.internal.AuthenticationServiceComponent} - 

##################################  ALERT  ##################################
[WARNING]: Internal authentication is utilizing default credentials,
which may expose the environment to potential security risks.
If this is a production environment, change the credentials immediately.
#############################################################################

[2026-02-10 12:32:15,297] []  WARN {org.wso2.carbon.identity.event.internal.IdentityEventServiceComponent} - Properties for IdentityFraudDetectorEventHandler is not configured. This event handler will not be activated
[2026-02-10 12:32:16,609] []  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.JsEngineFactory} - JsEngineFactory initialized. Mode: REMOTE, Transport: GRPC, gRPC Target: localhost:50051
[2026-02-10 12:32:16,818] []  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.HostCallbackServer} - Host callback server started at: /tmp/graaljs-callback-17fd90f3.sock
[2026-02-10 12:32:16,818] []  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.HostCallbackServer} - [HostCallbackServer] Starting accept loop...
[2026-02-10 12:32:16,818] []  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.HostCallbackServer} - [HostCallbackServer] Waiting for callback connection...
[2026-02-10 12:32:16,818] []  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilderFactory} - GraalJS engine abstraction initialized in REMOTE mode
[2026-02-10 12:32:16,921] []  INFO {org.wso2.carbon.identity.application.authentication.framework.store.SessionDataStore} - Thread pool size for temporary authentication context data delete task: 20
[2026-02-10 12:32:17,069] []  INFO {org.opensaml.core.config.InitializationService} - Initializing OpenSAML using the Java Services API
[2026-02-10 12:32:18,832] []  INFO {org.wso2.carbon.user.core.common.UserStoreDeploymentManager} - Realm configuration of tenant:-1234  modified with /Users/bashitha/Downloads/product/wso2is-7.2.1-SNAPSHOT/repository/deployment/server/userstores/AGENT.xml
[2026-02-10 12:32:22,031] []  INFO {org.hibernate.validator.internal.util.Version} - HV000001: Hibernate Validator 6.2.5.Final
[2026-02-10 12:32:28,316] []  INFO {org.wso2.carbon.core.transports.http.HttpTransportListener} - HTTP port        : 9763
[2026-02-10 12:32:28,317] []  INFO {org.wso2.carbon.core.transports.http.HttpsTransportListener} - HTTPS port       : 9443
[2026-02-10 12:32:28,506] []  WARN {org.apache.tomcat.util.net.SSLUtilBase} - The trusted certificate with alias [wso2carbon] and DN [CN=localhost, OU=WSO2, O=WSO2, L=Santa Clara, ST=CA, C=US] is not valid due to [NotAfter: Thu Jan 08 14:02:07 IST 2026]. Certificates signed by this trusted certificate WILL be accepted
[2026-02-10 12:32:28,510] []  INFO {org.apache.tomcat.util.net.NioEndpoint.certificate} - Connector [https-jsse-nio-9443], TLS virtual host [_default_], certificate type [UNDEFINED] configured from keystore [/Users/bashitha/Downloads/product/wso2is-7.2.1-SNAPSHOT/repository/resources/security/wso2carbon.p12] using alias [wso2carbon] with trust store [/Users/bashitha/Downloads/product/wso2is-7.2.1-SNAPSHOT/repository/resources/security/client-truststore.p12]
[2026-02-10 12:32:28,531] []  INFO {org.wso2.identity.apps.common.internal.AppsCommonServiceStartupObserver} - My Account URL : https://localhost:9443/myaccount
[2026-02-10 12:32:28,531] []  INFO {org.wso2.identity.apps.common.internal.AppsCommonServiceStartupObserver} - Console URL : https://localhost:9443/console
[2026-02-10 12:32:28,531] []  INFO {org.wso2.carbon.core.internal.StartupFinalizerServiceComponent} - Server           :  WSO2 Identity Server-7.2.1-SNAPSHOT
[2026-02-10 12:32:28,532] []  INFO {org.wso2.carbon.core.internal.StartupFinalizerServiceComponent} - WSO2 Carbon started in 24 sec
[2026-02-10 12:32:28,544] []  INFO {org.wso2.carbon.healthcheck.api.core.internal.HealthMonitorServiceComponent} - Carbon health monitoring service is activated..
[2026-02-10 12:33:19,617] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWith] Using REMOTE execution mode via sidecar
[2026-02-10 12:33:19,618] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createTransport() CALLED ==========
[2026-02-10 12:33:19,618] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Transport instance #1
[2026-02-10 12:33:19,618] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Config details - grpcTarget: localhost:50051, callbackPort: 0
[2026-02-10 12:33:19,619] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Creating NEW GrpcStreamingTransportImpl for target: localhost:50051
[2026-02-10 12:33:19,623] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcConnectionManager} - [GrpcConnectionManager] Configuration loaded - IdleTimeout: 180s, CallbackPort: 50052
[2026-02-10 12:33:19,623] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Created streaming transport for target: localhost:50051, timeout: 30s, correlationId: 26a769db-5854-479c-966d-547d0c3fe826
[2026-02-10 12:33:19,623] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] NEW singleton created, hashCode=1332500867
[2026-02-10 12:33:19,624] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Returning streaming transport, hashCode=1332500867
[2026-02-10 12:33:19,624] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createTransport() COMPLETED ==========
[2026-02-10 12:33:19,624] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createCallbackServer() CALLED ==========
[2026-02-10 12:33:19,624] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Config details - callbackPort: 0, grpcTarget: localhost:50051
[2026-02-10 12:33:19,624] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Returning streaming transport as callback server, hashCode=1332500867
[2026-02-10 12:33:19,624] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createCallbackServer() COMPLETED ==========
[2026-02-10 12:33:19,627] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Created with session: dbe6dafe-263d-49b5-8c56-4ca4001db790, transport: GrpcStreamingTransportImpl, callbackServer: GrpcStreamingTransportImpl, SP: app1
[2026-02-10 12:33:19,627] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWithRemote] Starting for SP: app1, contextId: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:19,627] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWithRemote] Registered 39 host functions: [doAssociationWithLocalUser, getAuthenticatedApplications, terminateUserSession, assignUserRoles, callChoreo, checkSessionExistence, httpPost, sendError, checkMicrosoftEmailVerification, removeUserRoles, sendEmail, getUserSessions, callElastic, hasAnyOfTheRolesV2, getValueFromDecodedAssertion, removeAssociatedLocalUser, loadLocalLibrary, assignUserRolesV2, getAssociatedLocalUser, removeUserRolesV2, filterAuthenticators, setCookie, resolveMultiAttributeLoginIdentifier, getUsersWithClaimValues, getCookieValue, callAnalytics, isAnyOfTheRolesAssignedToUser, updateUserPassword, hasAnyOfTheRoles, getUniqueUserWithClaimValues, hasRole, getMaskedValue, getSecretByName, publishToAnalytics, isMemberOfAnyOfGroups, executeStep, promptIdentifierForStep, prompt, httpGet]
[2026-02-10 12:33:19,628] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWithRemote] Sending script (length: 27878) to sidecar for evaluation
[2026-02-10 12:33:19,628] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] evaluate() called, session: dbe6dafe-263d-49b5-8c56-4ca4001db790, sourceId: adaptive-script
[2026-02-10 12:33:19,628] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Ensuring connection to remote engine
[2026-02-10 12:33:19,628] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] ensureConnected - transport: GrpcStreamingTransportImpl, connected: false
[2026-02-10 12:33:19,628] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Connecting transport
[2026-02-10 12:33:19,628] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] connect() to localhost:50051
[2026-02-10 12:33:19,629] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcConnectionManager} - [GrpcConnectionManager] Creating new gRPC client channel to: localhost:50051
[2026-02-10 12:33:19,725] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcConnectionManager} - [GrpcConnectionManager] gRPC client channel created successfully
[2026-02-10 12:33:19,729] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Created async stub for target: localhost:50051
[2026-02-10 12:33:19,729] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Connected successfully to: localhost:50051
[2026-02-10 12:33:19,729] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Connected to remote engine successfully
[2026-02-10 12:33:19,729] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Connection established, registering handler...
[2026-02-10 12:33:19,729] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Starting callback server if needed
[2026-02-10 12:33:19,729] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] start() - no separate callback server needed in streaming mode
[2026-02-10 12:33:19,729] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering handler with callback server: GrpcStreamingTransportImpl
[2026-02-10 12:33:19,729] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] registerHandler for session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:19,730] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Handler registered: true
[2026-02-10 12:33:19,741] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback address: streaming://localhost:50051
[2026-02-10 12:33:19,741] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing 0 bindings, 39 host functions
[2026-02-10 12:33:19,742] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: doAssociationWithLocalUser
[2026-02-10 12:33:19,742] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: terminateUserSession
[2026-02-10 12:33:19,742] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: assignUserRoles
[2026-02-10 12:33:19,743] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: callChoreo
[2026-02-10 12:33:19,743] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: checkMicrosoftEmailVerification
[2026-02-10 12:33:19,743] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getUserSessions
[2026-02-10 12:33:19,743] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: callElastic
[2026-02-10 12:33:19,743] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: hasAnyOfTheRolesV2
[2026-02-10 12:33:19,743] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: removeAssociatedLocalUser
[2026-02-10 12:33:19,743] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: assignUserRolesV2
[2026-02-10 12:33:19,743] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: resolveMultiAttributeLoginIdentifier
[2026-02-10 12:33:19,744] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getUsersWithClaimValues
[2026-02-10 12:33:19,744] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getCookieValue
[2026-02-10 12:33:19,744] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: callAnalytics
[2026-02-10 12:33:19,744] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: isAnyOfTheRolesAssignedToUser
[2026-02-10 12:33:19,744] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: updateUserPassword
[2026-02-10 12:33:19,744] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getUniqueUserWithClaimValues
[2026-02-10 12:33:19,744] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: hasRole
[2026-02-10 12:33:19,744] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getMaskedValue
[2026-02-10 12:33:19,744] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getSecretByName
[2026-02-10 12:33:19,745] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: promptIdentifierForStep
[2026-02-10 12:33:19,745] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: httpGet
[2026-02-10 12:33:19,745] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getAuthenticatedApplications
[2026-02-10 12:33:19,745] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: checkSessionExistence
[2026-02-10 12:33:19,745] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: httpPost
[2026-02-10 12:33:19,745] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: sendError
[2026-02-10 12:33:19,745] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: removeUserRoles
[2026-02-10 12:33:19,745] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: sendEmail
[2026-02-10 12:33:19,746] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getValueFromDecodedAssertion
[2026-02-10 12:33:19,746] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: loadLocalLibrary
[2026-02-10 12:33:19,746] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getAssociatedLocalUser
[2026-02-10 12:33:19,746] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: removeUserRolesV2
[2026-02-10 12:33:19,746] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: filterAuthenticators
[2026-02-10 12:33:19,746] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: setCookie
[2026-02-10 12:33:19,746] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: hasAnyOfTheRoles
[2026-02-10 12:33:19,746] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: publishToAnalytics
[2026-02-10 12:33:19,746] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: isMemberOfAnyOfGroups
[2026-02-10 12:33:19,746] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: executeStep
[2026-02-10 12:33:19,747] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: prompt
[2026-02-10 12:33:19,747] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding context data, step: 0, subject: null
[2026-02-10 12:33:19,749] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Sending evaluate request to remote engine...
[2026-02-10 12:33:19,749] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] sendEvaluate() - session: dbe6dafe-263d-49b5-8c56-4ca4001db790, script length: 27878
[2026-02-10 12:33:19,765] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Sent EvaluateRequest on stream, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,576] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,577] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: serviceProviderName, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,601] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,601] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: tenantDomain, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,603] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,603] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: currentKnownSubject, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,607] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,607] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,614] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,614] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::ip, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,618] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,618] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::headers, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,621] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,621] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::headers::User-Agent, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,625] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,625] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::headers::Host, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,627] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,627] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::params, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,628] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,628] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::params::sessionDataKey, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,630] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,631] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::params::type, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,632] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,633] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::cookies, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,635] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,635] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::cookies::commonAuthId, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,638] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,639] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: response, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,672] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,673] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: executeStep, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,673] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: executeStep with 2 args, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,674] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.lang.Double, value=1.0
[2026-02-10 12:33:20,674] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.HashMap, value={onFail=function(context) {
            Log.info('[TEST] STEP 1 FAILED - user authentication failed');
        }, onSuccess=function(context) {
            Log.info('==================================...[truncated]
[2026-02-10 12:33:20,674] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder$JsGraalStepExecuter
[2026-02-10 12:33:20,674] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:20,674] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: null
[2026-02-10 12:33:20,674] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:20,674] [36857dc6-ef86-4661-b126-55caef694c69]  WARN {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] PROP_CURRENT_NODE not found or wrong type in authContext. Type: null
[2026-02-10 12:33:20,675] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: executeStep, params: 2, paramTypes: [class java.lang.Integer, class [Ljava.lang.Object;]
[2026-02-10 12:33:20,675] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=2, isVarArgs=true
[2026-02-10 12:33:20,675] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting varargs method: fixedParams=1, varArgType=Object
[2026-02-10 12:33:20,675] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[0] from Double to Integer
[2026-02-10 12:33:20,675] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting vararg[0] from HashMap to Object
[2026-02-10 12:33:20,676] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Coercing number types in Map with 2 entries
[2026-02-10 12:33:20,676] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=java.lang.Integer
[2026-02-10 12:33:20,676] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=[Ljava.lang.Object;
[2026-02-10 12:33:20,676] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 12:33:20,676] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [addEventListeners] Received eventsMap with 2 entries
[2026-02-10 12:33:20,676] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [addEventListeners] Processing event: onFail, value type: java.lang.String, isValue: false, isMap: false
[2026-02-10 12:33:20,677] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [addEventListeners] Processing event: onSuccess, value type: java.lang.String, isValue: false, isMap: false
[2026-02-10 12:33:20,677] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: null
[2026-02-10 12:33:20,677] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function executeStep returned: null
[2026-02-10 12:33:20,696] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: EVALUATE_RESPONSE, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,696] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received EvaluateResponse, session: dbe6dafe-263d-49b5-8c56-4ca4001db790, success: true
[2026-02-10 12:33:20,697] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Stream completed, session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,697] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received response, success: true
[2026-02-10 12:33:20,697] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWithRemote] Script evaluation successful, elapsed: 676ms
[2026-02-10 12:33:20,697] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWithRemote] Updating bindings from sidecar: [rolesToStepUp, dynamicFlag, context, secrets]
[2026-02-10 12:33:20,698] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWithRemote] Script execution completed for SP: app1
[2026-02-10 12:33:20,698] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWithRemote] Persisted 4 bindings
[2026-02-10 12:33:20,698] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] unregisterHandler for session: dbe6dafe-263d-49b5-8c56-4ca4001db790
[2026-02-10 12:33:20,698] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] close() - clearing stub, correlationId: 26a769db-5854-479c-966d-547d0c3fe826
[2026-02-10 12:33:32,533] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createTransport() CALLED ==========
[2026-02-10 12:33:32,533] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Transport instance #2
[2026-02-10 12:33:32,534] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Config details - grpcTarget: localhost:50051, callbackPort: 0
[2026-02-10 12:33:32,534] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Returning streaming transport, hashCode=1332500867
[2026-02-10 12:33:32,534] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createTransport() COMPLETED ==========
[2026-02-10 12:33:32,534] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createCallbackServer() CALLED ==========
[2026-02-10 12:33:32,534] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Config details - callbackPort: 0, grpcTarget: localhost:50051
[2026-02-10 12:33:32,534] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Returning streaming transport as callback server, hashCode=1332500867
[2026-02-10 12:33:32,534] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createCallbackServer() COMPLETED ==========
[2026-02-10 12:33:32,534] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Created with session: 79d8a919-b3fc-4780-8a58-ab167366e7c8, transport: GrpcStreamingTransportImpl, callbackServer: GrpcStreamingTransportImpl, SP: app1
[2026-02-10 12:33:32,535] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Starting for SP: app1, contextId: e383ff05-1565-49f3-bf16-8b4a74f1ba29, step: 1, authContext hashCode: 1478518248
[2026-02-10 12:33:32,535] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Found 4 persisted bindings: [context, rolesToStepUp, dynamicFlag, secrets]
[2026-02-10 12:33:32,535] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: context = HashMap: {__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 12:33:32,535] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: rolesToStepUp = ArrayList: [admin, manager]
[2026-02-10 12:33:32,535] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: dynamicFlag = Long: 1
[2026-02-10 12:33:32,535] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: secrets = HashMap: {}
[2026-02-10 12:33:32,536] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback() called, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8, function length: 21943, args: 1
[2026-02-10 12:33:32,536] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - ensuring connection to remote engine
[2026-02-10 12:33:32,536] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] ensureConnected - transport: GrpcStreamingTransportImpl, connected: false
[2026-02-10 12:33:32,536] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Connecting transport
[2026-02-10 12:33:32,536] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] connect() to localhost:50051
[2026-02-10 12:33:32,537] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Created async stub for target: localhost:50051
[2026-02-10 12:33:32,537] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Connected successfully to: localhost:50051
[2026-02-10 12:33:32,537] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Connected to remote engine successfully
[2026-02-10 12:33:32,537] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - connection OK, registering handler...
[2026-02-10 12:33:32,537] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Starting callback server if needed
[2026-02-10 12:33:32,537] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] start() - no separate callback server needed in streaming mode
[2026-02-10 12:33:32,537] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering handler with callback server: GrpcStreamingTransportImpl
[2026-02-10 12:33:32,537] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] registerHandler for session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,537] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - handler registered: true
[2026-02-10 12:33:32,537] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Applying 4 callback bindings: [context, rolesToStepUp, dynamicFlag, secrets]
[2026-02-10 12:33:32,538] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: context = HashMap: {__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 12:33:32,538] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: rolesToStepUp = ArrayList: [admin, manager]
[2026-02-10 12:33:32,538] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: dynamicFlag = Long: 1
[2026-02-10 12:33:32,538] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: secrets = HashMap: {}
[2026-02-10 12:33:32,541] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - callback address: streaming://localhost:50051
[2026-02-10 12:33:32,541] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding context data, step: 1, subject: admin1
[2026-02-10 12:33:32,541] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing 1 arguments
[2026-02-10 12:33:32,541] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Arg[0] type: org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 12:33:32,541] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Total bindings to serialize: 4, keys: [rolesToStepUp, dynamicFlag, context, secrets]
[2026-02-10 12:33:32,541] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Host functions (excluded from bindings): [doAssociationWithLocalUser, terminateUserSession, assignUserRoles, callChoreo, checkMicrosoftEmailVerification, getUserSessions, callElastic, hasAnyOfTheRolesV2, removeAssociatedLocalUser, assignUserRolesV2, resolveMultiAttributeLoginIdentifier, getUsersWithClaimValues, getCookieValue, fail, callAnalytics, isAnyOfTheRolesAssignedToUser, updateUserPassword, getUniqueUserWithClaimValues, hasRole, getMaskedValue, getSecretByName, promptIdentifierForStep, httpGet, getAuthenticatedApplications, checkSessionExistence, httpPost, sendError, removeUserRoles, sendEmail, getValueFromDecodedAssertion, loadLocalLibrary, getAssociatedLocalUser, removeUserRolesV2, filterAuthenticators, setCookie, hasAnyOfTheRoles, publishToAnalytics, isMemberOfAnyOfGroups, executeStep, prompt]
[2026-02-10 12:33:32,542] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: rolesToStepUp = ArrayList: [admin, manager]
[2026-02-10 12:33:32,542] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: dynamicFlag = Long: 1
[2026-02-10 12:33:32,542] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: context = HashMap: {__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 12:33:32,542] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: secrets = HashMap: {}
[2026-02-10 12:33:32,542] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Bindings serialized: 4
[2026-02-10 12:33:32,542] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding 40 host function definitions
[2026-02-10 12:33:32,543] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: doAssociationWithLocalUser
[2026-02-10 12:33:32,543] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: terminateUserSession
[2026-02-10 12:33:32,543] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: assignUserRoles
[2026-02-10 12:33:32,543] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: callChoreo
[2026-02-10 12:33:32,543] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: checkMicrosoftEmailVerification
[2026-02-10 12:33:32,543] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getUserSessions
[2026-02-10 12:33:32,543] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: callElastic
[2026-02-10 12:33:32,543] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: hasAnyOfTheRolesV2
[2026-02-10 12:33:32,543] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: removeAssociatedLocalUser
[2026-02-10 12:33:32,543] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: assignUserRolesV2
[2026-02-10 12:33:32,543] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: resolveMultiAttributeLoginIdentifier
[2026-02-10 12:33:32,543] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getUsersWithClaimValues
[2026-02-10 12:33:32,543] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getCookieValue
[2026-02-10 12:33:32,543] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: fail
[2026-02-10 12:33:32,544] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: callAnalytics
[2026-02-10 12:33:32,544] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: isAnyOfTheRolesAssignedToUser
[2026-02-10 12:33:32,544] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: updateUserPassword
[2026-02-10 12:33:32,544] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getUniqueUserWithClaimValues
[2026-02-10 12:33:32,544] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: hasRole
[2026-02-10 12:33:32,544] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getMaskedValue
[2026-02-10 12:33:32,544] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getSecretByName
[2026-02-10 12:33:32,544] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: promptIdentifierForStep
[2026-02-10 12:33:32,544] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: httpGet
[2026-02-10 12:33:32,544] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getAuthenticatedApplications
[2026-02-10 12:33:32,544] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: checkSessionExistence
[2026-02-10 12:33:32,544] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: httpPost
[2026-02-10 12:33:32,544] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: sendError
[2026-02-10 12:33:32,544] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: removeUserRoles
[2026-02-10 12:33:32,544] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: sendEmail
[2026-02-10 12:33:32,545] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getValueFromDecodedAssertion
[2026-02-10 12:33:32,545] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: loadLocalLibrary
[2026-02-10 12:33:32,545] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getAssociatedLocalUser
[2026-02-10 12:33:32,545] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: removeUserRolesV2
[2026-02-10 12:33:32,545] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: filterAuthenticators
[2026-02-10 12:33:32,545] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: setCookie
[2026-02-10 12:33:32,545] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: hasAnyOfTheRoles
[2026-02-10 12:33:32,545] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: publishToAnalytics
[2026-02-10 12:33:32,545] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: isMemberOfAnyOfGroups
[2026-02-10 12:33:32,545] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: executeStep
[2026-02-10 12:33:32,545] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: prompt
[2026-02-10 12:33:32,545] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Sending executeCallback request to remote engine...
[2026-02-10 12:33:32,546] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] sendExecuteCallback() - session: 79d8a919-b3fc-4780-8a58-ab167366e7c8, function length: 21943
[2026-02-10 12:33:32,550] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Sent ExecuteCallbackRequest on stream, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,626] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,627] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,630] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,630] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,633] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,633] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::idp, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,634] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,635] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::authenticator, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,636] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,636] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,638] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,639] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::uniqueId, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,640] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,640] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::username, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,641] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,642] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::userStoreDomain, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,643] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,643] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::localClaims, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,647] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,647] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/givenname, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,650] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,650] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/lastname, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,652] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,653] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/emailaddress, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,655] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,656] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/mobile, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,662] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,662] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/country, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,666] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,666] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::claims, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,671] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_SET_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,672] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextPropertySet: steps::1::subject::claims::http://wso2.org/claims/testSessionClaim, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,672] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] setContextProperty called: steps::1::subject::claims::http://wso2.org/claims/testSessionClaim = String, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,672] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Successfully set property via putMember: steps::1::subject::claims::http://wso2.org/claims/testSessionClaim
[2026-02-10 12:33:32,678] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,679] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: isMemberOfAnyOfGroups, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,679] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: isMemberOfAnyOfGroups with 2 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,679] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=authenticateduser, __basePath=steps::1::subject}
[2026-02-10 12:33:32,679] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.ArrayList, value=[admin, Internal/everyone]
[2026-02-10 12:33:32,679] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.IsMemberOfAnyOfGroupsFunctionImpl
[2026-02-10 12:33:32,679] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,679] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 12:33:32,679] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,679] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 12:33:32,690] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: isMemberOfAnyOfGroups, params: 2, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticatedUser, interface java.util.List]
[2026-02-10 12:33:32,690] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=2, isVarArgs=false
[2026-02-10 12:33:32,690] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[0] from HashMap to JsAuthenticatedUser
[2026-02-10 12:33:32,691] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=authenticateduser, basePath=steps::1::subject
[2026-02-10 12:33:32,691] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Direct reconstruction of authenticateduser for step 1
[2026-02-10 12:33:32,691] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Directly reconstructed JsGraalAuthenticatedUser for step 1
[2026-02-10 12:33:32,691] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalAuthenticatedUser from context proxy marker
[2026-02-10 12:33:32,691] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[1] from ArrayList to List
[2026-02-10 12:33:32,691] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticatedUser
[2026-02-10 12:33:32,691] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=java.util.ArrayList
[2026-02-10 12:33:32,691] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 12:33:32,696] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: java.lang.Boolean=true
[2026-02-10 12:33:32,696] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function isMemberOfAnyOfGroups returned: Boolean
[2026-02-10 12:33:32,700] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,701] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: hasAnyOfTheRolesV2, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,701] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: hasAnyOfTheRolesV2 with 2 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,701] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 12:33:32,701] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.ArrayList, value=[Internal/admin, admin, Application/admin]
[2026-02-10 12:33:32,701] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.HasAnyOfTheRolesV2FunctionImpl
[2026-02-10 12:33:32,701] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,701] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 12:33:32,701] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,702] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 12:33:32,702] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: hasAnyOfTheRolesV2, params: 2, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext, interface java.util.List]
[2026-02-10 12:33:32,702] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=2, isVarArgs=false
[2026-02-10 12:33:32,702] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[0] from HashMap to JsAuthenticationContext
[2026-02-10 12:33:32,702] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=context, basePath=
[2026-02-10 12:33:32,702] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructing root context
[2026-02-10 12:33:32,702] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalAuthenticationContext from context proxy marker
[2026-02-10 12:33:32,702] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[1] from ArrayList to List
[2026-02-10 12:33:32,702] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 12:33:32,702] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=java.util.ArrayList
[2026-02-10 12:33:32,702] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 12:33:32,709] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: java.lang.Boolean=true
[2026-02-10 12:33:32,709] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function hasAnyOfTheRolesV2 returned: Boolean
[2026-02-10 12:33:32,711] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: hasAnyOfTheRolesV2, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: hasAnyOfTheRolesV2 with 2 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.ArrayList, value=[manager]
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.HasAnyOfTheRolesV2FunctionImpl
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: hasAnyOfTheRolesV2, params: 2, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext, interface java.util.List]
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=2, isVarArgs=false
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[0] from HashMap to JsAuthenticationContext
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=context, basePath=
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructing root context
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalAuthenticationContext from context proxy marker
[2026-02-10 12:33:32,712] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[1] from ArrayList to List
[2026-02-10 12:33:32,713] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 12:33:32,713] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=java.util.ArrayList
[2026-02-10 12:33:32,713] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 12:33:32,713] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: java.lang.Boolean=false
[2026-02-10 12:33:32,713] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function hasAnyOfTheRolesV2 returned: Boolean
[2026-02-10 12:33:32,716] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,716] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: hasAnyOfTheRolesV2, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,716] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: hasAnyOfTheRolesV2 with 2 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,716] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.ArrayList, value=[admin, manager]
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.HasAnyOfTheRolesV2FunctionImpl
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: hasAnyOfTheRolesV2, params: 2, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext, interface java.util.List]
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=2, isVarArgs=false
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[0] from HashMap to JsAuthenticationContext
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=context, basePath=
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructing root context
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalAuthenticationContext from context proxy marker
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[1] from ArrayList to List
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=java.util.ArrayList
[2026-02-10 12:33:32,717] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 12:33:32,718] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: java.lang.Boolean=true
[2026-02-10 12:33:32,718] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function hasAnyOfTheRolesV2 returned: Boolean
[2026-02-10 12:33:32,720] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,720] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: getUserSessions, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: getUserSessions with 1 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=authenticateduser, __basePath=steps::1::subject}
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.GetUserSessionsFunctionImpl
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: getUserSessions, params: 1, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticatedUser]
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=1, argsCount=1, isVarArgs=false
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[0] from HashMap to JsAuthenticatedUser
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=authenticateduser, basePath=steps::1::subject
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Direct reconstruction of authenticateduser for step 1
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Directly reconstructed JsGraalAuthenticatedUser for step 1
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalAuthenticatedUser from context proxy marker
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticatedUser
[2026-02-10 12:33:32,721] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 1 adapted args
[2026-02-10 12:33:32,785] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: java.util.ArrayList=[]
[2026-02-10 12:33:32,785] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function getUserSessions returned: ArrayList
[2026-02-10 12:33:32,797] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,797] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: getUniqueUserWithClaimValues, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,797] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: getUniqueUserWithClaimValues with 2 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,797] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={http://wso2.org/claims/username=admin1}
[2026-02-10 12:33:32,797] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 12:33:32,798] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.store.GetUserWithClaimValuesV2FunctionImpl
[2026-02-10 12:33:32,798] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,798] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 12:33:32,798] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,798] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 12:33:32,809] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: getUniqueUserWithClaimValues, params: 3, paramTypes: [interface java.util.Map, class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext, class [Ljava.lang.String;]
[2026-02-10 12:33:32,809] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=3, argsCount=2, isVarArgs=true
[2026-02-10 12:33:32,809] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting varargs method: fixedParams=2, varArgType=String
[2026-02-10 12:33:32,809] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[0] from HashMap to Map
[2026-02-10 12:33:32,809] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[1] from HashMap to JsAuthenticationContext
[2026-02-10 12:33:32,809] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=context, basePath=
[2026-02-10 12:33:32,809] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructing root context
[2026-02-10 12:33:32,809] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalAuthenticationContext from context proxy marker
[2026-02-10 12:33:32,809] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=java.util.HashMap
[2026-02-10 12:33:32,810] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 12:33:32,810] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[2]: type=[Ljava.lang.String;
[2026-02-10 12:33:32,810] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 3 adapted args
[2026-02-10 12:33:32,826] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticatedUser=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticatedUser@6355fddf
[2026-02-10 12:33:32,826] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function getUniqueUserWithClaimValues returned: JsGraalAuthenticatedUser
[2026-02-10 12:33:32,830] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,830] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: getUsersWithClaimValues, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,830] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: getUsersWithClaimValues with 2 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,830] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={http://wso2.org/claims/username=admin1}
[2026-02-10 12:33:32,831] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 12:33:32,831] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.store.GetUsersWithClaimValuesFunctionImpl
[2026-02-10 12:33:32,832] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,832] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 12:33:32,832] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,832] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 12:33:32,832] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: getUsersWithClaimValues, params: 3, paramTypes: [interface java.util.Map, class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext, class [Ljava.lang.String;]
[2026-02-10 12:33:32,833] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=3, argsCount=2, isVarArgs=true
[2026-02-10 12:33:32,833] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting varargs method: fixedParams=2, varArgType=String
[2026-02-10 12:33:32,833] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[0] from HashMap to Map
[2026-02-10 12:33:32,833] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[1] from HashMap to JsAuthenticationContext
[2026-02-10 12:33:32,833] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=context, basePath=
[2026-02-10 12:33:32,833] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructing root context
[2026-02-10 12:33:32,833] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalAuthenticationContext from context proxy marker
[2026-02-10 12:33:32,835] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=java.util.HashMap
[2026-02-10 12:33:32,835] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 12:33:32,839] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[2]: type=[Ljava.lang.String;
[2026-02-10 12:33:32,839] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 3 adapted args
[2026-02-10 12:33:32,841] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: java.util.ArrayList=[org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticatedUser@31a3bcf4]
[2026-02-10 12:33:32,841] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function getUsersWithClaimValues returned: ArrayList
[2026-02-10 12:33:32,844] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,844] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: response, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,848] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,849] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: setCookie, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,849] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: setCookie with 4 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,849] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=servletresponse, __basePath=response}
[2026-02-10 12:33:32,849] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.lang.String, value=testAdaptiveCookie
[2026-02-10 12:33:32,849] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[2]: type=java.lang.String, value=cookieVal123
[2026-02-10 12:33:32,849] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[3]: type=java.util.HashMap, value={path=/, encrypt=false, sameSite=LAX, max-age=3600.0, sign=false, httpOnly=true, secure=true}
[2026-02-10 12:33:32,849] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.http.SetCookieFunctionImpl
[2026-02-10 12:33:32,849] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,849] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 12:33:32,849] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,849] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 12:33:32,861] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: setCookie, params: 3, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsServletResponse, class java.lang.String, class [Ljava.lang.Object;]
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=3, argsCount=4, isVarArgs=true
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting varargs method: fixedParams=2, varArgType=Object
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[0] from HashMap to JsServletResponse
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=servletresponse, basePath=response
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Navigating to nested property: response
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Successfully navigated to: response, result type: JsGraalServletResponse
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalServletResponse from context proxy marker
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[1] from String to String
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting vararg[0] from String to Object
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting vararg[1] from HashMap to Object
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Coercing number types in Map with 7 entries
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Coerced max-age: 3600.0 -> 3600
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalServletResponse
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=java.lang.String
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[2]: type=[Ljava.lang.Object;
[2026-02-10 12:33:32,862] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 3 adapted args
[2026-02-10 12:33:32,866] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: null
[2026-02-10 12:33:32,866] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function setCookie returned: null
[2026-02-10 12:33:32,869] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,869] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,873] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,873] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: getCookieValue, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,873] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: getCookieValue with 3 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,873] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=servletrequest, __basePath=request}
[2026-02-10 12:33:32,873] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.lang.String, value=testAdaptiveCookie
[2026-02-10 12:33:32,873] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[2]: type=java.util.HashMap, value={validateSignature=false, decrypt=false}
[2026-02-10 12:33:32,873] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.http.GetCookieFunctionImpl
[2026-02-10 12:33:32,873] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,873] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 12:33:32,874] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,874] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 12:33:32,874] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: getCookieValue, params: 2, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsServletRequest, class [Ljava.lang.Object;]
[2026-02-10 12:33:32,874] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=3, isVarArgs=true
[2026-02-10 12:33:32,874] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting varargs method: fixedParams=1, varArgType=Object
[2026-02-10 12:33:32,874] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[0] from HashMap to JsServletRequest
[2026-02-10 12:33:32,874] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=servletrequest, basePath=request
[2026-02-10 12:33:32,874] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Navigating to nested property: request
[2026-02-10 12:33:32,874] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Successfully navigated to: request, result type: JsGraalServletRequest
[2026-02-10 12:33:32,874] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalServletRequest from context proxy marker
[2026-02-10 12:33:32,874] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting vararg[0] from String to Object
[2026-02-10 12:33:32,874] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting vararg[1] from HashMap to Object
[2026-02-10 12:33:32,874] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Coercing number types in Map with 2 entries
[2026-02-10 12:33:32,874] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalServletRequest
[2026-02-10 12:33:32,874] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=[Ljava.lang.Object;
[2026-02-10 12:33:32,875] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 12:33:32,875] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: null
[2026-02-10 12:33:32,875] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function getCookieValue returned: null
[2026-02-10 12:33:32,877] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,877] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::params, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,879] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,879] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::params::sessionDataKey, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,882] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,882] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::params::username, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,884] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,884] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: response::headers, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,886] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_SET_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,886] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextPropertySet: response::headers::X-Adaptive-Test, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,886] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] setContextProperty called: response::headers::X-Adaptive-Test = String, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,887] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Successfully set property via putMember: response::headers::X-Adaptive-Test
[2026-02-10 12:33:32,890] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,890] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: executeStep, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,890] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: executeStep with 2 args, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,890] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.lang.Double, value=2.0
[2026-02-10 12:33:32,890] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.HashMap, value={onFail=function(context) {
                        Log.info('[TEST] STEP 2 FAILED');
                    }, onSuccess=function(context) {
                        Log.info('===========================...[truncated]
[2026-02-10 12:33:32,891] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder$JsGraalStepExecuterInAsyncEvent
[2026-02-10 12:33:32,891] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,891] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 12:33:32,891] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:32,891] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 12:33:32,891] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: executeStep, params: 2, paramTypes: [class java.lang.Integer, class [Ljava.lang.Object;]
[2026-02-10 12:33:32,891] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=2, isVarArgs=true
[2026-02-10 12:33:32,891] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting varargs method: fixedParams=1, varArgType=Object
[2026-02-10 12:33:32,891] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[0] from Double to Integer
[2026-02-10 12:33:32,891] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting vararg[0] from HashMap to Object
[2026-02-10 12:33:32,891] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Coercing number types in Map with 2 entries
[2026-02-10 12:33:32,891] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=java.lang.Integer
[2026-02-10 12:33:32,891] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=[Ljava.lang.Object;
[2026-02-10 12:33:32,891] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 12:33:32,892] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [addEventListeners] Received eventsMap with 2 entries
[2026-02-10 12:33:32,892] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [addEventListeners] Processing event: onFail, value type: java.lang.String, isValue: false, isMap: false
[2026-02-10 12:33:32,892] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [addEventListeners] Processing event: onSuccess, value type: java.lang.String, isValue: false, isMap: false
[2026-02-10 12:33:32,892] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: null
[2026-02-10 12:33:32,892] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function executeStep returned: null
[2026-02-10 12:33:32,899] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: EXECUTE_CALLBACK_RESPONSE, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,899] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Stream completed, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,899] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received ExecuteCallbackResponse, session: 79d8a919-b3fc-4780-8a58-ab167366e7c8, success: true
[2026-02-10 12:33:32,899] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback response - success: true, elapsed: 336ms
[2026-02-10 12:33:32,900] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Re-persisted 4 bindings after callback
[2026-02-10 12:33:32,900] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] unregisterHandler for session: 79d8a919-b3fc-4780-8a58-ab167366e7c8
[2026-02-10 12:33:32,900] [1bd659d1-ebd8-40d0-83bd-be410692b123]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] close() - clearing stub, correlationId: 26a769db-5854-479c-966d-547d0c3fe826
[2026-02-10 12:33:45,152] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createTransport() CALLED ==========
[2026-02-10 12:33:45,152] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Transport instance #3
[2026-02-10 12:33:45,152] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Config details - grpcTarget: localhost:50051, callbackPort: 0
[2026-02-10 12:33:45,152] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Returning streaming transport, hashCode=1332500867
[2026-02-10 12:33:45,152] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createTransport() COMPLETED ==========
[2026-02-10 12:33:45,152] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createCallbackServer() CALLED ==========
[2026-02-10 12:33:45,153] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Config details - callbackPort: 0, grpcTarget: localhost:50051
[2026-02-10 12:33:45,153] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Returning streaming transport as callback server, hashCode=1332500867
[2026-02-10 12:33:45,153] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createCallbackServer() COMPLETED ==========
[2026-02-10 12:33:45,153] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Created with session: e43320b0-1250-4d76-ad77-a1c9862d89a5, transport: GrpcStreamingTransportImpl, callbackServer: GrpcStreamingTransportImpl, SP: app1
[2026-02-10 12:33:45,153] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Starting for SP: app1, contextId: e383ff05-1565-49f3-bf16-8b4a74f1ba29, step: 2, authContext hashCode: 1478518248
[2026-02-10 12:33:45,153] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Found 4 persisted bindings: [context, rolesToStepUp, dynamicFlag, secrets]
[2026-02-10 12:33:45,153] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: context = HashMap: {__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 12:33:45,153] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: rolesToStepUp = ArrayList: [admin, manager]
[2026-02-10 12:33:45,153] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: dynamicFlag = Long: 2
[2026-02-10 12:33:45,153] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: secrets = HashMap: {}
[2026-02-10 12:33:45,153] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback() called, session: e43320b0-1250-4d76-ad77-a1c9862d89a5, function length: 2411, args: 1
[2026-02-10 12:33:45,153] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - ensuring connection to remote engine
[2026-02-10 12:33:45,153] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] ensureConnected - transport: GrpcStreamingTransportImpl, connected: false
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Connecting transport
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] connect() to localhost:50051
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Created async stub for target: localhost:50051
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Connected successfully to: localhost:50051
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Connected to remote engine successfully
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - connection OK, registering handler...
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Starting callback server if needed
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] start() - no separate callback server needed in streaming mode
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering handler with callback server: GrpcStreamingTransportImpl
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] registerHandler for session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - handler registered: true
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Applying 4 callback bindings: [context, rolesToStepUp, dynamicFlag, secrets]
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: context = HashMap: {__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: rolesToStepUp = ArrayList: [admin, manager]
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: dynamicFlag = Long: 2
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: secrets = HashMap: {}
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - callback address: streaming://localhost:50051
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding context data, step: 2, subject: admin1
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing 1 arguments
[2026-02-10 12:33:45,154] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Arg[0] type: org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 12:33:45,155] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Total bindings to serialize: 4, keys: [rolesToStepUp, dynamicFlag, context, secrets]
[2026-02-10 12:33:45,155] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Host functions (excluded from bindings): [doAssociationWithLocalUser, terminateUserSession, assignUserRoles, callChoreo, checkMicrosoftEmailVerification, getUserSessions, callElastic, hasAnyOfTheRolesV2, removeAssociatedLocalUser, assignUserRolesV2, resolveMultiAttributeLoginIdentifier, getUsersWithClaimValues, getCookieValue, fail, callAnalytics, isAnyOfTheRolesAssignedToUser, updateUserPassword, getUniqueUserWithClaimValues, hasRole, getMaskedValue, getSecretByName, promptIdentifierForStep, httpGet, getAuthenticatedApplications, checkSessionExistence, httpPost, sendError, removeUserRoles, sendEmail, getValueFromDecodedAssertion, loadLocalLibrary, getAssociatedLocalUser, removeUserRolesV2, filterAuthenticators, setCookie, hasAnyOfTheRoles, publishToAnalytics, isMemberOfAnyOfGroups, executeStep, prompt]
[2026-02-10 12:33:45,155] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: rolesToStepUp = ArrayList: [admin, manager]
[2026-02-10 12:33:45,155] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: dynamicFlag = Long: 2
[2026-02-10 12:33:45,155] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: context = HashMap: {__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 12:33:45,155] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: secrets = HashMap: {}
[2026-02-10 12:33:45,155] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Bindings serialized: 4
[2026-02-10 12:33:45,155] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding 40 host function definitions
[2026-02-10 12:33:45,155] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: doAssociationWithLocalUser
[2026-02-10 12:33:45,155] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: terminateUserSession
[2026-02-10 12:33:45,155] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: assignUserRoles
[2026-02-10 12:33:45,155] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: callChoreo
[2026-02-10 12:33:45,155] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: checkMicrosoftEmailVerification
[2026-02-10 12:33:45,155] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getUserSessions
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: callElastic
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: hasAnyOfTheRolesV2
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: removeAssociatedLocalUser
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: assignUserRolesV2
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: resolveMultiAttributeLoginIdentifier
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getUsersWithClaimValues
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getCookieValue
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: fail
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: callAnalytics
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: isAnyOfTheRolesAssignedToUser
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: updateUserPassword
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getUniqueUserWithClaimValues
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: hasRole
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getMaskedValue
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getSecretByName
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: promptIdentifierForStep
[2026-02-10 12:33:45,156] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: httpGet
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getAuthenticatedApplications
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: checkSessionExistence
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: httpPost
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: sendError
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: removeUserRoles
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: sendEmail
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getValueFromDecodedAssertion
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: loadLocalLibrary
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getAssociatedLocalUser
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: removeUserRolesV2
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: filterAuthenticators
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: setCookie
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: hasAnyOfTheRoles
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: publishToAnalytics
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: isMemberOfAnyOfGroups
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: executeStep
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: prompt
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Sending executeCallback request to remote engine...
[2026-02-10 12:33:45,157] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] sendExecuteCallback() - session: e43320b0-1250-4d76-ad77-a1c9862d89a5, function length: 2411
[2026-02-10 12:33:45,158] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Sent ExecuteCallbackRequest on stream, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,171] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,172] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,173] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,174] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::2, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,177] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,177] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::2::idp, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,178] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,178] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::2::authenticator, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,180] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,180] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::2::subject, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,181] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,181] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::2::subject::username, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,183] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,183] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: hasAnyOfTheRolesV2, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,183] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: hasAnyOfTheRolesV2 with 2 args, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,183] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 12:33:45,183] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.ArrayList, value=[manager]
[2026-02-10 12:33:45,183] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.HasAnyOfTheRolesV2FunctionImpl
[2026-02-10 12:33:45,183] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:45,183] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 12:33:45,184] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: e383ff05-1565-49f3-bf16-8b4a74f1ba29
[2026-02-10 12:33:45,184] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 12:33:45,184] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: hasAnyOfTheRolesV2, params: 2, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext, interface java.util.List]
[2026-02-10 12:33:45,184] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=2, isVarArgs=false
[2026-02-10 12:33:45,184] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[0] from HashMap to JsAuthenticationContext
[2026-02-10 12:33:45,184] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=context, basePath=
[2026-02-10 12:33:45,184] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructing root context
[2026-02-10 12:33:45,184] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalAuthenticationContext from context proxy marker
[2026-02-10 12:33:45,184] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[1] from ArrayList to List
[2026-02-10 12:33:45,184] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 12:33:45,184] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=java.util.ArrayList
[2026-02-10 12:33:45,184] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 12:33:45,185] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: java.lang.Boolean=false
[2026-02-10 12:33:45,186] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function hasAnyOfTheRolesV2 returned: Boolean
[2026-02-10 12:33:45,191] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: EXECUTE_CALLBACK_RESPONSE, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,192] [36857dc6-ef86-4661-b126-55caef694c69]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Stream completed, session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,192] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received ExecuteCallbackResponse, session: e43320b0-1250-4d76-ad77-a1c9862d89a5, success: true
[2026-02-10 12:33:45,192] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback response - success: true, elapsed: 29ms
[2026-02-10 12:33:45,192] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Re-persisted 4 bindings after callback
[2026-02-10 12:33:45,192] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] unregisterHandler for session: e43320b0-1250-4d76-ad77-a1c9862d89a5
[2026-02-10 12:33:45,192] [2604fae2-3301-46eb-85e0-43eb5a093ade]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] close() - clearing stub, correlationId: 26a769db-5854-479c-966d-547d0c3fe826
"