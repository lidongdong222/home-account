use home;

alter table acc_info add column imp_id bigint comment '导入id';
alter table acc_info change wx_tran_code wx_tran_order varchar(60) comment '微信交易单号';
ALTER TABLE home.wx_bill MODIFY COLUMN acc_type varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '收支类型';
