package com.ldd.home.operate.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ldd.home.operate.common.utils.DateUtil;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.config.security.v1.HomeContextHolderStrategy;
import com.ldd.home.operate.entity.SysUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
public class HomeAutoFillFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        SysUser user = HomeContextHolderStrategy.getUser();
        try {
            this.strictInsertFill(metaObject,"createDate",String.class, DateUtil.formatDatetime(new Date()));
            this.strictInsertFill(metaObject,"createUser",String.class, StrUtil.toStr(user.getUserId()));
        } catch (ParseException e) {
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        SysUser user = HomeContextHolderStrategy.getUser();
        try {
            this.strictUpdateFill(metaObject,"updateDate",String.class, DateUtil.formatDatetime(new Date()));
            this.strictUpdateFill(metaObject,"updateUser",String.class,StrUtil.toStr(user.getUserId()));
        } catch (ParseException e) {
        }
    }
}
