ARG JAVA_VERSION=17
FROM openjdk:${JAVA_VERSION}
COPY target/programmed-household-app-1.0.0.RELEASE.jar programmed-household-app.jar
EXPOSE 5000
CMD ["java","-jar","/programmed-household-app.jar"]