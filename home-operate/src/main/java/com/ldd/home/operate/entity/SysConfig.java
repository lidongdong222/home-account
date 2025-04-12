package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ldd.home.operate.common.ext.Add;
import com.ldd.home.operate.common.ext.Upd;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 系统配置信息表
 * </p>
 *
 * @author ldd
 * @since 2024-09-05
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_config")
public class SysConfig {

    /**
     * 主键
     */
    @TableId(value = "config_id", type = IdType.AUTO)
    @NotNull(groups = {Upd.class})
    private Integer configId;

    /**
     * key
     */
    @TableField("config_key")
    @NotBlank(groups = {Add.class},message = "配置key不能为空！")
    @Length(groups = {Add.class,Upd.class},max = 60)
    private String configKey;

    /**
     * value
     */
    @TableField("config_value")
    @NotBlank(groups = {Add.class,Upd.class},message = "配置值不能为空！")
    @Length(groups = {Add.class,Upd.class},max = 300)
    private String configValue;

    /**
     * 配置描述
     */
    @Length(groups = {Add.class,Upd.class},max = 200)
    @TableField("config_desc")
    private String configDesc;


    ;

}
