FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
CMD ["java","-jar","target/server-0.0.1-SNAPSHOT.jar"]