package com.huangguang.work.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * Description:
 * User : huangguang
 * DATE : 2018-06-05 17:00
 */
public class TestList {
    public static void main(String[] args) {
        List<String> aaa = new ArrayList<String>();
        aaa.add("aaa");
        aaa.add("bbb");
        aaa.add("ccc");
        aaa.add(0, "adffdfaf");
        for(int i = 0; i< aaa.size(); i++) {
            System.out.println(aaa.get(i));
        }
    }
}
