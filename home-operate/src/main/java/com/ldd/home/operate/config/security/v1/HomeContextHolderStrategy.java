package com.ldd.home.operate.config.security.v1;

import com.ldd.home.operate.entity.SysUser;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class HomeContextHolderStrategy {
    private static final ThreadLocal<Supplier<SysUser>> contextHolder = new ThreadLocal<>();

    public void clearContext() {
        contextHolder.remove();
    }

    public void setUser(SysUser user){
        contextHolder.set(() -> user);
    }

    public static boolean hasUser(){
        return contextHolder.get()!=null && contextHolder.get().get()!=null;
    }

    public static SysUser getUser(){
        if(contextHolder.get()==null){
            return SysUser.builder().userId(0L).username("无用户").build();
        }else{
            return contextHolder.get().get();
        }
    }

}
