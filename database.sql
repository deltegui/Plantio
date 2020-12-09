create database if not exists plantio;
use plantio;
drop table if exists saved_plants;
drop table if exists saves;
drop table if exists users;

create table users(
    name varchar(255) primary key,
    password varchar(255) not null
);

create table saves(
    id integer primary key auto_increment,
    user varchar(255) not null,
    last_update timestamp not null,
    
    foreign key (user) references users(name) on delete cascade
);

create table saved_plants(
    save_id integer not null,
    pos_x integer unsigned not null check(pos_x <= 3),
    pos_y integer unsigned not null check(pos_y <= 3),
    plant_name varchar(255) not null,
    phase integer unsigned check(phase <= 6),
    
    primary key (save_id, pos_x, pos_y),
    foreign key (save_id) references saves(id) on delete cascade
);
