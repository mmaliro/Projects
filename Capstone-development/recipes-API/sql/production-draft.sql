drop database if exists recipes;
create database recipes;
use recipes;

-- DDL (table creation)
create table app_user (
	app_user_id int not null primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    enabled bit not null default 1,
    first_name varchar(75) not null,
    last_name varchar(75) not null,
    email varchar(320) not null unique,
    dob date not null
);

create table app_role (
	app_role_id int not null primary key auto_increment,
    role_name varchar(50) not null unique
);

create table recipe (
	recipe_id int not null primary key auto_increment,
    app_user_id int null,
    title varchar(100) not null,
    instructions varchar(2048) not null,
    recipe_description varchar(500) null,
    cook_time integer not null,
    prep_time integer not null,
    calories integer null,
    servings integer not null,
    image_url varchar(2048) null,
    constraint fk_recipe_app_user_id
		foreign key (app_user_id)
        references app_user(app_user_id)
);

create table tags (
	tag_id int not null primary key auto_increment,
    tag_name varchar(50) not null unique,
    default_image varchar(2048) null
);

create table food (
	food_id int not null primary key auto_increment,
    food_name varchar(50) not null unique
);

create table meal (
	meal_id int not null primary key auto_increment,
    app_user_id int not null,
    `date` date not null,
    `time` time(0) not null,
    meal_category varchar(50) null,
    constraint fk_meal_app_user_id
		foreign key (app_user_id)
        references app_user(app_user_id)
);

create table app_user_role (
	app_user_id int not null,
    app_role_id int not null default 1,
    constraint fk_app_user_role_app_user_id
		foreign key (app_user_id)
        references app_user(app_user_id),
	constraint fk_app_user_role_app_role_id
		foreign key (app_role_id)
        references app_role(app_role_id)
);

create table recipebook (
	app_user_id int not null,
    recipe_id int not null,
    constraint pk_recipebook
		primary key (app_user_id, recipe_id),
    constraint fk_recipebook_app_user_id
		foreign key (app_user_id)
        references app_user(app_user_id),
	constraint fk_recipebook_recipe_id
		foreign key (recipe_id)
        references recipe(recipe_id)
);

create table ingredients (
	ingredient_id int not null primary key auto_increment,
	recipe_id int not null,
    food_id int not null,
	amount decimal(7,3) not null,
    measurement_unit varchar(100) null,
    constraint fk_ingredients_recipe_id
		foreign key (recipe_id)
        references recipe(recipe_id),
	constraint fk_ingredients_food_id
		foreign key (food_id)
        references food(food_id)
);

create table meal_components (
	meal_id int not null,
    recipe_id int null,
    food_id int null,
	amount decimal(7,3) not null,
    measurement_unit varchar(100) null,
    constraint fk_meal_components_meal_id
		foreign key (meal_id)
        references meal(meal_id),
	constraint fk_meal_components_recipe_id
		foreign key (recipe_id)
        references recipe(recipe_id),
	constraint fk_meal_components_food_id
		foreign key (food_id)
		references food(food_id),
	constraint uq_meal_id_recipe_id
		unique (meal_id, recipe_id),
	constraint uq_meal_id_food_id
		unique (meal_id, food_id)
);

create table recipe_tags (
	recipe_id int not null,
    tag_id int not null,
    constraint fk_recipe_tags_recipe_id
		foreign key (recipe_id)
        references recipe(recipe_id),
	constraint fk_recipe_tags_tag_id
		foreign key (tag_id)
        references tags(tag_id)
);

-- DML for our own production purposes
        
