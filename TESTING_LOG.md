Identity Server Log "bashitha@bashitha bin % sh wso2server.sh run -Dosgi.clean=true
JAVA_HOME environment variable is set to /Library/Java/JavaVirtualMachines/temurin-11.jdk/Contents/Home
CARBON_HOME environment variable is set to /Users/bashitha/Downloads/product/wso2is-7.2.1-SNAPSHOT
Using Java memory options: -Xms256m -Xmx1024m
[2026-02-10 11:15:19,004] []  INFO {org.ops4j.pax.logging.spi.support.EventAdminConfigurationNotifier} - Sending Event Admin notification (configuration successful) to org/ops4j/pax/logging/Configuration
[2026-02-10 11:15:19,138] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - Starting WSO2 Carbon...
[2026-02-10 11:15:19,139] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - Operating System : Mac OS X 26.2, aarch64
[2026-02-10 11:15:19,139] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - Java Home        : /Library/Java/JavaVirtualMachines/temurin-11.jdk/Contents/Home
[2026-02-10 11:15:19,139] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - Java Version     : 11.0.29
[2026-02-10 11:15:19,139] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - Java VM          : OpenJDK 64-Bit Server VM 11.0.29+7,Eclipse Adoptium
[2026-02-10 11:15:19,139] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - Carbon Home      : /Users/bashitha/Downloads/product/wso2is-7.2.1-SNAPSHOT
[2026-02-10 11:15:19,139] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - Java Temp Dir    : /Users/bashitha/Downloads/product/wso2is-7.2.1-SNAPSHOT/tmp
[2026-02-10 11:15:19,139] []  INFO {org.wso2.carbon.core.internal.CarbonCoreActivator} - User             : bashitha, en-LK, Asia/Colombo
[2026-02-10 11:15:22,371] []  INFO {org.apache.jasper.servlet.TldScanner} - At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
[2026-02-10 11:15:25,107] []  INFO {org.wso2.carbon.registry.indexing.solr.SolrClient} - Default Embedded Solr Server Initialized
[2026-02-10 11:15:25,579] []  INFO {org.apache.axis2.transport.mail.MailTransportSender} - MAILTO Sender started
[2026-02-10 11:15:25,650] []  INFO {org.wso2.carbon.core.init.CarbonServerManager} - Repository       : /Users/bashitha/Downloads/product/wso2is-7.2.1-SNAPSHOT/repository/deployment/server/
[2026-02-10 11:15:25,669] []  INFO {org.wso2.carbon.core.multitenancy.eager.TenantLoadingConfig} - Using tenant lazy loading policy...
[2026-02-10 11:15:25,675] []  INFO {org.wso2.carbon.core.internal.permission.update.PermissionUpdater} - Permission cache updated for tenant -1234
[2026-02-10 11:15:28,304] []  WARN {org.wso2.carbon.identity.auth.service.internal.AuthenticationServiceComponent} - 

##################################  ALERT  ##################################
[WARNING]: Internal authentication is utilizing default credentials,
which may expose the environment to potential security risks.
If this is a production environment, change the credentials immediately.
#############################################################################

