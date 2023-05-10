package com.example.beans.factory;

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

}
