package com.example.processor;

import com.example.beans.BeansException;
import com.example.beans.beandefinition.BeanDefinition;
import com.example.beans.beandefinition.PropertyValue;
import com.example.beans.beandefinition.PropertyValues;
import com.example.beans.factory.BeanFactory;
import com.example.beans.processor.BeanFactoryPostProcessor;

/**
 * 自定义bean工厂后置处理器
 *
 * @author keemo 2023/5/26
 */
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException {
        BeanDefinition personBeanDefinition = beanFactory.getBeanDefinition("person");
        PropertyValues propertyValues = personBeanDefinition.getPropertyValues();
        //将person的name属性改为ivy
        propertyValues.addPropertyValue(new PropertyValue("name", "ivy"));
    }
}
