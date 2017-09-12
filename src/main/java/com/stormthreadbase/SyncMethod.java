package com.stormthreadbase;

/**
 * Author: changdalin
 * Date: 2017/9/11
 * Description:
 **/
public class SyncMethod {
    private static Object lock = new Object();
    public static void main(String[] args) {
        method1();
        method2();
    }
    synchronized static void method1() {
        System.out.println("1");
    }
    synchronized static void method2() {
        System.out.println("2");
    }
}
