#FROM maven:3.9.16-eclipse-temurin-25-alpine AS build
#
#WORKDIR /app
#
#COPY pom.xml .
#COPY src ./src
#
#RUN mvn clean package -DskipTests
#
#
#FROM eclipse-temurin:17-jdk-alpine AS build
#
#WORKDIR /app
#
#COPY --from=build /app/target/taskmanager-0.0.1-SNAPSHOT.jar taskmanager.jar
#
#EXPOSE 8080
#
#ENTRYPOINT ["java","-jar","taskmanager.jar"]

# Étape 1 : Build de l'application avec Java 25
FROM openjdk:25-jdk-slim AS build
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw package -DskipTests

# Étape 2 : Exécution de l'application avec Java 25
FROM openjdk:25-jdk-slim
WORKDIR /app
COPY --from=build /app/target/taskmanager-0.0.1-SNAPSHOT.jar taskmanager.jar

EXPOSE 9090
ENTRYPOINT ["java", "-jar", "taskmanager.jar"]