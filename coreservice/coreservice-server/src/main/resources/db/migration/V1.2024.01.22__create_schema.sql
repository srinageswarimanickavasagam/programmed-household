DROP TABLE IF EXISTS programmedhousehold.`recipe_item`;
DROP TABLE IF EXISTS programmedhousehold.`recipe`;
DROP TABLE IF EXISTS programmedhousehold.`item`;
DROP TABLE IF EXISTS programmedhousehold.`itemtype`;
DROP TABLE IF EXISTS programmedhousehold.`category`;
DROP TABLE IF EXISTS programmedhousehold.`appuser`;

-- appuser
CREATE TABLE programmedhousehold.`appuser` (
                                               `user_id` bigint NOT NULL AUTO_INCREMENT,
                                               `created_at` datetime(6) DEFAULT NULL,
                                               `email` varchar(255) DEFAULT NULL,
                                               `name` varchar(255) DEFAULT NULL,
                                               `provider` varchar(255) DEFAULT NULL,
                                               PRIMARY KEY (`user_id`),
                                               UNIQUE (`email`)
);

-- category
CREATE TABLE programmedhousehold.`category` (
                                                `category_id` bigint NOT NULL AUTO_INCREMENT,
                                                `day` varchar(255) DEFAULT NULL,
                                                `difficulty` varchar(255) NOT NULL,
                                                `meal` varchar(255) NOT NULL,
                                                `name` varchar(50) NOT NULL,
                                                `sidedish` bit(1) NOT NULL,
                                                PRIMARY KEY (`category_id`,`meal`)
);

-- itemtype
CREATE TABLE programmedhousehold.`itemtype` (
                                                `type_id` bigint NOT NULL AUTO_INCREMENT,
                                                `type` varchar(255) NOT NULL,
                                                PRIMARY KEY (`type_id`),
                                                UNIQUE (`type`)
);

-- item
CREATE TABLE programmedhousehold.`item` (
                                            `id` bigint NOT NULL AUTO_INCREMENT,
                                            `amount` DECIMAL(7,2) DEFAULT 0.0,
                                            `is_essential` bit(1) NOT NULL,
                                            `name` varchar(50) NOT NULL,
                                            `item_stock_qty` int DEFAULT 0,
                                            `stocked_dt` datetime(6) DEFAULT NULL,
                                            `unit` varchar(255) DEFAULT NULL,
                                            `type_id` bigint DEFAULT NULL,
                                            `refill` int DEFAULT 30,
                                            PRIMARY KEY (`id`),
                                            UNIQUE (`name`),
                                            FOREIGN KEY (`type_id`) REFERENCES `itemtype` (`type_id`)
);

-- recipe
CREATE TABLE programmedhousehold.`recipe` (
                                              `id` bigint UNSIGNED DEFAULT (CONV(SUBSTR(UUID(), 1, 16), 16, 10)),
                                              `cook_time` int DEFAULT NULL,
                                              `cuisine` varchar(255) DEFAULT 'INDIAN',
                                              `reference` varchar(255) DEFAULT NULL,
                                              `health_label` varchar(255) DEFAULT NULL,
                                              `instructions` text DEFAULT NULL,
                                              `is_active` bit(1) DEFAULT 1,
                                              `prep_time` int DEFAULT NULL,
                                              `servings` int DEFAULT 3,
                                              `title` varchar(50) NOT NULL,
                                              `user_id` bigint DEFAULT 1,
                                              `category_id` bigint NOT NULL,
                                              `scheduled_dt` datetime(6) DEFAULT NULL,
                                              `notes` varchar(255) DEFAULT NULL,
                                              UNIQUE (`title`),
                                              PRIMARY KEY (`id`),
                                              FOREIGN KEY (`user_id`) REFERENCES `appuser` (`user_id`),
                                              FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
);

-- recipe_item
CREATE TABLE programmedhousehold.`recipe_item` (
                                                   `item_id` bigint NOT NULL,
                                                   `recipe_id` bigint UNSIGNED NOT NULL,
                                                   `required_qty` DECIMAL(5,2) NOT NULL,
                                                   `unit` varchar(255) NOT NULL,
                                                   `culinary_step` varchar(20) NOT NULL,
                                                   PRIMARY KEY (`item_id`,`recipe_id`,`culinary_step`),
                                                   FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`),
                                                   FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
);

