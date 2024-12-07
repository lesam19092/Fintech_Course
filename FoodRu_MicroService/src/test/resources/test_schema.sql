CREATE TABLE users
(
    id    bigserial PRIMARY KEY,
    name  VARCHAR(100),
    email VARCHAR(100),
    role  varchar(100)
);


CREATE TABLE ingredients
(
    id   bigserial PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE menu
(
    id      bigserial PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    user_id bigserial,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE meals
(
    id                bigserial PRIMARY KEY,
    name              VARCHAR(100) NOT NULL,
    cook_instructions VARCHAR(100) NOT NULL
);

CREATE TABLE meals_ingredients
(
    PRIMARY KEY (meal_id, ingredient_id),
    meal_id       bigserial,
    ingredient_id bigserial,
    measure       Float NOT NULL,
    FOREIGN KEY (meal_id) REFERENCES meals (id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredients (id)
);
CREATE TABLE menu_meals
(
    PRIMARY KEY (menu_id, meal_id),
    menu_id bigserial,
    meal_id bigserial,
    FOREIGN KEY (menu_id) REFERENCES menu (id),
    FOREIGN KEY (meal_id) REFERENCES meals (id)
);

CREATE TABLE users_meals
(
    PRIMARY KEY (user_id, meal_id),
    user_id bigserial,
    meal_id bigserial,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (meal_id) REFERENCES meals (id)
);



INSERT INTO users (name, email, role)
VALUES
    ('John Doe', 'john.doe@example.com', 'ROLE_ADMIN'),
    ('Jane Smith', 'jane.smith@example.com', 'ROLE_USER'),
    ('Bob Brown', 'jane123.smith@example.com', 'ROLE_USER');

INSERT INTO ingredients (name)
VALUES
    ('Salt'),
    ('Sugar'),
    ('Flour');

INSERT INTO meals (name, cook_instructions)
VALUES
    ('Pancakes', 'Mix ingredients and fry on a pan'),
    ('Salad', 'Chop ingredients and mix together'),
    ('Omelette', 'Beat eggs and cook in a pan');

INSERT INTO menu (name, user_id)
VALUES
    ('Breakfast Menu', 1),
    ('Lunch Menu', 2);

INSERT INTO meals_ingredients (meal_id, ingredient_id, measure)
VALUES
    (1, 1, 1.0),
    (1, 2, 0.5),
    (2, 3, 2.0);

INSERT INTO menu_meals (menu_id, meal_id)
VALUES
    (1, 1),
    (2, 2);

INSERT INTO users_meals (user_id, meal_id)
VALUES
    (1, 1),
    (2, 2);
