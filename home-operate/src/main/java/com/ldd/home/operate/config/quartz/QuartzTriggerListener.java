package com.ldd.home.operate.config.quartz;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.AppUtil;
import com.ldd.home.operate.common.utils.ExceptionUtil;
import com.ldd.home.operate.common.utils.IdUtil;
import com.ldd.home.operate.common.utils.SpringContextUtil;
import com.ldd.home.operate.entity.SysSchedule;
import com.ldd.home.operate.entity.SysScheduleHis;
import com.ldd.home.operate.mapper.SysScheduleHisMapper;
import com.ldd.home.operate.mapper.SysScheduleMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.ldd.home.operate.config.redis.RedisKeyConstant.SYS_SCHEDULER;
import static com.ldd.home.operate.config.redis.RedisLockConstant.COMMON_RLOCK_EXPIRE_TIME_SECONDS;
import static com.ldd.home.operate.config.redis.RedisLockConstant.COMMON_RLOCK_WAIT_TIME_SECONDS;
import static com.ldd.home.operate.config.redis.RedisLockConstant.RLOCK_HOME_SYS_SCHEDULE_UPDATE_RUNNING_STATUS;

@Slf4j
public class QuartzTriggerListener implements TriggerListener {

    Scheduler scheduler;
    SysScheduleMapper scheduleMapper;
    RedisTemplate redisTemplate = SpringContextUtil.getBean("redisTemplate", RedisTemplate.class);
    SysScheduleHisMapper hisMapper = SpringContextUtil.getBean(SysScheduleHisMapper.class);
    RedissonClient redissonClient = SpringContextUtil.getBean(RedissonClient.class);





    public QuartzTriggerListener(Scheduler scheduler,SysScheduleMapper sysScheduleMapper) {
        this.scheduler = scheduler;
        this.scheduleMapper =sysScheduleMapper;
    }




    @Override
    public String getName() {
        return "QuartzTriggerListener";
    }