insert into recipe (title, recipe_description, instructions, prep_time, cook_time, calories, servings, image_url)
values
	("Scrambled Eggs",
	"The definition of simplicity: perfectly pillowy scrambled eggs.",
    "1. In a medium mixing bowl, aggressively whisk together the eggs, milk, and salt until the mixture is uniform in color and texture, and is light and foamy, without any separate streaks of yolk or whites.
	2. Melt the butter in a small nonstick pan over medium heat, until the butter coats the whole pan and just starts to foam.
    3. Add the eggs to the center of the pan and immediately reduce the heat to medium-low.
    4. Wait for the edges to just barely start to set, then using a rubber spatula, gently push the eggs from one end of the pan to the other. Continue this process, pausing in between swipes to allow the uncooked egg to settle on the warm pan and cook, gently pushing the liquid to form the curds. 
    5. When the eggs are mostly cooked, with big pillow-y folds, but still look pretty wet, slowly fold the eggs into itself just a couple times, bringing them together.
    6. Remove from the heat when the eggs still shimmer with some moisture.
    7. Transfer to serving plates. Finish with some pepper and salt.",
    3, 3, 251, 2,
    "https://thecozycook.com/wp-content/uploads/2021/02/Fluffy-Scrambled-Eggs-4.jpg"),
    ("French Toast",
    "A Sunday favorite that's great for any day of the week - flawless and fluffy French toast.",
    "1. Whisk milk, eggs, vanilla, cinnamon, and salt together in a shallow bowl.
    2. Lightly butter a griddle or skillet and heat over medium-high heat.
    3. Dunk bread in the egg mixture, soaking both sides.
    4. Transfer to the hot skillet and cook until golden, 3 to 4 minutes per side.
    5. Serve hot; top with butter, syrup, and extra cinnamon if desired.",
    5, 10, 240, 3,
    "https://foodwithfeeling.com/wp-content/uploads/2022/07/cinnamon-french-toast-9.jpg");
    
insert into food (food_name)
values
	('egg'),
    ('salt'),
    ('butter'),
    ('pepper'),
    ('milk'),
    ('vanilla extract'),
    ('cinnamon'),
    ('bread');
    
insert into ingredients (recipe_id, food_id, amount, measurement_unit)
values
	(1, 1, 4, null),
    (1, 5, 0.25, 'cup'),
    (1, 2, 0.25, 'tsp'),
    (1, 3, 1, 'tbsp'),
    (1, 4, 0.25, 'tsp'),
	(2, 5, 0.66, 'cup'),
    (2, 1, 2, null),
    (2, 6, 1, 'tsp'),
    (2, 7, 0.25, 'tsp'),
    (2, 2, 0.25, 'tsp'),
    (2, 8, 6, 'slices'),
    (2, 3, 1, 'tbsp');
    
    
insert into recipe (title, recipe_description, instructions, prep_time, cook_time, calories, servings, image_url)
values
	("Easiest Chicken Salad Sandwich",
	"A simple base for chicken salad, ready for any desired add-ins like diced onions or celery.",
    "1. Cook chicken breast using your desired method.
    2. Shred chicken with fork.
    3. Add pickle relish. Slowly add mayonnaise, stirring to combine. Be careful not to add more mayonnaise than is required to bind the chicken salad together or it could overpower the dish.
    4. Spread on one slice of bread and top with another slice to create your sandwich.",
    5, 0, 245, 4,
    "https://dinnerthendessert.com/wp-content/uploads/2019/01/Classic-Chicken-Salad.jpg"),
    ("Chicken Caprese Salad",
    "A fresh and delicious caprese salad that comes together in minutes.",
    "1. Halve cherry tomatoes.
	2. Thinly slice the basil leaves.
	3. Add the tomatoes, basil, and mozzarella balls to a medium bowl.
	4. Top with balsamic vinegar.
	5. Stir to combine, then serve.",
    5, 0, 358, 4,
    "https://www.tastingtable.com/img/gallery/cherry-tomato-caprese-salad-recipe/cherry-tomato-caprese-salad-1644963111.jpg");
    
insert into food (food_name)
values
    ('chicken breast'), -- 9
    ('mayonnaise'),
    ('sweet pickle relish'),
    ('cherry tomatoes'),
    ('fresh basil'),
    ('mozarella balls'),
    ('balsamic vinegar');
    
insert into ingredients (recipe_id, food_id, amount, measurement_unit)
values
	(3, 9, 12, 'oz'),
    (3, 10, 0.25, 'cup'),
    (3, 11, 2, 'tbsp'),
    (4, 12, 10, 'oz'),
    (4, 13, .75, 'oz'),
    (4, 14, 16, 'oz'),
    (4, 15, 1, 'tbsp');
    
    
