package com.wms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: bo
 * @DATE: 2022/11/3 10:57
 **/
@RestController
public class HelloController {
    @GetMapping("/")
    public String hello (){
        return "hello";
    }

}
