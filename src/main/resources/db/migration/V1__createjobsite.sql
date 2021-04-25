DROP TABLE if exists account;
CREATE TABLE `account`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户id',
  `money` double NOT NULL COMMENT '账户余额',
  `time` datetime NOT NULL COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) COMMENT = '账户余额';

DROP TABLE if exists category;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '种类ID',
  `name` varchar(255) NULL COMMENT '种类名称',
  `job_id` int NOT NULL COMMENT '所属的job的id，可以满足一个job有多个category',
  PRIMARY KEY (`id`)
) COMMENT = 'job所属的工作种类';

CREATE TABLE `comment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `reviewer_id` int NOT NULL COMMENT '评论者的id',
  `commented_id` int NOT NULL COMMENT '被评论者的id',
  `content` text NULL COMMENT '评论的内容',
  `score` int NULL COMMENT '评分',
  `create_time` datetime NOT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`)
) COMMENT = '评论，包括雇主对应聘者的评论和应聘者对雇主的评论';

DROP TABLE if exists hire;
CREATE TABLE `hire`  (
  `order_id` int NOT NULL,
  `employer_id` int NOT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`order_id`)
);

DROP TABLE if exists job;
CREATE TABLE `job`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'jobid',
  `title` varchar(255) NOT NULL COMMENT 'job名称',
  `employer_id` int NOT NULL COMMENT 'employer的id',
  `description` text NULL COMMENT 'job描述',
  `budget` double(255, 2) NOT NULL COMMENT '项目预算',
  `expertize_level` enum('beginner','intermediate','expert') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'beginner' COMMENT '所需技能等级',
  `ischeck` tinyint NULL DEFAULT 0 COMMENT '是否通过审核',
  `create_time` datetime NOT NULL COMMENT 'job创建时间',
  `required` int NULL COMMENT '所需人数',
  PRIMARY KEY (`id`)
) COMMENT = '招聘者发布的职位';

DROP TABLE if exists message;
CREATE TABLE `message`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '信息的id',
  `sender_id` int NOT NULL COMMENT '发送者id',
  `receiver_id` int NOT NULL COMMENT '接受者id',
  `content` text NULL COMMENT '信息的内容',
  `send_time` datetime NOT NULL COMMENT '信息的发送时间',
  PRIMARY KEY (`id`)
) COMMENT = '聊天双发发送的消息';

DROP TABLE if exists `order`;
CREATE TABLE `order`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_id` int NOT NULL COMMENT '所属job的id',
  `state` enum('unpaid','working','pending','finished') CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '订单状态',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `end_time` datetime NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) COMMENT = '职位达到要求后形成的订单 四种状态分别代表 待预付 工作中 待结算 已完成';

DROP TABLE if exists orderlog;
CREATE TABLE `orderlog`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '工作日志的id',
  `content` varchar(255) NULL COMMENT '工作日志的内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `user_id` int NOT NULL,
  `order_id` int NOT NULL COMMENT '订单的id',
  PRIMARY KEY (`id`)
) COMMENT = '由用户提交的工作日志表';

DROP TABLE if exists profile;
CREATE TABLE `profile`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `biography` text NULL,
  `workexperience` text NULL,
  `expertize_level` enum('beginner','intermedia','expert') CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'beginner' COMMENT '专业等级',
  `expertize_realm` varchar(255) NULL COMMENT '精通的领域，可以有多个，以；分隔',
  PRIMARY KEY (`id`)
) COMMENT = '简历';

DROP TABLE if exists sendresume;
CREATE TABLE `sendresume`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '简历id',
  `job_id` int NOT NULL COMMENT '投递的jobid',
  `profile_id` int NOT NULL COMMENT '投递的简历id',
  `create_time` datetime NOT NULL COMMENT '简历投递时间',
  PRIMARY KEY (`id`)
) COMMENT = '记录简历的投递关系';

DROP TABLE if exists transactionrecords;
CREATE TABLE `transactionrecords`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `record` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `account_id` int NOT NULL,
  PRIMARY KEY (`id`)
) COMMENT = '充值记录';

DROP TABLE if exists user;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `telephone` varchar(255) NOT NULL COMMENT '电话',
  `email` varchar(255) NULL COMMENT '邮箱',
  `location` varchar(255) NULL COMMENT '位置',
  `identity` enum('employer','employee','admin','superadmine') CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '四种身份 雇主 雇佣者 管理员 超级管理员',
  `headurl` varchar(255) NULL COMMENT '头像地址',
  PRIMARY KEY (`id`)
) COMMENT = '用户表，包含所有用户';

DROP TABLE if exists workpublisher;
CREATE TABLE `workpublisher`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `info` text NOT NULL,
  PRIMARY KEY (`id`)
);

ALTER TABLE `account` ADD CONSTRAINT `account_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `category` ADD CONSTRAINT `category_job_id` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`);
ALTER TABLE `comment` ADD CONSTRAINT `comment_reviewer_id` FOREIGN KEY (`reviewer_id`) REFERENCES `user` (`id`);
ALTER TABLE `comment` ADD CONSTRAINT `comment_commented_id` FOREIGN KEY (`commented_id`) REFERENCES `user` (`id`);
ALTER TABLE `hire` ADD CONSTRAINT `hire_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);
ALTER TABLE `hire` ADD CONSTRAINT `hire_employer_id` FOREIGN KEY (`employer_id`) REFERENCES `user` (`id`);
ALTER TABLE `hire` ADD CONSTRAINT `hire_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `user` (`id`);
ALTER TABLE `job` ADD CONSTRAINT `job_employer_id` FOREIGN KEY (`employer_id`) REFERENCES `user` (`id`);
ALTER TABLE `message` ADD CONSTRAINT `message_sender_id` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`);
ALTER TABLE `message` ADD CONSTRAINT `message_receiver_id` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`);
ALTER TABLE `order` ADD CONSTRAINT `oreder_job_id` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`);
ALTER TABLE `orderlog` ADD CONSTRAINT `orderlog_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `orderlog` ADD CONSTRAINT `orderlog_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);
ALTER TABLE `profile` ADD CONSTRAINT `profile_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION;
ALTER TABLE `sendresume` ADD CONSTRAINT `sendresume_job_id` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`);
ALTER TABLE `sendresume` ADD CONSTRAINT `sendresume_profile_id` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`);
ALTER TABLE `transactionrecords` ADD CONSTRAINT `transactionrecords_account_id` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`);
ALTER TABLE `workpublisher` ADD CONSTRAINT `workpublisher_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

