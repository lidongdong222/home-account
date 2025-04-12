package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 批量执行记录表
 * </p>
 *
 * @author ldd
 * @since 2024-08-26
 */
@Data
@Builder
@TableName("sys_schedule_his")
@AllArgsConstructor
@NotNull
public class SysScheduleHis {

    @TableId(value = "ssh_id", type = IdType.AUTO)
    private Long sshId;

    /**
     * 批量主键
     */
    @TableField("job_id")
    private String jobId;

    /**
     * 批量组
     */
    @TableField("group_id")
    private String groupId;

    @TableField("version")
    private String version;

    @TableField("exec_start_date")
    private LocalDateTime execStartDate;

    @TableField("exec_end_date")
    private LocalDateTime execEndDate;

    @TableField("exec_time_consum")
    private Integer execTimeConsum;

    @TableField("ip_addr")
    private String ipAddr;

    @TableField("container_id")
    private String containerId;

    /**
     * 执行状态 0待执行 1成功 2失败 3执行中 4失火 5其他原因未执行
     */
    @TableField("status")
    private Integer status;

    /**
     * 失败、异常原因
     */
    @TableField("err_message")
    private String errMessage;

    //    执行状态 0待执行 1成功 2失败 3执行中 4失火 5其他原因未执行
    public enum StatusEnum {

        WAITING(0),
        SUCCESS(1),
        FAIL(2),
        EXECUTING(3),
        MISFIRED(4),
        TRIGGER_FIRED(5);

        int code;

        StatusEnum(int code) {
            this.code = code;
        }

        public int getCode(){
            return this.code;
        }

    }
}