[2026-02-10 11:15:28,323] []  WARN {org.wso2.carbon.identity.event.internal.IdentityEventServiceComponent} - Properties for IdentityFraudDetectorEventHandler is not configured. This event handler will not be activated
[2026-02-10 11:15:29,636] []  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.JsEngineFactory} - JsEngineFactory initialized. Mode: REMOTE, Transport: GRPC, gRPC Target: localhost:50051
[2026-02-10 11:15:29,875] []  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.HostCallbackServer} - Host callback server started at: /tmp/graaljs-callback-593824a0.sock
[2026-02-10 11:15:29,875] []  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.HostCallbackServer} - [HostCallbackServer] Starting accept loop...
[2026-02-10 11:15:29,875] []  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.HostCallbackServer} - [HostCallbackServer] Waiting for callback connection...
[2026-02-10 11:15:29,875] []  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilderFactory} - GraalJS engine abstraction initialized in REMOTE mode
[2026-02-10 11:15:29,963] []  INFO {org.wso2.carbon.identity.application.authentication.framework.store.SessionDataStore} - Thread pool size for temporary authentication context data delete task: 20
[2026-02-10 11:15:30,122] []  INFO {org.opensaml.core.config.InitializationService} - Initializing OpenSAML using the Java Services API
[2026-02-10 11:15:31,821] []  INFO {org.wso2.carbon.healthcheck.api.core.internal.HealthMonitorServiceComponent} - Carbon health monitoring service is activated..
[2026-02-10 11:15:32,062] []  INFO {org.wso2.carbon.user.core.common.UserStoreDeploymentManager} - Realm configuration of tenant:-1234  modified with /Users/bashitha/Downloads/product/wso2is-7.2.1-SNAPSHOT/repository/deployment/server/userstores/AGENT.xml
[2026-02-10 11:15:35,265] []  INFO {org.hibernate.validator.internal.util.Version} - HV000001: Hibernate Validator 6.2.5.Final
[2026-02-10 11:15:42,210] []  INFO {org.wso2.carbon.core.transports.http.HttpTransportListener} - HTTP port        : 9763
[2026-02-10 11:15:42,211] []  INFO {org.wso2.carbon.core.transports.http.HttpsTransportListener} - HTTPS port       : 9443
[2026-02-10 11:15:42,449] []  WARN {org.apache.tomcat.util.net.SSLUtilBase} - The trusted certificate with alias [wso2carbon] and DN [CN=localhost, OU=WSO2, O=WSO2, L=Santa Clara, ST=CA, C=US] is not valid due to [NotAfter: Thu Jan 08 14:02:07 IST 2026]. Certificates signed by this trusted certificate WILL be accepted
[2026-02-10 11:15:42,454] []  INFO {org.apache.tomcat.util.net.NioEndpoint.certificate} - Connector [https-jsse-nio-9443], TLS virtual host [_default_], certificate type [UNDEFINED] configured from keystore [/Users/bashitha/Downloads/product/wso2is-7.2.1-SNAPSHOT/repository/resources/security/wso2carbon.p12] using alias [wso2carbon] with trust store [/Users/bashitha/Downloads/product/wso2is-7.2.1-SNAPSHOT/repository/resources/security/client-truststore.p12]
[2026-02-10 11:15:42,478] []  INFO {org.wso2.identity.apps.common.internal.AppsCommonServiceStartupObserver} - My Account URL : https://localhost:9443/myaccount
[2026-02-10 11:15:42,478] []  INFO {org.wso2.identity.apps.common.internal.AppsCommonServiceStartupObserver} - Console URL : https://localhost:9443/console
[2026-02-10 11:15:42,478] []  INFO {org.wso2.carbon.core.internal.StartupFinalizerServiceComponent} - Server           :  WSO2 Identity Server-7.2.1-SNAPSHOT
[2026-02-10 11:15:42,479] []  INFO {org.wso2.carbon.core.internal.StartupFinalizerServiceComponent} - WSO2 Carbon started in 26 sec
[2026-02-10 11:17:55,311] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWith] Using REMOTE execution mode via sidecar
[2026-02-10 11:17:55,313] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createTransport() CALLED ==========
[2026-02-10 11:17:55,313] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Transport instance #1
[2026-02-10 11:17:55,313] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Config details - grpcTarget: localhost:50051, callbackPort: 0
[2026-02-10 11:17:55,313] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Creating NEW GrpcStreamingTransportImpl for target: localhost:50051
[2026-02-10 11:17:55,318] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcConnectionManager} - [GrpcConnectionManager] Configuration loaded - IdleTimeout: 180s, CallbackPort: 50052
[2026-02-10 11:17:55,318] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Created streaming transport for target: localhost:50051, timeout: 30s, correlationId: 735b7e3c-9fc0-4605-ace2-85b6b3a3ae05
[2026-02-10 11:17:55,318] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] NEW singleton created, hashCode=1198084289
[2026-02-10 11:17:55,318] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Returning streaming transport, hashCode=1198084289
[2026-02-10 11:17:55,318] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createTransport() COMPLETED ==========
[2026-02-10 11:17:55,319] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createCallbackServer() CALLED ==========
[2026-02-10 11:17:55,319] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Config details - callbackPort: 0, grpcTarget: localhost:50051
[2026-02-10 11:17:55,319] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Returning streaming transport as callback server, hashCode=1198084289
[2026-02-10 11:17:55,319] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createCallbackServer() COMPLETED ==========
[2026-02-10 11:17:55,321] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Created with session: 33c7282a-8620-4fc3-b34e-59213023adb2, transport: GrpcStreamingTransportImpl, callbackServer: GrpcStreamingTransportImpl, SP: app1
[2026-02-10 11:17:55,321] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWithRemote] Starting for SP: app1, contextId: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:17:55,322] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWithRemote] Registered 39 host functions: [doAssociationWithLocalUser, getAuthenticatedApplications, terminateUserSession, assignUserRoles, callChoreo, checkSessionExistence, httpPost, sendError, checkMicrosoftEmailVerification, removeUserRoles, sendEmail, getUserSessions, callElastic, hasAnyOfTheRolesV2, getValueFromDecodedAssertion, removeAssociatedLocalUser, loadLocalLibrary, assignUserRolesV2, getAssociatedLocalUser, removeUserRolesV2, filterAuthenticators, setCookie, resolveMultiAttributeLoginIdentifier, getUsersWithClaimValues, getCookieValue, callAnalytics, isAnyOfTheRolesAssignedToUser, updateUserPassword, hasAnyOfTheRoles, getUniqueUserWithClaimValues, hasRole, getMaskedValue, getSecretByName, publishToAnalytics, isMemberOfAnyOfGroups, executeStep, promptIdentifierForStep, prompt, httpGet]
[2026-02-10 11:17:55,322] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWithRemote] Sending script (length: 27878) to sidecar for evaluation
[2026-02-10 11:17:55,323] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] evaluate() called, session: 33c7282a-8620-4fc3-b34e-59213023adb2, sourceId: adaptive-script
[2026-02-10 11:17:55,323] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Ensuring connection to remote engine
[2026-02-10 11:17:55,323] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] ensureConnected - transport: GrpcStreamingTransportImpl, connected: false
[2026-02-10 11:17:55,323] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Connecting transport
[2026-02-10 11:17:55,323] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] connect() to localhost:50051
[2026-02-10 11:17:55,323] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcConnectionManager} - [GrpcConnectionManager] Creating new gRPC client channel to: localhost:50051
[2026-02-10 11:17:55,491] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcConnectionManager} - [GrpcConnectionManager] gRPC client channel created successfully
[2026-02-10 11:17:55,494] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Created async stub for target: localhost:50051
[2026-02-10 11:17:55,495] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Connected successfully to: localhost:50051
[2026-02-10 11:17:55,495] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Connected to remote engine successfully
[2026-02-10 11:17:55,495] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Connection established, registering handler...
[2026-02-10 11:17:55,495] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Starting callback server if needed
[2026-02-10 11:17:55,495] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] start() - no separate callback server needed in streaming mode
[2026-02-10 11:17:55,495] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering handler with callback server: GrpcStreamingTransportImpl
[2026-02-10 11:17:55,495] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] registerHandler for session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:55,496] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Handler registered: true
[2026-02-10 11:17:55,509] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback address: streaming://localhost:50051
[2026-02-10 11:17:55,509] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing 0 bindings, 39 host functions
[2026-02-10 11:17:55,509] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: doAssociationWithLocalUser
[2026-02-10 11:17:55,510] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: terminateUserSession
[2026-02-10 11:17:55,510] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: assignUserRoles
[2026-02-10 11:17:55,510] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: callChoreo
[2026-02-10 11:17:55,510] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: checkMicrosoftEmailVerification
[2026-02-10 11:17:55,510] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getUserSessions
[2026-02-10 11:17:55,510] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: callElastic
[2026-02-10 11:17:55,511] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: hasAnyOfTheRolesV2
[2026-02-10 11:17:55,511] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: removeAssociatedLocalUser
[2026-02-10 11:17:55,511] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: assignUserRolesV2
[2026-02-10 11:17:55,511] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: resolveMultiAttributeLoginIdentifier
[2026-02-10 11:17:55,511] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getUsersWithClaimValues
[2026-02-10 11:17:55,511] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getCookieValue
[2026-02-10 11:17:55,511] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: callAnalytics
[2026-02-10 11:17:55,511] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: isAnyOfTheRolesAssignedToUser
[2026-02-10 11:17:55,512] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: updateUserPassword
[2026-02-10 11:17:55,512] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getUniqueUserWithClaimValues
[2026-02-10 11:17:55,512] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: hasRole
[2026-02-10 11:17:55,512] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getMaskedValue
[2026-02-10 11:17:55,512] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getSecretByName
[2026-02-10 11:17:55,512] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: promptIdentifierForStep
[2026-02-10 11:17:55,512] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: httpGet
[2026-02-10 11:17:55,512] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getAuthenticatedApplications
[2026-02-10 11:17:55,513] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: checkSessionExistence
[2026-02-10 11:17:55,513] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: httpPost
[2026-02-10 11:17:55,513] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: sendError
[2026-02-10 11:17:55,513] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: removeUserRoles
[2026-02-10 11:17:55,513] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: sendEmail
[2026-02-10 11:17:55,513] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getValueFromDecodedAssertion
[2026-02-10 11:17:55,513] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: loadLocalLibrary
[2026-02-10 11:17:55,513] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: getAssociatedLocalUser
[2026-02-10 11:17:55,514] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: removeUserRolesV2
[2026-02-10 11:17:55,514] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: filterAuthenticators
[2026-02-10 11:17:55,514] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: setCookie
[2026-02-10 11:17:55,514] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: hasAnyOfTheRoles
[2026-02-10 11:17:55,514] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: publishToAnalytics
[2026-02-10 11:17:55,514] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: isMemberOfAnyOfGroups
[2026-02-10 11:17:55,514] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: executeStep
[2026-02-10 11:17:55,514] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering host function: prompt
[2026-02-10 11:17:55,515] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding context data, step: 0, subject: null
[2026-02-10 11:17:55,517] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Sending evaluate request to remote engine...
[2026-02-10 11:17:55,518] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] sendEvaluate() - session: 33c7282a-8620-4fc3-b34e-59213023adb2, script length: 27878
[2026-02-10 11:17:55,536] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Sent EvaluateRequest on stream, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,406] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,407] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: serviceProviderName, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,425] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,425] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: tenantDomain, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,427] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,427] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: currentKnownSubject, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,431] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,431] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,437] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,437] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::ip, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,440] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,440] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::headers, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,443] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,444] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::headers::User-Agent, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,448] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,448] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::headers::Host, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,450] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,450] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::params, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,451] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,452] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::params::sessionDataKey, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,453] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,453] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::params::type, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,454] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,455] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::cookies, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,456] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,456] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::cookies::commonAuthId, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,458] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,458] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: response, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,490] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,491] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: executeStep, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,491] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: executeStep with 2 args, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,491] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.lang.Double, value=1.0
[2026-02-10 11:17:56,492] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.HashMap, value={onFail=function(context) {
            Log.info('[TEST] STEP 1 FAILED - user authentication failed');
        }, onSuccess=function(context) {
            Log.info('==================================...[truncated]
[2026-02-10 11:17:56,492] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder$JsGraalStepExecuter
[2026-02-10 11:17:56,492] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:17:56,492] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: null
[2026-02-10 11:17:56,492] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:17:56,492] [b7217146-602c-401d-b1a3-55480f0fc189]  WARN {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] PROP_CURRENT_NODE not found or wrong type in authContext. Type: null
[2026-02-10 11:17:56,493] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: executeStep, params: 2, paramTypes: [class java.lang.Integer, class [Ljava.lang.Object;]
[2026-02-10 11:17:56,493] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=2, isVarArgs=true
[2026-02-10 11:17:56,493] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting varargs method: fixedParams=1, varArgType=Object
[2026-02-10 11:17:56,493] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[0] from Double to Integer
[2026-02-10 11:17:56,493] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting vararg[0] from HashMap to Object
[2026-02-10 11:17:56,493] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=java.lang.Integer
[2026-02-10 11:17:56,493] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=[Ljava.lang.Object;
[2026-02-10 11:17:56,493] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 11:17:56,494] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [addEventListeners] Received eventsMap with 2 entries
[2026-02-10 11:17:56,494] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [addEventListeners] Processing event: onFail, value type: java.lang.String, isValue: false, isMap: false
[2026-02-10 11:17:56,495] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [addEventListeners] Processing event: onSuccess, value type: java.lang.String, isValue: false, isMap: false
[2026-02-10 11:17:56,495] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: null
[2026-02-10 11:17:56,495] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function executeStep returned: null
[2026-02-10 11:17:56,513] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: EVALUATE_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,514] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received EvaluateResponse, session: 33c7282a-8620-4fc3-b34e-59213023adb2, success: true
[2026-02-10 11:17:56,514] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Stream completed, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,514] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received response, success: true
[2026-02-10 11:17:56,515] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWithRemote] Script evaluation successful, elapsed: 706ms
[2026-02-10 11:17:56,515] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWithRemote] Updating bindings from sidecar: [rolesToStepUp, dynamicFlag, context, secrets]
[2026-02-10 11:17:56,515] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWithRemote] Script execution completed for SP: app1
[2026-02-10 11:17:56,516] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [createWithRemote] Persisted 4 bindings
[2026-02-10 11:17:56,516] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] unregisterHandler for session: 33c7282a-8620-4fc3-b34e-59213023adb2
[2026-02-10 11:17:56,516] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] close() - clearing stub, correlationId: 735b7e3c-9fc0-4605-ace2-85b6b3a3ae05
[2026-02-10 11:18:24,185] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createTransport() CALLED ==========
[2026-02-10 11:18:24,186] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Transport instance #2
[2026-02-10 11:18:24,186] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Config details - grpcTarget: localhost:50051, callbackPort: 0
[2026-02-10 11:18:24,186] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Returning streaming transport, hashCode=1198084289
[2026-02-10 11:18:24,186] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createTransport() COMPLETED ==========
[2026-02-10 11:18:24,186] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createCallbackServer() CALLED ==========
[2026-02-10 11:18:24,186] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Config details - callbackPort: 0, grpcTarget: localhost:50051
[2026-02-10 11:18:24,186] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Returning streaming transport as callback server, hashCode=1198084289
[2026-02-10 11:18:24,186] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createCallbackServer() COMPLETED ==========
[2026-02-10 11:18:24,187] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Created with session: 43be787c-75f6-4283-ad12-aeb5cd3808f2, transport: GrpcStreamingTransportImpl, callbackServer: GrpcStreamingTransportImpl, SP: app1
[2026-02-10 11:18:24,187] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Starting for SP: app1, contextId: 097b182e-8978-4b40-b95a-65a48fad2f19, step: 1, authContext hashCode: 424102875
[2026-02-10 11:18:24,187] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Found 4 persisted bindings: [context, rolesToStepUp, dynamicFlag, secrets]
[2026-02-10 11:18:24,187] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: context = HashMap: {__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 11:18:24,187] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: rolesToStepUp = ArrayList: [admin, manager]
[2026-02-10 11:18:24,187] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: dynamicFlag = Long: 1
[2026-02-10 11:18:24,187] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: secrets = HashMap: {}
[2026-02-10 11:18:24,188] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback() called, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2, function length: 21943, args: 1
[2026-02-10 11:18:24,188] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - ensuring connection to remote engine
[2026-02-10 11:18:24,188] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] ensureConnected - transport: GrpcStreamingTransportImpl, connected: false
[2026-02-10 11:18:24,188] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Connecting transport
[2026-02-10 11:18:24,188] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] connect() to localhost:50051
[2026-02-10 11:18:24,189] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Created async stub for target: localhost:50051
[2026-02-10 11:18:24,189] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Connected successfully to: localhost:50051
[2026-02-10 11:18:24,189] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Connected to remote engine successfully
[2026-02-10 11:18:24,189] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - connection OK, registering handler...
[2026-02-10 11:18:24,189] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Starting callback server if needed
[2026-02-10 11:18:24,189] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] start() - no separate callback server needed in streaming mode
[2026-02-10 11:18:24,189] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering handler with callback server: GrpcStreamingTransportImpl
[2026-02-10 11:18:24,189] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] registerHandler for session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,189] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - handler registered: true
[2026-02-10 11:18:24,189] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Applying 4 callback bindings: [context, rolesToStepUp, dynamicFlag, secrets]
[2026-02-10 11:18:24,190] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: context = HashMap: {__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 11:18:24,190] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: rolesToStepUp = ArrayList: [admin, manager]
[2026-02-10 11:18:24,190] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: dynamicFlag = Long: 1
[2026-02-10 11:18:24,190] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: secrets = HashMap: {}
[2026-02-10 11:18:24,192] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - callback address: streaming://localhost:50051
[2026-02-10 11:18:24,192] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding context data, step: 1, subject: admin1
[2026-02-10 11:18:24,192] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing 1 arguments
[2026-02-10 11:18:24,192] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Arg[0] type: org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 11:18:24,192] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Total bindings to serialize: 4, keys: [rolesToStepUp, dynamicFlag, context, secrets]
[2026-02-10 11:18:24,192] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Host functions (excluded from bindings): [doAssociationWithLocalUser, terminateUserSession, assignUserRoles, callChoreo, checkMicrosoftEmailVerification, getUserSessions, callElastic, hasAnyOfTheRolesV2, removeAssociatedLocalUser, assignUserRolesV2, resolveMultiAttributeLoginIdentifier, getUsersWithClaimValues, getCookieValue, fail, callAnalytics, isAnyOfTheRolesAssignedToUser, updateUserPassword, getUniqueUserWithClaimValues, hasRole, getMaskedValue, getSecretByName, promptIdentifierForStep, httpGet, getAuthenticatedApplications, checkSessionExistence, httpPost, sendError, removeUserRoles, sendEmail, getValueFromDecodedAssertion, loadLocalLibrary, getAssociatedLocalUser, removeUserRolesV2, filterAuthenticators, setCookie, hasAnyOfTheRoles, publishToAnalytics, isMemberOfAnyOfGroups, executeStep, prompt]
[2026-02-10 11:18:24,193] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: rolesToStepUp = ArrayList: [admin, manager]
[2026-02-10 11:18:24,193] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: dynamicFlag = Long: 1
[2026-02-10 11:18:24,193] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: context = HashMap: {__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 11:18:24,193] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: secrets = HashMap: {}
[2026-02-10 11:18:24,193] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Bindings serialized: 4
[2026-02-10 11:18:24,193] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding 40 host function definitions
[2026-02-10 11:18:24,194] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: doAssociationWithLocalUser
[2026-02-10 11:18:24,194] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: terminateUserSession
[2026-02-10 11:18:24,194] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: assignUserRoles
[2026-02-10 11:18:24,194] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: callChoreo
[2026-02-10 11:18:24,194] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: checkMicrosoftEmailVerification
[2026-02-10 11:18:24,194] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getUserSessions
[2026-02-10 11:18:24,194] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: callElastic
[2026-02-10 11:18:24,194] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: hasAnyOfTheRolesV2
[2026-02-10 11:18:24,194] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: removeAssociatedLocalUser
[2026-02-10 11:18:24,194] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: assignUserRolesV2
[2026-02-10 11:18:24,194] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: resolveMultiAttributeLoginIdentifier
[2026-02-10 11:18:24,194] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getUsersWithClaimValues
[2026-02-10 11:18:24,195] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getCookieValue
[2026-02-10 11:18:24,195] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: fail
[2026-02-10 11:18:24,195] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: callAnalytics
[2026-02-10 11:18:24,195] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: isAnyOfTheRolesAssignedToUser
[2026-02-10 11:18:24,195] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: updateUserPassword
[2026-02-10 11:18:24,195] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getUniqueUserWithClaimValues
[2026-02-10 11:18:24,195] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: hasRole
[2026-02-10 11:18:24,195] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getMaskedValue
[2026-02-10 11:18:24,195] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getSecretByName
[2026-02-10 11:18:24,195] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: promptIdentifierForStep
[2026-02-10 11:18:24,195] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: httpGet
[2026-02-10 11:18:24,195] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getAuthenticatedApplications
[2026-02-10 11:18:24,195] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: checkSessionExistence
[2026-02-10 11:18:24,196] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: httpPost
[2026-02-10 11:18:24,196] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: sendError
[2026-02-10 11:18:24,196] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: removeUserRoles
[2026-02-10 11:18:24,196] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: sendEmail
[2026-02-10 11:18:24,196] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getValueFromDecodedAssertion
[2026-02-10 11:18:24,196] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: loadLocalLibrary
[2026-02-10 11:18:24,196] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getAssociatedLocalUser
[2026-02-10 11:18:24,196] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: removeUserRolesV2
[2026-02-10 11:18:24,197] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: filterAuthenticators
[2026-02-10 11:18:24,197] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: setCookie
[2026-02-10 11:18:24,197] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: hasAnyOfTheRoles
[2026-02-10 11:18:24,197] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: publishToAnalytics
[2026-02-10 11:18:24,197] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: isMemberOfAnyOfGroups
[2026-02-10 11:18:24,197] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: executeStep
[2026-02-10 11:18:24,197] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: prompt
[2026-02-10 11:18:24,197] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Sending executeCallback request to remote engine...
[2026-02-10 11:18:24,197] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] sendExecuteCallback() - session: 43be787c-75f6-4283-ad12-aeb5cd3808f2, function length: 21943
[2026-02-10 11:18:24,201] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Sent ExecuteCallbackRequest on stream, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,242] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,242] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,246] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,247] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,250] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,250] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::idp, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,253] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,253] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::authenticator, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,255] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,255] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,257] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,258] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::uniqueId, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,259] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,260] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::username, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,262] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,262] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::userStoreDomain, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,263] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,264] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::localClaims, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,267] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,267] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/givenname, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,270] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,270] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/lastname, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,272] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,272] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/emailaddress, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,275] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,275] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/mobile, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,277] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,278] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/country, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,282] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,282] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::claims, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,288] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_SET_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,288] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextPropertySet: steps::1::subject::claims::http://wso2.org/claims/testSessionClaim, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,288] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] setContextProperty called: steps::1::subject::claims::http://wso2.org/claims/testSessionClaim = String, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,288] [b7217146-602c-401d-b1a3-55480f0fc189]  WARN {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Cannot navigate path segment: 1
[2026-02-10 11:18:24,292] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,292] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::1::subject::claims::http://wso2.org/claims/testSessionClaim, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,296] [b7217146-602c-401d-b1a3-55480f0fc189] ERROR {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsClaims} - Error when getting claim : http://wso2.org/claims/testSessionClaim of user: admin1@carbon.super org.wso2.carbon.user.core.UserStoreException: org.wso2.carbon.user.core.UserStoreClientException: Mapped attribute cannot be found for claim : http://wso2.org/claims/testSessionClaim in user store : PRIMARY
	at org.wso2.carbon.user.core.common.AbstractUserStoreManager.callSecure(AbstractUserStoreManager.java:266)
	at org.wso2.carbon.user.core.common.AbstractUserStoreManager.getUserClaimValuesWithID(AbstractUserStoreManager.java:13268)
	at org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsClaims.getLocalUserClaim(JsClaims.java:437)
	at org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsClaims.getLocalClaim(JsClaims.java:377)
	at org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsClaims.getRuntimeClaim(JsClaims.java:460)
	at org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalRuntimeClaims.getMember(JsGraalRuntimeClaims.java:59)
	at org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine.getContextProperty(RemoteJsEngine.java:532)
	at com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl.handleContextPropertyRequest(GrpcStreamingTransportImpl.java:430)
	at com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl.access$1(GrpcStreamingTransportImpl.java:406)
	at com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl$1.lambda$1(GrpcStreamingTransportImpl.java:300)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:829)
