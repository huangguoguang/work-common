package com.huangguang.work.controller;

import com.huangguang.work.util.HttpClientUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * Description:网页微信
 * User : huangguang
 * DATE : 2018-06-21 16:36
 */
@RestController
@RequestMapping("pcwx")
public class WechatPcController {
    @RequestMapping("qcrode")
    public Object getQcrode(Model model) {
        String oneUrl = "https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_=AAAA";
        long timestamp = System.currentTimeMillis();
        String bbb = oneUrl.replace("AAAA", String.valueOf(timestamp));
        System.out.println(bbb);
        String result = HttpClientUtil.httpGet(bbb, new HashMap<>());
        System.out.println(result);
        String uuid = result.substring(result.indexOf("\"") + 1, result.lastIndexOf("\""));
        System.out.println(uuid);
        String qcrodeUrl = "https://login.weixin.qq.com/qrcode/" + uuid;
        System.out.println(qcrodeUrl);
        model.addAttribute("qcrodeUrl", qcrodeUrl);
        return "aaa";
    }
}
