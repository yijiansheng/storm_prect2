package com.stormclass;

/**
 * Author: changdalin
 * Date: 2017/9/14
 * Description:
 **/
public class StaticTest {
    private int b = 0;

    /**
     * date:2017/9/14
     * description:静态内部类，提高封装性
     */
    static class A {
        private int a = 0;

        private void f() {
            a = 1;
            //不能引用外部变量
            //b=2;
            System.out.println(1);
        }

        private static void f2() {
            //不能用类里面的非静态变量
            //    a=2;
        }
    }

    public static void main(String[] args) {
        A a = new A();
        a.f();
    }
}
