package com.ldd.home.operate.schedule;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


@Slf4j
//@DisallowConcurrentExecution
public class TestTask implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Thread.sleep(1000*30);
            log.info("--------------------测试--------------------");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
