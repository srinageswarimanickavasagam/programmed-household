ARG JAVA_VERSION=17
FROM openjdk:${JAVA_VERSION}
COPY target/coreservice-1.0.0.RELEASE.jar coreservice.jar
EXPOSE 5000
CMD ["java","-jar","/coreservice.jar"]