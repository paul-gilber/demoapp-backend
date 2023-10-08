ARG BUILD_IMAGE="registry.access.redhat.com/ubi8/openjdk-17"
ARG RUNTIME_IMAGE="registry.access.redhat.com/ubi8/openjdk-17-runtime"
ARG MAVEN_ARGS="-Dmaven.test.skip=true"

# Build
FROM ${BUILD_IMAGE} as build
ARG MAVEN_ARGS

COPY . ./
RUN mvn "${MAVEN_ARGS[@]}" clean package

# APP
FROM ${RUNTIME_IMAGE}
COPY --from=build /home/jboss/target/*.jar app.jar
CMD ["/usr/bin/java", "-jar", "app.jar"]
