INSERT INTO home.sys_menu (menu_id,menu_code,menu_name,router,parent_id,sort,icon,status,remark) VALUES
	 (1,'sys','系统管理',NULL,0,7,'tools-white','1',''),
	 (14,'account','账务','/account/index',0,1,'account','1','测试'),
	 (16,'sys_menu','菜单管理','/sys/menu',1,2,'menu','1',NULL),
	 (17,'account_adjust','账务登记','/account/adjust',14,6,'accountAdjust','1',NULL),
	 (18,'sys_dict','业务字典','/sys/dict',1,4,'dict','1',NULL),
	 (19,'account_subject','科目信息','/account/subject',14,8,'subject','1',NULL),
	 (24,'account_statistics','统计报表','/account/statistics',14,5,'statistics','1',NULL),
	 (25,'sys_schedule','批量管理','/sys/schedule',1,9,'schedule','1',NULL);
INSERT INTO home.rep_column (col_id,rep_id,sheet_id,header_index,col_index,col_name,col_data_type,col_prop,row_span,col_span,height,width,is_necessary,create_date,create_user,update_date,update_user) VALUES
	 (1,1,1,1,1,'收支类型','string','accountType',1,1,18,20,'0',NULL,NULL,NULL,NULL),
	 (2,1,1,1,2,'代码','string','subCode',1,1,18,20,'1',NULL,NULL,NULL,NULL),
	 (3,1,1,1,3,'分类','string','subName',1,1,18,20,'0',NULL,NULL,NULL,NULL),
	 (4,1,1,1,4,'金额','number','amount',1,1,18,20,'1',NULL,NULL,NULL,NULL),
	 (5,1,1,1,5,'年度','string','accYear',1,1,18,20,'0',NULL,NULL,NULL,NULL),
	 (6,1,1,1,6,'月份','string','accMonth',1,1,18,20,'0',NULL,NULL,NULL,NULL),
	 (7,1,1,1,7,'账务日期','date','accDate',1,1,18,20,'1',NULL,NULL,NULL,NULL),
	 (8,1,1,1,8,'摘要','string','digest',1,1,18,20,'0',NULL,NULL,NULL,NULL),
	 (9,1,1,1,9,'流转说明','string','exchangeDesc',1,1,18,20,'0',NULL,NULL,NULL,NULL),
	 (10,1,1,1,10,'来源说明','string','sourceDesc',1,1,18,20,'0',NULL,NULL,NULL,NULL);
INSERT INTO home.rep_info (rep_id,rep_name,remark,deal_bean,deal_method,create_date,create_user,update_date,update_user) VALUES
	 (1,'账务明细',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO home.rep_sheet (sheet_id,rep_id,sheet_index,sheet_name,title,create_date,create_user,update_date,update_user) VALUES
	 (1,1,1,'账务明细','账务明细',NULL,NULL,NULL,NULL);
INSERT INTO home.sub_info (sub_id,sub_code,sub_name,parent_id,sub_level,sub_desc,create_date,create_user,update_date,update_user,status) VALUES
	 (1,'SR','收入',0,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (2,'ZC','支出',0,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (3,'SR001','家庭收入',1,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (4,'SR002','其他收入',1,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (5,'SR003','借款收入',1,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (6,'SR999','其他',1,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (7,'ZC001','交通支出',2,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (8,'ZC002','生活服务支出',2,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (9,'ZC003','餐饮支出',2,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (10,'ZC004','医疗支出',2,0,NULL,NULL,NULL,NULL,NULL,'1');
INSERT INTO home.sub_info (sub_id,sub_code,sub_name,parent_id,sub_level,sub_desc,create_date,create_user,update_date,update_user,status) VALUES
	 (11,'ZC005','学习支出',2,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (12,'ZC006','宝宝支出',2,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (13,'ZC007','其他支出',2,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (14,'ZC008','房子支出',2,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (15,'ZC009','借款支出',2,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (16,'ZC010','保费支出',2,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (17,'ZC999','其他',2,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (18,'ZC001001','用车加油',7,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (20,'ZC001002','过路费用',7,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (21,'ZC001003','车内装饰',7,0,NULL,NULL,NULL,NULL,NULL,'1');
INSERT INTO home.sub_info (sub_id,sub_code,sub_name,parent_id,sub_level,sub_desc,create_date,create_user,update_date,update_user,status) VALUES
	 (22,'ZC001004','用车保养',7,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (23,'ZC001005','车链',7,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (24,'ZC001006','车票',7,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (25,'ZC001007','买车',7,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (26,'ZC002001','理发预付卡',8,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (27,'ZC002002','取暖器',8,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (28,'ZC002003','年货',8,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (29,'ZC002004','护肤品',8,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (30,'ZC002005','电费',8,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (31,'ZC002006','衣物支出',8,0,NULL,NULL,NULL,NULL,NULL,'1');
INSERT INTO home.sub_info (sub_id,sub_code,sub_name,parent_id,sub_level,sub_desc,create_date,create_user,update_date,update_user,status) VALUES
	 (32,'ZC003001','堂食',9,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (33,'ZC003002','外卖',9,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (34,'ZC004001','冬冬',10,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (35,'ZC004002','菊红',10,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (36,'ZC005001','考证',11,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (37,'ZC005002','阅读',11,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (38,'ZC005003','投资',11,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (39,'ZC005004','其他',11,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (40,'ZC006001','纸尿裤',12,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (41,'ZC006002','奶粉',12,0,NULL,NULL,NULL,NULL,NULL,'1');
INSERT INTO home.sub_info (sub_id,sub_code,sub_name,parent_id,sub_level,sub_desc,create_date,create_user,update_date,update_user,status) VALUES
	 (42,'ZC006003','衣服',12,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (43,'ZC006004','学习',12,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (45,'ZC006006','医疗',12,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (46,'ZC006007','其他',12,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (47,'ZC007001','人情往来',13,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (48,'ZC007002','旅游',13,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (49,'ZC007003','利息支出',13,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (50,'ZC007004','订金',13,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (51,'ZC007005','花呗还款',13,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (52,'ZC007006','税费',13,0,NULL,NULL,NULL,NULL,NULL,'1');
INSERT INTO home.sub_info (sub_id,sub_code,sub_name,parent_id,sub_level,sub_desc,create_date,create_user,update_date,update_user,status) VALUES
	 (53,'ZC008001','房租费用',14,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (54,'ZC008002','房贷费用',14,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (55,'ZC008003','买房费用',14,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (56,'ZC009001','借款',15,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (57,'ZC010001','健康险',16,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (58,'ZC010002','车险',16,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (59,'SR001001','工资薪金-冬',3,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (60,'SR001002','工资薪金-红',3,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (61,'SR002001','股票收入',4,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (62,'SR002002','人情往来',4,0,NULL,NULL,NULL,NULL,NULL,'1');
INSERT INTO home.sub_info (sub_id,sub_code,sub_name,parent_id,sub_level,sub_desc,create_date,create_user,update_date,update_user,status) VALUES
	 (63,'SR002003','利息收入',4,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (64,'SR002004','红包收入',4,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (65,'SR002005','退款',4,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (68,'YHCK','资产',0,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (69,'YHCK001','银行存款',68,0,NULL,NULL,NULL,NULL,NULL,'1'),
	 (70,'1','2',0,0,NULL,NULL,NULL,NULL,NULL,'0');
