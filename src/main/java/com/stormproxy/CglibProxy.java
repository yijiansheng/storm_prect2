package com.stormproxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Author: changdalin
 * Date: 2017/9/19
 * Description:
 **/

/**
 * date:2017/9/19
 * description:具体实现类
 */
class ServiceImpl {
    public void add() {
        System.out.println("This is add service");
    }

    public void delete(int id) {
        System.out.println("This is delete service：delete " + id);
    }
}

/**
 * date:2017/9/19
 * description:拦截器
 */
class MyMethodInterceptor implements MethodInterceptor {

    public Object intercept(Object obj, Method method, Object[] arg, MethodProxy proxy) throws Throwable {
        System.out.println("Before:" + method);
        Object object = proxy.invokeSuper(obj, arg);
        System.out.println("After:" + method);
        return object;
    }
}

public class CglibProxy {
    /**
     * date:2017/9/19
     * description:生成靠这个enhancer
     */
    public static Object createProxy() {
        Enhancer enhancer = new Enhancer();
        //需要这个类的class二进制字节码
        enhancer.setSuperclass(ServiceImpl.class);
        enhancer.setCallback(new MyMethodInterceptor());
        Object o = enhancer.create();
        return o;
    }

    public static void main(String[] args) {
        ServiceImpl proxy = (ServiceImpl)createProxy();
        proxy.add();
    }
}