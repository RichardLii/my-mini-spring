package com.example.beans.strategy;


import com.example.beans.BeansException;
import com.example.beans.beandefinition.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 简单实例化策略
 *
 * @author keemo 2023/5/10
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    /**
     * 实例化bean
     *
     * @param beanDefinition beanDefinition
     * @return bean
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        Class<?> beanClass = beanDefinition.getClazz();
        try {
            Constructor<?> constructor = beanClass.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new BeansException("Failed to instantiate [" + beanClass.getName() + "]", e);
        }
    }
}
