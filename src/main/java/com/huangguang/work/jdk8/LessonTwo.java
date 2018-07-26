package com.huangguang.work.jdk8;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * Description: Lambda表达式
 * User : huangguang
 * DATE : 2018-07-25 17:22
 */
public class LessonTwo {

    /*    以下是lambda表达式的重要特征:
        可选类型声明：不需要声明参数类型，编译器可以统一识别参数值。
        可选的参数圆括号：一个参数无需定义圆括号，但多个参数需要定义圆括号。
        可选的大括号：如果主体包含了一个语句，就不需要使用大括号。
        可选的返回关键字：如果主体只有一个表达式返回值则编译器会自动返回值，大括号需要指定明表达式返回了一个数值。*/
    public static void main(String[] args) {
        LessonTwo lessonTwo = new LessonTwo();

        //类型声明
        MathOperation addition = (int a, int b) -> a + b;

        //不用类型声明
        MathOperation subition = (a, b) -> a - b;

        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };

        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;

        System.out.println("10 + 5 = " + lessonTwo.operate(10, 5, addition));
        System.out.println("10 - 5 = " + lessonTwo.operate(10, 5, subition));

        System.out.println("10 / 5 = " + addition.operation2(10, 5));

        // 不用括号
        GreetingService greetService1 = message -> System.out.println("Hello " + message);
        GreetingService greetingService3 = new GreetingService() {
            @Override
            public void sayMessage(String message) {
                System.out.println("Hello " + message);
            }
        };

        // 用括号
        GreetingService greetService2 = (message) -> System.out.println("Hello " + message);

        greetService1.sayMessage("Runoob");
        greetService2.sayMessage("Google");
        greetingService3.sayMessage("test");

        /*
            Lambda 表达式主要用来定义行内执行的方法类型接口，
            例如，一个简单方法接口。在上面例子中，我们使用各种类型的Lambda表达式来定义MathOperation接口的方法。然后我们定义了sayMessage的执行。
            Lambda 表达式免去了使用匿名方法的麻烦，并且给予Java简单但是强大的函数化的编程能力。
            ambda 表达式的局部变量可以不用声明为 final，但是必须不可被后面的代码修改（即隐性的具有 final 的语义）
         */

    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }


    interface MathOperation {
        int operation(int a, int b);
        default int operation2(int a, int b) {
            return a / b;
        };
    }

    interface GreetingService {
        void sayMessage(String message);
    }
}
