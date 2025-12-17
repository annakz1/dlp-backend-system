FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY . /workspace
RUN mvn -f pom.xml -pl services/scanner-service -am clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/services/scanner-service/target/scanner-service-*.jar /app/scanner-service.jar
COPY services/scanner-service/src/main/resources/application.properties /opt/conf/application.properties
EXPOSE 8080
CMD ["java", "-jar", "/app/scanner-service.jar", "--spring.config.location=file:/opt/conf/application.properties"]
