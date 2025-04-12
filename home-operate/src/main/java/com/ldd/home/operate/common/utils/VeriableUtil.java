package com.ldd.home.operate.common.utils;

import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.StringUtils;

public class VeriableUtil {
    /**
     * 将${***} 替换为实际值
     * @param repStr
     * @param requestParam
     * @return
     */
    public static String replaceVariable(String repStr, JSONObject requestParam) {
        while(repStr.contains("${")){
            int paramBeginIndex = repStr.indexOf("${");
            int paramEndIndex = repStr.indexOf("}",paramBeginIndex+2);
            String key  = StringUtils.trim(repStr.substring(paramBeginIndex+2,paramEndIndex));
            repStr = StringUtils.replaceOnce(repStr,repStr.substring(paramBeginIndex,paramEndIndex+1), StrUtil.toStr(requestParam.get(key)));
        }
        return repStr;
    }

}
