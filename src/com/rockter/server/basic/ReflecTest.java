package com.rockter.server.basic;

import java.lang.reflect.InvocationTargetException;

class Phone {
    public Phone(){
    }
}


public class ReflecTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        //1.对象.getClass()
        Phone phone = new Phone();
        Class clz = new Phone().getClass();
        //2.类.class()
        clz = Phone.class;
        //3.Class.forName("Package.classname")
        clz = Class.forName("com.rockter.server.basic.Phone");
        Phone phone1 = (Phone) clz.getConstructor().newInstance();
        System.out.println(phone1);
    }
}
