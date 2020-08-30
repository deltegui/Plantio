drop table if exists tokens;
drop table if exists users;

create table users (
    name varchar(255) primary key,
    password varchar(255) not null,
    last_connection timestamp with time zone not null
);

create table tokens (
    username varchar(255),
    token varchar(255),
    expiration timestamp with time zone not null,

    constraint tokens_fk_users foreign key(username) references users(name) ON DELETE CASCADE,
    constraint tokens_pk primary key(username, token)
);