package com.example.beans.processor;

import com.example.beans.BeansException;


/**
 * 用于修改实例化后的bean的修改扩展点
 *
 * @author keemo 2023/5/26
 */
public interface BeanPostProcessor {

    /**
     * 在bean执行初始化方法之前执行此方法
     *
     * @param bean     bean实例
     * @param beanName bean名称
     * @return 返回修改过后新的bean实例
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在bean执行初始化方法之后执行此方法
     *
     * @param bean     bean实例
     * @param beanName bean名称
     * @return 返回修改过后新的bean实例
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
