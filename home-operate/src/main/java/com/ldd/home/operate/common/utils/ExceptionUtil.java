package com.ldd.home.operate.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ExceptionUtil {

    public static String getExceptionCause(Throwable e){
        StackTraceElement[] stackTrace = e.getStackTrace();
        return e.getMessage()+"\n"+Arrays.stream(stackTrace).map(t->t.toString()).collect(Collectors.joining("\n"));
    }

    public static String getExceptionCause(Throwable e,int length){
        StackTraceElement[] stackTrace = e.getStackTrace();
        return StringUtils.substring(e.getMessage()+"\n"+Arrays.stream(stackTrace).map(t->t.toString()).collect(Collectors.joining("\n")),0,length);
    }

}
