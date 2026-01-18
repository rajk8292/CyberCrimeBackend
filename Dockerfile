FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

<<<<<<< HEAD
CMD ["java","-jar","target/cybercrime-0.0.1-SNAPSHOT.jar"]
=======
CMD ["java","-jar","target/cybercrime-0.0.1-SNAPSHOT.jar"]
>>>>>>> b3d035934d0177ec627b5ba95da4d60cbd0d1fad
