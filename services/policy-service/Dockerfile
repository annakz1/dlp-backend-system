FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY . /workspace
RUN mvn -f pom.xml -pl services/policy-service -am clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/services/policy-service/target/policy-service-*.jar /app/policy-service.jar
COPY services/policy-service/src/main/resources/application.properties /opt/conf/application.properties
EXPOSE 8080
CMD ["java", "-jar", "/app/policy-service.jar", "--spring.config.location=file:/opt/conf/application.properties"]
