DELIMITER //

CREATE PROCEDURE CALC_SCHEDULE_DT(IN desired_day VARCHAR(20), IN start_date DATE, OUT future_date_out DATE)
BEGIN
    DECLARE desired_day_value INT;
    DECLARE startDt DATE DEFAULT CURRENT_DATE();
    -- Default value as current date

    -- If start_date is provided, replace the default start date with it
    IF start_date IS NOT NULL THEN
        SET startDt = start_date;
    END IF;

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

    -- Calculate future date based on the integer value of the desired day and the start date
    SET future_date_out = (
        IF(DAYOFWEEK(startDt) < desired_day_value,
           DATE_ADD(startDt, INTERVAL (desired_day_value - DAYOFWEEK(startDt)) DAY),
           DATE_ADD(startDt, INTERVAL (7 - DAYOFWEEK(startDt) + desired_day_value) DAY))
        );

END //

DELIMITER ;;
