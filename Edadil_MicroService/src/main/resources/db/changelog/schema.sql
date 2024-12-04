CREATE TABLE companies
(
    id           bigserial PRIMARY KEY,
    company_name VARCHAR(100)
);

create table firms
(
    id        bigserial PRIMARY KEY,
    firm_name VARCHAR(100)
);

CREATE TABLE users
(
    id        bigserial PRIMARY KEY,
    user_name VARCHAR(50),
    role      VARCHAR(50)
);

CREATE TABLE products
(
    id      bigserial PRIMARY KEY,
    name    VARCHAR(100),
    firm_id bigserial,
    FOREIGN KEY (firm_id) REFERENCES firms (id)
);

CREATE TABLE shops
(
    id                 bigserial PRIMARY KEY,
    address            VARCHAR(255),
    city               VARCHAR(100),
    name_of_company_id bigserial,
    FOREIGN KEY (name_of_company_id) REFERENCES companies (id)
);

CREATE TABLE shop_product
(
    PRIMARY KEY (shop_id, product_id),
    shop_id    bigserial,
    product_id bigserial,
    count      INT   NOT NULL,
    price      FLOAT NOT NULL,
    FOREIGN KEY (shop_id) REFERENCES shops (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);
