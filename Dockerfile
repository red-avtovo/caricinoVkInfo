FROM hypriot/rpi-java:latest
ENTRYPOINT []

RUN apt-get update && \
    apt-get -qy install curl ca-certificates

ADD caricyno/target/caricyno-1.0-SNAPSHOT.jar /tmp

CMD ["java", "-jar", "/tmp/caricyno-1.0-SNAPSHOT.jar"]