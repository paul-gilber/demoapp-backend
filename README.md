# Demoapp Backend
This project was forked from: [arjungautam1/fullstack-backend](https://github.com/arjungautam1/fullstack-backend) and will be used for demonstration of DevOps CI/CD automation

## Dependencies
1. MySQL database instance

## Visual Studio Code Dev Container Configuration


## Build Application
### Build Application JAR from Dev Container
This project uses [Visual Studio Code Dev Containers](https://code.visualstudio.com/docs/devcontainers/containers) which provides consistent Development environment across user(s) or team(s).

Visual Studio Code Dev Containers extension looks up [devcontainer.json](.devcontainer/devcontainer.json) file which defines Container environment specification.

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
```json
// .devcontainer/devcontainer.json
"features": {
    // https://github.com/devcontainers/features/tree/main/src/java
    "ghcr.io/devcontainers/features/java:1": {
        "version": "17", // Version must match with pom.xml
    }
}
```
```sh
mvn clean install -Dmaven.test.skip=true
```
### Build and Run
```sh
docker build -f Containerfile -t demoapp-backend .
docker run -p 8080:8080 demoapp-backend
```

## Changes
1. Setup of [Visual Studio Code](https://code.visualstudio.com/) [Dev Container](https://code.visualstudio.com/docs/devcontainers/containers). See [devcontainer.json](./.devcontainer/devcontainer.json)
2. Removal of hardcoded credentials in [application.properties](./src/main/resources/application.properties)
3. Replacement of `localhost` hostname for mysql database url with `mysql` in [application.properties](./src/main/resources/application.properties)
4. Creation of [Containerfile](./Containerfile)
