FROM maven:3.9.11-eclipse-temurin-17
WORKDIR /app
COPY . .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]