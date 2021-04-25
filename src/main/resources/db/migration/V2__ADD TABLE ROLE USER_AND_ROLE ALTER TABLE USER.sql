ALTER TABLE `user`
DROP COLUMN `identity`,
ADD COLUMN `enable` tinyint(3) NULL DEFAULT 1 COMMENT '是否有效：0无效，1有效' AFTER `headurl`,
ADD COLUMN `locked` tinyint(3) NULL DEFAULT 0 COMMENT '是否锁住：0否，1是' AFTER `enable`;

CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(32) NULL DEFAULT NULL COMMENT '角色编码',
  `nameZh` varchar(32) NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) COMMENT = '角色表';


CREATE TABLE `user_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `uid` int NULL DEFAULT NULL COMMENT '用户ID',
  `rid` int NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) COMMENT = '用户角色表';

ALTER TABLE `user_role` ADD CONSTRAINT `user_role_uid` FOREIGN KEY (`uid`) REFERENCES `user` (`id`);
ALTER TABLE `user_role` ADD CONSTRAINT `user_role_rid` FOREIGN KEY (`rid`) REFERENCES `role` (`id`);


