FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar /app/api.jar

EXPOSE 9090

CMD ["java", "-jar", "api.jar"]