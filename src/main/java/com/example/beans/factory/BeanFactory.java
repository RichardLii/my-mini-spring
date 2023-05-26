package com.example.beans.factory;

import com.example.beans.beandefinition.BeanDefinition;

/**
 * BeanFactory
 *
 * @author keemo 2023/5/10
 */
public interface BeanFactory {

    /**
     * 根据名称获取 bean
     *
     * @param beanName bean的名称
     * @return bean
     */
    Object getBean(String beanName);

    /**
     * 获取BeanDefinition信息
     *
     * @param beanName bean名称
     * @return BeanDefinition信息
     */
    BeanDefinition getBeanDefinition(String beanName);

}