Caused by: java.security.PrivilegedActionException: java.lang.reflect.InvocationTargetException
	at java.base/java.security.AccessController.doPrivileged(Native Method)
	at org.wso2.carbon.user.core.common.AbstractUserStoreManager.callSecure(AbstractUserStoreManager.java:247)
	... 14 more
Caused by: java.lang.reflect.InvocationTargetException
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.wso2.carbon.user.core.common.AbstractUserStoreManager$2.run(AbstractUserStoreManager.java:250)
	... 16 more
Caused by: org.wso2.carbon.user.core.UserStoreException: org.wso2.carbon.user.core.UserStoreClientException: Mapped attribute cannot be found for claim : http://wso2.org/claims/testSessionClaim in user store : PRIMARY
	at org.wso2.carbon.user.core.common.AbstractUserStoreManager.doGetUserClaimValuesWithID(AbstractUserStoreManager.java:13447)
	at org.wso2.carbon.user.core.common.AbstractUserStoreManager.getUserClaimValuesWithID(AbstractUserStoreManager.java:13314)
	... 21 more
Caused by: org.wso2.carbon.user.core.UserStoreClientException: Mapped attribute cannot be found for claim : http://wso2.org/claims/testSessionClaim in user store : PRIMARY
	at org.wso2.carbon.user.core.common.AbstractUserStoreManager.getClaimAtrribute(AbstractUserStoreManager.java:4264)
	at org.wso2.carbon.user.core.common.AbstractUserStoreManager.doGetUserClaimValuesWithID(AbstractUserStoreManager.java:13445)
	... 22 more

[2026-02-10 11:18:24,314] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,314] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: isMemberOfAnyOfGroups, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,314] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: isMemberOfAnyOfGroups with 2 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,314] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=authenticateduser, __basePath=steps::1::subject}
[2026-02-10 11:18:24,315] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.ArrayList, value=[admin, Internal/everyone]
[2026-02-10 11:18:24,315] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.IsMemberOfAnyOfGroupsFunctionImpl
[2026-02-10 11:18:24,315] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,315] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 11:18:24,315] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,315] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 11:18:24,322] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: isMemberOfAnyOfGroups, params: 2, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticatedUser, interface java.util.List]
[2026-02-10 11:18:24,323] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=2, isVarArgs=false
[2026-02-10 11:18:24,323] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[0] from HashMap to JsAuthenticatedUser
[2026-02-10 11:18:24,323] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=authenticateduser, basePath=steps::1::subject
[2026-02-10 11:18:24,323] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Navigating to nested property: steps::1::subject
[2026-02-10 11:18:24,323] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Successfully navigated to: steps::1::subject, result type: null
[2026-02-10 11:18:24,323] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[1] from ArrayList to List
[2026-02-10 11:18:24,323] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=java.util.HashMap
[2026-02-10 11:18:24,323] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=java.util.ArrayList
[2026-02-10 11:18:24,323] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 11:18:24,324] [b7217146-602c-401d-b1a3-55480f0fc189] ERROR {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Error in host function isMemberOfAnyOfGroups java.lang.IllegalArgumentException: argument type mismatch
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine.invokeHostFunction(RemoteJsEngine.java:436)
	at com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl.handleHostFunctionRequest(GrpcStreamingTransportImpl.java:378)
	at com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl.access$0(GrpcStreamingTransportImpl.java:346)
	at com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl$1.lambda$0(GrpcStreamingTransportImpl.java:294)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:829)

[2026-02-10 11:18:24,337] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,337] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: hasAnyOfTheRolesV2, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,337] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: hasAnyOfTheRolesV2 with 2 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,337] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 11:18:24,337] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.ArrayList, value=[Internal/admin, admin, Application/admin]
[2026-02-10 11:18:24,337] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.HasAnyOfTheRolesV2FunctionImpl
[2026-02-10 11:18:24,338] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,338] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 11:18:24,338] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,338] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 11:18:24,338] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: hasAnyOfTheRolesV2, params: 2, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext, interface java.util.List]
[2026-02-10 11:18:24,338] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=2, isVarArgs=false
[2026-02-10 11:18:24,338] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[0] from HashMap to JsAuthenticationContext
[2026-02-10 11:18:24,338] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=context, basePath=
[2026-02-10 11:18:24,338] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructing root context
[2026-02-10 11:18:24,338] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalAuthenticationContext from context proxy marker
[2026-02-10 11:18:24,338] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[1] from ArrayList to List
[2026-02-10 11:18:24,338] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 11:18:24,339] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=java.util.ArrayList
[2026-02-10 11:18:24,339] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 11:18:24,345] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: java.lang.Boolean=true
[2026-02-10 11:18:24,345] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function hasAnyOfTheRolesV2 returned: Boolean
[2026-02-10 11:18:24,348] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,349] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: hasAnyOfTheRolesV2, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,349] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: hasAnyOfTheRolesV2 with 2 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,349] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 11:18:24,349] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.ArrayList, value=[manager]
[2026-02-10 11:18:24,350] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.HasAnyOfTheRolesV2FunctionImpl
[2026-02-10 11:18:24,350] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,350] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 11:18:24,350] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,350] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 11:18:24,350] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: hasAnyOfTheRolesV2, params: 2, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext, interface java.util.List]
[2026-02-10 11:18:24,350] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=2, isVarArgs=false
[2026-02-10 11:18:24,350] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[0] from HashMap to JsAuthenticationContext
[2026-02-10 11:18:24,350] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=context, basePath=
[2026-02-10 11:18:24,350] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructing root context
[2026-02-10 11:18:24,350] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalAuthenticationContext from context proxy marker
[2026-02-10 11:18:24,350] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[1] from ArrayList to List
[2026-02-10 11:18:24,350] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 11:18:24,350] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=java.util.ArrayList
[2026-02-10 11:18:24,350] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 11:18:24,351] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: java.lang.Boolean=false
[2026-02-10 11:18:24,352] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function hasAnyOfTheRolesV2 returned: Boolean
[2026-02-10 11:18:24,355] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,355] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: hasAnyOfTheRolesV2, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,355] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: hasAnyOfTheRolesV2 with 2 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,355] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 11:18:24,355] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.ArrayList, value=[admin, manager]
[2026-02-10 11:18:24,355] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.HasAnyOfTheRolesV2FunctionImpl
[2026-02-10 11:18:24,356] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,356] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 11:18:24,356] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,356] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 11:18:24,356] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: hasAnyOfTheRolesV2, params: 2, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext, interface java.util.List]
[2026-02-10 11:18:24,356] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=2, isVarArgs=false
[2026-02-10 11:18:24,356] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[0] from HashMap to JsAuthenticationContext
[2026-02-10 11:18:24,356] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=context, basePath=
[2026-02-10 11:18:24,356] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructing root context
[2026-02-10 11:18:24,356] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalAuthenticationContext from context proxy marker
[2026-02-10 11:18:24,357] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[1] from ArrayList to List
[2026-02-10 11:18:24,357] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 11:18:24,357] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=java.util.ArrayList
[2026-02-10 11:18:24,357] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 11:18:24,358] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: java.lang.Boolean=true
[2026-02-10 11:18:24,359] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function hasAnyOfTheRolesV2 returned: Boolean
[2026-02-10 11:18:24,362] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,362] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: getUserSessions, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,363] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: getUserSessions with 1 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,363] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=authenticateduser, __basePath=steps::1::subject}
[2026-02-10 11:18:24,363] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.GetUserSessionsFunctionImpl
[2026-02-10 11:18:24,363] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,363] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 11:18:24,363] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,363] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 11:18:24,363] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: getUserSessions, params: 1, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticatedUser]
[2026-02-10 11:18:24,363] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=1, argsCount=1, isVarArgs=false
[2026-02-10 11:18:24,364] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[0] from HashMap to JsAuthenticatedUser
[2026-02-10 11:18:24,364] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=authenticateduser, basePath=steps::1::subject
[2026-02-10 11:18:24,364] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Navigating to nested property: steps::1::subject
[2026-02-10 11:18:24,364] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Successfully navigated to: steps::1::subject, result type: null
[2026-02-10 11:18:24,364] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=java.util.HashMap
[2026-02-10 11:18:24,364] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 1 adapted args
[2026-02-10 11:18:24,364] [b7217146-602c-401d-b1a3-55480f0fc189] ERROR {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Error in host function getUserSessions java.lang.IllegalArgumentException: argument type mismatch
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine.invokeHostFunction(RemoteJsEngine.java:436)
	at com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl.handleHostFunctionRequest(GrpcStreamingTransportImpl.java:378)
	at com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl.access$0(GrpcStreamingTransportImpl.java:346)
	at com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl$1.lambda$0(GrpcStreamingTransportImpl.java:294)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:829)

