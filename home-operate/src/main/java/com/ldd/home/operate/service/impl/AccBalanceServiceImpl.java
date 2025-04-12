package com.ldd.home.operate.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ldd.home.operate.entity.AccBalance;
import com.ldd.home.operate.mapper.AccBalanceMapper;
import com.ldd.home.operate.service.IAccBalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 账务余额表 服务实现类
 * </p>
 *
 * @author ldd
 * @since 2024-08-20
 */
@Service
@Slf4j
public class AccBalanceServiceImpl implements IAccBalanceService {

    @Autowired
    AccBalanceMapper accBalanceMapper;

    @Override
    public void addList(List<AccBalance> accInfoList) {
        accBalanceMapper.insertBatch(accInfoList);
    }

    @Override
    public void deleteByPeriodType(String code) {
        accBalanceMapper.deleteByPeriodType(code);
    }

    @Override
    public AccBalance getBalanceByMonthPeriod(String period) {
        return accBalanceMapper.selectOne(Wrappers.lambdaQuery(AccBalance.class)
                .eq(AccBalance::getPeriodType, AccBalance.PeriodTypeEnum.MONTH.getCode())
                .eq(AccBalance::getPeriod,period));
    }

    @Override
    public void deleteByYear(String year) {
        accBalanceMapper.delete(Wrappers.lambdaQuery(AccBalance.class)
                .eq(AccBalance::getPeriodType,AccBalance.PeriodTypeEnum.MONTH.getCode())
                .likeRight(AccBalance::getPeriod,year));
    }
}
