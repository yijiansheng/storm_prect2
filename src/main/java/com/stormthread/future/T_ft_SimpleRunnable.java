package com.stormthread.future;

/**
 * Author: changdalin
 * Date: 2017/9/8
 * Description:
 **/
public class T_ft_SimpleRunnable implements Runnable {
    private int num;

    public void run() {
        try {
            f2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void f1() throws Exception {
        while (true) {
            Thread.sleep(1000);
            num++;
            System.out.println(Thread.currentThread().getName() + " " + num);
        }
    }

    private void f2() throws Exception {
        long l = (long) (Math.random() * 10000);
        System.out.println(Thread.currentThread().getName() + "  进入执行，并且sleep" + l + "毫秒");
        Thread.sleep(l);
        System.out.println(Thread.currentThread().getName() + "  sleep完毕");
        int sum = 0;
        for (int i = 0; i < 10000; i++)
            sum += i;
        System.out.println(Thread.currentThread().getName() + "  "+sum);
        System.out.println(Thread.currentThread().getName() + "  计算完毕");
    }
}
