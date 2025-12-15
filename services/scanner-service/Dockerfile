FROM openjdk:17-ea-17-jdk
COPY target/scanner-service*.jar /usr/src/scanner-service.jar
COPY src/main/resources/application.properties /opt/conf/application.properties
CMD ["java", "-jar", "/usr/src/scanner-service.jar", "--spring.config.location=file:/opt/conf/application.properties"]
