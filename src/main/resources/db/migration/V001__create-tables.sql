CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    cpf VARCHAR NOT NULL,
    sex VARCHAR(30) NOT NULL,
    birth date ,
    UNIQUE(cpf)
);

CREATE TABLE payment (
    id SERIAL PRIMARY KEY,
    status VARCHAR(30) NOT NULL,
    type VARCHAR(30) NOT NULL,
    card_number VARCHAR(50),
    barcode VARCHAR(100),
    expiration date
);

CREATE TABLE address (
    id SERIAL PRIMARY KEY,
    zip_code VARCHAR(9) NOT NULL,
    street VARCHAR(100) NOT NULL,
    number VARCHAR(10) NOT NULL,
    neighborhood VARCHAR(50) NOT NULL,
    complement VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(2) NOT NULL
);

CREATE TABLE order_ (
    id SERIAL PRIMARY KEY,
    customer_id integer NOT NULL,
    total decimal(19, 2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    payment_id integer NOT NULL,
    address_id integer NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp,
    finalized_at timestamp,
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (payment_id) REFERENCES payment (id),
    FOREIGN KEY (address_id) REFERENCES address (id)
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description bytea,
    price decimal(19, 2) NOT NULL,
    photo bytea,
    active boolean NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp
);

CREATE TABLE product_category (
    product_id integer NOT NULL,
    category_id integer NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE order_item (
    order_id integer NOT NULL,
    product_id integer NOT NULL,
    product_price decimal(19, 2) NOT NULL,
    quantity integer NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES order_ (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);
