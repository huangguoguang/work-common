package com.huangguang.work.jdk8;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * Description:
 * User : huangguang
 * DATE : 2018-07-25 17:01
 */
@Data
public class Dog {

    @Accessors(chain = true)
    private String name;

    @Accessors(chain = true)
    private Integer age;
}
