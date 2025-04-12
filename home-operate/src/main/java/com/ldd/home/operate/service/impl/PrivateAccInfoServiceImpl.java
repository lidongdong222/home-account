package com.ldd.home.operate.service.impl;

import com.ldd.home.operate.entity.PrivateAccInfo;
import com.ldd.home.operate.mapper.PrivateAccInfoMapper;
import com.ldd.home.operate.service.IPrivateAccInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PrivateAccInfoServiceImpl implements IPrivateAccInfoService {

    @Autowired
    PrivateAccInfoMapper accInfoMapper;

    /**
     * 查询月份期间内的消费情况
     */
    @Override
    public List<PrivateAccInfo> getStatisticsByMonths(List months) {
        return accInfoMapper.getStatisticsByMonths(months);
    }

    /**
     * 查询月度消费情况占比
     */
    @Override
    public List<PrivateAccInfo> getExpenditureByYearMonth(Integer year, Integer month) {
        return accInfoMapper.getExpenditureByYearMonth(year,month);
    }
}
