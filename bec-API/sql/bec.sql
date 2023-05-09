drop database if exists bec;
create database bec;
use bec;

-- DDL (table creation)
create table member_user (
	member_user_id int not null primary key auto_increment,
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

create table member_user_role (
	member_user_id int not null,
    app_role_id int not null default 1,
    constraint fk_member_user_role_member_user_id
		foreign key (member_user_id)
        references member_user(member_user_id),
	constraint fk_member_user_role_app_role_id
		foreign key (app_role_id)
        references app_role(app_role_id)
);

create table donation (
	donation_id int not null primary key auto_increment,
    donation_date date not null,
    amount decimal(10,2) not null,
    note varchar(500) null,
    member_user_id int not null,
    constraint fk_donation_member_user_role_member_user_id
		foreign key (member_user_id)
        references member_user(member_user_id)
);

create table event_plan (
	event_plan_id int not null primary key auto_increment,
    event_name varchar(50) not null unique,
    event_description varchar(500) null,
    `date` date not null,
    `time` time not null,
    location varchar(500) not null
    
    
);

create table event_plan_app_role (
	event_plan_id int not null,
	app_role_id int not null default 2,
    constraint fk_event_plan_app_role_event_plan_id
		foreign key (event_plan_id)
		references event_plan(event_plan_id),
	constraint fk_event_plan_app_role_app_role_id
		foreign key (app_role_id)
		references app_role(app_role_id)
);