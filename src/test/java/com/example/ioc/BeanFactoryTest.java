package com.example.ioc;

import com.example.beans.beandefinition.BeanDefinition;
import com.example.beans.factory.AutowireCapableBeanFactory;
import com.example.testbean.HelloService;
import org.junit.Test;

/**
 * BeanFactoryTest
 *
 * @author keemo 2023/5/10
 */
public class BeanFactoryTest {

    @Test
    public void testGetBean() {
        AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
        beanFactory.registerBeanDefinition("helloService", beanDefinition);

        HelloService helloService = (HelloService) beanFactory.getBean("helloService");
        helloService.sayHello();
    }

}
