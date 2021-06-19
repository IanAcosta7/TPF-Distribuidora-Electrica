--DROP DATABASE udee;
--CREATE DATABASE udee;
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
    user_type_id INT NOT NULL,
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
    client_id INT,
    street VARCHAR(50),
    address_number VARCHAR(50),
    CONSTRAINT PK_addresses PRIMARY KEY (id),
    CONSTRAINT FK_addresses_tariffs FOREIGN KEY (tariff_id) REFERENCES tariffs(id),
    CONSTRAINT FK_addresses_electric_meters FOREIGN KEY (electric_meter_id) REFERENCES electric_meters(id),
    CONSTRAINT FK_addresses_users FOREIGN KEY (client_id) REFERENCES users(id),
    CONSTRAINT UNQ_electric_meter_id UNIQUE (electric_meter_id)
);

CREATE TABLE IF NOT EXISTS bills (
    id INT NOT NULL AUTO_INCREMENT,
    address_id INT,
    initial_measure_id INT,
    final_measure_id INT,
    bill_date DATE,
    amount_payed FLOAT,
    consumption FLOAT,
    total FLOAT,
    CONSTRAINT PK_bills PRIMARY KEY (id),
    CONSTRAINT FK_bills_address FOREIGN KEY (address_id) REFERENCES addresses(id),
    CONSTRAINT FK_bills_initial_measurement FOREIGN KEY (initial_measure_id) REFERENCES measurements(id),
    CONSTRAINT FK_bills_final_measurement FOREIGN KEY (final_measure_id) REFERENCES measurements(id)
);

CREATE TABLE IF NOT EXISTS measurements (
    id INT NOT NULL AUTO_INCREMENT,
    bill_id INT,
    electric_meter_id INT,
    measure FLOAT NOT NULL,
    measure_date_time DATETIME,
    price FLOAT,
    CONSTRAINT PK_measurements PRIMARY KEY (id),
    CONSTRAINT FK_measurements_bills FOREIGN KEY (bill_id) REFERENCES bills(id) ON DELETE CASCADE
);

--------------------- INDEXES ---------------------
CREATE INDEX IDX_measurements_electric_meters ON measurements (electric_meter_id) USING BTREE;

---------------- STORED PROCEDURES ----------------
DELIMITER ;;
DROP PROCEDURE IF EXISTS sp_get_top_consumers;
CREATE PROCEDURE sp_get_top_consumers (IN _after DATETIME, IN _before DATETIME)
BEGIN
    SELECT * FROM (
        SELECT u.id, SUM(m.measure) consumed FROM users u
        INNER JOIN addresses a ON u.id = a.client_id
        INNER JOIN electric_meters em ON a.electric_meter_id = em.id
        INNER JOIN measurements m ON em.id = m.electric_meter_id
        WHERE m.measure_date_time BETWEEN _after AND _before
        GROUP BY id
        LIMIT 10
    ) user_consumptions
    ORDER BY consumed DESC;
END;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE sp_invoice_all_addresses()
BEGIN

    -- Cursor Values
    DECLARE a_id INT;

    -- Declare Cursor
    DECLARE finished BOOLEAN DEFAULT FALSE;
    DECLARE error_thrown BOOLEAN DEFAULT FALSE;
    DECLARE addresses_cursor CURSOR FOR SELECT A.id FROM addresses A;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = TRUE;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET error_thrown = TRUE;

    START TRANSACTION;

    OPEN addresses_cursor;
        addresses_cursor : LOOP

            -- Iterate
            FETCH addresses_cursor INTO a_id;
            IF finished THEN
                LEAVE addresses_cursor;
            END IF;

            -- Work
            CALL sp_invoice_address(a_id);

        END LOOP addresses_cursor;
    CLOSE addresses_cursor;

    IF error_thrown THEN
        ROLLBACK;
    ELSE
        COMMIT;
    END IF;

END;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE sp_invoice_address(IN _address_id INT)
BEGIN

    DECLARE _initial_measure INT;
    DECLARE _last_measure INT;
    DECLARE _consumption INT;
    DECLARE _total INT;

    CREATE TEMPORARY TABLE tmp_measurements_from_meter
    (
        SELECT M.* FROM measurements M
        INNER JOIN electric_meters EM ON M.electric_meter_id = EM.id
        INNER JOIN addresses A ON EM.id = A.electric_meter_id AND A.id = _address_id
        WHERE M.bill_id IS NULL
    );

    SET _initial_measure = (SELECT MFM.id FROM tmp_measurements_from_meter MFM ORDER BY MFM.measure, MFM.measure_date_time, MFM.id LIMIT 1);
    SET _last_measure = (SELECT MFM.id FROM tmp_measurements_from_meter MFM ORDER BY MFM.measure DESC, MFM.measure_date_time DESC, MFM.id DESC LIMIT 1);
    SET _consumption = (SELECT MFM.measure FROM tmp_measurements_from_meter MFM WHERE MFM.id = _last_measure);
    SET _total = (SELECT SUM(MFM.price) FROM tmp_measurements_from_meter MFM);

    IF (SELECT COUNT(*) FROM tmp_measurements_from_meter) > 0 THEN
        INSERT INTO bills
        (
        address_id,
        bill_date,
        amount_payed,
        initial_measure,
        final_measure,
        consumption,
        total
        )
        VALUES
        (
        _address_id,
        CURRENT_DATE(),
        0,
        _initial_measure,
        _last_measure,
        _consumption,
        _total
        );
    END IF;

    DROP TABLE tmp_measurements_from_meter;

END ;;
DELIMITER ;