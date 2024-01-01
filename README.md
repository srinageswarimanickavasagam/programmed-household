# App Data Modeling

Below are the key tables and their relationships in the database.

## Tables

1. **appuser**
   - `user_id` (Primary Key)
   - `created_at`
   - `email`
   - `name`
   - `provider`

2. **category**
   - `category_id` (Primary Key)
   - `day`
   - `difficulty`
   - `is_active`
   - `meal`
   - `name` (Unique)
   - `sidedish`

3. **itemtype**
   - `type_id` (Primary Key)
   - `type`

4. **item**
   - `item_id` (Primary Key)
   - `amount`
   - `is_essential`
   - `name` (Unique)
   - `stock_qty`
   - `stocked_dt`
   - `unit`
   - `type_id` (Foreign Key)

5. **recipe**
   - `recipe_id` (Primary Key)
   - `cook_time`
   - `cuisine`
   - `description`
   - `health_label`
   - `instructions`
   - `is_active`
   - `prep_time`
   - `servings`
   - `title`
   - `user_id` (Foreign Key)
   - `category_id` (Foreign Key)

6. **recipe_item**
   - `item_id` (Foreign Key)
   - `recipe_id` (Foreign Key)
   - `item_qty`
   - `unit`
   - Primary Key: (`item_id`, `recipe_id`)

7. **schedule**
   - `schedule_id` (Primary Key)
   - `scheduled_dt`
   - `recipe_id` (Foreign Key)

## Relationships

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

- **schedule Table:**
  - Relationship: Schedules link specific recipes to dates and times.
  - Connection: `recipe_id` associates a schedule with a particular recipe.

These relationships help users organize recipes into categories, associate recipes with specific items, and schedule cooking sessions. They also facilitate the categorization of items and the management of inventory.
