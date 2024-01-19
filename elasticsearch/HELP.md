## Table of Contents

1. [ElasticSearch](#elasticsearch)
2. [How does it work](#how-does-it-work)
3. [Applications](#applications)
4. [Elastic Stack](#elastic-stack-technologies-developed-by-the-company)
   1. [Kibana](#kibana-)
   2. [Logstash](#logstash)
      1. [Input plugin](#input-plugin---are-about-how-logstash-received-events)
      2. [Filter plugin](#filter-plugin---are-all-about-how-logstash-should-process)
      3. [Output plugin](#output-plugin---where-we-sent-the-processed-events-to---called-as-stashes)
   3. [X-Pack](#x-pack)
      1. [Authentication & Authorization](#authentication--authorization-feature)
      2. [Monitoring](#monitoring)
      3. [Graph](#graph-feature)
      4. [Elasticsearch SQL](#elasticsearch-sql-)
   4. [Beats](#beats)
      1. [FileBeat](#filebeat-)
      2. [MetricBeat](#metricbeat)
5. [Data flow](#data-flow-between-the-application-and-elastic-stack)
   1. [Communication](#communication)
   2. [How data gets into Elasticsearch?](#how-data-gets-into-elastic-search)
   3. [Significance of Logstash](#significance-of-logstash)
      1. [With Elasticsearch](#with-elasticsearch)
      2. [With Beats](#with-beats)
6. [Setup](#elasticsearch--kibana-setup-using-docker)
   1. [How to update elasticsearch.yml?](#how-to-disable-security-in-elasticsearchyml-file)
   

## Elasticsearch
- A search engine written in Java, built on Apache lucene

## How does it work?
- Data is stored as documents (similar to rows in relational databases)
- A document's data is separated into fields (similar to columns in relational databases)
- A document is a JSON object
- These documents are created using rest APIs

## Applications
- Analytics & full text search engine
- Query & analyze structured data
- Analyze application logs and system metrics
- Application Performance Management
- Abnormality Detection (compare with kafka)

## Elastic Stack (Technologies developed by the company)

- Elasticsearch is at the center which is used to search, analyze and store data.
- Ingesting data into Elasticsearch can be done with Beats and/or Logstash, but also through elasticsearch's API.
- Kibana is a user interface to visualize the data that it retrieves from Elasticsearch through the API.
- X-Pack enables additional features, such as Management of Logstash pipeline in kibana.

### Kibana 
  - An analytics & visualization platform - Elasticsearch dashboard - A web interface to the data that is stored within Elasticsearch.
  - We can plot website traffic based on visitors in real time
  - We can aggregate website traffic by browser and find out which browsers are important
  - We can create a dashboard for developers which monitors the number of appliction errors & API response times or a dashboard for sys admin to monitor the performance of servers, such as CPU and memory usage.
### Logstash
  - Used to process logs from applications and send them to Elasticsearch
  - It is a data processing pipeline. Data that Logstash receives, will be handled as events
    - Example : Log file entries, ecommerce orders, customers, chat messages, etc.
  - These events are then processed by Logstash and shipped off to one or more destinations.
    - Example : Elasticsearch, a Kafka queue, an e-mail message, or to an HTTP endpoint.
  - Logstash pipeline consists of three stages - inputs, filters, and outputs.
#### Input plugin - are about how Logstash received events
  - could be a file, meaning logstash will read events from a given file.
  - could also be that we are sending events to logstash over HTTP or 
  - could look up rows from a relational database
  - could listen to a Kafka queue
#### Filter plugin - are all about how Logstash should process
  - can parse CSV, XML, or JSON, for instance
  - can also do data enrichment. Example, looking up an IP address and resolving its geographical location or look up data in a relational database
#### Output plugin - Where we sent the processed events to - called as stashes
  - kafka, elastic search, http and so on
### X-pack
#### Authentication & Authorization feature
  - Integrates with authentication providers
  - Control permissions with fine-grained authorization
  - very useful as different people might need different privileges
#### Monitoring
  - Enables to monitor the performance of the Elastic Stack, being elasticsearch, logstash & kibana.<br> Specifically, you can see CPU & memory usage, disk space, & many other useful metrics
  - You can even set up alerting and be notified if something unusual happens.
  - Alerting is not limited to elastic stack, for eg you might want to be alerted if CPU or memory usage of web servers go through the roof or <br> if there is a spike in application errors.
#### Graph feature
  - Analyze the relationships in your data
    - Example showing/suggesting related products
  - Uncommonly common signals relevance
  - Only looking at popularity is misleading
  - Graph considers relevance with Elasticsearch
  - Graph integrate into applications with an API
#### Elasticsearch SQL 
  - Elasticsearch's queries are written in Query DSL
  - Send SQL over HTTP or through a JDBC driver
  - Elasticsearch translates the SQL internally
  - The translate API returns the corresponding Query DSL
### Beats
  - Beats is a collection of so-called data shippers that collect different kinds of data and serve different purposes.
#### Filebeat 
  - Used for collecting log files and sending the log entries off to either Logstash or Elasticsearch.
  - It ships with modules such as nginx, the Apache web server, or MySQL for common log files such as access logs or error logs.
#### Metricbeat
  - Collects system-level and/or service metrics.
  - can be used to collect CPU and memory usage for the OS and any services running on the system as well.
  - It ships with modules such as nginx, the Apache web server, or MySQL to monitor how they perform.

## Data flow between the application and elastic stack
### Communication
   - To do full text searches, the application communicates with Elasticsearch, can be done using HTTP but it is recommended to use official client libraries.
   - For example, if using elasticsearch SQL, the official JDBC driver can be used.
### How data gets into elastic search?
   - Data will be stored both in the DB and Elasticsearch
   - For a simple application, we can make use of Elasticsearch ingest node.
   - But for a microservice architecture or we have added two more web servers to handle more traffic, where events are processed in many different places, we should use Logstash pipeline to centralize the event processing.
     - We will be sending events from web servers to Logstash over HTTP.
     - Logstash will then process the events and send them off to elasticsearch.
### Significance of Logstash
Ideally, our web application would only be querying Elasticsearch for data, not modifying any data.
#### With Elasticsearch
- We could do this within our web application, but this has a few disadvantages.
- First of all, our business logic code will be cluttered with event processing, and this might increase response time unless we do this asynchronously.
- Nevertheless, it leads to more code, which is not directly related to business logic.
- Secondly, event processing will happen in multiple places.
#### With Beats
- As for Metricbeat, chances are that we don’t need to do any custom processing of the data, 
- But that might be the case for Filebeat if we are using custom logging formats, or 
- If we want to do some additional processing. If that’s the case - or if we just prefer to centralize things - we can definitely send
  the data to Logstash for processing. 
- Otherwise, it’s also okay to send data directly to Elasticsearch if we don’t mind that data is being added from multiple places.

## Elasticsearch & Kibana setup using docker
Using docker [refer](https://www.youtube.com/watch?v=UiJF5KFXE7U&list=PLc2rvfiptPSR9G6f2OsP308P0o3L6lw3g&index=7) 
#### How to disable security in elasticsearch.yml file?
- Bring up the containers by running the command <br>
`docker compose up`<br>
- It will ask for username and password. So we should disable it in elasticsearch.yml file
- Run the below commands one by one <br>
`docker exec --user='root' -it es8 //bin//bash` <br>
`apt-get update` <br>
`apt-get install nano` <br>
`cd config` <br>
`nano elasticsearch.yml`<br>
- Now add the below lines in the file<br>
`xpack.security.enabled: false` <br>
`xpack.security.enrollment.enabled: false` <br>
- Now stop the running containers. We should only stop the container, should't remove it. Otherwise the changes made to elasticsearch.yml will get lost. docker compose down will remove it, so don't use that.
- Then bring the containers up by using below command, <br>
`docker-compose up --build`








