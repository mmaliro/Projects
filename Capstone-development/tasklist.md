# Fresh Feast capstone

_Task list w/ estimated time per item_

## General

- [x] Research Lombok
  - [x] Lesson: Project Lombok (in Dev10 Teachable)
- [x] Research Spring Data JPA
- [x] Research CSS frameworks (Tailwind, Bulma, etc.)
- [x] Security Lesson

## Database

- [x] Draw out schema in DB Designer (1 hour [per person])
- [x] Create database using DDL
- [x] Set up and test the backend
  - [x] Create the database
  - [x] Start the API and send a test HTTP request

## Java API

- [x] Create Maven project
  - [x] Adding dependencies to pom.xml
- [x] Outlining models
  - [x] AppRole
  - [x] AppUSer
  - [x] Food
  - [x] MEal
  - [x] Recipe
  - [x] Tags
- [x] Repository/data layer
  - [x] implement JPA repositories
    - [x] AppUserRepository
    - [x] FoodRepository
    - [x] MealRepository
    - [x] Recipe Repository
    - [x] TagsRepository
  - [x] implement annotations for each model
  - [x] define entity relationships in each model
  - [x] test repository layer
    - [x] Fix Bugs with repository Layer
- [x] Service/domain layer & validations for each class

  - [x] AppRolesService
  - [x] FoodService
  - [x] Meal Service
  - [x] REcipe Service
  - [x] Tags Service
  - [x] Test Service Layer
    - [x] Fix security interference with repository tests

- [x] Controllers/UI layer
  - [x] Create Error Mapper
  - [x] AppUSer Controller
  - [x] Food Controller
  - [x] Meal Controller
  - [x] Recipe Controller
  - [x] Tags Controller
- [ ] Back End Security
  - [x] create AppUser Service
  - [x] create Security Config
  - [x] create JWT Request Filter
  - [x] create JWT
  - [x]

## React App

- [x] Set up React App
  - [x] clean out unused files
- [x] Set up pertinent routes
  - [x] home
  - [x] contact
  - [x] recipes
    - [] have back end populate
  - [x] recipes add form
    - [] populate form appropriately
  - [x] recipes delete
  - [x] login
  - [x] NavBar

## Wire Frame Layout

## NavBar

- [] home page
- [] login
- [] about
- [] contact
- [] all Recipes
- [] user profile/user recipes

## Home

- [] splash page image (self changing carousel ?)
- [] Title

## UserProfile/User Recipe Book

- [] Profile Picture ?
- [] Meals
- [] individual Recipes
- [] make individual recipe book searchable
- [] Button to add recipe
- [] edit recipe
- [] Button to delete recipe from individual recipe book

## Site Recipe List

- [] How are recipes listed (alphabetically, by tag?)
- [] search by name
- [] search by tag
- [] recipes presented as a grid of cards ?
- [] how many recipes presented at once? do we do pages?

## Recipe Card

- [] url based image
- [] title
- [] click on a card to go to its detail

## Recipe Details

- [] Title
  - [] serving size
  - [] prep time
  - [] cook time
  - [] tags
- [] Ingrediants
- [] image
- [] instructions

## Meal Card

- []

## Add a Recipe Form

- [] Complete Form
  - [] Have all fields represented
  - [] cancel button
  - [] submit button

## Admin Fucntionality

- [] delete user
- [] view user list
- [] edit user
- [] delete recipe
- [] delete food

## Add DML data

- [] recipes
- [] food
- [] users
- [] roles

## Implement Front End Security

- [] login
  - [] login form
    - [] username
    - [] password
    - [] login button
    - [] cancel button
    - [] give generic fail message if credentials not good
- []
