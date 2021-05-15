ALTER TABLE `jobsite`.`comment`
MODIFY COLUMN `score` double(11, 2) NULL DEFAULT NULL COMMENT '评分' AFTER `content`;