# At this point, BUILDER stage should have your .jar or whatever in some path
FROM openjdk:21-jdk
WORKDIR /opt/app
COPY pana-front-end/target/pana-front-end-0.0.1.jar .
EXPOSE 8080
CMD [ "java", "-jar", "/opt/app/pana-front-end-0.0.1.jar" ]
