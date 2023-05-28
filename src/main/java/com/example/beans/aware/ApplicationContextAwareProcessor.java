package com.example.beans.aware;

import com.example.beans.BeansException;
import com.example.beans.applicationcontext.ApplicationContext;
import com.example.beans.processor.BeanPostProcessor;

/**
 * 应用上下文感知处理器类。该类的作用是如果bean实现了ApplicationContextAware接口，那么在初始化应用上下文的时候会将一个
 * ApplicationContextAwareProcessor对象添加到处理器集合中，因此就能调用ApplicationContextAwareProcessor.postProcessBeforeInitialization()方法，
 * 从而将ApplicationContext对象传递给bean对象
 *
 * @author keemo 2023/5/28
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
