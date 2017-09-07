package com.stormthread.volat;

/**
 * Author: changdalin
 * Date: 2017/9/7
 * Description:
 **/
public class T_threadlocal implements Runnable {

    private ThreadLocal<Double> initValue = new ThreadLocal<Double>() {
        @Override
        protected Double initialValue() {
            return Math.random() * 100;
        }
    };
    private double randNum = Math.random() * 100;

    public void run() {
        try {
            Thread.sleep(100);
            System.out.println("propertyValue:" + randNum);
            System.out.println("threadlocal:"+initValue.get());
        } catch (InterruptedException e) {

        }

    }
}
