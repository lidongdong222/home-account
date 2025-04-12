package com.ldd.home.operate.config.redis;

public class RedisLockConstant {

    //公共设置：并发访问时线程等待时间为30秒，如果30秒内未加锁，则走加锁失败逻辑
    public static final int COMMON_RLOCK_WAIT_TIME_SECONDS = 30;

    //公共设置：锁过期时间为20秒，20秒后锁未释放时，将自动释放
    public static final int COMMON_RLOCK_EXPIRE_TIME_SECONDS = 30;

    //系统管理-菜单管理-菜单排序
    public static final String RLOCK_HOME_SYS_MENU_SORT="RLOCK:SYS:MENU:SORT";
    //系统管理-菜单管理-获取菜单id
    public static final String RLOCK_HOME_SYS_MENU_GET_SORT_ID="RLOCK:SYS:MENU:GET_SORT_ID";


    public static final String RLOCK_HOME_ACC_SUB_OPT="RLOCK:HOME:ACC:SUB_OPT";

    //系统管理-批量管理-分布式添加唯一批量job_id和group_id
    public static final String RLOCK_HOME_SYS_SCHEDULE_ADD_UNIQUE_JOB_GROUP="RLOCK:SYS:SCHEDULE:ADD_UNI_JOB_GROUP";
    //系统管理-批量管理-分布式更新批量
    public static final String RLOCK_HOME_SYS_SCHEDULE_UPD_SCHEDULE="RLOCK:SYS:SCHEDULE:UPD_SCHEDULE";
    //系统管理-批量管理-更新批量状态运行中
    public static final String RLOCK_HOME_SYS_SCHEDULE_UPDATE_RUNNING_STATUS="RLOCK:SYS:SCHEDULE:UPD_RUNNING";


}
