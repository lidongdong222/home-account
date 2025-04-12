package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * 报表定义表
 * </p>
 *
 * @author ldd
 * @since 2024-04-16
 */
@Getter
@Setter
@Builder
@TableName("rep_info")
public class RepInfo {

    /**
     * 主键
     */
    @TableId(value = "rep_id", type = IdType.AUTO)
    private Integer repId;

    /**
     * 报表名称
     */
    @TableField("rep_name")
    private String repName;

    /**
     * 描述
     */
    @TableField("remark")
    private String remark;

    /**
     * 处理bean
     */
    @TableField("remark")
    private String dealBean;

    /**
     * 描述
     */
    @TableField("remark")
    private String dealMethod;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private Date updateDate;

    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private String updateUser;
}
