package com.stormthread.waitnotify;

import java.util.List;

/**
 * Author: changdalin
 * Date: 2017/9/7
 * Description:
 **/
public class T_wn_producer implements Runnable {
    private final List<Integer> taskQueue;
    private final int MAX_CAPACITY;

    public T_wn_producer(List<Integer> sharedQueue, int size) {
        this.taskQueue = sharedQueue;
        this.MAX_CAPACITY = size;
    }

    public void run() {
        int counter = 0;
        while (true) {
            try {
                /**
                 * 测试代码块同步
                 * //produceNoSync(counter++);
                 System.out.println(Thread.currentThread().getName() + "线程先执行别的");
                 //produceSyncMethod(counter++);
                 produceSyncObj(counter++);
                 System.out.println(Thread.currentThread().getName() + "执行完了同步块，接下来执行");
                 */
                produce(counter++);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void produce(int i) throws InterruptedException {
        synchronized (taskQueue) {
            System.out.println(Thread.currentThread().getName() + "拿到了锁");
            while (taskQueue.size() == MAX_CAPACITY) {
                System.out.println("队列已经满了 " + taskQueue.size());
                System.out.println(Thread.currentThread().getName() + "进入了wait状态");
                taskQueue.wait();
                System.out.println(Thread.currentThread().getName() + "生产者执行wait下面");
            }
            Thread.sleep(1000);
            taskQueue.add(i);
            System.out.println("线程:" + Thread.currentThread().getName() + "生产: " + i);
            taskQueue.notifyAll();
        }
    }

    //不加入同步
    private void produceNoSync(int i) throws InterruptedException {
        if (taskQueue.size() >= MAX_CAPACITY) {
            return;
        }
        Thread.sleep(1000);
        taskQueue.add(i);
        System.out.println(Thread.currentThread().getName() + "queue size " + taskQueue.size());
    }


    //模拟插入时间
    private synchronized void produceSyncMethod(int i) throws InterruptedException {
        if (taskQueue.size() >= MAX_CAPACITY) {
            Thread.sleep(1000);
            return;
        }
        Thread.sleep(1000);
        taskQueue.add(i);
        System.out.println(Thread.currentThread().getName() + "  queue size " + taskQueue.size());
    }

    //模拟插入时间
    private void produceSyncObj(int i) throws InterruptedException {
        synchronized (taskQueue) {
            if (taskQueue.size() >= MAX_CAPACITY) {
                System.out.println(Thread.currentThread().getName() + "进入判断状态");
                Thread.sleep(1000);
                return;
            }
            Thread.sleep(1000);
            taskQueue.add(i);
            System.out.println(Thread.currentThread().getName() + "  queue size " + taskQueue.size());
        }
    }
}