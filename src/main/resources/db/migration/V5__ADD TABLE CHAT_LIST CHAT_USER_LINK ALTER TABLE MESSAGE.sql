CREATE TABLE `chat_list`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id 自增',
  `link_id` int NULL COMMENT '用户聊天关系表主键',
  `from_id` int NULL COMMENT '发送方id',
  `to_id` int NULL COMMENT '接受方id',
  `from_window` tinyint NULL DEFAULT NULL COMMENT '发送方是否在窗口',
  `to_window` tinyint NULL DEFAULT NULL COMMENT '接收方是否在窗口',
  `unread` int NULL COMMENT '未读数',
  `status` tinyint NULL DEFAULT NULL COMMENT '列表状态。是否删除',
  PRIMARY KEY (`id`),
  INDEX(`link_id`) USING BTREE,
  INDEX(`from_id`) USING BTREE,
  INDEX(`to_id`) USING BTREE,
  INDEX(`id`) USING BTREE
) COMMENT = '聊天列表表';

CREATE TABLE `chat_user_link`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `from_id` int NOT NULL COMMENT '发送方id，用户表主键',
  `to_id` int NOT NULL COMMENT '接收方id，用户表主键',
  `create_time` datetime NULL COMMENT '创建关联时间',
  PRIMARY KEY (`id`),
  INDEX(`from_id`) USING BTREE,
  INDEX(`to_id`) USING BTREE,
  INDEX(`id`) USING BTREE
) COMMENT = '用户聊天关系表';

ALTER TABLE `chat_list` ADD CONSTRAINT `chat_list_list_id` FOREIGN KEY (`link_id`) REFERENCES `chat_user_link` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `chat_list` ADD CONSTRAINT `chat_list_from_id` FOREIGN KEY (`from_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `chat_list` ADD CONSTRAINT `caht_list_to_id` FOREIGN KEY (`to_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `chat_user_link` ADD CONSTRAINT `chat_user_link_from_id` FOREIGN KEY (`from_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `chat_user_link` ADD CONSTRAINT `chat_user_link_to_id` FOREIGN KEY (`to_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `jobsite`.`message` 
ADD COLUMN `link_id` int NOT NULL COMMENT '用户聊天关系表主键' AFTER `id`,
ADD COLUMN `type` int NULL COMMENT '类型' AFTER `send_time`,
ADD COLUMN `is_latest` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '是否是最后一条消息' AFTER `type`,
ADD INDEX `message_id`(`id`) USING BTREE,
ADD INDEX `message_link_id`(`link_id`) USING BTREE,
ADD CONSTRAINT `message_link_id` FOREIGN KEY (`link_id`) REFERENCES `jobsite`.`chat_user_link` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `jobsite`.`chat_list`
MODIFY COLUMN `link_id` int(11) NOT NULL COMMENT '用户聊天关系表主键' AFTER `id`,
MODIFY COLUMN `from_id` int(11) NOT NULL COMMENT '发送方id' AFTER `link_id`,
MODIFY COLUMN `to_id` int(11) NOT NULL COMMENT '接受方id' AFTER `from_id`;