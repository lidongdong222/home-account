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
 * 导入信息表
 * </p>
 *
 * @author ldd
 * @since 2024-04-30
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("imp_info")
public class ImpInfo {

    /**
     * 主键
     */
    @TableId(value = "imp_id", type = IdType.AUTO)
    private Long impId;

    /**
     * 资源id 导入文件存放地址
     */
    @TableField("res_id")
    private Long resId;

    /**
     * 文件名称
     */
    @TableField("res_name")
    private String resName;

    /**
     * 来源菜单
     */
    @TableField("menu_id")
    private Integer menuId;

    /**
     * 导入时间
     */
    @TableField("imp_time")
    private Date impTime;

    /**
     * 导入目标表
     */
    @TableField("imp_target_table")
    private String impTargetTable;

    /**
     * 导入临时表
     */
    @TableField("imp_tmp_table")
    private String impTmpTable;

    /**
     * 导入临时表
     */
    @TableField("analysis_bean")
    private String analysisBean;

    /**
     * 导入临时表
     */
    @TableField("analysis_method")
    private String analysisMethod;

    /**
     * 导入临时表
     */
    @TableField("status")
    private Integer status;

    /**
     * 总条数
     */
    @TableField("imp_total")
    private Integer impTotal;

    /**
     * 成功条数
     */
    @TableField("imp_succ")
    private Integer impSucc;

    /**
     * 失败条数
     */
    @TableField("imp_fail")
    private Integer impFail;

    /**
     * 导入总耗时,单位：秒
     */
    @TableField("imp_time_comsum")
    private Integer impTimeComsum;

    /**
     * 导入信息
     */
    @TableField("imp_message")
    private String impMessage;

    /**
     * 创建日期
     */
    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 创建用户
     */
    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 更新日期
     */
    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private Date updateDate;

    /**
     * 更新用户
     */
    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private String updateUser;

    public enum ImpStatusEnum{
        WAIT(0),
        SUCCESS(1),
        FAIL(2),
        HANDING(3),
        OVERTIME(4),
        EXCEPTION(5);
        int code;

        ImpStatusEnum(int code) {
            this.code = code;
        }

        public int getCode(){
            return this.code;
        }
    }
}
