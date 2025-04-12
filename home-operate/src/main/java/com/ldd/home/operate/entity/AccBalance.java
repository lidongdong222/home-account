package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * <p>
 * 账务余额表
 * </p>
 *
 * @author ldd
 * @since 2024-08-20
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("acc_balance")
public class AccBalance {

    /**
     * 主键
     */
    @TableId(value = "ab_id", type = IdType.AUTO)
    private Long abId;

    /**
     * 期间类型 1年度 2季度 3月度 
     */
    @TableField("period_type")
    private String periodType;

    /**
     * 期间
     */
    @TableField("period")
    private String period;

    /**
     * 收入
     */
    @TableField("income")
    private BigDecimal income;

    /**
     * 支出
     */
    @TableField("expenditure")
    private BigDecimal expenditure;

    /**
     * 余额
     */
    @TableField("balance")
    private BigDecimal balance;

    @TableField("begin_balance")
    private BigDecimal beginBalance;

    @TableField("end_balance")
    private BigDecimal endBalance;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private String createDate;

    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "update_date",fill = FieldFill.INSERT)
    private String updateDate;

    @TableField(value = "update_user",fill = FieldFill.INSERT)
    private String updateUser;

    public enum PeriodTypeEnum{
        YEAR("1"),
        QUARTER("2"),
        MONTH("3");

        String code;

        PeriodTypeEnum(String code) {
            this.code = code;
        }

        public String getCode(){
            return this.code;
        }
    }
}
