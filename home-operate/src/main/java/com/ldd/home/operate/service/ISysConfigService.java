package com.ldd.home.operate.service;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.entity.SysConfig;
import com.ldd.home.operate.entity.req.BaseReq;
import com.ldd.home.operate.entity.req.IdReq;

/**
 * 系统配置
 * @author ldd
 * @since 2024-09-05
 */
public interface ISysConfigService {

    Result getSysConfigList(BaseReq req);

    String getConfigValue(String key);

    Result addSysConfig(SysConfig config) throws BusinessException, InterruptedException;

    Result updSysConfig(SysConfig config) throws BusinessException, InterruptedException;

    Result delSysConfig(IdReq req) throws BusinessException;
}
