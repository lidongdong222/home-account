package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 数据字典信息表
 * </p>
 *
 * @author ldd
 * @since 2024-03-14
 */
@Getter
@Setter
@TableName("sys_dict")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysDict {

    /**
     * 字典
     */
    @TableId("dict_type")
    private String dictType;

    /**
     * 字典名称
     */
    @TableField("dict_name")
    private String dictName;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 状态 1启用 0禁用
     */
    @TableField("status")
    private String status;

    /**
     * 状态 1启用 0禁用
     */
    @TableField(exist = false)
    private String statusName;

}
