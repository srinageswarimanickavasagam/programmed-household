DELIMITER //

CREATE PROCEDURE UPDATE_SUBSEQUENT_SCHEDULES()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE category_name VARCHAR(100);
    DECLARE recipe_id BIGINT;
    DECLARE schedule_Day VARCHAR(10);
    DECLARE schedule_Meal VARCHAR(10);
    DECLARE recent_scheduled_Dt DATETIME(6);
    DECLARE future_schedule_Dt DATETIME(6);

    -- Declare cursor to fetch rows from the table
    DECLARE cur CURSOR FOR SELECT c.name,c.day,c.meal,r.id FROM recipe r join programmedhousehold.category c on r.category_id = c.category_id WHERE DATE(scheduled_dt) = DATE_SUB(CURDATE(), INTERVAL 1 DAY);

    -- Declare handler for NOT FOUND condition
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- Open the cursor
    OPEN cur;

    -- Loop through each row
    read_loop: LOOP
        FETCH cur INTO category_name,schedule_Day,schedule_Meal,recipe_id;

        IF done THEN
            LEAVE read_loop;
        END IF;

        SELECT MAX(r.scheduled_dt) INTO recent_scheduled_Dt FROM recipe r JOIN programmedhousehold.category c ON c.category_id = r.category_id WHERE c.meal = schedule_Meal AND c.day = schedule_Day LIMIT 1;
        SET future_schedule_Dt = DATE_ADD(recent_scheduled_Dt,INTERVAL 7 DAY); -- Example calculation
        UPDATE recipe SET scheduled_dt = future_schedule_Dt WHERE id = recipe_id;
    END LOOP;
    -- Close the cursor
    CLOSE cur;
    CALL programmedhousehold.HANDLE_UNSCHEDULED_RECIPES();
END //

DELIMITER ;;