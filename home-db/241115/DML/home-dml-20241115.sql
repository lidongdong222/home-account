use home;

delete from rep_column where rep_id = 3;

INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(26, 3, 3, 1, 10, '月份|支出大类', 'string', 'subName', 1, 2, 18, 20, '0', NULL, NULL, NULL, NULL, '1', '0', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(27, 3, 3, 1, 30, '1月', 'number', 'month1', 1, 1, 18, 20, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subCode=${subCode}&subType=2', '{"beginMonth":"01","endMonth":"01"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(28, 3, 3, 1, 40, '2月', 'number', 'month2', 1, 1, 18, 20, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subCode=${subCode}&subType=2', '{"beginMonth":"02","endMonth":"02"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(29, 3, 3, 1, 50, '3月', 'number', 'month3', 1, 1, 18, 20, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subCode=${subCode}&subType=2', '{"beginMonth":"03","endMonth":"03"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(30, 3, 3, 1, 60, '4月', 'number', 'month4', 1, 1, 18, 20, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subCode=${subCode}&subType=2', '{"beginMonth":"04","endMonth":"04"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(31, 3, 3, 1, 70, '5月', 'number', 'month5', 1, 1, 18, 20, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subCode=${subCode}&subType=2', '{"beginMonth":"05","endMonth":"05"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(32, 3, 3, 1, 80, '6月', 'number', 'month6', 1, 1, 18, 20, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subCode=${subCode}&subType=2', '{"beginMonth":"06","endMonth":"06"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(33, 3, 3, 1, 90, '7月', 'number', 'month7', 1, 1, 18, 20, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subCode=${subCode}&subType=2', '{"beginMonth":"07","endMonth":"07"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(34, 3, 3, 1, 100, '8月', 'number', 'month8', 1, 1, 18, 20, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subCode=${subCode}&subType=2', '{"beginMonth":"08","endMonth":"08"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(35, 3, 3, 1, 110, '9月', 'number', 'month9', 1, 1, 18, 20, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subCode=${subCode}&subType=2', '{"beginMonth":"09","endMonth":"09"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(36, 3, 3, 1, 120, '10月', 'number', 'month10', 1, 1, 18, 20, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subCode=${subCode}&subType=2', '{"beginMonth":"10","endMonth":"10"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(37, 3, 3, 1, 130, '11月', 'number', 'month11', 1, 1, 18, 20, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subCode=${subCode}&subType=2', '{"beginMonth":"11","endMonth":"11"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(38, 3, 3, 1, 140, '12月', 'number', 'month12', 1, 1, 18, 20, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subCode=${subCode}&subType=2', '{"beginMonth":"12","endMonth":"12"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(39, 3, 3, 1, 150, '本年累计', 'number', 'bnlj', 1, 1, 18, 20, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subCode=${subCode}&subType=2', '{"beginMonth":"01","endMonth":"12"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(40, 3, 3, 1, 160, '全年支出金额占比（%）', 'number', 'qnzczb', 1, 1, 18, 20, '0', NULL, NULL, NULL, NULL, '0', '0', '0', '', NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(41, 3, 3, 1, 20, '月份|支出大类', 'string', 'item', 1, 0, 18, 20, '0', NULL, NULL, NULL, NULL, '0', '0', '0', NULL, NULL);


delete from home.rep_sheet where sheet_id = 3;
INSERT INTO home.rep_sheet (sheet_id, rep_id, sheet_index, sheet_name, title, deal_type, deal_bean, deal_method, deal_sql, create_date, create_user, update_date, update_user) VALUES(3, 3, 1, '支出月度统计表', '支出月度统计表', '2', NULL, NULL, 'select subCode,subName,if(item=1,''数量'',''金额'') item,sort,type,
format(month1,if(item=2,2,0)) month1,
format(month2,if(item=2,2,0)) month2,
format(month3,if(item=2,2,0)) month3,
format(month4,if(item=2,2,0)) month4,
format(month5,if(item=2,2,0)) month5,
format(month6,if(item=2,2,0)) month6,
format(month7,if(item=2,2,0)) month7,
format(month8,if(item=2,2,0)) month8,
format(month9,if(item=2,2,0)) month9,
format(month10,if(item=2,2,0)) month10,
format(month11,if(item=2,2,0)) month11,
format(month12,if(item=2,2,0)) month12,
format(bnlj,if(item=2,2,0)) bnlj,
case when item=2 then format(bnlj/total_amount*100,2) else ''--'' end  qnzczb
from (
select a.sub_code1 subCode,a.sub_name1 subName,2 item,min(a.sort) sort,1 type,
ifnull(sum(case when c.acc_period = ''${year}01'' then c.amount else 0 end),0) month1,
ifnull(sum(case when c.acc_period = ''${year}02'' then c.amount else 0 end),0) month2,
ifnull(sum(case when c.acc_period = ''${year}03'' then c.amount else 0 end),0) month3,
ifnull(sum(case when c.acc_period = ''${year}04'' then c.amount else 0 end),0) month4,
ifnull(sum(case when c.acc_period = ''${year}05'' then c.amount else 0 end),0) month5,
ifnull(sum(case when c.acc_period = ''${year}06'' then c.amount else 0 end),0) month6,
ifnull(sum(case when c.acc_period = ''${year}07'' then c.amount else 0 end),0) month7,
ifnull(sum(case when c.acc_period = ''${year}08'' then c.amount else 0 end),0) month8,
ifnull(sum(case when c.acc_period = ''${year}09'' then c.amount else 0 end),0) month9,
ifnull(sum(case when c.acc_period = ''${year}10'' then c.amount else 0 end),0) month10,
ifnull(sum(case when c.acc_period = ''${year}11'' then c.amount else 0 end),0) month11,
ifnull(sum(case when c.acc_period = ''${year}12'' then c.amount else 0 end),0) month12,
ifnull(sum(c.amount),0) bnlj
from sub_info a 
left join acc_info c  on a.sub_id  = c.sub_id and c.acc_period like concat(#{year},''%'')
where a.sub_type = 2 
group by a.sub_code1,a.sub_name1 
union all 
select a.sub_code1,a.sub_name1,1,min(a.sort) sort,1 type,
ifnull(count(case when c.acc_period = ''${year}01'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}02'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}03'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}04'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}05'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}06'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}07'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}08'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}09'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}10'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}11'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}12'' then 1 else null end),0) ,
ifnull(count(c.acc_period),0) bnlj
from sub_info a 
left join acc_info c  on a.sub_id  = c.sub_id and c.acc_period like concat(#{year},''%'')
where a.sub_type = 2 
group by a.sub_code1,a.sub_name1 
union all 
select '''',''合计'',2,0,2,
ifnull(sum(case when c.acc_period = ''${year}01'' then c.amount else 0 end),0) ,
ifnull(sum(case when c.acc_period = ''${year}02'' then c.amount else 0 end),0) ,
ifnull(sum(case when c.acc_period = ''${year}03'' then c.amount else 0 end),0) ,
ifnull(sum(case when c.acc_period = ''${year}04'' then c.amount else 0 end),0) ,
ifnull(sum(case when c.acc_period = ''${year}05'' then c.amount else 0 end),0) ,
ifnull(sum(case when c.acc_period = ''${year}06'' then c.amount else 0 end),0) ,
ifnull(sum(case when c.acc_period = ''${year}07'' then c.amount else 0 end),0) ,
ifnull(sum(case when c.acc_period = ''${year}08'' then c.amount else 0 end),0) ,
ifnull(sum(case when c.acc_period = ''${year}09'' then c.amount else 0 end),0) ,
ifnull(sum(case when c.acc_period = ''${year}10'' then c.amount else 0 end),0) ,
ifnull(sum(case when c.acc_period = ''${year}11'' then c.amount else 0 end),0) ,
ifnull(sum(case when c.acc_period = ''${year}12'' then c.amount else 0 end),0) ,
ifnull(sum(c.amount),0)
from sub_info a 
left join acc_info c  on a.sub_id  = c.sub_id and c.acc_period like concat(#{year},''%'')
where a.sub_type = 2
union all 
select '''',''合计'',1,0,2,
ifnull(count(case when c.acc_period = ''${year}01'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}02'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}03'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}04'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}05'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}06'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}07'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}08'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}09'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}10'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}11'' then 1 else null end),0) ,
ifnull(count(case when c.acc_period = ''${year}12'' then 1 else null end),0) ,
ifnull(count(c.acc_period),0) bnlj
from sub_info a 
left join acc_info c  on a.sub_id  = c.sub_id and c.acc_period like concat(#{year},''%'')
where a.sub_type = 2
) t1 
left join (
	select 
		ifnull(sum(c.amount),0) total_amount,
		ifnull(count(c.amount),0) total_count
	from sub_info a 
	left join acc_info c  on a.sub_id  = c.sub_id and c.acc_period like concat(#{year},''%'')
	where a.sub_type = 2
)t2 on 1=1
order by type,sort,item', NULL, NULL, NULL, NULL);

INSERT INTO home.sys_user (user_id, usercode, username, phone, salt, password, remark, create_date, create_user, update_date, update_user, status) VALUES(1, 'home', '超级管理员', '', 'iMzXEZGlAACxtMNDKc2/JA==', '$2a$10$3dW5mLcZby8TyBerbU.GA.Pep3/nAbREv4wG7RiZ5j2Z/Edb27CKS', '', '2024-09-27 15:55:00', NULL, NULL, NULL, '1');