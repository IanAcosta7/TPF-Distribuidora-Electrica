DROP DATABASE udee;
CREATE DATABASE udee;
USE udee;

CREATE TABLE IF NOT EXISTS tariff_types (
    id INT NOT NULL AUTO_INCREMENT,
    type_name VARCHAR(50),
    CONSTRAINT PK_tariff_types PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tariffs (
    id INT NOT NULL AUTO_INCREMENT,
    tariff_type_id INT,
    tariff_value FLOAT,
    CONSTRAINT PK_tariffs PRIMARY KEY (id),
    CONSTRAINT FK_tariffs_tariff_types FOREIGN KEY (tariff_type_id) REFERENCES tariff_types(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_types (
    id INT NOT NULL AUTO_INCREMENT,
    type_name VARCHAR(50),
    CONSTRAINT PK_user_types PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    user_type_id INT,
    username VARCHAR(50),
    password VARCHAR(128),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    CONSTRAINT PK_users PRIMARY KEY (id),
    CONSTRAINT FK_users_user_types FOREIGN KEY (user_type_id) REFERENCES user_types(id)
);

CREATE TABLE IF NOT EXISTS meter_brands (
    id INT NOT NULL AUTO_INCREMENT,
    brand_name VARCHAR(50),
    CONSTRAINT PK_meter_brands PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS meter_models (
    id INT NOT NULL AUTO_INCREMENT,
    brand_id INT,
    model_name VARCHAR(50),
    CONSTRAINT PK_meter_models PRIMARY KEY (id),
    CONSTRAINT FL_meter_models_brands FOREIGN KEY (brand_id) REFERENCES meter_brands(id)
);

CREATE TABLE IF NOT EXISTS electric_meters (
    id INT NOT NULL AUTO_INCREMENT,
    model_id INT,
    serial_number VARCHAR(50),
    password VARCHAR(50),
    CONSTRAINT PK_electric_meters PRIMARY KEY (id),
    CONSTRAINT FK_electric_meters_meter_models FOREIGN KEY (model_id) REFERENCES meter_models(id)
);

CREATE TABLE IF NOT EXISTS addresses (
    id INT NOT NULL AUTO_INCREMENT,
    tariff_id INT,
    electric_meter_id INT,
    street VARCHAR(50),
    number VARCHAR(50),
    CONSTRAINT PK_addresses PRIMARY KEY (id),
    CONSTRAINT FK_addresses_tariffs FOREIGN KEY (tariff_id) REFERENCES tariffs(id) ON DELETE CASCADE,
    CONSTRAINT FK_addresses_electric_meters FOREIGN KEY (electric_meter_id) REFERENCES electric_meters(id) ON DELETE CASCADE,
    CONSTRAINT UNQ_electric_meter_id UNIQUE (electric_meter_id)
);

CREATE TABLE IF NOT EXISTS bills (
    id INT NOT NULL AUTO_INCREMENT,
    address_id INT,
    client_id INT,
    bill_date DATE,
    amount_payed FLOAT,
    initial_measure FLOAT,
    initial_measure_date_time DATETIME,
    final_measure FLOAT,
    final_measure_date_time DATETIME,
    consumption FLOAT,
    total FLOAT,
    CONSTRAINT PK_bills PRIMARY KEY (id),
    CONSTRAINT FK_bills_address FOREIGN KEY (address_id) REFERENCES addresses(id),
    CONSTRAINT FK_bills_users FOREIGN KEY (client_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS measurements (
    id INT NOT NULL AUTO_INCREMENT,
    bill_id INT,
    electric_meter_id INT,
    measure FLOAT NOT NULL,
    measure_date_time DATETIME,
    CONSTRAINT PK_measurements PRIMARY KEY (id),
    CONSTRAINT FK_measurements_bills FOREIGN KEY (bill_id) REFERENCES bills(id) ON DELETE CASCADE
);

INSERT INTO user_types (type_name) values ('ROLE_EMPLOYEE');
INSERT INTO users (user_type_id, username, password, first_name, last_name) values (1, 'Test', 'Test', 'Test', 'Test');