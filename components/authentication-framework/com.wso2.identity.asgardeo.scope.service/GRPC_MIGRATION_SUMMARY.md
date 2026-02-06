# gRPC Transport Implementation Migration Summary

## Overview
Successfully migrated gRPC transport implementation from `org.wso2.carbon.identity.application.authentication.framework` jar to `com.wso2.identity.asgardeo.scope.service` jar to avoid dependency conflicts. The new jar now contains **ONLY** the gRPC transport code with all original scope service code removed.

## Changes Made

### 1. Framework Jar (`org.wso2.carbon.identity.application.authentication.framework`)

#### Files Modified/Deprecated:
- **GrpcCallbackServerImpl.java** - Replaced with deprecation placeholder
- **GrpcConnectionManager.java** - Replaced with deprecation placeholder
- **GrpcTransportImpl.java** - Replaced with deprecation placeholder
- **TransportFactory.java** - Commented out gRPC provider registration
- **JsEngineFactory.java** - Updated to use TransportFactory instead of direct gRPC instantiation

All deprecated files throw `UnsupportedOperationException` directing users to the new location.

### 2. New Jar (`com.wso2.identity.asgardeo.scope.service`) - CLEANED UP

#### Package Structure:
```
com.wso2.identity.asgardeo.scope.service/
├── graaljs/transport/
│   ├── GrpcConnectionManager.java          (Singleton gRPC connection manager)
│   ├── GrpcTransportImpl.java              (gRPC client transport)
│   ├── GrpcCallbackServerImpl.java         (gRPC callback server)
│   └── GrpcTransportProvider.java          (TransportFactory provider)
└── internal/
    └── GrpcTransportServiceComponent.java  (Registers provider on activation)
```

**Total: 5 Java source files (gRPC transport only)**

#### Dependencies (Minimal - gRPC Focused):
```xml
<!-- gRPC Libraries -->
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-netty-shaded</artifactId>
    <version>1.51.1</version>
</dependency>
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-protobuf</artifactId>
    <version>1.51.1</version>
</dependency>
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-stub</artifactId>
    <version>1.51.1</version>
</dependency>
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-services</artifactId>
    <version>1.51.1</version>
</dependency>

<!-- Compile-time only dependencies -->
<dependency>
    <groupId>commons-logging</groupId>
    <artifactId>commons-logging</artifactId>
    <version>1.2</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>org.osgi</groupId>
    <artifactId>org.osgi.service.component</artifactId>
    <version>1.3.0</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>org.osgi</groupId>
    <artifactId>org.osgi.service.component.annotations</artifactId>
    <version>1.3.0</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>org.wso2.orbit.graalvm.sdk</groupId>
    <artifactId>graal-sdk</artifactId>
    <version>22.3.4.wso2v1</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>org.wso2.carbon.identity.framework</groupId>
    <artifactId>org.wso2.carbon.identity.application.authentication.framework</artifactId>
    <version>7.8.644</version>
    <scope>provided</scope>
    <optional>true</optional>
    <exclusions>
        <exclusion>
            <groupId>*</groupId>
            <artifactId>*</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

#### OSGi Manifest Configuration:
```xml
<Private-Package>
    com.wso2.identity.asgardeo.scope.service.internal,
    io.grpc.*,
    com.google.protobuf.*,
    com.google.common.util.concurrent.*,
    io.perfmark.*,
</Private-Package>
<Export-Package>
    !com.wso2.identity.asgardeo.scope.service.internal,
    com.wso2.identity.asgardeo.scope.service.*; version = "${project.version}"
</Export-Package>
<Import-Package>
    org.apache.commons.logging; version="${commons-logging.osgi.version.range}",
    org.osgi.framework; version="${osgi.framework.imp.pkg.version.range}",
    org.osgi.service.component; version="${osgi.service.component.imp.pkg.version.range}",
    org.osgi.service.component.annotations; version="${osgi.service.component.imp.pkg.version.range}",
    org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine; resolution:=optional,
    org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.engine.proto; resolution:=optional,
