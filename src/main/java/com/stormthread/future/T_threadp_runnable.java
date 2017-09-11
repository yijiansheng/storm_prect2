package com.stormthread.future;

/**
 * Author: changdalin
 * Date: 2017/9/11
 * Description:测试runnable的run方法
 **/
public class T_threadp_runnable {
    public static void main(String[] args) {
        new SimpleRunnable().run();
    }
}

class SimpleRunnable implements Runnable{

    public void run() {
        System.out.println(123);
    }
}
