package com.stormclass;

/**
 * Author: changdalin
 * Date: 2017/9/14
 * Description:
 **/

class Parent {
    static int a = 100;

    static {
        System.out.println("parent init！");
    }
}

class Child extends Parent {
    static {
        System.out.println("child init！");
    }
}

class FinalClass {
    static final int b = 100;

    static {
        System.out.println("finalclass init！");
    }
}

public class ClassInit {
    public static void main(String[] args) {
//        f();
        f2();
    }

    public static void f() {
        System.out.println(Child.a);
        Child a = new Child();
    }

    public static void f2() {
        System.out.println(FinalClass.b);
    }
}
