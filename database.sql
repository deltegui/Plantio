create database if not exists plantio;
use plantio;
drop table if exists weather_snapshots;
drop table if exists saved_plants;
drop table if exists saves;
drop table if exists users;

create table users(
    name varchar(255) primary key,
    password varchar(255) not null
);

create table saves(
    save_user varchar(255) primary key,
    last_update timestamp not null,
    latitude float,
    longitude float,
    
    foreign key (save_user) references users(name) on delete cascade
);

create table saved_plants(
    save_user varchar(255) not null,
    pos_x integer unsigned not null check(pos_x <= 3),
    pos_y integer unsigned not null check(pos_y <= 3),
    plant_name varchar(255) not null,
    watered varchar(255) not null,
    phase integer unsigned not null check(phase <= 6),
    
    primary key (save_user, pos_x, pos_y),
    foreign key (save_user) references saves(save_user) on delete cascade
);

create table weather_snapshots(
	user varchar(255),
    creation timestamp,
    location varchar(255) not null,
    latitude float not null,
    longitude float not null,
    weather_state varchar(255) not null,
    temperature float not null,
    sunrise integer unsigned not null,
    sunset integer unsigned not null,
    
    primary key (user, creation),
    foreign key (user) references saves(save_user) on delete cascade
);
