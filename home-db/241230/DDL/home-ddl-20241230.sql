use home;

alter table rep_row add column `is_metadata` char(2) DEFAULT '0' COMMENT '是否支持源菜单数据查看 0-否 1-是';
alter table rep_row add column `metadata_router` char(150) DEFAULT NULL COMMENT '源菜单路由及附带参数';
alter table rep_row add column `metadata_self_params` char(100) DEFAULT NULL COMMENT '源数据由列支持的路由参数';
