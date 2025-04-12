package com.ldd.home.operate.service;

import com.ldd.home.operate.entity.PrivateAccInfo;

import java.util.List;

public interface IPrivateAccInfoService {
    /**
     * 查询月份期间内的消费情况
     */
    List<PrivateAccInfo> getStatisticsByMonths(List months);

    /**
     * 查询月度消费情况占比
     */
    List<PrivateAccInfo> getExpenditureByYearMonth(Integer substring, Integer substring1);
}
