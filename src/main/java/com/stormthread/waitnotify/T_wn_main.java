package com.stormthread.waitnotify;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: changdalin
 * Date: 2017/9/7
 * Description:
 **/
public class T_wn_main {
    public static void main(String[] args) {
        //共享资源是这个taskQueue
        List<Integer> taskQueue = new ArrayList<Integer>();
        int MAX_CAPACITY = 5;
        T_wn_producer producer = new T_wn_producer(taskQueue, MAX_CAPACITY);
        T_wn_consumer consumer = new T_wn_consumer(taskQueue);

        //生产者线程
        new Thread(producer, "Producer_t1").start();
        new Thread(producer, "Producer_t2").start();
        new Thread(producer, "Producer_t3").start();
        new Thread(producer, "Producer_t4").start();
        new Thread(producer, "Producer_t5").start();
        new Thread(producer, "Producer_t6").start();
        //消费者线程
        new Thread(consumer, "Consumer_t1").start();
        new Thread(consumer, "Consumer_t2").start();
        new Thread(consumer, "Consumer_t3").start();
        new Thread(consumer, "Consumer_t4").start();

        System.out.println("main方法继续执行");
    }
}
