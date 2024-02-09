DELIMITER //

CREATE PROCEDURE HANDLE_UNSCHEDULED_RECIPES()
BEGIN
    DECLARE done_outer INT DEFAULT FALSE;
    DECLARE done_inner INT DEFAULT FALSE;
    DECLARE unscheduled_Day VARCHAR(10);
    DECLARE unscheduled_Meal VARCHAR(10);

    Block1:
    BEGIN
        -- Declare outer cursor to fetch distinct day and meal which is unscheduled
        DECLARE unscheduled_recipe_cur CURSOR FOR
            SELECT DISTINCT c.day, c.meal
            FROM programmedhousehold.recipe r
                     JOIN programmedhousehold.category c ON r.category_id = c.category_id
            WHERE c.day != 'NA' AND c.name != 'Leftover'
              AND r.is_active
              AND r.scheduled_dt IS NULL;

        -- Declare handler for NOT FOUND condition for outer cursor
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_outer = TRUE;

        -- Open outer cursor
        OPEN unscheduled_recipe_cur;

        -- Loop through distinct day and meal
        read_outer_loop:
        LOOP
            FETCH unscheduled_recipe_cur INTO unscheduled_Day, unscheduled_Meal;
            IF done_outer THEN
                LEAVE read_outer_loop;
            END IF;

            -- reset done_inner flag before opening inner cursor
            SET done_inner = FALSE;

            Block2:
            BEGIN
                -- Declare inner variables
                DECLARE row_num INT;
                DECLARE category_name VARCHAR(100);
                DECLARE recipe_id BIGINT;
                DECLARE reschedule_Day VARCHAR(10);
                DECLARE reschedule_Meal VARCHAR(10);
                DECLARE recent_scheduled_Dt DATETIME(6);
                DECLARE future_schedule_Dt DATETIME(6);

                -- Declare inner cursor to reschedule recipes having same meal & day combination
                DECLARE reschedule_recipe_cur CURSOR FOR
                    SELECT ROW_NUMBER() OVER (PARTITION BY c.category_id) AS row_num,
                           c.name,
                           c.day,
                           c.meal,
                           r.id
                    FROM programmedhousehold.recipe r
                             JOIN programmedhousehold.category c ON r.category_id = c.category_id
                    WHERE r.is_active
                      AND c.day = unscheduled_Day
                      AND c.meal = unscheduled_Meal
                      AND c.name != 'Leftover'
                    GROUP BY 2, 3, 4, 5
                    ORDER BY 1, 2;

                -- Declare handler for NOT FOUND condition for inner cursor
                DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_inner = TRUE;

                -- reset schedule for all the recipes having the same meal and day
                UPDATE programmedhousehold.recipe r
                    JOIN programmedhousehold.category c ON r.category_id = c.category_id
                SET r.scheduled_dt = NULL
                WHERE c.meal = unscheduled_Meal
                  AND c.day = unscheduled_Day
                  AND c.name != 'Leftover';

                -- Open inner cursor
                OPEN reschedule_recipe_cur;
                -- Loop through recipes for the current day and meal
                read_inner_loop:
                LOOP
                    FETCH reschedule_recipe_cur INTO row_num, category_name, reschedule_Day, reschedule_Meal, recipe_id;
                    IF done_inner THEN
                        LEAVE read_inner_loop;
                    END IF;

                    SELECT MAX(r.scheduled_dt)
                    INTO recent_scheduled_Dt
                    FROM programmedhousehold.recipe r
                             JOIN programmedhousehold.category c ON c.category_id = r.category_id
                    WHERE c.meal = reschedule_Meal
                      AND c.day = reschedule_Day
                      AND c.name != 'Leftover'
                    LIMIT 1;

                    IF recent_scheduled_Dt IS NOT NULL THEN
                        SET future_schedule_Dt = DATE_ADD(recent_scheduled_Dt, INTERVAL 7 DAY);
                    ELSE
                        CALL programmedhousehold.CALC_SCHEDULE_DT(reschedule_Day, NULL, future_schedule_Dt);
                    END IF;

                    -- Update scheduled_dt for the recipe
                    UPDATE programmedhousehold.recipe
                    SET scheduled_dt = future_schedule_Dt
                    WHERE id = recipe_id;
                END LOOP;
                -- End of inner loop

                -- Close inner cursor
                CLOSE reschedule_recipe_cur;
            END Block2;
        END LOOP;
        -- End of outer loop

        -- Close outer cursor
        CLOSE unscheduled_recipe_cur;
    END Block1;
END //

DELIMITER ;;