insert into recipe (title, recipe_description, instructions, prep_time, cook_time, calories, servings, image_url)
values
	("Easy Homemade Chicken Alfredo",
	"A staple Italian comfort food that's extra impressive here since the sauce is made from scratch!",
    "1. Cut the chicken breast into chunks or cubes. 
    2. In a pan over medium-high heat, melt 2 tbsp of butter, then add the chicken breast.
    3. Season with 1/2 tsp each of salt, pepper, oregano, and basil. Cook 8-10 minutes or until chicken is fully cooked. Remove from heat and set chicken aside.
	4. In the same pan over medium heat, melt 2 more tbsp of butter. While the butter is melting, mince your garlic. Once butter is melted, add the minced garlic. Cook until the garlic begins to soften.
	5. Add half of the flour to the garlic and butter, stirring until incorporated. Then add the rest of the flour and stir.
    6. Pour in the milk a little bit at a time, stirring well in between, until fully incorporated and sauce begins to thicken.
    7. Season with the rest of the salt, pepper, oregano, and basil, and stir well to incorporate.
	8. Add 1/2 cup of parmesan cheese and stir until melted.
	9. Pour the sauce over cooked pasta of choice, add the chicken and mix well.
	10. Add any extra parmesan or spices you desire. Mix well, and enjoy!",
    15, 20, 464, 6,
    "https://img.buzzfeed.com/thumbnailer-prod-us-east-1/video-api/assets/9768.jpg"),
    ("All-American Cheeseburger",
    "Just about everyone loves a good cheeseburger! These patties are versatile and easy to make on the grill or stovetop.",
    "1. Set out a large mixing bowl. Add in the ground beef, crushed crackers or panko crumbs, egg, Worcestershire sauce, milk, salt, garlic powder, onion powder, and pepper. Mix by hand until the meat mixture is smooth, but stop once the mixture looks even. (Overmixing can create a dense heavy texture.)
	2. Press the meat down in the bowl into an even disk. Use a knife to cut and divide the hamburger patty mixture. You can divide into 6 grill or skillet patties that are about 1/3 of a pound each, or 12 thin griddle patties.
	3. Set out a baking sheet, lined with wax paper or foil, to hold the patties. One at a time, gather the patty mix and press firmly into patties. Shape them just slightly larger than the buns you plan to use, to account for shrinkage during cooking. Set the patties on the baking sheet. Use a spoon to press a dent in the center of each patty so they don't puff up as they cook. If you need to stack the patties separate them with a sheet of wax paper.
    4. Preheat the grill or a skillet to medium heat. (Approximately 350-400 degrees F.)
    5. If you chose to make thicker patties, grill or fry each patty for 3-4 minutes per side. If you opted for thin patties, cook them on the griddle for 2 minutes per side.
	6. Stack the hot patties on burger buns, and top with cheese. Let cheese melt onto the patty until it doesn't slide off easily, and then top with lettuce and slices of tomatoes. Serve warm.",
    15, 8, 430, 6,
    "https://food.fnr.sndimg.com/content/dam/images/food/fullset/2004/2/25/0/bw2b07_hambugers1.jpg.rend.hgtvcom.826.620.suffix/1558017418187.jpeg");
    
insert into food (food_name)
values
	('dried oregano'), -- 15
    ('dried basil'),
    ('cooked pasta'),
    ('shredded parmesan cheese'),
    ('garlic'),
    ('all-purpose flour'), -- 20
    ('ground beef'),
    ('breadcrumbs'),
    ('Worcestorshire sauce'),
    ('garlic powder'),
    ('onion powder');
    
