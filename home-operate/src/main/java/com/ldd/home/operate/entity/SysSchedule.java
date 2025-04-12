package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ldd.home.operate.common.ext.Add;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 批量定时作业表
 * </p>
 *
 * @author ldd
 * @since 2024-08-26
 */
@Getter
@Setter
@TableName("sys_schedule")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysSchedule implements Serializable {

    static final long serialVersionUID = 3298963326723355437L;

    /**
     * 主键id
     */
    @TableId(value = "sche_id", type = IdType.AUTO)
    private Integer scheId;

    /**
     * 批量主键
     */
    @TableField("job_id")
    @NotBlank(groups = {Add.class},message = "批量id不能为空")
    private String jobId;

    /**
     * 批量组
     */
    @TableField("group_id")
    @NotBlank(groups = {Add.class},message = "批量组不能为空")
    private String groupId;

    /**
     * 版本号
     */
    @TableField("version")
    private String version;

    /**
     * cron表达式
     */
    @TableField("cron")
    @NotBlank(groups = {Add.class},message = "cron表达式不能为空")
    private String cron;

    /**
     * 执行类
     */
    @TableField("class_name")
    @NotBlank(groups = {Add.class},message = "执行类不能为空")
    private String className;

    /**
     * 执行策略 1串行 2并行
     */
    @TableField("exec_strategy")
    private Integer execStrategy;

    /**
     * 是否线程批量 1是 2否
     */
    @TableField("high_frequency")
    private Integer highFrequency;

    /**
     * 批量描述
     */
    @TableField("remark")
    private String remark;

    /**
     * 状态 1启动 2停用
     */
    @TableField("status")
    @NotNull(groups = {Add.class},message = "启用状态不能为空")
    private Integer status;

    @TableField(exist = false)
    private String statusName;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private String createDate;

    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private String updateDate;

    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private String updateUser;

    @TableField(exist = false)
    private Integer runningStatus;

    @TableField(exist = false)
    private LocalDateTime beginRunningDate;

    @TableField(exist = false)
    private String sessionId;

    @TableField(exist = false)
    private String runningIp;

    public enum ExecStrategyEnum{

//        串行
        SERIAL(1),
//        并行
        PARALLEL(2);

        int code;

        ExecStrategyEnum(int code){
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum RunningStatusEnum{
//        未运行
        NO(0),
//        运行中
        RUNNING(1);

        int code;

        RunningStatusEnum(int code){
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum StatusEnum{
        ENABLE(1,"启用"),
        DISABLE(2,"停用");

        int code;
        String name;

        StatusEnum(int code,String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode(){
            return this.code;
        }
        public static String getName(int code){
            for (StatusEnum value : values()) {
                if(Objects.equals(value.code,code)) return value.name;
            }
            return null;
        }
    }

    public enum HighFrequencyEnum{
        YES(1,"是"),
        NO(2,"否");

        int code;
        String name;

        HighFrequencyEnum(int code,String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode(){
            return this.code;
        }
    }



}
