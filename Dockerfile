FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY target/simple-webapp-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

EXPOSE 8082

CMD ["java", "-jar", "app.jar"]
