package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 科目规则匹配表
 * </p>
 *
 * @author ldd
 * @since 2025-04-23
 */
@Getter
@Setter
@TableName("wx_bill_match_subject_rule")
public class WxBillMatchSubjectRule {

    /**
     * 主键
     */
    @TableId(value = "wbmsr_id", type = IdType.NONE)
    private String wbmsrId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 收支类型
     */
    @TableField("sub_type")
    private String subType;

    /**
     * 微信业务数据项
     */
    @TableField("wx_data_item")
    private String wxDataItem;

    /**
     * 匹配类型1包含 2等于
     */
    @TableField("match_type")
    private String matchType;

    /**
     * 匹配内容
     */
    @TableField("match_content")
    private String matchContent;

    /**
     * 匹配科目
     */
    @TableField("match_sub_id")
    private Long matchSubId;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private String createDate;

    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private String updateDate;

    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private String updateUser;
}
