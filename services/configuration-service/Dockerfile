FROM openjdk:17
COPY target/configuration-service*.jar /usr/src/configuration-service.jar
COPY src/main/resources/application.properties /opt/conf/application.properties
CMD ["java", "-jar", "/usr/src/configuration-service.jar", "--spring.config.location=file:/opt/conf/application.properties"]
