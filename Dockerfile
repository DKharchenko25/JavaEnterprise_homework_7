FROM openjdk:8-jdk-alpine
WORKDIR /application
EXPOSE 8081
COPY ./target/homework7-0.0.1-SNAPSHOT.war /application
CMD ["java", "-jar", "homework7-0.0.1-SNAPSHOT.war"]
