package com.stormreflect;

/**
 * Author: changdalin
 * Date: 2017/9/14
 * Description:
 **/
public class Str {
    public static void main(String[] args) {
        String a = "1";
        String b = "2";
        String c = "1" + "2";
        String d = "12";
        String e = a + b;
        System.out.println(c==d);
        System.out.println(c==e);
    }
}
