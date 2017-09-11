package com.stormthread.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: changdalin
 * Date: 2017/9/11
 * Description:
 **/
public class T_threadpool {

    static class SimpleRunnable implements Runnable {
        private int num;

        public void run() {
            System.out.println(Thread.currentThread().getName() + "正在执行");
            num += Math.random() * 1000;
            System.out.println(Thread.currentThread().getName() + "  " + num);
        }
    }

    public static void main(String[] args) throws Exception {
        SimpleRunnable runnable = new SimpleRunnable();
//        for (int i = 0; i < 10; i++) {
//            new Thread(runnable).start();
//        }
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        threadPool.execute(runnable);
        threadPool.execute(runnable);
        threadPool.shutdown();
    }

}
