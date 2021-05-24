DROP DATABASE udee;
CREATE DATABASE udee;
USE udee;

CREATE TABLE IF NOT EXISTS tariff_types (
    id INT NOT NULL AUTO_INCREMENT,
    typeName VARCHAR(50),
    CONSTRAINT PK_tariff_types PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tariffs (
    id INT NOT NULL AUTO_INCREMENT,
    tariffTypeId INT,
    tariffValue FLOAT,
    CONSTRAINT PK_tariffs PRIMARY KEY (id),
    CONSTRAINT FK_tariffs_tariff_types FOREIGN KEY (tariffTypeId) REFERENCES tariff_types(id)
);

CREATE TABLE IF NOT EXISTS user_types (
    id INT NOT NULL AUTO_INCREMENT,
    typeName VARCHAR(50),
    CONSTRAINT PK_user_types PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    userTypeId INT,
    username VARCHAR(50),
    password VARCHAR(128),
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    CONSTRAINT PK_users PRIMARY KEY (id),
    CONSTRAINT FK_users_user_types FOREIGN KEY (userTypeId) REFERENCES user_types(id)
);

CREATE TABLE IF NOT EXISTS meter_brands (
    id INT NOT NULL AUTO_INCREMENT,
    brandName VARCHAR(50),
    CONSTRAINT PK_meter_brands PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS meter_models (
    id INT NOT NULL AUTO_INCREMENT,
    brandId INT,
    modelName VARCHAR(50),
    CONSTRAINT PK_meter_models PRIMARY KEY (id),
    CONSTRAINT FL_meter_models_brands FOREIGN KEY (brandId) REFERENCES meter_brands(id)
);

CREATE TABLE IF NOT EXISTS electric_meters (
    id INT NOT NULL AUTO_INCREMENT,
    serialNumber VARCHAR(50),
    modelId INT NOT NULL,
    CONSTRAINT PK_electric_meters PRIMARY KEY (id),
    CONSTRAINT FK_electric_meters_meter_models FOREIGN KEY (modelId) REFERENCES meter_models(id)
);

CREATE TABLE IF NOT EXISTS addresses (
    id INT NOT NULL AUTO_INCREMENT,
    tariffId INT,
    electricMeterId INT,
    street VARCHAR(50),
    number VARCHAR(50),
    CONSTRAINT PK_addresses PRIMARY KEY (id),
    CONSTRAINT FK_addresses_tariffs FOREIGN KEY (tariffId) REFERENCES tariffs(id),
    CONSTRAINT FK_addresses_electric_meters FOREIGN KEY (electricMeterId) REFERENCES electric_meters(id),
    CONSTRAINT UNQ_electric_meter_id UNIQUE (electricMeterId)
);

CREATE TABLE IF NOT EXISTS bills (
    id INT NOT NULL AUTO_INCREMENT,
    addressId INT,
    clientId INT,
    billDate DATE,
    amountPayed FLOAT,
    initialMeasure FLOAT,
    initialMeasureDateTime DATETIME,
    finalMeasure FLOAT,
    finalMeasureDateTime DATETIME,
    consumption FLOAT,
    total FLOAT,
    CONSTRAINT PK_bills PRIMARY KEY (id),
    CONSTRAINT FK_bills_address FOREIGN KEY (addressId) REFERENCES addresses(id),
    CONSTRAINT FK_bills_users FOREIGN KEY (clientId) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS measurements (
    id INT NOT NULL AUTO_INCREMENT,
    billId INT,
    electricMeterId INT,
    measure FLOAT,
    measureDateTime DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_measurements PRIMARY KEY (id),
    CONSTRAINT FK_measurements_bills FOREIGN KEY (billId) REFERENCES bills(id)
);