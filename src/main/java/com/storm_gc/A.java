package com.storm_gc;

/**
 * Author: changdalin
 * Date: 2017/11/2
 * Description:
 **/
public class A {
    public static String aStr = "123";

    public static void main(String[] args) {
        String a = "123";
        String b = "123";
        System.out.println(B.bStr==A.aStr);
    }
}
