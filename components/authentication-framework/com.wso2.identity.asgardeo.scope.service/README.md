# Asgardeo Scope Service

This service is utilized to retrieve metadata from the Authorization Service. 
- Specifically, during the consent flow, the scope metadata is fetched in order to display the scope associated with each API resource on the consent screen.

## Build

```
mvn clean install
```

## Generate gPRC stubs from the proto file

```
mvn generate-sources -P gen-proto
```

## Configurations

- Add the jar file to the `<IS_HOME>/repository/components/dropins` directory.
- Add the following configurations to the `deployment.toml` file.
- Authorization Service should be running in order to use this service. Refer to [Setup Authorization Service](https://github.com/wso2-enterprise/authz-management-service/blob/main/README.md)

```toml
[authz_scope_service]
enabled = true
endpoint = "<SCOPE_SERVICE_ENDPOINT>"
```

