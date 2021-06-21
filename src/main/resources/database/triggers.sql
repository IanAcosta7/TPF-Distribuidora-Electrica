CREATE TRIGGER tub_adjustment_fee BEFORE UPDATE
ON tariffs
FOR EACH ROW
BEGIN

    -- Cursor Values
    DECLARE em_id INT;

    -- Declare Cursor
    DECLARE finished BOOLEAN DEFAULT FALSE;
    DECLARE addresses_cursor CURSOR FOR SELECT A.electric_meter_id FROM addresses A WHERE A.tariff_id = NEW.id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = TRUE;

    OPEN addresses_cursor;
        addresses_cursor : LOOP

            -- Iterate
            FETCH addresses_cursor INTO em_id;
            IF finished THEN
                LEAVE addresses_cursor;
            END IF;

            -- Work
            CALL sp_create_adjustment_bill(em_id, OLD.tariff_value, NEW.tariff_value);

        END LOOP addresses_cursor;
    CLOSE addresses_cursor;

END;
