package com.example.ioc;

import com.example.beans.beandefinition.reader.XmlBeanDefinitionReader;
import com.example.beans.factory.AutowireCapableBeanFactory;
import com.example.testbean.Car;
import com.example.testbean.Person;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 从XML文件读取BeanDefinition配置信息测试类
 *
 * @author keemo 2023/5/26
 */
public class XmlFileDefineBeanTest {

    @Test
    public void testXmlFile() throws Exception {
        AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        Person person = (Person) beanFactory.getBean("person");
        System.out.println(person);
        assertThat(person.getName()).isEqualTo("derek");
        assertThat(person.getCar().getBrand()).isEqualTo("porsche");

        Car car = (Car) beanFactory.getBean("car");
        System.out.println(car);
        assertThat(car.getBrand()).isEqualTo("porsche");
    }
}
