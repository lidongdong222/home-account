package com.ldd.home.operate.service;

import com.ldd.home.operate.entity.AccBalance;

import java.util.List;

/**
 * <p>
 * 账务余额表 服务类
 * </p>
 *
 * @author ldd
 * @since 2024-08-20
 */
public interface IAccBalanceService {

    void addList(List<AccBalance> accInfoList);

    void deleteByPeriodType(String code);

    AccBalance getBalanceByMonthPeriod(String s);

    void deleteByYear(String s);
}
