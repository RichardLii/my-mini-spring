package com.example.beans.factory;

import com.example.beans.BeansException;
import com.example.beans.beandefinition.BeanDefinition;
import com.example.beans.beandefinition.BeanDefinitionRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * AbstractBeanFactory
 *
 * @author keemo 2023/5/10
 */
public abstract class AbstractBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    private final Map<String, Object> beanMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public Object getBean(String beanName) {
        Object bean = beanMap.get(beanName);

        if (bean == null) {
            // bean如果为null的话，则需要创建bean
            bean = createBean(getBeanDefinition(beanName));
            beanMap.put(beanName, bean);
        }

        return bean;
    }

    /**
     * 根据BeanDefinition信息创建bean，留给具体子类来实现
     *
     * @param beanDefinition beanDefinition
     * @return bean
     */
    protected abstract Object createBean(BeanDefinition beanDefinition) throws BeansException;

    /**
     * 获取 beanDefinition
     *
     * @param beanName bean名称
     * @return beanDefinition
     */
    private BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }

        return beanDefinition;
    }

}
