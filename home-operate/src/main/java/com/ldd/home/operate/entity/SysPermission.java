package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 权限信息表
 * </p>
 *
 * @author ldd
 * @since 2024-09-09
 */
@Getter
@Setter
@TableName("sys_permission")
public class SysPermission {

    /**
     * 主键
     */
    @TableId(value = "perm_id", type = IdType.AUTO)
    private Long permId;

    /**
     * 权限编码
     */
    @TableField("perm_code")
    private Integer permCode;

    /**
     * 1.菜单权限 2功能权限
     */
    @TableField("perm_type")
    private Byte permType;

    /**
     * 权限接口url
     */
    @TableField("perm_url")
    private String permUrl;

    /**
     * 权限描述
     */
    @TableField("perm_desc")
    private String permDesc;
}
