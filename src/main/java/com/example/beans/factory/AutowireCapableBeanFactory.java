package com.example.beans.factory;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.example.beans.BeansException;
import com.example.beans.aware.BeanFactoryAware;
import com.example.beans.beandefinition.BeanDefinition;
import com.example.beans.beandefinition.BeanReference;
import com.example.beans.beandefinition.PropertyValue;
import com.example.beans.beandefinition.initanddestroy.DisposableBean;
import com.example.beans.beandefinition.initanddestroy.DisposableBeanAdapter;
import com.example.beans.beandefinition.initanddestroy.InitializingBean;
import com.example.beans.processor.BeanPostProcessor;
import com.example.beans.strategy.InstantiationStrategy;
import com.example.beans.strategy.SimpleInstantiationStrategy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 可自动装配的BeanFactory
 *
 * @author keemo 2023/5/10
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    // 实例化策略
    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    /**
     * 根据BeanDefinition信息创建bean
     *
     * @param beanName       beanName
     * @param beanDefinition beanDefinition
     * @return bean
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return doCreateBean(beanName, beanDefinition);
    }

    /**
     * 根据BeanDefinition信息创建bean
     *
     * @param beanName       beanName
     * @param beanDefinition beanDefinition
     * @return bean
     */
    private Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean;

        try {
            // bean实例化 --> 创建bean实例，需要注意的是创建bean实例会调用类的无参构造器，所以类需要有无参的构造方法
            bean = createBeanInstance(beanDefinition);

            // 设置bean属性
            setBeanProperties(beanName, bean, beanDefinition);

            // bean初始化 --> 设置bean需要初始化的属性信息之类的
            bean = initializeBean(beanName, bean, beanDefinition);

        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 注册有销毁方法的bean
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

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
     * @param beanName       beanName
     * @param bean           bean
     * @param beanDefinition bean定义
     */
    private void setBeanProperties(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValueList()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                // 增加对BeanReference引用的识别
                if (value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }

                Field field = bean.getClass().getDeclaredField(name);
                field.setAccessible(true);
                field.set(bean, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values for bean: " + beanName, e);
        }
    }

    /**
     * 初始化bean
     *
     * @param beanName       beanName
     * @param bean           bean
     * @param beanDefinition beanDefinition
     * @return 返回包装后的bean
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 如果bean实现了BeanFactoryAware接口，那么在初始化bean的时候将会调用setBeanFactory()方法，将beanFactory传递给bean对象
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }

        // 执行BeanPostProcessor的前置处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 执行bean的初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Throwable ex) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", ex);
        }

        // 执行BeanPostProcessor的后置处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);

        return wrappedBean;
    }

    /**
     * bean初始化前执行bean后置处理器
     *
     * @param existingBean 待处理的bean
     * @param beanName     beanName
     * @return 返回包装后的bean
     */
    private Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;

        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }

        return result;
    }

    /**
     * 执行bean的初始化方法
     *
     * @param beanName       beanName
     * @param bean           bean
     * @param beanDefinition beanDefinition
     */
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Throwable {
        // 如果一个类既实现了InitializingBean接口，又在类的内部的定义了自定义初始化方法，那么将会执行两个步骤。

        // 1.调用InitializingBean接口的afterPropertiesSet()方法
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        // 2.调用bean自定义的initMethod
        String initMethodName = beanDefinition.getInitMethodName();

        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = ClassUtil.getPublicMethod(beanDefinition.getClazz(), initMethodName);
            if (initMethod == null) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
    }

    /**
     * bean初始化后执行bean后置处理器
     *
     * @param existingBean 待处理的bean
     * @param beanName     beanName
     * @return 返回包装后的bean
     */
    private Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;

        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }

        return result;
    }

    /**
     * 注册有销毁方法的bean，即bean继承自DisposableBean或有自定义的销毁方法
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 类实现了DisposableBean接口或者自定义销毁方法，则向bean工厂的销毁容器中注入销毁bean的包装类
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

}
