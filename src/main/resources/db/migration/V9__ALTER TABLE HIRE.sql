ALTER TABLE `jobsite`.`hire` DROP FOREIGN KEY `hire_order_id`;

ALTER TABLE `jobsite`.`hire`
    DROP COLUMN `order_id`,
    ADD COLUMN `id` int NOT NULL AUTO_INCREMENT AFTER `employee_id`,
    ADD COLUMN `job_id` int(11) NOT NULL AFTER `id`,
    ADD COLUMN `created_time` datetime NULL AFTER `job_id`,
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (`id`) USING BTREE,
    ADD CONSTRAINT `hire_job_id` FOREIGN KEY (`job_id`) REFERENCES `jobsite`.`job` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `jobsite`.`orderinfo`
    DROP COLUMN `employer_id`,
    DROP COLUMN `employee_id`;