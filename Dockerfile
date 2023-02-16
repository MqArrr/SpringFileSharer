FROM openjdk:17-jdk
LABEL authors="MqArrr"

COPY ./target/SpringFileService-0.0.1-SNAPSHOT.jar /app/application.jar
EXPOSE 8080

# Определяем точку входа (опционально)
ENTRYPOINT ["java", "-jar", "/app/application.jar"]