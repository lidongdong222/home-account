package com.ldd.home.operate.service;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.entity.req.ReportReq;
import jakarta.servlet.http.HttpServletResponse;

public interface IReportService {

    /**
     * 获取报表列定义信息
     * @param id
     * @return
     */
    Result getRepColumns(String id);

    void reportTemplate(String id, HttpServletResponse response) throws Exception;

    void exportStatistics(ReportReq req, HttpServletResponse response) throws Exception;

    /**
     * 获取报表定义信息
     */
    Result getReportInfo(ReportReq rep) throws Exception;
}
