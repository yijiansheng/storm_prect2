package com.stormthread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

public class T_countdown {
    private int threadNum;
    private int workNum;
    private ExecutorService service;
    private ArrayBlockingQueue<String> blockingQueue;
    private CountDownLatch latch;

    public T_countdown() {
        this.threadNum = 5;
        this.workNum = 20;
    }

    @Before
    public void setUp() {
        this.service = Executors.newFixedThreadPool(this.threadNum, new ThreadFactoryBuilder().setNameFormat("WorkThread-%d").build());
        this.blockingQueue = new ArrayBlockingQueue(this.workNum);
        for (int i = 0; i < this.workNum; i++) {
            this.blockingQueue.add("任务-" + i);
        }
        this.latch = new CountDownLatch(this.workNum);
    }

    @Test
    public void test()
            throws InterruptedException {
        System.out.println("主线程运行");
        for (int i = 0; i < this.workNum; i++) {
            this.service.execute(new WorkRunnable());
        }
        this.latch.await();
        System.out.println("主线程继续执行");
    }

    public String getWork() {
        return (String) this.blockingQueue.poll();
    }

    class WorkRunnable implements Runnable {
        WorkRunnable() {
        }

        public void run() {
            String work = T_countdown.this.getWork();
            T_countdown.this.performWork(work);
            T_countdown.this.latch.countDown();
        }
    }

    private void performWork(String work) {
        System.out.println("处理任务:" + work);
        try {
            Thread.currentThread();
            Thread.sleep(60L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
