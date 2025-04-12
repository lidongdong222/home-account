package com.ldd.home.operate.config.redis;

public class RedisKeyConstant {

    /**
     * 默认情况下redis缓存时长
     * 系统配置缓存
     * 单位：分钟
     */
    public static final int COMMON_EXPIRE_TIME_SECONDS = 30;

    public static final String SYS_DICT="SYS:DICT";

    //最新版本的批量作业
    public static final String SYS_SCHEDULER="SYS:SCHEDULER";

    public static final String SYS_CONFIG="SYS:CONFIG";

    public static final String SYS_SESSION="SYS:SESSION";
}
