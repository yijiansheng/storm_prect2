package com.storm_gc;

/**
 * Author: changdalin
 * Date: 2017/11/27
 * Description:
 **/
class User implements Cloneable{
    String name;
    int age;

    @Override
    public User clone() throws CloneNotSupportedException {
        return (User) super.clone();
    }
}

class Account implements Cloneable {
    User user;
    long balance;
    String s;

    @Override
    public Object clone() throws CloneNotSupportedException {
        Account account = null;

        account = (Account) super.clone();
        if (user != null) {
            //这就是一个新对象了u2的name和其他都指向了同一块内存地址，比如u1.name 和 u2.name 是同一个地址
            User userClone =  user.clone();
            account.user = userClone;
        }

        return account;
    }
}

public class TestClone {
    public static void main(String[] args) throws Exception{
        User user = new User();
        user.name = "user";
        user.age = 20;

        Account obj1 = new Account();
        obj1.user = user;
        obj1.balance = 10000;
        obj1.s = "123";
        //那么 clone之后是两个对象,obj1 和 obj2 是不同
        Account obj2 = (Account) obj1.clone();
        System.out.println(obj1 == obj2);
        //浅copy obj1.user 这个ox 和 obj2.user 这个ox地址是相同的
        //深copy 就是两个对象了
        System.out.println(obj1.user == obj2.user);
        System.out.println(obj1.s == obj2.s);
        System.out.println(obj1.user.name == obj2.user.name);
    }
}