use home;
#删除无效菜单
delete from sys_menu where menu_id = 24;
#初始化创建人
update acc_info  set create_user = (select user_id from sys_user where usercode = 'ldd' )
where digest like '%冬%' and create_user is null;
update acc_info  set create_user = (select user_id from sys_user where usercode = 'mjh' )
where digest like '%红%' and create_user is null;
#更新问题（不小心拿页面的数据更新了数据库）
update acc_info a set create_user  = (select b.user_id from sys_user b where b.username = a.create_user ) 
where exists (
	select 1 from sys_user c where c.username = a.create_user 
);

#初始化报表
delete from home.rep_info where rep_id in (1,2,3,4);
INSERT INTO home.rep_info
(rep_id, rep_name, remark, deal_bean, deal_method, create_date, create_user, update_date, update_user)
VALUES(1, '${year}年账务明细', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO home.rep_info
(rep_id, rep_name, remark, deal_bean, deal_method, create_date, create_user, update_date, update_user)
VALUES(2, '${year}年记账月度统计表', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO home.rep_info
(rep_id, rep_name, remark, deal_bean, deal_method, create_date, create_user, update_date, update_user)
VALUES(3, '${year}年支出月度统计表', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO home.rep_info
(rep_id, rep_name, remark, deal_bean, deal_method, create_date, create_user, update_date, update_user)
VALUES(4, '${year}年收入月度明细表', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

delete from home.rep_sheet where rep_id in (1,2,3,4);
INSERT INTO home.rep_sheet (sheet_id, rep_id, sheet_index, sheet_name, title, deal_type, deal_bean, deal_method, deal_sql, create_date, create_user, update_date, update_user) VALUES(1, 1, 1, '账务明细', '${year}年账务明细', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO home.rep_sheet (sheet_id, rep_id, sheet_index, sheet_name, title, deal_type, deal_bean, deal_method, deal_sql, create_date, create_user, update_date, update_user) VALUES(2, 2, 1, '记账月度统计表', '${year}年记账月度统计表', '2', NULL, NULL, 'select 
	1 sort,
	ifnull(sum(case when a.period = ''${year}01'' then a.income else 0 end),0) month1,
	ifnull(sum(case when a.period = ''${year}02'' then a.income else 0 end),0) month2,
	ifnull(sum(case when a.period = ''${year}03'' then a.income else 0 end),0) month3,
	ifnull(sum(case when a.period = ''${year}04'' then a.income else 0 end),0) month4,
	ifnull(sum(case when a.period = ''${year}05'' then a.income else 0 end),0) month5,
	ifnull(sum(case when a.period = ''${year}06'' then a.income else 0 end),0) month6,
	ifnull(sum(case when a.period = ''${year}07'' then a.income else 0 end),0) month7,
	ifnull(sum(case when a.period = ''${year}08'' then a.income else 0 end),0) month8,
	ifnull(sum(case when a.period = ''${year}09'' then a.income else 0 end),0) month9,
	ifnull(sum(case when a.period = ''${year}10'' then a.income else 0 end),0) month10,
	ifnull(sum(case when a.period = ''${year}11'' then a.income else 0 end),0) month11,
	ifnull(sum(case when a.period = ''${year}12'' then a.income else 0 end),0) month12,
	ifnull(sum(a.income),0) accumulateYear,
	date_format(max(create_date),''%Y-%m-%d %H:%i:%s'')  createDate
from acc_balance a
where a.period_type = ''3''
and a.period like concat(#{year},''%'')
union 
select 
	2 sort,
	ifnull(sum(case when a.period = ''${year}01'' then a.expenditure else 0 end),0) month1,
	ifnull(sum(case when a.period = ''${year}02'' then a.expenditure else 0 end),0) month2,
	ifnull(sum(case when a.period = ''${year}03'' then a.expenditure else 0 end),0) month3,
	ifnull(sum(case when a.period = ''${year}04'' then a.expenditure else 0 end),0) month4,
	ifnull(sum(case when a.period = ''${year}05'' then a.expenditure else 0 end),0) month5,
	ifnull(sum(case when a.period = ''${year}06'' then a.expenditure else 0 end),0) month6,
	ifnull(sum(case when a.period = ''${year}07'' then a.expenditure else 0 end),0) month7,
	ifnull(sum(case when a.period = ''${year}08'' then a.expenditure else 0 end),0) month8,
	ifnull(sum(case when a.period = ''${year}09'' then a.expenditure else 0 end),0) month9,
	ifnull(sum(case when a.period = ''${year}10'' then a.expenditure else 0 end),0) month10,
	ifnull(sum(case when a.period = ''${year}11'' then a.expenditure else 0 end),0) month11,
	ifnull(sum(case when a.period = ''${year}12'' then a.expenditure else 0 end),0) month12,
	ifnull(sum(a.expenditure),0) accumulateYear,
	date_format(max(create_date),''%Y-%m-%d %H:%i:%s'')  createDate
from acc_balance a
where a.period_type = ''3''
and a.period like concat(#{year},''%'')
union 
select 
	3 sort,
	ifnull(sum(case when a.period = ''${year}01'' then a.balance else 0 end),0) month1,
	ifnull(sum(case when a.period = ''${year}02'' then a.balance else 0 end),0) month2,
	ifnull(sum(case when a.period = ''${year}03'' then a.balance else 0 end),0) month3,
	ifnull(sum(case when a.period = ''${year}04'' then a.balance else 0 end),0) month4,
	ifnull(sum(case when a.period = ''${year}05'' then a.balance else 0 end),0) month5,
	ifnull(sum(case when a.period = ''${year}06'' then a.balance else 0 end),0) month6,
	ifnull(sum(case when a.period = ''${year}07'' then a.balance else 0 end),0) month7,
	ifnull(sum(case when a.period = ''${year}08'' then a.balance else 0 end),0) month8,
	ifnull(sum(case when a.period = ''${year}09'' then a.balance else 0 end),0) month9,
	ifnull(sum(case when a.period = ''${year}10'' then a.balance else 0 end),0) month10,
	ifnull(sum(case when a.period = ''${year}11'' then a.balance else 0 end),0) month11,
	ifnull(sum(case when a.period = ''${year}12'' then a.balance else 0 end),0) month12,
	ifnull(sum(a.balance),0) accumulateYear,
	date_format(max(create_date),''%Y-%m-%d %H:%i:%s'')  createDate
from acc_balance a
where a.period_type = ''3''
and a.period like concat(#{year},''%'')
union 
select 
	4 sort,
	ifnull(sum(case when a.period = ''${year}01'' then a.begin_balance else 0 end),0) month1,
	ifnull(sum(case when a.period = ''${year}02'' then a.begin_balance else 0 end),0) month2,
	ifnull(sum(case when a.period = ''${year}03'' then a.begin_balance else 0 end),0) month3,
	ifnull(sum(case when a.period = ''${year}04'' then a.begin_balance else 0 end),0) month4,
	ifnull(sum(case when a.period = ''${year}05'' then a.begin_balance else 0 end),0) month5,
	ifnull(sum(case when a.period = ''${year}06'' then a.begin_balance else 0 end),0) month6,
	ifnull(sum(case when a.period = ''${year}07'' then a.begin_balance else 0 end),0) month7,
	ifnull(sum(case when a.period = ''${year}08'' then a.begin_balance else 0 end),0) month8,
	ifnull(sum(case when a.period = ''${year}09'' then a.begin_balance else 0 end),0) month9,
	ifnull(sum(case when a.period = ''${year}10'' then a.begin_balance else 0 end),0) month10,
	ifnull(sum(case when a.period = ''${year}11'' then a.begin_balance else 0 end),0) month11,
	ifnull(sum(case when a.period = ''${year}12'' then a.begin_balance else 0 end),0) month12,
	''--'' accumulateYear,
	date_format(max(create_date),''%Y-%m-%d %H:%i:%s'')  createDate
from acc_balance a
where a.period_type = ''3''
and a.period like concat(#{year},''%'')
union 
select 
	5 sort,
	ifnull(sum(case when a.period = ''${year}01'' then a.end_balance else 0 end),0) month1,
	ifnull(sum(case when a.period = ''${year}02'' then a.end_balance else 0 end),0) month2,
	ifnull(sum(case when a.period = ''${year}03'' then a.end_balance else 0 end),0) month3,
	ifnull(sum(case when a.period = ''${year}04'' then a.end_balance else 0 end),0) month4,
	ifnull(sum(case when a.period = ''${year}05'' then a.end_balance else 0 end),0) month5,
	ifnull(sum(case when a.period = ''${year}06'' then a.end_balance else 0 end),0) month6,
	ifnull(sum(case when a.period = ''${year}07'' then a.end_balance else 0 end),0) month7,
	ifnull(sum(case when a.period = ''${year}08'' then a.end_balance else 0 end),0) month8,
	ifnull(sum(case when a.period = ''${year}09'' then a.end_balance else 0 end),0) month9,
	ifnull(sum(case when a.period = ''${year}10'' then a.end_balance else 0 end),0) month10,
	ifnull(sum(case when a.period = ''${year}11'' then a.end_balance else 0 end),0) month11,
	ifnull(sum(case when a.period = ''${year}12'' then a.end_balance else 0 end),0) month12,
	''--'' accumulateYear,
	date_format(max(create_date),''%Y-%m-%d %H:%i:%s'')  createDate
from acc_balance a
where a.period_type = ''3''
and a.period like concat(#{year},''%'')
order by sort', NULL, NULL, NULL, NULL);
INSERT INTO home.rep_sheet (sheet_id, rep_id, sheet_index, sheet_name, title, deal_type, deal_bean, deal_method, deal_sql, create_date, create_user, update_date, update_user) VALUES(3, 3, 1, '支出月度统计表', '${year}年支出月度统计表', '2', NULL, NULL, 'select subCode,subName,if(item=1,''数量'',''金额'') item,sort,type,
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
INSERT INTO home.rep_sheet (sheet_id, rep_id, sheet_index, sheet_name, title, deal_type, deal_bean, deal_method, deal_sql, create_date, create_user, update_date, update_user) VALUES(4, 4, 1, '收入月度统计表', '${year}年收入月度统计表', '2', NULL, NULL, 'select subCode,subName,if(item=1,''数量'',''金额'') item,sort,type,
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
where a.sub_type = 1 
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
where a.sub_type = 1 
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
where a.sub_type = 1
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
where a.sub_type = 1
) t1 
left join (
	select 
		ifnull(sum(c.amount),0) total_amount,
		ifnull(count(c.amount),0) total_count
	from sub_info a 
	left join acc_info c  on a.sub_id  = c.sub_id and c.acc_period like concat(#{year},''%'')
	where a.sub_type = 1
)t2 on 1=1
order by type,sort,item', NULL, NULL, NULL, NULL);

delete from home.rep_column where rep_id in (1,2,3,4);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(1, 1, 1, 1, 10, '所属期间', 'string', 'accPeriod', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(2, 1, 1, 1, 20, '分类科目', 'string', 'subTypeName', 1, 1, 18, 70, '1', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(3, 1, 1, 1, 30, '科目代码', 'string', 'subCode', 1, 1, 18, NULL, '0', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(4, 1, 1, 1, 40, '科目名称', 'string', 'subName', 1, 1, 18, NULL, '1', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(5, 1, 1, 1, 50, '金额', 'number', 'amount', 1, 1, 18, 100, '0', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(6, 1, 1, 1, 60, '业务发生日期', 'string', 'accDate', 1, 1, 18, 100, '0', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(7, 1, 1, 1, 70, '摘要', 'string', 'digest', 1, 1, 18, 100, '1', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(8, 1, 1, 1, 80, '结算方式', 'string', 'paymentTypeName', 1, 1, 18, 100, '0', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(9, 1, 1, 1, 90, '创建时间', 'string', 'createDate', 1, 1, 18, 150, '0', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(10, 1, 1, 1, 100, '创建人', 'string', 'createUserName', 1, 1, 18, 80, '0', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(11, 2, 2, 1, 10, '月份|项目', 'string', '', 1, 1, 18, 100, '0', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(12, 2, 2, 1, 20, '1月', 'number', 'month1', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '1', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=${subType}', '{"beginMonth":"01","endMonth":"01"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(13, 2, 2, 1, 30, '2月', 'number', 'month2', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '1', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=${subType}', '{"beginMonth":"02","endMonth":"02"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(14, 2, 2, 1, 40, '3月', 'number', 'month3', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '1', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=${subType}', '{"beginMonth":"03","endMonth":"03"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(15, 2, 2, 1, 50, '4月', 'number', 'month4', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '1', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=${subType}', '{"beginMonth":"04","endMonth":"04"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(16, 2, 2, 1, 60, '5月', 'number', 'month5', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '1', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=${subType}', '{"beginMonth":"05","endMonth":"05"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(17, 2, 2, 1, 70, '6月', 'number', 'month6', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '1', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=${subType}', '{"beginMonth":"06","endMonth":"06"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(18, 2, 2, 1, 80, '7月', 'number', 'month7', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '1', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=${subType}', '{"beginMonth":"07","endMonth":"07"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(19, 2, 2, 1, 90, '8月', 'number', 'month8', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '1', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=${subType}', '{"beginMonth":"08","endMonth":"08"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(20, 2, 2, 1, 100, '9月', 'number', 'month9', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '1', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=${subType}', '{"beginMonth":"09","endMonth":"09"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(21, 2, 2, 1, 110, '10月', 'number', 'month10', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '1', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=${subType}', '{"beginMonth":"10","endMonth":"10"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(22, 2, 2, 1, 120, '11月', 'number', 'month11', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '1', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=${subType}', '{"beginMonth":"11","endMonth":"11"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(23, 2, 2, 1, 130, '12月', 'number', 'month12', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '1', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=${subType}', '{"beginMonth":"12","endMonth":"12"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(24, 2, 2, 1, 140, '本年累计', 'number', 'accumulateYear', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '1', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=${subType}', '{"beginMonth":"01","endMonth":"12"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(26, 3, 3, 1, 10, '月份|支出大类', 'string', 'subName', 1, 2, 18, 100, '0', NULL, NULL, NULL, NULL, '1', '0', '0', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(27, 3, 3, 1, 30, '1月', 'number', 'month1', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', '{"beginMonth":"01","endMonth":"01"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(28, 3, 3, 1, 40, '2月', 'number', 'month2', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', '{"beginMonth":"02","endMonth":"02"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(29, 3, 3, 1, 50, '3月', 'number', 'month3', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', '{"beginMonth":"03","endMonth":"03"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(30, 3, 3, 1, 60, '4月', 'number', 'month4', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', '{"beginMonth":"04","endMonth":"04"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(31, 3, 3, 1, 70, '5月', 'number', 'month5', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', '{"beginMonth":"05","endMonth":"05"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(32, 3, 3, 1, 80, '6月', 'number', 'month6', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', '{"beginMonth":"06","endMonth":"06"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(33, 3, 3, 1, 90, '7月', 'number', 'month7', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', '{"beginMonth":"07","endMonth":"07"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(34, 3, 3, 1, 100, '8月', 'number', 'month8', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', '{"beginMonth":"08","endMonth":"08"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(35, 3, 3, 1, 110, '9月', 'number', 'month9', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', '{"beginMonth":"09","endMonth":"09"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(36, 3, 3, 1, 120, '10月', 'number', 'month10', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', '{"beginMonth":"10","endMonth":"10"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(37, 3, 3, 1, 130, '11月', 'number', 'month11', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', '{"beginMonth":"11","endMonth":"11"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(38, 3, 3, 1, 140, '12月', 'number', 'month12', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', '{"beginMonth":"12","endMonth":"12"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(39, 3, 3, 1, 150, '本年累计', 'number', 'bnlj', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', '{"beginMonth":"01","endMonth":"12"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(40, 3, 3, 1, 160, '全年支出金额占比（%）', 'number', 'qnzczb', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '0', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(41, 3, 3, 1, 20, '月份|支出大类', 'string', 'item', 1, 0, 18, 100, '0', NULL, NULL, NULL, NULL, '0', '0', '0', '/account/adjust?startPeriod=${beginMonthPeriod}&endPeriod=${endMonthPeriod}&subType=2&subCode=${subCode}', NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(42, 4, 4, 1, 10, '月份|收入大类', 'string', 'subName', 1, 2, 18, 100, '0', NULL, NULL, NULL, NULL, '1', '0', '0', '&subCode=${subCode}', NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(43, 4, 4, 1, 30, '1月', 'number', 'month1', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '&subCode=${subCode}', '{"beginMonth":"01","endMonth":"01"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(44, 4, 4, 1, 40, '2月', 'number', 'month2', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '&subCode=${subCode}', '{"beginMonth":"02","endMonth":"02"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(45, 4, 4, 1, 50, '3月', 'number', 'month3', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '&subCode=${subCode}', '{"beginMonth":"03","endMonth":"03"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(46, 4, 4, 1, 60, '4月', 'number', 'month4', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '&subCode=${subCode}', '{"beginMonth":"04","endMonth":"04"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(47, 4, 4, 1, 70, '5月', 'number', 'month5', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '&subCode=${subCode}', '{"beginMonth":"05","endMonth":"05"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(48, 4, 4, 1, 80, '6月', 'number', 'month6', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '&subCode=${subCode}', '{"beginMonth":"06","endMonth":"06"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(49, 4, 4, 1, 90, '7月', 'number', 'month7', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '&subCode=${subCode}', '{"beginMonth":"07","endMonth":"07"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(50, 4, 4, 1, 100, '8月', 'number', 'month8', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '&subCode=${subCode}', '{"beginMonth":"08","endMonth":"08"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(51, 4, 4, 1, 110, '9月', 'number', 'month9', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '&subCode=${subCode}', '{"beginMonth":"09","endMonth":"09"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(52, 4, 4, 1, 120, '10月', 'number', 'month10', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '&subCode=${subCode}', '{"beginMonth":"10","endMonth":"10"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(53, 4, 4, 1, 130, '11月', 'number', 'month11', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '&subCode=${subCode}', '{"beginMonth":"11","endMonth":"11"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(54, 4, 4, 1, 140, '12月', 'number', 'month12', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '&subCode=${subCode}', '{"beginMonth":"12","endMonth":"12"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(55, 4, 4, 1, 150, '本年累计', 'number', 'bnlj', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '1', '&subCode=${subCode}', '{"beginMonth":"01","endMonth":"12"}');
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(56, 4, 4, 1, 160, '全年收入金额占比（%）', 'number', 'qnzczb', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '0', '0', '&subCode=${subCode}', NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(57, 4, 4, 1, 20, '月份|收入大类', 'string', 'item', 1, 0, 18, 100, '0', NULL, NULL, NULL, NULL, '0', '0', '0', '&subCode=${subCode}', NULL);


delete from home.rep_row where rep_id in (1,2,3,4);
INSERT INTO home.rep_row (row_id, rep_id, sheet_id, header_index, row_index, row_name, row_span, col_span, height, create_date, create_user, update_date, update_user, is_metadata, metadata_router, metadata_self_params) VALUES(1, 2, 2, 1, 1, '本期收入', 0, 0, 0, NULL, NULL, NULL, NULL, '1', NULL, '{"subType":"1"}');
INSERT INTO home.rep_row (row_id, rep_id, sheet_id, header_index, row_index, row_name, row_span, col_span, height, create_date, create_user, update_date, update_user, is_metadata, metadata_router, metadata_self_params) VALUES(2, 2, 2, 1, 2, '本期支出', 0, 0, 0, NULL, NULL, NULL, NULL, '1', NULL, '{"subType":"2"}');
INSERT INTO home.rep_row (row_id, rep_id, sheet_id, header_index, row_index, row_name, row_span, col_span, height, create_date, create_user, update_date, update_user, is_metadata, metadata_router, metadata_self_params) VALUES(3, 2, 2, 1, 3, '本期余额', 0, 0, 0, NULL, NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO home.rep_row (row_id, rep_id, sheet_id, header_index, row_index, row_name, row_span, col_span, height, create_date, create_user, update_date, update_user, is_metadata, metadata_router, metadata_self_params) VALUES(4, 2, 2, 1, 4, '期初余额', 0, 0, 0, NULL, NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO home.rep_row (row_id, rep_id, sheet_id, header_index, row_index, row_name, row_span, col_span, height, create_date, create_user, update_date, update_user, is_metadata, metadata_router, metadata_self_params) VALUES(5, 2, 2, 1, 5, '期末余额', 0, 0, 0, NULL, NULL, NULL, NULL, '0', NULL, NULL);

delete from home.sys_config where config_id in (6);
INSERT INTO home.sys_config (config_id, config_key, config_value, config_desc) VALUES(6, 'SYS_DEFAULT_BATCH_HANDLER_SIZE', '500', '批量处理的单次最大数据量');