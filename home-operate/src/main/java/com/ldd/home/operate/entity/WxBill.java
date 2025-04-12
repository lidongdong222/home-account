package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 微信账单表
 * </p>
 *
 * @author ldd
 * @since 2024-10-31
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@TableName("wx_bill")
public class WxBill {

    /**
     * 主键
     */
    @TableId("wb_id")
    private Long wbId;

    /**
     * 导入id
     */
    @TableField("imp_id")
    private Long impId;

    /**
     * 交易时间
     */
    @TableField("tran_date")
    private String tranDate;

    /**
     * 交易类型
     */
    @TableField("tran_type")
    private String tranType;

    /**
     * 交易商户
     */
    @TableField("merchant")
    private String merchant;

    /**
     * 交易商品
     */
    @TableField("goods")
    private String goods;

    /**
     * 收支类型
     */
    @TableField("acc_type")
    private String accType;

    /**
     * 金额(元)
     */
    @TableField("amount")
    private String amount;

    /**
     * 支付方式
     */
    @TableField("pay_type")
    private String payType;

    /**
     * 当前状态
     */
    @TableField("wx_status")
    private String wxStatus;

    /**
     * 交易单号
     */
    @TableField("tran_order")
    private String tranOrder;

    /**
     * 商户单号
     */
    @TableField("merchant_order")
    private String merchantOrder;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @TableField("create_date")
    private LocalDateTime createDate;

    @TableField("create_user")
    private String createUser;

    @TableField("update_date")
    private LocalDateTime updateDate;

    @TableField("update_user")
    private String updateUser;

    @TableField(exist = false)
    private String subId;

    @TableField(exist = false)
    private String digest;
}
