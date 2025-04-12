package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 * 版本历史记录表
 * </p>
 *
 * @author ldd
 * @since 2024-08-26
 */
@Getter
@Setter
@TableName("sys_schedule_version_his")
public class SysScheduleVersionHis {

    /**
     * 主键id
     */
    @TableId(value = "ssvh_id", type = IdType.AUTO)
    private Long ssvhId;

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

    /**
     * 版本号
     */
    @TableField("version")
    private String version;

    /**
     * cron表达式
     */
    @TableField("cron")
    private String cron;

    /**
     * 执行类
     */
    @TableField("bean_name")
    private String beanName;

    /**
     * 执行方法
     */
    @TableField("method_name")
    private String methodName;

    /**
     * 执行策略 1串行 2并行
     */
    @TableField("execution_strategy")
    private Integer executionStrategy;

    /**
     * 状态 1启动 2停用
     */
    @TableField("status")
    private Integer status;

    /**
     * 版本开始时间
     */
    @TableField("start_date")
    private LocalDateTime startDate;

    /**
     * 版本结束时间
     */
    @TableField("end_date")
    private LocalDateTime endDate;
}
