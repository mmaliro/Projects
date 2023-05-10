drop database if exists recipes_test;
create database recipes_test;
use recipes_test;

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
    tag_name varchar(25) not null unique,
    default_image varchar(2048) not null
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

-- creating known good state
delimiter //
create procedure set_known_good_state()
begin


	-- clearing current tables
    delete from meal_components;
	delete from meal;
    alter table meal auto_increment = 1;
    
    delete from ingredients;
    delete from food;
    alter table food auto_increment = 1;
    
    delete from recipe_tags;
    delete from tags;
    alter table tags auto_increment = 1;
    
    delete from recipebook;
    delete from recipe;
    alter table recipe auto_increment = 1;
    
	delete from app_user_role;
    delete from app_role;
    alter table app_role auto_increment = 1;
    delete from app_user;
    alter table app_user auto_increment = 1;
    
    
	-- inserting info for known good state
    
	-- NOTES: in individual entity tables (NOT bridge tables), test methods on each entry as follows:
		-- the first item (ID 1) should always be used to test the Update method
        -- the second item (ID 2) should always be used to test the Delete method
        -- the third item (ID 3) should always be used to test the Find method
        -- to test your Add method, you can add a fourth item (it should end up with ID 4 if the auto_increment ID field is working correctly)
	-- see the first individual entity table below (app_user) for an example labeling each entry with the appropriate method it should be used to test\
    
    insert into app_user (username, password_hash, first_name, last_name, email, dob)
	values
		('appuser', '$2a$10$/Ltp.l1Z4JDEgI8OpOwWo.8x7MEUYAwqqnbYt8sfxIezigmyh9ADS', 'userfirst', 'userlast', 'user@user.com', '1998-01-01'), -- update
        ('appadmin', '$2a$10$M7zdZA/n26txoefFMQF8ZeNXuarS2IwqxnbXHdD1n.CgeTpoyunpe', 'adminfirst', 'adminlast', 'admin@admin.com', '2000-01-01'), -- delete
        ('adminuser', 'p@ssw0rd', 'adminuserfirst', 'adminuserlast', 'adminuser@adminuser.com', '2000-01-01'); -- find
        -- add
        
	insert into app_role (role_name)
    values
        ('USER'),
        ('ADMIN');
        
	insert into app_user_role
    values
		(1, 1),
        (2, 2),
        (3, 1),
        (3, 2);
        
	insert into recipe (title, instructions, recipe_description, cook_time, prep_time, calories, servings, image_url)
    values
		('Chicken Alfredo Penne Pasta', 'chicken alfredo instructions', 'chicken alfredo penne pasta', 20, 15, 460, 6, 'https://img.buzzfeed.com/thumbnailer-prod-us-east-1/video-api/assets/9768.jpg'),
        ('Scrambled Eggs', 'scrambled eggs instructions', 'scrambled eggs', 5, 2, 100, 2, 'https://bellyfull.net/wp-content/uploads/2019/03/The-Best-Scrambled-Eggs-blog.jpg'),
        ('Marinated Italian Tomato Salad', 'tomato salad instructions', 'marinated italian tomato salad', 0, 10, 115, 4, 'https://loveandgoodstuff.com/wp-content/uploads/2019/07/italian-tomato-salad-6.jpg');
        
	insert into recipebook
    values
		(1, 1),
        (2, 2),
        (3, 3);
        
	insert into tags (tag_name, default_image)
    values
		('vegetarian', 'https://creazilla-store.fra1.digitaloceanspaces.com/emojis/57185/green-salad-emoji-clipart-md.png'),
        ('Italian', 'https://w7.pngwing.com/pngs/107/113/png-transparent-italian-cuisine-pasta-emoji-spaghetti-android-spaghetti-food-orange-cooking.png'),
        ('under 30 mins', 'https://creazilla-store.fra1.digitaloceanspaces.com/emojis/58241/alarm-clock-emoji-clipart-md.png');
	
    insert into recipe_tags
    values
		(1, 2),
        (2, 1),
        (2, 3),
        (3, 1),
        (3, 2);
        
	insert into food (food_name)
    values
		('chicken'),
        ('alfredo sauce'),
        ('penne'),
        ('egg'),
        ('tomato'),
        ('red onion'),
        ('dressing'),
        ('fresh basil'),
        ('fresh parsley'),
        ('garlic'),
        ('salt'),
        ('pepper'),
        ('apple');
        
	insert into ingredients (recipe_id, food_id, amount, measurement_unit)
    values
		(1, 1, 1.5, 'lbs'),
        (1, 2, 1.0, 'cup'),
        (1, 3, 16.0, 'oz'),
        (2, 4, 2.0, null),
        (3, 5, 3.0, null),
        (3, 6, 3.0, 'tbsp'),
        (3, 7, 6.5, 'tbsp'),
        (3, 8, 4.0, 'leaves'),
        (3, 9, 1.0, 'tbsp'),
        (3, 10, 4.0, 'cloves'),
        (3, 11, 0.5, 'tsp'),
        (3, 12, 1.0, 'pinch');
	
    insert into meal (app_user_id, `date`,`time`, meal_category)
    values
		(1, '2023-01-16', '18:00:00', 'dinner'),
        (1, '2023-01-16', '08:00:00', 'breakfast'),
        (2, '2023-01-16', '12:00:00', null);
	
    insert into meal_components
    values
		(1, 1, null, 1.0, 'serving'),
        (2, 2, null, 2.0, 'eggs'),
        (2, null, 13, 1.0, null),
        (3, 3, null, 1, 'serving');
        

end //
delimiter ;

-- actual data (DELETE THIS when it comes time to test)
set sql_safe_updates = 0;
call set_known_good_state();
set sql_safe_updates = 1;

select * from ingredients;
