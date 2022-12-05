FROM maven:latest as build
WORKDIR /build
COPY . .

RUN mvn package

FROM openjdk:11
WORKDIR /application
EXPOSE ${APPLICATION_PORT}
COPY --from=build /build/target/interview-planning-0.0.1-SNAPSHOT.jar .
ENTRYPOINT java -jar interview-planning-0.0.1-SNAPSHOT.jar