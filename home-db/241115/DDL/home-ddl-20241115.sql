use home;

alter table acc_info add column wx_tran_code varchar(60) comment '微信交易单号';

alter table rep_column add column is_metadata  char(2) default '0' comment '是否支持源数据菜单数据查看 0-否 1-是';
alter table rep_column add column metadata_router  char(150) comment '源数据菜单路由及附带参数';
alter table rep_column add column metadata_self_params char(100) comment '源数据由列支持的路由参数';

CREATE TABLE `wx_bill` (
  `wb_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `imp_id` bigint DEFAULT NULL COMMENT '导入id',
  `tran_date` varchar(30) DEFAULT NULL COMMENT '交易时间',
  `tran_type` varchar(20) DEFAULT NULL COMMENT '交易类型',
  `merchant` varchar(60) DEFAULT NULL COMMENT '交易商户',
  `goods` varchar(150) DEFAULT NULL COMMENT '交易商品',
  `acc_type` varchar(2) DEFAULT NULL COMMENT '收支类型',
  `amount` varchar(20) DEFAULT NULL COMMENT '金额(元)',
  `pay_type` varchar(30) DEFAULT NULL COMMENT '支付方式',
  `wx_status` varchar(10) DEFAULT NULL COMMENT '当前状态',
  `tran_order` varchar(60) DEFAULT NULL COMMENT '交易单号',
  `merchant_order` varchar(60) DEFAULT NULL COMMENT '商户单号',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(30) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`wb_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='微信账单表';

drop table if exists sys_user;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `usercode` varchar(50) DEFAULT NULL COMMENT '账号',
  `username` varchar(30) DEFAULT NULL COMMENT '用户名',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `salt` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(30) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` varchar(30) DEFAULT NULL,
  `status` char(2) DEFAULT '1' COMMENT '状态 0-停用 1-启用',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';



drop table if exists sys_user_role;
CREATE TABLE `sys_user_role` (
  `ur_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint DEFAULT NULL COMMENT '主键',
  `role_id` int DEFAULT NULL COMMENT '权限id',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(30) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ur_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色列表信息表';


drop table if exists sys_role;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_code` varchar(30) DEFAULT NULL COMMENT '角色编码',
  `role_name` varchar(30) DEFAULT NULL COMMENT '角色id',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `status` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' COMMENT '状态 0-停用 1-启用',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(30) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色信息表';



