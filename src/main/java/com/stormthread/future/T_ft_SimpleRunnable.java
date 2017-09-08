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
            while(true){
                Thread.sleep(1000);
                num++;
                System.out.println(Thread.currentThread().getName() + " " + num);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
