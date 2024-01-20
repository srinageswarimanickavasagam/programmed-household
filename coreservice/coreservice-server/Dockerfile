ARG JAVA_VERSION=17
FROM openjdk:${JAVA_VERSION}
COPY target/coreservice-server-1.0.0.RELEASE.jar coreservice-server.jar
EXPOSE 5000
CMD ["java","-jar","/coreservice-server.jar"]