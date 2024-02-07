SET @recipe_id := (SELECT id FROM programmedhousehold.recipe WHERE title = 'Ven Pongal');

INSERT INTO programmedhousehold.recipe_item (recipe_id,item_id, required_qty, culinary_step)
VALUES (@recipe_id ,31,0.5,'PRESSURE_COOK'),
       (@recipe_id,21,0.25,'PRESSURE_COOK'),
       (@recipe_id,184,1.5,'PRESSURE_COOK'),
       (@recipe_id,185,4,'PRESSURE_COOK'),
       (@recipe_id ,26,2,'TEMPER'),
       (@recipe_id,59,1,'TEMPER'),
       (@recipe_id,60,0.5,'TEMPER'),
       (@recipe_id,110,0.5,'TEMPER'),
       (@recipe_id,112,2,'TEMPER'),
       (@recipe_id,94,10,'TEMPER'),
       (@recipe_id,49,0.3,'TEMPER');

SET @recipe_id := (SELECT id FROM programmedhousehold.recipe WHERE title = 'Rava Pongal');

INSERT INTO programmedhousehold.recipe_item (recipe_id,item_id, required_qty, culinary_step)
VALUES (@recipe_id,21,0.25,'PRESSURE_COOK'),
       (@recipe_id,26,3,'FRY'),
       (@recipe_id,59,0.5,'FRY'),
       (@recipe_id,60,1,'FRY'),
       (@recipe_id,110,1,'FRY'),
       (@recipe_id,106,1,'FRY'),
       (@recipe_id,94,20,'FRY'),
       (@recipe_id,67,0.75,'OTHERS'),
       (@recipe_id,184,1.5,'OTHERS'),
       (@recipe_id,49,0.3,'OTHERS'),
       (@recipe_id,185,2,'OTHERS');

SET @recipe_id := (SELECT id FROM programmedhousehold.recipe WHERE title = 'Wheat Poori-Channa masala');

INSERT INTO programmedhousehold.recipe_item (recipe_id,item_id, required_qty, culinary_step)
VALUES (@recipe_id,63,1,'FRY'),
       (@recipe_id,59,1,'FRY'),
       (@recipe_id,87,2,'FRY'),
       (@recipe_id,91,1,'FRY'),
       (@recipe_id,108,1,'CHOP'),
       (@recipe_id,187,1,'FRY'),
       (@recipe_id,1,0.75,'PRESSURE_COOK'),
       (@recipe_id,109,2,'GRIND'),
       (@recipe_id,38,1.5,'OTHERS'),
       (@recipe_id,46,0.75,'OTHERS'),
       (@recipe_id,36,0.5,'OTHERS'),
       (@recipe_id,37,1,'OTHERS'),
       (@recipe_id,39,1,'OTHERS'),
       (@recipe_id,81,1,'OTHERS'),
       (@recipe_id,107,1,'OTHERS');

SET @recipe_id := (SELECT id FROM programmedhousehold.recipe WHERE title = 'Ragi Poori-Potato masala');

INSERT INTO programmedhousehold.recipe_item (recipe_id,item_id, required_qty, culinary_step)
VALUES (@recipe_id,63,2,'FRY'),
       (@recipe_id,20,1,'FRY'),
       (@recipe_id,18,1,'FRY'),
       (@recipe_id,59,0.5,'FRY'),
       (@recipe_id,53,0.5,'FRY'),
       (@recipe_id,108,1,'CHOP'),
       (@recipe_id,112,4,'CHOP'),
       (@recipe_id,110,1,'CHOP'),
       (@recipe_id,106,1,'FRY'),
       (@recipe_id,49,0.3,'FRY'),
       (@recipe_id,109,1,'CHOP'),
       (@recipe_id,4,0.25,'SOAK'),
       (@recipe_id,36,0.5,'OTHERS'),
       (@recipe_id,184,1.5,'OTHERS'),
       (@recipe_id,22,3,'OTHERS'),
       (@recipe_id,118,2,'PRESSURE_COOK'),
       (@recipe_id,61,0.25,'GRIND'),
       (@recipe_id,39,0.5,'OTHERS'),
       (@recipe_id,107,1,'OTHERS');

SET @recipe_id := (SELECT id FROM programmedhousehold.recipe WHERE title = 'Aloo Poori-Green peas masala');

INSERT INTO programmedhousehold.recipe_item (recipe_id,item_id, required_qty, culinary_step)
VALUES (@recipe_id,118,3,'PRESSURE_COOK'),
       (@recipe_id,4,0.5,'PRESSURE_COOK'),
       (@recipe_id,185,1,'PRESSURE_COOK'),
       (@recipe_id,186,0.25,'GRIND'),
       (@recipe_id,61,0.5,'GRIND'),
       (@recipe_id,63,2,'FRY'),
       (@recipe_id,61,0.5,'FRY'),
       (@recipe_id,106,1,'FRY'),
       (@recipe_id,108,1,'CHOP'),
       (@recipe_id,187,1,'FRY'),
       (@recipe_id,37,1,'OTHERS'),
       (@recipe_id,38,0.5,'OTHERS'),
       (@recipe_id,39,0.5,'OTHERS'),
       (@recipe_id,36,0.5,'OTHERS');


































