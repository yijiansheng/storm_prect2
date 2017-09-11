package com.stormthread.futuretask;

import java.util.concurrent.Callable;


/**
 * Author: changdalin
 * Date: 2017/9/8
 * Description:理解成runnble，待执行的
 **/
public class T_ft_CallRunnable implements Callable<Integer> {

    private int result;

    public Integer call() throws Exception {
        long l = (long) (Math.random() * 10000);
        System.out.println(Thread.currentThread().getName() + "  进入执行，并且sleep" + l + "毫秒");
        Thread.sleep(l);
        System.out.println(Thread.currentThread().getName() + "  sleep完毕");
        int sum = 0;
        for (int i = 0; i < 10000; i++)
            sum += i;
        result = sum;
        System.out.println(Thread.currentThread().getName() + "  计算完毕");
        return result;
    }
}