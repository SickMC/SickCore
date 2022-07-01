ARG BUILD_DIR="/usr/src/sickcore-server"

FROM gradle:7.4.2-jdk AS builder

ARG BUILD_DIR

WORKDIR $BUILD_DIR
COPY /sickcore-server/ .
RUN cd ..
COPY gradlew sickcore-server/
RUN cd sickcore-server/
RUN ./gradlew installDist --no-daemon --stacktrace

FROM openjdk:17-slim

ARG BUILD_DIR

WORKDIR /app/
COPY --from=builder $BUILD_DIR/build/install/sickcore-server/ .

CMD ["./bin/sickcore-server"]
