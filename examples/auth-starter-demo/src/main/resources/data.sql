CREATE TABLE if not exists auth_role
(
    id
    BIGINT
    NOT
    NULL,
    title
    VARCHAR
(
    255
) NOT NULL,
    code VARCHAR
(
    255
) NOT NULL,
    created_time TIMESTAMP DEFAULT NULL,
    updated_time TIMESTAMP DEFAULT NULL,
    updated_by_user_id BIGINT DEFAULT NULL,
    created_by_user_id BIGINT DEFAULT NULL,
    PRIMARY KEY
(
    id
),
    UNIQUE
(
    code
)
    );

CREATE TABLE if not exists auth_url_resource
(
    id
    BIGINT
    NOT
    NULL,
    url
    VARCHAR
(
    255
) NOT NULL,
    title VARCHAR
(
    255
) NOT NULL,
    created_time TIMESTAMP DEFAULT NULL,
    updated_time TIMESTAMP DEFAULT NULL,
    updated_by_user_id BIGINT DEFAULT NULL,
    created_by_user_id BIGINT DEFAULT NULL,
    PRIMARY KEY
(
    id
),
    UNIQUE
(
    title
)
);

CREATE TABLE if not exists auth_user
(
    id
    BIGINT
    NOT
    NULL,
    username
    VARCHAR
(
    255
) NOT NULL,
    password VARCHAR
(
    255
) NOT NULL,
    created_time TIMESTAMP DEFAULT NULL,
    updated_time TIMESTAMP DEFAULT NULL,
    updated_by_user_id BIGINT DEFAULT NULL,
    created_by_user_id BIGINT DEFAULT NULL,
    PRIMARY KEY
(
    id
),
    UNIQUE
(
    username
)
    );

CREATE TABLE if not exists link_role_url_resource
(
    role_id
    BIGINT
    NOT
    NULL,
    url_resource_id
    BIGINT
    NOT
    NULL,
    PRIMARY
    KEY
(
    role_id,
    url_resource_id
)
    );

CREATE TABLE if not exists link_user_role
(
    user_id
    BIGINT
    NOT
    NULL,
    role_id
    BIGINT
    NOT
    NULL,
    PRIMARY
    KEY
(
    user_id,
    role_id
)
    );


insert into auth_user (id, username, password)
values (1111111, 'admin', '$2a$10$xwCqYyAxkqtyY0H/UyKowe6VVCOZCTWc.XdeLNPnGKng1lR0KMUFS')
;
