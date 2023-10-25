#! /bin/bash

# Installation of MySQL (via Homebrew) feature fails.
# Install MySQL after container creation.
# Failed feature: ghcr.io/devcontainers-contrib/features/mysql-homebrew:1
brew install mysql

# No available feature for installing Container Structure Test https://github.com/GoogleContainerTools/container-structure-test
brew install container-structure-test
