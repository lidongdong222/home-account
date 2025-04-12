package com.ldd.home.operate.service;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.entity.req.ResourceHisReq;

import java.text.ParseException;

/**
 * 导入公共业务处理类
 */
public interface IImportExportService {

    Result getImportHisList(ResourceHisReq req) throws ParseException;

}
