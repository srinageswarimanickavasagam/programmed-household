# Stage 1: Build the parent POM and all its modules
FROM maven:3.8.4-openjdk-17-slim AS build-all
WORKDIR /app

# Copy the entire project directory
COPY . .

# Build the parent POM and all its modules
RUN mvn -f pom.xml clean install -DskipTests

# Stage 2: Create the coreservice-server image
FROM maven:3.8.4-openjdk-17-slim AS coreservice-build
WORKDIR /app
COPY --from=build-all /app/coreservice/coreservice-server/target/coreservice-server-1.0.0.RELEASE.jar ./coreservice/coreservice-server/target/
EXPOSE 5000
CMD ["java", "-jar", "./coreservice/coreservice-server/target/coreservice-server-1.0.0.RELEASE.jar"]

# Tag the image as programmed-household-coreservice:latest
FROM coreservice-build AS programmed-household-coreservice-img

# Stage 3: Create the elasticsearch-server image
FROM maven:3.8.4-openjdk-17-slim AS elasticsearch-build
WORKDIR /app
COPY --from=build-all /app/elasticsearch/elasticsearch-server/target/elasticsearch-server-1.0.0.RELEASE.jar ./elasticsearch/elasticsearch-server/target/
EXPOSE 9000
CMD ["java", "-jar", "./elasticsearch/elasticsearch-server/target/elasticsearch-server-1.0.0.RELEASE.jar"]

# Tag the image as programmed-household-elasticsearch:latest
FROM elasticsearch-build AS programmed-household-elasticsearch-img