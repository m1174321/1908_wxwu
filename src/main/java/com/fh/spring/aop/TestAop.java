package com.fh.spring.aop;

import com.fh.spring.entity.Dog;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAop {

    @Test
    public void test2(){
        ClassPathXmlApplicationContext beans=new ClassPathXmlApplicationContext("applicationContext.xml");
        Dog dog = (Dog) beans.getBean("dog");
        dog.wangbadan();// 狗咬人  之前放开狗
    }

}
