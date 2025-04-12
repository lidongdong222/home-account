use home;

INSERT INTO home.sys_menu
(menu_id, menu_code, menu_name, router, parent_id, sort, icon, status, remark)
VALUES(27, 'sys_user', '用户管理', '/sys/user', 1, 11, 'sys-user', '1', NULL);
INSERT INTO home.sys_menu
(menu_id, menu_code, menu_name, router, parent_id, sort, icon, status, remark)
VALUES(28, 'sys_role', '角色管理', '/sys/role', 1, 12, 'sys-role', '1', NULL);
INSERT INTO home.sys_menu
(menu_id, menu_code, menu_name, router, parent_id, sort, icon, status, remark)
VALUES(29, 'sys_permission', '权限管理', '/sys/permission', 1, 13, 'sys-permission', '1', NULL);
