DELIMITER ;;
CREATE PROCEDURE sp_create_adjustment_bill(IN _electric_meter_id INT, IN _old_tariff_value FLOAT, IN _new_tariff_value FLOAT)
BEGIN

    -- Cursor Values
    DECLARE m_id INT;
    DECLARE m_price FLOAT;
    DECLARE _new_price FLOAT;
    DECLARE _total_price FLOAT DEFAULT 0;

    -- Declare Cursor
    DECLARE finished BOOLEAN DEFAULT FALSE;
    DECLARE error_thrown BOOLEAN DEFAULT FALSE;
    DECLARE measurements_cursor CURSOR FOR SELECT M.id, M.price FROM measurements M WHERE M.electric_meter_id = _electric_meter_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = TRUE;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET error_thrown = TRUE;

    START TRANSACTION;

    IF (SELECT COUNT(*) FROM measurements M WHERE M.electric_meter_id = _electric_meter_id) THEN
        OPEN measurements_cursor;
            addresses_cursor : LOOP

                -- Iterate
                FETCH measurements_cursor INTO m_id, m_price;
                IF finished THEN
                    LEAVE addresses_cursor;
                END IF;

                -- Work
                SET _new_price = _new_tariff_value * m_price / _old_tariff_value;
                SET _total_price = _total_price + (_new_price - m_price);

                UPDATE measurements M SET M.price = _new_price WHERE M.id = m_id;

            END LOOP addresses_cursor;
        CLOSE measurements_cursor;

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
            (SELECT A.id FROM addresses A WHERE A.electric_meter_id = _electric_meter_id),
            CURRENT_DATE(),
            0,
            (SELECT M.id FROM measurements M WHERE M.electric_meter_id = _electric_meter_id ORDER BY M.measure, M.measure_date_time, M.id LIMIT 1),
            (SELECT M.id FROM measurements M WHERE M.electric_meter_id = _electric_meter_id ORDER BY M.measure DESC, M.measure_date_time DESC, M.id DESC LIMIT 1),
            0,
            _total_price
        );
    END IF;

    IF error_thrown THEN
        ROLLBACK;
    ELSE
        COMMIT;
    END IF;

END;;
DELIMITER ;

DELIMITER ;;
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

        UPDATE measurements M SET M.bill_id = LAST_INSERT_ID() WHERE M.id IN (SELECT MFM.id FROM tmp_measurements_from_meter MFM);
    END IF;

    DROP TABLE tmp_measurements_from_meter;

END ;;
DELIMITER ;