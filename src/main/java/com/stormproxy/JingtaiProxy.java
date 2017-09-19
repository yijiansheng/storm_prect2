package com.stormproxy;

/**
 * Author: changdalin
 * Date: 2017/9/14
 * Description:
 **/
public class JingtaiProxy {
    public static void main(String[] args) {
        //真正执行的
        RealSubject subject = new RealSubject();
        //代理的，代理的塞进了real
        //原因是代理也实现了interface接口
        JTProxy p = new JTProxy(subject);
        p.request();
    }
}

interface Subject {
    void request();
}
class RealSubject implements Subject {
    public void request(){
        System.out.println("RealSubject");
    }
}

class JTProxy implements Subject {
    private Subject subject;

    public JTProxy(Subject subject){
        this.subject = subject;
    }
    public void request(){
        System.out.println("begin");
        subject.request();
        System.out.println("end");
    }
}