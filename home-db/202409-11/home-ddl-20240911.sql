use home;

RENAME TABLE home.sub_info TO home.private_sub_info;
RENAME TABLE home.acc_info TO home.private_acc_info;
RENAME TABLE home.acc_info_tmp TO home.private_acc_info_tmp;

-- home.sub_info definition

CREATE TABLE `sub_info` (
  `sub_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sub_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '分类 1收入 2支出',
  `sub_code` varchar(200) DEFAULT NULL COMMENT '总科目',
  `sub_name` varchar(350) DEFAULT NULL COMMENT '总科目名称',
  `sub_code1` varchar(30) DEFAULT NULL COMMENT '1级科目',
  `sub_name1` varchar(30) DEFAULT NULL COMMENT '1级科目名称',
  `sub_code2` varchar(30) DEFAULT NULL COMMENT '2级科目',
  `sub_name2` varchar(50) DEFAULT NULL COMMENT '2级科目名称',
  `sub_code3` varchar(30) DEFAULT NULL COMMENT '3级科目',
  `sub_name3` varchar(50) DEFAULT NULL COMMENT '3级科目名称',
  `sub_code4` varchar(30) DEFAULT NULL COMMENT '4级科目',
  `sub_name4` varchar(50) DEFAULT NULL COMMENT '4级科目名称',
  `sub_code5` varchar(30) DEFAULT NULL COMMENT '5级科目',
  `sub_name5` varchar(50) DEFAULT NULL COMMENT '5级科目名称',
  `sub_code6` varchar(30) DEFAULT NULL COMMENT '6级科目',
  `sub_name6` varchar(50) DEFAULT NULL COMMENT '6级科目名称',
  `sub_code7` varchar(30) DEFAULT NULL COMMENT '7级科目',
  `sub_name7` varchar(50) DEFAULT NULL COMMENT '7级科目名称',
  `sub_code8` varchar(30) DEFAULT NULL COMMENT '8级科目',
  `sub_name8` varchar(50) DEFAULT NULL COMMENT '8级科目名称',
  `sub_code9` varchar(30) DEFAULT NULL COMMENT '9级科目',
  `sub_name9` varchar(50) DEFAULT NULL COMMENT '9级科目名称',
  `sub_code10` varchar(30) DEFAULT NULL COMMENT '10级科目',
  `sub_name10` varchar(50) DEFAULT NULL COMMENT '10级科目名称',
  `sub_code11` varchar(30) DEFAULT NULL COMMENT '11级科目',
  `sub_name11` varchar(50) DEFAULT NULL COMMENT '11级科目名称',
  `sub_code12` varchar(30) DEFAULT NULL COMMENT '12级科目',
  `sub_name12` varchar(50) DEFAULT NULL COMMENT '12级科目名称',
  `sub_code13` varchar(30) DEFAULT NULL COMMENT '13级科目',
  `sub_name13` varchar(50) DEFAULT NULL COMMENT '13级科目名称',
  `sub_code14` varchar(30) DEFAULT NULL COMMENT '14级科目',
  `sub_name14` varchar(50) DEFAULT NULL COMMENT '14级科目名称',
  `sub_code15` varchar(30) DEFAULT NULL COMMENT '15级科目',
  `sub_name15` varchar(50) DEFAULT NULL COMMENT '15级科目名称',
  `sub_code16` varchar(30) DEFAULT NULL COMMENT '16级科目',
  `sub_name16` varchar(50) DEFAULT NULL COMMENT '16级科目名称',
  `sub_code17` varchar(30) DEFAULT NULL COMMENT '17级科目',
  `sub_name17` varchar(50) DEFAULT NULL COMMENT '17级科目名称',
  `sub_code18` varchar(30) DEFAULT NULL COMMENT '18级科目',
  `sub_name18` varchar(50) DEFAULT NULL COMMENT '18级科目名称',
  `sub_code19` varchar(30) DEFAULT NULL COMMENT '19级科目',
  `sub_name19` varchar(50) DEFAULT NULL COMMENT '19级科目名称',
  `sub_code20` varchar(30) DEFAULT NULL COMMENT '20级科目',
  `sub_name20` varchar(50) DEFAULT NULL COMMENT '20级科目名称',
  `sort` bigint DEFAULT NULL COMMENT '排序',
  `sub_desc` varchar(100) DEFAULT NULL COMMENT '科目描述',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(30) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` varchar(30) DEFAULT NULL,
  `status` char(2) DEFAULT NULL COMMENT '状态 1正常 0软删除',
  PRIMARY KEY (`sub_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='横式科目信息表';


-- home.acc_info definition

CREATE TABLE `acc_info` (
  `acc_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sub_id` varchar(50) NOT NULL COMMENT '科目编码',
  `amount` decimal(15,2) NOT NULL COMMENT '金额',
  `acc_period` varchar(20) DEFAULT NULL COMMENT '所属期间',
  `acc_date` datetime NOT NULL COMMENT '业务发生日期',
  `digest` varchar(100) DEFAULT NULL COMMENT '摘要',
  `payment_type` char(2) DEFAULT NULL COMMENT '结算方式 1银行交易 2现金交易 3支付宝交易 4微信交易 5其他方式',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(30) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` varchar(30) DEFAULT NULL,
  `status` char(2) DEFAULT NULL COMMENT '状态 1正常 0软删除',
  `tmp_id` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '临时导入表id。临时导入表定时清理，该字段仅供参考',
  PRIMARY KEY (`acc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='科目流水信息表';