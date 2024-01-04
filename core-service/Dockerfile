ARG JAVA_VERSION=17
FROM openjdk:${JAVA_VERSION}
COPY target/core-service-1.0.0.RELEASE.jar core-service.jar
EXPOSE 5000
CMD ["java","-jar","/core-service.jar"]