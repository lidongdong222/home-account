use home;

alter table sys_schedule add column high_frequency tinyint comment '是否高频批量 1是 2否 每几分钟就执行的批量为高频批量' after exec_strategy;

create table sys_config(
config_id int primary key auto_increment comment '主键',
config_key varchar(60) comment 'key',
config_value varchar(300) comment 'value',
config_desc varchar(200) comment '配置描述'
) comment '系统配置信息表';

create table sys_user (
	user_id bigint primary key auto_increment comment '主键',
	usercode varchar(50) comment '账号',
	username varchar(30) comment '用户名',
	phone varchar(20) comment '手机号',
	salt varchar(30) comment '',
	password varchar(30) comment '密码',
	remark varchar(100) comment '备注',
	`create_date` datetime DEFAULT NULL,
  `create_user` varchar(30) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` varchar(30) DEFAULT NULL
) comment '用户信息表';

create table sys_user_role (
	ur_id bigint primary key auto_increment comment '主键',
	user_id bigint  comment '主键',
	role_id int comment '权限id'
)comment '用户角色列表信息表';

create table sys_role (
	role_id bigint primary key auto_increment comment '主键',
	role_name int comment '角色id',
	role_desc int comment '角色描述'	
)comment '角色信息表';
create table sys_role_permisson(
	srp_id bigint primary key auto_increment comment '主键',
	role_id bigint comment '角色id',
	perm_id bigint comment '权限id'
)comment '角色权限表';
create table sys_permission (
	perm_id bigint primary key auto_increment comment '主键',
	perm_code int comment '权限编码',
	perm_type tinyint comment '1.菜单权限 2功能权限',
	perm_url varchar(200) comment '权限接口url',
	perm_desc varchar(200) comment '权限描述'	
)comment '权限信息表';