CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    cpf VARCHAR NOT NULL,
    sex VARCHAR(30) NOT NULL,
    birth DATE ,
    UNIQUE(cpf)
);

CREATE TABLE payment (
    id SERIAL PRIMARY KEY,
    status VARCHAR(30) NOT NULL,
    type VARCHAR(30) NOT NULL,
    card_number VARCHAR(50),
    barcode VARCHAR(100),
    expiration DATE
);

CREATE TABLE address (
    id SERIAL PRIMARY KEY,
    zip_code CHAR(9) NOT NULL,
    street VARCHAR(100) NOT NULL,
    number VARCHAR(10) NOT NULL,
    neighborhood VARCHAR(50) NOT NULL,
    complement VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state CHAR(2) NOT NULL
);

CREATE TABLE order_ (
    id SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL,
    total DECIMAL(19, 2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    payment_id INTEGER NOT NULL,
    address_id INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    finalized_at TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (payment_id) REFERENCES payment (id),
    FOREIGN KEY (address_id) REFERENCES address (id)
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(19, 2) NOT NULL,
    photo VARCHAR,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE product_category (
    product_id INTEGER NOT NULL,
    category_id INTEGER NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE order_item (
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    product_price DECIMAL(19, 2) NOT NULL,
    quantity INTEGER NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES order_ (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);
