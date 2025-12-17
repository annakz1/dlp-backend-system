FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace
# copy full repo so we can run Maven with reactor
COPY . /workspace
# build only the configuration-service module and its dependencies
RUN mvn -f pom.xml -pl services/configuration-service -am clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
# copy the built jar from the builder stage
COPY --from=build /workspace/services/configuration-service/target/configuration-service-*.jar /app/configuration-service.jar
COPY services/configuration-service/src/main/resources/application.properties /opt/conf/application.properties
EXPOSE 8080
CMD ["java", "-jar", "/app/configuration-service.jar", "--spring.config.location=file:/opt/conf/application.properties"]
