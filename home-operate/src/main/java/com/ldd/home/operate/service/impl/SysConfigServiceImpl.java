package com.ldd.home.operate.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.home.operate.common.constant.ErrorMsgConst;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.config.redis.RedisKeyConstant;
import com.ldd.home.operate.entity.SysConfig;
import com.ldd.home.operate.entity.req.BaseReq;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.mapper.SysConfigMapper;
import com.ldd.home.operate.service.ISysConfigService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.ldd.home.operate.config.redis.RedisLockConstant.COMMON_RLOCK_EXPIRE_TIME_SECONDS;
import static com.ldd.home.operate.config.redis.RedisLockConstant.COMMON_RLOCK_WAIT_TIME_SECONDS;

/**
 * <p>
 * 系统配置信息表 服务实现类
 * </p>
 *
 * @author ldd
 * @since 2024-09-05
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {

    @Autowired
    SysConfigMapper configMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Override
    public Result getSysConfigList(BaseReq req) {
        Page<SysConfig> page = configMapper.selectPage(
                new Page<>(req.getPageNum(),req.getPageSize()),
                Wrappers.lambdaQuery(SysConfig.class)
                        .like(Objects.nonNull(req.getMhcx()),SysConfig::getConfigKey,req.getMhcx())
                        .or(Objects.nonNull(req.getMhcx()),l->l.like(SysConfig::getConfigValue,req.getMhcx())));
        return Result.successPage("查询成功",page.getTotal(),page.getRecords());
    }

    /**
     * 获取系统配置
     */
    @Override
    public String getConfigValue(String key) {
        Object obj = redisTemplate.opsForValue().get(key);
        if(Objects.isNull(obj)){
            SysConfig config = configMapper.selectOne(
                    Wrappers.lambdaQuery(SysConfig.class).eq(SysConfig::getConfigKey,key));
            redisTemplate.opsForValue().setIfAbsent(config.getConfigKey(),config.getConfigValue(),Duration.ofMinutes(RedisKeyConstant.COMMON_EXPIRE_TIME_SECONDS));
            return config.getConfigValue();
        }
        return StrUtil.toStr(obj);
    }


    @Override
    public Result addSysConfig(SysConfig config) throws BusinessException, InterruptedException {
        RLock lock = redissonClient.getLock(RedisKeyConstant.SYS_CONFIG);
        try{
            if (lock.tryLock(COMMON_RLOCK_WAIT_TIME_SECONDS,
                    COMMON_RLOCK_EXPIRE_TIME_SECONDS,
                    TimeUnit.SECONDS)) {
                SysConfig check = configMapper.selectOne(
                        Wrappers.lambdaQuery(SysConfig.class).eq(SysConfig::getConfigKey,config.getConfigKey()));
                if(Objects.nonNull(check)) throw new BusinessException(check.getConfigKey()+"已存在，请勿重新新增！");
                int r = configMapper.insert(config);
                if(!Objects.equals(r,1)) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
                redisTemplate.opsForValue().set(config.getConfigKey(),config.getConfigValue(), Duration.ofMinutes(RedisKeyConstant.COMMON_EXPIRE_TIME_SECONDS));
            }
        }catch (Exception e){
            throw  e;
        }finally {
            lock.unlock();
        }
        return Result.success("新增成功！");
    }

    @Override
    public Result updSysConfig(SysConfig config) throws BusinessException, InterruptedException {
        RLock lock = redissonClient.getLock(RedisKeyConstant.SYS_CONFIG);
        try{
            if (lock.tryLock(COMMON_RLOCK_WAIT_TIME_SECONDS,
                    COMMON_RLOCK_EXPIRE_TIME_SECONDS,
                    TimeUnit.SECONDS)) {
                int r = configMapper.updateById(SysConfig.builder()
                        .configId(config.getConfigId())
                        .configDesc(config.getConfigDesc())
                        .configValue(config.getConfigValue()).build());
                if(!Objects.equals(r,1)) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
                redisTemplate.opsForValue().set(config.getConfigKey(),config.getConfigValue(), Duration.ofMinutes(RedisKeyConstant.COMMON_EXPIRE_TIME_SECONDS));
            }
        }catch (Exception e){
            throw  e;
        }finally {
            lock.unlock();
        }
        return Result.success("修改成功！");
    }

    @Override
    public Result delSysConfig(IdReq req) throws BusinessException {
        SysConfig config = configMapper.selectById(req.getId());
        int r = configMapper.deleteById(req.getId());
        if(Objects.isNull(config) || !Objects.equals(r,1)) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        redisTemplate.delete(config.getConfigKey());
        return Result.success("修改成功！");
    }
}
