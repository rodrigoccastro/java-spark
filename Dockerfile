FROM maven:3.8.4-openjdk-17

ADD . /usr/src/axreng
WORKDIR /usr/src/axreng
EXPOSE 4567
ENTRYPOINT ["mvn", "clean", "verify", "exec:java"]