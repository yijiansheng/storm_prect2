package com.stormthread.lock;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author: changdalin
 * Date: 2017/9/11
 * Description:
 **/
public class T_lo_parkunpark {

    public static void main(String[] args) throws InterruptedException {
        //    park();
        // unpark();
        //simpleTest();
        //test();
        //gongpingSuo();
        //recyclePark();
        debugAcquireQueue();
//        testFor();
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

    public static void simpleTest() {
        Thread parkThread = new Thread(new T_lo_Parkthread());
        Thread unparkThread = new Thread(new T_lo_Unparkthread(parkThread));
        parkThread.start();
        unparkThread.start();
    }

    /**
     * date:2017/9/12
     * description:测试多线程下面的park
     */
    public static void test() {
        T_lo_Parkthread runnable = new T_lo_Parkthread();
        /**
         * date:2017/9/12
         * description:十个线程进入阻塞
         */
        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }

    /**
     * date:2017/9/12
     * description:可见lock的时候，并不是说一起抢，而是有顺序的
     */
    public static void gongpingSuo() {
        LockRunnable runnable = new LockRunnable();
        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }

    /**
     * date:2017/9/12
     * description:循环里面有park
     * 可见，只有那个进了if的线程，park了，其他线程不受影响
     */
    public static void recyclePark() {
        RecycleRunnable runnable = new RecycleRunnable();
        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start();
        }
    }


    /**
     * date:2017/9/12
     * description:
     */
    public static void debugAcquireQueue() throws InterruptedException {
        DebugRunnable runnable = new DebugRunnable();
        for (int i = 0; i < 21; i++) {
            Thread.sleep(200);
            //没有疑问，新进来一个线程，走一遍acquire
            //然后明确一点，acqueue的是新加入的node节点
            //新加入的thread节点，才有可能挂起
            //刚好卡住19个，全部都park住
            new Thread(runnable).start();
        }
    }

    public static void testFor() {
        for (;;) {
            System.out.println(123);
        }
    }
}


/**
 * date:2017/9/12
 * description:唤醒和争锁的过程，其实是有顺序的。并不是进来一个，就park起来一个线程
 */
class LockRunnable implements Runnable {
    ReentrantLock lock = new ReentrantLock();

    public void run() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取锁");
            //    Thread.sleep((long) (Math.random() * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * date:2017/9/12
 * description:循环里面有park
 */
class RecycleRunnable implements Runnable {

    public void run() {
        for (; ; ) {
            System.out.println(Thread.currentThread().getName());
            if (Thread.currentThread().getName().equals("Thread-0")) {
                System.out.println(Thread.currentThread().getName() + "park了");
                LockSupport.park();
            }
        }
    }
}

/**
 * date:2017/9/12
 * description:调试acquireQueue方法，那个for循环
 */
class DebugRunnable implements Runnable {
    ReentrantLock lock = new ReentrantLock();

    public void run() {
        lock.lock();
        try {
            System.out.println("睡眠中");
            Thread.sleep(2000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}