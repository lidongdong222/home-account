package com.ldd.home.operate.service;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.entity.SysSchedule;
import com.ldd.home.operate.entity.SysScheduleHis;
import com.ldd.home.operate.entity.req.BaseReq;
import com.ldd.home.operate.entity.req.IdReq;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * <p>
 * 批量定时作业表 服务类
 * </p>
 *
 * @author ldd
 * @since 2024-08-26
 */
public interface ISysScheduleService{

    List<SysSchedule> queryAllScheduler();

    void addSysScheduleHis(SysScheduleHis his);

    Result getScheduleList(BaseReq req);

    Result addSchedule(SysSchedule req) throws Exception;

    Result updSchedule(SysSchedule req) throws BusinessException, SchedulerException, ClassNotFoundException;

    Result delSchedule(IdReq req) throws BusinessException, SchedulerException;

    Result execImmediateSchedule(IdReq req) throws SchedulerException, BusinessException, InterruptedException;
}
