package com.ldd.home.operate.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class AppUtil {
    public static String getCurrentIp(){
        InetAddress inetAddress;
        String address = "127.0.0.1";
        try {
            inetAddress = InetAddress.getLocalHost();
            address = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("getCurrentIp 异常：{}",e.getMessage());
        }

        return address;
    }
}
