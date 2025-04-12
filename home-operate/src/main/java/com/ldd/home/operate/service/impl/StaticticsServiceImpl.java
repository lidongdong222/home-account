package com.ldd.home.operate.service.impl;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.entity.AccBalance;
import com.ldd.home.operate.entity.PrivateAccInfo;
import com.ldd.home.operate.entity.req.YearMonthPeriodReq;
import com.ldd.home.operate.entity.req.YearMonthReq;
import com.ldd.home.operate.entity.req.YearReq;
import com.ldd.home.operate.service.IAccBalanceService;
import com.ldd.home.operate.service.IPrivateAccInfoService;
import com.ldd.home.operate.service.IStaticticsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class StaticticsServiceImpl implements IStaticticsService {

    @Autowired
    IPrivateAccInfoService accInfoService;

    @Autowired
    IAccBalanceService balanceService;

    /**
     * 月度消费情况图标
     * @return
     */
    @Override
    public Result getMonthlyConsumption(YearReq req) {
        List<String> months = Arrays.asList(req.getYear()+"01"
                ,req.getYear()+"02"
                ,req.getYear()+"03"
                ,req.getYear()+"04"
                ,req.getYear()+"05"
                ,req.getYear()+"06"
                ,req.getYear()+"07"
                ,req.getYear()+"08"
                ,req.getYear()+"09"
                ,req.getYear()+"10"
                ,req.getYear()+"11"
                ,req.getYear()+"12");
        List<String> amountList = new ArrayList<>();
        List<PrivateAccInfo> PrivateAccInfo = accInfoService.getStatisticsByMonths(months);
        for (int i=0;i<months.size();i++) {
            amountList.add(i,"0.00");
            for (PrivateAccInfo info : PrivateAccInfo) {
                if(Objects.equals(StrUtil.toInt(info.getAccYear())*100+StrUtil.toInt(info.getAccMonth()),StrUtil.toInt(months.get(i)))){
                    amountList.add(i,new BigDecimal(info.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString());
                }
            }
        }
        return Result.successData("查询成功",
                new MonthlyConsumptionVo(months,amountList));
    }

    /**
     * 月度消费情况占比
     */
    @Override
    public Result getMonthConsumptionSituation(YearMonthReq req) {
        List<PrivateAccInfo> accInfos = accInfoService.getExpenditureByYearMonth(
                StrUtil.toInt(req.getYearMonth().substring(0,4)),
                StrUtil.toInt(req.getYearMonth().substring(4,6)));
        List<MonthConsumptionSituationVo> vos = new ArrayList<>();
        for (PrivateAccInfo info : accInfos) {
            vos.add(new MonthConsumptionSituationVo(
                    new BigDecimal(info.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString(),
                    info.getSubName()));
        }
        if(vos.size()<1){
            vos.add(new MonthConsumptionSituationVo("0.00","暂无消费"));
        }
        return Result.successData("查询成功",vos);
    }

    /**
     * 获取累计余额情况
     */
    @Override
    public Result getAccumulatedBalanceSituation(YearMonthPeriodReq req) {
        List<String> yearMonthList = generateYearMonthList(req.getBeginYearMonth(),req.getEndYearMonth());
        List<AccBalance> balances = new ArrayList<>();
        return Result.successData("查询成功",new BalanceSituationVo(yearMonthList,
                balances.stream().map(b->{
                    return b.getBalance().setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString();
                }).toList()));
    }


    private List<String> generateYearMonthList(String beginYearMonth, String endYearMonth) {
        List<String> yearMonthList = new ArrayList<>();
        int beginYear = StrUtil.toInt(beginYearMonth.substring(0,4)),
                beginMonth =StrUtil.toInt(beginYearMonth.substring(4,6)),
                endYear = StrUtil.toInt(endYearMonth.substring(0,4)),
                endMonth = StrUtil.toInt(endYearMonth.substring(4,6));
        while(beginYear<endYear){
            while(beginMonth<=12){
                yearMonthList.add(beginYear*100+beginMonth+"");
                beginMonth++;
            }
            beginYear++;
            beginMonth=1;
        }
        if(Objects.equals(beginYear,endYear)){
            while(beginMonth<=endMonth){
                yearMonthList.add(beginYear*100+beginMonth+"");
                beginMonth++;
            }
        }
        return yearMonthList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class MonthlyConsumptionVo{
        List<String> yearMonthList;
        List<String> amountList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class MonthConsumptionSituationVo{
        String value;
        String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class BalanceSituationVo{
        List<String> yearMonthList;
        List<String> balanceList;
    }
}
