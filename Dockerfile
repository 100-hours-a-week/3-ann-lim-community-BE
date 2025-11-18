# 1단계: 빌드 스테이지
FROM bellsoft/liberica-openjdk-alpine:17 AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle gradle
COPY gradlew .

COPY src src

RUN chmod +x gradlew

RUN ./gradlew clean build -x test


# 2단계: 실행 스테이지
FROM bellsoft/liberica-openjdk-alpine:17

WORKDIR /app

COPY --from=builder /app/build/libs/*-SNAPSHOT.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]