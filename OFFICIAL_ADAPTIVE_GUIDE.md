Conditional authentication - API reference¶
WSO2 Identity Server provides a set of defined functions and objects to write your conditional authentication script. They are grouped as follows:

Core functions: These are the basic functions used in the script. These are used to identify the user who initiated the login flow, execute a step in the login flow, handle login failures, etc. Listed below are the core functions that can be used in conditional authentication scripts.

onLoginRequest()
executeStep()
fail()
sendError()
Utility functions: These utility functions are used for specific scenarios. For example, checking whether the login user belongs to a specific user group. Listed below are the utility functions that can be used in conditional authentication scripts.

isMemberOfAnyOfGroups()
hasAnyOfTheRolesV2()
assignUserRolesV2()
removeUserRolesV2()
setCookie()
getCookieValue()
prompt()
getUserSessions()
terminateUserSession()
sendEmail()
getValueFromDecodedAssertion()
getUniqueUserWithClaimValues()
getUsersWithClaimValues()
getAssociatedLocalUser()
doAssociationWithLocalUser()
removeAssociatedLocalUser()
httpGet()
httpPost()
resolveMultiAttributeLoginIdentifier()
updateUserPassword()
Object references: You can use objects to capture user behaviors and set attributes. For example, you can use the user and request objects and write the login conditions accordingly. Listed below are the object references that can be used in conditional authentication scripts.

context
step
user
request
response
session
application
userAgent
connectionMetadata
authConfig
Core functions¶
These are the basic functions that are required for defining the application login flow using an authentication script.

Initial login request¶
onLoginRequest()

This function is called when WSO2 Identity Server receives the initial login request. It includes the parameters given below.

Parameters

context	The authentication context, which contains the context information about the request.
Example


onLoginRequest(context)
Execute a step¶
executeStep()

This function is called to execute an authentication step in the login flow. You need to define your application's login flow before using this function.

This method accepts an object as a parameter and should include the details listed below.

