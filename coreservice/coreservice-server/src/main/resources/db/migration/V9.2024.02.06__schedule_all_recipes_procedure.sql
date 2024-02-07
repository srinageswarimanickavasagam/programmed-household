DELIMITER //

CREATE PROCEDURE CALC_SCHEDULED_DT()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE recipe_id BIGINT;
    DECLARE schedule_Day VARCHAR(10);
    DECLARE schedule_Meal VARCHAR(10);
    DECLARE recent_scheduled_Dt DATETIME(6);
    DECLARE future_schedule_Dt DATETIME(6);

    -- Declare cursor to fetch rows from the table
    DECLARE cur CURSOR FOR SELECT id,c2.day,c2.meal FROM recipe r join programmedhousehold.category c2 on c2.category_id = r.category_id;

    -- Declare handler for NOT FOUND condition
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- Open the cursor
    OPEN cur;

    -- Loop through each row
    read_loop: LOOP
        FETCH cur INTO recipe_id,schedule_Day,schedule_Meal;

        IF done THEN
            LEAVE read_loop;
        END IF;

        SELECT MAX(r.scheduled_dt) INTO recent_scheduled_Dt FROM recipe r JOIN programmedhousehold.category c ON c.category_id = r.category_id WHERE c.meal = schedule_Meal AND c.day = schedule_Day LIMIT 1;
        IF recent_scheduled_Dt IS NOT NULL THEN
            SET future_schedule_Dt = DATE_ADD(recent_scheduled_Dt,INTERVAL 7 DAY); -- Example calculation
        ELSE
            CALL CalculateFutureDate(schedule_Day, future_schedule_Dt); -- Adjust the desired day as needed
        END IF;
        UPDATE recipe SET scheduled_dt = future_schedule_Dt WHERE id = recipe_id;
    END LOOP;

    -- Close the cursor
    CLOSE cur;

END //

DELIMITER ;;