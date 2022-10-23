FROM maven:latest as build
WORKDIR /workdir
COPY . .
EXPOSE 8080

RUN mvn -q dependency:go-offline

RUN mvn package

FROM openjdk:11 as runtime
COPY --from=build /workdir/target/interview-planning-0.0.1-SNAPSHOT.jar /application/interview-app.jar
ENTRYPOINT java -jar /application/interview-app.jar