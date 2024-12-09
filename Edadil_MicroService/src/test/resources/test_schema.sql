-- Создание таблиц
CREATE TABLE companies (
                           id           bigserial PRIMARY KEY,
                           company_name VARCHAR(100)
);

CREATE TABLE firms (
                       id        bigserial PRIMARY KEY,
                       firm_name VARCHAR(100)
);

CREATE TABLE users (
                       id        bigserial PRIMARY KEY,
                       user_name VARCHAR(50),
                       role      VARCHAR(50)
);

CREATE TABLE products (
                          id      bigserial PRIMARY KEY,
                          name    VARCHAR(100),
                          firm_id bigserial,
                          FOREIGN KEY (firm_id) REFERENCES firms (id)
);

CREATE TABLE shops (
                       id                 bigserial PRIMARY KEY,
                       address            VARCHAR(255),
                       city               VARCHAR(100),
                       name_of_company_id bigserial,
                       FOREIGN KEY (name_of_company_id) REFERENCES companies (id)
);

CREATE TABLE shop_product (
                              PRIMARY KEY (shop_id, product_id),
                              shop_id    bigserial,
                              product_id bigserial,
                              count      INT   NOT NULL,
                              price      FLOAT NOT NULL,
                              FOREIGN KEY (shop_id) REFERENCES shops (id),
                              FOREIGN KEY (product_id) REFERENCES products (id)
);

-- Заполнение таблиц
INSERT INTO companies (company_name) VALUES
                                         ('Пятерочка'),
                                         ('Дикси');

INSERT INTO firms (firm_name) VALUES
                                  ('Простоквашино'),
                                  ('Lays'),
                                  ('Lipton'),
                                  ('Coca-Cola'),
                                  ('Pepsi'),
                                  ('Nestle'),
                                  ('Mars'),
                                  ('Kinder'),
                                  ('KitKat'),
                                  ('Twix'),
                                  ('Snickers'),
                                  ('Bounty'),
                                  ('M&M''s'),
                                  ('Skittles'),
                                  ('Haribo'),
                                  ('Chupa-Chups'),
                                  ('Raffaello'),
                                  ('Tic Tac'),
                                  ('Ferrero Rocher'),
                                  ('Nutella');

INSERT INTO products (name, firm_id) VALUES
                                         ('Молоко', 1),
                                         ('Кефир', 1),
                                         ('Чипсы', 2),
                                         ('Чай', 3),
                                         ('Кола', 4),
                                         ('Пепси-кола', 5),
                                         ('Шоколад', 6),
                                         ('Батончик', 7),
                                         ('Шоколадные конфеты', 8),
                                         ('Томатный соус', 20);

INSERT INTO shops (address, city, name_of_company_id) VALUES
                                                          ('ул. Ленина, 15', 'Москва', 1),
                                                          ('пр. Мира, 10', 'Санкт-Петербург', 2),
                                                          ('ул. Пушкина, 5', 'Москва', 2),
                                                          ('ул. Советская, 20', 'Москва', 2),
                                                          ('Московское шоссе, 123', 'Москва', 1),
                                                          ('Невский проспект, 87', 'Санкт-Петербург', 1),
                                                          ('ул. Мира, 1', 'Москва', 1),
                                                          ('Ленинградский проспект, 100', 'Санкт-Петербург', 1),
                                                          ('ул. Кирова, 55', 'Санкт-Петербург', 2),
                                                          ('ул. Ленина, 36', 'Москва', 2),
                                                          ('ул. Советская, 15', 'Санкт-Петербург', 1);

INSERT INTO shop_product (shop_id, product_id, count, price) VALUES
                                                                 (1, 1, 100, 50),
                                                                 (1, 2, 50, 35),
                                                                 (2, 3, 200, 15),
                                                                 (3, 4, 150, 25),
                                                                 (4, 5, 120, 40),
                                                                 (4, 6, 80, 30),
                                                                 (5, 1, 75, 52),
                                                                 (5, 2, 110, 38),
                                                                 (6, 3, 180, 17),
                                                                 (7, 4, 160, 26),
                                                                 (8, 5, 130, 42),
                                                                 (8, 6, 90, 32),
                                                                 (9, 1, 85, 54),
                                                                 (9, 2, 120, 36),
                                                                 (10, 3, 190, 18),
                                                                 (11, 4, 170, 27),
                                                                 (1, 5, 140, 44),
                                                                 (1, 6, 100, 34),
                                                                 (2, 1, 95, 56),
                                                                 (2, 2, 130, 38),
                                                                 (3, 3, 200, 19),
                                                                 (4, 4, 180, 28),
                                                                 (5, 5, 150, 46),
                                                                 (5, 6, 110, 36),
                                                                 (6, 1, 105, 58),
                                                                 (6, 2, 140, 40),
                                                                 (7, 3, 210, 20),
                                                                 (8, 4, 190, 29),
                                                                 (9, 5, 160, 48),
                                                                 (11, 10, 100, 200);
