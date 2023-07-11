package com.example.beans.processor;

import com.example.beans.BeansException;

/**
 * 用于修改实例化后的bean的修改扩展点
 *
 * @author keemo 2023/5/26
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 在bean实例化之前执行此方法
     *
     * @param beanClass beanClass
     * @param beanName  bean名称
     * @return 返回修改过后的bean实例
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}
