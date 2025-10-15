# 🍳 Programmed Household

A personal project designed to simplify **meal planning**, **inventory management**, and **recipe scheduling** — powered by **Java**, **Spring Boot**, **Elasticsearch**, and **Microservices**.

---

## 🚀 Backend Features

### Basic
- OAuth2 for **User Signup / Login / Logout**
- **Database Modelling**
- CRUD for:
  - User  
  - Recipe  
  - Ingredients  

### Recipe / Ingredients (3 days)
- **Autocomplete** feature for ingredients while creating a recipe  
  - Creates ingredient if it doesn’t exist  
  - Implemented using both **JPA** and **Elasticsearch**
- **Search Recipes** based on multiple filter parameters  
  - Built using both **JPA** and **Elasticsearch**
- Display:
  - **Menu for the day**
  - **Leftover food** whenever the user logs in
- User can view the **menu** by clicking any specific day in the **calendar**
- **Update ingredient quantity** when servings are changed *(Additional)*
- **Show recipes** based on available inventory *(Additional)*
- Prompt user to **update inventory weekly** *(Additional)*

---

### Schedule-On Date Specific Features (1 day)
- `ScheduleOn` date should exist for all recipes having a non-null `day` field
- Scheduler updates the **next available schedule-on date** daily for each recipe based on:
  - **Meal type** (Breakfast/Lunch/Dinner)
  - **Category** (Main/Snack/Soup/Juice)
  - **Day** (nullable — required if scheduling)
  - **Scheduled On date**
- Display **ingredients required** for the recipes between given dates and servings
- **Manual Rescheduling (Additional)**:
  - If a recipe is **not cooked**
  - If there are **leftovers**
  - Daily reminder prompts the user to mark leftovers
  - App automatically moves the recipe to the **next available date**

---

## ⚙️ Tech Stack

- **Elasticsearch**
- **Redis Cache** (store recently cooked recipes — last 2 weeks history)
- **Database** with **Spring Data JPA**
- **Microservices Architecture**
- **Docker / Kubernetes**
- **GCP / AWS**
- **Spring Boot**

---

## 📚 Learning & Development Plan

