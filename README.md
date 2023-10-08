# Demoapp Frontend
This project was forked from: [arjungautam1/fullstack-backend](https://github.com/arjungautam1/fullstack-backend) and will be used for demonstration of DevOps CI/CD automation

## Dependencies
1. MySQL database instance

## Build and Run
```sh
docker build -f Containerfile -t demoapp-backend .
docker run -p 8080:8080 demoapp-backend
```
