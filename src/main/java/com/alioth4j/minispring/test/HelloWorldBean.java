package com.alioth4j.minispring.test;

import com.alioth4j.minispring.web.RequestMapping;

public class HelloWorldBean {

    @RequestMapping("/test")
    public String doTest() {
        return "Hello world!";
    }

}
