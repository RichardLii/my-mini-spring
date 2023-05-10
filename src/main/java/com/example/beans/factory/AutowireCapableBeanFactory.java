package com.example.beans.factory;

import com.example.beans.BeansException;
import com.example.beans.beandefinition.BeanDefinition;

/**
 * 可自动装配的BeanFactory
 *
 * @author keemo 2023/5/10
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * 根据BeanDefinition信息创建bean，留给具体子类来实现
     *
     * @param beanDefinition beanDefinition
     * @return bean
     */
    @Override
    protected Object createBean(BeanDefinition beanDefinition) throws BeansException {
        return doCreateBean(beanDefinition);
    }

    /**
     * 根据BeanDefinition信息创建bean
     *
     * @param beanDefinition beanDefinition
     * @return bean
     */
    private Object doCreateBean(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getClazz();

        Object bean = null;
        try {
            bean = beanClass.newInstance();
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        return bean;
    }
}
