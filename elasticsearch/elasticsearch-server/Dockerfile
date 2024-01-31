ARG JAVA_VERSION=17
FROM openjdk:${JAVA_VERSION}
COPY target/elasticsearch-server-1.0.0.RELEASE.jar elasticsearch-server.jar
EXPOSE 9000
CMD ["java","-jar","/elasticsearch-server.jar"]