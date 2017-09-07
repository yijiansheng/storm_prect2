package com.stormthread.volat;

/**
 * Author: changdalin
 * Date: 2017/9/7
 * Description:
 **/
public class T_vola_main {
    public static void main(String[] args) {
    //    T_vola_increase runnable = new T_vola_increase();
        T_threadlocal runnable = new T_threadlocal();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}