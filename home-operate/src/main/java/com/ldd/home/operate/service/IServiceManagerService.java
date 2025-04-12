package com.ldd.home.operate.service;

import com.ldd.home.operate.common.entity.FtpInfoEntity;

public interface IServiceManagerService {
    FtpInfoEntity queryFtpInfo(boolean needNet);
}
