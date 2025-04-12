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

import java.util.Date;

/**
 * <p>
 * 资源业务关联信息表
 * </p>
 *
 * @author ldd
 * @since 2024-04-30
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("resource_busi")
public class ResourceBusi {

    /**
     * 主键
     */
    @TableId(value = "res_id", type = IdType.AUTO)
    private Long resId;

    /**
     * 来源业务id
     */
    @TableField("busi_id")
    private String busiId;

    /**
     * 来源业务表
     */
    @TableField("busi_table")
    private String busiTable;

    /**
     * 0待关联，1已关联 当客户优先长传文件时为待关联，业务完成后，变为已关联。待关联文件会定时清理详见批量
     */
    @TableField("status")
    private String status;

    /**
     * 创建日期
     */
    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 创建用户
     */
    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 更新日期
     */
    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private Date updateDate;

    /**
     * 更新用户
     */
    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private String updateUser;
}
