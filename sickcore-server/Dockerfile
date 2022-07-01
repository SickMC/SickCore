ARG BUILD_DIR="/usr/src/"

FROM gradle:7.4.2-jdk AS builder

ARG BUILD_DIR

WORKDIR $BUILD_DIR
COPY /sickcore-server/ sickcore-server
COPY gradlew sickcore-server
WORKDIR $BUILD_DIR/sickcore-server
RUN ./gradlew installDist --no-daemon --stacktrace

FROM openjdk:17-slim

ARG BUILD_DIR

WORKDIR /app/
COPY --from=builder $BUILD_DIR/build/install/sickcore-server/ .

CMD ["./bin/sickcore-server"]
