CREATE DATABASE `home` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
use home;
-- home.acc_balance definition

CREATE TABLE `acc_balance` (
  `ab_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period_type` tinyint NOT NULL COMMENT '期间类型 1年度 2季度 3月度 ',
  `period` varchar(10) DEFAULT NULL COMMENT '期间',
  `invome` decimal(15,2) DEFAULT NULL COMMENT '收入',
  `expenditure` decimal(15,2) DEFAULT NULL COMMENT '支出',
  `balance` decimal(15,2) DEFAULT NULL COMMENT '余额',
  `create_date` datetime DEFAULT NULL,
  `create_user` varchar(30) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ab_id`),
  KEY `idx_period` (`period`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='账务余额表';


-- home.acc_info definition

CREATE TABLE `acc_info` (
  `acc_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sub_code` varchar(50) NOT NULL COMMENT '科目编码',
  `amount` decimal(15,2) NOT NULL COMMENT '金额',
  `acc_year` int DEFAULT NULL COMMENT '年度',
  `acc_month` int DEFAULT NULL COMMENT '月份',
  `acc_date` datetime NOT NULL COMMENT '登记时间',
  `digest` varchar(100) DEFAULT NULL COMMENT '摘要',
  `exchange_desc` varchar(100) DEFAULT NULL COMMENT '流转说明',
  `source_desc` varchar(100) DEFAULT NULL COMMENT '来源说明',
  `create_date` datetime DEFAULT NULL,
  `create_user` varchar(30) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` varchar(30) DEFAULT NULL,
  `status` char(2) DEFAULT NULL COMMENT '状态 1正常 0软删除',
  `tmp_id` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '临时导入表id。临时导入表定时清理，该字段仅供参考',
  PRIMARY KEY (`acc_id`),
  KEY `idx_sub_date` (`sub_code`,`acc_date`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='科目流水信息表';


-- home.acc_info_tmp definition

CREATE TABLE `acc_info_tmp` (
  `tmp_id` varchar(60) NOT NULL COMMENT '主键id',
  `imp_id` bigint DEFAULT NULL COMMENT '导入id',
  `res_id` bigint DEFAULT NULL COMMENT '资源id',
  `account_type` varchar(20) DEFAULT NULL COMMENT '收支类型',
  `sub_code` varchar(50) DEFAULT NULL COMMENT '科目编码',
  `sub_name` varchar(20) DEFAULT NULL COMMENT '分类',
  `amount` varchar(30) DEFAULT NULL COMMENT '金额',
  `acc_year` varchar(20) DEFAULT NULL COMMENT '年度',
  `acc_month` varchar(20) DEFAULT NULL COMMENT '月份',
  `acc_date` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '账务时间',
  `digest` varchar(100) DEFAULT NULL COMMENT '摘要',
  `exchange_desc` varchar(100) DEFAULT NULL COMMENT '流转说明',
  `source_desc` varchar(100) DEFAULT NULL COMMENT '来源说明',
  `import_date` datetime DEFAULT NULL COMMENT '导入时间',
  `import_user` bigint DEFAULT NULL COMMENT '导入用户',
  `deal_status` int DEFAULT NULL COMMENT '处理状态 0未处理 1处理成功 2处理失败',
  `deal_msg` varchar(200) DEFAULT NULL COMMENT '处理失败原因',
  PRIMARY KEY (`tmp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='科目流水信息临时导入表';


-- home.imp_info definition

CREATE TABLE `imp_info` (
  `imp_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `res_id` bigint DEFAULT NULL COMMENT '资源id 导入文件存放地址',
  `res_name` varchar(100) DEFAULT NULL COMMENT '文件名称',
  `menu_id` int DEFAULT NULL COMMENT '来源菜单',
  `imp_time` datetime DEFAULT NULL COMMENT '导入时间',
  `imp_target_table` varchar(30) DEFAULT NULL COMMENT '导入目标表',
  `imp_tmp_table` varchar(30) DEFAULT NULL COMMENT '导入临时表',
  `analysis_bean` varchar(60) DEFAULT NULL COMMENT '入库解析bean',
  `analysis_method` varchar(60) DEFAULT NULL COMMENT '入库解析method',
  `status` int DEFAULT NULL COMMENT '导入状态 0等待导入 1导入成功 2导入失败 3导入中 4导入超时 5导入异常',
  `imp_total` int DEFAULT NULL COMMENT '总条数',
  `imp_succ` int DEFAULT NULL COMMENT '成功条数',
  `imp_fail` int DEFAULT NULL COMMENT '失败条数',
  `imp_time_comsum` int DEFAULT NULL COMMENT '导入总耗时，单位：秒',
  `imp_message` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '导入成功/失败信息',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新日期',
  `update_user` varchar(30) DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`imp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='导入信息表';


-- home.imp_tmp definition

CREATE TABLE `imp_tmp` (
  `tmp_id` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键id',
  `imp_id` bigint DEFAULT NULL COMMENT '导入表id',
  `sheet_index` int DEFAULT NULL COMMENT 'sheet定义表主键,从1开始',
  `tmp1` varchar(200) DEFAULT NULL COMMENT '第1列',
  `tmp2` varchar(200) DEFAULT NULL COMMENT '第2列',
  `tmp3` varchar(200) DEFAULT NULL COMMENT '第3列',
  `tmp4` varchar(200) DEFAULT NULL COMMENT '第4列',
  `tmp5` varchar(200) DEFAULT NULL COMMENT '第5列',
  `tmp6` varchar(200) DEFAULT NULL COMMENT '第6列',
  `tmp7` varchar(200) DEFAULT NULL COMMENT '第7列',
  `tmp8` varchar(200) DEFAULT NULL COMMENT '第8列',
  `tmp9` varchar(200) DEFAULT NULL COMMENT '第9列',
  `tmp10` varchar(200) DEFAULT NULL COMMENT '第10列',
  `tmp11` varchar(200) DEFAULT NULL COMMENT '第11列',
  `tmp12` varchar(200) DEFAULT NULL COMMENT '第12列',
  `tmp13` varchar(200) DEFAULT NULL COMMENT '第13列',
  `tmp14` varchar(200) DEFAULT NULL COMMENT '第14列',
  `tmp15` varchar(200) DEFAULT NULL COMMENT '第15列',
  `tmp16` varchar(200) DEFAULT NULL COMMENT '第16列',
  `tmp17` varchar(200) DEFAULT NULL COMMENT '第17列',
  `tmp18` varchar(200) DEFAULT NULL COMMENT '第18列',
  `tmp19` varchar(200) DEFAULT NULL COMMENT '第19列',
  `tmp20` varchar(200) DEFAULT NULL COMMENT '第20列',
  `tmp21` varchar(200) DEFAULT NULL COMMENT '第21列',
  `tmp22` varchar(200) DEFAULT NULL COMMENT '第22列',
  `tmp23` varchar(200) DEFAULT NULL COMMENT '第23列',
  `tmp24` varchar(200) DEFAULT NULL COMMENT '第24列',
  `tmp25` varchar(200) DEFAULT NULL COMMENT '第25列',
  `tmp26` varchar(200) DEFAULT NULL COMMENT '第26列',
  `tmp27` varchar(200) DEFAULT NULL COMMENT '第27列',
  `tmp28` varchar(200) DEFAULT NULL COMMENT '第28列',
  `tmp29` varchar(200) DEFAULT NULL COMMENT '第29列',
  `tmp30` varchar(200) DEFAULT NULL COMMENT '第30列',
  `deal_status` int DEFAULT NULL COMMENT '处理状态0未处理 1处理成功 2处理失败',
  `deal_msg` varchar(200) DEFAULT NULL COMMENT '处理失败信息',
  PRIMARY KEY (`tmp_id`),
  KEY `idx_imp` (`imp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='科目流水信息表-临时导入信息表';


-- home.rep_column definition

CREATE TABLE `rep_column` (
  `col_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rep_id` int DEFAULT NULL COMMENT '报表id',
  `sheet_id` int DEFAULT NULL COMMENT '报表sheet定义表id',
  `header_index` int DEFAULT NULL COMMENT '多级表头层级索引，从1开始',
  `col_index` int DEFAULT NULL COMMENT '列索引，从1开始',
  `col_name` varchar(60) DEFAULT NULL COMMENT '列名称',
  `col_data_type` varchar(10) DEFAULT NULL COMMENT '数据类型 number string date time datetime',
  `col_prop` varchar(30) DEFAULT NULL COMMENT '列匹配查询结果集合字段',
  `row_span` int DEFAULT NULL COMMENT '行合并单元格个数',
  `col_span` int DEFAULT NULL COMMENT '列合并单元格个数',
  `height` int DEFAULT NULL COMMENT '高度',
  `width` int DEFAULT NULL COMMENT '宽度',
  `is_necessary` char(1) DEFAULT '0' COMMENT '模板下载时是否必填 0否1是',
  `create_date` datetime DEFAULT NULL,
  `create_user` varchar(30) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`col_id`),
  KEY `idx_sheet` (`sheet_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sheet页列定义表';


-- home.rep_info definition

CREATE TABLE `rep_info` (
  `rep_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rep_name` varchar(100) DEFAULT NULL COMMENT '报表名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  `deal_bean` varchar(30) DEFAULT NULL COMMENT '解析bean名称',
  `deal_method` varchar(60) DEFAULT NULL COMMENT '解析方法',
  `create_date` datetime DEFAULT NULL,
  `create_user` varchar(30) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`rep_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='报表定义表';


-- home.rep_sheet definition

CREATE TABLE `rep_sheet` (
  `sheet_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rep_id` int DEFAULT NULL COMMENT '报表定义表id',
  `sheet_index` int DEFAULT NULL COMMENT 'sheet索引，从1开始',
  `sheet_name` varchar(60) DEFAULT NULL COMMENT 'sheet名称',
  `title` varchar(100) DEFAULT NULL COMMENT 'sheet标题',
  `create_date` datetime DEFAULT NULL,
  `create_user` varchar(30) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`sheet_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='报表sheet定义表';


-- home.resource_busi definition

CREATE TABLE `resource_busi` (
  `res_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `busi_id` varchar(100) DEFAULT NULL COMMENT '来源业务id',
  `busi_table` varchar(60) DEFAULT NULL COMMENT '来源业务表',
  `status` varchar(2) DEFAULT NULL COMMENT '0待关联，1已关联 当客户优先长传文件时为待关联，业务完成后，变为已关联。待关联文件会定时清理详见批量',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新日期',
  `update_user` varchar(30) DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`res_id`),
  KEY `idx_busi_id` (`busi_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='资源业务关联信息表';


-- home.resource_info definition

CREATE TABLE `resource_info` (
  `res_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `res_store_type` int DEFAULT NULL COMMENT '存储类型（1本地存储、2FTP、SFTP存储）',
  `res_name` varchar(100) DEFAULT NULL COMMENT '资源名称',
  `res_unique_name` varchar(100) DEFAULT NULL COMMENT '系统资源唯一名称',
  `res_size` bigint DEFAULT NULL COMMENT '资源大小',
  `upload_time` datetime DEFAULT NULL COMMENT '上传时间',
  `res_inner_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '资源互联网url',
  `res_internet_url` varchar(200) DEFAULT NULL COMMENT '内网资源访问地址',
  `res_server_ip` varchar(20) DEFAULT NULL COMMENT '资源所在服务器ip',
  `res_server_path` varchar(200) DEFAULT NULL COMMENT '资源地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新日期',
  `update_user` varchar(30) DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`res_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='资源信息表';


-- home.sub_info definition

CREATE TABLE `sub_info` (
  `sub_id` bigint NOT NULL AUTO_INCREMENT COMMENT '科目id',
  `sub_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '科目编码',
  `sub_name` varchar(50) DEFAULT NULL COMMENT '科目名称',
  `parent_id` bigint DEFAULT NULL COMMENT '父级id',
  `sub_level` int DEFAULT NULL COMMENT '科目等级',
  `sub_desc` varchar(100) DEFAULT NULL COMMENT '科目描述',
  `create_date` datetime DEFAULT NULL,
  `create_user` varchar(30) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` varchar(30) DEFAULT NULL,
  `status` char(2) DEFAULT NULL COMMENT '状态 1正常 0软删除',
  PRIMARY KEY (`sub_id`),
  UNIQUE KEY `idx_unique_code` (`sub_code`),
  KEY `idx_code_name` (`sub_code`,`sub_name`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='科目信息表';


-- home.sys_dict definition

CREATE TABLE `sys_dict` (
  `dict_type` varchar(30) NOT NULL COMMENT '字典',
  `dict_name` varchar(30) DEFAULT NULL COMMENT '字典名称',
  `sort` int DEFAULT NULL COMMENT '排序',
  `status` char(2) DEFAULT NULL COMMENT '状态 1启用 0禁用',
  PRIMARY KEY (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='数据字典信息表';


-- home.sys_dict_dtl definition

CREATE TABLE `sys_dict_dtl` (
  `dict_id` int NOT NULL AUTO_INCREMENT COMMENT '字典id',
  `dict_type` varchar(30) DEFAULT NULL COMMENT '字典',
  `dict_code` varchar(30) DEFAULT NULL COMMENT '字典码',
  `dict_value` varchar(30) DEFAULT NULL COMMENT '字典值',
  `sort` int DEFAULT NULL COMMENT '排序',
  `status` char(2) DEFAULT NULL COMMENT '状态 1启用 0禁用',
  PRIMARY KEY (`dict_id`),
  KEY `idx_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='数据字典信息表';


-- home.sys_menu definition

CREATE TABLE `sys_menu` (
  `menu_id` int NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `menu_code` varchar(30) DEFAULT NULL COMMENT '菜单编码',
  `menu_name` varchar(30) DEFAULT NULL COMMENT '菜单名称',
  `router` varchar(50) DEFAULT NULL COMMENT '路由',
  `parent_id` int DEFAULT NULL COMMENT '父id',
  `sort` int DEFAULT NULL COMMENT '排序',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图标',
  `status` char(2) DEFAULT NULL COMMENT '状态 1启用 0禁用',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单信息表';


-- home.sys_schedule definition

CREATE TABLE `sys_schedule` (
  `sche_id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `job_id` varchar(50) DEFAULT NULL COMMENT '批量主键',
  `group_id` varchar(50) DEFAULT NULL COMMENT '批量组',
  `version` varchar(59) DEFAULT NULL COMMENT '版本号',
  `cron` varchar(60) DEFAULT NULL COMMENT 'cron表达式',
  `class_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行类',
  `exec_strategy` tinyint DEFAULT NULL COMMENT '执行策略 1串行 2并行',
  `remark` varchar(200) DEFAULT NULL COMMENT '批量描述',
  `status` tinyint DEFAULT NULL COMMENT '状态 1启动 2停用',
  `create_date` datetime DEFAULT NULL,
  `create_user` varchar(30) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`sche_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='批量定时作业表';


-- home.sys_schedule_his definition

CREATE TABLE `sys_schedule_his` (
  `ssh_id` bigint NOT NULL AUTO_INCREMENT,
  `job_id` varchar(50) DEFAULT NULL COMMENT '批量主键',
  `group_id` varchar(50) DEFAULT NULL COMMENT '批量组',
  `version` varchar(59) DEFAULT NULL,
  `exec_start_date` datetime DEFAULT NULL,
  `exec_end_date` datetime DEFAULT NULL,
  `exec_time_consum` int DEFAULT NULL,
  `ip_addr` varchar(20) DEFAULT NULL,
  `container_id` varchar(20) DEFAULT NULL,
  `status` tinyint DEFAULT NULL COMMENT '执行状态 0待执行 1成功 2失败 3执行中 4失火 5其他原因未执行',
  `err_message` varchar(500) DEFAULT NULL COMMENT '失败、异常原因',
  PRIMARY KEY (`ssh_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='批量执行记录表';


-- home.sys_schedule_version_his definition

CREATE TABLE `sys_schedule_version_his` (
  `ssvh_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `job_id` varchar(50) DEFAULT NULL COMMENT '批量主键',
  `group_id` varchar(50) DEFAULT NULL COMMENT '批量组',
  `version` varchar(59) DEFAULT NULL COMMENT '版本号',
  `cron` varchar(60) DEFAULT NULL COMMENT 'cron表达式',
  `bean_name` varchar(200) DEFAULT NULL COMMENT '执行类',
  `method_name` varchar(200) DEFAULT NULL COMMENT '执行方法',
  `execution_strategy` tinyint DEFAULT NULL COMMENT '执行策略 1串行 2并行',
  `status` tinyint DEFAULT NULL COMMENT '状态 1启动 2停用',
  `start_date` datetime DEFAULT NULL COMMENT '版本开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '版本结束时间',
  PRIMARY KEY (`ssvh_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='版本历史记录表';