ALTER TABLE `jobsite`.`job`
ADD COLUMN `skill` varchar(255) NULL COMMENT '所需要的技术' AFTER `required`,
ADD COLUMN `work_time` int NULL COMMENT '每天工作时间' AFTER `skill`,
ADD COLUMN `position` varchar(255) NULL COMMENT '职位' AFTER `work_time`;