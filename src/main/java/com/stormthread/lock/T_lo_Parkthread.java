package com.stormthread.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * Author: changdalin
 * Date: 2017/9/11
 * Description:
 **/
public class T_lo_Parkthread implements Runnable {


    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("已经锁住");
        LockSupport.park();
        System.out.println("继续运行");
    }
}
