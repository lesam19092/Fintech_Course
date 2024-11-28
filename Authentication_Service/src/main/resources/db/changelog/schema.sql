create table instance
(
    id            bigserial PRIMARY KEY,
    instance_name VARCHAR(100)
);

CREATE TABLE users
(
    id          bigserial PRIMARY KEY,
    name        VARCHAR(100),
    email       VARCHAR(100),
    password    VARCHAR(100),
    user_role   varchar(100),
    is_enabled  boolean,
    instance_id bigserial,
    FOREIGN KEY (instance_id) REFERENCES instance (id)
);

create table password_reset_token
(
    id      bigserial PRIMARY KEY,
    token   VARCHAR(200),
    user_id bigserial unique,
    is_used boolean,
    FOREIGN KEY (user_id) REFERENCES users (id)
);


create table tokens
(
    id            bigserial PRIMARY KEY,
    access_token  VARCHAR(400),
    is_logged_out boolean,
    user_id       bigserial,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

create table confirmation_token
(
    id                 bigserial PRIMARY KEY,
    confirmation_token VARCHAR(200),
    date               TIMESTAMP,
    user_id            bigserial UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users (id)
)

