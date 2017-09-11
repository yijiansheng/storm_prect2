package com.stormthread.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * Author: changdalin
 * Date: 2017/9/11
 * Description:
 **/
public class T_lo_parkunpark {
    public static void main(String[] args) {
        //    park();
        // unpark();
        Thread parkThread = new Thread(new T_lo_Parkthread());
        Thread unparkThread = new Thread(new T_lo_Unparkthread(parkThread));
        parkThread.start();
        unparkThread.start();
    }

    /**
     * date:2017/9/11
     * description:park,不向下执行
     */
    public static void park() {
        LockSupport.park();
        System.out.println("123");
    }

    public static void unpark() {
        LockSupport.unpark(Thread.currentThread());
        LockSupport.park();
        System.out.println("123");
    }

}
