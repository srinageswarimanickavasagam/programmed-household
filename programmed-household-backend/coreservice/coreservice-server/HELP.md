# TO DO

## Add flyway

## File name syntax

https://blog.tericcabrel.com/handle-database-migrations-in-a-springboot-application-with-flyway/
![Local Image](src/main/resources/images/flyway_filename.png)

- Part 1: It is the letter "v" in uppercase. The name always starts with this letter.
- Part 2: It is the migration version; it can be 1, 001, 1.2.3, 2021.09.24.12.55.32, ... you got it.
- Part 3: It is the two underscores (_)
- Part 4: The description of the migration; you can separate words with an underscore or a space.
- Part 5: It is the extension of the file .sql