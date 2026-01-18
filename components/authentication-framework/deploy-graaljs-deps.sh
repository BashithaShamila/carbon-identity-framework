#!/bin/bash

# Deploy script for GraalJS Dependencies Wrapper and Authentication Framework
# This script builds the wrapper bundle and authentication framework, then deploys to IS

set -e

IS_HOME="/Users/bashitha/Downloads/product/wso2is-7.2.1-SNAPSHOT"
FRAMEWORK_HOME="/Users/bashitha/Downloads/product/carbon-identity-framework"
AUTH_FRAMEWORK_HOME="${FRAMEWORK_HOME}/components/authentication-framework"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}=== Building GraalJS Dependencies Wrapper Bundle ===${NC}"

# Build the wrapper bundle first
cd "${AUTH_FRAMEWORK_HOME}/org.wso2.carbon.identity.graaljs.deps.wrapper"
mvn clean package -DskipTests -Dcheckstyle.skip=true -q

if [ $? -ne 0 ]; then
    echo -e "${RED}Failed to build wrapper bundle${NC}"
    exit 1
fi

echo -e "${GREEN}Wrapper bundle built successfully${NC}"

echo -e "${YELLOW}=== Building Authentication Framework ===${NC}"

# Build the authentication framework
cd "${AUTH_FRAMEWORK_HOME}/org.wso2.carbon.identity.application.authentication.framework"
mvn package -DskipTests -Dcheckstyle.skip=true -q

if [ $? -ne 0 ]; then
    echo -e "${RED}Failed to build authentication framework${NC}"
    exit 1
fi

echo -e "${GREEN}Authentication framework built successfully${NC}"

echo -e "${YELLOW}=== Deploying bundles to IS ===${NC}"

# Get the bundle versions - wrapper is now a shaded jar, not a bundle
WRAPPER_JAR="${AUTH_FRAMEWORK_HOME}/org.wso2.carbon.identity.graaljs.deps.wrapper/target/org.wso2.carbon.identity.graaljs.deps.wrapper-7.8.644-SNAPSHOT.jar"
AUTH_FRAMEWORK_JAR="${AUTH_FRAMEWORK_HOME}/org.wso2.carbon.identity.application.authentication.framework/target/org.wso2.carbon.identity.application.authentication.framework-7.8.644-SNAPSHOT.jar"

if [ ! -f "$WRAPPER_JAR" ]; then
    echo -e "${RED}Wrapper JAR not found at: ${WRAPPER_JAR}${NC}"
    exit 1
fi

if [ ! -f "$AUTH_FRAMEWORK_JAR" ]; then
    # Try without -SNAPSHOT
    AUTH_FRAMEWORK_JAR="${AUTH_FRAMEWORK_HOME}/org.wso2.carbon.identity.application.authentication.framework/target/org.wso2.carbon.identity.application.authentication.framework-7.8.644.jar"
fi

if [ ! -f "$AUTH_FRAMEWORK_JAR" ]; then
    echo -e "${RED}Authentication framework JAR not found${NC}"
    exit 1
fi

# Deploy wrapper bundle to dropins (since it's a new bundle not in the original product)
echo "Deploying wrapper bundle to dropins..."
cp "$WRAPPER_JAR" "${IS_HOME}/repository/components/dropins/"

# Deploy authentication framework to plugins (replacing existing)
echo "Deploying authentication framework to plugins..."
cp "$AUTH_FRAMEWORK_JAR" "${IS_HOME}/repository/components/plugins/org.wso2.carbon.identity.application.authentication.framework_7.8.644.jar"

echo -e "${GREEN}=== Deployment completed ===${NC}"
echo ""
echo "Deployed:"
echo "  - Wrapper: $(basename $WRAPPER_JAR) -> dropins/"
echo "  - Auth Framework: $(basename $AUTH_FRAMEWORK_JAR) -> plugins/"
echo ""
echo -e "${YELLOW}To start IS with clean OSGi:${NC}"
echo "  cd ${IS_HOME}/bin"
echo "  sh wso2server.sh run -Dosgi.clean=true"
