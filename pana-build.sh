#!/bin/bash

# Set Java environment to Java 21
export JAVA_HOME=/usr/lib/jvm/jdk-21-oracle-x64
export PATH=$JAVA_HOME/bin:$PATH

./mvnw clean install -DskipTests=true

#./mvnw -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline -DexcludeArtifactIds=pana-beans -DexcludeArtifactIds=pana-workers

#docker-compose down && docker-compose --env-file pana-env up  -d
docker-compose down --rmi all --volumes && docker-compose up -d
