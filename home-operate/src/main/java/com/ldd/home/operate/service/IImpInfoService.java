package com.ldd.home.operate.service;

import com.ldd.home.operate.entity.ImpInfo;

import java.util.List;

public interface IImpInfoService {
    List<ImpInfo> getWaitingList();

    /**
     * 获取导入信息
     */
    ImpInfo getImpInfo(String id);
}
