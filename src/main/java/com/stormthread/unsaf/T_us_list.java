package com.stormthread.unsaf;

/**
 * Author: changdalin
 * Date: 2017/9/8
 * Description:
 **/
public class T_us_list {
    public static void main(String[] args) throws InterruptedException {
        Node<Integer> root = new Node(0);
        NodeRunnable runnable = new NodeRunnable(root);
        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
        Thread.sleep(5000);
        root.printSelf();
    }
}
