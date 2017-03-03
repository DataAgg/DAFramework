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

CREATE TABLE `da_account` (
	`id`        BIGINT(32)  NOT NULL AUTO_INCREMENT
	COMMENT '主键id',
	`user_id`        BIGINT(32)  NOT NULL
	COMMENT 'user id',
	`full_name` VARCHAR(64) NULL     DEFAULT NULL
	COMMENT '全名',
	`mobile`    VARCHAR(64) NULL     DEFAULT NULL
	COMMENT '手机号码',
	`address`   VARCHAR(64) NULL     DEFAULT NULL
	COMMENT '地址',
	`comment`   VARCHAR(64) NULL     DEFAULT NULL
	COMMENT '备注',
	PRIMARY KEY (`id`)
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8
	COLLATE = utf8_bin
	COMMENT = '用户信息表';


