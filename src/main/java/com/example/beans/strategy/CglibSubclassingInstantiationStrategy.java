package com.example.beans.strategy;

import com.example.beans.BeansException;
import com.example.beans.beandefinition.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * Cglib 实例化策略
 *
 * @author keemo 2023/5/10
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

    /**
     * 实例化bean
     *
     * @param beanDefinition beanDefinition
     * @return bean
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getClazz());
        enhancer.setCallback((MethodInterceptor) (obj, method, argsTemp, proxy) -> proxy.invokeSuper(obj, argsTemp));

        return enhancer.create();
    }
}
