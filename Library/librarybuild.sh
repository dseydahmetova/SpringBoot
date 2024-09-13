#!/bin/sh

docker stop libraryservice
./gradlew build
docker build . -t libraryrest
docker run -d -p 80:8080 --rm --name libraryservice libraryrest