insert into ingredients (recipe_id, food_id, amount, measurement_unit)
values
	(6, 21, 2, 'lbs'),
    (6, 22, 0.25, 'cup'),
    (6, 1, 1, null),
    (6, 23, 2, 'tbsp'),
    (6, 5, 2, 'tbsp'),
    (6, 2, 1, 'tsp'),
    (6, 24, 1, 'tsp'),
    (6, 25, 1, 'tsp'),
    (6, 4, 0.50, 'tsp'),
    (5, 9, 1.50, 'lbs'),
    (5, 3, 4, 'tbsp'),
    (5, 19, 4, 'cloves'),
    (5, 15, 1, 'tsp'),
    (5, 16, 1, 'tsp'),
    (5, 2, 1, 'tsp'),
    (5, 4, 1, 'tsp'),
    (5, 17, 16, 'oz'),
    (5, 18, 0.75, 'cup'),
    (5, 20, 3, 'tbsp'),
    (5, 5, 2, 'cup');
    
insert into recipe (title, recipe_description, instructions, prep_time, cook_time, calories, servings, image_url)
values
	("Vanilla Mug Cake",
	"Got a craving for sweets? This easy-to-make, no-eggs-needed vanilla mug cake should hit the spot. It tastes amazing on it's own, but feel free to add mix-ins of your choice—whether it's Nutella, peanut butter, chocolate chips, or sprinkles, this recipe complements them all!",
    "1. Add flour, sugar, baking powder, and salt to a mug and stir together.
    2. Stir in milk, melted butter, and vanilla extract until smooth, being sure to scrape the bottom of the mug. Stir in sprinkles.
    3. Cook in microwave for 70-90 seconds (until cake is just set, but still barely shiny on top). Allow to rest in microwave for 1 minute before consuming.",
    3, 2, 458, 1,
    "https://fantabulosity.com/wp-content/uploads/2021/06/The-BEST-Vanilla-Mug-Cake-Recipe.jpg"),
    ("Caramel Pudding",
    "This recipe for pudding takes it all back to the basics with homemade caramel.",
    "1. In heavy-bottom saucepan set over medium heat, combine 1/2 cup of the sugar and 2 tbsp water; cook, stirring, until sugar melts.
	2. Increase heat to medium-high and bring to boil without stirring; boil for 8 to 10 minutes or until dark amber colour. Remove from heat and immediately add milk. Return to low heat and stir until caramel is dissolved.
	3. Meanwhile, separate the egg whites from their yolks. Discard the egg whites. Then, in a heatproof bowl, whisk together egg yolks, remaining 1/2 cup sugar, cornstarch and salt until mixture resembles a thick paste. Gradually whisk in one-quarter of the hot milk mixture, then return to saucepan; cook over medium-low heat, whisking constantly, for about 5 minutes or until thickened. Remove from heat; stir in butter and vanilla.
	4. Divide among individual bowls; cover with plastic wrap or waxed paper directly on surface. Refrigerate until chilled.",
    5, 15, null, 4,
    "https://www.momontimeout.com/wp-content/uploads/2020/04/homemade-butterscotch-pudding-recipe-square.jpg");
    
insert into food (food_name)
values
	('sugar'), -- 26
    ('baking powder'),
    ('cornstarch');
    
insert into ingredients (recipe_id, food_id, amount, measurement_unit)
values
	(7, 20, 2.25, 'oz'),
    (7, 26, 2, 'tbsp'),
    (7, 27, 0.25, 'tsp'),
    (7, 2, 0.125, 'tsp'),
    (7, 5, 3, 'tbsp'),
    (7, 6, 0.5, 'tsp'),
    (8, 26, 1, 'cup'),
    (8, 5, 2, 'cup'),
    (8, 1, 4, null),
    (8, 28, 0.25, 'cup'),
    (8, 2, 0.25, 'tsp'),
    (8, 3, 1, 'tbsp'),
    (8, 6, 1, 'tsp');
    
