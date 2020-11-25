create database if not exists plantio;
use plantio;
drop table if exists users;

create table users(
	name varchar(255) primary key,
    password varchar(255) not null
);