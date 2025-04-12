use home;

delete from home.rep_sheet where sheet_id in (2,3,4);
INSERT INTO home.rep_sheet (sheet_id, rep_id, sheet_index, sheet_name, title, deal_type, deal_bean, deal_method, deal_sql, create_date, create_user, update_date, update_user) VALUES(2, 2, 1, '记账月度统计表', '${year}年记账月度统计表', '2', NULL, NULL, 'select 
	1 sort,
	ifnull(sum(case when a.period = ''${year}01'' then a.income else 0 end),0)/${unit} month1,
	ifnull(sum(case when a.period = ''${year}02'' then a.income else 0 end),0)/${unit} month2,
	ifnull(sum(case when a.period = ''${year}03'' then a.income else 0 end),0)/${unit} month3,
	ifnull(sum(case when a.period = ''${year}04'' then a.income else 0 end),0)/${unit} month4,
	ifnull(sum(case when a.period = ''${year}05'' then a.income else 0 end),0)/${unit} month5,
	ifnull(sum(case when a.period = ''${year}06'' then a.income else 0 end),0)/${unit} month6,
	ifnull(sum(case when a.period = ''${year}07'' then a.income else 0 end),0)/${unit} month7,
	ifnull(sum(case when a.period = ''${year}08'' then a.income else 0 end),0)/${unit} month8,
	ifnull(sum(case when a.period = ''${year}09'' then a.income else 0 end),0)/${unit} month9,
	ifnull(sum(case when a.period = ''${year}10'' then a.income else 0 end),0)/${unit} month10,
	ifnull(sum(case when a.period = ''${year}11'' then a.income else 0 end),0)/${unit} month11,
	ifnull(sum(case when a.period = ''${year}12'' then a.income else 0 end),0)/${unit} month12,
	ifnull(sum(a.income),0)/${unit} accumulateYear,
	date_format(max(create_date),''%Y-%m-%d %H:%i:%s'')  createDate
from acc_balance a
where a.period_type = ''3''
and a.period like concat(#{year},''%'')
union 
select 
	2 sort,
	ifnull(sum(case when a.period = ''${year}01'' then a.expenditure else 0 end),0)/${unit} month1,
	ifnull(sum(case when a.period = ''${year}02'' then a.expenditure else 0 end),0)/${unit} month2,
	ifnull(sum(case when a.period = ''${year}03'' then a.expenditure else 0 end),0)/${unit} month3,
	ifnull(sum(case when a.period = ''${year}04'' then a.expenditure else 0 end),0)/${unit} month4,
	ifnull(sum(case when a.period = ''${year}05'' then a.expenditure else 0 end),0)/${unit} month5,
	ifnull(sum(case when a.period = ''${year}06'' then a.expenditure else 0 end),0)/${unit} month6,
	ifnull(sum(case when a.period = ''${year}07'' then a.expenditure else 0 end),0)/${unit} month7,
	ifnull(sum(case when a.period = ''${year}08'' then a.expenditure else 0 end),0)/${unit} month8,
	ifnull(sum(case when a.period = ''${year}09'' then a.expenditure else 0 end),0)/${unit} month9,
	ifnull(sum(case when a.period = ''${year}10'' then a.expenditure else 0 end),0)/${unit} month10,
	ifnull(sum(case when a.period = ''${year}11'' then a.expenditure else 0 end),0)/${unit} month11,
	ifnull(sum(case when a.period = ''${year}12'' then a.expenditure else 0 end),0)/${unit} month12,
	ifnull(sum(a.expenditure),0)/${unit} accumulateYear,
	date_format(max(create_date),''%Y-%m-%d %H:%i:%s'')  createDate
from acc_balance a
where a.period_type = ''3''
and a.period like concat(#{year},''%'')
union 
select 
	3 sort,
	ifnull(sum(case when a.period = ''${year}01'' then a.balance else 0 end),0)/${unit} month1,
	ifnull(sum(case when a.period = ''${year}02'' then a.balance else 0 end),0)/${unit} month2,
	ifnull(sum(case when a.period = ''${year}03'' then a.balance else 0 end),0)/${unit} month3,
	ifnull(sum(case when a.period = ''${year}04'' then a.balance else 0 end),0)/${unit} month4,
	ifnull(sum(case when a.period = ''${year}05'' then a.balance else 0 end),0)/${unit} month5,
	ifnull(sum(case when a.period = ''${year}06'' then a.balance else 0 end),0)/${unit} month6,
	ifnull(sum(case when a.period = ''${year}07'' then a.balance else 0 end),0)/${unit} month7,
	ifnull(sum(case when a.period = ''${year}08'' then a.balance else 0 end),0)/${unit} month8,
	ifnull(sum(case when a.period = ''${year}09'' then a.balance else 0 end),0)/${unit} month9,
	ifnull(sum(case when a.period = ''${year}10'' then a.balance else 0 end),0)/${unit} month10,
	ifnull(sum(case when a.period = ''${year}11'' then a.balance else 0 end),0)/${unit} month11,
	ifnull(sum(case when a.period = ''${year}12'' then a.balance else 0 end),0)/${unit} month12,
	ifnull(sum(a.balance),0)/${unit} accumulateYear,
	date_format(max(create_date),''%Y-%m-%d %H:%i:%s'')  createDate
from acc_balance a
where a.period_type = ''3''
and a.period like concat(#{year},''%'')
union 
select 
	4 sort,
	ifnull(sum(case when a.period = ''${year}01'' then a.begin_balance else 0 end),0)/${unit} month1,
	ifnull(sum(case when a.period = ''${year}02'' then a.begin_balance else 0 end),0)/${unit} month2,
	ifnull(sum(case when a.period = ''${year}03'' then a.begin_balance else 0 end),0)/${unit} month3,
	ifnull(sum(case when a.period = ''${year}04'' then a.begin_balance else 0 end),0)/${unit} month4,
	ifnull(sum(case when a.period = ''${year}05'' then a.begin_balance else 0 end),0)/${unit} month5,
	ifnull(sum(case when a.period = ''${year}06'' then a.begin_balance else 0 end),0)/${unit} month6,
	ifnull(sum(case when a.period = ''${year}07'' then a.begin_balance else 0 end),0)/${unit} month7,
	ifnull(sum(case when a.period = ''${year}08'' then a.begin_balance else 0 end),0)/${unit} month8,
	ifnull(sum(case when a.period = ''${year}09'' then a.begin_balance else 0 end),0)/${unit} month9,
	ifnull(sum(case when a.period = ''${year}10'' then a.begin_balance else 0 end),0)/${unit} month10,
	ifnull(sum(case when a.period = ''${year}11'' then a.begin_balance else 0 end),0)/${unit} month11,
	ifnull(sum(case when a.period = ''${year}12'' then a.begin_balance else 0 end),0)/${unit} month12,
	''--'' accumulateYear,
	date_format(max(create_date),''%Y-%m-%d %H:%i:%s'')  createDate
from acc_balance a
where a.period_type = ''3''
and a.period like concat(#{year},''%'')
union 
select 
	5 sort,
	ifnull(sum(case when a.period = ''${year}01'' then a.end_balance else 0 end),0)/${unit} month1,
	ifnull(sum(case when a.period = ''${year}02'' then a.end_balance else 0 end),0)/${unit} month2,
	ifnull(sum(case when a.period = ''${year}03'' then a.end_balance else 0 end),0)/${unit} month3,
	ifnull(sum(case when a.period = ''${year}04'' then a.end_balance else 0 end),0)/${unit} month4,
	ifnull(sum(case when a.period = ''${year}05'' then a.end_balance else 0 end),0)/${unit} month5,
	ifnull(sum(case when a.period = ''${year}06'' then a.end_balance else 0 end),0)/${unit} month6,
	ifnull(sum(case when a.period = ''${year}07'' then a.end_balance else 0 end),0)/${unit} month7,
	ifnull(sum(case when a.period = ''${year}08'' then a.end_balance else 0 end),0)/${unit} month8,
	ifnull(sum(case when a.period = ''${year}09'' then a.end_balance else 0 end),0)/${unit} month9,
	ifnull(sum(case when a.period = ''${year}10'' then a.end_balance else 0 end),0)/${unit} month10,
	ifnull(sum(case when a.period = ''${year}11'' then a.end_balance else 0 end),0)/${unit} month11,
	ifnull(sum(case when a.period = ''${year}12'' then a.end_balance else 0 end),0)/${unit} month12,
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
ifnull(sum(case when c.acc_period = ''${year}01'' then c.amount else 0 end),0)/${unit} month1,
ifnull(sum(case when c.acc_period = ''${year}02'' then c.amount else 0 end),0)/${unit} month2,
ifnull(sum(case when c.acc_period = ''${year}03'' then c.amount else 0 end),0)/${unit} month3,
ifnull(sum(case when c.acc_period = ''${year}04'' then c.amount else 0 end),0)/${unit} month4,
ifnull(sum(case when c.acc_period = ''${year}05'' then c.amount else 0 end),0)/${unit} month5,
ifnull(sum(case when c.acc_period = ''${year}06'' then c.amount else 0 end),0)/${unit} month6,
ifnull(sum(case when c.acc_period = ''${year}07'' then c.amount else 0 end),0)/${unit} month7,
ifnull(sum(case when c.acc_period = ''${year}08'' then c.amount else 0 end),0)/${unit} month8,
ifnull(sum(case when c.acc_period = ''${year}09'' then c.amount else 0 end),0)/${unit} month9,
ifnull(sum(case when c.acc_period = ''${year}10'' then c.amount else 0 end),0)/${unit} month10,
ifnull(sum(case when c.acc_period = ''${year}11'' then c.amount else 0 end),0)/${unit} month11,
ifnull(sum(case when c.acc_period = ''${year}12'' then c.amount else 0 end),0)/${unit} month12,
ifnull(sum(c.amount),0)/${unit} bnlj
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
ifnull(sum(case when c.acc_period = ''${year}01'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}02'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}03'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}04'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}05'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}06'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}07'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}08'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}09'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}10'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}11'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}12'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(c.amount),0)/${unit}
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
		ifnull(sum(c.amount),0)/${unit} total_amount,
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
ifnull(sum(case when c.acc_period = ''${year}01'' then c.amount else 0 end),0)/${unit} month1,
ifnull(sum(case when c.acc_period = ''${year}02'' then c.amount else 0 end),0)/${unit} month2,
ifnull(sum(case when c.acc_period = ''${year}03'' then c.amount else 0 end),0)/${unit} month3,
ifnull(sum(case when c.acc_period = ''${year}04'' then c.amount else 0 end),0)/${unit} month4,
ifnull(sum(case when c.acc_period = ''${year}05'' then c.amount else 0 end),0)/${unit} month5,
ifnull(sum(case when c.acc_period = ''${year}06'' then c.amount else 0 end),0)/${unit} month6,
ifnull(sum(case when c.acc_period = ''${year}07'' then c.amount else 0 end),0)/${unit} month7,
ifnull(sum(case when c.acc_period = ''${year}08'' then c.amount else 0 end),0)/${unit} month8,
ifnull(sum(case when c.acc_period = ''${year}09'' then c.amount else 0 end),0)/${unit} month9,
ifnull(sum(case when c.acc_period = ''${year}10'' then c.amount else 0 end),0)/${unit} month10,
ifnull(sum(case when c.acc_period = ''${year}11'' then c.amount else 0 end),0)/${unit} month11,
ifnull(sum(case when c.acc_period = ''${year}12'' then c.amount else 0 end),0)/${unit} month12,
ifnull(sum(c.amount),0)/${unit} bnlj
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
ifnull(sum(case when c.acc_period = ''${year}01'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}02'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}03'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}04'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}05'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}06'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}07'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}08'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}09'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}10'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}11'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(case when c.acc_period = ''${year}12'' then c.amount else 0 end),0)/${unit} ,
ifnull(sum(c.amount),0)/${unit}
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
		ifnull(sum(c.amount),0)/${unit} total_amount,
		ifnull(count(c.amount),0) total_count
	from sub_info a 
	left join acc_info c  on a.sub_id  = c.sub_id and c.acc_period like concat(#{year},''%'')
	where a.sub_type = 1
)t2 on 1=1
order by type,sort,item', NULL, NULL, NULL, NULL);
