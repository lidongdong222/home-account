package com.ldd.home.operate.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.home.operate.common.constant.ErrorMsgConst;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.IdUtil;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.config.quartz.SchedulerComponent;
import com.ldd.home.operate.entity.SysSchedule;
import com.ldd.home.operate.entity.SysScheduleHis;
import com.ldd.home.operate.entity.req.BaseReq;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.mapper.SysScheduleMapper;
import com.ldd.home.operate.service.ISysScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.ldd.home.operate.config.redis.RedisKeyConstant.SYS_SCHEDULER;
import static com.ldd.home.operate.config.redis.RedisLockConstant.COMMON_RLOCK_EXPIRE_TIME_SECONDS;
import static com.ldd.home.operate.config.redis.RedisLockConstant.COMMON_RLOCK_WAIT_TIME_SECONDS;
import static com.ldd.home.operate.config.redis.RedisLockConstant.RLOCK_HOME_SYS_SCHEDULE_ADD_UNIQUE_JOB_GROUP;
import static com.ldd.home.operate.config.redis.RedisLockConstant.RLOCK_HOME_SYS_SCHEDULE_UPDATE_RUNNING_STATUS;

/**
 * <p>
 * 批量定时作业表 服务实现类
 * </p>
 *
 * @author ldd
 * @since 2024-08-26
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysScheduleServiceImpl implements ISysScheduleService {

    @Autowired
    SysScheduleMapper sysScheduleMapper;

    @Autowired
    RedissonClient redissonClient;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SchedulerComponent schedulerComponent;

    /**
     * 加载有效批量作业
     * @return
     */
    @Override
    public List<SysSchedule> queryAllScheduler() {
        List list = sysScheduleMapper.selectList(
                Wrappers.lambdaQuery(SysSchedule.class)
                        .eq(SysSchedule::getStatus,1));
        return list;
    }

    /**
     * 添加执行历史
     * @param his
     */
    @Override
    public void addSysScheduleHis(SysScheduleHis his) {
//        int r = sysScheduleMapper.
    }

    @Override
    public Result getScheduleList(BaseReq req) {
        Page<SysSchedule> page =  sysScheduleMapper.selectPage(
                new Page<>(req.getPageNum(),req.getPageSize()),
                Wrappers.lambdaQuery(SysSchedule.class)
                .like(StrUtil.isNotEmpty(req.getMhcx()),SysSchedule::getJobId,req.getMhcx())
                .or().like(StrUtil.isNotEmpty(req.getMhcx()),SysSchedule::getGroupId,req.getMhcx())
                .or().like(StrUtil.isNotEmpty(req.getMhcx()),SysSchedule::getCron,req.getMhcx())
                .or().like(StrUtil.isNotEmpty(req.getMhcx()),SysSchedule::getRemark,req.getMhcx()));
        page.getRecords().forEach(s->{
            s.setStatusName(SysSchedule.StatusEnum.getName(s.getStatus()));
        });
        return Result.successPage("查询成功！", page.getTotal(), page.getRecords());
    }

    @Override
    public Result addSchedule(SysSchedule schedule) throws Exception {
        //默认版本为1，定时任务串行执行（A任务执行过程中，A任务不会被触发，只有执行完成才会被下次触发）
        schedule.setVersion("1");
        schedule.setExecStrategy(SysSchedule.ExecStrategyEnum.SERIAL.getCode());
        RLock lock = redissonClient.getLock(RLOCK_HOME_SYS_SCHEDULE_ADD_UNIQUE_JOB_GROUP);
        try {
            if(lock.tryLock()){
                List check = sysScheduleMapper.selectList(Wrappers.lambdaQuery(SysSchedule.class)
                        .eq(SysSchedule::getJobId,schedule.getJobId())
                        .eq(SysSchedule::getGroupId,schedule.getGroupId()));
                if(check.size()>0) throw new BusinessException("作业主键"+schedule.getJobId()+"."+schedule.getGroupId()+"已存在，请检查！");
                int r = sysScheduleMapper.insert(schedule);
                if(r!=1) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
            }
            if(Objects.equals(schedule.getStatus(),SysSchedule.StatusEnum.ENABLE.getCode())){
                schedulerComponent.schedulerJob(schedule);
            }
        }catch (Exception e){
            throw e;
        }finally {
            if(lock.isLocked())lock.unlock();
        }
        return Result.success("新增成功！");
    }

    @Override
    public Result updSchedule(SysSchedule s) throws BusinessException, SchedulerException, ClassNotFoundException {
        SysSchedule schedule = sysScheduleMapper.selectOne(Wrappers.lambdaQuery(SysSchedule.class)
                .eq(SysSchedule::getScheId,s.getScheId())
                .eq(SysSchedule::getVersion,s.getVersion()));
        if(Objects.isNull(schedule)) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        schedule.setCron(s.getCron());
        schedule.setStatus(s.getStatus());
        schedule.setRemark(s.getRemark());
        schedule.setVersion(IdUtil.getUUid());
        int r = sysScheduleMapper.update(schedule,Wrappers.lambdaQuery(SysSchedule.class)
                .eq(SysSchedule::getScheId,s.getScheId())
                .eq(SysSchedule::getVersion,s.getVersion()));
        if(r!=1) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        schedulerComponent.refreshSchedulerJob(schedule,s.getVersion());
        return Result.success("更新成功！");
    }

    @Override
    public Result delSchedule(IdReq req) throws BusinessException, SchedulerException {
        SysSchedule schedule = sysScheduleMapper.selectById(req.getId());
        int r = sysScheduleMapper.deleteById(req.getId());
        if(r!=1) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        schedulerComponent.delScheduleJob(schedule);
        return Result.success("删除成功！");
    }

    /**
     * 立即执行批量
     * @param req
     * @return
     */
    @Override
    public Result execImmediateSchedule(IdReq req) throws SchedulerException, BusinessException, InterruptedException {
        SysSchedule s = sysScheduleMapper.selectById(req.getId());
        JobKey jobKey = JobKey.jobKey(s.getJobId(),s.getGroupId());
        RLock lock = redissonClient.getLock(RLOCK_HOME_SYS_SCHEDULE_UPDATE_RUNNING_STATUS+":"+jobKey);
        try {
            //锁-更新当前作业在执行中
            if (lock.tryLock(COMMON_RLOCK_WAIT_TIME_SECONDS,
                    COMMON_RLOCK_EXPIRE_TIME_SECONDS,
                    TimeUnit.SECONDS)) {

                SysSchedule cache = (SysSchedule) redisTemplate.opsForValue().get(SYS_SCHEDULER+":"+jobKey);
                if(cache!=null && Objects.equals(cache.getRunningStatus(),SysSchedule.RunningStatusEnum.RUNNING.getCode())) throw new BusinessException("批量正在执行中！");
                schedulerComponent.exexImmediateSchedulerJob(jobKey);
            }
        } catch (Exception e) {
            log.error(".execImmediateSchedule执行异常：{}",e.getMessage());
            throw e;
        } finally {
            if (lock.isLocked()) lock.unlock();
        }
        return Result.success("执行成功！");
    }
}
