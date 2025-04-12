package com.ldd.home.operate.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.ext.Page;
import com.ldd.home.operate.entity.SysSchedule;
import com.ldd.home.operate.entity.req.BaseReq;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.service.ISysScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 批量定时作业表 前端控制器
 * </p>
 *
 * @author ldd
 * @since 2024-08-26
 */
@Slf4j
@RestController
@RequestMapping("/sys/schedule")
public class SysScheduleController {

    @Autowired
    ISysScheduleService scheduleService;

    /**
     * 查询批量列表
     * @param req
     * @return
     */
    @PostMapping("/getScheduleList")
    public Result getScheduleList(@RequestBody @Validated(Page.class) BaseReq req){
        log.info("{}.getScheduleList入参：{}",getClass().getName(), JSONObject.toJSONString(req));
        Result result = scheduleService.getScheduleList(req);
        log.info("{}.getScheduleList出参：{}",getClass().getName(),result);
        return result;
    }

    /**
     * 添加批量
     * @param req
     * @return
     */
    @PostMapping("/addSchedule")
    public Result addSchedule(@RequestBody @Validated() SysSchedule req) throws Exception {
        log.info("{}.addSchedule入参：{}",getClass().getName(), JSONObject.toJSONString(req));
        if(!CronExpression.isValidExpression(req.getCron())){
            throw new BusinessException("cron表达式解析失败！");
        }
        Result result = scheduleService.addSchedule(req);
        log.info("{}.addSchedule出参：{}",getClass().getName(),result);
        return result;
    }


    /**
     * 更新批量
     * @param req
     * @return
     */
    @PostMapping("/updSchedule")
    public Result updSchedule(@RequestBody SysSchedule req) throws BusinessException, SchedulerException, ClassNotFoundException {
        log.info("{}.updSchedule入参：{}",getClass().getName(), JSONObject.toJSONString(req));
        Result result = scheduleService.updSchedule(req);
        log.info("{}.updSchedule出参：{}",getClass().getName(),result);
        return result;
    }

    /**
     * 删除批量
     * @param req
     * @return
     */
    @PostMapping("/delSchedule")
    public Result delSchedule(@RequestBody IdReq req) throws BusinessException, SchedulerException {
        log.info("{}.delSchedule入参：{}",getClass().getName(), JSONObject.toJSONString(req));
        Result result = scheduleService.delSchedule(req);
        log.info("{}.delSchedule出参：{}",getClass().getName(),result);
        return result;
    }

    /**
     * 立即执行批量
     * @return
     */
    @PostMapping("/execImmediateSchedule")
    public Result execImmediateSchedule(@RequestBody IdReq req) throws BusinessException, SchedulerException, InterruptedException {
        log.info("{}.delSchedule入参：{}",getClass().getName(), JSONObject.toJSONString(req));
        Result result = scheduleService.execImmediateSchedule(req);
        log.info("{}.delSchedule出参：{}",getClass().getName(),result);
        return result;
    }
}
