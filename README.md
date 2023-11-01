# Demoapp Backend
This project was forked from: [arjungautam1/fullstack-backend](https://github.com/arjungautam1/fullstack-backend) and will be used for demonstration of DevOps CI/CD automation

See [repository configuration](docs/repository-configuration/README.md)

## Features
1. Provides consistent development environment across users using [Visual Studio Code Dev Containers](https://code.visualstudio.com/docs/devcontainers/containers). See [configuration](.devcontainer/devcontainer.json)

2. Uses [git pre-commit hooks](https://git-scm.com/book/en/v2/Customizing-Git-Git-Hooks) to prevent Workflow failures caused by trivial errors like linter errors. This [pre-commit hook](.githooks/pre-commit) is shared across users through [Visual Studio Code Dev Containers](https://code.visualstudio.com/docs/devcontainers/containers) using [postCreateCommand.sh](.devcontainer/postCreateCommand.sh) which sets hooks path to `.githooks`

3. Uses [Docker Compose](https://docs.docker.com/compose/) to enable local deployment of the `application` (demoapp-backend) including all `dependencies` (mysql). See [compose.yaml](deploy/docker-compose/compose.yaml)

4. Provides [pull request checklist](.github/pull_request_template.md). See [sample pull request](https://github.com/paul-gilber/demoapp-backend/pull/34)

5. Supports multi-platform container image builds using [Docker buildx bake](https://docs.docker.com/build/bake/). See [docker-bake.hcl](docker-bake.hcl)

6. Uses [Container Structure Tests](https://github.com/GoogleContainerTools/container-structure-test) for running metadata, command and file existence tests to ensure consistency of image builds. See [container-structure-test.yaml](container-structure-test.yaml)

7. Uses [GitHub Actions workflows](https://docs.github.com/en/actions/using-workflows/about-workflows) for automating builds, scans, tests, publishing of [GitHub Packages](https://github.com/features/packages), automatic pull request labeling, and release drafting (and versioning)

## GitHub Actions workflows
The following workflows are included in this project:

1. [Build](.github/workflows/build.yml)
- Builds application container image and pushes it to [Docker Hub](https://hub.docker.com/)
- Tests application container image with [Container Structure Tests](https://github.com/GoogleContainerTools/container-structure-test)
- Scans application container image with [Aqua Security Trivy](https://trivy.dev/#:~:text=Trivy%20is%20the%20most%20popular,Apache%2D2.0%20License)

2. [Coverage reports with CodeCov](.github/workflows/code-scan-codecov.yml)

[Codecov](https://about.codecov.io/) is the all-in-one code coverage reporting solution for any test suite — giving developers actionable insights to deploy reliable code with confidence. Trusted by over 29,000 organizations.

3. [Code Analysis with CodeQL](.github/workflows/code-scan-codecov.yml)

[CodeQL](https://codeql.github.com/docs/codeql-overview/about-codeql/) is the analysis engine used by developers to automate security checks, and by security researchers to perform variant analysis.

4. [Code Analysis with SonarCloud](.github/workflows/code-scan-sonarcloud.yml)

[SonarCloud](https://docs.sonarcloud.io/) is a cloud-based code analysis service designed to detect coding issues.

5. [Pull Request Labeler](.github/workflows/labeler.yml)

Automatically label new pull requests based on the paths of files being changed.

6. [Release Drafter](.github/workflows/release-drafter.yml)

Drafts your next release notes as pull requests are merged into `main`.
Release drafter recommends release version based on [release-drafter.yml](.github/release-drafter.yml#L22)
See [sample releases](https://github.com/paul-gilber/demoapp-backend/releases).

7. [Publish Container Image to GitHub Packages](.github/workflows/release.yml)
```
To create a release:
1. Create pre-release from draft. A corresponding tag is created by this step e.g. `v1.0.0`. Workflow is triggered when a tag starting with `v` is created.
2. Workflow builds and publishes release image to GitHub packages
3. Set pre-release as latest version
```

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

### Build Application Container Image with Maven
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

# Notes:
# - Visual Studio Dev Container creates and runs `mysql` service. To add other dependency services, update `.devcontainer/compose.yaml`
# - Maven was configured to create 2 image tags: [1] based on project.version [2] `latest` tag
```
### Build Application Container Image with Multi-stage builds
[Multi-stage](https://docs.docker.com/build/building/multi-stage/) builds are useful to anyone who has struggled to optimize Dockerfiles while keeping them easy to read and maintain.
```sh
docker build -f Containerfile.multistage -t demoapp-backend:latest .
```

## Run Application from Visual Studio Code Dev Container
### Run Application using Java
```sh
java -jar target/demoapp-backend-0.0.1-SNAPSHOT.jar
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
# Note: by default, compose.yaml was configured to use an existing application image. Run build before docker compose or update compose.yaml and enable `build` field

docker compose --project-directory deploy/docker-compose up
docker compose --project-directory deploy/docker-compose up --build # rebuild application image, only applicable if `build` field is enabled
docker compose --project-directory deploy/docker-compose down # remove containers, networks and volumes created by docker compose

```

## Testing Application Container Image with Container Structure Tests
[Container Structure Tests](https://github.com/GoogleContainerTools/container-structure-test) provide a powerful framework to validate the structure of a container image. These tests can be used to check the output of commands in an image, as well as verify metadata and contents of the filesystem

Run below command to run [test](container-structure-test.yaml) for `demoapp-backend`
```sh
container-structure-test test --image demoapp-backend --config container-structure-test.yaml
```
