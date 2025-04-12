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
 * 报表sheet定义表
 * </p>
 *
 * @author ldd
 * @since 2024-04-16
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("rep_sheet")
public class RepSheet {

    /**
     * 主键
     */
    @TableId(value = "sheet_id", type = IdType.AUTO)
    private Integer sheetId;

    /**
     * 报表定义表id
     */
    @TableField("rep_id")
    private Integer repId;

    /**
     * sheet索引，从1开始
     */
    @TableField("sheet_index")
    private Integer sheetIndex;

    /**
     * sheet名称
     */
    @TableField("sheet_name")
    private String sheetName;

    /**
     * sheet标题
     */
    @TableField("title")
    private String title;

    @TableField("deal_type")
    private String dealType;

    @TableField("deal_bean")
    private String dealBean;

    @TableField("deal_method")
    private String dealMethod;

    @TableField("deal_sql")
    private String dealSql;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private Date updateDate;

    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private String updateUser;

    public enum DealTypeEnum{

        PROGRAM("1"),
        SQL("2");

        String code;
        DealTypeEnum(String code){
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
    }

}
