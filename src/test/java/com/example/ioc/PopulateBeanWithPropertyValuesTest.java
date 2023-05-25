package com.example.ioc;

import com.example.beans.beandefinition.BeanDefinition;
import com.example.beans.beandefinition.PropertyValue;
import com.example.beans.beandefinition.PropertyValues;
import com.example.beans.factory.AutowireCapableBeanFactory;
import com.example.testbean.Person;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * bean属性填充测试
 *
 * @author keemo 2023/5/10
 */
public class PopulateBeanWithPropertyValuesTest {

    @Test
    public void testGetBean() {
        AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();

        // 创建bean并填充属性
        PropertyValues propertyValues = new PropertyValues();
        List<PropertyValue> propertyValueList = propertyValues.getPropertyValueList();
        propertyValueList.add(new PropertyValue("name", "zhangsan"));
        propertyValueList.add(new PropertyValue("age", 18));

        BeanDefinition beanDefinition = new BeanDefinition(Person.class, propertyValues);
        beanFactory.registerBeanDefinition("person", beanDefinition);

        // 获取bean实例
        Person person = (Person) beanFactory.getBean("person");
        System.out.println(person);
        assertThat(person.getName()).isEqualTo("zhangsan");
        assertThat(person.getAge()).isEqualTo(18);
    }

}
