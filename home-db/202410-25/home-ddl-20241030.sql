use home;

alter table rep_column  add column is_data_merge_cell char(2) default '0' comment '是否合并数据区间相同值的单元格（仅竖向合并） 0-否 1-是' ;
alter table rep_column  add column is_number_format char(2) default '1' comment 'number类型是否格式化为固定小数位 0-否 1-是' ;
