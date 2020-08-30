insert into users (name, password, last_connection) values ('diego', 'diego', now());
insert into tokens (username, token, expiration) values('diego', 'blahblahthisismytokenblahblah', now());