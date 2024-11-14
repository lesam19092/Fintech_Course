create table instance
(
    id           INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    instance_name VARCHAR(100)
);

CREATE TABLE users
(
    id        INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name      VARCHAR(100),
    email     VARCHAR(100),
    password  VARCHAR(100),
    user_role varchar(100),
    instance_id INT,
    FOREIGN KEY (instance_id) REFERENCES instance (id)
);
create table tokens
(
    id            INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    access_token  VARCHAR(300),
    is_logged_out boolean,
    user_id       INT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

