FROM bellsoft/liberica-openjdk-alpine-musl:11
MAINTAINER ag
COPY dist/tefas-api.jar tefas-api.jar
ENTRYPOINT ["java","-jar","/tefas-api.jar"]
