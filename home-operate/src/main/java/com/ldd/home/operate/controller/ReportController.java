package com.ldd.home.operate.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ldd.home.operate.common.constant.ErrorMsgConst;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.ExceptionUtil;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.entity.req.ReportReq;
import com.ldd.home.operate.service.IReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/report")
public class ReportController {

    @Autowired
    IReportService reportService;

    @PostMapping("/getRepColumns")
    public Result getRepColumns(@RequestBody ReportReq req) throws BusinessException {
        if(StrUtil.isEmpty(req.getRepId())){
            throw new BusinessException(ErrorMsgConst.PARAMS_DELETION_ERROR);
        }
        return reportService.getRepColumns(StrUtil.toStr(req.getRepId()));
    }

    @PostMapping("/getReportInfo")
    public Result getReportInfo(@RequestBody ReportReq req) throws Exception {
        log.info("getReportInfo 入参：{}", JSON.toJSONString(req));
        Result result = reportService.getReportInfo(req);
        log.info("getReportInfo 出参：{}", JSON.toJSONString(result));
        return result;
    }

    /**
     * 导出数据
     * @param req
     * @param response
     */
    @PostMapping("/exportStatistics")
    public void exportStatistics(@RequestBody ReportReq req, HttpServletResponse response){
        try {
            if(StrUtil.isEmpty(req.getRepId())){
                throw new BusinessException(ErrorMsgConst.PARAMS_DELETION_ERROR);
            }
            reportService.exportStatistics(req,response);
        } catch (Exception e) {
            log.error(e instanceof BusinessException?e.getMessage(): ExceptionUtil.getExceptionCause(e));
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            try {
                response.getOutputStream().write(JSONObject.toJSONString(Result.fail(e.getMessage())).getBytes());
            } catch (IOException ex) {
            }
        }
    }

    /**
     * 导出报表模板
     * @param req
     * @param response
     */
    @PostMapping("/exportTemplate")
    public void reportTemplate(@RequestBody @Validated IdReq req, HttpServletResponse response){
        try {
            reportService.reportTemplate(req.getId(),response);
        } catch (Exception e) {
            log.error(e instanceof BusinessException?e.getMessage(): ExceptionUtil.getExceptionCause(e));
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            try {
                response.getOutputStream().write(JSONObject.toJSONString(Result.fail(e.getMessage())).getBytes());
            } catch (IOException ex) {
            }
        }
    }

}
