# Use OpenJDK 21 image as the base
FROM openjdk:21-jdk AS BUILDER

# Set the working directory inside the container
WORKDIR /opt/app

# Copy the .jar file from the host machine into the container
COPY pana-front-end/target/pana-front-end-0.0.1.jar .

# Expose the port on which the app will run
EXPOSE 8080

# Command to run the jar file using java
CMD [ "java", "-jar", "/opt/app/pana-front-end-0.0.1.jar" ]