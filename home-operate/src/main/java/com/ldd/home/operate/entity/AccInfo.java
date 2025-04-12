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
import lombok.ToString;

import java.math.BigDecimal;

/**
 * <p>
 * 科目流水信息表
 * </p>
 *
 * @author ldd
 * @since 2024-09-11
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName("acc_info")
public class AccInfo {

    /**
     * 主键id
     */
    @TableId(value = "acc_id", type = IdType.AUTO)
    private Long accId;

    /**
     * 科目编码
     */
    @TableField("sub_id")
    private String subId;

    @TableField("tmp_id")
    private Long impId;

    @TableField(exist = false)
    private String subType;

    @TableField(exist = false)
    private String subTypeName;

    @TableField(exist = false)
    private String subCode;

    @TableField(exist = false)
    private String subName;

    /**
     * 金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 所属期间
     */
    @TableField("acc_period")
    private String accPeriod;

    /**
     * 业务发生日期
     */
    @TableField("acc_date")
    private String accDate;

    /**
     * 摘要
     */
    @TableField("digest")
    private String digest;

    /**
     * 结算方式 1银行交易 2现金交易 3支付宝交易 4微信交易 5其他方式
     */
    @TableField("payment_type")
    private String paymentType;

    @TableField(exist = false)
    private String paymentTypeName;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private String createDate;

    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private String updateDate;

    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private String updateUser;

    /**
     * 状态 1正常 0软删除
     */
    @TableField("status")
    private String status;

    /**
     * 临时导入表id。临时导入表定时清理，该字段仅供参考
     */
    @TableField("tmp_id")
    private String tmpId;

    @TableField("wx_tran_order")
    private String wxTranOrder;

    @TableField(exist = false)
    private String createUserName;

    @TableField(exist = false)
    private String merchant;

    @TableField(exist = false)
    private String goods;

}
