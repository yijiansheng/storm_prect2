package com.stormthread.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * Author: changdalin
 * Date: 2017/9/11
 * Description:
 **/
public class T_lo_Unparkthread implements Runnable {

    private Thread jiesuoThread;

    T_lo_Unparkthread(Thread t) {
        jiesuoThread = t;
    }

    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行解锁");
        LockSupport.unpark(jiesuoThread);
    }
}
