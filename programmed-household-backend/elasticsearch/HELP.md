## Table of Contents

1. [ElasticSearch](#elasticsearch)
2. [Architecture](#architecture)
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

## Elasticsearch

- A search engine written in Java, built on Apache lucene

## Architecture

`Clusters > Nodes > Indices > Shards > Documents`

### Node

- A node refers to a running instance of Elasticsearch, which can be running on a physical or virtual machine, or within
  a Docker container, for instance.
- To ensure that we can store many terabytes of data if we need to, we can run as many nodes as we want.
- Each node will then store a part of our data.
- A node refers to an instance of Elasticsearch and not a machine, so you can run any number of nodes on the same
  machine.
- This means that on your development machine, you can start up five nodes if you want to, without having to deal with
  virtual machines or containers.
- That being said, you should typically separate things in a production environment so that each node runs on a
  dedicated machine, a virtual machine, or within a container.
- We can query node details in kibana console,
    - we can get the node details by <br>
      `GET /_nodes`

### Cluster

- An Elasticsearch cluster is a collection of related nodes, which are responsible for storing data.
- We can have many clusters if we want to, but one is usually enough.
- Elasticsearch cluster exposes a REST API, which is what we will be communicating with.
- We can query cluster details in kibana console,
    - we can get the cluster health by <br>
      `GET /_cluster/health`

### Document

- Data is stored as documents (similar to rows in relational databases)
- A document's data is separated into fields (similar to columns in relational databases)
- A document is a JSON object
- These documents are created using rest APIs

### Index

- Every document within Elasticsearch, is stored within an index.
- When you index a document, the original JSON object that you sent to Elasticsearch is stored along with some metadata
  that Elasticsearch uses internally.
- Index is a way to logically group together related documents.
- An index may contain as many documents as you want, so there is no hard limit.
- When we get to searching for data, you will see that we specify the index that we want to search for documents,
  meaning that search queries are actually run against indices.
- Commands <br>
  `PUT /pages`

### Shards

- Sharding is a way to sub-divide an index into smaller pieces, each being a shard which is an Apache lucene index.
- Sharding is done at the index level, and not at the cluster or node level.

#### Why Sharding?

- Sharding enables horizontal scaling, to be able to store more documents.
- Improved performance through parallelization of queries increases the throughput of index

#### How to configure?

- By default, indices in elasticsearch > 7.0.0 created with single shards.
- To increase the number of shards, Split API is used. But this will again create a new index anyways.
- To reduce the number of shards, there is a Shrink API.
- Number of shards required depends on number of nodes within the cluster, the capacity of the nodes, the number of
  indices and their sizes, the number of queries run against the indices, etc.

### Replicas

- Replication works by creating copies of each of the shards that an index contains.
- These copies are referred to as replicas or replica shards.
- Replica shards are never stored on the same node as their primary shard, so that if one node disappears, there will
  always be at least one copy of a shard's data available on a different node.

#### Why Replicas?

- What happens if the node where a shard is stored breaks down, i.e. has a disk failure? the data is lost, since we have
  no copy of it, since a hardware can fail at any given time.
- So we need some fault tolerance and failover mechanism, which is where replication comes into the picture.

#### How to configure?

- A shard that has been replicated one or more times, is referred to as a primary shard.
- A primary shard and its replica shards, are referred to as a replication group.
- Replica shards are a complete copy of a shard that can serve search requests just like the primary shard.
- When creating an index, we can choose how many replicas of each shard that we want, with one being the default value.
- So an index contains shards, which in turn may contain replica shards.

#### Snapshots

- Replication is indeed a way of preventing data loss, but replication only works with "live data."
- Snapshots, on the other hand, enable you to export the current state of the cluster (or specific indices) to a file.
- This file can then be used to restore the state of the cluster or indices to that state.
- Example
    - For instance, imagine that we have been tasked to restructure how millions of documents are stored within an
      index.
    - To be sure that we can recover from any implications, we snapshot the index before running the queries.
    - When running the queries, the documents got messed up, and we need to revert the changes to get things back to a
      working state.
    - Replication cannot help with that, because replication just ensures that we don't lose our latest data, which has
      already been modified in this example.
    - Instead, we need to revert the state of the index to the snapshot that we took.

### Replication vs Snapshot

- Snapshots are commonly used for daily backups and manual snapshots may be taken before applying changes to data, to
  roll back the changes in case something goes wrong.
- Replication just ensures that indices can recover from a node failure and keep serving requests, as if nothing had
  happened.
- Apart from preventing data loss, it can be used to increase the throughput of a given index.

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

- An analytics & visualization platform - Elasticsearch dashboard - A web interface to the data that is stored within
  Elasticsearch.
- We can plot website traffic based on visitors in real time
- We can aggregate website traffic by browser and find out which browsers are important
- We can create a dashboard for developers which monitors the number of appliction errors & API response times or a
  dashboard for sys admin to monitor the performance of servers, such as CPU and memory usage.

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
- can also do data enrichment. Example, looking up an IP address and resolving its geographical location or look up data
  in a relational database

#### Output plugin - Where we sent the processed events to - called as stashes

- kafka, elastic search, http and so on

### X-pack

#### Authentication & Authorization feature

- Integrates with authentication providers
- Control permissions with fine-grained authorization
- very useful as different people might need different privileges

#### Monitoring

- Enables to monitor the performance of the Elastic Stack, being elasticsearch, logstash & kibana.<br> Specifically, you
  can see CPU & memory usage, disk space, & many other useful metrics
- You can even set up alerting and be notified if something unusual happens.
- Alerting is not limited to elastic stack, for eg you might want to be alerted if CPU or memory usage of web servers go
  through the roof or <br> if there is a spike in application errors.

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
- It ships with modules such as nginx, the Apache web server, or MySQL for common log files such as access logs or error
  logs.

#### Metricbeat

- Collects system-level and/or service metrics.
- can be used to collect CPU and memory usage for the OS and any services running on the system as well.
- It ships with modules such as nginx, the Apache web server, or MySQL to monitor how they perform.

## Data flow between the application and elastic stack

### Communication

- To do full text searches, the application communicates with Elasticsearch, can be done using HTTP but it is
  recommended to use official client libraries.
- For example, if using elasticsearch SQL, the official JDBC driver can be used.

### How data gets into elastic search?

- Data will be stored both in the DB and Elasticsearch
- For a simple application, we can make use of Elasticsearch ingest node.
- But for a microservice architecture or we have added two more web servers to handle more traffic, where events are
  processed in many different places, we should use Logstash pipeline to centralize the event processing.
    - We will be sending events from web servers to Logstash over HTTP.
    - Logstash will then process the events and send them off to elasticsearch.

### Significance of Logstash

Ideally, our web application would only be querying Elasticsearch for data, not modifying any data.

#### With Elasticsearch

- We could do this within our web application, but this has a few disadvantages.
- First of all, our business logic code will be cluttered with event processing, and this might increase response time
  unless we do this asynchronously.
- Nevertheless, it leads to more code, which is not directly related to business logic.
- Secondly, event processing will happen in multiple places.

#### With Beats

- As for Metricbeat, chances are that we don’t need to do any custom processing of the data,
- But that might be the case for Filebeat if we are using custom logging formats, or
- If we want to do some additional processing. If that’s the case - or if we just prefer to centralize things - we can
  definitely send
  the data to Logstash for processing.
- Otherwise, it’s also okay to send data directly to Elasticsearch if we don’t mind that data is being added from
  multiple places.

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
- Now stop the running containers. We should only stop the container, should't remove it. Otherwise the changes made to
  elasticsearch.yml will get lost. docker compose down will remove it, so don't use that.
- Then bring the containers up by using below command, <br>
  `docker-compose up --build`








