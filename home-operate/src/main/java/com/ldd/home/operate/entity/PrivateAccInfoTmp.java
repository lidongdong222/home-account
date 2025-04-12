package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * <p>
 * 科目流水信息临时导入表
 * </p>
 *
 * @author ldd
 * @since 2024-08-06
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("private_acc_info_tmp")
public class PrivateAccInfoTmp {

    /**
     * 主键id
     */
    @TableId("tmp_id")
    private String tmpId;

    /**
     * 导入id
     */
    @TableField("imp_id")
    private Long impId;

    /**
     * 资源id
     */
    @TableField("res_id")
    private Long resId;

    /**
     * 收支类型
     */
    @TableField("account_type")
    private String accountType;

    /**
     * 科目编码
     */
    @TableField("sub_code")
    private String subCode;

    /**
     * 分类
     */
    @TableField("sub_name")
    private String subName;

    /**
     * 金额
     */
    @TableField("amount")
    private String amount;

    /**
     * 年度
     */
    @TableField("acc_year")
    private String accYear;

    /**
     * 月份
     */
    @TableField("acc_month")
    private String accMonth;

    /**
     * 账务时间
     */
    @TableField("acc_date")
    private String accDate;

    /**
     * 摘要
     */
    @TableField("digest")
    private String digest;

    /**
     * 流转说明
     */
    @TableField("exchange_desc")
    private String exchangeDesc;

    /**
     * 来源说明
     */
    @TableField("source_desc")
    private String sourceDesc;

    /**
     * 导入时间
     */
    @TableField("import_date")
    private Date importDate;

    /**
     * 导入用户
     */
    @TableField("import_user")
    private Long importUser;

    /**
     * 处理状态 0未处理 1处理成功 2处理失败
     */
    @TableField("deal_status")
    private Integer dealStatus;

    /**
     * 处理失败原因
     */
    @TableField("deal_msg")
    private String dealMsg;
}