insert into recipe (title, recipe_description, instructions, prep_time, cook_time, calories, servings, image_url)
values
	("Crispy Roasted Chickpeas",
	"Crispy, crunchy chickpeas are just as delicious as plain old potato chips. You won't be able to stop eating these once you start!",
    "1. Heat the oven to 400°F. Arrange a rack in the middle of the oven and heat to 400°F.
	2. Rinse and drain the chickpeas. Open the cans of chickpeas and pour the chickpeas into a strainer in the sink. Rinse thoroughly under running water.
	3. Dry the chickpeas. Pat the chickpeas very dry with a clean dishtowel or paper towels. They should look matte and feel dry to the touch; if you have time, leave them to air-dry for a few minutes. Remove any chickpea skins that come off while drying, but otherwise don’t worry about them.
	4. Toss the chickpeas with olive oil and salt. Spread the chickpeas out in an even layer on a rimmed baking sheet. Drizzle with the oil and sprinkle with the salt. Stir with your hands or a spatula to make sure the chickpeas are evenly coated.
	5. Roast the chickpeas for 20 to 30 minutes. Roast, stirring the chickpeas or shaking the pan every 10 minutes. A few chickpeas may pop – that’s normal. The chickpeas are done when golden and slightly darkened, dry and crispy on the outside, and soft in the middle, 20 to 30 minutes total.
	6. Toss the chickpeas with the spices. Sprinkle the spices if using over the chickpeas and stir to coat evenly. Serve while the chickpeas are still warm and crispy. They will gradually lose their crispiness as they cool, becoming addictively chewy.",
    10, 30, 179, 8,
    "https://cdn.apartmenttherapy.info/image/upload/f_auto,q_auto:eco,c_fill,g_auto,w_1392,h_1949/k%2Farchive%2F78193639ccc595a1fd21e1df714637be6dc773a9"),
    ("Tahini-free Hummus",
    "This hummus can be made quickly with staple ingredients without needing to run to the store to grab tahini, not to mention it tastes just as good as store-bought versions!",
    "1. Mince the garlic clove.
    2. Add the chickpeas, 2 tablespoons of water, the olive oil, lemon juice, garlic, cumin, and ¼ teaspoon of salt to a food processor. Process until smooth and creamy. If needed, add additional water to thin out the hummus and ¼ teaspoon of salt to your taste preference.
	3. Serve as a dip with chips and/or vegetables. Store covered in the refrigerator.",
    5, 0, 70, 10,
    "https://www.quick-german-recipes.com/images/easy-hummus-recipe-7-edit.jpg");
    
insert into food (food_name)
values
	('chickpeas'), -- 29
    ('olive oil'),
    ('rosemary'),
    ('thyme'),
    ('lemon juice'),
    ('cumin'); -- 34
    
insert into ingredients (recipe_id, food_id, amount, measurement_unit)
values
	(9, 29, 30, 'oz'),
    (9, 30, 2, 'tbsp'),
    (9, 2, 1.5, 'tsp'),
    (9, 4, 1.5, 'tsp'),
    (9, 15, 1, 'tsp'),
    (9, 24, 1, 'tsp'),
    (9, 31, 1, 'tsp'),
    (9, 32, 1, 'tsp'),
    (10, 29, 15, 'oz'),
    (10, 30, 2, 'tbsp'),
    (10, 33, 1, 'tbsp'),
    (10, 19, 1, 'clove'),
    (10, 34, 0.75, 'tsp'),
    (10, 2, 0.25, 'tsp');
    
-- insert into recipe (title, recipe_description, instructions, prep_time, cook_time, calories, servings, image_url)
-- values
-- 	('',
-- 	'',
--     '',
--     0, 0, 0, 0,
--     ''),
--     ('',
--     "",
--     '',
--     0, 0, 0, 0,
--     '');
--     
-- insert into food (food_name)
-- values
-- 	(''),
--     (''),
--     (''),
--     (''),
--     (''),
--     (''),
--     (''),
--     ('');
--     
-- insert into ingredients (recipe_id, food_id, amount, measurement_unit)
-- values
-- 	(0, 0, 0.00, null),
--     (0, 0, 0.00, '');
        

