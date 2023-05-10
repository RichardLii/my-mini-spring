package com.example.beans.factory;

import com.example.beans.BeansException;
import com.example.beans.beandefinition.BeanDefinition;
import com.example.beans.strategy.InstantiationStrategy;
import com.example.beans.strategy.SimpleInstantiationStrategy;

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
            bean = getInstantiationStrategy().instantiate(beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        return bean;
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

}
