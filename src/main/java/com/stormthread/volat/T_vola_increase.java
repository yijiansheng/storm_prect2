package com.stormthread.volat;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author: changdalin
 * Date: 2017/9/7
 * Description:
 **/
public class T_vola_increase implements Runnable {
    private volatile int num;

    public void run() {
        for (int i = 0; i < 10; i++) {
            //syncIncrease();
            //lockIncrease();
            atomIncrease();
        }
    }

    public synchronized void syncIncrease() {
        try {
            Thread.sleep(100);
            num++;
            System.out.println(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    Lock lock = new ReentrantLock();

    public void lockIncrease() {
        lock.lock();
        try {
            Thread.sleep(100);
            num++;
            System.out.println(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    AtomicInteger aNum = new AtomicInteger();

    public void atomIncrease() {
        try {
            Thread.sleep(100);
            aNum.getAndIncrement();
            System.out.println(aNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
