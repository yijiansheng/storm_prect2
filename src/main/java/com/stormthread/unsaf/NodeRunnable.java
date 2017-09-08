package com.stormthread.unsaf;

/**
 * Author: changdalin
 * Date: 2017/9/8
 * Description:
 **/
public class NodeRunnable implements Runnable {
    private Node<Integer> root;

    NodeRunnable(Node node) {
        this.root = node;
    }

    public void run() {
        for (int i = 0; i < 50000; i++) {
            root.setNormal();
            //root.setUnsafe();
        }
    }
}
