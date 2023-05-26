package com.example.beans.factory;

import com.example.beans.BeansException;
import com.example.beans.beandefinition.BeanDefinition;

import java.util.Map;

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
     * 获取指定类型的bean
     *
     * @param beanName     beanName
     * @param requiredType 需要的类型
     * @return 返回bean
     */
    <T> T getBean(String beanName, Class<T> requiredType) throws BeansException;

    /**
     * 获取指定类型的所有bean
     *
     * @param type 指定类型
     * @return 返回所有符合的bean
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 获取BeanDefinition信息
     *
     * @param beanName bean名称
     * @return BeanDefinition信息
     */
    BeanDefinition getBeanDefinition(String beanName);

}
