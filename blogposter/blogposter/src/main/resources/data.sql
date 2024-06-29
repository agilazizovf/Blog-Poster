insert into posters
(post_name,description,creation_date,comments, likes, username)
values
    ('First post','This is my first post','22-05-2020 10:10:10',0, 'user1'),
    ('Second post','This is second first post','25-05-2020 10:10:10',0,'user2');


insert into users
(username,password,enabled)
values
    ('user1','{noop}1234',1),
    ('user2','{noop}1234',1);


insert into authorities
(username,authority)
values
    ('user1','ROLE_ADMIN'),
    ('user2','ROLE_ADMIN');


insert into comments
(text,post_id,username)
values
    ('This is random text',1,'user1'),
    ('This is random text',2,'user2');


insert into likes
(likes,post_id,username)
values
    ('0',1,'user1'),
    ('0',2,'user2');