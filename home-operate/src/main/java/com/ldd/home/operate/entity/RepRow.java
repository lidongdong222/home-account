package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * sheet表头行定义表
 * </p>
 *
 * @author ldd
 * @since 2024-09-18
 */
@Getter
@Setter
@TableName("rep_row")
public class RepRow {

    /**
     * 主键
     */
    @TableId(value = "row_id", type = IdType.AUTO)
    private Long rowId;

    /**
     * 报表id
     */
    @TableField("rep_id")
    private Integer repId;

    /**
     * 报表sheet定义表id
     */
    @TableField("sheet_id")
    private Integer sheetId;

    /**
     * 多级表头层级，从1开始
     */
    @TableField("header_index")
    private Integer headerIndex;

    /**
     * 列索引，从1开始
     */
    @TableField("row_index")
    private Integer rowIndex;

    /**
     * 列名称
     */
    @TableField("row_name")
    private String rowName;

    /**
     * 行合并单元格个数
     */
    @TableField("row_span")
    private Integer rowSpan;

    /**
     * 列合并单元格个数
     */
    @TableField("col_span")
    private Integer colSpan;

    /**
     * 高度
     */
    @TableField("height")
    private Integer height;

    @TableField("is_metadata")
    private String isMetadata;

    @TableField("metadata_router")
    private String metadataRouter;

    @TableField("metadata_self_params")
    private String metadataSelfParams;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private String createDate;

    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private String updateDate;

    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private String updateUser;
}
