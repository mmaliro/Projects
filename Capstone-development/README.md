# Capstone

1. Problem Statement

People everywhere struggle with tracking and changing their eating habits. Eating healthy, or even eating on a consistent schedule, is difficult. Having a ready-to-go schedule and easy ways to meal plan are key to the success of these changes. You don't even have to "diet" in the strictest sense of the word; even adjusting one meal a week can help you reach your goals!

Finding what works for you can be hard to do on your own. This application aims to provide users with a simple organizational structure and readily-available recipe list to begin this process.

2. Technical Solution

Create an application where users can schedule recipes for the week and log their meals.

Scenario 1: Hector will often go hours at a time before realizing that the last thing he ate was a granola bar in the morning. Using this web app allows him to schedule meals ahead of time and at least have a plan for what to eat throughout the day.

Scenario 2: Axel is a young boy who can't seem to eat enough. He needs help planning large meals multiple times throughout the day that will suit his needs and sophisticated taste. This app allows him to pick from a large variety of ready-to-go recipes so he doesn't have to come up with anything too complicated on his own.

3. Glossary

Week - A unit of time made up of 7 days, starts on Monday. Each user may have multiple weeks in their app.
Day - A unit of time (24 hrs) during which a user adds zero or more meals.
Meal - Each meal is composed of one or more recipes or one or more simple food items.
Recipe - A composition of two or more food items. There are default recipes built into the app, and then user-uploaded recipes.
Food - A single item of food (logged as type of food and amount).
Recipebook - a user's unique log of recipes. Each user's recipebook is loaded with default recipes at first, and then they can add their own recipes (or other user's recipes) to their personal recipebook at their convenience.
User - app user who is able to adjust the recipes within their own recipebook or upload recipes to the larger app database. Unable to delete or edit recipes from the larger database (except their own).
Administrator - app administrator who handles the default recipes of the general recipe database.

4. High Level Requirements
create week, add meals to days in week, edit meals, view week, view day, view meal, delete meals, delete week, add recipe to general recipe database


User - create week, add meals to days in week, edit meals, view week, view day, view meal, delete meals, delete week, add recipe to general recipe database
User recipebook functions - full CRUD (add, read, view, delete recipes)
Admin - full CRUD for general recipe database (not just recipebooks), delete user