[2026-02-10 11:18:24,369] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,369] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: getUniqueUserWithClaimValues, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,369] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: getUniqueUserWithClaimValues with 2 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,369] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={http://wso2.org/claims/username=admin1}
[2026-02-10 11:18:24,369] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 11:18:24,369] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.store.GetUserWithClaimValuesV2FunctionImpl
[2026-02-10 11:18:24,369] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,369] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 11:18:24,369] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,370] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 11:18:24,376] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: getUniqueUserWithClaimValues, params: 3, paramTypes: [interface java.util.Map, class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext, class [Ljava.lang.String;]
[2026-02-10 11:18:24,377] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=3, argsCount=2, isVarArgs=true
[2026-02-10 11:18:24,377] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting varargs method: fixedParams=2, varArgType=String
[2026-02-10 11:18:24,377] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[0] from HashMap to Map
[2026-02-10 11:18:24,377] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[1] from HashMap to JsAuthenticationContext
[2026-02-10 11:18:24,377] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=context, basePath=
[2026-02-10 11:18:24,377] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructing root context
[2026-02-10 11:18:24,377] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalAuthenticationContext from context proxy marker
[2026-02-10 11:18:24,377] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=java.util.HashMap
[2026-02-10 11:18:24,377] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 11:18:24,377] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[2]: type=[Ljava.lang.String;
[2026-02-10 11:18:24,377] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 3 adapted args
[2026-02-10 11:18:24,401] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticatedUser=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticatedUser@96ebad2
[2026-02-10 11:18:24,401] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function getUniqueUserWithClaimValues returned: JsGraalAuthenticatedUser
[2026-02-10 11:18:24,405] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,406] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: getUsersWithClaimValues, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,406] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: getUsersWithClaimValues with 2 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,406] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={http://wso2.org/claims/username=admin1}
[2026-02-10 11:18:24,406] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 11:18:24,406] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.store.GetUsersWithClaimValuesFunctionImpl
[2026-02-10 11:18:24,406] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,406] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 11:18:24,406] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,406] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 11:18:24,406] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: getUsersWithClaimValues, params: 3, paramTypes: [interface java.util.Map, class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext, class [Ljava.lang.String;]
[2026-02-10 11:18:24,406] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=3, argsCount=2, isVarArgs=true
[2026-02-10 11:18:24,406] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting varargs method: fixedParams=2, varArgType=String
[2026-02-10 11:18:24,407] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[0] from HashMap to Map
[2026-02-10 11:18:24,407] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[1] from HashMap to JsAuthenticationContext
[2026-02-10 11:18:24,407] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=context, basePath=
[2026-02-10 11:18:24,407] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructing root context
[2026-02-10 11:18:24,407] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalAuthenticationContext from context proxy marker
[2026-02-10 11:18:24,407] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=java.util.HashMap
[2026-02-10 11:18:24,407] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 11:18:24,407] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[2]: type=[Ljava.lang.String;
[2026-02-10 11:18:24,407] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 3 adapted args
[2026-02-10 11:18:24,408] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: java.util.ArrayList=[org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticatedUser@3fb41da8]
[2026-02-10 11:18:24,408] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function getUsersWithClaimValues returned: ArrayList
[2026-02-10 11:18:24,411] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,411] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: response, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,415] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,416] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: setCookie, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,416] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: setCookie with 4 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,416] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=servletresponse, __basePath=response}
[2026-02-10 11:18:24,416] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.lang.String, value=testAdaptiveCookie
[2026-02-10 11:18:24,416] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[2]: type=java.lang.String, value=cookieVal123
[2026-02-10 11:18:24,416] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[3]: type=java.util.HashMap, value={path=/, encrypt=false, sameSite=LAX, max-age=3600.0, sign=false, httpOnly=true, secure=true}
[2026-02-10 11:18:24,416] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.http.SetCookieFunctionImpl
[2026-02-10 11:18:24,416] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,417] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 11:18:24,417] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,417] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 11:18:24,424] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: setCookie, params: 3, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsServletResponse, class java.lang.String, class [Ljava.lang.Object;]
[2026-02-10 11:18:24,424] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=3, argsCount=4, isVarArgs=true
[2026-02-10 11:18:24,424] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting varargs method: fixedParams=2, varArgType=Object
[2026-02-10 11:18:24,424] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[0] from HashMap to JsServletResponse
[2026-02-10 11:18:24,424] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=servletresponse, basePath=response
[2026-02-10 11:18:24,424] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Navigating to nested property: response
[2026-02-10 11:18:24,424] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Successfully navigated to: response, result type: JsGraalServletResponse
[2026-02-10 11:18:24,424] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalServletResponse from context proxy marker
[2026-02-10 11:18:24,424] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[1] from String to String
[2026-02-10 11:18:24,424] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting vararg[0] from String to Object
[2026-02-10 11:18:24,424] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting vararg[1] from HashMap to Object
[2026-02-10 11:18:24,424] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalServletResponse
[2026-02-10 11:18:24,424] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=java.lang.String
[2026-02-10 11:18:24,424] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[2]: type=[Ljava.lang.Object;
[2026-02-10 11:18:24,424] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 3 adapted args
[2026-02-10 11:18:24,426] [b7217146-602c-401d-b1a3-55480f0fc189] ERROR {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Host function 'setCookie' threw exception: java.lang.ClassCastException: class java.lang.Double cannot be cast to class java.lang.Integer (java.lang.Double and java.lang.Integer are in module java.base of loader 'bootstrap')
[2026-02-10 11:18:24,427] [b7217146-602c-401d-b1a3-55480f0fc189] ERROR {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Root cause stack trace: java.lang.ClassCastException: class java.lang.Double cannot be cast to class java.lang.Integer (java.lang.Double and java.lang.Integer are in module java.base of loader 'bootstrap')
	at org.wso2.carbon.identity.conditional.auth.functions.http.SetCookieFunctionImpl.setCookie(SetCookieFunctionImpl.java:112)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine.invokeHostFunction(RemoteJsEngine.java:436)
	at com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl.handleHostFunctionRequest(GrpcStreamingTransportImpl.java:378)
	at com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl.access$0(GrpcStreamingTransportImpl.java:346)
	at com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl$1.lambda$0(GrpcStreamingTransportImpl.java:294)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:829)

[2026-02-10 11:18:24,433] [b7217146-602c-401d-b1a3-55480f0fc189] ERROR {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Error in host function setCookie java.lang.reflect.InvocationTargetException
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine.invokeHostFunction(RemoteJsEngine.java:436)
	at com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl.handleHostFunctionRequest(GrpcStreamingTransportImpl.java:378)
	at com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl.access$0(GrpcStreamingTransportImpl.java:346)
	at com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl$1.lambda$0(GrpcStreamingTransportImpl.java:294)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:829)
Caused by: java.lang.ClassCastException: class java.lang.Double cannot be cast to class java.lang.Integer (java.lang.Double and java.lang.Integer are in module java.base of loader 'bootstrap')
	at org.wso2.carbon.identity.conditional.auth.functions.http.SetCookieFunctionImpl.setCookie(SetCookieFunctionImpl.java:112)
	... 13 more

[2026-02-10 11:18:24,444] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,444] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,446] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,446] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: getCookieValue, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,446] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: getCookieValue with 3 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,446] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=servletrequest, __basePath=request}
[2026-02-10 11:18:24,446] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.lang.String, value=testAdaptiveCookie
[2026-02-10 11:18:24,447] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[2]: type=java.util.HashMap, value={validateSignature=false, decrypt=false}
[2026-02-10 11:18:24,447] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.http.GetCookieFunctionImpl
[2026-02-10 11:18:24,447] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,447] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 11:18:24,447] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,447] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 11:18:24,447] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: getCookieValue, params: 2, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsServletRequest, class [Ljava.lang.Object;]
[2026-02-10 11:18:24,447] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=3, isVarArgs=true
[2026-02-10 11:18:24,447] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting varargs method: fixedParams=1, varArgType=Object
[2026-02-10 11:18:24,447] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[0] from HashMap to JsServletRequest
[2026-02-10 11:18:24,447] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=servletrequest, basePath=request
[2026-02-10 11:18:24,447] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Navigating to nested property: request
[2026-02-10 11:18:24,448] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Successfully navigated to: request, result type: JsGraalServletRequest
[2026-02-10 11:18:24,448] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalServletRequest from context proxy marker
[2026-02-10 11:18:24,448] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting vararg[0] from String to Object
[2026-02-10 11:18:24,448] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting vararg[1] from HashMap to Object
[2026-02-10 11:18:24,448] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalServletRequest
[2026-02-10 11:18:24,448] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=[Ljava.lang.Object;
[2026-02-10 11:18:24,448] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 11:18:24,448] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: null
[2026-02-10 11:18:24,448] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function getCookieValue returned: null
[2026-02-10 11:18:24,450] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,450] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::params, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,452] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,452] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::params::sessionDataKey, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,455] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,455] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: request::params::username, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,456] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,457] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: response::headers, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,458] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_SET_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,459] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextPropertySet: response::headers::X-Adaptive-Test, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,459] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] setContextProperty called: response::headers::X-Adaptive-Test = String, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,459] [b7217146-602c-401d-b1a3-55480f0fc189]  WARN {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Could not set property: response::headers::X-Adaptive-Test
[2026-02-10 11:18:24,462] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,462] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: executeStep, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,463] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: executeStep with 2 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,463] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.lang.Double, value=2.0
[2026-02-10 11:18:24,463] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.HashMap, value={onFail=function(context) {
                        Log.info('[TEST] STEP 2 FAILED');
                    }, onSuccess=function(context) {
                        Log.info('===========================...[truncated]
[2026-02-10 11:18:24,463] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder$JsGraalStepExecuterInAsyncEvent
[2026-02-10 11:18:24,463] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,463] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 11:18:24,463] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:24,463] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 11:18:24,463] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: executeStep, params: 2, paramTypes: [class java.lang.Integer, class [Ljava.lang.Object;]
[2026-02-10 11:18:24,463] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=2, isVarArgs=true
[2026-02-10 11:18:24,463] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting varargs method: fixedParams=1, varArgType=Object
[2026-02-10 11:18:24,463] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting fixed arg[0] from Double to Integer
[2026-02-10 11:18:24,464] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting vararg[0] from HashMap to Object
[2026-02-10 11:18:24,464] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=java.lang.Integer
[2026-02-10 11:18:24,464] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=[Ljava.lang.Object;
[2026-02-10 11:18:24,464] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 11:18:24,464] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [addEventListeners] Received eventsMap with 2 entries
[2026-02-10 11:18:24,464] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [addEventListeners] Processing event: onFail, value type: java.lang.String, isValue: false, isMap: false
[2026-02-10 11:18:24,464] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [addEventListeners] Processing event: onSuccess, value type: java.lang.String, isValue: false, isMap: false
[2026-02-10 11:18:24,464] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: null
[2026-02-10 11:18:24,464] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function executeStep returned: null
[2026-02-10 11:18:24,473] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: EXECUTE_CALLBACK_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,473] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Stream completed, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,473] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received ExecuteCallbackResponse, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2, success: true
[2026-02-10 11:18:24,473] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback response - success: true, elapsed: 261ms
[2026-02-10 11:18:24,473] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Re-persisted 4 bindings after callback
[2026-02-10 11:18:24,474] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] unregisterHandler for session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[2026-02-10 11:18:24,474] [50a53f07-78fb-48f1-9551-86b84a0bfbf2]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] close() - clearing stub, correlationId: 735b7e3c-9fc0-4605-ace2-85b6b3a3ae05
[2026-02-10 11:18:36,486] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createTransport() CALLED ==========
[2026-02-10 11:18:36,486] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Transport instance #3
[2026-02-10 11:18:36,486] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Config details - grpcTarget: localhost:50051, callbackPort: 0
[2026-02-10 11:18:36,486] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Returning streaming transport, hashCode=1198084289
[2026-02-10 11:18:36,486] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createTransport() COMPLETED ==========
[2026-02-10 11:18:36,486] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createCallbackServer() CALLED ==========
[2026-02-10 11:18:36,487] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Config details - callbackPort: 0, grpcTarget: localhost:50051
[2026-02-10 11:18:36,487] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] Returning streaming transport as callback server, hashCode=1198084289
[2026-02-10 11:18:36,487] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcTransportProvider} - [GrpcTransportProvider] ========== createCallbackServer() COMPLETED ==========
[2026-02-10 11:18:36,487] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Created with session: 90ed9a2a-5ccf-451f-8377-cf35af038b46, transport: GrpcStreamingTransportImpl, callbackServer: GrpcStreamingTransportImpl, SP: app1
[2026-02-10 11:18:36,487] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Starting for SP: app1, contextId: 097b182e-8978-4b40-b95a-65a48fad2f19, step: 2, authContext hashCode: 424102875
[2026-02-10 11:18:36,487] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Found 4 persisted bindings: [context, rolesToStepUp, dynamicFlag, secrets]
[2026-02-10 11:18:36,487] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: context = HashMap: {__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 11:18:36,487] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: rolesToStepUp = ArrayList: [admin, manager]
[2026-02-10 11:18:36,487] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: dynamicFlag = Long: 2
[2026-02-10 11:18:36,487] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Binding: secrets = HashMap: {}
[2026-02-10 11:18:36,487] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback() called, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46, function length: 2411, args: 1
[2026-02-10 11:18:36,487] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - ensuring connection to remote engine
[2026-02-10 11:18:36,487] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] ensureConnected - transport: GrpcStreamingTransportImpl, connected: false
[2026-02-10 11:18:36,487] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Connecting transport
[2026-02-10 11:18:36,487] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] connect() to localhost:50051
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Created async stub for target: localhost:50051
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Connected successfully to: localhost:50051
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Connected to remote engine successfully
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - connection OK, registering handler...
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Starting callback server if needed
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] start() - no separate callback server needed in streaming mode
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Registering handler with callback server: GrpcStreamingTransportImpl
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] registerHandler for session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - handler registered: true
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Applying 4 callback bindings: [context, rolesToStepUp, dynamicFlag, secrets]
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: context = HashMap: {__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: rolesToStepUp = ArrayList: [admin, manager]
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: dynamicFlag = Long: 2
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Callback binding: secrets = HashMap: {}
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback - callback address: streaming://localhost:50051
[2026-02-10 11:18:36,488] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding context data, step: 2, subject: admin1
[2026-02-10 11:18:36,489] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing 1 arguments
[2026-02-10 11:18:36,489] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Arg[0] type: org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 11:18:36,489] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Total bindings to serialize: 4, keys: [rolesToStepUp, dynamicFlag, context, secrets]
[2026-02-10 11:18:36,489] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Host functions (excluded from bindings): [doAssociationWithLocalUser, terminateUserSession, assignUserRoles, callChoreo, checkMicrosoftEmailVerification, getUserSessions, callElastic, hasAnyOfTheRolesV2, removeAssociatedLocalUser, assignUserRolesV2, resolveMultiAttributeLoginIdentifier, getUsersWithClaimValues, getCookieValue, fail, callAnalytics, isAnyOfTheRolesAssignedToUser, updateUserPassword, getUniqueUserWithClaimValues, hasRole, getMaskedValue, getSecretByName, promptIdentifierForStep, httpGet, getAuthenticatedApplications, checkSessionExistence, httpPost, sendError, removeUserRoles, sendEmail, getValueFromDecodedAssertion, loadLocalLibrary, getAssociatedLocalUser, removeUserRolesV2, filterAuthenticators, setCookie, hasAnyOfTheRoles, publishToAnalytics, isMemberOfAnyOfGroups, executeStep, prompt]
[2026-02-10 11:18:36,489] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: rolesToStepUp = ArrayList: [admin, manager]
[2026-02-10 11:18:36,489] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: dynamicFlag = Long: 2
[2026-02-10 11:18:36,489] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: context = HashMap: {__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 11:18:36,489] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Serializing binding: secrets = HashMap: {}
[2026-02-10 11:18:36,489] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Bindings serialized: 4
[2026-02-10 11:18:36,489] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding 40 host function definitions
[2026-02-10 11:18:36,489] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: doAssociationWithLocalUser
[2026-02-10 11:18:36,489] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: terminateUserSession
[2026-02-10 11:18:36,489] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: assignUserRoles
[2026-02-10 11:18:36,489] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: callChoreo
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: checkMicrosoftEmailVerification
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getUserSessions
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: callElastic
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: hasAnyOfTheRolesV2
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: removeAssociatedLocalUser
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: assignUserRolesV2
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: resolveMultiAttributeLoginIdentifier
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getUsersWithClaimValues
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getCookieValue
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: fail
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: callAnalytics
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: isAnyOfTheRolesAssignedToUser
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: updateUserPassword
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getUniqueUserWithClaimValues
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: hasRole
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getMaskedValue
[2026-02-10 11:18:36,490] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getSecretByName
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: promptIdentifierForStep
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: httpGet
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getAuthenticatedApplications
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: checkSessionExistence
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: httpPost
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: sendError
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: removeUserRoles
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: sendEmail
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getValueFromDecodedAssertion
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: loadLocalLibrary
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: getAssociatedLocalUser
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: removeUserRolesV2
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: filterAuthenticators
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: setCookie
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: hasAnyOfTheRoles
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: publishToAnalytics
[2026-02-10 11:18:36,491] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: isMemberOfAnyOfGroups
[2026-02-10 11:18:36,492] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: executeStep
[2026-02-10 11:18:36,492] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adding host function: prompt
[2026-02-10 11:18:36,492] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Sending executeCallback request to remote engine...
[2026-02-10 11:18:36,492] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] sendExecuteCallback() - session: 90ed9a2a-5ccf-451f-8377-cf35af038b46, function length: 2411
[2026-02-10 11:18:36,494] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Sent ExecuteCallbackRequest on stream, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,527] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,528] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,530] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,530] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::2, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,537] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,537] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::2::idp, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,544] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,545] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::2::authenticator, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,547] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,547] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::2::subject, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,549] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: CONTEXT_PROPERTY_REQUEST, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,549] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleContextProperty: steps::2::subject::username, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,552] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: HOST_FUNCTION_REQUEST, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,552] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] handleHostFunction: hasAnyOfTheRolesV2, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,552] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] invokeHostFunction called: hasAnyOfTheRolesV2 with 2 args, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,552] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[0]: type=java.util.HashMap, value={__isContextProxy=true, __proxyType=context, __basePath=}
[2026-02-10 11:18:36,552] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Raw arg[1]: type=java.util.ArrayList, value=[manager]
[2026-02-10 11:18:36,552] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found host function impl: org.wso2.carbon.identity.conditional.auth.functions.user.HasAnyOfTheRolesV2FunctionImpl
[2026-02-10 11:18:36,552] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Setting up thread context for tenant: carbon.super, contextId: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:36,552] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Thread context set - tenantDomain: carbon.super, tenantId: -1234, username: admin1
[2026-02-10 11:18:36,552] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set contextForJs ThreadLocal with authContext: 097b182e-8978-4b40-b95a-65a48fad2f19
[2026-02-10 11:18:36,553] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Set dynamicallyBuiltBaseNode ThreadLocal: DynamicDecisionNode
[2026-02-10 11:18:36,553] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Found @HostAccess.Export method: hasAnyOfTheRolesV2, params: 2, paramTypes: [class org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext, interface java.util.List]
[2026-02-10 11:18:36,553] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] adaptArgumentsForMethod: paramCount=2, argsCount=2, isVarArgs=false
[2026-02-10 11:18:36,553] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[0] from HashMap to JsAuthenticationContext
[2026-02-10 11:18:36,553] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Received context proxy marker: type=context, basePath=
[2026-02-10 11:18:36,553] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructing root context
[2026-02-10 11:18:36,553] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Reconstructed JsGraalAuthenticationContext from context proxy marker
[2026-02-10 11:18:36,553] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapting arg[1] from ArrayList to List
[2026-02-10 11:18:36,553] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[0]: type=org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.graaljs.JsGraalAuthenticationContext
[2026-02-10 11:18:36,553] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Adapted arg[1]: type=java.util.ArrayList
[2026-02-10 11:18:36,553] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Invoking method with 2 adapted args
[2026-02-10 11:18:36,555] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] Method returned: java.lang.Boolean=false
[2026-02-10 11:18:36,555] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Host function hasAnyOfTheRolesV2 returned: Boolean
[2026-02-10 11:18:36,567] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received message type: EXECUTE_CALLBACK_RESPONSE, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,567] [b7217146-602c-401d-b1a3-55480f0fc189]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Stream completed, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,567] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] Received ExecuteCallbackResponse, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46, success: true
[2026-02-10 11:18:36,567] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.RemoteJsEngine} - [RemoteJsEngine] executeCallback response - success: true, elapsed: 63ms
[2026-02-10 11:18:36,567] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.JsGraalGraphBuilder} - [evaluateRemote] Re-persisted 4 bindings after callback
[2026-02-10 11:18:36,567] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] unregisterHandler for session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[2026-02-10 11:18:36,567] [1389cad3-01c6-41fa-a4c8-6cf25f9c1d48]  INFO {com.wso2.identity.asgardeo.scope.service.graaljs.transport.GrpcStreamingTransportImpl} - [GrpcStreaming] close() - clearing stub, correlationId: 735b7e3c-9fc0-4605-ace2-85b6b3a3ae05
" ,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

