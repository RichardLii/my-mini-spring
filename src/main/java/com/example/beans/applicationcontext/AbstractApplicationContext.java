package com.example.beans.applicationcontext;

import com.example.beans.BeansException;
import com.example.beans.aware.ApplicationContextAwareProcessor;
import com.example.beans.beandefinition.BeanDefinition;
import com.example.beans.factory.AbstractBeanFactory;
import com.example.beans.factory.AutowireCapableBeanFactory;
import com.example.beans.processor.BeanFactoryPostProcessor;
import com.example.beans.processor.BeanPostProcessor;

import java.util.Map;

/**
 * AbstractApplicationContext
 *
 * @author keemo 2023/5/26
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private AbstractBeanFactory beanFactory;

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

    private void doClose() {
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
