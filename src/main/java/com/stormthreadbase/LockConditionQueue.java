package com.stormthreadbase;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author: changdalin
 * Date: 2017/9/12
 * Description:测试Condition的队列
 **/
public class LockConditionQueue {
    private String[] items = null;
    private final Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();
    private int head, tail, count;

    public LockConditionQueue(int maxSize) {
        items = new String[maxSize];
    }

    public LockConditionQueue() {
        this(3);
    }

    /**
     * date:2017/9/12
     * description:生产者总有wait和notify的代码
     */
    public void put(String t) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                //数组满时，线程进入等待队列挂起。线程被唤醒时，从这里返回。
                //挂的是线程
                //而且是挂在了notFull上面，而且没有执行完的for循环继续执行
                System.out.println(Thread.currentThread().getName() + "生产者阻塞");
                notFull.await();
            }
            items[tail] = t;
            tail++;
            if (tail == items.length) {
                tail = 0;
            }
            ++count;
            System.out.println(Thread.currentThread().getName() + "   produce:" + t);
            //notify，唤醒之后，就会有一个线程获取锁
            notEmpty.signal();
            System.out.println(Thread.currentThread().getName() + "生产者nofity了一把锁");
        } finally {
            lock.unlock();
        }
    }


    public String take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                System.out.println(Thread.currentThread().getName() + "消费者阻塞");
                notEmpty.await();
            }
            String o = items[head];
            items[head] = null;//GC
            head++;
            if (head == items.length) {
                head = 0;
            }
            --count;
            notFull.signal();
            System.out.println(Thread.currentThread().getName() + "  consume:" + o);
            System.out.println(Thread.currentThread().getName() + "消费者nofity了一把锁");
            return o;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final LockConditionQueue queue = new LockConditionQueue();
        //十个线程，每一个线程生产十个str
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    for (int j = 100; j < 110; j++) {
                        try {
                            queue.put(j + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
        //十个消费者
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    for (int j = 100; j < 110; j++) {
                        try {
                            queue.take();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }

    }
}
