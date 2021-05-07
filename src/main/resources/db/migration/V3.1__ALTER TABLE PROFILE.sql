ALTER TABLE `jobsite`.`profile`
ADD COLUMN `completiontime` varchar(255) NULL COMMENT '预计完成时间' AFTER `expertize_realm`,
ADD COLUMN `compensation` varchar(255)  COMMENT '期望薪资' AFTER `expertize_realm`,
MODIFY COLUMN `expertize_level` enum('beginner','intermediate','expert') CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'beginner' COMMENT '专业等级' AFTER `expertize_realm`;