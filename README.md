Reference: https://www.javaguides.net/2022/12/deploy-spring-boot-mysql-application-to-docker.html

#############################Running the app with only MySQL container before dockerizing################################

//bring up the mysql image
docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=recipedb -d mysql
//here enter the local root password instead of "root"

//bring up the application
mvn spring-boot:run

below is the datasource url in properties file (host will be changed to run in containerized environment)
spring.datasource.url=jdbc:mysql://localhost:3306/recipedb

##########################################Step 1 : Create jar#############################################################

mvn clean package

Once maven builds success, go target folder and you will be able to see the
recipe-manager-1.0.0.RELEASE.jar file generated under the target folder.

##########################################Step 2 : Create Dockerfile to Build the docker image############################
Docker builds images automatically by reading the instructions from a Dockerfile. The Dockerfile is a text file that contains all commands, in order, needed to build a given image. 

Let's go to the project root directory and create a file named Dockerfile and the following content to it:

ARG JAVA_VERSION=17
FROM openjdk:${JAVA_VERSION}
COPY target/recipe-manager-1.0.0.RELEASE.jar recipe-manager.jar
EXPOSE 5000
CMD ["java","-jar","/recipe-manager.jar"]

Next, use the following command to maven build this project:
mvn clean package
Once maven builds success, go target folder and you will be able to see the
recipe-manager-1.0.0.RELEASE.jar file generated under the target folder.

##########################################Step 3 : Build Docker Image from Dockerfile######################################
Now that we have defined the Dockerfile, let’s build a docker image for our application.

Before building the docker image, you need to make sure that you’ve packaged the application in the form of a jar file using maven. 

Let’s now build the docker image by typing the following command:

docker build -t recipe-manager-app .
The file path . defines the location of the Dockerfile in the current directory, and the -t argument tags the resulting image, where the image name is the recipe-manager-app and the tag is the latest.

After the build is successfully finished, we can check to see if it appears in the list of docker images available locally. To do so, we can execute the below command.
docker images

############################################################################################################################
###########################Running the containers by executing Docker-compose.yml before pushing image to hub ##############

version: "3.8"

services:
  mysqldb:
    container_name: mysql
    image: mysql
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: recipedb
    networks:
      springboot-mysql-net:

  app:
    container_name: recipe-manager
    build:
      context: ./
      dockerfile: Dockerfile
    ports:
      - "5000:5000"
    depends_on:
      - mysqldb
    networks:
      springboot-mysql-net:
    restart: on-failure

networks:
  springboot-mysql-net:

##########################################Push docker image to docker hub###################################################

$ docker logout
Removing login credentials for https://index.docker.io/v1/

$ docker login
Login with your Docker ID to push and pull images from Docker Hub. If you don't have a Docker ID, head over to https://hub.docker.com to create one.
Username: smanickavasa
Password: 
Login Succeeded

Logging in with your password grants your terminal complete access to your account.
For better security, log in with a limited-privilege personal access token. Learn more at https://docs.docker.com/go/access-tokens/

$ docker tag recipe-manager-app smanickavasa/recipe-manager-app

$ docker push smanickavasa/recipe-manager-app
Using default tag: latest
The push refers to repository [docker.io/smanickavasa/recipe-manager-app]
4d3437a9695e: Pushed
dc9fa3d8b576: Pushed
27ee19dc88f2: Pushed
c8dd97366670: Pushed
latest: digest: sha256:891b742ae18fbd0da3289c41b46b1c850a15dcf6a08003b9da3c7c8a77b9b23f size: 1166

############################################################################################################################
###########################Running the containers by executing Docker-compose.yml after pushing image to hub################

After pushing the image to docker hub repository, only docker-compose.yml file is enough to bring up the app. Only thing is we need to mention the image name instead of build param

#after pushing image to docker hub repository remove the build parameter under 'app' service then add "image: smanickavasa/recipe-manager-app" similar to 'mysql' service

Copy the below content in a file named docker-compose.yml and run the command docker-compose to bring up the dockerized application image in local

version: "3.8"

services:
  mysqldb:
    container_name: mysql
    image: mysql
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: recipedb
    networks:
      springboot-mysql-net:

  app:
    container_name: recipe-manager
    image: smanickavasa/recipe-manager-app
    ports:
      - "5000:5000"
    depends_on:
      - mysqldb
    networks:
      springboot-mysql-net:
    restart: on-failure

networks:
  springboot-mysql-net:

###########################################################################################################################
########################################## Manually running the containers from commandline ###############################

Since we are going to create two docker containers (recipe-manager-app & MYSQL) that should communicate with each other, we will need to start them on the same Docker network. 

Deploy MySQL Image in a Container

##########################################Pull MySQL Image#################################################################
Here is the docker command to pull the latest MySQL docker image:

docker pull mysql

######################Create a docker network to communicate Spring boot application and MySQL database####################
Here is the docker command to create a new network:
docker network create springboot-mysql-net
Here springboot-mysql-net is the network name.

Use the below command to list the networks:
docker network ls

##########################################Run MySQL image in a docker container in the same network########################
Here is the docker command to run MySQL image in a container in the same network:

docker run --name mysql --network springboot-mysql-net -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=recipedb -d mysql

##########################################Access the MySQL database in a container#########################################
Here is the command to access the MySQL database in a container:

docker exec -it mysql bash

For example:
~/Documents/repo/personal/recipe-manager (master)
bash-4.4# mysql -u root -p
Enter password:
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 9
Server version: 8.1.0 MySQL Community Server - GPL

Copyright (c) 2000, 2023, Oracle and/or its affiliates.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| recipedb           |
| sys                |
+--------------------+
5 rows in set (0.01 sec)

mysql> use recipedb;
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed
mysql> show tables;
+--------------------+
| Tables_in_recipedb |
+--------------------+
| recipe             |
+--------------------+
1 row in set (0.00 sec)

mysql> exit
Bye
bash-4.4# exit
exit

That's it. Once the MySQL image is deployed in a docker container. Next, we will pull and run the recipe app in a docker container.


Deploy Recipe Manager Application in a Docker Container

############################Login to your personal docker hub account to pull the app's image############################

$ docker login
Login with your Docker ID to push and pull images from Docker Hub. If you don't have a Docker ID, head over to https://hub.docker.com to create one.
Username: smanickavasa
Password: 
Login Succeeded

Logging in with your password grants your terminal complete access to your account.
For better security, log in with a limited-privilege personal access token. Learn more at https://docs.docker.com/go/access-tokens/

##########################################Run app's docker image in a docker container in the same network################
Once publish a docker image in docker hub repository, you can run it using the docker run command like so: (this will pull the app's image if it doesn't exist in local)

docker run --network springboot-mysql-net --name recipe-app-container -p 5000:5000 smanickavasa/recipe-manager-app

Note that we are running the Spring boot application in a container in the same docker network (springboot-mysql-net).

