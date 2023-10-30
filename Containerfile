# Build demoapp-backend using dockerfile-maven-plugin: https://github.com/spotify/dockerfile-maven

ARG RUNTIME_IMAGE="registry.access.redhat.com/ubi8/openjdk-17-runtime"
FROM ${RUNTIME_IMAGE}
WORKDIR /app

# Add the service itself
ARG JAR_FILE
COPY --chown=185:0 target/${JAR_FILE} demoapp-backend.jar

CMD ["/usr/bin/java", "-jar", "/app/demoapp-backend.jar"]
