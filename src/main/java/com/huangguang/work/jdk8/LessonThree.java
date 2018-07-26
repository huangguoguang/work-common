package com.huangguang.work.jdk8;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * Description: 方法引用通过方法的名字来指向一个方法。
                方法引用可以使语言的构造更紧凑简洁，减少冗余代码。
                方法引用使用一对冒号 :: 。
 * User : huangguang
 * DATE : 2018-07-25 18:04
 */
public class LessonThree {
    public static void main(String[] args) {
        List names = new ArrayList();

        names.add("Google");
        names.add("Runoob");
        names.add("Taobao");
        names.add("Baidu");
        names.add("Sina");

        names.forEach(System.out::println);

        names.forEach(x -> LessonThree.print(String.valueOf(x)));

        names.forEach(LessonThree::print);
    }

    public static void print(Object str) {
        System.out.println(str);
    }
}
