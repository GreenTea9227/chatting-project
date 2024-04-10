#!/bin/bash

echo "Clean build gradle"
./gradlew clean build

echo "Running ./gradlew docker"
./gradlew docker

echo "Running docker-compose up -d"
docker-compose up -d
