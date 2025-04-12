package util;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyTest {

    final String regex_usercode = "^[^ ]{3,20}";

    @Test
    public  void t1(){
        System.out.println(new Random().nextLong());
    }

    @Test
    public  void t2(){
        System.out.println(JSON.toJSONString("你好|测试".split("\\|")));
    }

    @Test
    public  void t3(){
        String dealSql = "select #{ aaa }";
        Map params = new HashMap();
        while(dealSql.contains("#{")){
            int paramBeginIndex = dealSql.indexOf("#{");
            int paramEndIndex = dealSql.indexOf("}",paramBeginIndex+2);
            String key  = dealSql.substring(paramBeginIndex+2,paramEndIndex);
            if(params.containsKey(key.trim())){
            }
            dealSql=dealSql.replace(dealSql.substring(paramBeginIndex,paramEndIndex+1),"?");
        }
        System.out.println(dealSql);
    }

    @Test
    public void t4(){
        System.out.println("11lddtwtw..33".matches(".*[0-9]+.*"));
    }

    @Test
    public void t5(){
        System.out.println(StringUtils.strip("  test    \r\n\t "));
    }

}