insert into app_user (username, password_hash, first_name, last_name, email, dob)
	values
		-- user 1 (password: mwenge)
		('mmaliro', 
			'$2a$12$zYzSWHD1kbz0J/CIFoeB2u47jx/K2J1KCRDGTmWIhGU.Jun0X3.ve', 
            'Mwenge', 
            'Maliro', 
            'mwenge@maliro.com', 
            '2000-01-01'), 
		-- user 2 (password: hanako)
        ('hlb2000', 
			'$2a$12$I/vtDT1qSkGCdWpbOl32ZOJQk7rZHvpweShQr.AtQpE5/eeIWcMuO', 
            'Hanako', 
            'Boucher', 
            'hanako@boucher.com', 
            '2000-12-31'), 
		-- admin (password: paul)
        ('pm', 
			'$2a$12$LTkdX5A697u/57UvDNj14ekTGCbu9tFH5IwaEsuFalEWL91uQGI2C', 
			'Paul', 
			'Mitchell', 
			'paul@mitchell.com', 
			'1990-01-01');

insert into app_role (role_name) values ('USER'), ('ADMIN');

insert into app_user_role (app_user_id, app_role_id)
	values
		(1, 1),
        (2, 1),
        (3, 1),
        (3, 2);
        
insert into recipebook
values
	(1, 1),
	(1, 2),
    (1, 3),
	(1, 4),
    (1, 5),
	(1, 6),
    (1, 7),
	(1, 8),
    (1, 9),
	(1, 10),
    (2, 1),
	(2, 2),
    (2, 3),
	(2, 4),
    (2, 5),
	(2, 6),
    (2, 7),
	(2, 8),
    (2, 9),
	(2, 10),
    (3, 1),
	(3, 2),
    (3, 3),
	(3, 4),
    (3, 5),
	(3, 6),
    (3, 7),
	(3, 8),
    (3, 9),
	(3, 10);

insert into recipe (title, recipe_description, instructions, prep_time, cook_time, calories, servings, image_url, app_user_id)
values
	("Classic Vanilla Overnight Oats",
	"These vanilla overnight oats are ready to eat as-is, but they also make a great base to which you can add in any mix-ins for a variety of flavors.",
    "1. Whisk together all ingredients in a medium-sized mixing bowl. Spoon into a jar with a tight-fitting lid.
	2. Close and refrigerate for at least 4 hours, but preferably overnight before eating.",
    5, 240, 177, 2,
    "https://wholefully.com/wp-content/uploads/2019/04/classic-vanilla-overnight-oats-in-jar-814x814.jpg",
    1),
    ("Peanut Butter Chocolate-Chip Oatmeal Balls",
    "Easy energy balls for snacking! They're also extremely versatile. The chocolate chips can be replaced or complemented with other add-ins create many different (but equally delicious) variations.",
    "1. Before you start, if your peanut butter is not creamy (maybe you opened the jar a while back), then pour it into a microwave-safe bowl with sweetener. Microwave for 30 seconds to soften. Stir and then use in the recipe.
	2. In a large mixing bowl, add all the ingredients: old-fashioned oats, fresh runny peanut butter, maple syrup, and chocolate chips.
	3. Use a spatula to combine all the ingredients until it is sticky and forms a dough. If too dry, add 1 or 2 more tablespoons of maple syrup.
	4. Refrigerate the oatmeal balls mixture for 20 minutes to set the dough. This makes it easier to roll into energy bites.
	5. Remove the mixture from the fridge. slightly oil your hands with an oil before shaping each energy ball to prevent the batter from sticking to your hands. Scoop out about 1 1/2 tablespoons of dough and roll within your hands to shape energy balls. You should be able to create anywhere from 10-12.
	6. Place each oatmeal ball onto a plate covered with parchment paper.
	7. Enjoy immediately or store in the fridge in an airtight container for up to 3 weeks. The oatmeal balls may also be frozen in an airtight container instead if you'd like for them to last longer. In this case, remove them from the freezer 3 hours before you're ready to eat them, and thaw at room temperature until soft.",
    15, 30, 115, 12,
    "https://i0.wp.com/chefsavvy.com/wp-content/uploads/peanut-butter-chocolate-chip-energy-balls.jpg?w=665&ssl=1",
    1);

insert into food (food_name)
values
	('plain Greek yogurt'), -- 36
    ('rolled oats'),
    ('chia seeds'),
    ('maple syrup'),
    ('peanut butter'), -- 40
    ('dark chocolate chips');

