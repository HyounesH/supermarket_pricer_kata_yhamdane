FROM maven:3.6.3-jdk-8 as build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/supermarket-pricer-kata.jar"]