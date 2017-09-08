package com.stormthread.unsaf;


import sun.misc.Unsafe;

/**
 * Author: changdalin
 * Date: 2017/9/8
 * Description:
 **/
public class Node<E> {

    volatile int data;
    volatile Node<E> next;

    private static Unsafe unsafe;
    // 对应data在类里面的内存偏移量
    private static long itemOffset;
    // 对应next在类里面的内存偏移量
    private static long nextOffset;
    //不需要实例化，直接就可以获取偏移量

    static {
        try {
            unsafe = UnsafeSecond.getInstance();
            Class<?> k = Node.class;
            itemOffset = unsafe.objectFieldOffset(k.getDeclaredField("data"));
            nextOffset = unsafe.objectFieldOffset(k.getDeclaredField("next"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //构造方法，一般来看，是这样设置
//    public Node(E data) {
//        this.data = data;
//    }
    //操作地址，原子的改变
    public Node(E data) {
        //将data的引用值，放到itemOffset的偏移地址上
        unsafe.putObject(this, itemOffset, data);
    }

    public Node(int data) {
        this.data = data;
    }

    //val是新值，是要更新成的值
    public boolean casItem(E cmp, E val) {
        return unsafe.compareAndSwapObject(this, itemOffset, cmp, val);
    }

    public void setNormal() {
        this.data = data + 1;
    }



    public void setUnsafe() {
        int temp = data;
        // 最后一旦返回true,(temp+1)就进入了data的位置，data也就加了1
        while (unsafe.compareAndSwapInt(this, itemOffset, temp, temp + 1) == false) {
            //说明这个线程，不能修改data，它的temp，还比较小，比如是49，而实际已经到了50了
            //此时就返回false，那就将50给temp。
            //如果data又增到了51，那么还是不能改，继续尝试
            temp = data;
        }
        ;
    }


    public void print() {
        while (next != null) {
            System.out.println(data);
            next = next.next;
        }
    }

    public void printSelf() {
        System.out.println(data);
    }

    //类似于putObject，但是可见性低，其他线程过一会儿才可见
    public void lazySetNext(Node<E> val) {
        unsafe.putOrderedObject(this, nextOffset, val);
    }


    public boolean casNext(Node<E> cmp, Node<E> val) {
        return unsafe.compareAndSwapObject(this, nextOffset, cmp, val);
    }
}
