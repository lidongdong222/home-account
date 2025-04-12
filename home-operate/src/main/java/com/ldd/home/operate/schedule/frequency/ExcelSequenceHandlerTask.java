package com.ldd.home.operate.schedule.frequency;

import com.alibaba.fastjson2.JSONObject;
import com.ldd.home.operate.common.utils.AppUtil;
import com.ldd.home.operate.common.utils.DateUtil;
import com.ldd.home.operate.common.utils.SpringContextUtil;
import com.ldd.home.operate.entity.ImpInfo;
import com.ldd.home.operate.entity.SysScheduleHis;
import com.ldd.home.operate.service.IImpInfoService;
import com.ldd.home.operate.service.ISysScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 每2分钟执行一次，减少用户导入等待时长
 * 系统后台排序执行EXCEL、CSV等数据导入流程，防止集中时间处理造成内存溢出
 * 禁用串行策略DisallowConcurrentExecution 是因为该任务执行频率较高，在失火后不需要等待当前任务结束后继续执行（高频率阻塞线程），可以直接结束任务，等待下次触发即可
 */
@Slf4j
//@DisallowConcurrentExecution
public class ExcelSequenceHandlerTask implements Job {

    ISysScheduleService scheduleService = SpringContextUtil.getBean(ISysScheduleService.class);
    IImpInfoService impInfoService = SpringContextUtil.getBean(IImpInfoService.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("{}Excel导入排队作业开始！", getClass().getName());
        main(context);
        log.info("{}Excel导入排队作业结束！", getClass().getName());
    }

    /**
     * 导入处理
     *
     * @param context
     */
    private void main(JobExecutionContext context) {
        int status = SysScheduleHis.StatusEnum.SUCCESS.getCode();
        LocalDateTime start = LocalDateTime.now();
        String errMessage = "执行成功";


        List<ImpInfo> impInfos = impInfoService.getWaitingList();
        log.error("{}Excel导入排队作业本次加载处理条数：{}", getClass().getName(), impInfos.size());
        for (ImpInfo impInfo : impInfos) {
            log.error("{}Excel导入排队作业正在执行：{}", getClass().getName(), JSONObject.toJSONString(impInfo));
            try {
                Object obj = SpringContextUtil.getBean(impInfo.getAnalysisBean());
                Method method = obj.getClass().getMethod(impInfo.getAnalysisMethod(),long.class);
                method.invoke(obj, impInfo.getImpId());
            } catch (Exception e) {
                log.error("{}Excel导入排队作业执行异常：{}", getClass().getName(), e.getMessage());
            }
        }

        SysScheduleHis his = SysScheduleHis.builder()
                .jobId(context.getJobDetail().getKey().getName())
                .groupId(context.getJobDetail().getKey().getGroup())
                .ipAddr(AppUtil.getCurrentIp())
                .status(status)
                .execStartDate(start)
                .execEndDate(LocalDateTime.now())
                .execTimeConsum(DateUtil.betweenSecond(start, LocalDateTime.now()))
                .errMessage(errMessage)
                .build();
        scheduleService.addSysScheduleHis(his);
    }
}
