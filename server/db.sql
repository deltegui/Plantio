drop table if exists tokens;
drop table if exists users;

create table users (
    name varchar(255) primary key,
    password varchar(255) not null,
    lastConnection datetime not null
);

create table tokens (
    created datetime not null,
    user varchar(255),
    foreign key (user) references users(name) on delete cascade
);
