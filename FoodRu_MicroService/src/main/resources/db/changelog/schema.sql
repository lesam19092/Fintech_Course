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
    type_id bigserial,
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



