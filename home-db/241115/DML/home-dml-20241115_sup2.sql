use home;

INSERT INTO home.sys_role
(role_id, role_code, role_name, role_desc, status, create_date, create_user, update_date, update_user)
VALUES(1, 'super-admin', '系统超级管理员', '拥有一切权限', '1', '2024-11-07 00:00:00', '1', NULL, NULL);
INSERT INTO home.sys_role
(role_id, role_code, role_name, role_desc, status, create_date, create_user, update_date, update_user)
VALUES(2, 'account', '账务管理员', '账务及账务报表管理', '1', '2024-11-07 00:00:00', '1', NULL, NULL);
