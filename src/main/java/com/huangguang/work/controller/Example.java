package com.huangguang.work.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huangguang on 2017/7/28.
 */
@RestController
@RequestMapping(value = "/")
public class Example {

    @RequestMapping(value = "test")
    public Object test() {
        return "hello world";
    }
}
