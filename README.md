# pana-ws-app: A Guide to Running the Application

## Introduction
`pana-ws-app` is a modulith Spring Boot application that uses Temporal.io to manage workflows and keycloak as an identity server for creating and authenticating users. This application is database agnostic and can work with any relational database.

## Prerequisites
- Java Development Kit (JDK) 8 or later
- Maven
- Docker (optional)
- Access to a relational database
- Temporal.io
- keycloak

## Steps to Run the Application

1. **Clone the Repository**
   Clone the `pana-ws-app` repository onto your local machine.

2. **Configure Database**
   Update the `application.properties` file located in the resources directory (`src/main/resources`) of each module with your database connection details.

3. **Setup Temporal.io**
    - Install Temporal CLI on your local machine[^1^][1].
    - Start a local Temporal Service using the command `temporal server start-dev`. The Temporal Service will be available on `keycloak:7233`[^2^][2].

4. **Setup keycloak**
    - Download and install keycloak[^3^][10].
    - Create a new realm and add users and roles as per your application's requirement[^4^][12].

5. **Build the Application**
   Navigate to the root directory of each module where the `pom.xml` file is located and run the following command:

mvn clean install

This command will compile the code and install the necessary dependencies.

6. **Run the Application**
   After successfully building the application, navigate back to the root directory of `pana-ws-app` and run the following command:

mvn spring-boot:run

7. **Docker (Optional)**
   If you prefer to run your application in a Docker container, you can build a Docker image and run it using the provided `Dockerfile` and `docker-compose.yml` files.

## Conclusion
This guide provides the basic steps to get the `pana-ws-app` up and running. For more specific configurations or advanced usage, please refer to the official Spring Boot, Temporal.io, and keycloak documentation.
