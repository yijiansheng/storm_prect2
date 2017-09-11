package com.stormthread.futuretask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Author: changdalin
 * Date: 2017/9/8
 * Description:
 **/
public class T_ft_CallMain {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //xianchengchiOneRunnable();
        //threadOneRunnable();
        //xianchengchiShut();
        //someThreadOneRunnable();
        someThreadOneCallable();
    }

    /**
     * date:2017/9/8
     * description:结果显示，线程池分了一个线程，就将一个runnable执行完
     * 待测试:为什么线程池不关闭程序不停止
     */
    public static void xianchengchiOneRunnable() throws ExecutionException, InterruptedException {
        ExecutorService xianchengchi = Executors.newFixedThreadPool(10);
        //建立一个callable任务
        T_ft_CallRunnable mission = new T_ft_CallRunnable();
        //建立一个runnable任务
        FutureTask<Integer> runnable = new FutureTask<Integer>(mission);
        xianchengchi.execute(runnable);
        Integer result = runnable.get();
        System.out.println(result);
        xianchengchi.shutdown();
    }


    /**
     * date:2017/9/8
     * description:一个子线程跑一个任务
     * 可见主线程确实阻塞
     */
    public static void threadOneRunnable() throws ExecutionException, InterruptedException {
        T_ft_CallRunnable mission = new T_ft_CallRunnable();
        FutureTask<Integer> runnable = new FutureTask<Integer>(mission);
        new Thread(runnable).start();
        Thread.sleep(1000);
        System.out.println("主线程sleep完毕");
        Integer result = runnable.get();
        System.out.println(result);
    }

    /**
     * date:2017/9/8
     * description:可见确实需要关闭线程池，不然程序不停止
     */
    public static void xianchengchiShut() {
        ExecutorService xianchengchi = Executors.newFixedThreadPool(1);
        T_ft_SimpleRunnable mission = new T_ft_SimpleRunnable();
        xianchengchi.execute(mission);
        xianchengchi.shutdown();
    }


    /**
     * date:2017/9/8
     * description:多个线程执行一个任务
     */
    public static void someThreadOneRunnable() throws ExecutionException, InterruptedException {
        //建立一个callable任务,与futureTask是对应的数量
        // 一定是3个线程执行run()代码
        T_ft_SimpleRunnable mission = new T_ft_SimpleRunnable();
        new Thread(mission,"p1").start();
        new Thread(mission,"p2").start();
        new Thread(mission,"p3").start();
    }

    /**
     * date:2017/9/8
     * description:多个线程执行一个任务
     */
    public static void someThreadOneCallable() throws ExecutionException, InterruptedException {
        //建立一个callable任务,与futureTask是对应的数量
        T_ft_CallRunnable mission = new T_ft_CallRunnable();
        final FutureTask<Integer> runnable = new FutureTask<Integer>(mission);
        /**
         * description:任务开始执行
         */
        new Thread(runnable,"p1").start();

        /**
         * description:其他的thread再执行不起作用了
         * 所以start不起作用了
         */
        new Thread(runnable,"p2").start();
        new Thread(runnable,"p3").start();

        //观察get方法的runnable
        Runnable resultRunnable = new Runnable() {
            public void run() {
                try {
                    runnable.get();
                    System.out.println(Thread.currentThread().getName() + "线程拿到了结果");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(resultRunnable,"sub1").start();
        new Thread(resultRunnable,"sub2").start();
        new Thread(resultRunnable,"sub3").start();
        /**
         * description:观察不get的子线程是否阻塞
         */
        new Thread(new Runnable() {
            public void run() {
                System.out.println("无关紧要的子线程");
            }
        }).start();
        /**
         * description:观察子线程的get，是否阻塞主线程
         * 不阻塞
         */
        System.out.println("主线程结束");
        /**
         * date:2017/9/11
         * description:主线程开始拿结果
         * 几乎和子线程同时获取到
         */
        Integer result = runnable.get();
        System.out.println("主线程结果"+result);
        /**
         * date:2017/9/11
         * description:
         * 判断是否已经执行完毕，就看里面管理的那个线程
         是什么状态
         */
        System.out.println(runnable.isDone());
    }


}
