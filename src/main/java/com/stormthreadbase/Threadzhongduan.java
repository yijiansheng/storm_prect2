package com.stormthreadbase;

/**
 * Author: changdalin
 * Date: 2017/9/12
 * Description:
 **/
public class Threadzhongduan {

    public static void main(String[] args) throws InterruptedException {
        inter1();
    }


    public static void inter1() {
        /**
         * date:2017/9/12
         * description:注意这样线程不会中断
         *             会一直执行下去
         *             catch不到，for循环不会抛出异常
         */
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    for (int i = 0; i < 1000000; i++) {
                        System.out.println(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        t.interrupt();
        System.out.println(t.isInterrupted());
    }

    public static void inter2() throws InterruptedException {
        /**
         * date:2017/9/12
         * description:如果t执行完了,main再中断，不会报错
         * 谁在执行，currentThread是谁
         */
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + "进入执行");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + "中断");
                }
                //会继续执行,中断不影响
                System.out.println(Thread.currentThread().getName() + "继续执行");
            }
        });
        t2.start();
        System.out.println(Thread.currentThread().getName() + "当前名称");
        Thread.sleep(9000);
        t2.interrupt();
    }
}
