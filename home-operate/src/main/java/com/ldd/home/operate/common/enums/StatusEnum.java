package com.ldd.home.operate.common.enums;

public enum StatusEnum{
    //有效
    EFFECTIVE("1"),
    //无效
    INVALID("0");

    String code;

    StatusEnum(String code) {
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }


}