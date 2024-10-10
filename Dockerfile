
# Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
# Click nbfs://nbhost/SystemFileSystem/Templates/Other/Dockerfile to edit this template

FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/libreria-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_libreria.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_libreria.jar"]
