DELIMITER //

CREATE PROCEDURE RESET_ALL_RECIPE_SCHEDULES(IN startDt DATE)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE row_num INT;
    DECLARE category_name VARCHAR(100);
    DECLARE recipe_id BIGINT;
    DECLARE schedule_Day VARCHAR(10);
    DECLARE schedule_Meal VARCHAR(10);
    DECLARE recent_scheduled_Dt DATETIME(6);
    DECLARE future_schedule_Dt DATETIME(6);

    -- reset schedule for all the recipes to refresh the schedules
    UPDATE programmedhousehold.recipe r
        JOIN programmedhousehold.category c ON r.category_id = c.category_id
    SET r.scheduled_dt = NULL
    WHERE scheduled_dt IS NOT NULL;

    Block:
    BEGIN

        -- Declare cursor to fetch rows from the table
        DECLARE cur CURSOR FOR SELECT ROW_NUMBER() OVER (PARTITION BY c.category_id) AS row_num,
                                      c.name,
                                      c.day,
                                      c.meal,
                                      r.id
                               FROM recipe r
                                        join programmedhousehold.category c on r.category_id = c.category_id
                               WHERE day != 'NA' AND c.name != 'Leftover'
                                 AND r.is_active
                               GROUP BY 2, 3, 4, 5
                               ORDER BY 1, 2;

        -- Declare handler for NOT FOUND condition
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

        -- Open the cursor
        OPEN cur;

        -- Loop through each row
        read_loop:
        LOOP
            FETCH cur INTO row_num,category_name,schedule_Day,schedule_Meal,recipe_id;

            IF done THEN
                LEAVE read_loop;
            END IF;

            SELECT MAX(r.scheduled_dt)
            INTO recent_scheduled_Dt
            FROM recipe r
                     JOIN programmedhousehold.category c ON c.category_id = r.category_id
            WHERE c.meal = schedule_Meal
              AND c.day = schedule_Day
              AND c.name != 'Leftover'
            LIMIT 1;
            IF recent_scheduled_Dt IS NOT NULL THEN
                SET future_schedule_Dt = DATE_ADD(recent_scheduled_Dt, INTERVAL 7 DAY); -- Example calculation
            ELSE
                CALL CALC_SCHEDULE_DT(schedule_Day, startDt, future_schedule_Dt); -- Adjust the desired day as needed
            END IF;
            UPDATE recipe SET scheduled_dt = future_schedule_Dt WHERE id = recipe_id;
        END LOOP;

        -- Close the cursor
        CLOSE cur;
    END Block;
END //

DELIMITER ;;