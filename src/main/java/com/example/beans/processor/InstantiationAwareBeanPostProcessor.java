package com.example.beans.processor;

import com.example.beans.BeansException;
import com.example.beans.beandefinition.PropertyValues;

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

    /**
     * bean实例化之后，设置属性之前执行
     *
     * @param bean     bean
     * @param beanName beanName
     * @return true：继续填充bean的属性  false：不需要填充bean的属性 ，默认情况下应该返回true
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * bean实例化之后，设置属性之前执行
     *
     * @param pvs      bean属性
     * @param bean     bean
     * @param beanName bean名称
     * @return bean属性
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;
}
