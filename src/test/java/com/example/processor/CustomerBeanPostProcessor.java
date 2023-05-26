package com.example.processor;

import com.example.beans.BeansException;
import com.example.beans.processor.BeanPostProcessor;
import com.example.testbean.Car;

/**
 * 自定义bean后置处理器
 *
 * @author keemo 2023/5/26
 */
public class CustomerBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("CustomerBeanPostProcessor# postProcessBeforeInitialization");

        //换兰博基尼
        if ("car".equals(beanName)) {
            ((Car) bean).setBrand("lamborghini");
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("CustomerBeanPostProcessor# postProcessAfterInitialization");
        return bean;
    }

}
