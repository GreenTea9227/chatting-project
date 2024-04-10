FROM gradle:latest as builder

WORKDIR /workspace/app

COPY . /workspace/app

RUN gradle build

FROM eclipse-temurin:17.0.10_7-jre-alpine as run

WORKDIR /app

COPY --from=builder /workspace/app/build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]