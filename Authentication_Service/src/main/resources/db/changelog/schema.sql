CREATE TYPE role AS ENUM ('ADMIN', 'USER');

CREATE TABLE users_edadil
(
    id        INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name      VARCHAR(100),
    email     VARCHAR(100),
    password  VARCHAR(100),
    user_role role
);


CREATE TABLE users_foodru
(
    id        INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_name VARCHAR(50),
    user_role role,
    password  VARCHAR(100)
);

create table tokens_edadil
(
    id            INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    access_token  VARCHAR(200),
    is_logged_out boolean,
    user_id       INT,
    FOREIGN KEY (user_id) REFERENCES users_edadil (id)
);

create table tokens_foodru
(
    id            INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    access_token  VARCHAR(200),
    is_logged_out boolean,
    user_id       INT,
    FOREIGN KEY (user_id) REFERENCES users_foodru (id)
);