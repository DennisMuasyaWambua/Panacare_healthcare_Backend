# Stage 1: Dependency Caching
FROM maven:3.8.7-openjdk-21-slim as DEPS

WORKDIR /opt/app

# Copy only the pom files to cache dependencies
COPY pom.xml .
COPY pana-beans/pom.xml pana-beans/pom.xml
COPY pana-workers/pom.xml pana-workers/pom.xml
COPY pana-front-end/pom.xml pana-front-end/pom.xml
COPY pana-workers/wk-onboarding/pom.xml pana-workers/wk-onboarding/pom.xml

# Download dependencies only (without compiling)
RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline \
    -DexcludeArtifactIds=pana-beans,pana-workers

# Stage 2: Build Application
FROM maven:3.8.7-openjdk-21-slim as BUILDER

WORKDIR /opt/app

# Reuse dependencies
COPY --from=DEPS /root/.m2 /root/.m2

# Copy actual source files
COPY . .

# Build the application (Only pana-front-end)
RUN mvn -pl pana-front-end clean package -DskipTests

# Stage 3: Run Application (Lightweight Final Image)
FROM openjdk:21-jdk-slim as RUNTIME

WORKDIR /opt/app

# Copy the final JAR from the builder stage
COPY pana-front-end/target/pana-front-end-0.0.1.jar .

# Expose port
EXPOSE 8080

# Set keycloak and Temporal environment variables
ENV KC_URL=https://keycloak-production-2aa3.up.railway.app
#ENV TEMPORAL_URL=http://temporal:7233

# Run the application
CMD ["java", "-jar", "app.jar"]
