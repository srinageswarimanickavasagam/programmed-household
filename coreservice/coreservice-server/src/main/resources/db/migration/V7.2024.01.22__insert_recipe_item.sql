SET @recipe_id := (SELECT id FROM programmedhousehold.recipe WHERE title = 'Ven Pongal');

INSERT INTO programmedhousehold.recipe_item (recipe_id,item_id, required_qty, unit, culinary_step)
VALUES (@recipe_id ,31,0.5,'CUP','PRESSURE_COOK'),
       (@recipe_id,21,0.25,'CUP','PRESSURE_COOK'),
       (@recipe_id,184,1.5,'TEASPOON','PRESSURE_COOK'),
       (@recipe_id,185,4,'CUP','PRESSURE_COOK'),
       (@recipe_id ,26,2,'TABLESPOON','TEMPER'),
       (@recipe_id,59,1,'TEASPOON','TEMPER'),
       (@recipe_id,60,0.5,'TEASPOON','TEMPER'),
       (@recipe_id,110,0.5,'TEASPOON','TEMPER'),
       (@recipe_id,112,2,'COUNT','TEMPER'),
       (@recipe_id,94,10,'COUNT','TEMPER'),
       (@recipe_id,49,0.3,'TEASPOON','TEMPER');

SET @recipe_id := (SELECT id FROM programmedhousehold.recipe WHERE title = 'Rava Pongal');

INSERT INTO programmedhousehold.recipe_item (recipe_id,item_id, required_qty, unit, culinary_step)
VALUES (@recipe_id,21,0.25,'CUP','PRESSURE_COOK'),
       (@recipe_id,26,3,'TABLESPOON','FRY'),
       (@recipe_id,59,0.5,'TEASPOON','FRY'),
       (@recipe_id,60,1,'TEASPOON','FRY'),
       (@recipe_id,110,1,'TEASPOON','FRY'),
       (@recipe_id,106,1,'TEASPOON','FRY'),
       (@recipe_id,94,20,'COUNT','FRY'),
       (@recipe_id,67,0.75,'CUP','OTHERS'),
       (@recipe_id,184,1.5,'TEASPOON','OTHERS'),
       (@recipe_id,49,0.3,'TEASPOON','OTHERS'),
       (@recipe_id,185,2,'CUP','OTHERS');

SET @recipe_id := (SELECT id FROM programmedhousehold.recipe WHERE title = 'Wheat Poori-Channa masala');

INSERT INTO programmedhousehold.recipe_item (recipe_id,item_id, required_qty, unit, culinary_step)
VALUES (@recipe_id,63,1,'TABLESPOON','FRY'),
       (@recipe_id,59,1,'TEASPOON','FRY'),
       (@recipe_id,87,2,'COUNT','FRY'),
       (@recipe_id,91,1,'COUNT','FRY'),
       (@recipe_id,108,1,'COUNT','CHOP'),
       (@recipe_id,187,1,'TEASPOON','FRY'),
       (@recipe_id,1,0.75,'CUP','PRESSURE_COOK'),
       (@recipe_id,109,2,'COUNT','GRIND'),
       (@recipe_id,38,1.5,'TEASPOON','OTHERS'),
       (@recipe_id,46,0.75,'TEASPOON','OTHERS'),
       (@recipe_id,36,0.5,'TEASPOON','OTHERS'),
       (@recipe_id,37,1,'TEASPOON','OTHERS'),
       (@recipe_id,39,1,'TEASPOON','OTHERS'),
       (@recipe_id,81,1,'TEASPOON','OTHERS'),
       (@recipe_id,107,1,'TEASPOON','OTHERS');

SET @recipe_id := (SELECT id FROM programmedhousehold.recipe WHERE title = 'Ragi Poori-Potato masala');

INSERT INTO programmedhousehold.recipe_item (recipe_id,item_id, required_qty, unit, culinary_step)
VALUES (@recipe_id,63,2,'TABLESPOON','FRY'),
       (@recipe_id,20,1,'TEASPOON','FRY'),
       (@recipe_id,18,1,'TEASPOON','FRY'),
       (@recipe_id,59,0.5,'TEASPOON','FRY'),
       (@recipe_id,53,0.5,'TEASPOON','FRY'),
       (@recipe_id,108,1,'COUNT','CHOP'),
       (@recipe_id,112,4,'COUNT','CHOP'),
       (@recipe_id,110,1,'TEASPOON','CHOP'),
       (@recipe_id,106,1,'TEASPOON','FRY'),
       (@recipe_id,49,0.3,'TEASPOON','FRY'),
       (@recipe_id,109,1,'COUNT','CHOP'),
       (@recipe_id,4,0.25,'CUP','SOAK'),
       (@recipe_id,36,0.5,'TEASPOON','OTHERS'),
       (@recipe_id,184,1.5,'TEASPOON','OTHERS'),
       (@recipe_id,22,3,'TABLESPOON','OTHERS'),
       (@recipe_id,118,2,'COUNT','PRESSURE_COOK'),
       (@recipe_id,61,0.25,'TEASPOON','GRIND'),
       (@recipe_id,39,0.5,'TEASPOON','OTHERS'),
       (@recipe_id,107,1,'TEASPOON','OTHERS');

SET @recipe_id := (SELECT id FROM programmedhousehold.recipe WHERE title = 'Aloo Poori-Green peas masala');

INSERT INTO programmedhousehold.recipe_item (recipe_id,item_id, required_qty, unit, culinary_step)
VALUES (@recipe_id,118,3,'COUNT','PRESSURE_COOK'),
       (@recipe_id,4,0.5,'CUP','PRESSURE_COOK'),
       (@recipe_id,185,1,'CUP','PRESSURE_COOK'),
       (@recipe_id,186,0.25,'CUP','GRIND'),
       (@recipe_id,61,0.5,'TEASPOON','GRIND'),
       (@recipe_id,63,2,'TABLESPOON','FRY'),
       (@recipe_id,61,0.5,'TEASPOON','FRY'),
       (@recipe_id,106,1,'TEASPOON','FRY'),
       (@recipe_id,108,1,'COUNT','CHOP'),
       (@recipe_id,187,1,'TEASPOON','FRY'),
       (@recipe_id,37,1,'TEASPOON','OTHERS'),
       (@recipe_id,38,0.5,'TEASPOON','OTHERS'),
       (@recipe_id,39,0.5,'TEASPOON','OTHERS'),
       (@recipe_id,36,0.5,'TEASPOON','OTHERS');


































