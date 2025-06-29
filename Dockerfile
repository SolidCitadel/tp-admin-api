# Build
FROM eclipse-temurin:21-alpine AS build

WORKDIR /app
COPY . .
RUN ./gradlew build -x test --no-daemon


# Exec
FROM eclipse-temurin:21-alpine

WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ENV DOCKERIZE_VERSION=v0.2.0
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

ENTRYPOINT [ "sh", "-c", "dockerize -wait tcp://${DB_HOST:-db}:3306 -timeout 20s && java -jar ./app.jar" ]

EXPOSE 8081