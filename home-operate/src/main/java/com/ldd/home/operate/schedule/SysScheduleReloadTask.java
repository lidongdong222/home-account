package com.ldd.home.operate.schedule;


import com.ldd.home.operate.common.utils.AppUtil;
import com.ldd.home.operate.common.utils.DateUtil;
import com.ldd.home.operate.common.utils.ExceptionUtil;
import com.ldd.home.operate.common.utils.SpringContextUtil;
import com.ldd.home.operate.config.quartz.SchedulerComponent;
import com.ldd.home.operate.entity.SysScheduleHis;
import com.ldd.home.operate.mapper.SysScheduleMapper;
import com.ldd.home.operate.service.ISysScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;


@Slf4j
@DisallowConcurrentExecution
public class SysScheduleReloadTask implements Job {

    SchedulerComponent schedulerComponent = SpringContextUtil.getBean(SchedulerComponent.class);
    ISysScheduleService scheduleService = SpringContextUtil.getBean(ISysScheduleService.class);
    SysScheduleMapper scheduleMapper = SpringContextUtil.getBean(SysScheduleMapper.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("{}定时加载更新批量作业开始！",getClass().getName());

        int status = SysScheduleHis.StatusEnum.SUCCESS.getCode();
        LocalDateTime start = LocalDateTime.now();
        String errMessage="执行成功";

        try {
            schedulerComponent.schedulerAllJob();
        }catch (Exception e){
            log.error("{}.execute 执行异常:{}",getClass().getName(),e.getMessage());
            errMessage = ExceptionUtil.getExceptionCause(e,500);
        }
        SysScheduleHis his= SysScheduleHis.builder()
                .jobId(jobExecutionContext.getJobDetail().getKey().getName())
                .groupId(jobExecutionContext.getJobDetail().getKey().getGroup())
                .ipAddr(AppUtil.getCurrentIp())
                .status(status)
                .execStartDate(start)
                .execEndDate(LocalDateTime.now())
                .execTimeConsum(DateUtil.betweenSecond(start,LocalDateTime.now()))
                .errMessage(errMessage)
                .build();
        scheduleService.addSysScheduleHis(his);
        log.info("{}定时加载更新批量作业结束！",getClass().getName());
    }


}
