FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY build/libs/srv-config-server-0.0.1-SNAPSHOT.jar /app/srv-config-server-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/srv-config-server-0.0.1-SNAPSHOT.jar"]
