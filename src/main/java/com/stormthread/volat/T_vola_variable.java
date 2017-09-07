package com.stormthread.volat;

/**
 * Author: changdalin
 * Date: 2017/9/7
 * Description:
 **/
public class T_vola_variable implements Runnable {
    private volatile int num;

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                num++;
                System.out.println(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
