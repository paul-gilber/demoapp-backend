# Demoapp Backend
This project was forked from: [arjungautam1/fullstack-backend](https://github.com/arjungautam1/fullstack-backend) and will be used for demonstration of DevOps CI/CD automation

## Dependencies
1. MySQL database instance

## Visual Studio Dev Container with Podman Desktop
1. Install [Podman Desktop](https://podman-desktop.io/docs/installation)
2. Install [Podman CLI](https://podman.io/docs#installing-podman)
3. Install [Podman Compose](https://github.com/containers/podman-compose#installation)
4. Update [Visual Studio Code User Settings](https://code.visualstudio.com/docs/getstarted/settings#_settingsjson)
```yaml
# settings.json
{
  "dev.containers.dockerComposePath": "podman-compose",    # Add this
  "dev.containers.dockerPath": "podman"    # Add this
}
```

## Build Application from Visual Studio Code Dev Container
This project uses [Visual Studio Code Dev Containers](https://code.visualstudio.com/docs/devcontainers/containers) which provides consistent Development environment across user(s) or team(s).

Visual Studio Code Dev Containers extension looks up [devcontainer.json](.devcontainer/devcontainer.json) file which defines the Container environment specification.

### Build Application JAR
To ensure successful build of this project, `project.properties['java.version']` value from [pom.xml](pom.xml) must match with `features['ghcr.io/devcontainers/features/java:1'].version` from [devcontainer.json](.devcontainer/devcontainer.json)
```xml
<!-- pom.xml -->
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<properties>
		<java.version>17</java.version>
	</properties>
</project>
```
```yaml
# .devcontainer/devcontainer.json
"features": {
    # https://github.com/devcontainers/features/tree/main/src/java
    "ghcr.io/devcontainers/features/java:1": {
        "version": "17" # Version must match with pom.xml
    }
}
```
```sh
# Build and run tests
mvn clean install
# Build and skip tests
mvn clean install -Dmaven.test.skip=true

# Note: Visual Studio Dev Container creates and runs `mysql` service. To add other dependency services, update `.devcontainer/compose.yaml`
```
### Build Application Container with Multi-stage builds
[Multi-stage](https://docs.docker.com/build/building/multi-stage/) builds are useful to anyone who has struggled to optimize Dockerfiles while keeping them easy to read and maintain.
```sh
docker build -f Containerfile.multistage -t demoapp-backend .
```

## Run Application from Visual Studio Code Dev Container
### Run Application using Java
```sh
java -jar target/demoapp-backend-0.0.1-SNAPSHOT.jar
# Note: Application startup will fail due to the absence of mysql database
```
### Run Application using Docker Compose
[Compose](https://docs.docker.com/compose/) is a tool for defining and running multi-container Docker applications. With Compose, you use a YAML file to configure your application's services. Then, with a single command, you create and start all the services from your configuration.

By default, Compose looks up configuration from [compose.yaml](compose.yaml).
To ensure successful run of the Application, `spring.datasource.url` from [application.properties](src/main/resources/application.properties) must match with [compose.yaml](compose.yaml)
```sh
# src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://mysql:3306/demoapp
# Database credentials must not be hardcoded and should be provided using Environment variables
# Externalized Spring Configuration: https://docs.spring.io/spring-boot/docs/1.5.6.RELEASE/reference/html/boot-features-external-config.html
spring.datasource.username=  # Environment variable: SPRING_DATASOURCE_USERNAME
spring.datasource.password=  # Environment variable: SPRING_DATASOURCE_PASSWORD
```
```yaml
# compose.yaml
services:
  mysql: # Service name is also used as hostname when connecting from other containers
    environment:
      MYSQL_ROOT_PASSWORD: local
      MYSQL_DATABASE: demoapp
      MYSQL_USER: user
      MYSQL_PASSWORD: password
    healthcheck:
      test: mysql --host=localhost --user=root --password=$$MYSQL_ROOT_PASSWORD demoapp # Login to mysql demoapp db. `$$` tells docker compose not to parse `MYSQL_ROOT_PASSWORD` environment variable
  demoapp-backend:
    depends_on:
      mysql:
        condition: service_healthy # Ensure `mysql` health before starting `demoapp-backend` container
    environment:
      # Externalized Spring Configuration: https://docs.spring.io/spring-boot/docs/1.5.6.RELEASE/reference/html/boot-features-external-config.html
      SPRING_DATASOURCE_USERNAME: root  # Overrides application.properties `spring.datasource.username`
      SPRING_DATASOURCE_PASSWORD: local # Overrides application.properties `spring.datasource.password`
    healthcheck:
      test: curl --fail http://localhost:8080/actuator/health # Check Spring Boot Actuator. Requires `org.springframework.boot:spring-boot-starter-actuator` dependency in pom.xml
    ports:
      - "8080:8080" # Forwards container port 8080 to host port 8080. URL: http://localhost:8080/. Actuator URL: http://localhost:8080/actuator/health
```
```sh
docker-compose up # builds application image when it doesn't exist
docker-compose up --build # rebuild application image
docker-compose down # remove containers and network created by docker compose 
```

## Testing Application Container Image with Container Structure Tests
[Container Structure Tests](https://github.com/GoogleContainerTools/container-structure-test) provide a powerful framework to validate the structure of a container image. These tests can be used to check the output of commands in an image, as well as verify metadata and contents of the filesystem

Run below command to run [test](container-structure-test.yaml) for `demoapp-backend`
```sh
container-structure-test test --image demoapp-backend --config container-structure-test.yaml
```

## Changes
1. Setup of [Visual Studio Code](https://code.visualstudio.com/) [Dev Container](https://code.visualstudio.com/docs/devcontainers/containers). See [devcontainer.json](./.devcontainer/devcontainer.json)
2. Removal of hardcoded credentials in [application.properties](./src/main/resources/application.properties)
3. Replacement of `localhost` hostname for mysql database url with `mysql` in [application.properties](./src/main/resources/application.properties)
4. Creation of [Containerfile](./Containerfile)
5. Creation of [compose.yaml](./compose.yaml)
6. Enablement of Spring Boot Actuator in [pom.xml](./pom.xml)
7. [Container Structure Test](#testing-application-container-image-with-container-structure-tests)