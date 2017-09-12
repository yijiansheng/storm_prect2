package com.stormthreadbase;

/**
 * Author: changdalin
 * Date: 2017/9/12
 * Description:
 **/
public class ThreadYield {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("子线程执行");
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        System.out.println(System.currentTimeMillis());
        thread.start();
        Thread.yield();
        System.out.println(System.currentTimeMillis());
    }
}
