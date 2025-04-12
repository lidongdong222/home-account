package com.ldd.home.operate.common.utils;

import jakarta.servlet.http.Cookie;

import java.util.Objects;

public class HttpServletRequestUtil {
    /**
     * 获取COOKIE信息
     */
    public static String getCookieInfo(Cookie[] cookies, String cookieKey) {
        if(cookies==null) return null;
        for (Cookie cookie : cookies) {
            if(Objects.equals(cookie.getName(),cookieKey)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
