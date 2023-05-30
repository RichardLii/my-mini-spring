package com.example.beans.eventandlistener.multicaster;

import com.example.beans.BeansException;
import com.example.beans.aware.BeanFactoryAware;
import com.example.beans.eventandlistener.ApplicationEvent;
import com.example.beans.eventandlistener.ApplicationListener;
import com.example.beans.factory.BeanFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * 抽象事件广播中心
 *
 * @author keemo 2023/5/30
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new HashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
