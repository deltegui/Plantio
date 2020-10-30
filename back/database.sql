create database plantio;
use plantio;

drop table if exists tokens;
drop table if exists users;

create table users (
	name varchar(255) primary key,
    password varchar(255) not null,
    last_connection datetime not null
);

create table tokens (
	user varchar(255) primary key,
    value varchar(255) not null,
    expiration datetime not null,
    foreign key (user) references users(name)
);
