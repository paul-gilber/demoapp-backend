// Configuration for docker/bake-action: https://github.com/docker/bake-action
// https://github.com/marketplace/actions/docker-metadata-action#bake-definition
// docker-bake.hcl
target "docker-metadata-action" {}

target "build" {
  inherits = ["docker-metadata-action"]
  context = "./"
  // Use multi-stage Containerfile
  dockerfile = "Containerfile.multistage"
  // Set target platforms for multi-platform builds https://docs.docker.com/build/bake/reference/#targetplatforms
  platforms = [
    "linux/amd64",
    "linux/arm64"
  ]
}
