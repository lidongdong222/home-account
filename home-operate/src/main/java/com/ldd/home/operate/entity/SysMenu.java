package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ldd.home.operate.common.ext.Add;
import com.ldd.home.operate.common.ext.Upd;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * 菜单信息表
 * </p>
 *
 * @author ldd
 * @since 2024-01-31
 */
@Getter
@Setter
@TableName("sys_menu")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu {

    /**
     * 菜单id
     */
    @NotNull(groups = {Upd.class})
    @TableId(value = "menu_id",type = IdType.AUTO)
    private Integer menuId;

    /**
     * 菜单编码
     */
    @Size(max= 30,groups = {Upd.class, Add.class})
    @NotBlank(groups = {Upd.class, Add.class})
    @TableField("menu_code")
    private String menuCode;

    /**
     * 菜单名称
     */
    @Size(max= 30,groups = {Upd.class, Add.class})
    @NotBlank(groups = {Upd.class, Add.class})
    @TableField("menu_name")
    private String menuName;

    /**
     * 菜单名称
     */
    @Size(max= 30,groups = {Upd.class, Add.class})
    @TableField("router")
    private String router;

    /**
     * 父id
     */
    @NotNull(groups = {Add.class})
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 图标
     */
    @TableField("icon")
    @Size(max= 30,groups = {Upd.class, Add.class})
    private String icon;

    /**
     * 状态 1启用 0禁用
     */
    @NotBlank(groups = {Upd.class, Add.class})
    @Pattern(regexp = "^1|2$",groups = {Upd.class, Add.class})
    @TableField("status")
    private String status;

    /**
     * 状态 1启用 0禁用
     */
    @TableField(exist = false)
    private String statusDesc;

    /**
     * 备注
     */
    @TableField("remark")
    @Size(max= 100,groups = {Upd.class, Add.class})
    private String remark;


    @TableField(exist = false)
    private List<SysMenu> children;



}
