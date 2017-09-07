package com.stormthread.waitnotify;

import java.util.List;

/**
 * Author: changdalin
 * Date: 2017/9/7
 * Description:
 **/
public class T_wn_consumer implements Runnable {

    private final List<Integer> taskQueue;

    public T_wn_consumer(List<Integer> sharedQueue) {
        this.taskQueue = sharedQueue;
    }

    public void run() {
        while (true) {
            try {
                consume();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void consume() throws InterruptedException {
        synchronized (taskQueue) {
            System.out.println(Thread.currentThread().getName() + "拿到了锁");
            while (taskQueue.isEmpty()) {
                System.out.println("队列已经空了 " + taskQueue.size());
                System.out.println(Thread.currentThread().getName() + "进入了wait状态");
                taskQueue.wait();
                System.out.println(Thread.currentThread().getName() + "生产者执行wait下面");
            }
            Thread.sleep(1000);
            int i = taskQueue.remove(0);
            System.out.println("线程:" + Thread.currentThread().getName() + "消费了: " + i);
            taskQueue.notifyAll();
        }
    }
}
