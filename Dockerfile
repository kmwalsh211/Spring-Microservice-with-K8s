FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy your locally built jar
COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
