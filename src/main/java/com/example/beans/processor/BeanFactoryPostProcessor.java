package com.example.beans.processor;

import com.example.beans.BeansException;
import com.example.beans.factory.BeanFactory;

/**
 * 允许自定义修改BeanDefinition的属性值
 *
 * @author keemo 2023/5/26
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有BeanDefinition加载完成后，但在bean实例化之前，提供修改BeanDefinition属性值的机制
     *
     * @param beanFactory bean工厂
     */
    void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;

}
