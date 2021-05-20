ALTER TABLE `jobsite`.`user_role` DROP FOREIGN KEY `user_role_rid`;

ALTER TABLE `jobsite`.`user_role` DROP FOREIGN KEY `user_role_uid`;

ALTER TABLE `jobsite`.`user_role`
ADD CONSTRAINT `user_role_rid` FOREIGN KEY (`rid`) REFERENCES `jobsite`.`role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
ADD CONSTRAINT `user_role_uid` FOREIGN KEY (`uid`) REFERENCES `jobsite`.`user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE `jobsite`.`orderlog` DROP FOREIGN KEY `orderlog_order_id`;

ALTER TABLE `jobsite`.`orderlog`
ADD CONSTRAINT `orderlog_order_id` FOREIGN KEY (`order_id`) REFERENCES `jobsite`.`orderinfo` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE `jobsite`.`orderlog` DROP FOREIGN KEY `orderlog_user_id`;

ALTER TABLE `jobsite`.`orderlog`
ADD CONSTRAINT `orderlog_user_id` FOREIGN KEY (`user_id`) REFERENCES `jobsite`.`user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;