FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/server-0.0.1-SNAPSHOT.jar /tmp
CMD ["java","-jar","/tmp/server-0.0.1-SNAPSHOT.jar"]