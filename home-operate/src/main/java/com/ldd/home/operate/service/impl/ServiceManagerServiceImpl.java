package com.ldd.home.operate.service.impl;

import com.ldd.home.operate.common.entity.FtpInfoEntity;
import com.ldd.home.operate.service.IServiceManagerService;
import org.springframework.stereotype.Service;

/**
 * 服务器资源管理服务
 */
@Service
public class ServiceManagerServiceImpl implements IServiceManagerService {

    @Override
    public FtpInfoEntity queryFtpInfo(boolean needNet) {
        return new FtpInfoEntity();
    }
}
