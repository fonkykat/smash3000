FROM openjdk:8-jre-alpine


# Make port 8080 available to the world outside this container
EXPOSE 8081

# Add the application's jar to the container
ADD target/smash3000.jar app.jar

# Run the jar file 
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]


