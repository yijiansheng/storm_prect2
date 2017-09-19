package com.stormproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author: changdalin
 * Date: 2017/9/15
 * Description:
 **/
interface Service {
    void add();
}

/**
 * date:2017/9/15
 * description:真正实现类
 */
class UserServiceImpl implements Service {
    public void add() {
        System.out.println("This is add service");
    }
}

/**
 * date:2017/9/15
 * description:代理类
 */
class MyInvocatioHandler implements InvocationHandler {
    private Object target;

    public MyInvocatioHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("-----before-----");
        Object result = method.invoke(target, args);
        System.out.println("-----end-----");
        return result;
    }


    // 生成代理对象
    public Object getProxy() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        /**
         * date:2017/9/15
         * description:这个
         */
        Class<?>[] interfaces = target.getClass().getInterfaces();
        Object o = Proxy.newProxyInstance(loader, interfaces, this);
        return o;
    }
}



public class DongtaiProxy {
    public static void main(String[] args) {
        //真正实现类
        UserServiceImpl u = new UserServiceImpl();
        /**
         * date:2017/9/15
         * description:代理套进去service，生成新的代理对象
         * 这个代理对象有很多功能
         */
        MyInvocatioHandler handler = new MyInvocatioHandler(u);
        Service serviceProxy = (Service) handler.getProxy();
        serviceProxy.add();
    }
}