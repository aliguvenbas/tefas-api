FROM openjdk:8-jdk-alpine
MAINTAINER ag
COPY dist/tefas-api-0.0.1-SNAPSHOT.jar tefas-api.jar
ENTRYPOINT ["java","-jar","/tefas-api.jar"]
