ARG BUILD_IMAGE="registry.access.redhat.com/ubi8/openjdk-17"
ARG RUNTIME_IMAGE="registry.access.redhat.com/ubi8/openjdk-17-runtime"
ARG MAVEN_ARGS="-Dmaven.test.skip=true"

# Build
FROM ${BUILD_IMAGE} as build
ARG MAVEN_ARGS

COPY src ./src
COPY pom.xml ./pom.xml
RUN mvn "${MAVEN_ARGS[@]}" clean package

# APP
FROM ${RUNTIME_IMAGE}
COPY --from=build /home/jboss/target/demoapp-backend*.jar demoapp-backend.jar
CMD ["/usr/bin/java", "-jar", "demoapp-backend.jar"]
