package com.example.beans.strategy;

import com.example.beans.BeansException;
import com.example.beans.beandefinition.BeanDefinition;

/**
 * 实例化策略
 *
 * @author keemo 2023/5/10
 */
public interface InstantiationStrategy {

    /**
     * 实例化bean
     *
     * @param beanDefinition beanDefinition
     * @return bean
     */
    Object instantiate(BeanDefinition beanDefinition) throws BeansException;

}
