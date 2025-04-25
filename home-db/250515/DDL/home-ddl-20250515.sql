use home;

create table wx_bill_match_subject_rule (
	wbmsr_id varchar(50) primary key comment '主键',
	user_id bigint not null comment '用户id',
	acc_type varchar(10) comment '收支类型',
	wx_data_item varchar(20) comment '微信业务数据项',
	match_type char(2) comment '匹配类型1包含 2等于',
	match_content char(100) comment '匹配内容',
	match_sub_id bigint comment '匹配科目',
	`create_date` datetime DEFAULT CURRENT_TIMESTAMP,
	  `create_user` varchar(30) DEFAULT NULL,
	  `update_date` datetime DEFAULT NULL,
	  `update_user` varchar(30) DEFAULT NULL
) comment '科目规则匹配表';

ALTER TABLE home.wx_bill CHANGE acc_type sub_type varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '收支类型';
ALTER TABLE home.wx_bill_match_subject_rule CHANGE acc_type sub_type varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '收支类型';

