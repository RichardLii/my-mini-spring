package com.example.ioc;

import com.example.beans.applicationcontext.ClassPathXmlApplicationContext;
import com.example.testbean.Car;
import com.example.testbean.Person;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * ApplicationContextTest
 *
 * @author keemo 2023/5/26
 */
public class ApplicationContextTest {

    @Test
    public void testApplicationContext() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        Person person = applicationContext.getBean("person", Person.class);
        System.out.println(person);
        //name属性在CustomBeanFactoryPostProcessor中被修改为ivy
        assertThat(person.getName()).isEqualTo("ivy");

        Car car = applicationContext.getBean("car", Car.class);
        System.out.println(car);
        //brand属性在CustomerBeanPostProcessor中被修改为lamborghini
        assertThat(car.getBrand()).isEqualTo("lamborghini");
    }
}
