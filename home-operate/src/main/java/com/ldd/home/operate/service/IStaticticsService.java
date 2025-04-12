package com.ldd.home.operate.service;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.entity.req.YearMonthPeriodReq;
import com.ldd.home.operate.entity.req.YearMonthReq;
import com.ldd.home.operate.entity.req.YearReq;

public interface IStaticticsService {
    Result getMonthlyConsumption(YearReq year);

    Result getMonthConsumptionSituation(YearMonthReq req);

    Result getAccumulatedBalanceSituation(YearMonthPeriodReq req);
}
