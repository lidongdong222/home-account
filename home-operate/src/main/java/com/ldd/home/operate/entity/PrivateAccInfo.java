package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ldd.home.operate.common.ext.Add;
import com.ldd.home.operate.common.ext.Upd;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * 科目流水信息表
 * </p>
 *
 * @author ldd
 * @since 2024-03-21
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("private_acc_info")
public class PrivateAccInfo {

    /**
     * 主键id
     */
    @NotNull(groups = {Upd.class})
    @TableId(value = "acc_id", type = IdType.AUTO)
    private Long accId;

    /**
     * 科目编码
     */
    @NotBlank(groups = {Add.class})
    @Size(max= 50,groups = {Upd.class, Add.class})
    @TableField("sub_code" )
    private String subCode;

    /**
     * 金额
     */
    @NotNull(groups = {Add.class,Upd.class})
    @TableField("amount")
    private Double amount;

    @TableField("acc_year")
    private int accYear;

    @TableField("acc_month")
    private int accMonth;

    /**
     * 登记时间
     */
    @NotNull(groups = {Add.class,Upd.class})
    @TableField("acc_date")
    private String accDate;

    /**
     * 摘要
     */
    @Size(max= 100,groups = {Upd.class, Add.class})
    @TableField("digest")
    private String digest;

    /**
     * 流转说明
     */
    @Size(max= 100,groups = {Upd.class, Add.class})
    @TableField("exchange_desc")
    private String exchangeDesc;

    /**
     * 来源说明
     */
    @Size(max= 100,groups = {Upd.class, Add.class})
    @TableField("source_desc")
    private String sourceDesc;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private Date updateDate;

    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private String updateUser;

    /**
     * 状态 1正常 0软删除
     */
    @TableField("status")
    private String status;

    @TableField(exist = false)
    private String subName;
    @TableField(exist = false)
    private String accountType;

    ;

    public enum AccountType{

        SR("SR","收入"),
        ZC("ZC","支出"),
        YHCK("YHCK","银行存款");


        String key;
        String value;

        AccountType(String key, String value) {
            this.key=key;
            this.value=value;
        }

        public static String getValue(String key) {
            for (AccountType t : AccountType.values()) {
                if(t.key.equalsIgnoreCase(key)) return t.value;
            }
            return "其他";
        }
    }
}
