package com.huangguang.work.jdk8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * Description: Lambda表达式
 * User : huangguang
 * DATE : 2018-07-25 16:48
 */
public class LessonOne {

    public static void main(String[] args) {
        List<String> names1 = new ArrayList<>();
        names1.add("cat");
        names1.add("dog");
        names1.add("teacher");


        List<String> names2 = new ArrayList<>();
        names2.add("cat");
        names2.add("dog");
        names2.add("teacher");

        LessonOne lessonOne = new LessonOne();
        System.out.println("使用java 7语法");
        lessonOne.sortByJava7(names1);
        System.out.println(names1);

        System.out.println("使用java 8语法");
        lessonOne.sortByJava7(names2);
        System.out.println(names2);


        List<Dog> dogs1 = new ArrayList<>();
        dogs1.add(new Dog().setName("one").setAge(10));
        dogs1.add(new Dog().setName("tas").setAge(8));
        dogs1.add(new Dog().setName("cb").setAge(12));

        List<Dog> dogs2 = new ArrayList<>();
        dogs2.add(new Dog().setName("one").setAge(10));
        dogs2.add(new Dog().setName("tas").setAge(8));
        dogs2.add(new Dog().setName("cb").setAge(12));

        Collections.sort(dogs1, (d1, d2) -> d1.getAge().compareTo(d2.getAge()));
        System.out.println(dogs1);
    }

    private void sortByJava7(List<String> names1) {
        Collections.sort(names1, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
    }

    private void sortByJava8(List<String> names2) {
        Collections.sort(names2, (s1, s2) -> s2.compareTo(s1));
    }
}
