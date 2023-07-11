package com.example.expanding;

import com.example.beans.applicationcontext.ClassPathXmlApplicationContext;
import com.example.testbean.Car;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 属性占位符配置器测试类
 *
 * @author keemo 2023/7/11
 */
public class PropertyPlaceholderConfigurerTest {

    @Test
    public void test() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:property-placeholder-configurer.xml");

        Car car = applicationContext.getBean("car", Car.class);
        assertThat(car.getBrand()).isEqualTo("lamborghini");
    }
}
