ALTER TABLE `jobsite`.`order`
    ADD COLUMN `employer_id` int(255) NOT NULL COMMENT '订单所属雇佣者id' AFTER `end_time`,
    ADD COLUMN `employee_id` int NOT NULL COMMENT '订单所属找工作者id' AFTER `employer_id`,
    MODIFY COLUMN `state` enum('0','1','2','3') CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单状态' AFTER `job_id`;