package com.ldd.home.operate.common.utils;

public class RegexUtil {

    public static final String REGEX_DATE_YYYY_MM_DD = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    public  static final String REGEX_EXCEL_DOUBLE = "^((-?[0-9]{1,7})|(-?[0-9]{1}(,[0-9]{3}){2})|(-?[0-9]{1,3}(,[0-9]{3}){1}))(\\.[0-9]{1,2})?$";

//    YYYYMM
    public static final String REGEX_DATE_YYYYMM = "((19[7-9][0-9])|(2[0-9]{3}))((0[1-9])|1[1-2])$";

    public static final String REGEX_LOGIN_PASSWORD = "[a-zA-Z0-9!@#\\$%\\^&*()_+\\-=\\{\\}\\[\\]:\";'\\,\\.\\?/\\\\\\|]{6,20}";


}

