FROM openjdk:11-jre-slim
COPY target/demo-app-1.0-SNAPSHOT.jar app.jar
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "app.jar"]
