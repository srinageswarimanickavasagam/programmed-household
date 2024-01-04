# Programmed Household: Personal Meal Planner and Inventory Manager

Welcome to my Programmed Household App! 🍲📋

## Why Programmed Household?

Meet Programmed Household—an app that will make my daily life easier. It will help me plan meals, keep track of groceries, and make life smoother. Programmed Household is not just an app—it's a project that will allow me to apply and experiment with all the learnings acquired throughout my tech career. From defining the project scope to choosing a tech stack, developing frontend and backend components, and deploying the app. It's a journey of continuous learning and practical application, all personalized for my use!

## Key Features

1. **Meal Planning:** Schedule meals efficiently based on the categories configured for a specific weekday without the need for intricate scheduling.
2. **Inventory Tracking:** Keep an eye on the fridge and pantry, minimizing disruptions to the tech-focused routine.
3. **Recipe Suggestions:** Recipe suggestions based on available ingredients, eliminating the need for last-minute grocery runs!
4. **Smart Notifications:** Receive reminders about expiring items based on stocked date, ensuring optimization of grocery purchases.

## App Data Modeling

Below are the key tables and their relationships in the database.

### Tables

#### `appuser`
| Column      | Data Type   |
|-------------|-------------|
| user_id (PK)| bigint      |
| created_at  | datetime(6) |
| email       | varchar(255)|
| name        | varchar(255)|
| provider    | varchar(255)|

#### `category`
| Column         | Data Type   |
|----------------|-------------|
| category_id(PK)| bigint      |
| day            | varchar(255)|
| difficulty     | varchar(255)|
| meal           | varchar(255)|
| name (unique)  | varchar(50) |
| sidedish       | bit(1)      |

#### `itemtype`
| Column      | Data Type   |
|-------------|-------------|
| type_id (PK)| bigint      |
| type        | varchar(255)|

#### `item`
| Column         | Data Type   |
|----------------|-------------|
| id (PK)        | bigint      |
| amount         | float       |
| is_essential   | bit(1)      |
| name (unique)  | varchar(50) |
| item_stock_qty | int         |
| stocked_dt     | datetime(6) |
| unit           | varchar(255)|
| type_id (FK)   | bigint      |

#### `recipe`
| Column         | Data Type   |
|----------------|-------------|
| id (PK)        | bigint      |
| cook_time      | int         |
| cuisine        | varchar(255)|
| description    | varchar(100)|
| health_label   | varchar(255)|
| instructions   | tinytext    |
| is_active      | bit(1)      |
| prep_time      | int         |
| servings       | int         |
| title          | varchar(50) |
| user_id (FK)   | bigint      |
| category_id(FK)| bigint      |

#### `recipe_item`
| Column    | Data Type   |
|-----------|-------------|
| item_id (FK)| bigint     |
| recipe_id (FK)| bigint    |
| item_qty   | int         |
| unit      | varchar(255)|

*Composite Primary Key: (item_id, recipe_id)*

### Relationships

- **appuser Table:**
    - Relationship: Users (appuser) can create multiple recipes.
    - Connection: `user_id` is a unique identifier for each user.

- **category Table:**
    - Relationship: Categories group multiple recipes together.
    - Connection: `category_id` uniquely identifies each category.

- **itemtype Table:**
    - Relationship: Item types categorize various food items.
    - Connection: `type_id` uniquely identifies each item type.

- **item Table:**
    - Relationship 1: Items are associated with specific recipes.
    - Connection 1: `item_id` uniquely identifies each item.
    - Relationship 2: Items are categorized under specific item types.
    - Connection 2: `type_id` connects items to their types.

- **recipe Table:**
    - Relationship 1: Recipes belong to a specific category.
    - Connection 1: `category_id` links recipes to categories.
    - Relationship 2: Recipes include various items.
    - Connection 2: `recipe_id` uniquely identifies each recipe.

- **recipe_item Table:**
    - Relationship 1: Connects recipes to their required items.
    - Connection 1: `recipe_id` links to the recipe.
    - Relationship 2: Specifies the quantity and unit of each item in a recipe.
    - Connection 2: `item_id` links to the item.

These relationships help me organize recipes into categories, associate recipes with specific items, and schedule cooking sessions. They also facilitate the categorization of items and the management of inventory.