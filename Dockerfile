#Build jar file
FROM maven:3.6.3-jdk-11-slim as buildJar
COPY pom.xml /jar/
COPY src /jar/src/
WORKDIR /jar/
RUN mvn package
#Execute jar file
FROM openjdk:11-jdk-slim
WORKDIR /start_java
COPY --from=buildJar /jar/target/handyman-0.0.1-SNAPSHOT.jar /start_java/
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "handyman-0.0.1-SNAPSHOT.jar"]


