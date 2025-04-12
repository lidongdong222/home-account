package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ldd.home.operate.common.ext.Add;
import com.ldd.home.operate.common.ext.Upd;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色信息表
 * </p>
 *
 * @author ldd
 * @since 2024-09-09
 */
@Getter
@Setter
@TableName("sys_role")
public class SysRole {

    /**
     * 主键
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色id
     */
    @TableField("role_code")
    @Size(groups = {Add.class},min=3,max =30,message = "角色编码长度建议设置3~30个字符" )
    private String roleCode;

    /**
     * 角色id
     */
    @TableField("role_name")
    @NotBlank(groups = {Upd.class,Add.class},message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色描述
     */
    @TableField("role_desc")
    private String roleDesc;

    @TableField("status")
    private String status;

    @TableField(exist = false)
    private String statusDesc;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private String createDate;

    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private String updateDate;

    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private String updateUser;

}
