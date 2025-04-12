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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * <p>
 * 横式科目信息表
 * </p>
 *
 * @author ldd
 * @since 2024-09-10
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sub_info")
public class SubInfo {

    /**
     * 主键
     */
    @TableId(value = "sub_id", type = IdType.AUTO)
    private Integer subId;

    /**
     * 分类
     */
    @TableField("sub_type")
    @NotBlank(groups = {Add.class, Upd.class},message = "分类不能为空！")
    private String subType;

    @TableField(exist = false)
    private String subTypeName;

    /**
     * 总科目
     */
    @TableField("sub_code")
    @NotBlank(groups = {Add.class, Upd.class},message = "科目编码不能为空！")
    @Size(max=200,message = "科目编码最大长度200！")
    private String subCode;

    /**
     * 总科目名称
     */
    @TableField("sub_name")
    @NotBlank(groups = {Add.class,Upd.class},message = "科目名称不能为空！")
    @Size(max=200,message = "科目编码最大长度350！")
    private String subName;

    /**
     * 1级科目
     */
    @TableField("sub_code1")
    private String subCode1;

    /**
     * 1级科目名称
     */
    @TableField("sub_name1")
    private String subName1;

    /**
     * 2级科目
     */
    @TableField("sub_code2")
    private String subCode2;

    /**
     * 2级科目名称
     */
    @TableField("sub_name2")
    private String subName2;

    /**
     * 3级科目
     */
    @TableField("sub_code3")
    private String subCode3;

    /**
     * 3级科目名称
     */
    @TableField("sub_name3")
    private String subName3;

    /**
     * 4级科目
     */
    @TableField("sub_code4")
    private String subCode4;

    /**
     * 4级科目名称
     */
    @TableField("sub_name4")
    private String subName4;

    /**
     * 5级科目
     */
    @TableField("sub_code5")
    private String subCode5;

    /**
     * 5级科目名称
     */
    @TableField("sub_name5")
    private String subName5;

    /**
     * 6级科目
     */
    @TableField("sub_code6")
    private String subCode6;

    /**
     * 6级科目名称
     */
    @TableField("sub_name6")
    private String subName6;

    /**
     * 7级科目
     */
    @TableField("sub_code7")
    private String subCode7;

    /**
     * 7级科目名称
     */
    @TableField("sub_name7")
    private String subName7;

    /**
     * 8级科目
     */
    @TableField("sub_code8")
    private String subCode8;

    /**
     * 8级科目名称
     */
    @TableField("sub_name8")
    private String subName8;

    /**
     * 9级科目
     */
    @TableField("sub_code9")
    private String subCode9;

    /**
     * 9级科目名称
     */
    @TableField("sub_name9")
    private String subName9;

    /**
     * 10级科目
     */
    @TableField("sub_code10")
    private String subCode10;

    /**
     * 10级科目名称
     */
    @TableField("sub_name10")
    private String subName10;

    /**
     * 11级科目
     */
    @TableField("sub_code11")
    private String subCode11;

    /**
     * 11级科目名称
     */
    @TableField("sub_name11")
    private String subName11;

    /**
     * 12级科目
     */
    @TableField("sub_code12")
    private String subCode12;

    /**
     * 12级科目名称
     */
    @TableField("sub_name12")
    private String subName12;

    /**
     * 13级科目
     */
    @TableField("sub_code13")
    private String subCode13;

    /**
     * 13级科目名称
     */
    @TableField("sub_name13")
    private String subName13;

    /**
     * 14级科目
     */
    @TableField("sub_code14")
    private String subCode14;

    /**
     * 14级科目名称
     */
    @TableField("sub_name14")
    private String subName14;

    /**
     * 15级科目
     */
    @TableField("sub_code15")
    private String subCode15;

    /**
     * 15级科目名称
     */
    @TableField("sub_name15")
    private String subName15;

    /**
     * 16级科目
     */
    @TableField("sub_code16")
    private String subCode16;

    /**
     * 16级科目名称
     */
    @TableField("sub_name16")
    private String subName16;

    /**
     * 17级科目
     */
    @TableField("sub_code17")
    private String subCode17;

    /**
     * 17级科目名称
     */
    @TableField("sub_name17")
    private String subName17;

    /**
     * 18级科目
     */
    @TableField("sub_code18")
    private String subCode18;

    /**
     * 18级科目名称
     */
    @TableField("sub_name18")
    private String subName18;

    /**
     * 19级科目
     */
    @TableField("sub_code19")
    private String subCode19;

    /**
     * 19级科目名称
     */
    @TableField("sub_name19")
    private String subName19;

    /**
     * 20级科目
     */
    @TableField("sub_code20")
    private String subCode20;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;

    /**
     * 科目描述
     */
    @TableField("sub_desc")
    private String subDesc;

    /**
     * 20级科目名称
     */
    @TableField("sub_name20")
    private String subName20;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private String createDate;

    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private String updateDate;

    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private String updateUser;

    /**
     * 状态 1正常 0软删除
     */
    @TableField("status")
    private String status;

    public enum SubTypeEnum{
        SR("1","收入"),
        ZC("2","支出");

        String code;
        String name;

        SubTypeEnum(String code,String name) {
            this.code = code;
            this.name = name;
        }

        public static String getSubTypeName(String subType) {
            SubTypeEnum[] values = values();
            for (SubTypeEnum e : values) {
                if(Objects.equals(e.code,subType)) return e.name;
            }
            return null;
        }

        public String getCode(){
            return this.code;
        }
    }
}
