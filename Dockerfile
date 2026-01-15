# Step 1: Base image (Java 17)
FROM openjdk:17-jdk-slim

# Step 2: JAR file ka path
ARG JAR_FILE=target/*.jar

# Step 3: JAR copy karo container me
COPY ${JAR_FILE} app.jar

# Step 4: Port expose karo
EXPOSE 8080

# Step 5: Run command
ENTRYPOINT ["java", "-jar", "/app.jar"]