External Service Log "bashitha@bashitha external-graaljs % java -Xmx512m -jar /Users/bashitha/Downloads/product/external-graaljs/target/graaljs-sidecar-1.0.0-SNAPSHOT.jar grpc
[main] INFO org.wso2.carbon.identity.graaljs.sidecar.Main - [Main] Starting sidecar in gRPC mode
[SIDECAR-STARTUP] Starting GraalJS Sidecar in gRPC mode
[SIDECAR-STARTUP] Port: 50051
[SIDECAR-STARTUP] Statement limit: 5000, Thread pool size: 10
[main] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Started on port: 50051
[main] INFO org.wso2.carbon.identity.graaljs.sidecar.Main - [Main] Sidecar started on: localhost:50051
[SIDECAR-STARTUP] Sidecar listening on: localhost:50051
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] New stream opened
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: EVALUATE_REQUEST, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] handleEvaluate - session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Created streaming callback client
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Created with external delegate for session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] handleEvaluate (streaming) called
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] handleEvaluate (streaming) - session: 33c7282a-8620-4fc3-b34e-59213023adb2, sourceId: adaptive-script
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Script length: 27878, bindings: 0, hostFunctions: 39
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: executeStep, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: sendError, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: fail, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: showPrompt, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: loadLocalLibrary, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: getSecretByName, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Created HostFunctionStub for: selectAcrFrom, callbackClient: available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Creating DYNAMIC context proxy with data: username=, userStoreDomain=, tenantDomain=, step=0
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Bound DYNAMIC context proxy for session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Starting script evaluation (streaming)...
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] ========================================
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] ADAPTIVE SCRIPT STARTED
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] ========================================
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'serviceProviderName', full path: serviceProviderName
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: serviceProviderName, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'serviceProviderName' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] context.serviceProviderName: app1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'tenantDomain', full path: tenantDomain
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: tenantDomain, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'tenantDomain' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] context.tenantDomain: carbon.super
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'currentKnownSubject', full path: currentKnownSubject
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: currentKnownSubject, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'currentKnownSubject' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] context.currentKnownSubject: undefined
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'request', full path: request
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'request', type: servletrequest, keys: 4
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] context.request: EXISTS
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'ip', full path: request::ip
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::ip, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'ip' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] request.ip: 127.0.0.1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'headers', full path: request::headers
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::headers, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'headers', type: writableparameters, keys: 15
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'User-Agent', full path: request::headers::User-Agent
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::headers::User-Agent, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'User-Agent' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] request.headers[User-Agent]: not available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'Host', full path: request::headers::Host
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::headers::Host, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'Host' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] request.headers[Host]: not available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'params', full path: request::params
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::params, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'params', type: parameters, keys: 12
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'sessionDataKey', full path: request::params::sessionDataKey
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::params::sessionDataKey, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'sessionDataKey' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] request.params.sessionDataKey[0]: not available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'type', full path: request::params::type
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::params::type, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'type' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] request.params.type[0]: not available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'cookies', full path: request::cookies
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::cookies, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'cookies', type: writableparameters, keys: none
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'commonAuthId', full path: request::cookies::commonAuthId
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::cookies::commonAuthId, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'commonAuthId' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] request.cookies[commonAuthId]: not available
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'response', full path: response
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: response, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
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
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'executeStep' with 2 args, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: executeStep, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 33c7282a-8620-4fc3-b34e-59213023adb2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: null
[DEBUG-SIDECAR] Callback returned: null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] Script evaluation completed successfully (streaming)
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Evaluate completed in 737ms, success: true
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] New stream opened
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: EXECUTE_CALLBACK_REQUEST, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] handleExecuteCallback - session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Created streaming callback client
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Created with external delegate for session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] handleExecuteCallback (streaming) - session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
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
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'steps', type: steps, keys: none
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember '1', full path: steps::1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for '1', type: step, keys: 4
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'idp', full path: steps::1::idp
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::idp, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'idp' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] step.idp: LOCAL
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'authenticator', full path: steps::1::authenticator
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::authenticator, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'authenticator' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] step.authenticator: BasicAuthenticator
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'subject', full path: steps::1::subject
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'subject', type: authenticateduser, keys: 8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'uniqueId', full path: steps::1::subject::uniqueId
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::uniqueId, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'uniqueId' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.uniqueId: 9ed0e960-61fc-4e59-9106-6334c3282edc
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'username', full path: steps::1::subject::username
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::username, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'username' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.username: admin1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'userStoreDomain', full path: steps::1::subject::userStoreDomain
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::userStoreDomain, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'userStoreDomain' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.userStoreDomain: PRIMARY
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'localClaims', full path: steps::1::subject::localClaims
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::localClaims, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'localClaims', type: claims, keys: none
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'http://wso2.org/claims/givenname', full path: steps::1::subject::localClaims::http://wso2.org/claims/givenname
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/givenname, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'http://wso2.org/claims/givenname' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.localClaims[givenname]: admin1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'http://wso2.org/claims/lastname', full path: steps::1::subject::localClaims::http://wso2.org/claims/lastname
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/lastname, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'http://wso2.org/claims/lastname' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.localClaims[lastname]: 1admin
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'http://wso2.org/claims/emailaddress', full path: steps::1::subject::localClaims::http://wso2.org/claims/emailaddress
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/emailaddress, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'http://wso2.org/claims/emailaddress' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.localClaims[emailaddress]: bs@email.com
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'http://wso2.org/claims/mobile', full path: steps::1::subject::localClaims::http://wso2.org/claims/mobile
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/mobile, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'http://wso2.org/claims/mobile' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.localClaims[mobile]: not set
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'http://wso2.org/claims/country', full path: steps::1::subject::localClaims::http://wso2.org/claims/country
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::localClaims::http://wso2.org/claims/country, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'http://wso2.org/claims/country' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.localClaims[country]: not set
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'claims', full path: steps::1::subject::claims
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::claims, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'claims', type: runtimeclaims, keys: none
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] putMember 'steps::1::subject::claims::http://wso2.org/claims/testSessionClaim' = testValue123
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] setContextProperty 'steps::1::subject::claims::http://wso2.org/claims/testSessionClaim' (type: runtimeclaims), session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] setContextProperty: steps::1::subject::claims::http://wso2.org/claims/testSessionClaim, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertySetRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_SET_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_SET_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertySetResponse, success: false
[pool-1-thread-1] ERROR org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Failed to set 'http://wso2.org/claims/testSessionClaim': 
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'http://wso2.org/claims/testSessionClaim', full path: steps::1::subject::claims::http://wso2.org/claims/testSessionClaim
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::1::subject::claims::http://wso2.org/claims/testSessionClaim, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'http://wso2.org/claims/testSessionClaim' = null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] user.claims[testSessionClaim] (after set): not set
[DEBUG-SIDECAR] Host function 'isMemberOfAnyOfGroups' called with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'isMemberOfAnyOfGroups' called with 2 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@1710f692
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=authenticateduser, basePath=steps::1::subject
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=authenticateduser, basePath=steps::1::subject
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Converting arg[1]: (2)["admin", "Internal/everyone"]
[DEBUG-SIDECAR] Converted arg[1] to: Object[]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: Object[]
[DEBUG-SIDECAR] Invoking callback to IS for 'isMemberOfAnyOfGroups'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'isMemberOfAnyOfGroups' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'isMemberOfAnyOfGroups' with 2 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: isMemberOfAnyOfGroups, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: false
[pool-1-thread-1] ERROR org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Host function failed: argument type mismatch
[DEBUG-SIDECAR] ERROR: Host function failed: argument type mismatch
[pool-1-thread-1] ERROR org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Error calling host function: isMemberOfAnyOfGroups
java.io.IOException: Host function failed: argument type mismatch
	at org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient.invokeHostFunction(HostCallbackClient.java:142)
	at org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$HostFunctionStub.execute(JsEngineServiceImpl.java:813)
	at com.oracle.truffle.host.GuestToHostCodeCache$3.executeImpl(GuestToHostCodeCache.java:125)
	at com.oracle.truffle.host.GuestToHostRootNode.execute(GuestToHostRootNode.java:80)
	at com.oracle.truffle.api.impl.DefaultCallTarget.callDirectOrIndirect(DefaultCallTarget.java:85)
	at com.oracle.truffle.api.impl.DefaultRuntimeAccessor$DefaultRuntimeSupport.callInlined(DefaultRuntimeAccessor.java:177)
	at com.oracle.truffle.host.GuestToHostRootNode.guestToHostCall(GuestToHostRootNode.java:102)
	at com.oracle.truffle.host.HostProxy.execute(HostProxy.java:170)
	at com.oracle.truffle.host.HostProxyGen$InteropLibraryExports$Cached.executeNode_AndSpecialize(HostProxyGen.java:208)
	at com.oracle.truffle.host.HostProxyGen$InteropLibraryExports$Cached.execute(HostProxyGen.java:192)
	at com.oracle.truffle.api.interop.InteropLibraryGen$CachedDispatch.execute(InteropLibraryGen.java:7765)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode$ForeignExecuteNode.executeCall(JSFunctionCallNode.java:1494)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode.executeAndSpecialize(JSFunctionCallNode.java:306)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode.executeCall(JSFunctionCallNode.java:251)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode$CallNode.execute(JSFunctionCallNode.java:537)
	at com.oracle.truffle.js.nodes.access.JSWriteCurrentFrameSlotNodeGen.execute_generic4(JSWriteCurrentFrameSlotNodeGen.java:124)
	at com.oracle.truffle.js.nodes.access.JSWriteCurrentFrameSlotNodeGen.execute(JSWriteCurrentFrameSlotNodeGen.java:43)
	at com.oracle.truffle.js.nodes.JavaScriptNodeWrapper.executeVoid(JavaScriptNodeWrapper.java:240)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:80)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:55)
	at com.oracle.truffle.api.impl.DefaultBlockNode.executeVoid(DefaultBlockNode.java:73)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:70)
	at com.oracle.truffle.js.nodes.control.TryCatchNode.executeVoid(TryCatchNode.java:154)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:80)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:55)
	at com.oracle.truffle.api.impl.DefaultBlockNode.executeVoid(DefaultBlockNode.java:73)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:70)
	at com.oracle.truffle.js.nodes.control.VoidBlockNode.execute(VoidBlockNode.java:61)
	at com.oracle.truffle.js.nodes.control.IfNode.execute(IfNode.java:161)
	at com.oracle.truffle.js.nodes.JavaScriptNodeWrapper.executeVoid(JavaScriptNodeWrapper.java:240)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:80)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:55)
	at com.oracle.truffle.api.impl.DefaultBlockNode.executeVoid(DefaultBlockNode.java:73)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:70)
	at com.oracle.truffle.js.nodes.control.VoidBlockNode.execute(VoidBlockNode.java:61)
	at com.oracle.truffle.js.nodes.control.IfNode.execute(IfNode.java:161)
	at com.oracle.truffle.js.nodes.JavaScriptNodeWrapper.executeVoid(JavaScriptNodeWrapper.java:240)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:80)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:55)
	at com.oracle.truffle.api.impl.DefaultBlockNode.executeVoid(DefaultBlockNode.java:73)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:70)
	at com.oracle.truffle.js.nodes.control.VoidBlockNode.execute(VoidBlockNode.java:61)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeGeneric(AbstractBlockNode.java:85)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeGeneric(AbstractBlockNode.java:55)
	at com.oracle.truffle.api.impl.DefaultBlockNode.executeGeneric(DefaultBlockNode.java:65)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.execute(AbstractBlockNode.java:75)
	at com.oracle.truffle.js.nodes.function.FunctionBodyNode.execute(FunctionBodyNode.java:73)
	at com.oracle.truffle.js.nodes.function.FunctionRootNode.executeInRealm(FunctionRootNode.java:149)
	at com.oracle.truffle.js.runtime.JavaScriptRealmBoundaryRootNode.execute(JavaScriptRealmBoundaryRootNode.java:88)
	at com.oracle.truffle.api.impl.DefaultCallTarget.callDirectOrIndirect(DefaultCallTarget.java:85)
	at com.oracle.truffle.api.impl.DefaultDirectCallNode.call(DefaultDirectCallNode.java:59)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode$UnboundJSFunctionCacheNode.executeCall(JSFunctionCallNode.java:1314)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode.executeAndSpecialize(JSFunctionCallNode.java:306)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode.executeCall(JSFunctionCallNode.java:251)
	at com.oracle.truffle.js.nodes.interop.JSInteropExecuteNode.doDefault(JSInteropExecuteNode.java:68)
	at com.oracle.truffle.js.nodes.interop.JSInteropExecuteNodeGen.executeAndSpecialize(JSInteropExecuteNodeGen.java:62)
	at com.oracle.truffle.js.nodes.interop.JSInteropExecuteNodeGen.execute(JSInteropExecuteNodeGen.java:44)
	at com.oracle.truffle.js.runtime.builtins.JSFunctionObject.execute(JSFunctionObject.java:142)
	at com.oracle.truffle.js.runtime.builtins.JSFunctionObjectGen$InteropLibraryExports$Cached.executeNode_AndSpecialize(JSFunctionObjectGen.java:143)
	at com.oracle.truffle.js.runtime.builtins.JSFunctionObjectGen$InteropLibraryExports$Cached.execute(JSFunctionObjectGen.java:126)
	at com.oracle.truffle.api.interop.InteropLibraryGen$CachedDispatch.execute(InteropLibraryGen.java:7765)
	at com.oracle.truffle.polyglot.PolyglotValueDispatch$InteropValue$AbstractExecuteNode.executeShared(PolyglotValueDispatch.java:4268)
	at com.oracle.truffle.polyglot.PolyglotValueDispatch$InteropValue$ExecuteNode.executeImpl(PolyglotValueDispatch.java:4349)
	at com.oracle.truffle.polyglot.HostToGuestRootNode.execute(HostToGuestRootNode.java:124)
	at com.oracle.truffle.api.impl.DefaultCallTarget.callDirectOrIndirect(DefaultCallTarget.java:85)
	at com.oracle.truffle.api.impl.DefaultCallTarget.call(DefaultCallTarget.java:102)
	at com.oracle.truffle.api.impl.DefaultRuntimeAccessor$DefaultRuntimeSupport.callProfiled(DefaultRuntimeAccessor.java:182)
	at com.oracle.truffle.polyglot.PolyglotValueDispatch$InteropValue.execute(PolyglotValueDispatch.java:2396)
	at org.graalvm.polyglot.Value.execute(Value.java:880)
	at org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl.handleExecuteCallback(JsEngineServiceImpl.java:533)
	at org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport$JsEngineStreamingServiceImpl$1.handleExecuteCallback(GrpcStreamingServerTransport.java:256)
	at org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport$JsEngineStreamingServiceImpl$1.lambda$onNext$1(GrpcStreamingServerTransport.java:149)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:829)
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] isMemberOfAnyOfGroups ERROR: undefined
[DEBUG-SIDECAR] Host function 'hasAnyOfTheRolesV2' called with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'hasAnyOfTheRolesV2' called with 2 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@60373b64
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=context, basePath=
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=context, basePath=
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Converting arg[1]: (3)["Internal/admin", "admin", "Application/admin"]
[DEBUG-SIDECAR] Converted arg[1] to: Object[]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: Object[]
[DEBUG-SIDECAR] Invoking callback to IS for 'hasAnyOfTheRolesV2'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'hasAnyOfTheRolesV2' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'hasAnyOfTheRolesV2' with 2 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: hasAnyOfTheRolesV2, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: Boolean
[DEBUG-SIDECAR] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] hasAnyOfTheRolesV2(admin): true
[DEBUG-SIDECAR] Host function 'hasAnyOfTheRolesV2' called with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'hasAnyOfTheRolesV2' called with 2 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@60373b64
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=context, basePath=
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=context, basePath=
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Converting arg[1]: ["manager"]
[DEBUG-SIDECAR] Converted arg[1] to: Object[]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: Object[]
[DEBUG-SIDECAR] Invoking callback to IS for 'hasAnyOfTheRolesV2'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'hasAnyOfTheRolesV2' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'hasAnyOfTheRolesV2' with 2 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: hasAnyOfTheRolesV2, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: Boolean
[DEBUG-SIDECAR] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] hasAnyOfTheRolesV2(manager): false
[DEBUG-SIDECAR] Host function 'hasAnyOfTheRolesV2' called with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'hasAnyOfTheRolesV2' called with 2 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@60373b64
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=context, basePath=
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=context, basePath=
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Converting arg[1]: (2)["admin", "manager"]
[DEBUG-SIDECAR] Converted arg[1] to: Object[]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: Object[]
[DEBUG-SIDECAR] Invoking callback to IS for 'hasAnyOfTheRolesV2'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'hasAnyOfTheRolesV2' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'hasAnyOfTheRolesV2' with 2 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: hasAnyOfTheRolesV2, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: Boolean
[DEBUG-SIDECAR] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: Boolean
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] hasAnyOfTheRolesV2(admin,manager): true
[DEBUG-SIDECAR] Host function 'getUserSessions' called with 1 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'getUserSessions' called with 1 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@1710f692
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=authenticateduser, basePath=steps::1::subject
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=authenticateduser, basePath=steps::1::subject
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Invoking callback to IS for 'getUserSessions'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'getUserSessions' with 1 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'getUserSessions' with 1 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: getUserSessions, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: false
[pool-1-thread-1] ERROR org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Host function failed: argument type mismatch
[DEBUG-SIDECAR] ERROR: Host function failed: argument type mismatch
[pool-1-thread-1] ERROR org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Error calling host function: getUserSessions
java.io.IOException: Host function failed: argument type mismatch
	at org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient.invokeHostFunction(HostCallbackClient.java:142)
	at org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$HostFunctionStub.execute(JsEngineServiceImpl.java:813)
	at com.oracle.truffle.host.GuestToHostCodeCache$3.executeImpl(GuestToHostCodeCache.java:125)
	at com.oracle.truffle.host.GuestToHostRootNode.execute(GuestToHostRootNode.java:80)
	at com.oracle.truffle.api.impl.DefaultCallTarget.callDirectOrIndirect(DefaultCallTarget.java:85)
	at com.oracle.truffle.api.impl.DefaultRuntimeAccessor$DefaultRuntimeSupport.callInlined(DefaultRuntimeAccessor.java:177)
	at com.oracle.truffle.host.GuestToHostRootNode.guestToHostCall(GuestToHostRootNode.java:102)
	at com.oracle.truffle.host.HostProxy.execute(HostProxy.java:170)
	at com.oracle.truffle.host.HostProxyGen$InteropLibraryExports$Cached.executeNode_AndSpecialize(HostProxyGen.java:208)
	at com.oracle.truffle.host.HostProxyGen$InteropLibraryExports$Cached.execute(HostProxyGen.java:192)
	at com.oracle.truffle.api.interop.InteropLibraryGen$CachedDispatch.execute(InteropLibraryGen.java:7765)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode$ForeignExecuteNode.executeCall(JSFunctionCallNode.java:1494)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode.executeAndSpecialize(JSFunctionCallNode.java:306)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode.executeCall(JSFunctionCallNode.java:251)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode$CallNode.execute(JSFunctionCallNode.java:537)
	at com.oracle.truffle.js.nodes.access.JSWriteCurrentFrameSlotNodeGen.execute_generic4(JSWriteCurrentFrameSlotNodeGen.java:124)
	at com.oracle.truffle.js.nodes.access.JSWriteCurrentFrameSlotNodeGen.execute(JSWriteCurrentFrameSlotNodeGen.java:43)
	at com.oracle.truffle.js.nodes.JavaScriptNodeWrapper.executeVoid(JavaScriptNodeWrapper.java:240)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:80)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:55)
	at com.oracle.truffle.api.impl.DefaultBlockNode.executeVoid(DefaultBlockNode.java:73)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:70)
	at com.oracle.truffle.js.nodes.control.TryCatchNode.executeVoid(TryCatchNode.java:154)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:80)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:55)
	at com.oracle.truffle.api.impl.DefaultBlockNode.executeVoid(DefaultBlockNode.java:73)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:70)
	at com.oracle.truffle.js.nodes.control.VoidBlockNode.execute(VoidBlockNode.java:61)
	at com.oracle.truffle.js.nodes.control.IfNode.execute(IfNode.java:161)
	at com.oracle.truffle.js.nodes.JavaScriptNodeWrapper.executeVoid(JavaScriptNodeWrapper.java:240)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:80)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:55)
	at com.oracle.truffle.api.impl.DefaultBlockNode.executeVoid(DefaultBlockNode.java:73)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:70)
	at com.oracle.truffle.js.nodes.control.VoidBlockNode.execute(VoidBlockNode.java:61)
	at com.oracle.truffle.js.nodes.control.IfNode.execute(IfNode.java:161)
	at com.oracle.truffle.js.nodes.JavaScriptNodeWrapper.executeVoid(JavaScriptNodeWrapper.java:240)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:80)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:55)
	at com.oracle.truffle.api.impl.DefaultBlockNode.executeVoid(DefaultBlockNode.java:73)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:70)
	at com.oracle.truffle.js.nodes.control.VoidBlockNode.execute(VoidBlockNode.java:61)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeGeneric(AbstractBlockNode.java:85)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeGeneric(AbstractBlockNode.java:55)
	at com.oracle.truffle.api.impl.DefaultBlockNode.executeGeneric(DefaultBlockNode.java:65)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.execute(AbstractBlockNode.java:75)
	at com.oracle.truffle.js.nodes.function.FunctionBodyNode.execute(FunctionBodyNode.java:73)
	at com.oracle.truffle.js.nodes.function.FunctionRootNode.executeInRealm(FunctionRootNode.java:149)
	at com.oracle.truffle.js.runtime.JavaScriptRealmBoundaryRootNode.execute(JavaScriptRealmBoundaryRootNode.java:88)
	at com.oracle.truffle.api.impl.DefaultCallTarget.callDirectOrIndirect(DefaultCallTarget.java:85)
	at com.oracle.truffle.api.impl.DefaultDirectCallNode.call(DefaultDirectCallNode.java:59)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode$UnboundJSFunctionCacheNode.executeCall(JSFunctionCallNode.java:1314)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode.executeAndSpecialize(JSFunctionCallNode.java:306)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode.executeCall(JSFunctionCallNode.java:251)
	at com.oracle.truffle.js.nodes.interop.JSInteropExecuteNode.doDefault(JSInteropExecuteNode.java:68)
	at com.oracle.truffle.js.nodes.interop.JSInteropExecuteNodeGen.executeAndSpecialize(JSInteropExecuteNodeGen.java:62)
	at com.oracle.truffle.js.nodes.interop.JSInteropExecuteNodeGen.execute(JSInteropExecuteNodeGen.java:44)
	at com.oracle.truffle.js.runtime.builtins.JSFunctionObject.execute(JSFunctionObject.java:142)
	at com.oracle.truffle.js.runtime.builtins.JSFunctionObjectGen$InteropLibraryExports$Cached.executeNode_AndSpecialize(JSFunctionObjectGen.java:143)
	at com.oracle.truffle.js.runtime.builtins.JSFunctionObjectGen$InteropLibraryExports$Cached.execute(JSFunctionObjectGen.java:126)
	at com.oracle.truffle.api.interop.InteropLibraryGen$CachedDispatch.execute(InteropLibraryGen.java:7765)
	at com.oracle.truffle.polyglot.PolyglotValueDispatch$InteropValue$AbstractExecuteNode.executeShared(PolyglotValueDispatch.java:4268)
	at com.oracle.truffle.polyglot.PolyglotValueDispatch$InteropValue$ExecuteNode.executeImpl(PolyglotValueDispatch.java:4349)
	at com.oracle.truffle.polyglot.HostToGuestRootNode.execute(HostToGuestRootNode.java:124)
	at com.oracle.truffle.api.impl.DefaultCallTarget.callDirectOrIndirect(DefaultCallTarget.java:85)
	at com.oracle.truffle.api.impl.DefaultCallTarget.call(DefaultCallTarget.java:102)
	at com.oracle.truffle.api.impl.DefaultRuntimeAccessor$DefaultRuntimeSupport.callProfiled(DefaultRuntimeAccessor.java:182)
	at com.oracle.truffle.polyglot.PolyglotValueDispatch$InteropValue.execute(PolyglotValueDispatch.java:2396)
	at org.graalvm.polyglot.Value.execute(Value.java:880)
	at org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl.handleExecuteCallback(JsEngineServiceImpl.java:533)
	at org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport$JsEngineStreamingServiceImpl$1.handleExecuteCallback(GrpcStreamingServerTransport.java:256)
	at org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport$JsEngineStreamingServiceImpl$1.lambda$onNext$1(GrpcStreamingServerTransport.java:149)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:829)
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] getUserSessions ERROR: undefined
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
[DEBUG-SIDECAR] Converting arg[1]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@60373b64
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=context, basePath=
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=context, basePath=
[DEBUG-SIDECAR] Converted arg[1] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: HashMap
[DEBUG-SIDECAR] Invoking callback to IS for 'getUniqueUserWithClaimValues'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'getUniqueUserWithClaimValues' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'getUniqueUserWithClaimValues' with 2 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: getUniqueUserWithClaimValues, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
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
[DEBUG-SIDECAR] Converting arg[1]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@60373b64
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=context, basePath=
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=context, basePath=
[DEBUG-SIDECAR] Converted arg[1] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: HashMap
[DEBUG-SIDECAR] Invoking callback to IS for 'getUsersWithClaimValues'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'getUsersWithClaimValues' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'getUsersWithClaimValues' with 2 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: getUsersWithClaimValues, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: ArrayList
[DEBUG-SIDECAR] Callback returned: ArrayList
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: ArrayList
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] getUsersWithClaimValues count: 1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'response', full path: response
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: response, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'response', type: servletresponse, keys: 1
[DEBUG-SIDECAR] Host function 'setCookie' called with 4 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'setCookie' called with 4 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@7fd24693
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
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'setCookie' with 4 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: setCookie, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: false
[pool-1-thread-1] ERROR org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Host function failed: java.lang.reflect.InvocationTargetException
[DEBUG-SIDECAR] ERROR: Host function failed: java.lang.reflect.InvocationTargetException
[pool-1-thread-1] ERROR org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Error calling host function: setCookie
java.io.IOException: Host function failed: java.lang.reflect.InvocationTargetException
	at org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient.invokeHostFunction(HostCallbackClient.java:142)
	at org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$HostFunctionStub.execute(JsEngineServiceImpl.java:813)
	at com.oracle.truffle.host.GuestToHostCodeCache$3.executeImpl(GuestToHostCodeCache.java:125)
	at com.oracle.truffle.host.GuestToHostRootNode.execute(GuestToHostRootNode.java:80)
	at com.oracle.truffle.api.impl.DefaultCallTarget.callDirectOrIndirect(DefaultCallTarget.java:85)
	at com.oracle.truffle.api.impl.DefaultRuntimeAccessor$DefaultRuntimeSupport.callInlined(DefaultRuntimeAccessor.java:177)
	at com.oracle.truffle.host.GuestToHostRootNode.guestToHostCall(GuestToHostRootNode.java:102)
	at com.oracle.truffle.host.HostProxy.execute(HostProxy.java:170)
	at com.oracle.truffle.host.HostProxyGen$InteropLibraryExports$Cached.executeNode_AndSpecialize(HostProxyGen.java:208)
	at com.oracle.truffle.host.HostProxyGen$InteropLibraryExports$Cached.execute(HostProxyGen.java:192)
	at com.oracle.truffle.api.interop.InteropLibraryGen$CachedDispatch.execute(InteropLibraryGen.java:7765)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode$ForeignExecuteNode.executeCall(JSFunctionCallNode.java:1494)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode.executeAndSpecialize(JSFunctionCallNode.java:306)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode.executeCall(JSFunctionCallNode.java:251)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode$CallNode.execute(JSFunctionCallNode.java:537)
	at com.oracle.truffle.js.nodes.JavaScriptNodeWrapper.executeVoid(JavaScriptNodeWrapper.java:240)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:80)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:55)
	at com.oracle.truffle.api.impl.DefaultBlockNode.executeVoid(DefaultBlockNode.java:73)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:70)
	at com.oracle.truffle.js.nodes.control.TryCatchNode.executeVoid(TryCatchNode.java:154)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:80)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:55)
	at com.oracle.truffle.api.impl.DefaultBlockNode.executeVoid(DefaultBlockNode.java:73)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:70)
	at com.oracle.truffle.js.nodes.control.VoidBlockNode.execute(VoidBlockNode.java:61)
	at com.oracle.truffle.js.nodes.control.IfNode.execute(IfNode.java:161)
	at com.oracle.truffle.js.nodes.JavaScriptNodeWrapper.executeVoid(JavaScriptNodeWrapper.java:240)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:80)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:55)
	at com.oracle.truffle.api.impl.DefaultBlockNode.executeVoid(DefaultBlockNode.java:73)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:70)
	at com.oracle.truffle.js.nodes.control.VoidBlockNode.execute(VoidBlockNode.java:61)
	at com.oracle.truffle.js.nodes.control.IfNode.execute(IfNode.java:161)
	at com.oracle.truffle.js.nodes.JavaScriptNodeWrapper.executeVoid(JavaScriptNodeWrapper.java:240)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:80)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:55)
	at com.oracle.truffle.api.impl.DefaultBlockNode.executeVoid(DefaultBlockNode.java:73)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeVoid(AbstractBlockNode.java:70)
	at com.oracle.truffle.js.nodes.control.VoidBlockNode.execute(VoidBlockNode.java:61)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeGeneric(AbstractBlockNode.java:85)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.executeGeneric(AbstractBlockNode.java:55)
	at com.oracle.truffle.api.impl.DefaultBlockNode.executeGeneric(DefaultBlockNode.java:65)
	at com.oracle.truffle.js.nodes.control.AbstractBlockNode.execute(AbstractBlockNode.java:75)
	at com.oracle.truffle.js.nodes.function.FunctionBodyNode.execute(FunctionBodyNode.java:73)
	at com.oracle.truffle.js.nodes.function.FunctionRootNode.executeInRealm(FunctionRootNode.java:149)
	at com.oracle.truffle.js.runtime.JavaScriptRealmBoundaryRootNode.execute(JavaScriptRealmBoundaryRootNode.java:88)
	at com.oracle.truffle.api.impl.DefaultCallTarget.callDirectOrIndirect(DefaultCallTarget.java:85)
	at com.oracle.truffle.api.impl.DefaultDirectCallNode.call(DefaultDirectCallNode.java:59)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode$UnboundJSFunctionCacheNode.executeCall(JSFunctionCallNode.java:1314)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode.executeAndSpecialize(JSFunctionCallNode.java:306)
	at com.oracle.truffle.js.nodes.function.JSFunctionCallNode.executeCall(JSFunctionCallNode.java:251)
	at com.oracle.truffle.js.nodes.interop.JSInteropExecuteNode.doDefault(JSInteropExecuteNode.java:68)
	at com.oracle.truffle.js.nodes.interop.JSInteropExecuteNodeGen.executeAndSpecialize(JSInteropExecuteNodeGen.java:62)
	at com.oracle.truffle.js.nodes.interop.JSInteropExecuteNodeGen.execute(JSInteropExecuteNodeGen.java:44)
	at com.oracle.truffle.js.runtime.builtins.JSFunctionObject.execute(JSFunctionObject.java:142)
	at com.oracle.truffle.js.runtime.builtins.JSFunctionObjectGen$InteropLibraryExports$Cached.executeNode_AndSpecialize(JSFunctionObjectGen.java:143)
	at com.oracle.truffle.js.runtime.builtins.JSFunctionObjectGen$InteropLibraryExports$Cached.execute(JSFunctionObjectGen.java:126)
	at com.oracle.truffle.api.interop.InteropLibraryGen$CachedDispatch.execute(InteropLibraryGen.java:7765)
	at com.oracle.truffle.polyglot.PolyglotValueDispatch$InteropValue$AbstractExecuteNode.executeShared(PolyglotValueDispatch.java:4268)
	at com.oracle.truffle.polyglot.PolyglotValueDispatch$InteropValue$ExecuteNode.executeImpl(PolyglotValueDispatch.java:4349)
	at com.oracle.truffle.polyglot.HostToGuestRootNode.execute(HostToGuestRootNode.java:124)
	at com.oracle.truffle.api.impl.DefaultCallTarget.callDirectOrIndirect(DefaultCallTarget.java:85)
	at com.oracle.truffle.api.impl.DefaultCallTarget.call(DefaultCallTarget.java:102)
	at com.oracle.truffle.api.impl.DefaultRuntimeAccessor$DefaultRuntimeSupport.callProfiled(DefaultRuntimeAccessor.java:182)
	at com.oracle.truffle.polyglot.PolyglotValueDispatch$InteropValue.execute(PolyglotValueDispatch.java:2396)
	at org.graalvm.polyglot.Value.execute(Value.java:880)
	at org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl.handleExecuteCallback(JsEngineServiceImpl.java:533)
	at org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport$JsEngineStreamingServiceImpl$1.handleExecuteCallback(GrpcStreamingServerTransport.java:256)
	at org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport$JsEngineStreamingServiceImpl$1.lambda$onNext$1(GrpcStreamingServerTransport.java:149)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:829)
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] setCookie ERROR: undefined
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'request', full path: request
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'request', type: servletrequest, keys: 4
[DEBUG-SIDECAR] Host function 'getCookieValue' called with 3 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'getCookieValue' called with 3 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@675f2bba
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
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'getCookieValue' with 3 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: getCookieValue, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: null
[DEBUG-SIDECAR] Callback returned: null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] getCookieValue(testAdaptiveCookie): not found
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] dynamicFlag in step1 callback: 1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] dynamicFlag === 1: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'params', full path: request::params
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::params, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'params', type: parameters, keys: 4
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'sessionDataKey', full path: request::params::sessionDataKey
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::params::sessionDataKey, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'sessionDataKey' = ArrayList
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] (post-step) request.params.sessionDataKey[0]: 097b182e-8978-4b40-b95a-65a48fad2f19
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'username', full path: request::params::username
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: request::params::username, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'username' = ArrayList
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] (post-step) request.params.username[0]: admin1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'headers', full path: response::headers
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: response::headers, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'headers', type: headers, keys: 2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] putMember 'response::headers::X-Adaptive-Test' = script-executed
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] setContextProperty 'response::headers::X-Adaptive-Test' (type: headers), session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] setContextProperty: response::headers::X-Adaptive-Test, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertySetRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_SET_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_SET_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertySetResponse, success: false
[pool-1-thread-1] ERROR org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Failed to set 'X-Adaptive-Test': 
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
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'executeStep' with 2 args, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: executeStep, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 43be787c-75f6-4283-ad12-aeb5cd3808f2
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: HOST_FUNCTION_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received HostFunctionResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Returning result: null
[DEBUG-SIDECAR] Callback returned: null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Callback returned: null
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] ExecuteCallback completed in 264ms, success: true
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] New stream opened
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: EXECUTE_CALLBACK_REQUEST, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] handleExecuteCallback - session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Created streaming callback client
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] Created with external delegate for session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar] handleExecuteCallback (streaming) - session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
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
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'steps', type: steps, keys: none
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember '2', full path: steps::2
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::2, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for '2', type: step, keys: 4
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'idp', full path: steps::2::idp
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::2::idp, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'idp' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] step2.idp: LOCAL
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'authenticator', full path: steps::2::authenticator
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::2::authenticator, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'authenticator' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] step2.authenticator: totp
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'subject', full path: steps::2::subject
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::2::subject, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Creating nested proxy for 'subject', type: authenticateduser, keys: 8
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] getMember 'username', full path: steps::2::subject::username
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] getContextProperty: steps::2::subject::username, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent ContextPropertyRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: CONTEXT_PROPERTY_RESPONSE, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Delivering response type: CONTEXT_PROPERTY_RESPONSE
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Received ContextPropertyResponse, success: true
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [DynamicContextProxy] Deserialized 'username' = String
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] step2.subject.username: admin1
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [JS] [TEST] Step 2 callback: dynamicFlag = 2
[DEBUG-SIDECAR] Host function 'hasAnyOfTheRolesV2' called with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Host function 'hasAnyOfTheRolesV2' called with 2 args
[DEBUG-SIDECAR] Converting arg[0]: org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl$DynamicContextProxy@67bb72d6
[DEBUG-SIDECAR] Converting DynamicContextProxy to marker: type=context, basePath=
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converting DynamicContextProxy to marker: type=context, basePath=
[DEBUG-SIDECAR] Converted arg[0] to: HashMap
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[0] to: HashMap
[DEBUG-SIDECAR] Converting arg[1]: ["manager"]
[DEBUG-SIDECAR] Converted arg[1] to: Object[]
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Converted arg[1] to: Object[]
[DEBUG-SIDECAR] Invoking callback to IS for 'hasAnyOfTheRolesV2'
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.JsEngineServiceImpl - [Sidecar-Stub] Invoking callback to IS for 'hasAnyOfTheRolesV2' with 2 args
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.HostCallbackClient - [HostCallbackClient] invokeHostFunction 'hasAnyOfTheRolesV2' with 2 args, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] invokeHostFunction: hasAnyOfTheRolesV2, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.StreamingCallbackClient - [StreamingCallback] Sent HostFunctionRequest on stream
[grpc-default-executor-0] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] Received message type: HOST_FUNCTION_RESPONSE, session: 90ed9a2a-5ccf-451f-8377-cf35af038b46
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
[pool-1-thread-1] INFO org.wso2.carbon.identity.graaljs.sidecar.transport.GrpcStreamingServerTransport - [gRPC-Streaming-Server] ExecuteCallback completed in 64ms, success: true
"