package com.ldd.home.operate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/t")
public class TestController {

    @RequestMapping("/t1")
    public String t1(){
        return "111";
    }


}
