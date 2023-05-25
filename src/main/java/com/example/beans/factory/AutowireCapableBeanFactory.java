package com.example.beans.factory;

import com.example.beans.BeansException;
import com.example.beans.beandefinition.BeanDefinition;
import com.example.beans.beandefinition.PropertyValue;
import com.example.beans.strategy.InstantiationStrategy;
import com.example.beans.strategy.SimpleInstantiationStrategy;

import java.lang.reflect.Field;

/**
 * 可自动装配的BeanFactory
 *
 * @author keemo 2023/5/10
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    // 实例化策略
    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

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
        Object bean;

        try {
            // 创建bean实例
            bean = createBeanInstance(beanDefinition);

            // 设置bean属性
            setBeanProperties(bean, beanDefinition);

        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        return bean;
    }

    /**
     * 创建bean实例
     *
     * @param beanDefinition bean定义
     * @return 返回bean实例
     */
    private Object createBeanInstance(BeanDefinition beanDefinition) {
        return getInstantiationStrategy().instantiate(beanDefinition);
    }

    /**
     * 设置bean属性
     *
     * @param bean           bean
     * @param beanDefinition bean定义
     */
    private void setBeanProperties(Object bean, BeanDefinition beanDefinition) {
        for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValueList()) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();

            try {
                Field field = bean.getClass().getDeclaredField(name);
                field.setAccessible(true);
                field.set(bean, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

}
