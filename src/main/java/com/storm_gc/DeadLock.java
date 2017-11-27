package com.storm_gc;

/**
 * Author: changdalin
 * Date: 2017/11/7
 * Description:
 **/

class LockT1 implements Runnable {
    private Object o1;
    private Object o2;


    public LockT1(Object o1, Object o2) {
        super();
        this.o1 = o1;
        this.o2 = o2;
    }

    public void run() {
        synchronized (o1) {
            try {
                System.out.println(Thread.currentThread().getName() + " hold o1");
                Thread.sleep(5000);
                synchronized (o2) {
                    System.out.println(Thread.currentThread().getName() + " hold o2");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class LockT2 implements Runnable {
    private Object o1;
    private Object o2;


    public LockT2(Object o1, Object o2) {
        super();
        this.o1 = o1;
        this.o2 = o2;
    }

    public void run() {
        synchronized (o2) {
            try {
                System.out.println(Thread.currentThread().getName() + " hold o2");
                Thread.sleep(1000);
                synchronized (o1) {
                    System.out.println(Thread.currentThread().getName() + " hold o1");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}


class printRunnable implements Runnable {

    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "正在执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class DeadLock {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();
        Thread t1 = new Thread(new LockT1(o1, o2), "thread1");
        Thread t2 = new Thread(new LockT2(o1, o2), "thread2");
        Thread t3 = new Thread(new printRunnable(), "thread3");
        t1.start();
        t2.start();
        t3.start();
    }
}
