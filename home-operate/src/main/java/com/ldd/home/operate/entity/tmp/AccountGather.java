package com.ldd.home.operate.entity.tmp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 账户余额汇总
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountGather {
    /**
     * 可以是年度：2020、2021、2022
     * 可以是月度：1,2,3,4,5,6,7,8,9,10,11,12
     * 可以是日，1~31
     */
    private int date;
    //总余额
    private Double totalBalance;
    //当年总收入
    private Double currentYearIncome;
    //当年账面消费
    private Double currentYearConsumption;
    //当年净存款
    private Double currentYearNetDeposit;


}
