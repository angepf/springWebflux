FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/config-server-1.0-SNAPSHOT.jar /app/config-server-1.0-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/config-server-1.0-SNAPSHOT.jar"]