insert into ingredients (recipe_id, food_id, amount, measurement_unit)
values
	(11, 36, 0.33, 'cup'),
    (11, 37, 0.50, 'cup'),
    (11, 5, 0.66, 'cup'),
    (11, 37, 1, 'tbsp'),
    (11, 6, 0.50, 'tsp'),
    (11, 2, 0.25, 'tsp'),
    (11, 39, 2, 'tbsp'),
    (12, 37, 1.25, 'cup'),
    (12, 40, 0.50, 'cup'),
    (12, 39, 0.25, 'cup'),
    (12, 40, 0.25, 'cup'),
    (12, 6, 0.50, 'tsp'),
    (12, 2, 0.25, 'tsp');
    
insert into recipe (title, instructions, prep_time, cook_time, calories, servings, image_url, app_user_id)
values
	("Granola",
    "1. Preheat oven to 350 degrees Fahrenheit and line a large, rimmed baking sheet with parchment paper.
    2. In a large mixing bowl, combine the oats, pecans, pepitas, salt and cinnamon. Stir to blend.
	3. Pour in the oil, maple syrup, and vanilla. Mix well, until every oat and nut is lightly coated. Pour the granola onto your prepared pan and use a large spoon to spread it in an even layer.
	4. Bake until lightly golden, about 21 to 24 minutes, stirring halfway (for extra-clumpy granola, press the stirred granola down with your spatula to create a more even layer). The granola will further crisp up as it cools.
	5. Let the granola cool completely, undisturbed (at least 45 minutes). Top with the dried fruit and chocolate chips. Break the granola into pieces with your hands if you want to retain big chunks, or stir it around with a spoon if you don’t want extra-clumpy granola.
	6. Store the granola in an airtight container at room temperature for 1 to 2 weeks, or in a sealed freezer bag in the freezer for up to 3 months. The dried fruit can freeze solid, so let it warm to room temperature for 5 to 10 minutes before serving.",
    5, 24, 251, 16,
    "https://cookieandkate.com/images/2015/10/clumpy-granola.jpg",
    3),
    ("Easy-Peasy Chili",
    "1. Place ground beef and onion in a large saucepan over medium heat; cook and stir until meat is browned and onion is tender, about 5 to 7 minutes.
	2. Stir in tomato sauce, kidney beans, stewed tomatoes with juice, and water. Season with chili powder, garlic powder, salt, and black pepper. Bring to a boil, reduce heat to low, cover and let simmer for 15 minutes.",
    10, 20, 394, 6,
    "https://www.foodnetwork.com/content/dam/images/food/fullset/2012/2/1/0/WU0202_chili_s3x4.jpg",
    3),
    ("Asian Lettuce Wraps",
    "1. Rinse whole lettuce leaves and pat dry, being careful not to tear them; set aside. Chop onion (both varieties) and mince garlic cloves. Drain water chestnuts before finely chopping them.
	2. Heat oil in a large skillet over medium-high heat. Add beef; cook and stir until browned and crumbly, 5 to 7 minutes. Drain and discard grease; transfer beef to a bowl. Add yellow onion to the same skillet; cook and stir until just tender, 5 to 7 minutes.
	3. Stir in hoisin sauce, garlic, soy sauce, vinegar, ginger, and chile pepper sauce until well combined. Add water chestnuts, green onions, sesame oil, and cooked beef; cook and stir until the onions just begin to wilt, about 2 minutes.
	4. Arrange lettuce leaves around the outer edge of a large serving platter; spoon beef mixture in the center.",
    15, 15, 388, 4,
    "https://www.allrecipes.com/thmb/eE1YsnC8Bmdn-Yv7myza5kCh830=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/2630776-asian-lettuce-wraps-Chefd-1x1-1-b10d1fecc65748a4b4c60113da62a863.jpg",
    3);
    
