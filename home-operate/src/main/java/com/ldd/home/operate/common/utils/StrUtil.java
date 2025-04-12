package com.ldd.home.operate.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author lidongdong
 * @version 1.0
 * @description
 * @date 2021/10/9
 */
public class StrUtil {

    /**
     * 转换成字符串
     */
    public static   String toStr(Object o){
        if(Objects.isNull(o)) return "";
        return o.toString().trim();
    }

    /**
     * 获取int值
     */
    public static   Integer toInt(Object o){
        return toInt(o,0);
    }

    /**
     * 获取int值
     */
    public static  Integer toInt(Object o,int defaultValue){
        if(isEmpty(o)){
            return defaultValue;
        }
        if(o instanceof Integer){
            return (Integer) o;
        }
        return new BigDecimal(o.toString()).intValue();
    }

    /**
     * 转换为boolean
     */
    public static Boolean toBoolean(Object o) {
        if(isEmpty(o)){
            return false;
        }
        if(o instanceof Boolean){
            return (Boolean) o;
        }
        String s = o.toString().toLowerCase();
        if("true".equals(s)){
            return true;
        }else if("false".equals(s)){
            return false;
        }
        throw new RuntimeException("转换失败！");
    }

    /**
     * 转换为Double
     */
    public static Double toDouble(Object o){
        return toDouble(o,2,0.00D);
    }

    /**
     * 转换为Double
     */
    public static Double toDouble(Object o,int scale){
        return toDouble(o,scale,0.00D);
    }

    /**
     * 转换为Double
     */
    public static Double toDouble(Object o,Double defaultValue){
        return toDouble(o,2,defaultValue);
    }

    /**
     * 转换为Double
     */
    public static Double toDouble(Object o,int scale,Double defaultValue){
        if(isEmpty(o)){
            return defaultValue;
        }
        if(o instanceof Double){
            return (Double) o;
        }
        return new BigDecimal(o.toString()).setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 转换为Double
     */
    public static Long toLong(Object o,Long defaultValue){
        if(isEmpty(o)){
            return defaultValue;
        }
        if(o instanceof Long){
            return (Long) o;
        }
        return new BigDecimal(o.toString()).longValue();
    }


    /**
     * 转换为Double
     */
    public static Long toLong(Object o){
        return toLong(o,0L);
    }


    /**
     * 转换为Double
     */
    public static Long toLong(Object o,Double defaultValue){
        return toLong(o,defaultValue);
    }


    /**
     * 判断对象是否为空
     */
    public static boolean isEmpty(Object o){
        if(o==null || "".equals(toStr(o))){
            return true;
        }
        return false;
    }

    /**
     * 判断对象是否为空
     */
    public static boolean isNotEmpty(Object o){
        return !isEmpty(o);
    }

    /**
     * 获取字符串长度
     */
    public static long getStrLength(String o) {
        if(isEmpty(o)) return 0l;
        return toLong(o.trim().length());
    }

    /**
     * trim
     */
    public static String trim(String s){
        return StringUtils.trim(s);
    }
}
