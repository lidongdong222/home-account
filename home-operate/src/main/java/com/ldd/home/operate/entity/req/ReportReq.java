package com.ldd.home.operate.entity.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportReq{
    long repId;
    int total;
    String title;
    String requestUrl;
    String requestParam;


    /**
     * 金额单位
     */
    public enum UnitEnum{
        YUAN("1","元"),
        THOUSAND_YUAN("10000","万元"),
        TRILLION_YUAN("100000000","亿元");

        String code;
        String name;

        UnitEnum(String code,String name) {
            this.code = code;
            this.name = name;
        }

        public static String getUnitName(String code) {
            UnitEnum[] values = values();
            for (UnitEnum e : values) {
                if(Objects.equals(e.code,code)) return e.name;
            }
            return null;
        }

        public String getCode(){
            return this.code;
        }

        public String getName(){
            return this.name;
        }
    }
}
