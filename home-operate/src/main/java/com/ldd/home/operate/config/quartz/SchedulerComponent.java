package com.ldd.home.operate.config.quartz;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ldd.home.operate.common.constant.ErrorMsgConst;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.config.redis.RedisLockConstant;
import com.ldd.home.operate.entity.SysSchedule;
import com.ldd.home.operate.mapper.SysScheduleMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Component
public class SchedulerComponent {

    @Autowired
    Scheduler scheduler;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    SysScheduleMapper scheduleMapper;

    public SysSchedule getSchedule(JobKey jobKey){
        return scheduleMapper.selectOne(Wrappers.lambdaQuery(SysSchedule.class)
                .eq(SysSchedule::getJobId,jobKey.getName())
                .eq(SysSchedule::getGroupId,jobKey.getGroup()));
    }


    /**
     * 重新加载本地全部批量
     */
    public void schedulerAllJob() throws SchedulerException, ClassNotFoundException {
        List<SysSchedule> schedules = scheduleMapper.selectList(Wrappers.query());
        Set<JobKey> localSchedules = scheduler.getJobKeys(GroupMatcher.anyGroup());
        for (SysSchedule schedule : schedules) {
            JobKey jobKey = JobKey.jobKey(schedule.getJobId(),schedule.getGroupId());
            if(localSchedules.contains(jobKey)){
                String localVersion = (String) scheduler.getJobDetail(jobKey).getJobDataMap().get("version");
                if(!Objects.equals(localVersion,schedule.getVersion())){
                    schedulerJob(schedule);
                }
            }
        }
        for (JobKey jobkey : localSchedules) {
            boolean b= true;
            for (SysSchedule schedule : schedules) {
                if(jobkey.equals(JobKey.jobKey(schedule.getJobId(),schedule.getGroupId()))){
                    b = false;
                    break;
                }
            }
            if(b) deleteJob(jobkey);
        }
    }

    /**
     * 加載 批量
     *
     */
    public void schedulerJob(SysSchedule schedule) throws SchedulerException, ClassNotFoundException {
        JobKey jobKey = JobKey.jobKey(schedule.getJobId(), schedule.getGroupId());
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
        if (Objects.equals(schedule.getStatus(), SysSchedule.StatusEnum.DISABLE.getCode()))
            scheduler.pauseJob(jobKey);
        log.info("{}已加载。", jobKey);
    }

    /**
     * 卸载批量
     *
     * @param schedule
     * @throws SchedulerException
     */
    public void delScheduleJob(SysSchedule schedule) throws SchedulerException {
        JobKey key = JobKey.jobKey(schedule.getJobId(), schedule.getGroupId());
        if (scheduler.checkExists(key)) scheduler.deleteJob(key);
        log.info("{}已卸载。", key);
    }

    /**
     * 卸载批量
     *
     */
    public void delScheduleJob(JobKey jobKey) throws SchedulerException {
        if (scheduler.checkExists(jobKey)) scheduler.deleteJob(jobKey);
        log.info("{}已卸载。", jobKey.toString());
    }

    /**
     * 刷新批量任务
     *
     * @param schedule
     */
    public void refreshSchedulerJob(SysSchedule schedule,String oldVersion) throws SchedulerException, BusinessException, ClassNotFoundException {
        JobKey jobkey = JobKey.jobKey(schedule.getJobId(), schedule.getGroupId());
        RLock lock = redissonClient.getLock(RedisLockConstant.RLOCK_HOME_SYS_SCHEDULE_UPD_SCHEDULE);
        try {
            if (lock.tryLock()) {
                SysSchedule newSchedule = getSchedule(jobkey);
                if (!Objects.equals(oldVersion,newSchedule.getVersion()))
                    throw new BusinessException(ErrorMsgConst.DATA_ERROR);
                if (scheduler.checkExists(jobkey)) scheduler.deleteJob(jobkey);
                JobDetail jobDetail = JobBuilder.newJob()
                        .withIdentity(jobkey)
                        .ofType((Class<? extends Job>) Class.forName(newSchedule.getClassName()))
                        .usingJobData("version", schedule.getVersion())
                        .storeDurably()
                        .build();
                CronTrigger trigger = TriggerBuilder.newTrigger()
                        .withSchedule(CronScheduleBuilder.cronSchedule(schedule.getCron()))
                        .withIdentity(TriggerKey.triggerKey(schedule.getJobId(), schedule.getGroupId()))
                        .forJob(jobDetail)
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
                if (Objects.equals(schedule.getStatus(), SysSchedule.StatusEnum.DISABLE.getCode()))
                    scheduler.pauseJob(jobkey);
                log.info("refreshSchedulerJob已重新加载:{}",jobkey);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 立即执行批量
     *
     * @param jobKey
     * @throws SchedulerException
     */
    public void exexImmediateSchedulerJob(JobKey jobKey) throws SchedulerException, BusinessException {
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyGroup());
        if(jobKeys.contains(jobKey)) scheduler.triggerJob(jobKey);
        else throw new BusinessException("批量任务不存在");
    }

    /**
     * 检查批量作业是否变化
     */
    public static boolean checkDataChange(SysSchedule schedule, SysSchedule cache) {
        if (!schedule.getCron().equals(cache.getCron())) return true;
        if (!Objects.equals(schedule.getStatus(), cache.getStatus())) return true;
        if(!Objects.equals(schedule.getHighFrequency(),cache.getHighFrequency())) return true;
        if(!Objects.equals(schedule.getVersion(),cache.getVersion())) return true;
        return false;
    }

    public String getScheduleVersion(JobKey jobKey) throws SchedulerException {
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        return StrUtil.toStr(jobDetail.getJobDataMap().get("version"));
    }

    public boolean checkExists(JobKey key) throws SchedulerException {
        return scheduler.checkExists(key);
    }

    public void deleteJob(JobKey key) throws SchedulerException {
        scheduler.deleteJob(key);
    }

    public <JobKey> Set<JobKey> getJobKeys(GroupMatcher tGroupMatcher) throws SchedulerException {
        return scheduler.getJobKeys(tGroupMatcher);
    }

}
