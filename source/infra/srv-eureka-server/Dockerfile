FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/discovery-server-1.0-SNAPSHOT.jar /app/discovery-server-1.0-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/discovery-server-1.0-SNAPSHOT.jar"]