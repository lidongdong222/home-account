package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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
@TableName("sys_dict_dtl")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysDictDtl implements Serializable {

    /**
     * 字典id
     */
    @TableId(value = "dict_id", type = IdType.AUTO)
    private Integer dictId;

    /**
     * 字典
     */
    @TableField("dict_type")
    private String dictType;

    /**
     * 字典码
     */
    @TableField("dict_code")
    private String dictCode;

    /**
     * 字典值
     */
    @TableField("dict_value")
    private String dictValue;

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
