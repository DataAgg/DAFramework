CREATE TABLE `sys_users` (
	`id`        BIGINT(32)  NOT NULL AUTO_INCREMENT
	COMMENT '主键id',
	`user_name` VARCHAR(64) NULL     DEFAULT NULL
	COMMENT '用户名',
	`password`  VARCHAR(64) NULL     DEFAULT NULL
	COMMENT '密码',
	PRIMARY KEY (`id`)
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8
	COLLATE = utf8_bin
	COMMENT = '用户表';

