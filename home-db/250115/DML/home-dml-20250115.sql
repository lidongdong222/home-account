use home;

update acc_info set payment_type ='99' where payment_type ='5';
delete from home.rep_info where rep_id = 1;
INSERT INTO home.rep_info (rep_id, rep_name, remark, deal_bean, deal_method, create_date, create_user, update_date, update_user) 
VALUES(1, '账务明细', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO home.sys_dict (dict_type, dict_name, sort, status) VALUES('PAY_TYPE', '支付方式', NULL, '1');
INSERT INTO home.sys_dict_dtl (dict_id, dict_type, dict_code, dict_value, sort, status) VALUES(1, 'PAY_TYPE', '1', '银行交易', NULL, '1');
INSERT INTO home.sys_dict_dtl (dict_id, dict_type, dict_code, dict_value, sort, status) VALUES(2, 'PAY_TYPE', '2', '现金交易', NULL, '1');
INSERT INTO home.sys_dict_dtl (dict_id, dict_type, dict_code, dict_value, sort, status) VALUES(3, 'PAY_TYPE', '3', '支付宝交易', NULL, '1');
INSERT INTO home.sys_dict_dtl (dict_id, dict_type, dict_code, dict_value, sort, status) VALUES(4, 'PAY_TYPE', '4', '微信交易', NULL, '1');
INSERT INTO home.sys_dict_dtl (dict_id, dict_type, dict_code, dict_value, sort, status) VALUES(5, 'PAY_TYPE', '5', '京东白条', NULL, '1');
INSERT INTO home.sys_dict_dtl (dict_id, dict_type, dict_code, dict_value, sort, status) VALUES(6, 'PAY_TYPE', '99', '其他方式', NULL, '1');

delete from home.rep_info where rep_id = 5;
INSERT INTO home.rep_info (rep_id, rep_name, remark, deal_bean, deal_method, create_date, create_user, update_date, update_user) VALUES(5, '科目明细', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
delete from home.rep_sheet where rep_id = 5;
INSERT INTO home.rep_sheet (sheet_id, rep_id, sheet_index, sheet_name, title, deal_type, deal_bean, deal_method, deal_sql, create_date, create_user, update_date, update_user) VALUES(5, 5, 1, '科目明细', '科目明细', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
delete from home.rep_column where rep_id = 5;
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(58, 5, 5, 1, 10, '分类', 'string', 'subTypeName', 1, 1, 18, 70, '0', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(59, 5, 5, 1, 20, '科目代码', 'string', 'subCode', 1, 1, 18, 130, '1', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(60, 5, 5, 1, 30, '科目名称', 'string', 'subName', 1, 1, 18, 130, '0', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(61, 5, 5, 1, 40, '科目代码1', 'string', 'subCode1', 1, 1, 18, 100, '1', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(62, 5, 5, 1, 50, '科目名称1', 'string', 'subName1', 1, 1, 18, 100, '0', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(63, 5, 5, 1, 60, '科目代码2', 'string', 'subCode2', 1, 1, 18, 100, '0', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(64, 5, 5, 1, 70, '科目名称2', 'string', 'subName2', 1, 1, 18, 100, '1', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(65, 5, 5, 1, 80, '科目代码3', 'string', 'subCode3', 1, 1, 18, 100, '0', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(66, 5, 5, 1, 90, '科目名称3', 'string', 'subName3', 1, 1, 18, 150, '0', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);
INSERT INTO home.rep_column (col_id, rep_id, sheet_id, header_index, col_index, col_name, col_data_type, col_prop, row_span, col_span, height, width, is_necessary, create_date, create_user, update_date, update_user, is_data_merge_cell, is_number_format, is_metadata, metadata_router, metadata_self_params) VALUES(67, 5, 5, 1, 100, '创建时间', 'string', 'createDate', 1, 1, 18, NULL, '0', NULL, NULL, NULL, NULL, '0', '1', '0', NULL, NULL);

delete from home.sys_dict where dict_type = 'PAY_TYPE';
INSERT INTO home.sys_dict (dict_type, dict_name, sort, status) VALUES('PAY_TYPE', '支付方式', NULL, '1');
delete from home.sys_dict_dtl where dict_type = 'PAY_TYPE';
INSERT INTO home.sys_dict_dtl (dict_id, dict_type, dict_code, dict_value, sort, status) VALUES(1, 'PAY_TYPE', '1', '银行交易', NULL, '1');
INSERT INTO home.sys_dict_dtl (dict_id, dict_type, dict_code, dict_value, sort, status) VALUES(2, 'PAY_TYPE', '2', '现金交易', NULL, '1');
INSERT INTO home.sys_dict_dtl (dict_id, dict_type, dict_code, dict_value, sort, status) VALUES(3, 'PAY_TYPE', '3', '支付宝交易', NULL, '1');
INSERT INTO home.sys_dict_dtl (dict_id, dict_type, dict_code, dict_value, sort, status) VALUES(4, 'PAY_TYPE', '4', '微信交易', NULL, '1');
INSERT INTO home.sys_dict_dtl (dict_id, dict_type, dict_code, dict_value, sort, status) VALUES(5, 'PAY_TYPE', '5', '京东白条', NULL, '1');
INSERT INTO home.sys_dict_dtl (dict_id, dict_type, dict_code, dict_value, sort, status) VALUES(6, 'PAY_TYPE', '99', '其他方式', NULL, '1');