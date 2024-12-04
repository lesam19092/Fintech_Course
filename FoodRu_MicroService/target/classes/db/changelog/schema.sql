CREATE TYPE role AS ENUM ('admin', 'user');

CREATE TABLE users
(
    id        INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name      VARCHAR(100),
    email     VARCHAR(100),
    password  VARCHAR(100),
    user_role role
);

CREATE TABLE types
(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE menu
(
    id      INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name    VARCHAR(100) NOT NULL,
    user_id INT          NOT NULL,
    type_id INT          NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (type_id) REFERENCES types (id)
);

CREATE TABLE meals
(
    id                INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name              VARCHAR(100) NOT NULL,
    cook_instructions VARCHAR(100) NOT NULL
);

CREATE TABLE ingredients
(
    id   INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(100) NOT NULL
);
CREATE TABLE meals_ingredients
(
    PRIMARY KEY (meal_id, ingredient_id),
    meal_id       INT   NOT NULL,
    ingredient_id INT   NOT NULL,
    measure       Float NOT NULL,
    FOREIGN KEY (meal_id) REFERENCES meals (id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredients (id)
);
CREATE TABLE menu_meals
(
    PRIMARY KEY (menu_id, meal_id),
    menu_id INT NOT NULL,
    meal_id INT NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES menu (id),
    FOREIGN KEY (meal_id) REFERENCES meals (id)
);

CREATE TABLE users_meals
(
    PRIMARY KEY (user_id, meal_id),
    user_id INT NOT NULL,
    meal_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (meal_id) REFERENCES meals (id)
);



