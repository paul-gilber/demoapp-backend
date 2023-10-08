# Demoapp Frontend
This project was forked from: [arjungautam1/fullstack-backend](https://github.com/arjungautam1/fullstack-backend) and will be used for demonstration of DevOps CI/CD automation

## Dependencies
1. MySQL database instance

## Build and Run
```sh
docker build -f Containerfile -t demoapp-backend .
docker run -p 8080:8080 demoapp-backend
```

## Changes
1. Setup of [Visual Studio Code](https://code.visualstudio.com/) [Dev Container](https://code.visualstudio.com/docs/devcontainers/containers). See [devcontainer.json](./.devcontainer/devcontainer.json)
2. Removal of hardcoded credentials in [application.properties](./src/main/resources/application.properties)
3. Replacement of `localhost` hostname for mysql database url with `mysql` in [application.properties](./src/main/resources/application.properties)
4. Creation of [Containerfile](./Containerfile)
