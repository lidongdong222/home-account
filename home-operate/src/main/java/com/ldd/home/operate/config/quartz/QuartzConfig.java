package com.ldd.home.operate.config.quartz;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.EverythingMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Configuration
@Slf4j
public class QuartzConfig {

    @Value("${org.quartz.scheduler.instanceName}")
    String instanceName;

    @Value("${org.quartz.threadPool.class}")
    String threadPoolClass;

    @Value("${org.quartz.threadPool.threadCount}")
    String threadCount;

    @Value("${org.quartz.threadPool.threadPriority}")
    String threadPriority;

    @Value("${org,quartz.jobStore.class}")
    String jobStoreClass;

    @Autowired
    SysScheduleMapper scheduleMapper;

    @Autowired
    RedisTemplate redisTemplate;

    public SchedulerFactoryBean schedulerFactoryBean() {
        try {
            Properties properties = new Properties();
            properties.setProperty("org.quartz.scheduler.instanceName", instanceName);
            properties.setProperty("org.quartz.threadPool.class", threadPoolClass);
            properties.setProperty("org.quartz.threadPool.threadCount", threadCount);
            properties.setProperty("org.quartz.threadPool.threadPriority", threadPriority);
            properties.setProperty("org,quartz.jobStore.class", jobStoreClass);
            SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
            factoryBean.setQuartzProperties(properties);
            factoryBean.setAutoStartup(true);
            factoryBean.setStartupDelay(5);
            factoryBean.afterPropertiesSet();
            return factoryBean;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("{}.schedulerFactoryBean创建失败:{}", getClass().getName(), e.getMessage());
        }
        return null;
    }

    @Bean(name = "homeScheduler")
    public Scheduler scheduler() {
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        try {
//            监听器
            scheduler.getListenerManager().addTriggerListener(
                    new QuartzTriggerListener(scheduler,scheduleMapper),
                    EverythingMatcher.allTriggers()
            );
//          加载定时
            List<SysSchedule> schedules = scheduleMapper.selectList(Wrappers.query());
            log.info("{}加载定时任务：{}", getClass().getName(), JSONObject.toJSONString(schedules));
            for (SysSchedule schedule : schedules) {
                JobKey jobKey = new JobKey(schedule.getJobId(), schedule.getGroupId());
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
            }
            scheduler.startDelayed(5);
        } catch (Exception e) {
            log.error("{}.scheduler创建失败:{}", getClass().getName(), e.getMessage());
        }
        return scheduler;
    }

}
