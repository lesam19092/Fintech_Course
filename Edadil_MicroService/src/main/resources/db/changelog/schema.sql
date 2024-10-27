CREATE TABLE companies
(
    id           INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    company_name VARCHAR(100)
);

create table firms
(
    id           INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    firm_name VARCHAR(100)
);

CREATE TABLE users
(
    id        INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_name VARCHAR(50),
    role      VARCHAR(50),
    password  VARCHAR(100)
);

CREATE TABLE products
(
    id         INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR(100),
    firm_id INT,
    FOREIGN KEY (firm_id) REFERENCES firms (id)
);

CREATE TABLE shops
(
    id                 INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    address            VARCHAR(255),
    city               VARCHAR(100),
    name_of_company_id INT,
    FOREIGN KEY (name_of_company_id) REFERENCES companies (id)
);

CREATE TABLE shop_product
(
    PRIMARY KEY (shop_id, product_id),
    shop_id INT,
    product_id INT,
    count INT   NOT NULL,
    price FLOAT NOT NULL,
    FOREIGN KEY (shop_id) REFERENCES shops (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);
