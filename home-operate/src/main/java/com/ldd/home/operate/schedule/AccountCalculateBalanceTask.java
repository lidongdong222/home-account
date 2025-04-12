package com.ldd.home.operate.schedule;

import com.ldd.home.operate.common.utils.SpringContextUtil;
import com.ldd.home.operate.entity.AccBalance;
import com.ldd.home.operate.service.IAccBalanceService;
import com.ldd.home.operate.service.IAccInfoService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@DisallowConcurrentExecution
@Slf4j
public class AccountCalculateBalanceTask implements Job {

    int periodSpan = 10;

    IAccInfoService accInfoService = SpringContextUtil.getBean(IAccInfoService.class);
    IAccBalanceService accBalanceService = SpringContextUtil.getBean(IAccBalanceService.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("余额批量作业开始！");
        //计算10年内的累计余额
        Calendar c = Calendar.getInstance();
        int endYear = c.get(Calendar.YEAR);
        int startYear = endYear-9;
        for(int i=startYear;i<=endYear;i++){
            List<AccBalance> yearAccBalanceList = new ArrayList<>();
            List<String> monthPeriodList = getMonthPeriodList(i);
            AccBalance lastYearAccBalance = accBalanceService.getBalanceByMonthPeriod((i-1)*100+12+"");
            BigDecimal lastYearEndBalance = lastYearAccBalance==null?BigDecimal.ZERO:lastYearAccBalance.getEndBalance();
            List<AccBalance> accSummary = accInfoService.getAccMonthSummaryInfo(i+"");
            for (String p : monthPeriodList) {
                AccBalance nextAccBalance = null;
                boolean flag = true;
                for (AccBalance a : accSummary) {
                    if(Objects.equals(p,a.getPeriod())){
                        flag=false;
                        nextAccBalance=a;
                        break;
                    }
                }
                if(flag){
                    nextAccBalance = AccBalance.builder()
                            .period(p).periodType(AccBalance.PeriodTypeEnum.MONTH.getCode())
                            .income(BigDecimal.ZERO).expenditure(BigDecimal.ZERO).balance(BigDecimal.ZERO)
                            .beginBalance(BigDecimal.ZERO).endBalance(BigDecimal.ZERO)
                            .build();
                }
                //年末余额转月初余额
                nextAccBalance.setBeginBalance(lastYearEndBalance.add(BigDecimal.ZERO));
                nextAccBalance.setEndBalance(lastYearEndBalance.add(nextAccBalance.getBalance()));
                lastYearEndBalance = nextAccBalance.getEndBalance();
                yearAccBalanceList.add(nextAccBalance);
            }
            accBalanceService.deleteByYear(i+"");
            accBalanceService.addList(yearAccBalanceList);
        }


        log.info("余额批量作业结束！");
    }

    private List<String> getMonthPeriodList(int year) {
        List<String> periods = new ArrayList<>();
        for(int i=1;i<=12;i++){
            periods.add(year*100+i+"");
        }
        return periods;
    }

}
