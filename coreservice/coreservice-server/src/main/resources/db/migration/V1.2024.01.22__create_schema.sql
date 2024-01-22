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
                                                PRIMARY KEY (`category_id`),
                                                UNIQUE (`name`)
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
                                            `amount` float NOT NULL,
                                            `is_essential` bit(1) NOT NULL,
                                            `name` varchar(50) NOT NULL,
                                            `item_stock_qty` int NOT NULL,
                                            `stocked_dt` datetime(6) DEFAULT NULL,
                                            `unit` varchar(255) DEFAULT NULL,
                                            `type_id` bigint DEFAULT NULL,
                                            PRIMARY KEY (`id`),
                                            UNIQUE (`name`),
                                            FOREIGN KEY (`type_id`) REFERENCES `itemtype` (`type_id`)
);

-- recipe
CREATE TABLE programmedhousehold.`recipe` (
                                              `id` bigint NOT NULL AUTO_INCREMENT,
                                              `cook_time` int DEFAULT NULL,
                                              `cuisine` varchar(255) NOT NULL,
                                              `description` varchar(100) DEFAULT NULL,
                                              `health_label` varchar(255) DEFAULT NULL,
                                              `instructions` tinytext NOT NULL,
                                              `is_active` bit(1) NOT NULL,
                                              `prep_time` int DEFAULT NULL,
                                              `servings` int DEFAULT NULL,
                                              `title` varchar(50) NOT NULL,
                                              `user_id` bigint DEFAULT NULL,
                                              `category_id` bigint DEFAULT NULL,
                                              `scheduled_dt` datetime(6) DEFAULT NULL,
                                              UNIQUE (`title`),
                                              PRIMARY KEY (`id`),
                                              FOREIGN KEY (`user_id`) REFERENCES `appuser` (`user_id`),
                                              FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
);

-- recipe_item
CREATE TABLE programmedhousehold.`recipe_item` (
                                                   `item_id` bigint NOT NULL,
                                                   `recipe_id` bigint NOT NULL,
                                                   `item_qty` int NOT NULL,
                                                   `unit` varchar(255) NOT NULL,
                                                   PRIMARY KEY (`item_id`,`recipe_id`),
                                                   FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`),
                                                   FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
);

