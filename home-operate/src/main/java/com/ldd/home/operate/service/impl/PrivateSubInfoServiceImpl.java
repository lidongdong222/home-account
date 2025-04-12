package com.ldd.home.operate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ldd.home.operate.entity.PrivateSubInfo;
import com.ldd.home.operate.mapper.PrivateSubInfoMapper;
import com.ldd.home.operate.service.IPrivateSubInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PrivateSubInfoServiceImpl implements IPrivateSubInfoService {

    @Autowired
    PrivateSubInfoMapper subInfoMapper;

    @Override
    public List<PrivateSubInfo> getSubInfoList(){
        return subInfoMapper.selectList(new LambdaQueryWrapper<PrivateSubInfo>()
                .select(PrivateSubInfo::getSubCode,PrivateSubInfo::getSubName)
                .eq(PrivateSubInfo::getStatus,"1"));
    }
}
