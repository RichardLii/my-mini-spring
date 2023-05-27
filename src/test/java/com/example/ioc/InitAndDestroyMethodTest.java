package com.example.ioc;

import com.example.beans.applicationcontext.ClassPathXmlApplicationContext;
import com.example.testbean.Person;
import org.junit.Test;

/**
 * InitAndDestroyMethodTest
 *
 * @author keemo 2023/5/27
 */
public class InitAndDestroyMethodTest {

    @Test
    public void testInitAndDestroyMethod() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:init-and-destroy-method.xml");

        Person person = applicationContext.getBean("person", Person.class);
        System.out.println(person);

        // 或者手动关闭 applicationContext.close();
        applicationContext.registerShutdownHook();
    }
}