- **Microservices Course** – 40 hours (4 hrs/day)
- **Low-Level Design (LLD)** – 3 weeks
- **Java Multithreading** – [Codecademy Advanced Java](https://www.codecademy.com/learn/learn-advanced-java)
- **HTML / CSS / JS (React)** – ongoing *(optional)*

---

## 🧰 Tools

- **ER Diagram:** [SoftFactory ER Tool](https://console.softfactory.cloud/login)
- **API Documentation:** [Theneo](https://app.theneo.io/create-project)

---

## 🔗 References

### Base Code
- [Recipe App – yildizmy](https://github.com/yildizmy/recipe-app/tree/master)

### Data Modelling
- [Architecture Docs](https://github.com/yildizmy/recipe-app/blob/master/src/main/resources/docs/architecture.md)
- [Udemy Database Design Course](https://www.udemy.com/course/database-design/)

### User Registration and Login
- [Video 1](https://www.youtube.com/watch?v=7bIx4B5XhIA&t=5534s)
- [Video 2](https://www.youtube.com/watch?v=mxs_00KpUE4)

### JWT Authentication
- [Guide 1](https://www.youtube.com/watch?v=aOUL9Xr-U68)
- [Guide 2](https://www.youtube.com/watch?v=_zbfs0_4dbE)

### Security
- [Tutorial](https://www.youtube.com/watch?v=FLxDnRQonUA)

### OAuth2
- [Reference 1](https://www.youtube.com/watch?v=ouE3NuTzf20)
- [CRUD Example](https://github.com/caligula95/crud-app-example)
- [Reference 2](https://www.youtube.com/watch?v=40BxatEr5aE)
- [Spring Social Cloud Example](https://github.com/kanezi/spring-social-2-cloud)

### Spring + React
- [Full Stack Tutorial](https://www.youtube.com/watch?v=MTj7vk9F02A)
- [Hotel Booking Example](https://www.youtube.com/watch?v=0XJu4Nnl0Kc&t=301s)
- [Extended Guide](https://www.youtube.com/watch?v=7gZwWSsGIDE&t=38297s)

### JSON Field Storage
- [Save JSON as DB Field](https://www.youtube.com/watch?v=Yjsw2Q_Ncls)

### Spring Project Restructuring
- [GitHub Sample](https://github.com/SaiUpadhyayula/spring-boot-microservices/tree/22c49c4f8b89d2bd0f487ed92314ad9a34b8e04f)
- [6-hour Course](https://www.youtube.com/watch?v=mPPhcU7oWDU&list=PLSVW22jAG8pBnhAdq9S8BpLnZ0_jVBj0c&index=11&t=16254s)

### JPA and Search
- [JPA Playlist](https://www.youtube.com/playlist?list=PLVz2XdJiJQxxdOhu-xmEUYDzY_Pz8cRGa)
- [OneToMany Explained](https://medium.com/@burakkocakeu/in-spring-data-jpa-onetomany-what-are-these-fields-mappedby-fetch-cascade-and-orphanremoval-2655f4027c4f)
- [Advanced Search / Filtering](https://medium.com/@cmmapada/advanced-search-and-filtering-using-spring-data-jpa-specification-and-criteria-api-b6e8f891f2bf)
- [Code Example](https://github.com/carlmaps/AdvancedSearch/tree/master)
- [YouTube Playlist](https://www.youtube.com/playlist?list=PLoyb0HJlmv_lvsJv02JKe7hz45oB3qlgX)
- [Full Text Search in MySQL](https://www.codejava.net/frameworks/spring-boot/full-text-search-with-mysql)

### Spring + Elasticsearch
- [Elastic Recipe Example](https://github.com/elastic/examples/tree/master/Search/recipe_search_java)
- [Tutorial 1](https://www.youtube.com/watch?v=4-xhVF9hT_s)
- [Tutorial 2](https://www.youtube.com/watch?v=0hq3_JpTAbk)
- [GitHub Examples](https://github.com/madhusudhankonda/elasticsearch-clients/tree/main)

### Elasticsearch Test Cases
- [Sample Tests](https://github.com/spinscale/elasticsearch-rest-client-samples/tree/main/src/test)

---

## 🔄 Data Sync Between Database and Elasticsearch

1. **Configure Debezium with MySQL:** Capture DB changes and publish to Kafka  
2. **Set Up Kafka Connect:** Consume Kafka messages and index into Elasticsearch  
3. **Configure Elasticsearch Sink Connector:** Map MySQL data to Elasticsearch schema  
4. **Run the Pipeline:**  
   - Debezium → Kafka → Elasticsearch  
   - Changes from MySQL are indexed in Elasticsearch in near real time

---

## 🧪 Testing

- **JUnit** and **Testcontainers**  
  - [Docs](https://java.testcontainers.org/)

---

## 🧠 SQL Learning Resources

### Basics
- [Codecademy SQL](https://www.codecademy.com/learn/learn-sql) – 5 hrs

### Intermediate
- [Multiple Tables & Subqueries](https://www.youtube.com/watch?v=nJIEIzF7tDw)
- [SQL Table Transformation](https://www.codecademy.com/learn/sql-table-transformation) – 2 hrs

### Advanced
- [Window Functions](https://www.youtube.com/watch?v=Ww71knvhQ-s) – 25 mins  
- [CTE](https://www.youtube.com/watch?v=QNfnuK-1YYY) – 25 mins  
- [Stored Procedures](https://www.youtube.com/watch?v=yLR1w4tZ36I&t=187s) – 1 hr  

### Channels
- [TechTFQ](https://www.youtube.com/@techTFQ)
- [PlanetScale MySQL Course](https://planetscale.com/learn/courses/mysql-for-developers/introduction/course-introduction)

---

## 🧩 APIs

### Shopping List API
- Fetch upcoming week’s **special occasions**
- Get shopping list between given dates or `prep_time`
- Aggregate ingredient’s `requiredQty` across different recipes from **Elasticsearch**
- Fetch each item’s `stockQty` from **Database**
- Group items by type  
- Generate Excel report with:
  - `ingredientName`
  - `stockQty`
  - `requiredQty`
- Email the Excel file

### Essential Items API
- Fetch essential item `stockQty` from DB
- Cache results for quick response

### Schedule Date API

#### Initial Scheduling
- Populate initial schedule dates for recipes based on **start date**
- If `startDt` is null → use **today**
- Based on `prep_time` & `meal`, assign future dates having the same day

#### Subsequent Scheduling
- Calculate next available date by:
  - Taking **max scheduled date** of recipes with same day and meal
  - Apply for any new recipe with `active = true`

---

✅ **End of Document**
