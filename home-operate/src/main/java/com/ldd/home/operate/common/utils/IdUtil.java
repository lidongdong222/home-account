package com.ldd.home.operate.common.utils;

import java.util.UUID;

public class IdUtil {
    public static String getUUid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
