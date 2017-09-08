package com.stormthread.future;

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
        //someThreadOneCallable();
        someThreadOneRunnable();
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
    public static void someThreadOneCallable() throws ExecutionException, InterruptedException {
        //建立一个callable任务,与futureTask是对应的数量
        T_ft_CallRunnable mission = new T_ft_CallRunnable();
        FutureTask<Integer> runnable = new FutureTask<Integer>(mission);
        new Thread(runnable,"p1").start();
        new Thread(runnable,"p2").start();
        new Thread(runnable,"p3").start();
        Integer result = runnable.get();
        System.out.println(result);
    }



    /**
     * date:2017/9/8
     * description:多个线程执行一个任务
     */
    public static void someThreadOneRunnable() throws ExecutionException, InterruptedException {
        //建立一个callable任务,与futureTask是对应的数量
        T_ft_SimpleRunnable mission = new T_ft_SimpleRunnable();
        new Thread(mission,"p1").start();
        new Thread(mission,"p2").start();
        new Thread(mission,"p3").start();
    }
}
