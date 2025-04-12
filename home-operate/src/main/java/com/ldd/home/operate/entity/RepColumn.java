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
 * sheet页列定义表
 * </p>
 *
 * @author ldd
 * @since 2024-04-16
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("rep_column")
public class RepColumn {

    /**
     * 主键
     */
    @TableId(value = "col_id", type = IdType.AUTO)
    private Long colId;

    /**
     * 报表sheet定义表id
     */
    @TableField("rep_id")
    private Integer repId;

    /**
     * 报表sheet定义表id
     */
    @TableField("sheet_id")
    private Integer sheetId;

    /**
     * 多级表头层级索引，从1开始
     */
    @TableField("header_index")
    private Integer headerIndex;

    /**
     * 列索引，从1开始
     */
    @TableField("col_index")
    private Integer colIndex;

    /**
     * 列名称
     */
    @TableField("col_name")
    private String colName;

    /**
     * 列数据类型
     */
    @TableField("col_data_type")
    private String colDataType;

    /**
     * 列匹配查询结果集合字段
     */
    @TableField("col_prop")
    private String colProp;

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

    /**
     * 宽度
     */
    @TableField("width")
    private Integer width;

    @TableField("is_necessary")
    private String isNecessary;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private Date updateDate;

    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private String updateUser;

    @TableField("is_data_merge_cell")
    private String isDataMergeCell;

    @TableField("is_number_format")
    private String isNumberFormat;

    @TableField("is_metadata")
    private String isMetadata;

    @TableField("metadata_router")
    private String metadataRouter;

    @TableField("metadata_self_params")
    private String metadataSelfParams;

    public enum NecessaryStatus{
        YES("1"),
        No("0");

        String value;
        NecessaryStatus(String value){
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }
    }

    public enum ColDataType{
        STRING("string"),
        NUMBER("number"),
        DATE("date"),
        TIME("time"),
        DATETIME("datetime");

        String value;
        ColDataType(String value){
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }
    }
}
