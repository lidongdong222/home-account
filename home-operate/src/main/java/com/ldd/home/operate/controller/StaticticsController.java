package com.ldd.home.operate.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ldd.home.operate.common.constant.ErrorMsgConst;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.RegexUtil;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.entity.req.YearMonthPeriodReq;
import com.ldd.home.operate.entity.req.YearMonthReq;
import com.ldd.home.operate.entity.req.YearReq;
import com.ldd.home.operate.service.IStaticticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 图表统计
 */
@RestController
@RequestMapping("/statistics")
@Slf4j
public class StaticticsController {

    String logPrefix = this.getClass().getName();

    @Autowired
    IStaticticsService staticticsService;

    /**
     * 年度消费情况
     */
    @PostMapping("/getMonthlyConsumption")
    public Result getMonthlyConsumption(@RequestBody @Validated YearReq req) throws BusinessException {
        log.info("{}.getMonthlyConsumption请求入参:{}",logPrefix, JSONObject.toJSONString(req));
        if(StrUtil.toInt(req.getYear())> LocalDateTime.now().getYear()){
            throw new BusinessException(ErrorMsgConst.PARAMS_DELETION_ERROR);
        }
        Result  result = staticticsService.getMonthlyConsumption(req);
        log.info("{}.getMonthlyConsumption请求出参:{}",logPrefix, result);
        return result;
    }

    /**
     * 月度消费占比
     */
    @PostMapping("/getMonthConsumptionSituation")
    public Result getMonthConsumptionSituation(@RequestBody @Validated YearMonthReq req) throws BusinessException {
        log.info("{}.getMonthConsumptionSituation请求入参:{}",logPrefix, JSONObject.toJSONString(req));
        if(!req.getYearMonth().matches(RegexUtil.REGEX_DATE_YYYYMM)){
            throw new BusinessException(ErrorMsgConst.PARAMS_DELETION_ERROR);
        }
        Result  result = staticticsService.getMonthConsumptionSituation(req);
        log.info("{}.getMonthConsumptionSituation请求出参:{}",logPrefix, JSONObject.toJSONString(result));
        return result;
    }

    /**
     * 月度消费占比
     */
    @PostMapping("/getAccumulatedBalanceSituation")
    public Result getAccumulatedBalanceSituation(@RequestBody @Validated YearMonthPeriodReq req) throws BusinessException {
        log.info("{}.getAccumulatedBalanceSituation请求入参:{}",logPrefix, JSONObject.toJSONString(req));
        if(!req.getBeginYearMonth().matches(RegexUtil.REGEX_DATE_YYYYMM)
                || !req.getEndYearMonth().matches(RegexUtil.REGEX_DATE_YYYYMM)
                ||StrUtil.toInt(req.getBeginYearMonth())>StrUtil.toInt(req.getEndYearMonth())){
            throw new BusinessException(ErrorMsgConst.PARAMS_DELETION_ERROR);
        }
        Result  result = staticticsService.getAccumulatedBalanceSituation(req);
        log.info("{}.getAccumulatedBalanceSituation请求出参:{}",logPrefix, JSONObject.toJSONString(result));
        return result;
    }

}
