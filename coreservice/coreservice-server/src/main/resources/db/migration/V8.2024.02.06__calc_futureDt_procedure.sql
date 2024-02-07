DELIMITER //

CREATE PROCEDURE CalculateFutureDate(IN desired_day VARCHAR(20), OUT future_date_out DATE)
BEGIN
    DECLARE desired_day_value INT;

    -- Map the string representation of the desired day to its corresponding integer value
    CASE desired_day
        WHEN 'Monday' THEN SET desired_day_value = 2;
        WHEN 'Tuesday' THEN SET desired_day_value = 3;
        WHEN 'Wednesday' THEN SET desired_day_value = 4;
        WHEN 'Thursday' THEN SET desired_day_value = 5;
        WHEN 'Friday' THEN SET desired_day_value = 6;
        WHEN 'Saturday' THEN SET desired_day_value = 7;
        WHEN 'Sunday' THEN SET desired_day_value = 1;
        ELSE SET desired_day_value = NULL; -- Handle invalid input
        END CASE;

    -- Calculate future date based on the integer value of the desired day
    SET future_date_out = (
        CASE
            WHEN DAYOFWEEK(CURRENT_DATE()) < desired_day_value THEN DATE_ADD(CURRENT_DATE(), INTERVAL (desired_day_value - DAYOFWEEK(CURRENT_DATE())) DAY)
            ELSE DATE_ADD(CURRENT_DATE(), INTERVAL (7 - DAYOFWEEK(CURRENT_DATE()) + desired_day_value) DAY)
            END
        );

END //

DELIMITER ;;