<stepId>	(Mandatory) The step number in the login flow.
<options>	(Optional) A map that can configure step execution. Authentication option filtering is supported.
For more information, see the example on filtering connections in a step given below.
<eventCallbacks>	(optional) The object that contains the callback functions, which are to be called based on the result of the step execution.
Supported results are onSuccess and onFail which can have their own optional callbacks as anonymous functions. For these callbacks, the [context](#context) and [data](#data) parameters are passed.
If the flow reaches the eventCallback stage and additional steps need to be executed, those steps should be included within the callback itself.
The following sample template shows how the above categorization can be used in a script.


executeStep(<stepId>, 
{
  <options>:[
    //The objects of the option
  ]
},
{
  <eventCallback>: function()> {
    //eventCallback can be `onSuccess` or `onFail` 
    //Define what should be done
  }
});
Parameters
This section describes the options you can use to configure the executeStep() function, and the values you can use as local authenticators and federated IdPs in the function.

Options

You can use these options when executing an authentication step through the script. See the examples given below for details.

authenticationOptions	List the authentication methods that will be prompted for this step.
authenticationOptions.authenticator	The name of the local authenticator used.
authenticationOptions.idp	The name of the federated identity provider used.
authenticatorParams	Pass the configuration for the step authenticators or identity providers.
authenticatorParams.common	Specify the common configurations for both local authenticators and federated identity providers.
authenticatorParams.local.{authenticator-name}	The name of the local authenticator used in the application. The configurations passed here will be available for that specific authenticator.
authenticatorParams.local.{federated-identity-provider}	The name of the federated identity provider used in the application. The configurations passed here will be available for that federated identity provider.
Connections: Local authenticators and federated IdPs

When you want to access your configured connections in an authentication step, you can use the following values through the options explained above.

See the example on filtering connections in a step given below for details.

The local authenticators are represented by the authenticator parameter. The table shows the connection names (as displayed on the WSO2 Identity Server Console) and the corresponding authenticator name you can use in the scripts.

Connection Name	Authenticator
Email OTP	email-otp-authenticator
Identifier First	IdentifierExecutor
Magic Link	MagicLinkAuthenticator
Passkeys	FIDOAuthenticator
SMS OTP	sms-otp-authenticator
TOTP	totp
Username & Password	BasicAuthenticator
The external identity providers are represented by the idp parameter. The federated connection names are generated based on the name you assign to the connection at registration.

Example

If you add a federated google connection with the name bifrost google, the value you can use in your authentication scripts is bifrost_google.

Examples
Shown below are ways to define a login flow using the core functions.

Example 1: Use stepId

This example uses only the stepId.


executeStep(1)
Example 2: Use stepId and eventCallbacks

This example uses only the stepId and eventCallbacks.


executeStep(1, {
    onSuccess: function(context) {
        //Do something on success
    }
});
Example 3: Use all parameters

This example uses the stepId, options, and an empty eventCallbacks object. Different properties can be defined by the options object, such as authenticationOptions, authenticatorParams and stepOptions. However, you cannot write a script with only the stepId and options.

See the following examples:


 executeStep(1, {
    authenticationOptions:[{
       authenticator: 'totp'
    }]}, {}
 );

executeStep(1, {
    authenticatorParams: {
        local: {
           email-otp-authenticator: {
                enableRetryFromAuthenticator: 'true'
           }
        }
    }, {}
);

 executeStep(1, {
     stepOptions: {
         forceAuth: 'true'
     }, {}
 );
Example 4: Filter connections in a step

The authenticationOptions array filters out connections (local authenticators and federated identity providers) of a step based on a condition.

This can be achieved by specifying an array named authenticationOptions to the options map. You can have idp as an array item for federated connections and authenticator as an array item for local connections, as shown below.


  executeStep(1,{
    authenticationOptions:[{authenticator:'BasicAuthenticator'},{idp:'google'}]
    },{
        onSuccess: function (context) {
            // Do something on success
  };
Example 5: Force authentication with stepOptions

The stepOptions is an optional property that can be defined in the executeStep. This allows the addition of the forceAuth authentication option, which can prompt the authenticator in the steps to re-authenticate, even if it was already authenticated.


  executeStep(1,{
     stepOptions: {
        forceAuth: 'true'
    }, {}
  };
Fail the login flow¶
fail()

This function redirects the user to the redirect URI provided in the authorization request when the login flow fails.

This function takes a map as an optional parameter. When a map is provided as the parameter, the redirect URL will be appended with the following properties (which should be contained in the map). Otherwise, the default parameters are passed. All the properties passed in the map are also optional.

Parameters

errorCode	The error code to be appended in the redirect URL.
errorMessage	The error message to be appended in the redirect URL.
errorURI	The URI of a web page that includes additional information about the error.
Example


var parameterMap = {'errorCode': 'access_denied', 'errorMessage': 'login could not be completed', "errorURI":'http://www.example.com/error'};
if (!isAuthenticated) {
    fail(parameterMap);
}
Redirect to error code¶
sendError()

This function redirects the user to an error page. It includes the parameters listed below.

Parameters

url	The URL of the error page that the user is redirected to. If the value is null, the user is redirected by default to the retry.do error page.
Note that any relative URL is assumed to be relative to the host's root.
parameters	Key value map passed as parameters. These are converted to query parameters in the URL.
Example

It is recommended to use an i18n key to describe the error messages so that they can be internationalized easily on the error page.


var isAdmin = hasAnyOfTheRolesV2(context, ['admin']);
if (!isAdmin) {
    sendError('http://www.example.com/error',{'status':'000403','statusMsg':'You are not allowed to login to this app.', 'i18nkey':'not.allowed.error'});
}
Utility functions¶
The implementation of utility functions can be found in the WSO2 extensions code repository.

Check group membership¶
isMemberOfAnyOfGroups()

This function returns true if the specified user belongs to at least one of the given groups, and returns false if the user does not. It includes the parameters listed below.

Parameters


user	A user object representing the user details.
groups	A list of strings that contain the groups. Each string is a group name.
Example


var groups = ['admin', 'manager'];
var user = context.steps[1].subject;
var isMember = isMemberOfAnyOfGroups(user, groups);
if (isMember) {
    executeStep(2);
}
Has Any Of The Roles¶
hasAnyOfTheRolesV2()

This function checks if the given user has at least one of the given roles(v2). It returns true if the user has at least one of the given roles and returns false for any other case.

Parameters


context	The authentication context, which contains the context information about the request.
roleNames	A list of strings that contains roles that needs to be checked where each string is a role name.
Example


var rolesToStepUp = ['admin', 'manager'];
var hasRole = hasAnyOfTheRolesV2(context, rolesToStepUp);
if (hasRole) {
    executeStep(2);
}
Assign User Roles¶
assignUserRolesV2()

This function assigns each of the roles(v2) specified in the roleListToAssign parameter for a given user. It returns true if all the roles(v2) are successfully assigned and returns false if not.

Parameters


context	The authentication context, which contains the context information about the request.
roleListToAssign	A list of strings containing roles that are to be assigned where each string is a role name.
Example


executeStep(1, {
    onSuccess: function (context) {
        assignUserRolesV2(context, ['exampleRole1', 'exampleRole2']);
    }
});
Remove User Roles¶
removeUserRolesV2()

This function removes each of the roles(v2) specified in the roleListToAssign parameter to the given user. It returns true if all the roles(v2) are successfully removed and returns false if not.

Parameters


context	The authentication context, which contains the context information about the request.
roleListToRemove	A list of strings that contains roles that are to be removed where each string is a role name.
Example


executeStep(1, {
    onSuccess: function (context) {
        removeUserRolesV2(context, ['exampleRole1', 'exampleRole2']);
    }
});
Set cookie¶
setCookie(response, name, value, properties)

This function sets a new cookie. It includes the parameters listed below.

Parameters

response	The HTTP response.
name	Name of the cookie.
value	Value of the cookie.
properties	
A map that may contain optional attributes of the cookie with the two custom attributes given below.

sign: The default value is false. If it is set to true, the value will be signed.
encrypt: The default value is false. If it is set to true, the value will be encrypted.
Example

The size of the value has to be less than the RSA key pair length if 'encrypt' is enabled (set to true).


setCookie(context.response, "name", "test", {"max-age" : 4000,
                                            "path" : "/",
                                            "domain" : "localhost",
                                            "httpOnly" : true,
                                            "secure" : true,
                                            'sameSite': 'LAX',
                                            "version" : 1,
                                            "comment" : "some comments",
                                            "encrypt" : true,
                                            "sign" : true})
Get cookie value¶
getCookieValue(request, name, properties)

This function gets the plain-text cookie value for the cookie name if it is present. It includes the parameters listed below.

Parameters

request	HTTP authentication request.
name	Name of the cookie.
properties	
A map that may contain optional attributes of the cookie:

decrypt: The default value is false. If it is set to true, the value will be decrypted.
validateSignature: The default value is false. If it is set to true, the signature will be validated before returning a response.
Example


getCookieValue(context.request,"name", {"decrypt" : true,"validateSignature" : true })
Prompt for user input¶
prompt()

This function prompts user input. It includes the parameters listed below.

Parameters

templateId	Identifier of the template that needs to be prompted.
data	The data to send to the prompt.
eventHandlers	The callback event handlers.
Example


var onLoginRequest = function(context) {
  executeStep(1, {
      onSuccess: function (context) {
          var username = context.steps[1].subject.username;
          prompt("genericForm", {"username":username, "inputs":[{"id":"fname","label":"First Name"},{"id":"lname","label":"Last Name"}]}, {
            onSuccess : function(context) {
                var fname = context.request.params.fname[0];
                var lname = context.request.params.lname[0];
                Log.info(fname);
                Log.info(lname);
            }
          });
      }
  });
}
Get user sessions¶
getUserSessions()

This function returns a session object (i.e., all the active user sessions of the specified user or an empty array if there are no sessions). It includes the parameters listed below.

Parameters

user	This is a user object that represents the user details.
Example


var user = context.currentKnownSubject;
var sessions = getUserSessions(user);
for (var key in sessions) {
    Log.info("Session ID: " + sessions[key].id);
}
Terminate user session¶
terminateUserSession()

This function returns a session object (i.e., all the active user sessions of the specified user or an empty array if there are no sessions). It includes the parameters listed below.

Parameters

user	This is a user object that represents the user details.
sessionId	This is the sessionId string of the session that needs to be terminated.
Example


var user = context.currentKnownSubject;
var sessions = getUserSessions(user);
if (sessions.length > 0) {
    var result = terminateUserSession(user, sessions[0]);
    Log.info("Terminate Operation Successful?: " + result);
}
Send email¶
sendEmail()

This function sends an email to the specified user. It includes the parameters listed below.

Parameters

user	An object representing the user details.
templateId	Identifier of the email template. The email template specifies the body of the email that is sent out.
placeholderParameters	Used to replace any placeholders in the template.
Example


var user = context.steps[1].subject;
var firstName = user.localClaims['http://wso2.org/claims/givenname'];
sendEmail(user, 'myTemplate', {'firstName':firstName});
Get parameter value from JWT¶
getValueFromDecodedAssertion(jwt, parameterName, isParameterInPayload)

This function returns a string containing the parameter's value in a decoded Json Web Token (JWT). It includes the following parameters:

Parameters

jwt	The JWT to be decoded.
parameterName	The name of the parameter in the JWT for which the value should be retrieved.
isParameterInPayload	Indicates whether the parameter to be retrieved is in the JWT header or body.
Value should be true if the parameter to be retrieved is in the body.
Example


var state = getValueFromDecodedAssertion(context.request.params.request[0],"state",true);
Get unique user¶
getUniqueUserWithClaimValues(claimMap, context)

The utility function will search the underlying user stores and return a unique user with the claim values. The claim map consists of the claim and value.

Parameters

claimMap	A map that contains the claim URI and claim value.
context	The authentication context, which contains the context information about the request.
Example


var claimMap = {};
claimMap["http://wso2.org/claims/username"] = federatedUserName;
var mappedUsername = getUniqueUserWithClaimValues(claimMap, context);
Get multiple users¶
getUsersWithClaimValues(claimMap, context)

This function will search the underlying user stores and return a list of users with the expected claim values. The claim map consists of the claim and value.

Parameters

claimMap	A map that contains the claim URI and claim value.
context	The authentication context, which contains the context information about the request.
Example


var claimMap = {};
claimMap["http://wso2.org/claims/mobile"] = "1234567890";
var usersList = getUsersWithClaimValues(claimMap, context);
for (var key in usersList) {
    var userObj = userList[key];
    Log.info("Username: " + userObj.username):
}
Get associated user¶
getAssociatedLocalUser(federatedUser)

This function returns the local user associated with the federate username given as input.

Parameters

federatedUser	The federated user object.
Do association with local user¶
doAssociationWithLocalUser(federatedUser, localUsername, tenantDomain, userStoreDomain)

This function sets association to the local user with federated user. It includes the following parameters.

Parameters

federatedUser	The federated user object.
localUsername	The username of the local user to be associated.
tenantDomain	The tenant domain of the local user.
userStoreDomain	The user store domain of the local user.
Remove associated local user¶
removeAssociatedLocalUser(federatedUser)

This function removes the existing association of a federated user with the local user.

Parameters

federatedUser	The federated user object.
Example


var federatedUser = context.steps[1].subject;
removeAssociatedLocalUser(federatedUser);
HTTP GET¶
httpGet(url, headers, authConfig, eventHandlers)

The HTTP GET function enables sending HTTP GET requests to specified endpoints as part of the adaptive authentication scripts in WSO2 Identity Server. It's commonly used to interact with external systems or APIs to retrieve necessary data for authentication decisions.

Parameters

url	The URL of the endpoint to which the HTTP GET request should be sent.
headers	HTTP request headers to be included in the GET request (optional).
authConfig	An object containing the necessary authentication metadata to invoke the API. See AuthConfig for information.
eventHandlers	The object that contains the callback functions, which are to be called based on the result of the GET request.
Supported results are onSuccess and onFail, which can have their own optional callbacks as anonymous functions.
Example


    var authConfig = {
        type: "basic",
        properties: {
            username: "admin",
            password: "adminPassword"
        }
    };

    var onLoginRequest = function(context) {
        httpGet('https://example.com/resource', {
            "Accept": "application/json"
        }, authConfig, {
            onSuccess: function(context, data) {
                Log.info('httpGet call succeeded');
                context.selectedAcr = data.status;
                executeStep(1);
            },
            onFail: function(context, data) {
                Log.info('httpGet call failed');
                context.selectedAcr = 'FAILED';
                executeStep(2);
            }
        });
    }
HTTP POST¶
httpPost(url, body, headers, authConfig, eventHandlers)

The HTTP POST function enables sending HTTP POST requests to specified endpoints as part of the adaptive authentication scripts in WSO2 Identity Server. It's commonly used to interact with external systems or APIs to retrieve necessary data for authentication decisions.

Parameters

url	The URL of the endpoint to which the HTTP POST request should be sent.
body	HTTP request body to be included in the POST request.
headers	HTTP request headers to be included in the POST request (optional).
authConfig	Authentication configuration to be included in the GET request (optional).
eventHandlers	The object that contains the callback functions, which are to be called based on the result of the GET request.
Supported results are onSuccess and onFail, which can have their own optional callbacks as anonymous functions.
Example


var authConfig = {
    type: "clientcredential",
    properties: {
        consumerKey: "clientId",
        consumerSecret: "clientSecret",
        tokenEndpoint: "https://token-endpoint.com/token"
    }
};

var onLoginRequest = function(context) {
    httpPost('https://example.com/resource', {
        "email": "test@wso2.com"
    }, {
        "Authorization": "Bearer token",
        "Accept": "application/json"
    }, authConfig, {
        onSuccess: function(context, data) {
            Log.info('httpPost call succeeded');
            context.selectedAcr = data.status;
            executeStep(1);
        },
        onFail: function(context, data) {
            Log.info('httpPost call failed');
            context.selectedAcr = 'FAILED';
            executeStep(2);
        }
    });
}
Note

To restrict HTTP GET requests to certain domains, for httpGet and httpPost functions in adaptive authentication scripts, update the deployment.toml file as follows:


[authentication.adaptive]
http_function_allowed_domains = ["example.com", "api.example.org"]
To fine-tune connections initiated by WSO2 Identity Server to external services, you may add the following configurations to the deployment.toml file located in the <IS_HOME>/repository/conf/ directory

Property	Description	Default Value
http_connections.read_timeout	The maximum time (in milliseconds) the server will wait for a response from the external service.	3000 ms
http_connections.request_timeout	The maximum time (in milliseconds) the server will wait to obtain a connection from the connection pool.	1000 ms
http_connections.connection_timeout	The maximum time (in milliseconds) the server will wait to establish a connection to the external service.	3000 ms
http_connections.request_retry_count	Specifies the number of retry attempts for token requests initiated for authentication from client credentials.	2
Sample configuration is as follows:


[authentication.adaptive]
http_connections.read_timeout = 6000
http_connections.request_timeout = 3000
http_connections.connection_timeout = 3000
http_connections.request_retry_count = 2
Resolve multi attribute login identifier¶
resolveMultiAttributeLoginIdentifier(loginIdentifier, tenantDomain)

If alternative login identifiers are enabled, this function resolves the username from the provided login identifier.

Parameters

loginIdentifier	User provided login identifier.
organization	Organization name.
Example


var loginIdentifier = context.request.params.username[0];
var tenantDomain = context.tenantDomain;

var username = resolveMultiAttributeLoginIdentifier(loginIdentifier, tenantDomain);
Update user password¶
updateUserPassword(user, newPassword, eventHandlers, skipPasswordValidation)

This function updates the user password.

Parameters

user	The user object.
newPassword	New user password.
eventHandlers	Optional callback event handlers.
skipPasswordValidation	Optional parameter to skip password pattern validation.
Example


updateUserPassword(user, "newPassword");

updateUserPassword(user, "newPassword", {
  onSuccess: function(context) {
    Log.info("Password updated successfully.");
  },
  onFail: function(context) {
    Log.info("Password update failed.");
  }
});

updateUserPassword(user, "newPassword", {
  onSuccess: function(context) {
    Log.info("Password updated successfully.");
  },
  onFail: function(context) {
    Log.info("Password update failed.");
  }
}, true);
Object reference¶
Context¶
Contains the authentication context information. The information can be accessed as follows:

context.steps[n]	Access the authentication step information, where 'n' is the step number (1-based). See step for more information.
The step number is the one configured at the step configuration, not the actual order in which they get executed.

context.request	Access the HTTP authentication request information. See request for more information.
context.response	Access the HTTP response, which will be sent back to the client. See response for more information.
context.serviceProviderName	Get the application name.
Step¶
Contains the authentication step information. It may be a null or invalid step number.

step.subject	Contains the authenticated user's information from this step. It may be null if the step is not yet executed. See user for more information.
step.idp	Gives the name of the federated connection that is used to authenticate the user.
step.authenticator	Give the name of the authenticator that is used for authenticating te user. You can find the authenticator names from the connection names table.
User¶
user.uniqueId	The unique identifier of the user.
user.username	The user's username.
user.userStoreDomain	(Read/Write)
The user store domain of the local user.
user.localClaims["local_claim_url"]	(Read/Write)
User's attribute (claim) value for the given "local_claim_url". If the user is a federated user, this will be the value of the mapped remote claim from the identity provider.
user.claims["local_claim_url"]	(Read/Write)
Sets a temporary claim value for the session.
user.localClaims["local_claim_url"]	Updates the claim value in the user store as well. The user.claims["local_claim_url"] parameter is an alternative to setting a temporary claim.
user.remoteClaims["remote_claim_url"]	(Read/Write)
User's attribute (claim) as returned by the identity provider for the given remote_claim_url. Applicable only for federated users.
Request¶
request.headers["header_name"]	The request's header value for the given header name.
request.params.param_name[0]	The request's parameter value for the given parameter name by the param_name index (param_name is an array).
request.cookies["cookie_name"]	The request's cookie value for the given cookie name.
request.ip	The client IP address of the user who initiated the request. If there are any load balancers (eg. Nginx) with connection termination, the IP is retrieved from the headers set by the load balancer.
Response¶
response.headers["header_name"]	(Write) The response header value for the given header name.
Session¶
session.userAgent	The user agent object of the user session. See userAgent for more information.
session.ip	The session's IP address.
session.loginTime	The session's last login time.
session.lastAccessTime	The session's last accessed time.
session.id	The list of application objects in the session. See application for more information.
Application¶
application.subject	This is the subject of the application.
application.appName	This is the name of the application.
application.appId	This is the ID of the application.
User agent¶
userAgent.raw	This is the raw userAgent string.
userAgent.browser	This is the web browser property that is extracted from the raw userAgent string.
userAgent.platform	This is the operating system property that is extracted from the raw userAgent string.
userAgent.device	This is the device property that is extracted from the raw userAgent string.
You can securely store consumer keys and secrets as secrets in conditional authentication scripts and refer to them in your conditional authentication scripts using the secrets.{secret name} syntax. For example, to retrieve a secret value, you may use:


var consumerSecret = secrets.clientSecret;
For more information on adding secrets, refer to the Add a secret to the script section in the documentation.
AuthConfig¶
When using httpGet or httpPost functions in adaptive authentication scripts, the table summarizes each authentication type and its required properties:

Authentication Type	,Properties	,Description
basic	username, password	Uses user credentials.
apikey	apiKey, headerName	Uses an API key sent as a header.
clientcredential	consumerKey, consumerSecret, tokenEndpoint, scope (optional, a space separated list of scopes)	Uses client credentials to obtain an access token.
bearer	token	Uses a bearer token for authentication.
You can securely store sensitive values of properties like username, password, consumerKey, consumerSecret as secrets in conditional authentication scripts and refer to them in your conditional authentication scripts using the secrets.{secret name} syntax. For example, to retrieve a secret value, you can use:


var consumerSecret = secrets.clientSecret;
For more information on adding secrets, refer to the Add a secret to the script section in the documentation.

Data¶
data	The response data is a JSON object that contains the response data from the API call.

,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,


Add conditional authentication¶
With conditional authentication, the login flow in an application is dependent on the risk factors associated with the user's login request. This allows you to strengthen the authentication flow when the risk is higher. In WSO2 Identity Server, conditional authentication is configured using a script.

what is conditional authentication

Authentication script¶
The authentication script for configuring dynamic authentication flows in WSO2 Identity Server uses a functional language similar to Javascript. You can configure the script using the script editor in the WSO2 Identity Server Console. You can either use a template or write a custom script.

This scripting language supports a set of inbuilt functions and objects. A simple conditional authentication script will look like the following:


var onLoginRequest = function(context) {
    // Some possible initializations...
    executeStep(1);
        if (doStepUp(context) === true) { 
            executeStep(2);
        }
};

function doStepUp(context) {
    // A function that decides whether to enforce second step based on the request context.
    return true;
}
Note

Find out more about the scripting language in the Conditional Authentication API Reference.

Script templates¶
The script editor in WSO2 Identity Server comes with a set of predefined templates to get you started with some of the most common conditional authentication scenarios. These scripts contain inline comments explaining the conditions that are applied.

conditional-auth-templates-view

The available templates are categorized as follows:

User
Request
Environment
Analytics
IdP
The pre-defined templates are listed below.

Template	Description
Role-Based	This login flow prompts two-factor authentication (2FA) for users who are assigned to any of the given set of roles.
User-Age-Based	This configures a login flow where users can log in only if their age is over the configured value. The user's age is calculated using the date of birth attribute.
User Store-Based	This login flow prompts two-factor authentication (2FA) for users who are from to any of the given set of user stores.
Login Attempt-Based	This login flow prompts two-factor authentication (2FA) for users who are from to any of the given set of user stores.
Group-Based	This login flow prompts two-factor authentication (2FA) for users who belong to any of the given set of groups.
Concurrent Session-Based	This login flow prompts adaptive authentication for users who have exceeded the maximum number of allowed sessions.
New-Device-Based	This login flow sends an email notification and/or prompts two-factor authentication for users who are logged in from a previously unused device.
IP-Based	This login flow prompts two-factor authentication for users who log in from outside the given IP range.
Passkey-Progressive-Enrollment-Based	This login flow permits users to seamlessly enroll their passkey on-the-fly, when Passkey is designated as the first authentication factor.
If required, you can also use the script editor to introduce new functions and fields to an authentication script based on your requirement. See the instructions on writing a custom authentication script.

,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,



Configure conditional authentication¶
Given below are the high-level steps for enabling conditional authentication in your applications.

Note

ECMAScript Compliance: Adaptive scripts currently comply with ECMAScript 2022 (ES13).

Limitations: Adaptive scripts do not support loops, Log.warn logs, or stringifying Java objects using JSON.stringify().

Prerequisites¶
Register your application on the WSO2 Identity Server Console.

Enable conditional authentication¶
Follow the steps given below.

On the WSO2 Identity Server Console, click Applications.
Select the application for which you wish to apply a conditional login flow and go to its Login Flow tab.
Click Start with default configuration to define the login flow starting with the username and password login.
Turn on Conditional Authentication by switching the toggle on. You can define your conditional authentication script in the editor.

Enable conditional auth

Warning

As a security measure, WSO2 Identity Server does not allow the usage of two consecutive periods (..) in authentication scripts.

Add conditional authentication script¶
There are two ways to add a conditional authentication script:

Use a predefined template.
Write a new conditional auth script.
Before you proceed

When working with conditional authentication scripts, never log secrets or sensitive information within your authentication flows.

Add a secret to the script¶
Secrets securely store values associated with external APIs. These secret values are used in conditional authentication scripts when WSO2 Identity Server is required to interact with an external API (service endpoint) during the authentication process.

You can securely store these secret values on the WSO2 Identity Server Console and retrieve them whenever required for conditional authentication script.

Create a new secret¶
To add a new secret:

On the WSO2 Identity Server Console, go to Applications.

Select your application and go to the Login Flow tab .

Add a new secret from your preferred editor:


Classic Editor
Visual Editor
Enable conditional authentication and click the key icon above the script to create a new secret.

Add secret to script


Click Create new secret from the drop-down menu.

Enter the following details:

Create secret in WSO2 Identity Server

Parameter	Description
Secret Name	A meaningful name for the secret. This name is not changeable and will be used in the script to reference the secret.
Secret Value	You can enter the secret value that is 1 to 2048 bits in length.
Secret Description	A short description for the secret.
Click Finish to complete the creation.

Use secret in the script¶
You may refer to the previously added secrets in your conditional authentication scripts using the secrets.{secret name} syntax. For example, to retrieve a secret value, you may use:


var secretValue = secrets.secretName;
This allows you to securely access secret values within your authentication scripts, enhancing the security and flexibility of your authentication process.

Delete an existing secret¶
To delete an existing secret:

On the WSO2 Identity Server Console, go to Applications.

Select your application and go to the Login Flow tab .

Delete the secret by using your preferred editor:


Classic Editor
Visual Editor
Enable conditional authentication and click the key icon above the script to delete a secret.

Add secret to script


Click the trash icon next to the secret you wish to delete.

Select the checkbox and confirm your action.



,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

Add user age-based access control¶
To control access to your application based on the user's age, you can apply the User Age-Based conditional authentication template. The age of the user is calculated using the date of birth attribute in the user's profile. Users are redirected to an error page if the date of birth is not specified in the user profile or if the user's age is below the minimum age configured in the template.

Scenario¶
Consider a scenario where users who are younger than 18 years should be prevented from signing in to an application and redirected to an error message.

Age based access control

Prerequisites¶
You need to register an application with WSO2 Identity Server. You can register your own application or use one of the sample applications provided.

Go to the user's profile and update the date of birth so that the current age is below 18 years. For instructions, see Manage user profiles.

Configure the login flow¶
On the WSO2 Identity Server Console, click Applications.

Select the relevant application and go to its Login Flow tab.

Add user-age-based access control as follows:

Go to Predefined Flows > Conditional Login Flows.

Click Access Control > User-Age-Based > ADD.

Click Confirm to replace any existing script with the selected predefined script.

Update the following parameter in the script.

Parameter	Description
ageLimit	
Minimum age required for the user to log in to the application.

For this example scenario, enter 18 as the value.
errorPage	The error page to which users are redirected if the age limit is below age limit.
The default error page is used if this parameter is not configured.
errorPageParameters	Parameters to be passed to the error page. This information will display on the error page.
Click Update to confirm.

How it works¶
Shown below is the user age-based conditional authentication template.


// This script will only allow login to application if the user's age is over configured value
// The user will be redirected to an error page if the date of birth is not present or user is below configured value

var ageLimit = 18;

// Error page to redirect unauthorized users,
// can be either an absolute url or relative url to server root, or empty/null
// null/empty value will redirect to the default error page
var errorPage = '';

// Additional query params to be added to the above url.
// Hint: Use i18n keys for error messages
var errorPageParameters = {
    'status': 'Unauthorized',
    'statusMsg': 'You need to be over ' + ageLimit + ' years to login to this application.'
};

// Date of birth attribute at the client side
var dateOfBirthClaim = 'http://wso2.org/claims/dob';

// The validator function for DOB. Default validation check if the DOB is in YYYY-MM-dd format
var validateDOB = function (dob) {
    return dob.match(/^(\d{4})-(\d{2})-(\d{2})$/);
};

var onLoginRequest = function(context) {
    executeStep(1, {
        onSuccess: function (context) {
            var underAge = true;
            // Extracting user store domain of authenticated subject from the first step
            var dob = context.currentKnownSubject.localClaims[dateOfBirthClaim];
            Log.debug('DOB of user ' + context.currentKnownSubject.uniqueId + ' is : ' + dob);
            if (dob && validateDOB(dob)) {
                var birthDate = new Date(dob);
                if (getAge(birthDate) >= ageLimit) {
                    underAge = false;
                }
            }
            if (underAge === true) {
                Log.debug('User ' + context.currentKnownSubject.uniqueId + ' is under aged. Hence denied to login.');
                sendError(errorPage, errorPageParameters);
            }
        }
    });
};

var getAge = function(birthDate) {
    var today = new Date();
    var age = today.getFullYear() - birthDate.getFullYear();
    var m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    return age;
};
Let's look at how this script works.

The validateDOB function validates whether the provided date of birth is correct.
The getAge function calculates the age based on the configured birth date.
When step 1 of the authentication flow is complete, the onLoginRequest function checks whether the age of the user is above the configured age limit.
If the age is below the configured limit, the user is directed to the configured error page.
Note

Find out more about the scripting language in the Conditional Authentication API Reference.

Try it out¶
Follow the steps given below.

Access the application URL.
Try to log in as a user who is above 18 years of age. This user will successfully log in to the application.
Log out of the application.
Log in again with a user who is below 18 years. The user will see the following error.


,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,


Add concurrent sessions based access control¶
You can effectively control the number of concurrent user sessions for an application by implementing the Session-Based conditional authentication template. Users are redirected to a dedicated page where they can manage their existing sessions or cancel the current authentication request if they exceed the number of allowed concurrent sessions.

Scenario¶
Consider a scenario with two roles, admin and manager. Users belonging to these roles are limited to having only one active session at a time. If they try to initiate a second session, they will be presented with a list of their current sessions and offered with the following two options:

Terminate any of their existing sessions.
Cancel their current authentication attempt.
Prerequisites¶
You need to register an application with WSO2 Identity Server. You can register your own application or use one of the sample applications provided.

Create two roles named admin and manager in application audience selecting the created application or create roles in organization audience and associate to the created application.

Managing roles
Assign user accounts to the created roles. For instructions, see the following:

Managing users
Assigning users to roles
Configure the login flow¶
To configure the login flow with concurrent session-based access control:

On the WSO2 Identity Server Console, click Applications.
Select the relevant application and go to its Login Flow tab.
Add concurrent session-based access control as follows.

Go to Predefined Flows > Conditional Login Flows.

Click Access Control > Session-Based > ADD.

Click Confirm to replace any existing script with the selected predefined script.

Update the following parameter in the script.

Parameter	Description
rolesToStepUp	Comma-separated list of user roles. Two-factor authentication should apply
to users from these roles.
For this example scenario, enter admin and manager.
maxSessionCount	
The number of allowed sessions for the user

For this example scenario, enter 1 as we allow only one concurrent active sessions per user.
MaxSessionCount	
The number of allowed sessions for the user

For this example scenario, enter 1 as we allow only one concurrent active sessions per user.
Use the same value assigned for MaxSessionCount.
Click Update to confirm.

How it works¶
Shown below is the concurrent session-based conditional authentication template.


// This script will prompt concurrent session handling
// to one of the given roles
// If the user has any of the below roles, concurrent session handling will be prompted
// and it will either kill sessions or abort login based on number of active concurrent user sessions
var rolesToStepUp = ['admin', 'manager'];
var maxSessionCount = 1;

var onLoginRequest = function(context) {
   executeStep(1, {
       onSuccess: function (context) {
       // Extracting authenticated subject from the first step
           var user = context.currentKnownSubject;
           // Checking if the user is assigned to one of the given roles
           var hasRole = hasAnyOfTheRolesV2(context, rolesToStepUp);

           if (hasRole) {
               Log.info(user.username + ' Has one of Roles: ' + rolesToStepUp.toString());
                   executeStep(2, {
                       authenticatorParams: {
                            local: {
                                 SessionExecutor: {
                                      MaxSessionCount: '1'
                                 }
                            }
                       }
                   }, {});
           }
       }
   });
};
Let's look at how this script works.

When step 1 of the authentication flow is complete, the onLoginRequest function retrieves the authenticating user from the context.
The function verifies whether the authenticating user is a member of the roles listed in rolesToStepUp.
If the authenticating user is assigned to one or more roles in rolesToStepUp, authentication step 2 is prompted with maxSessionCount being passed as a parameter to the Active Sessions Limit handler.
Note

Find out more about the scripting language in the Conditional Authentication API Reference.

Try it out¶
Follow the steps given below.

Access the application URL.

Log in to the application as a user belonging to the admin or manager.

Attempt to log in as the same user from a second browser.

Now, the user will receive a prompt, allowing them to either terminate one of their existing sessions or deny the authentication request for the second session.



,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,


Add adaptive MFA¶
Use authentication scripts to enforce adaptive MFA in the login flow of your applications.

User role based adaptive MFA
User store based adaptive MFA
Login attempts based adaptive MFA
User group based adaptive MFA.
User device based adaptive MFA.
IP address based adaptive MFA.


Add MFA based on user roles¶
You can enable a more secure login flow for users that belong to specific roles associated with the application by applying the Role-Based conditional authentication template for Adaptive MFA. This template enables two-factor authentication with TOTP or passkeys for users who belong to the user role you specify.

Scenario¶
Consider a scenario with two roles, admin and manager associated with an application. For users assigned to these roles, the login flow in the application should be stepped up with TOTP or passkeys as follows:

Username and password
TOTP or Passkey
Role based adaptive authentication

Prerequisites¶
You need to register an application with WSO2 Identity Server. You can register your own application or use one of the sample applications provided.

Create two roles named admin and manager in application audience selecting the created application or create roles in organization audience and associate to the created application.

Managing roles
Assign user accounts to the created roles. For instructions, see the following:

Managing users
Assigning users to roles
Configure the login flow¶
To enable conditional authentication:

On the WSO2 Identity Server Console, click Applications.

Select the relevant application and go to its Login Flow tab.

Add role-based adaptive MFA as follows:

Go to Predefined Flows > Conditional Login Flows.

Click Adaptive MFA > Role-Based > Add to add the role-based adaptive MFA script.

Role-based adaptive MFA with visual editor

Click Confirm to replace any existing script with the selected predefined script.

Verify that the login flow is now updated with the following two authentication steps:

Step 1: Username and Password
Step 2: TOTP and Passkey
Update the following parameter in the script.

Parameter	Description
rolesToStepUp	
Comma-separated list of user roles. Two-factor authentication should apply to users from these roles.

For this example scenario, enter admin and manager.
Click Update to confirm.

How it works¶
Shown below is the script of the role-based conditional authentication template.


// This script will step up authentication for any user belonging
// to one of the given roles
// If the user has any of the below roles, authentication will be stepped up
var rolesToStepUp = ['admin', 'manager'];

var onLoginRequest = function(context) {
    executeStep(1, {
        onSuccess: function(context) {
            // Extracting authenticated subject from the first step
            var user = context.currentKnownSubject;
            // Checking if the user is assigned to one of the given roles
            var hasRole = hasAnyOfTheRolesV2(context, rolesToStepUp);
            if (hasRole) {
                Log.info(user.username + ' Has one of Roles: ' + rolesToStepUp.toString());
                executeStep(2);
            }
        }
    });
};
Let's look at how this script works.

When step 1 of the authentication flow is complete, the onLoginRequest function retrieves the user from the context.
The user and the configured list of roles are passed to the following function: hasAnyOfTheRolesV2.
This function (which is available in WSO2 Identity Server by default) verifies whether the given user belongs to any of the listed roles associated to the login application.
If the user belongs to any of the configured roles, authentication step 2 (TOTP or Passkey) is prompted.
Note

Find out more about the scripting language in the Conditional Authentication API Reference.

Try it out¶
Follow the steps given below.

Access the application URL.
Try to log in with a user who does not belong to any of the configured roles (manager or admin). You will successfully sign in to the application.
Log out of the application.
Log in with a user who belongs to the admin or manager role.

The user will be prompted to select the step-up method, and the sign-in flow will be stepped up according to the user's preference.


,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,


Configure ACR-based adaptive authentication¶
Authentication Context Class Reference (ACR) is an optional parameter that is used in SAML and OpenID Connect (OIDC) requests. This parameter enables applications to send additional information to the identity provider regarding the required level of assurance. Developers can then define authentication steps required for a given ACR value using an adaptive authentication script in the WSO2 Identity Server.

Scenario¶
Let's look at a scenario where you want to dynamically adjust the authentication flow based on the authentication context value received in the authentication request.

For example, imagine a financial institution that wants to send ACR values in the authentication request based on activities.

For a high value transaction, the application may request for an ACR value of ACR2 from the identity provider which generally include a stronger authentication mechanism.

For a balance inquiry, the application may request for an ACR value of ACR1 from the identity provider which generally include a basic authentication mechanism.

The guides below explain how you can leverage an adaptive authentication script in WSO2 Identity Server to achieve this.

Prerequisites¶
You need to register an application with WSO2 Identity Server. You can register your own application or use the playground2 sample application to test ACR-based adaptive authentication.

Request ACR from applications¶
Follow the sections below to learn how you can request ACR parameters from either an OIDC or a SAML application.

Some commonly accepted ACR values
From an OIDC application¶
acr_values is an optional parameter that can be included in an OIDC authentication request. This parameter may include the necessary context class URIs. If there are multiple ACR values they can be included separated by commas.

Shown below is an example authentication request including the optional acr_values parameter.


Format
Sample request

https://localhost:9443/oauth2/authorize?
scope={scope}
&acr_values={acr_value1} {acr_value2}
&response_type={response_type}
&redirect_uri={redirect_uri}
&client_id={client_id}

From a SAML application¶
To receive ACR values from a SAML application, developers can add the following to the SAML SSO request when initiating the SAML SSO flow.


<samlp:RequestedAuthnContext Comparison="exact">
    <saml:AuthnContextClassRef>urn:federation:authentication:windows</saml:AuthnContextClassRef>
    <saml:AuthnContextClassRef>pwd</saml:AuthnContextClassRef>
    <saml:AuthnContextClassRef>LOA2</saml:AuthnContextClassRef>
</samlp:RequestedAuthnContext>
Warning

WSO2 Identity Server authentication script does not support the Comparison attribute in the RequestedAuthnContext element due to the following reasons:

OAuth2 does not support similar functionality.

WSO2 Identity Server provides more flexibility to enforce ACR through adaptive authentication scripts. A client sending an exact, minimum, maximum, or better comparison may not be as effective.

Configure the login flow¶
The steps below explain how you can set up an ACR-based conditional authentication script to define the logic for the received ACR values.

On the WSO2 Identity Server Console, click Applications.

Select the relevant application and go to its Login Flow tab.

Add ACR-based adaptive MFA as follows:

Go to Predefined Flows > Conditional Login Flows.

Click Adaptive MFA > ACR-Based > ADD to add the ACR-based adaptive MFA script.

Role-based adaptive MFA with visual editor

Click Confirm on the prompt to replace any existing script with the current script.

Verify that the login flow is now updated. For the sample application, we'll have the following three authentication steps:

Step 1: Username and Password
Step 2: TOTP authenticator
Step 3: Passkey authenticator
Click Update to confirm.

How it works¶
Shown below is the default script for ACR-based conditional authentication.


// Define conditional authentication by passing one or many Authentication Context Class References 
// as comma separated values.

// Specify the ordered list of ACR here.
var supportedAcrValues = ['acr1', 'acr2', 'acr3'];

var onLoginRequest = function(context) {
    var selectedAcr = selectAcrFrom(context, supportedAcrValues);
    Log.info('--------------- ACR selected: ' + selectedAcr);
    context.selectedAcr = selectedAcr;
    switch (selectedAcr) {
        case supportedAcrValues[0] :
            executeStep(1);
            break;
        case supportedAcrValues[1] :
            executeStep(1);
            executeStep(2);
            break;
        case supportedAcrValues[2] :
            executeStep(1);
            executeStep(3);
            break;
        default :
            executeStep(1);
            executeStep(2);
            executeStep(3);
    }
};
Access ACR Values from the authentication script

The authentiaction script is protocol-agnostic, i.e. it works for both OIDC and SAML SSO requests.

For OIDC requests the acr_values parameter is available as context.requestedAcr.

For SAML SSO requests, the list of samlp:AuthnContextClassRef is available as context.requestedAcr.

You can also assign any string value that is returned in the authentication response, using context.selectedAcr.

The following example explains this.


var acr_values = context.requestedAcr; //Assigns the list of ACR values returned from the application to an array.
context.selectedAcr="LOA1";  //Sets the ACR value to be returned in the response.
Let's look at how this script works.

The ordered list, supportedAcrValues, contains comma separated ACR values accepted from the application.
The selectAcrFrom function dynamically and adaptively determines the strongest ACR value from the received and configured ACR values.
context.selectedAcr sets the selected ACR value to be returned in the authentication response.
Based on the selected ACR value, authentication level is determined in the switch cases. In this case,
acr1 - step 1 (basic authentication)
acr2 - step 1 and 2 (basic authentication and TOTP)
acr3 - step 1 and 3 (basic authentication and Passkeys)
Note

Find out more about the scripting language in the Conditional Authentication API Reference.

Try it out¶
Follow the steps given below to try out ACR-based adaptive authentication with the playground2 sample application.

Access the application URL: http://wso2is.local:8080/playground2/index.jsp

Click Import Photos.

Enter the client ID of the OAuth service provider application you registered above and enter acr2 as the Authentication Context Class value.

Authentication context class

You are now prompted for basic authentication followed by TOTP authentication which corresponds to the received acr2 ACR value.

TOTP authenticator

Enter the TOTP and click Continue.ACR-based login successful

Click Get Access Token and proceed to obtain the access token.

ACR-based access token

Note

Authentication Method Reference (AMR) value found in the access token provides information about the authentication methods that are used to assert the authenticity of the user.

The AMR values for the relevant request are BasicAuthenticator and totp which were the methods used for authenticaion.

Logout from the application and try this flow with different ACR values.

Tip

Try this flow using the ACR value acr3 which will then prompt the user for steps 1 and 3 (basic authentication and passkeys).



,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,


Add MFA based on user store¶
You can enable a more secure login flow for users that belong to specific user stores by applying the User Store-Based conditional authentication template for Adaptive MFA. This template enables two-factor authentication with TOTP for users who belong to the user store you specify.

Scenario¶
Consider a scenario with two user stores, EMPLOYEES and CONTRACTORS. For users assigned to these user stores, the login flow in applications should be stepped up with TOTP as follows:

Username and password
TOTP
User store-based adaptive authentication

Prerequisites¶
You need to register an application with WSO2 Identity Server. You can register your own application or use one of the sample applications provided.

Create two user stores named EMPLOYEES and CONTRACTORS and add user accounts to them. For instructions, see the following:

Managing user stores
Managing users
Configure the login flow¶
To enable conditional authentication:

On the WSO2 Identity Server Console, click Applications.

Select the relevant application and go to its Login Flow tab.

Add user store based adaptive MFA as follows:

Go to Predefined Flows > Conditional Login Flows.

Click Adaptive MFA > User Store-Based > ADD to add the user store based adaptive MFA script.

User store-based adaptive MFA with visual editor

Click Confirm on the prompt to replace any existing script with the current script.

Verify that the login flow is now updated with the following two authentication steps:

Step 1: Username and Password
Step 2: TOTP
Update the following parameter in the script.

Parameter	Description
userStoresToStepUp	
Comma-separated list of user stores. Two-factor authentication should apply to users from the
specified user stores. For this example scenario, enter EMPLOYEES and CONTRACTORS.

Click Update to confirm.

How it works¶
Shown below is the script of the user store-based conditional authentication template.


// This script will prompt 2FA to the app only for a selected set of user stores.
// If the user is in one of the following user stores, user will be prompted 2FA
var userStoresToStepUp = ['EMPLOYEES', 'CONTRACTORS'];

var onLoginRequest = function(context) {
    executeStep(1, {
        onSuccess: function (context) {
            // Extracting user store domain of authenticated subject from the first step
            var userStoreDomain = context.currentKnownSubject.userStoreDomain;
            // Checking if the user is from whitelisted tenant domain
            if (userStoresToStepUp.indexOf(userStoreDomain) >= 0) {
                executeStep(2);
            }
        }
    });
};
Let's look at how this script works.

When step 1 of the authentication flow is complete, the onLoginRequest function retrieves the user from the context.
The userStoreDomain is extracted from the authentication information provided in step one.
Check if the extracted userStoreDomain is in the values specified for the variable userStoresToStepUp.
If the user belongs to any of the configured user stores, authentication step 2 (TOTP) is prompted.
Note

Find out more about the scripting language in the Conditional Authentication API Reference.

Try it out¶
Follow the steps given below.

Access the application URL.
Try to log in with a user who does not belong to any of the configured user stores (EMPLOYEES or CONTRACTORS). You will successfully sign in to the application.
Log out of the application.
Log in with a user who belongs to the EMPLOYEES or CONTRACTORS user store. TOTP authentication is prompted.


,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,


Add MFA based on login attempts¶
You can enable a more secure login flow for users based on the number of invalid login attempts by applying the Login-Attempt-Based conditional authentication template for Adaptive MFA. This template enables two-factor authentication with TOTP for users who exceed the number of invalid login attempts you specify.

Scenario¶
Consider a scenario where the login flow of the application is stepped up with TOTP if a user exceeds three failed login attempts. The authentication steps are as follows:

Username and password
TOTP
Login attempt-based adaptive authentication

Prerequisites¶
You need to register an application with WSO2 Identity Server. You can register your own application or use one of the sample applications provided.

Configure the login flow¶
To enable conditional authentication:

On the WSO2 Identity Server Console, click Applications.

Select the relevant application and go to its Login Flow tab.

Add role-based adaptive MFA as follows:

Go to Predefined Flows > Conditional Login Flows.

Click Adaptive MFA > Login-Attempts-Based > ADD to add the role-based adaptive MFA script.

Login attempts based adaptive MFA with visual editor

Click Confirm to replace any existing script with the selected predefined script.

Verify that the login flow is now updated with the following two authentication steps:

Step 1: Username and Password
Step 2: TOTP
Update the following parameter in the script.

Parameter	Description
invalidAttemptsToStepup	
Minimum number of attempts made by a user to prompt 2FA. For this example scenario, enter 3.

Click Update to confirm.

How it works¶
Shown below is the script of the login-attempt-based conditional authentication template.


// This script will step up authentication for any user who has exceeded 3 invalid login attempts continuously.
// This variable is used to define the number of invalid attempts allowed before prompting the second facto.
var invalidAttemptsToStepup = 3;

var failedLoginAttemptsBeforeSuccessClaim= 'http://wso2.org/claims/identity/failedLoginAttemptsBeforeSuccess';
var onLoginRequest = function(context) {
  doLogin(context);
};

var doLogin = function(context) {
   executeStep(1, {
       onSuccess : function(context){
           var user = context.steps[1].subject;
           if (isExceedInvalidAttempts(user)) {
               executeStep(2, {
                 onSuccess : function(context) {
                   var user = context.steps[1].subject;
                   user.localClaims[failedLoginAttemptsBeforeSuccessClaim] = "0";
                 }
               });
           }
       },
       onFail : function(context) {
           // Retry the login..
           doLogin(context);
       }
   });
};

var isExceedInvalidAttempts  = function(user) {
   if (user.localClaims[failedLoginAttemptsBeforeSuccessClaim] >= invalidAttemptsToStepup) {
       return true;
   } else {
       return false;
   }
};
Let's look at how this script works.

Note

Find out more about the scripting language in the Conditional Authentication API Reference.

Try it out¶
Follow the steps given below.

Access the application URL.
Try to log in, but use invalid credentials.
Repeat step 2 for two more attempts making three failed login attempts.
Try to log in to the application using valid credentials.

The user will be prompted to enter the TOTP received on their registered TOTP authenticator.


,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,


Add MFA based on user group¶
You can enable a more secure login flow for users that belong to specific groups by applying the Group-Based conditional authentication template for Adaptive MFA. This template enables two-factor authentication with TOTP for users who belong to the user groups you specify.

Scenario¶
Consider a scenario with two user groups, manager and employee. For users assigned to these groups, the login flow in applications should be stepped up with TOTP as follows:

Username and password
TOTP
Group based adaptive authentication

Prerequisites¶
You need to register an application with WSO2 Identity Server. You can register your own application or use one of the sample applications provided.

Create two user groups named manager and employee and assign user accounts to them. For instructions, see the following:

Managing groups
Managing users
Configure the login flow¶
To enable conditional authentication:

On the WSO2 Identity Server Console, click Applications.

Select the relevant application and go to its Login Flow tab.

Add group-based adaptive MFA as follows:

Go to Predefined Flows > Conditional Login Flows.

Click Adaptive MFA > Group-Based > ADD.

Click Confirm to replace any existing script with the selected predefined script.

Verify that the login flow is now updated with the following two authentication steps:

Step 1: Username and Password
Step 2: TOTP
Update the following parameter in the script.

Parameter	Description
groupsToStepUp	
Comma separated list of user groups. Two-factor authentication should apply to users from these groups.

For this example scenario, enter manager and employee.
Click Update to confirm.

How it works¶
Shown below is the script of the group-based conditional authentication template.


var groupsToStepUp = ['manager', 'employee'];

var onLoginRequest = function (context) {
   executeStep(1, {
      onSuccess: function (context) {
            // Extracting authenticated subject from the first step.
            var user = context.currentKnownSubject;
            // Checking if the user is assigned to one of the given groups.
            var isMember = isMemberOfAnyOfGroups(user, groupsToStepUp);
            if (isMember) {
               Log.info(getMaskedValue(user.username) + ' is a member of one of the groups: ' + groupsToStepUp.toString());
               executeStep(2);
            }
      }
   });
};
Let's look at how this script works.

When step 1 of the authentication flow is complete, the onLoginRequest function retrieves the user from the context.
The user and the configured list of groups are passed to the following function: isMemberOfAnyOfGroups.
This function (which is available in WSO2 Identity Server by default) verifies whether the given user belongs to any of the listed groups.
If the user belongs to any of the configured groups, authentication step 2 (TOTP) is prompted.
Note

Find out more about the scripting language in the Conditional Authentication API Reference.

Try it out¶
Follow the steps given below.

Access the application URL.
Try to log in with a user who does not belong to any of the configured groups (manager or employee). You will successfully sign in to the application.
Log out of the application.
Log in with a user who belongs to the manager or employee group or both. TOTP authentication is prompted.


,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,


Add MFA based on user device¶
You can apply the New-Device-Based conditional authentication template to your application to enable a more secure login flow for users who log in from a previously unused device.

When the user signs in from a previously unused device, this template enables two-factor authentication and/or sends an email notification when the user passes the first authentication step. A cookie is used to identify whether the device has been used before. When the cookie expires (this expiry time is specified in the template), the same browser or device is considered a new device.

Scenario¶
Consider a scenario where users who log in to an application from a new device or browser should be prompted with TOTP as a second authentication step. The two authentication steps are as follows:

Username and password
TOTP
An email should also be sent to the user with details of the login attempt.

Prerequisites¶
You need to register an application with WSO2 Identity Server. You can register your own application or use one of the sample applications provided.

Configure the login flow¶
To enable conditional authentication:

On the WSO2 Identity Server Console, click Applications.

Select the relevant application and go to it's Login Flow tab.

Add New-Device-based adaptive MFA as follows:

Go to Predefined Flows > Conditional Login Flows.

Click Adaptive MFA > New-Device-Based > ADD.

Click Confirm to replace any existing script with the selected predefined script.

Verify that the login flow is now updated with the following two authentication steps:

Step 1: Username and Password
Step 2: TOTP
Update the following parameters in the script.

Parameter	Description
sendNotification	
Specifies whether email notifications should be sent to users.

For this scenario, set this parameter to true.
cookieName	A user-defined cookie name to be used for device identification.
deviceRememberPeriod	
The length of time in seconds for which this device should be remembered as a trusted device. Once this time period passes, login attempts are considered as new device logins.

For example, you can specify two years as follows: 60 * 60 * 24 * 365 * 2
Click Update to confirm.

How it works¶
Shown below is the script of the device-based conditional authentication template.


// This script will step up authentication and send email notification in case of
// a user being logging in from a new device (identified by a cookie).

// Amount of time in seconds to remember a device. Set to 2 years below.
var deviceRememberPeriod = 60 * 60 * 24 * 365 * 2;

// Cookie name to be set
var cookieName = 'deviceAuth';

// Whether to send a notification on new device login
var sendNotification = true;

// Whether to step up authentication for new device login
var stepUpAuthentication = true;

// Email template to be used for new device login notification
var emailTemplate = 'UnseenDeviceLogin';


var onLoginRequest = function(context) {
    executeStep(1, {
        onSuccess: function (context) {
            subject = context.currentKnownSubject;
            if (!validateCookie(context, subject)) {
                Log.debug('New device login with ' + subject.uniqueId);

                if (sendNotification === true) {
                    var templatePlaceholders = {
                        'username': subject.uniqueId,
                        'login-time': new Date().toUTCString()
                    };
                    var isSent = sendEmail(subject, emailTemplate, templatePlaceholders);
                    if (isSent) {
                         Log.debug('New device login notification sent to ' + subject.uniqueId);
                    } else {
                         Log.debug('New device login notification sending failed to ' + subject.uniqueId);
                    }
                }

                if (stepUpAuthentication === true) {
                    Log.debug('Stepping up authentication due to a new device login with ' + subject.uniqueId);
                    executeStep(2, {
                        onSuccess: function (context) {
                            setCookie(context.response, cookieName, subject.uniqueId, {
                                'sign': true,
                                'max-age': deviceRememberPeriod,
                                'sameSite': 'LAX'
                            });
                        }
                    });
                }
            }
        }
    });
};

//Validate if the user has a valid cookie with the value as subject's username
var validateCookie = function(context, subject) {
    var cookieVal = getCookieValue(context.request, cookieName, {'validateSignature': true});
    return subject.uniqueId === cookieVal;
};
Let's look at how this script works.

The validateCookie function verifies whether the user has a valid cookie for the logged-in user. This function calls the getCookieValue(request, name, properties) function. The cookie name is configured with the cookieName parameter.

When step 1 of the authentication flow is complete, the onLoginRequest function validates the deviceAuth cookie.

If there is no valid cookie found, the function checks whether the sendNotification and stepUpAuthentication parameters are enabled.

If the sendNotification property is enabled, the sendEmail(user, templateId, placeholderParameters) function is called to send the notification email with the login timestamp. The email template is set as UnseenDeviceLogin in the emailTemplate variable.

If the stepUpAuthentication parameter is enabled, step 2 of the authentication flow is executed.

On the successful execution of step 2 of the authentication flow, the setCookie(response, name, value, properties) function is called to set a deviceAuth cookie.

Note

Find out more about the scripting language in the Conditional Authentication API Reference.

Try it out¶
Follow the steps given below.

Access the application URL from a new device/browser.

Try to log in to the application. TOTP authentication is prompted and the configured email of the user receives the email notification.

new-device-email-notification-sample

Log out of the application.

Log in with the same user from the same device/browser. You will successfully log in to the application with only the basic authentication.

,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,


Add MFA based on IP address¶
You can secure the login flow based on the user's IP address by applying the IP-Based conditional authentication template to your application. This template prompts two-factor authentication for users who log in from outside a given IP range (such as external networks or other geographical locations).

Scenario¶
Consider a scenario where the internal IPs of an organization are as follows: 192.168.1.0/24, 10.100.0.0/16. The login flow should be stepped up with TOTP when users log in from outside this IP range as follows:

Username and password
TOTP
Users who log in from the internal network should be allowed to simply log in with their username and password.

IP address based adaptive authentication

Prerequisites¶
You need to register an application with WSO2 Identity Server. You can register your own application or use one of the sample applications provided.

Configure the login flow¶
To enable conditional authentication:

On the WSO2 Identity Server Console, click Applications.

Select the relevant application and go to it's Login Flow tab.

Add IP-based adaptive MFA using your preferred editor:

Go to Predefined Flows > Conditional Login Flows.

Click Adaptive MFA > IP-Based > ADD to add the IP-based adaptive MFA script.

Click Confirm to replace any existing script with the selected predefined script.

Verify that the login flow is now updated with the following two authentication steps:

Step 1: Username and Password
Step 2: TOTP
Update the following parameter in the script.

Parameter	Description
corpNetwork	Comma separated list of IP addresses. Two-factor authentication should apply when users log in from
outside this range. The default values in the template are 192.168.1.0/24 and 10.100.0.0/16.
Click Update to confirm.

How it works¶
Shown below is the script of the IP-based conditional authentication template.


// Configure the network ranges here
var corpNetwork = ['192.168.1.0/24', '10.100.0.0/16'];

var onLoginRequest = function(context) {
    executeStep(1, {
        onSuccess: function (context) {
            var user = context.currentKnownSubject;
            // Extracting the origin IP of the request
            var loginIp = context.request.ip;
            Log.info('User: ' + user.username + ' logged in from IP: ' + loginIp);
            // Checking if the IP is within the allowed range
            if (!isCorporateIP(loginIp, corpNetwork)) {
                executeStep(2);
            }
        }
    });
};

// Function to convert ip address string to long value
var convertIpToLong = function(ip) {
    var components = ip.split('.');
    if (components) {
        var ipAddr = 0, pow = 1, i = 3;
        return getIpAddrInLong(ipAddr, i, pow, components);
    } else {
        return -1;
    }
};

// Function to convert ip address string to long value
var getIpAddrInLong = function(ipAddr, i, pow, components) {
    if (i >= 0) {
        ipAddr += pow * parseInt(components[i]);
        pow *= 256;
        i -= 1;
        return getIpAddrInLong(ipAddr, i, pow, components);
    } else {
        return ipAddr;
    }
};

// Function to check if the ip address is within the given subnet
var isCorporateIP = function (ip, subnets, i) {
    if (i === undefined) {
        i = 0;
    }
    if (i < subnets.length) {
        var subnetComponents = subnets[i].split('/');
        var minHost = convertIpToLong(subnetComponents[0]);
        var ipAddr = convertIpToLong(ip);
        var mask = subnetComponents[1];
        if (subnetComponents && minHost >= 0) {
            var numHosts = Math.pow(2, 32 - parseInt(mask));
            if ((ipAddr >= minHost) && (ipAddr <= minHost + numHosts - 1)) {
                return true;
            }
        }
        i++;
        return isCorporateIP(ip, subnets, i);
    } else {
        return false;
    }
};
Let's look at how this script works.

The convertIpToLong function converts and returns the provided IP address as a long value.

The isCorporateIP function returns whether the user's IP address is in the given range. This method accepts two inputs. The first argument is the IP address that should be validated and the second argument is the allowed IP range.

When step 1 of the authentication flow is complete, the onLoginRequest function retrieves the IP address of the user from the context.

This IP address is passed to the isCorporateIP function along with the configured IP address range.

If the IP address of the logged-in user is not in the configured IP range, step 2 of the authentication flow is executed.

Note

Find out more about the scripting language in the Conditional Authentication API Reference.

Try it out¶
Follow the steps given below.

Access the application URL.

Try to log in with a user whose IP address is in the configured range. You will successfully log in to the application.

Log out of the application.

Log in with a user who does not belong to the configured IP address range. TOTP authentication is prompted.


,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

Add MFA based on ELK-risk¶
This guide shows you how to use ELK Analytics to assess an end user's risk score and enable adaptive authentication.

Scenario¶
Consider a business use case where a bank wants to prompt an additional authentication step when a user attempts to log in to the system after making transactions amounting to over $10,000 within five minutes.

The diagram below shows how the connection between the client applications, ELK Analytics, and WSO2 Identity Server works to assess the risk of the user.

risk-based-adaptive-authentication

The user performs bank transactions through different applications.

Transaction data from all these applications are published to the ELK Analytics via the “transaction” index.

The user attempts to access an application that uses WSO2 Identity Server as the identity provider.

The application sends an authentication request to WSO2 Identity Server.

The user is prompted to log in, and WSO2 Identity Server authenticates the user using basic authentication (username/password credentials).

WSO2 Identity Server publishes an event to ELK, which computes the user's risk score based on the user's transaction history using the data received in step 2.

For example

If the user has made transactions that add up to over $10,000 within the last five minutes, the risk score is 1. Else, the risk score is 0.

If the risk score is 1, WSO2 Identity Server prompts an additional step of authentication for the user (i.e., entering a hardware key number) before allowing the user to access the service provider application.

Prerequisites¶
See the general prerequisites for all adaptive authenticaiton scenarios.

Configure ELK analytics for adaptive authentication, and run the following command to create an index named transaction to store transaction data.

Info

Replace {ELASTICSEARCH_HOST} and {ELASTICSEARCH_BASIC_AUTH_HEADER} to match your settings.

Request Format


curl -L -X PUT 'https://{ELASTICSEARCH_HOST}/transaction' -H 'Authorization: Basic {ELASTICSEARCH_BASIC_AUTH_HEADER}' -H 'Content-Type: application/json' --data-raw '{"mappings":{"properties":{"@timestamp":{"type":"date"}}}}'
Sample Request

curl -L -X PUT 'https://localhost:9200/transaction' -H 'Authorization: Basic d3NvMnVzZXI6Y2hhbmdlbWU=' -H 'Content-Type: application/json' --data-raw '{"mappings":{"properties":{"@timestamp":{"type":"date"}}}}'
Response

{
"acknowledged": true,
"shards_acknowledged": true,
"index": "transaction"
}
Configure risk-based authentication¶
To configure risk-based conditional authentication:

On the WSO2 Identity Server Console, click Applications.

Select the relevant application and go to its Login Flow tab.

Add risk-based adaptive MFA as follows:

Go to Predefined Flows > Conditional Login Flows.

Click Adaptive MFA > ELK-Risk-Based > Add to add the ELK risk-based adaptive MFA script.

template-for-risk-based-authentication]

Click Confirm to replace any existing script with the selected predefined script.

Info

The resulting authentication script defines a conditional step that executes the second authentication step if the riskScore is greater than 0.
By default, TOTP will be added as the second authentication step. You can update this with any authentication method.
Click Update to save your configurations and restart WSO2 Identity Server.

Try it out¶
Start the Tomcat server and access the following sample PickUp application URL: http://localhost.com:8080/saml2-web-app-pickup-dispatch.com .

Log in to the application by giving your username and password.

Note

The user is authenticated with basic authentication only.

Log out of the application, and execute the following cURL command. This command publishes an event regarding a user bank transaction exceeding $10,000.

Request


curl -L -X POST 'https://{ELASTICSEARCH_HOST}/transaction/_doc' -H 'Authorization: Basic {ELASTICSEARCH_BASIC_AUTH_HEADER}' -H 'Content-Type: application/json' --data-raw '{
"@timestamp":"{CURRENT_TIMESTAMP}",
"username":"{USERNAME}",
"amount": {TRANSACTION_AMOUNT}
}'
Sample Request

curl -L -X POST 'https://localhost:9200/transaction/_doc' -H 'Authorization: Basic d3NvMnVzZXI6Y2hhbmdlbWU=' -H 'Content-Type: application/json' --data-raw '{
"@timestamp":"{{currenttimestamp}}",
"username":"Alex",
"amount": 12000
}'
Response

{
"_index": "transaction",
"_id": "_75YR4EBPqDnJYiU7W_A",
"_version": 1,
"result": "created",
"_shards": {
 "total": 2,
 "successful": 1,
 "failed": 0
},
"_seq_no": 0,
"_primary_term": 1
}
Log in to the application again. You are now prompted for the TOTP after the basic authentication.

Info

Before executing the cURL command given in step 4, the user had no transaction history, and the user's riskScore was 0. The authentication script is programmed to prompt only basic authentication if the risk score is 0.

After executing the command, a transaction event that indicates the user spending more than $10,000 is published and recorded in the Siddhi application. Therefore, when the user attempts to log in again, the user's riskScore is evaluated to 1, and the user is prompted for an extra authentication step.


,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,


Configure TypingDNA-based adaptive Authentication¶
Typing DNA uses AI-based technology to authenticate users according to the way they type.

You can integrate typingDNA with WSO2 Identity Server to provide risk-based adaptive authentication for users.

Scenario¶
Consider a scenario where you want to prompt an additional authentication step if the typing pattern of the user trying to log in does not match the typing pattern registered in the user's account. Then the log in flow of the user should be stepped up as follows:

Basic authentication (username and password)
TOTP
Prerequisites¶
See the general prerequisites for all adaptive authentication scenarios.

You need to register an application with WSO2 Identity Server. You can register your own application or use the playground2 sample application to test TypingDNA-based adaptive authentication.

Create a user named Alex with login permissions.

Create a typingDNA account. Learn how to create one here.

Info

Once you sign up for a typingDNA account, go to the dashboard and under API settings, enable Auto-enroll, and Force initial enrollments.

Set up TypingDNA in WSO2 IS¶
Follow the steps given below to set up typingDNA in the WSO2 IS server.

Open the deployment.toml file found in the <IS_HOME>/repository/conf/ directory and add the following configuration:


[myaccount.security]
enabled_features=["security.loginVerifyData.typingDNA"]
Go to the WSO2 store and download both the authenticator and the artifacts from the TypingDNA Connector.

Copy the Authenticator file (org.wso2.carbon.identity.conditional.auth.typingdna.functions-x.x.x.jar) to the <IS_HOME>/repository/components/dropins directory.

Unzip the Artifacts archive, copy the api#identity#typingdna#v_.war file to the <IS_HOME>/repository/deployment/server/webapps directory.

Restart the WSO2 IS.

Go to Login & Registration -> Other Settings -> TypingDNA Configuration and make the following changes.

Enable TypingDNA
Configure the typingDNA API key and API secret retrieved from the typingDNA dashboard.
Enable Advance TypingDNA-API mode if you have a pro/enterprise typingDNA account.
Configure the region (eu or us).
TypingDNA configuration

Click Update to save the changes.

Configure TypingDNA in applications¶
Follow the steps given below to configure TypingDNA in your application.

Go to Service Providers -> List and click Edit on the service provider that you want to configure TypingDNA in.

Expand Local and Outbound Authentication Configuration and click Advanced Configuration.

Configure two authentication steps.

Info

In this scenario, we will configure Username and Password and TOTP.

TypingDNA configure two authentication steps

Expand Script Based Adaptive Authentication and add the following script:


// This script will step up 2FA authentication if the user's typing behaviour does not match with the enrolled behaviour.

// You can use the parameters 'score' (num 0-100), 'result' (boolean), 'confidence' (num 0-100), 'comparedPatterns' in your 
// authentication logic to trigger the 2nd step. 
// Only the 'result' parameter has been used in the sample script. 

var onLoginRequest = function(context) {
    executeStep(1, {
        onSuccess: function (context) {
            verifyUserWithTypingDNA(context, {
                onSuccess: function(context,data){
                    // Change the definition here as required.
                    var userVerified = data.result;

                    // data.isTypingPatternReceived indicates whether a typing pattern is received from the login portal.
                    if (data.isTypingPatternReceived && !userVerified){
                        executeStep(2);
                    }
                },onFail: function(context,data){
                    executeStep(2);
                }
            });
        }
    });
};
TypingDNA script
Click Update to save the changes.

Try it out¶
Access the login page of the sample application and click Log in
Use the credentials of Alex and log in to the application two times.

Info

You will be prompted for the second step on both occasions. TypingDNA requires two initial enrollments to register the user’s typing pattern. You can change the number of minimum initial enrollments required in the API settings of the typingDNA dashboard.

Log in for the third time with Alex's credentials.

Info

From this log in attempt and beyond, typingDNA will analyze your typing pattern against the registered typing pattern of the account. TOTP will only be prompted if your typing pattern does not match the typing pattern registered in Alex's account.


,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,


Passkey Progressive Enrollment¶
This method of adaptive authentication is specifically designed for applications that have set up Passkey as a login option and have enabled passkey progressive enrollment so that users may enroll passkeys at the moment they log in to an application.

Note

Learn how to enable passkeys and passkey progressive enrollment in Add Passkey login.

Follow the guide below to learn about the adaptive script smoothly transition users using other methods of authentication to use passkeys as their primary authentication method.

Configure the login flow¶
The script is designed to execute during the authentication flow. When a user initiates passkey enrollment, the system prompts the user to log in with one of the other configured first-factor authentication methods. After successfully logging in, the user is guided through the passkey enrollment. To enable conditional authentication:

On the WSO2 Identity Server Console, click Applications.

Select the relevant application and go to its Login Flow tab.

Add the passkey progressive enrollment based adaptive script as follows.

Go to Predefined Flows > Conditional Login Flows.

Click Passkey Enrollment > Passkey Progressive Enrollment > ADD.

Click Confirm to replace any existing script with the selected predefined script.

Enable the Conditional Authentication toggle located at the bottom of the editor.

Important

Adding the passkey progressive enrollment adaptive script, modifies the authentication flow to include only the Username & Password and Passkey authenticators in the first step. If you need to include other authenticators in the first step, make sure to add them manually. Learn more in How it works.

Click Update to save your changes.

How it works¶
Shown below is the conditional authentication template for passkey progressive enrollment.


var onLoginRequest = function(context) {
    executeStep(1, {
        onFail: function(context) {
            var authenticatorStatus = context.request.params.scenario;

            // Passkey progressive enrollment flow trigger
            if (authenticatorStatus != null && authenticatorStatus[0] == 'INIT_FIDO_ENROLL') {
                var filteredAuthenticationOptions = filterAuthenticators(context.steps[1].options, 'FIDOAuthenticator');
                executeStep(1, {
                    stepOptions: {
                        markAsSubjectIdentifierStep: 'true',
                        markAsSubjectAttributeStep: 'true'
                    },
                    authenticationOptions: filteredAuthenticationOptions
                }, {
                    onSuccess: function(context) {
                        // Trigger FIDO Authenticator for Passkey enrollment
                        executeStep(1, {
                            stepOptions: {
                                forceAuth: 'true'
                            },
                            authenticationOptions: [{
                                authenticator: 'FIDOAuthenticator'
                            }]
                        }, {});
                    },
                });
            }
        }
    });
};
Let's look at how this script works:

If the user chooses Sign In With Passkey and consents to passkey enrollment, an onFail event is triggered. The parameter scenario returns the value INIT_FIDO_ENROLL, uniquely identifying the passkey enrollment request.

The filterAuthenticators() method takes the configured list of authenticators in the first step and the authenticator to be excluded and returns the list of authenticators excluding the Passkey authenticator(FIDOAuthenticator).

The user is then prompted for the first step of the authentication flow with authenticationOptions set to the list of filtered authenticators from the above step.

After successful authentication with an alternative authenticator, the script re-triggers the passkey authenticator. This allows users to seamlessly proceed with passkey enrollment.

,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

Push Notification Device Progressive Enrollment¶
This method of adaptive authentication is specifically designed for applications that have set up Push Notification as a passwordless login option and have enabled push notification device progressive enrollment so that users may enroll their push notification devices at the moment they log in to an application.

Note

Learn how to enable push notifications and push notification device progressive enrollment in Add Push Notification login.

Follow the guide below to learn about the adaptive script smoothly transition users using other methods of authentication to use push notification as their primary authentication method.

Configure the login flow¶
The script is designed to execute during the authentication flow. When a user initiates push notification device enrollment, the system prompts the user to log in with one of the other configured authentication methods. After successfully logging in, the user is guided through the push notification device enrollment. To enable conditional authentication:

On the WSO2 Identity Server Console, click Applications.

Select the relevant application and go to its Login Flow tab.

Add the push notification device progressive enrollment based adaptive script as follows.

Go to Predefined Flows > Conditional Login Flows.

Click Progressive Enrollment > Push Device Progressive Enrollment > ADD.

Click Confirm to replace any existing script with the selected predefined script.

Enable the Conditional Authentication toggle located at the bottom of the editor.

Configuring push device progressive enrollment in WSO2 Identity Server

Important

Adding the push device progressive enrollment adaptive script, modifies the authentication flow to include only the Username & Password and Push Notification authenticators in the authentication flow. If you need to include other authenticators, make sure to add them manually. Learn more in How it works.

Click Update to save your changes.

How it works¶
Shown below is the conditional authentication template for push device progressive enrollment.


var onLoginRequest = function(context) {
    executeStep(1, {
        onFail: function(context) {
            var authenticatorStatus = context.request.params.scenario;

            // If it is a push notification device progressive enrollment request, trigger the following flow.
            if (authenticatorStatus != null && authenticatorStatus[0] === 'INIT_PUSH_ENROLL') {
                executeStep(2, {
                    stepOptions: {
                        markAsSubjectIdentifierStep: 'true',
                        markAsSubjectAttributeStep: 'true'
                    }
                }, {
                    onSuccess: function(context) {
                        // If the user is successfully authenticated
                        executeStep(1, {
                            stepOptions: {
                                forceAuth: 'true'
                            }
                        }, {});
                    }
                });
            }
        }
    });
};
Let's look at how this script works:

The user will be prompted for the username in the first step. Since the user does not have a push notification device enrolled, the user will be displayed with an option to enroll a push notification device or to cancel the enrollment.

If the user clicks on Register to enroll a push notification device, the onFail event is triggered. The parameter scenario returns the value INIT_PUSH_ENROLL, uniquely identifying the push notification device enrollment request.

The second step of the authentication flow is triggered where Username & Password authentication is enforced by default. The user needs to authenticate by entering the password for the previously mentioned username.

After successful authentication, the onSuccess event is triggered. The first step of the authentication flow is triggered again which contains Push notification authenticator. This time, the user will be shown with the QR code to scan and enroll the push notification device.

After successful registration, the user will be sent a push notification to authenticate. The user can approve the authentication request to complete the login process.


,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,


On-demand silent password migration¶
This adaptive authentication script is specifically designed for on-demand silent password migration. A migrated user's password can be seamlessly migrated to WSO2 Identity Server using this method without forcing the user to reset the password.

Note

Learn how to set up on-demand silent password migration.

Conditional template¶
Shown below is the conditional authentication template for on-demand silent password migration.

Note

Learn more about the conditional authentication functions and objects in its API reference.


var user = null;
var userId = "";
var username = "";
var password = "";
var contextId = "";
var loginIdentifier = "";

var requestAuthConfig = {
    type: "clientcredential",
    properties: {
        consumerKey: "<consumer_key>",
        consumerSecret: "<consumer_secret>",
        tokenEndpoint: "<token_endpoint>"
    }
}

var requestHeaders = {
    "Content-Type": "application/json",
    "Accept": "application/json",
}

var onLoginRequest = function(context) {
    executeStep(1, {
        onSuccess: function(context) {
            Log.info("Login successful. Authenticated the user locally!");
        },
        onFail: function(context) {
            resolveAndInitUser(context);

            // If the user is found, proceed with the migration.
            if (user !== null) {
                userId = user.uniqueId;

                // Check whether the user is already migrated.
                if (user.localClaims["http://wso2.org/claims/is_migrated"] === "true") {
                    Log.info("Password is already migrated for the user: " + userId + ".");
                    sendError(null, {
                        'status': 'Login failed!',
                        'statusMsg': 'Please check your username and password and try again.',
                        'i18nkey': 'auth.fail.error'
                    });
                } else {
                    Log.info("Password is not yet migrated for the user: " + userId + ". Starting the external authentication.");

                    // Start the external authentication.
                    authenticateExternally();
                }
            } else {
                sendError(null, {
                    'status': 'Login failed!',
                    'statusMsg': 'Please check your username and password and try again.',
                    'i18nkey': 'auth.fail.error'
                });
            }
        }
    });
};


/**
 * This function will resolve the user using the login identifier and initialize the login variables.
 */
var resolveAndInitUser = function(context) {

    // Retrieve login identifier and password provided by the user.
    loginIdentifier = context.request.params.username[0];
    password = context.request.params.password[0];

    Log.info("User login initiated for the user: " + loginIdentifier);

    // If your organization has enabled alternative login identifiers, the username has to be resolved using the login identifier.
    // If not, uncomment the immediate next line and comment out rest of the section.
    // username = loginIdentifier;
    Log.info("Resolving username using the login identifier: " + loginIdentifier);
    username = resolveMultiAttributeLoginIdentifier(loginIdentifier, context.tenantDomain);
    Log.info("Username resolved using the login identifier. Resolved username: " + username);
    // End of username resolving section.

    // Retrieve unique user object for the username.
    var claimMap = {};
    claimMap["http://wso2.org/claims/username"] = "PRIMARY/" + username;
    user = getUniqueUserWithClaimValues(claimMap, context);
};


/**
 * This function will authenticate the user with the external service.
 */
var authenticateExternally = function() {

    var requestPayload = {
        id: userId,
        username: username,
        password: password
    };

    Log.info("Invoking the external authentication endpoint for the user: " + userId + ".");

    // Invoke the start authentication API.
    httpPost("<start_authentication_endpoint>", requestPayload, requestHeaders, requestAuthConfig, {
        onSuccess: function(context, data) {
            if (data !== null && data.message !== null) {
                if (data.message === "Received") {
                    // Set the context ID to be used in subsequent requests.
                    contextId = data.contextId;

                    Log.info("Started external authentication for the user: " + userId + " with context ID: " + contextId + ". Redirecting to the waiting page.");

                    // Redirect to the waiting page to wait until the external authentication is completed.
                    prompt("internalWait", {
                        "waitingType": "POLLING",
                        "waitingConfigs": {
                            "timeout": "10",
                            "pollingEndpoint": "<polling_endpoint>",
                            "requestMethod": "GET",
                            "requestData": "contextId=" + contextId,
                            "pollingInterval": "2"
                        }
                    }, {
                        onSuccess: function(context) {
                            Log.info("Successfully redirected back from the waiting page.");

                            // Check authentication status, update password and re-authenticate the user.
                            updatePasswordAndReAuthenticate();
                        },
                        onFail: function(context, data) {
                            Log.info("Error occurred while redirecting. Please retry!");
                        }
                    });
                } else {
                    Log.info("External authentication failed for the user: " + userId + ". Message: " + data.message + ".");
                    sendError(null, {
                        'status': 'Authentication failed',
                        'statusMsg': 'Please contact your administrator.',
                        'i18nkey': 'auth.fail.error'
                    });
                }
            } else {
                Log.info("External authentication failed for the user: " + userId + ".");
                sendError(null, {
                    'status': 'Authentication failed',
                    'statusMsg': 'Please contact your administrator.',
                    'i18nkey': 'auth.fail.error'
                });
            }
        },
        onFail: function(context, data) {
            Log.info("Error occurred while invoking the external API to start authentication.");

            sendError(null, {
                'status': 'Authentication failed',
                'statusMsg': 'Please contact your administrator.',
                'i18nkey': 'auth.fail.error'
            });
        },
        onTimeout: function(context, data) {
            Log.info("Connection timed out while invoking the external API to start authentication.");

            sendError(null, {
                'status': 'Authentication failed',
                'statusMsg': 'Please contact your administrator.',
                'i18nkey': 'auth.fail.error'
            });
        }
    });
};


/**
 * This function will check for the authentication status, update the password and re-authenticate.
 */
var updatePasswordAndReAuthenticate = function() {

    var requestPayload = {
        contextId: contextId,
        username: username
    };

    Log.info("Invoking the external API to check auth status for the user: " + userId + " with context ID: " + contextId + ".");

    // Invoke the external authentication API.
    httpPost("<authentication_status_endpoint>", requestPayload, requestHeaders, requestAuthConfig, {
        onSuccess: function(context, data) {
            if (data.status !== null && data.status === "SUCCESS") {
                Log.info("External authentication is successful for the user: " + userId + ". Proceeding with password update.");

                // Update the user password.
                updateUserPassword(user, password, {
                    onSuccess: function(context) {
                        Log.info("Password updated successfully for the user: " + userId + ".");

                        // Set the password migration flag to true.
                        user.localClaims["http://wso2.org/claims/is_migrated"] = "true";

                        reAuthenticate();
                    },
                    onFail: function(context) {
                        Log.info("Failed to update password of the user: " + userId + ".");
                    }
                });
            } else if (data.status !== null && data.status === "FAIL") {
                var errorMessage = "";
                if (data.message !== null) {
                    errorMessage = data.message;
                }

                Log.info("External authentication failed for the user: " + userId + ". Message: " + errorMessage + ".");

                sendError(null, {
                    'status': 'Authentication failed',
                    'statusMsg': 'External authentication failed with the error: ' + errorMessage + '. Please contact your administrator.',
                    'i18nkey': 'auth.fail.error'
                });
            } else {
                var errorMessage = "";
                if (data.message !== null) {
                    errorMessage = data.message;
                }

                Log.info("Something went wrong during the external authentication for the user: " + userId + ". Message: " + errorMessage + ".");

                sendError(null, {
                    'status': 'Authentication failed',
                    'statusMsg': 'Please contact your administrator.',
                    'i18nkey': 'auth.fail.error'
                });
            }
        },
        onFail: function(context, data) {
            Log.info("Error occurred while invoking the external API to check auth status.");

            sendError(null, {
                'status': 'Authentication failed',
                'statusMsg': 'Please contact your administrator.',
                'i18nkey': 'auth.fail.error'
            });
        },
        onTimeout: function(context, data) {
            Log.info("Connection timed out while invoking the external API to check auth status.");

            sendError(null, {
                'status': 'Authentication failed',
                'statusMsg': 'Please contact your administrator.',
                'i18nkey': 'auth.fail.error'
            });
        }
    });
};


/**
 * This function will re-authenticate the user with the new password.
 */
var reAuthenticate = function() {

    // Re-authenticate without prompting user input.
    Log.info("Re-authenticating the user: " + userId + " with the new password.");

    executeStep(1, {
        authenticatorParams: {
            common: {
                'username': loginIdentifier,
                'password': password
            }
        },
    }, {
        onSuccess: function(context) {
            Log.info("Re-authentication successful for the user: " + userId + ".");
        },
        onFail: function() {
            Log.info("Re-authentication failed for the user: " + userId + ".");

            sendError(null, {
                'status': 'Authentication failed',
                'statusMsg': 'Please contact your administrator.',
                'i18nkey': 'auth.fail.error'
            });
        }
    });
};
Replace the following parameters of the script with values relevant to your setup:

start_authentication_endpoint	URL of the start authentication endpoint deployed in an external service
polling_endpoint	URL of the polling endpoint deployed in an external service
authentication_status_endpoint	URL of the authentication status endpoint deployed in an external service
requestAuthConfig	An object containing necessary authentication metadata to invoke the APIs. Refer Conditional authentication - API reference for more information.
How it works¶
Let's look at how the above conditional authentication script works.

The first authentication step (Username & Password) is initiated with the executeStep(1, ..) function. Based on its status, one of the following happens.

If the entered credentials match, the user's password is already migrated. Hence, the onSuccess function will be called and the user will be authenticated.

If the user's credentials don't match, either the credentials are incorrect or the password may not have be migrated yet. Hence, the onFail callback function is called and the script continues.

If the onFail function is called, the script will next try to locate a unique user in the system using the resolveAndInitUser function.

Adjust script for alternate login identifiers

If your organization does not use alternate login identifiers, comment the following lines in the resolveAndInitUser function.


Log.info("Resolving username using the login identifier: " + loginIdentifier);
username = resolveMultiAttributeLoginIdentifier(loginIdentifier, context.tenantDomain);
Log.info("Username resolved using the login identifier. Resolved username: " + username);
If a unique user is found, the script checks the value of the is_migrated attribute of the user and does one of the following. (The is_migrated user attribute holds the status of the password migration.)

If this is set to true, user's password is already migrated. Hence, the entered credentials are incorrect and the flow fails with an error.

If it is not set to true, the user's password is not yet migrated. Hence, the script calls for external authentication.

The script calls for external authentication with the authenticateExternally function and it works as follows:

The requestAuthConfig object holds the necessary authentication metadata to invoke the APIs.
The script first calls the httpPost() function along with the requestAuthConfig and invokes the start authentication endpoint.

If the API call is successful, the onSuccess() callback function is called which in turn calls the prompt() function.

The prompt() function continuously polls the external polling endpoint and redirects the user to a waiting page until the external authentication completes.

Once the authentication is complete, the onSuccess() callback function of the prompt() function calls the updatePasswordAndReAuthenticate() function.

The updatePasswordAndReAuthenticate() function is responsible for checking the status of the authentication and taking necessary actions as explained below.

The requestAuthConfig object holds the necessary authentication metadata to invoke the APIs.
The script first calls the httpPost() function along with the requestAuthConfig and invokes the authentication status endpoint.

If the API call is successful, the onSuccess() callback function is called and the response message is checked. If it is SUCCESS, the external authentication was successful.

The script then calls the updateUserPassword() function to update the user password in the WSO2 Identity Server user store.
Afterwards, the is_migrated attribute of the user is set to true and the user is re-authenticated.
The reAuthenticate() function that handles the re-authentication performs a silent authentication. This means that the user is not prompted to enter the credentials again.



,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,


Write a custom authentication script¶
Follow this guide to write a conditional authentication script from scratch and to understand its internals.

Note

ECMAScript Compliance: Adaptive scripts currently comply with ECMAScript 2022 (ES13).

Limitations: Adaptive scripts do not support loops, Log.warn logs, or stringifying Java objects using JSON.stringify().

Scenario¶
Let's consider the following simplified set of requirements for your business application:

User tries to log in to an application with username and password authentication.
If the user belongs to the manager or employee group, they can access the application. Other users should not be allowed to access the application.
Authentication flow with Group based Access control

Prerequisites¶
You need to register an application with WSO2 Identity Server. You can register your own application or use one of the sample applications provided.

Customize the login flow in your application and enable Username and Password authentication.

Create two user groups named manager and employee and assign user accounts to them. For instructions, see the following:

Managing groups
Managing users
Start with the default script¶
To start off, configure conditional authentication for your application and check the default script once you enable the two steps authentication.


var onLoginRequest = function(context) {
    executeStep(1);
};
The above scripts do not have any conditional authentication. It allows all users to access the application after successful authentication through username and password.
Implement onSuccess callback¶
Now, let's implement what happens when username and password authentication is successful. You can use the onSuccess eventCallback.


var onLoginRequest = function (context) {
    executeStep(1, {
        onSuccess: function (context) {
            // Implement what to do when Step 1 authentication is success.
        }
    });
};
Get user object¶
If username and password authentication is successful, let's get the user from the context. You can use context.currentKnownSubject.


var groups = ['employee', 'manager'];

var onLoginRequest = function (context) {
    executeStep(1, {
        onSuccess: function (context) {
            // Extracting authenticated user from the first step.
            var user = context.currentKnownSubject;
        }
    });
};
Check membership of the user¶
Now, let's check whether the user is a member of manager or employee. You can use the isMemberOfAnyOfGroups(user, groups) utility function.

Refer the inbuilt functions to get to know more existing functions.


var groups = ['employee', 'manager'];

var onLoginRequest = function (context) {
    executeStep(1, {
        onSuccess: function (context) {
            // Extracting authenticated user from the first step.
            var user = context.currentKnownSubject;
            // Checking if the user is assigned to one of the given groups.
            var isMember = isMemberOfAnyOfGroups(user, groups);           
        }
    });
};
Fail authentication¶
If the user is not a member, fail the authentication and redirect the user to the application with some error code.


var groups = ['employee', 'manager'];
var errorCode = 'access_denied';
var errorMessage = 'You do not have access to login to this app';

var onLoginRequest = function (context) {
    executeStep(1, {
        onSuccess: function (context) {
            // Extracting authenticated user from the first step.
            var user = context.currentKnownSubject;
            // Checking if the user is assigned to one of the given groups.
            var isMember = isMemberOfAnyOfGroups(user, groups);
            if (!isMember) {
               fail({'errorCode': errorCode, 'errorMessage': errorMessage});
            }  
        }
    });
};
You have now written a conditional authentication script for the group-based access control scenario.

Similarly, you can build your own scripts to handle many scenarios using the API references.





