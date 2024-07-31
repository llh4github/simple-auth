DROP TABLE if exists auth_user;
create table auth_user
(
    id       INT          NOT NULL,
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(100) NOT NULL
);

insert into auth_user (id, username, password)
values (1111111, 'admin', '$2a$10$xwCqYyAxkqtyY0H/UyKowe6VVCOZCTWc.XdeLNPnGKng1lR0KMUFS')
;
