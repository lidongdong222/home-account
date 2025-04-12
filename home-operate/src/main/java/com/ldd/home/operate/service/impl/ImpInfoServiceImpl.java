package com.ldd.home.operate.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ldd.home.operate.entity.ImpInfo;
import com.ldd.home.operate.mapper.ImpInfoMapper;
import com.ldd.home.operate.service.IImpInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpInfoServiceImpl implements IImpInfoService {

    @Autowired
    ImpInfoMapper impInfoMapper;

    @Override
    public List<ImpInfo> getWaitingList() {
        return impInfoMapper.selectList(Wrappers.lambdaQuery(ImpInfo.class)
                .eq(ImpInfo::getStatus,ImpInfo.ImpStatusEnum.WAIT.getCode()));
    }

    /**
     * 获取导入信息
     */
    @Override
    public ImpInfo getImpInfo(String id) {
        return impInfoMapper.selectById(id);
    }
}
