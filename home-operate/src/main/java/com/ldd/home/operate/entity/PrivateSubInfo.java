package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ldd.home.operate.common.ext.Add;
import com.ldd.home.operate.common.ext.Upd;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 科目信息表
 * </p>
 *
 * @author ldd
 * @since 2024-03-21
 */
@Getter
@Setter
@TableName("private_sub_info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrivateSubInfo {

    /**
     * 科目id
     */
    @TableId(value = "sub_id", type = IdType.AUTO)
    private Long subId;

    /**
     * 科目编码
     */
    @NotBlank(groups = {Add.class})
    @Size(max= 50,groups = {Upd.class, Add.class})
    @TableField("sub_code")
    private String subCode;

    /**
     * 科目名称
     */
    @NotBlank(groups = {Add.class})
    @Size(max= 50,groups = {Upd.class, Add.class})
    @TableField("sub_name")
    private String subName;

    /**
     * 父级id
     */
    @NotNull(groups = {Add.class})
    @TableField("parent_id")
    private Long parentId;

    /**
     * 科目等级
     */
    @TableField("sub_level")
    private Integer subLevel;

    /**
     * 科目描述
     */
    @Size(max= 100,groups = {Upd.class, Add.class})
    @TableField("sub_desc")
    private String subDesc;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private Date updateDate;

    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private String updateUser;

    /**
     * 状态 1正常 0软删除
     */
    @TableField("status")
    private String status;

    @TableField(exist = false)
    public List<PrivateSubInfo> children;

    @TableField(exist = false)
    public Boolean hasChildren;



}
