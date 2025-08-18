FROM maven:3.9.11-eclipse-temurin-17
WORKDIR /app
COPY . .
RUN mvn clean package
RUN cp target/meli-item-detail-api-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]