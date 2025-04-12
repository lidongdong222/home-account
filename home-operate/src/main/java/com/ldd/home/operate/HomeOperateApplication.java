package com.ldd.home.operate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(exclude = {QuartzAutoConfiguration.class})
@EnableTransactionManagement
public class HomeOperateApplication /*extends SpringBootServletInitializer*/ {


    public static void main(String[] args) {
        SpringApplication.run(HomeOperateApplication.class, args);
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
//        return builder.sources(HomeOperateApplication.class);
//    }

}
