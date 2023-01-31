FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim

RUN groupadd -r springapp && useradd --no-log-init -r -g springapp springapp
RUN mkdir /app

WORKDIR /app

COPY --from=build ./target/sj-refresh-data.jar /app/app.jar
COPY ./application-docker.yaml /app/application.yaml
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]