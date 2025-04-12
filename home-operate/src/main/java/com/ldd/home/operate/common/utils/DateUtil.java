package com.ldd.home.operate.common.utils;

import com.ldd.home.operate.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

public class DateUtil {


    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYYMMDD = "yyyyMMdd";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public final static SimpleDateFormat SDF_DATETIME = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
    public static String convertCommonDateParttern(String date) throws BusinessException {
        try{
            if(StrUtil.isEmpty(date)) return null;
            String result = Arrays.stream(date.replaceAll("\\.","-")
                    .replaceAll("/","-")
                    .replaceAll("年","-")
                    .replaceAll("月","-")
                    .replaceAll("日","-")
                    .replaceAll(" ","-")
                    .replaceAll(":","-")
                    .split("-")).map(s-> StringUtils.leftPad(s,2,"0"))
                    .collect(Collectors.joining(""));
            if(Objects.equals(StrUtil.getStrLength(result),6)) result+="01";
            if(StrUtil.getStrLength(result)>=8) result = StringUtils.substring(result,0,8).substring(0,4)
                    +"-"+result.substring(4,6)
                    +"-"+result.substring(6,8);
            if(!result.matches(RegexUtil.REGEX_DATE_YYYY_MM_DD)) throw new BusinessException();
            return result;
        }catch (Exception e){
            throw new BusinessException("日期格式不正确请检查，建议使用YYYY-MM-DD格式！您输入的日期："+date);
        }
    }

    public static Date parseDatetime(String datetime, String yyyyMmDdHhMmSs) throws ParseException {
        return new SimpleDateFormat(yyyyMmDdHhMmSs).parse(datetime);
    }

    public static Date parseDatetime(String datetime) throws ParseException {
        return SDF_DATETIME.parse(datetime);
    }

    public static String formatDatetime(Date datetime) throws ParseException {
        return SDF_DATETIME.format(datetime);
    }

    public static String formatDate(Date date,String format) throws ParseException {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 计算两个LocalDateTime间隔秒数
     */
    public static int betweenSecond(LocalDateTime d1, LocalDateTime d2){
        return Math.abs(StrUtil.toInt(d1.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond()-
                d2.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond()));
    }
}
