create table sys_menu(
 menu_id int auto_increment comment '菜单id',
 menu_code varchar(30) comment '菜单编码',
 menu_name varchar(30) comment '菜单名称',
 router varchar(50) comment '路由',
 parent_id int comment '父id',
 sort int comment '排序',
 icon varchar(50) comment '图标',
 status char(2) comment '状态 1启用 0禁用',
 remark varchar(100) comment '备注',
 primary key(menu_id)
)comment '菜单信息表';

