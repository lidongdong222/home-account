package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ldd.home.operate.common.ext.Add;
import com.ldd.home.operate.common.ext.Login;
import com.ldd.home.operate.common.ext.Upd;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author ldd
 * @since 2024-09-09
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class SysUser implements UserDetails, Serializable {

    /**
     * 主键
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 账号
     */
    @TableField("usercode")
    @NotBlank(groups = {Login.class, Add.class},message = "usercode不能为空！" )
    @Size(groups={Add.class},min = 3,max=50,message = "用户账号长度支持3~30个字符")
    private String usercode;

    /**
     * 用户名
     */
    @NotBlank(groups = {Upd.class,Add.class},message = "username不能为空！")
    @TableField("username")
    private String username;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    @TableField("salt")
    private String salt;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private String createDate;

    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private String updateDate;

    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private String updateUser;

    @TableField(value = "status")
    private String status;

    @TableField(value = "signature")
    private String signature;

    @TableField(exist = false)
    private String statusDesc;

    @TableField(exist = false)
    @NotNull(groups = {Add.class,Upd.class})
    private List<String> roleIds;

    @TableField(exist = false)
    private String viewRoleNames;

    @TableField(exist = false)
    private String viewRoleIds;

    @TableField(exist = false)
    private SecretKey secretKey;

    @TableField(exist = false)
    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 账户是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
