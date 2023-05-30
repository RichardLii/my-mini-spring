package com.example.beans.applicationcontext;

import com.example.beans.BeansException;
import com.example.beans.aware.ApplicationContextAwareProcessor;
import com.example.beans.beandefinition.BeanDefinition;
import com.example.beans.eventandlistener.ApplicationEvent;
import com.example.beans.eventandlistener.ApplicationListener;
import com.example.beans.eventandlistener.event.ContextClosedEvent;
import com.example.beans.eventandlistener.event.ContextRefreshedEvent;
import com.example.beans.eventandlistener.multicaster.ApplicationEventMulticaster;
import com.example.beans.eventandlistener.multicaster.SimpleApplicationEventMulticaster;
import com.example.beans.factory.AbstractBeanFactory;
import com.example.beans.factory.AutowireCapableBeanFactory;
import com.example.beans.processor.BeanFactoryPostProcessor;
import com.example.beans.processor.BeanPostProcessor;

import java.util.Collection;
import java.util.Map;

/**
 * AbstractApplicationContext
 *
 * @author keemo 2023/5/26
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private AbstractBeanFactory beanFactory;

    /**
     * 事件广播中心
     */
    private ApplicationEventMulticaster applicationEventMulticaster;

    /**
     * 刷新容器
     */
    @Override
    public void refresh() throws BeansException {
        // 刷新bean工厂 --> 创建bean工厂，加载BeanDefinition信息，将创建后的工厂赋值给属性
        refreshBeanFactory();

        // 添加ApplicationContextAwareProcessor，让实现ApplicationContextAware的bean能感知到应用上下文
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 在bean实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        //BeanPostProcessor需要提前与其他bean实例化之前注册
        registerBeanPostProcessors(beanFactory);

        // 初始化事件发布者
        initApplicationEventMulticaster();

        // 注册事件监听器
        registerListeners();

        // 发布容器刷新完成事件
        finishRefresh();
    }

    /**
     * 关闭应用上下文
     */
    @Override
    public void close() {
        doClose();
    }

    /**
     * 向虚拟机中注册一个钩子方法，在虚拟机关闭之前执行关闭容器等操作
     */
    @Override
    public void registerShutdownHook() {
        Thread shutdownHook = new Thread(this::doClose);

        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    /**
     * 发布事件
     *
     * @param event 事件
     */
    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    private void doClose() {
        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        // 执行bean的销毁方法
        destroyBeans();
    }

    private void destroyBeans() {
        beanFactory.destroySingletons();
    }

    /**
     * 刷新bean工厂
     */
    private void refreshBeanFactory() throws BeansException {
        AbstractBeanFactory beanFactory = new AutowireCapableBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * 在bean实例化之前，执行BeanFactoryPostProcessor
     *
     * @param beanFactory beanFactory
     */
    private void invokeBeanFactoryPostProcessors(AbstractBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);

        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 注册BeanPostProcessor
     *
     * @param beanFactory beanFactory
     */
    private void registerBeanPostProcessors(AbstractBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);

        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }


    /**
     * 初始化事件发布者
     */
    private void initApplicationEventMulticaster() {
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(this.beanFactory);

        beanFactory.getBeanMap().put(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    /**
     * 注册事件监听器
     */
    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();

        for (ApplicationListener applicationListener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(applicationListener);
        }
    }

    /**
     * 发布容器刷新完成事件
     */
    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }


    /**
     * 加载BeanDefinition
     *
     * @param beanFactory bean工厂
     */
    protected abstract void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws BeansException;

    /**
     * 根据bean名称获取bean
     *
     * @param beanName bean的名称
     * @return bean
     */
    @Override
    public Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }

    /**
     * 获取指定类型的bean
     *
     * @param beanName     beanName
     * @param requiredType 需要的类型
     * @return 返回bean
     */
    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return beanFactory.getBean(beanName, requiredType);
    }

    /**
     * 获取指定类型的所有bean
     *
     * @param type 指定类型
     * @return 返回所有符合的bean
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return beanFactory.getBeansOfType(type);
    }

    /**
     * 获取 beanDefinition
     *
     * @param beanName bean名称
     * @return beanDefinition
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanFactory.getBeanDefinition(beanName);
    }

}
