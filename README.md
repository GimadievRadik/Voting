### This is a Java web-application with REST API - voting system for deciding where to have lunch.

#### Overview

Readme contents API documentation and curl commands to get data for voting and vote.
 - 2 types of users: admin and regular users
 - Admin can input a restaurant and it's lunch menu of the day
 - Admin can update or delete restaurants dish or restaurant itself
 - Users can vote on which restaurant they want to have lunch at
 - Users can see restaurants daily ratings, their menu, dishes history, voting history 
 - Only one vote counted per user
 - If user votes again the same day:
     * If it is before 11:00 vote will be changed.
     * If it is after 11:00 vote will not be changed.
         
#### Authentication
Basic.
Regular user: --user user@yandex.ru:password
Admin: --user admin@gmail.com:admin

#### Responses
Content-Type is application/json, charset UTF-8

#### Commands
##### Get all restaurants with today's menu
`curl -X GET http://localhost:8080/voting/rest/user/restaurants --user user@yandex.ru:password`\
Request param: none\
Path variables: none\
Success Response: 200 OK.\
Content: restaurants array. Each object contains "id", "name" and "menu" - dishes array.\
Dish contains "id", "description", "date" - string in ISO_LOCAL_DATE format and "price"(floating point number with 2 digits after decimal separator).

##### Get restaurant by id 100006
`curl -X GET http://localhost:8080/voting/rest/user/restaurants/100006 --user user@yandex.ru:password`\
Request param: none\
Path variables: restaurant's id\
Success Response: 200 OK.\
Content: restaurant with "id" and "name".

##### Get restaurant 100006 with menu
`curl -X GET http://localhost:8080/voting/rest/user/restaurants/100006/with-menu --user user@yandex.ru:password`\
Request param: none\
Path variables: restaurant's id\
Success Response: 200 OK.\
Content: restaurant with "menu" - dishes array.

##### Get current 100006 restaurant's menu
`curl -X GET http://localhost:8080/voting/rest/user/restaurants/100006/dishes --user user@yandex.ru:password`\
Request param: none\
Path variables: restaurant's id\
Success Response: 200 OK.\
Content: just restaurants menu.

##### Get restaurants voting ratings
`curl -X GET http://localhost:8080/voting/rest/user/restaurants/votes --user user@yandex.ru:password`\
Request param: none\
Path variables: none\
Success Response: 200 OK.\
Content: array of restaurants with votes count - "id", "name", "rating".

##### Get current restaurant's dishes history after date (inclusive)
`curl -X GET http://localhost:8080/voting/rest/user/restaurants/100004/dishes/history?date=2020-04-07 --user user@yandex.ru:password`\
Request param: date string in ISO_LOCAL_DATE format\
Path variables: restaurant's id\
Success Response: 200 OK.\
Content: array of dishes

##### Get user's voting history after date (inclusive)
`curl -X GET http://localhost:8080/voting/rest/user/votes?date=2020-04-06 --user user@yandex.ru:password`\
Request param: date string in ISO_LOCAL_DATE format\
Path variables: none\
Success Response: 200 OK.\
Content: array of votes - "id", voting date-time string, format 'yyyy-MM-dd HH:mm', restaurant name, restaurant id.\

##### Add vote 
`curl -X POST http://localhost:8080/voting/rest/user/restaurants/100006/votes --user user@yandex.ru:password`
Request param: none\
Path variables: restaurant's id\
Success Response: 201 Created.\
Content: Vote object.\

##### Change vote
`curl -X PUT http://localhost:8080/voting/rest/user/restaurants/100006/votes --user user@yandex.ru:password`\
Request param: none\
Path variables: restaurant's id\
Success Response: 204 No content.\

##### Create new restaurant "Pretty place"
`curl -X POST http://localhost:8080/voting/rest/admin/restaurants?name=Pretty%20place --user admin@gmail.com:admin`\
Request param: restaurant's name\
Path variables: none\
Success Response: 201 Created.\
Content: "id" and restaurant's "name"\

##### Post menu to new restaurant
`curl -X POST http://localhost:8080/voting/rest/admin/restaurants/100023/dishes --user admin@gmail.com:admin -H 'Content-Type: application/json' --data-raw '`
```json
{
    "menu": [
        {
            "description": "Tea",
            "price": 23.02
        },
        {
            "description": "Soup",
            "price": 46.07
        }
    ]
}'
```
Request body: object with "menu" - array of dish transfer objects ("description" - dish's name and "price").\
Path variables: restaurant's id\
Success Response: 201 Created.\
Content: array of dishes\

##### Update dish
`curl -X PUT http://localhost:8080/voting/rest/admin/restaurants/100023/dishes/100024 --user admin@gmail.com:admin -H 'Content-Type: application/json' --data-raw '`
```json
{
  "id": 100024,
  "description": "new soup",
  "price": 55.55
}'
```
Request body: dish object - "id", "description" and "price"
Path variables: restaurant's id, dish's id\
Success Response: 201 Created.\
Content: array of dishes\