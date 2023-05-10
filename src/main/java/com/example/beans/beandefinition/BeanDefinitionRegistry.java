package com.example.beans.beandefinition;

/**
 * BeanDefinition注册表
 *
 * @author keemo 2023/5/10
 */
public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注BeanDefinition
     *
     * @param beanName       bean名称
     * @param beanDefinition beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

}