    /**
     * 触发器被触发回调
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {

    }

    /**
     * 触发器即将被触发，但实际未触发
     * 该监听器主要是为了防止在集群环境中多容器跑同一批量的问题
     * return true 本次触发不再执行 false触发正常执行
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        String sessionId = IdUtil.getUUid();
        jobExecutionContext.getMergedJobDataMap().put("sessionId",sessionId);
        RLock lock = redissonClient.getLock(RLOCK_HOME_SYS_SCHEDULE_UPDATE_RUNNING_STATUS+":"+jobKey);
        try {
            //锁-更新当前作业在执行中
            if (lock.tryLock(COMMON_RLOCK_WAIT_TIME_SECONDS,
                    COMMON_RLOCK_EXPIRE_TIME_SECONDS,
                    TimeUnit.SECONDS)) {
                SysSchedule schedule = scheduleMapper.selectOne(Wrappers.lambdaQuery(SysSchedule.class)
                        .eq(SysSchedule::getJobId,jobKey.getName())
                        .eq(SysSchedule::getGroupId,jobKey.getGroup()));
                String redisDataKey = SYS_SCHEDULER+":"+jobKey;

                //检查版本号
                checkVersionAndReloadSchedule(jobExecutionContext, schedule);

                SysSchedule cache = (SysSchedule) redisTemplate.opsForValue().get(redisDataKey);
                //检查执行策略、执行状态
                if(Objects.nonNull(cache)){
                    checkStrategyAndRunningStatus(jobExecutionContext, cache);
                }else{
                    schedule.setRunningStatus(SysSchedule.RunningStatusEnum.RUNNING.getCode());
                    schedule.setBeginRunningDate(LocalDateTime.now());
                    schedule.setRunningIp(AppUtil.getCurrentIp());
                    schedule.setSessionId(sessionId);
                    redisTemplate.opsForValue().set(redisDataKey,schedule,Duration.ofHours(1));
                }

            }
        } catch (Exception e) {
            log.info("vetoJobExecution 批量任务{}未执行：{}",
                    jobExecutionContext.getJobDetail().getKey(),
                    e.getMessage());
            LocalDateTime now = LocalDateTime.now();
            hisMapper.insert(SysScheduleHis.builder()
                    .execStartDate(now).execEndDate(now).execTimeConsum(0)
                    .jobId(jobExecutionContext.getJobDetail().getKey().getName())
                    .groupId(jobExecutionContext.getJobDetail().getKey().getGroup())
                    .version(jobExecutionContext.getJobDetail().getJobDataMap().getString("version"))
                    .ipAddr(AppUtil.getCurrentIp())
                    .status(SysScheduleHis.StatusEnum.FAIL.getCode()).errMessage(ExceptionUtil.getExceptionCause(e,500))
                    .build());
            return true;
        } finally {
            if (lock.isLocked()) lock.unlock();
        }
        return false;
    }


    /**
     * 到达触发时间点，由于线程池原因或者串行策略原因未被触发
     */
    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    /**
     * 触发完成
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        SysSchedule cache = (SysSchedule) redisTemplate.opsForValue().get(SYS_SCHEDULER+":"+jobExecutionContext.getJobDetail().getKey());
        if(Objects.nonNull(cache) && Objects.equals(jobExecutionContext.getMergedJobDataMap().get("sessionId"),cache.getSessionId()))
            redisTemplate.delete(SYS_SCHEDULER+":"+jobExecutionContext.getJobDetail().getKey());
    }

    private void checkVersionAndReloadSchedule(JobExecutionContext jobExecutionContext, SysSchedule schedule) throws SchedulerException, BusinessException, ClassNotFoundException {
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        String version = jobExecutionContext.getMergedJobDataMap().getString("version");
        if (!Objects.equals(schedule.getVersion(),version)) {
            if (scheduler.checkExists(jobKey)) scheduler.deleteJob(jobKey);
            JobDetail jobDetail = JobBuilder.newJob()
                    .withIdentity(jobKey)
                    .ofType((Class<? extends Job>) Class.forName(schedule.getClassName()))
                    .usingJobData("version", schedule.getVersion())
                    .storeDurably()
                    .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule(schedule.getCron()))
                    .withIdentity(TriggerKey.triggerKey(schedule.getJobId(), schedule.getGroupId()))
                    .forJob(jobDetail)
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            if(Objects.equals(schedule.getStatus(),SysSchedule.StatusEnum.DISABLE.getCode())) scheduler.pauseJob(jobKey);
            log.info("{}已加载最新版本:{}", jobKey, schedule.getVersion());
            throw new BusinessException(jobExecutionContext.getJobDetail().getKey().toString() + "批量任务版本号已刷新，重新加载等待下次触发！");
        }
    }

    /**
     * 校验执行策略
     */
    private void checkStrategyAndRunningStatus(JobExecutionContext jobExecutionContext, SysSchedule cacheSchedule) throws BusinessException {
        //针对集群环境多节点之间
        if (Objects.equals(cacheSchedule.getRunningStatus(),SysSchedule.RunningStatusEnum.RUNNING.getCode())
                && !cacheSchedule.getRunningIp().equals(AppUtil.getCurrentIp())) {
            throw new BusinessException(jobExecutionContext.getJobDetail().getKey().toString() + "批量任务正在执行中！");
        }

        //针对当前节点高频批量
        if(Objects.equals(cacheSchedule.getRunningIp(),AppUtil.getCurrentIp())
                && Objects.equals(cacheSchedule.getRunningStatus(),SysSchedule.RunningStatusEnum.RUNNING.getCode())
                && Objects.equals(cacheSchedule.getHighFrequency(),SysSchedule.HighFrequencyEnum.YES.getCode()) ){
            throw new BusinessException(jobExecutionContext.getJobDetail().getKey().toString() + "批量任务正在执行中！");
        }
//            让当前触发继续等待直至执行中的任务执行结束
//            TODO 如何实现等待触发？？?
    }
}
