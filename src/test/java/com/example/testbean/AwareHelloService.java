package com.example.testbean;

import com.example.beans.BeansException;
import com.example.beans.applicationcontext.ApplicationContext;
import com.example.beans.aware.ApplicationContextAware;
import com.example.beans.aware.BeanFactoryAware;
import com.example.beans.factory.BeanFactory;

/**
 * HelloService
 *
 * @author keemo 2023/5/10
 */
public class AwareHelloService implements BeanFactoryAware, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private BeanFactory beanFactory;


    public void sayHello() {
        System.out.println("Halo");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
