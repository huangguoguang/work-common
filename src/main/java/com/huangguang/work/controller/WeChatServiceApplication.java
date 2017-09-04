package com.huangguang.work.controller;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by huangguang on 2017/7/28.
 */
@SpringBootApplication
@ComponentScan("com.huangguang.work")
public class WeChatServiceApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WeChatServiceApplication.class);
        app.setBannerMode(Banner.Mode.CONSOLE);
        app.run(args);
    }
}
