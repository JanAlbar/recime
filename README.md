# Recime Recipe API
A Spring Boot REST API for managing recipes including **creating**, **reading**, **updating**, **deleting**, and **filtering** recipes.

## Requirements
Before running locally, make sure you have:

- **Java 17+**
- **PostgreSQL 14+**
- **Maven 3.9+**
- An API testing tool like **Postman**

## Running Locally
1. Clone the repository.
2. Update your `application.properties` file with values that matches your database configurations. See sample below:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/recime
    spring.datasource.username=recime_user
    spring.datasource.password=recime_password
    ```
3. Build the project.
    ```bash
    mvn clean install
    ```
4. Run the application.
    ```bash
    mvn spring-boot:run
    ```
5. Call API using base url: http://localhost:8080/recipe

## API Endpoints and Features

| Method   | Endpoint                | Description                                | Request Body Example                            |
| -------- | ----------------------- | ------------------------------------------ |-------------------------------------------------|
| `POST`   | `/recipe`               | Create a new recipe                        | [Create Recipe](#create-recipe-payload-example) |
| `GET`    | `/recipe`               | Get all recipes                            | –                                               |
| `GET`    | `/recipe/title/{title}` | Search recipes by title (case-insensitive) | –                                               |
| `GET`    | `/recipe/filter`        | Filter recipes based on criteria           | [Filter Recipe](#filter-recipe-payload-example) |
| `PUT`    | `/recipe/{id}`          | Update an existing recipe                  | [Update Recipe](#update-recipe-payload-example) |
| `DELETE` | `/recipe/{id}`          | Delete a recipe                            | –                                               |


---
### Create Recipe Payload Example
```json
{
  "title": "Classic Cheeseburger",
  "description": "A juicy grilled beef patty topped with melted cheese, fresh vegetables, and condiments in a toasted bun.",
  "serving": 2,
  "ingredients": [
    {
      "name": "Ground Beef Patty",
      "quantity": "2 x 150g",
      "nonVegetarian": true
    },
    {
      "name": "Cheddar Cheese Slice",
      "quantity": "2 slices"
    },
    {
      "name": "Burger Buns",
      "quantity": "2"
    }
  ],
  "instructions": [
    {
      "step": 1,
      "description": "Preheat grill or skillet to medium-high heat."
    },
    {
      "step": 2,
      "description": "Season beef patties with salt and pepper, then grill for 3–4 minutes per side."
    }
  ]
}
```
---
### Filter Recipe Payload Example

```json
{
    "isVegetarianFriendly": false,
    "minServing": 1,
    "maxServing": 1,
    "includedIngredients": ["Ground Beef Patty", "Cheddar Cheese Slice"],
    "excludedIngredients": [],
    "instruction": "Season beef patties"
}
```
---
### Update Recipe Payload Example

```json
{
  "title": "Double Patty Burger",
  "serving": 4,
  "ingredients": [
    {
      "name": "Ground Beef Patty",
      "quantity": "4 x 150g",
      "nonVegetarian": true
    },
    {
      "name": "Cheddar Cheese Slice",
      "quantity": "2 slices"
    },
    {
      "name": "Burger Buns",
      "quantity": "2"
    }
  ]
}
```
