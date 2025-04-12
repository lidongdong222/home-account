package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @author ldd
 * @since 2024-09-09
 */
@Getter
@Setter
@TableName("sys_role_permisson")
public class SysRolePermisson {

    /**
     * 主键
     */
    @TableId(value = "srp_id", type = IdType.AUTO)
    private Long srpId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 权限id
     */
    @TableField("perm_id")
    private Long permId;
}