insert into food (food_name)
values
	('pecans'), -- 42
    ('pepitas'),
    ('dried fruit'),
    ('yellow onion'), -- 45
    ('tomato sauce'),
    ('kidney beans'),
    ('stewed tomatoes'),
    ('chili powder'),
    ('lettuce'), -- 50 
    ('tomato'),
    ('American cheese'),
    ('hoisin sauce'),
    ('soy sauce'),
    ('rice wine vinegar'), -- 55
    ('minced pickled ginger'),
    ('water chestnuts'),
    ('green onions'),
    ('dark sesame oil');

insert into ingredients (recipe_id, food_id, amount, measurement_unit)
values
	(13, 37, 4, 'cups'),
    (13, 42, 0.75, 'cup'),
    (13, 43, 0.75, 'cup'),
    (13, 2, 0.75, 'tsp'),
    (13, 7, 0.50, 'tsp'),
    (13, 31, 0.50, 'cup'),
    (13, 39, 0.50, 'cup'),
    (13, 6, 0.50, 'tsp'),
    (13, 43, 0.66, 'cup'),
    (13, 41, 0.50, 'cup'),
    (14, 22, 1, 'lb'),
    (14, 45, 1, null),
    (14, 46, 1, '15-oz can'),
    (14, 47, 1, '15-oz can'),
    (14, 48, 1, '14.5-oz can'),
    (14, 49, 0.50, 'tsp'),
    (14, 25, 0.50, 'tsp'),
    (14, 2, 0.50, 'tsp'),
    (14, 4, 0.50, 'tsp'),
    (6, 50, 12, 'leaves'),
    (6, 51, 4, null),
    (6, 52, 6, 'slices'),
    (15, 50, 16, 'leaves'),
    (15, 22, 1, 'lb'),
    (15, 31, 1, 'tbsp'),
    (15, 45, 1, null),
    (15, 53, 0.25, 'cup'),
    (15, 20, 2, 'cloves'),
    (15, 54, 1, 'tbsp'),
    (15, 55, 1, 'tbsp'),
    (15, 56, 1, 'tsp'),
    (15, 57, 1, '8-oz can'),
    (15, 58, 1, 'bunch'),
    (15, 59, 2, 'tsp');
    
insert into tags (tag_name)
values
	('breakfast'),
    ('lunch'),
    ('dinner'),
    ('sweet'),
    ('snack'), -- 5
    ('vegetarian'),
    ('vegan'),
    ('one pot meal'),
    ('slow cooker meal'),
    ('sheet pan meal'), -- 10
    ('under 30 mins'),
    ('under 15 mins'),
    ('under 10 mins'),
    ('appetizer'),
    ('main dish'), -- 15
    ('side dish'),
    ('Italian'),
    ('Japanese'),
    ('Chinese'),
    ('Asian'), -- 20
    ('African'),
    ('Ethiopian'),
    ('Nigerian'),
    ('Kenyan'),
    ('Greek'), -- 25
    ('Indian'),
    ('American'),
    ('Mexican'),
    ('Thai'),
	('French'), -- 30
	('low-calorie (under 300 calories per serving)');
    
insert into recipe_tags
values
	(1, 1),
    (1, 6),
    (1, 13),
    (1, 15),
    (1, 16),
    (1, 31),
    (2, 1),
    (2, 6),
    (2, 12),
    (2, 15),
    (2, 31),
    (3, 2),
    (3, 13),
    (3, 15),
    (3, 31),
    (4, 2),
    (4, 13),
    (4, 14),
    (4, 15),
    (4, 16),
    (4, 17),
    (5, 3),
    (5, 17),
    (5, 15),
    (6, 11),
    (6, 3),
    (6, 15),
    (6, 27),
    (7, 13),
    (7, 6),
    (7, 4),
    (8, 4),
    (8, 6),
    (8, 11),
    (9, 31),
    (9, 16),
    (9, 10),
    (9, 6),
    (9, 7),
    (9, 5),
    (10, 31),
    (10, 16),
    (10, 14),
    (10, 13),
    (10, 7),
    (10, 6),
    (10, 5);
    
insert into recipebook
values 
	(1, 11),
    (1, 12),
    (3, 13),
    (3, 14),
    (3, 15);
    
    
    
    

select * from ingredients;
select * from food order by food_id;
select * from recipe order by recipe_id;