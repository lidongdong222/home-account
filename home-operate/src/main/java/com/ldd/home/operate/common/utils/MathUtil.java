package com.ldd.home.operate.common.utils;

public class MathUtil {

    public final static String MATH_REGEX="^-?\\d+.?(\\d+)?$";

    public static boolean isMath(String mathStr){
        if(StrUtil.isEmpty(mathStr)) return false;
        return mathStr.matches(MATH_REGEX);
    }

}
