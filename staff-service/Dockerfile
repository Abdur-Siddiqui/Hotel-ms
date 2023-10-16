# Dockerfile tells you how to create a Docker img
# anybody using this will have the same environment

FROM gradle:7.4 as builder
WORKDIR /usr/src/app
COPY src ./src
COPY build.gradle .
RUN ["gradle", "bootJar"]

EXPOSE 8080

FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY --from=builder /usr/src/app/${JAR_FILE} app.jar
#RUN apt-get update; apt-get -y install curl
RUN microdnf update \
   && microdnf install curl \
    && microdnf install jq

# telling container how to launch it
ENTRYPOINT ["java", "-jar", "/app.jar"]