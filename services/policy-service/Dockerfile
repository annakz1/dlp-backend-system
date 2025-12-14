FROM openjdk:17-ea-17-jdk
COPY target/policy-service*.jar /usr/src/policy-service.jar
COPY src/main/resources/application.properties /opt/conf/application.properties
CMD ["java", "-jar", "/usr/src/policy-service.jar", "--spring.config.location=file:/opt/conf/application.properties"]
