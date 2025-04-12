use home;

INSERT INTO home.sys_menu
(menu_id, menu_code, menu_name, router, parent_id, sort, icon, status, remark)
VALUES(26, 'sys_config', '系统配置', '/sys/config', 1, 10, 'sys-config', '1', NULL);

INSERT INTO home.sys_config
(config_id, config_key, config_value, config_desc)
VALUES(1, 'SYS_TEMP_FILE_PATH', '/home/ldd/jar/home/temp', '系统临时文件存放路径');
INSERT INTO home.sys_config
(config_id, config_key, config_value, config_desc)
VALUES(4, 'SYS_RESOURCE_LOCAL_PATH', '/home/ldd/jar/home/resource', '本地资源存储路径');
INSERT INTO home.sys_config
(config_id, config_key, config_value, config_desc)
VALUES(5, 'SYS_DEFAULT_USER_PD', 'home2024!', '默认用户密码');