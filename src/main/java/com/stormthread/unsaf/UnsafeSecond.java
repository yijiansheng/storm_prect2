package com.stormthread.unsaf;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * Author: changdalin
 * Date: 2017/9/8
 * Description:
 **/
public class UnsafeSecond {

    private static Unsafe unsafe;

    static {
        try {
            // 通过反射的方式获取unsafe类
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(new Object());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Unsafe getInstance() {
        return unsafe;
    }

    public static void main(String[] args) {
        UnsafeSecond.getInstance();
        System.out.println(123);
    }
}