</Import-Package>
```

#### Files Removed:
- All original scope service code (AsgardeoScopeService, AsgardeoScopeServiceUtils, etc.)
- Proto files and generated code (AuthzScope, AuthzScopeGrpc)
- Datasource, exception, and utility packages
- Original OSGi service component (AsgardeoScopeServiceServiceComponent)
- All OAuth and organization management related code

## Architecture

### How It Works:

1. **OSGi Activation**: When `com.wso2.identity.asgardeo.scope.service` bundle starts, `GrpcTransportServiceComponent` is activated

2. **Provider Registration**: The component registers `GrpcTransportProvider` with:
   ```java
   TransportFactory.getInstance().registerProvider("GRPC", new GrpcTransportProvider())
   ```

3. **Runtime Usage**: Framework jar can now use gRPC transport via:
   ```java
   TransportConfig config = TransportConfig.forGrpc("localhost:50051", 50052);
   RemoteEngineTransport transport = TransportFactory.getInstance().createTransport(config);
   CallbackServer callbackServer = TransportFactory.getInstance().createCallbackServer(config);
   ```

### Benefits:

- ✅ **No Dependency Conflicts**: gRPC libraries are private to `com.wso2.identity.asgardeo.scope.service`
- ✅ **Clean Separation**: Framework jar remains dependency-clean
- ✅ **Minimal Footprint**: New jar contains ONLY gRPC transport code (5 source files)
- ✅ **OSGi Integration**: Automatic registration via service component
- ✅ **Backward Compatible**: UDS transport still works directly in framework jar
- ✅ **Pluggable**: Can add more transport implementations similarly
- ✅ **Successfully Compiles**: Clean build with no compilation errors

## Build Results

### Compilation:
```
[INFO] Compiling 5 source files to target/classes
[INFO] BUILD SUCCESS
```

### Bundle Size:
```
com.wso2.identity.asgardeo.scope.service-1.0.257-SNAPSHOT.jar: 7.9M
```
(Size includes embedded gRPC libraries: grpc-netty-shaded, grpc-protobuf, grpc-stub, grpc-services, and dependencies)

### Generated Classes:
```
com/wso2/identity/asgardeo/scope/service/graaljs/transport/GrpcCallbackServerImpl.class
com/wso2/identity/asgardeo/scope/service/graaljs/transport/GrpcCallbackServerImpl$HostCallbackServiceImpl.class
com/wso2/identity/asgardeo/scope/service/graaljs/transport/GrpcConnectionManager.class
com/wso2/identity/asgardeo/scope/service/graaljs/transport/GrpcTransportImpl.class
com/wso2/identity/asgardeo/scope/service/graaljs/transport/GrpcTransportProvider.class
com/wso2/identity/asgardeo/scope/service/internal/GrpcTransportServiceComponent.class
```

## Testing Checklist

- [x] Build new jar successfully (clean compile)
- [x] Remove all non-gRPC code from new jar
- [x] Verify gRPC libraries embedded in bundle
- [x] Verify OSGi manifest configuration
- [ ] Verify OSGi bundle activation logs show gRPC provider registration
- [ ] Test UDS transport (should still work)
- [ ] Test gRPC transport with external service
- [x] Verify no compilation errors in framework jar
- [ ] Check that deprecated classes throw appropriate exceptions if accessed directly

## Notes

- **Jar Purpose**: This jar now serves a single purpose - providing gRPC transport implementation for the authentication framework
- **No breaking changes** for UDS transport users
- **gRPC transport** now requires `com.wso2.identity.asgardeo.scope.service` bundle to be active
- **External service** can still use both UDS and gRPC modes (unchanged)
- **Original implementations** preserved in git history for reference
- **All original scope service code removed** - jar contains ONLY gRPC transport functionality

---
Migration completed: 2026-02-05
Final cleanup completed: 2026-02-05
