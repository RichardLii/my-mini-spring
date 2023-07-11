package com.example.aop.proxy;

import com.example.aop.aspectj.Advisor;
import com.example.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.example.aop.aspectj.ClassFilter;
import com.example.aop.aspectj.Pointcut;
import com.example.beans.BeansException;
import com.example.beans.aware.BeanFactoryAware;
import com.example.beans.beandefinition.BeanDefinition;
import com.example.beans.beandefinition.PropertyValues;
import com.example.beans.factory.AutowireCapableBeanFactory;
import com.example.beans.factory.BeanFactory;
import com.example.beans.processor.InstantiationAwareBeanPostProcessor;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

/**
 * 默认增强代理创建器
 *
 * @author keemo 2023/7/11
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private AutowireCapableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        //避免死循环
        if (isInfrastructureClass(beanClass)) {
            return null;
        }

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        try {
            for (AspectJExpressionPointcutAdvisor advisor : advisors) {
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                // Todo 此时只支持单切面的增强
                if (classFilter.matches(beanClass)) {
                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                    Object bean = beanFactory.getInstantiationStrategy().instantiate(beanDefinition);

                    AdvisedSupport advisedSupport = new AdvisedSupport();
                    advisedSupport.setTargetSource(new TargetSource(bean));
                    advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                    advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

                    //返回代理对象
                    return new ProxyFactory(advisedSupport).getProxy();
                }
            }
        } catch (Exception ex) {
            throw new BeansException("Error create proxy bean for: " + beanName, ex);
        }

        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (AutowireCapableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
