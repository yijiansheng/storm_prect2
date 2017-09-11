package com.stormthread.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Author: changdalin
 * Date: 2017/9/11
 * Description:
 **/
public class T_fut_Main {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        List<Future<String>> resultList = new ArrayList<Future<String>>();
        for (int i = 0; i < 5; i++) {
            Future<String> future = threadPool.submit(new SimpleCallable(i + 1));
            /**
             * date:2017/9/11
             * description:瞬时的，这个不阻塞，还没有执行call()方法
             */
            resultList.add(future);
        }
        //normalDayin(threadPool, resultList);
        //dayin1(threadPool, resultList);
        dayin2(threadPool, resultList);
    }

    /**
     * date:2017/9/11
     * description:就直接循环所有future
     * 这个打印方式明显有问题
     * 主线程在第一次for的时候，就阻塞住了
     * 直到get的这个值返回，主线程才会被放开，执行下一次for循环，又可能会被阻塞
     */
    private static void normalDayin(ExecutorService threadPool, List<Future<String>> resultList) {
        for (Future<String> tempFuture : resultList) {
            System.out.println("执行for循环");
            try {
                System.out.println(Thread.currentThread().getName() + "打印线程被阻塞");
                String tempStr = tempFuture.get();
                System.out.println(tempStr);
                System.out.println(Thread.currentThread().getName() + "打印线程解除阻塞");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                threadPool.shutdown();
            }
        }
    }


    /**
     * date:2017/9/11
     * description:每一个future，都另起一个线程，来打印结果
     * 这样耗费性能，特点是只要一个返回，则立刻返回结果
     */
    public static void dayin1(final ExecutorService threadPool, final List<Future<String>> resultList) {
        for (final Future<String> tempFuture1 : resultList) {
            System.out.println("在for循环内");
            new Thread(new Runnable() {
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + "打印线程被阻塞");
                        String tempStr = tempFuture1.get();
                        System.out.println(tempStr);
                        System.out.println(Thread.currentThread().getName() + "打印线程解除阻塞");
                    } catch (Exception e) {

                    } finally {
                        threadPool.shutdown();
                    }
                }
            }).start();

        }
    }


    /**
     * date:2017/9/11
     * description:用线程池检测，不自己启动线程
     * 这样可以复用线程，执行线程名会被记录
     * 而且execute的任务，和submit的一样，都会被线程池记录在任务队列里面
     * 那个队列的参数，存储的是runnable
     */
    public static void dayin2(ExecutorService threadPool, List<Future<String>> resultList) {
        for (final Future<String> tempFuture1 : resultList) {
            System.out.println("在for循环内");
            threadPool.execute(new Runnable() {
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + "打印线程被阻塞......");
                        String tempStr = tempFuture1.get();
                        System.out.println(tempStr);
                        System.out.println(Thread.currentThread().getName() + "打印线程解除阻塞");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
    }
}


class SimpleCallable implements Callable<String> {
    private int id;

    public SimpleCallable(int id) {
        this.id = id;
    }

    public String call() throws Exception {
        System.out.println("任务线程" + Thread.currentThread().getName() + " 开始执行");
        Thread.sleep((long) (Math.random() * 10000));
        System.out.println("任务线程" + Thread.currentThread().getName() + " 执行完");
        return "输出:执行任务线程:" + Thread.currentThread().getName() + "结果是" + id;
    }
}
