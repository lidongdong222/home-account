use home;
-- home.sys_config definition

CREATE TABLE `sys_config` (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `config_key` varchar(60) DEFAULT NULL COMMENT 'key',
  `config_value` varchar(300) DEFAULT NULL COMMENT 'value',
  `config_desc` varchar(200) DEFAULT NULL COMMENT '配置描述',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统配置信息表';



