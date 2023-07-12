package com.example.beans.factory;

import com.example.beans.BeansException;
import com.example.beans.beandefinition.BeanDefinition;
import com.example.beans.beandefinition.BeanDefinitionRegistry;
import com.example.beans.beandefinition.initanddestroy.DisposableBean;
import com.example.beans.factorybean.FactoryBean;
import com.example.beans.processor.BeanPostProcessor;
import com.example.util.StringValueResolver;

import java.util.*;

/**
 * AbstractBeanFactory
 *
 * @author keemo 2023/5/10
 */
public abstract class AbstractBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    private final Map<String, Object> beanMap = new HashMap<>();

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    // FactoryBean的缓存
    private final Map<String, Object> factoryBeanObjectCache = new HashMap<>();

    // 嵌入式值解析器
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<StringValueResolver>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    /**
     * 销毁单例bean
     */
    public void destroySingletons() {
        ArrayList<String> beanNames = new ArrayList<>(disposableBeans.keySet());

        for (String beanName : beanNames) {
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    /**
     * 根据bean名称获取bean
     *
     * @param beanName bean的名称
     * @return bean
     */
    @Override
    public Object getBean(String beanName) {
        Object bean = beanMap.get(beanName);

        if (bean == null) {
            // bean如果为null的话，则需要创建bean
            bean = createBean(beanName, getBeanDefinition(beanName));
            beanMap.put(beanName, bean);
        }


        return getObjectForBeanInstance(bean, beanName);
    }

    /**
     * 获取bean实例，封装了对FactoryBean类型bean的处理
     *
     * @param beanInstance bean实例
     * @param beanName     bean名称
     * @return 返回bean实例
     */
    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        Object result = beanInstance;

        if (beanInstance instanceof FactoryBean) {
            FactoryBean factoryBean = (FactoryBean) beanInstance;
            try {
                // 如果bean是FactoryBean类型，那么会返回它的getObject()方法的结果而不是bean本身
                result = this.factoryBeanObjectCache.get(beanName);
                if (result == null) {
                    result = factoryBean.getObject();
                    this.factoryBeanObjectCache.put(beanName, result);
                }
            } catch (Exception ex) {
                throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", ex);
            }
        }

        return result;
    }

    /**
     * 获取指定类型的bean
     *
     * @param requiredType 需要的类型
     * @return 返回bean
     */
    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        List<String> beanNames = new ArrayList<>();

        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            if (requiredType.isAssignableFrom(beanDefinition.getClazz())) {
                beanNames.add(beanName);
            }
        });

        if (beanNames.size() != 1) {
            throw new BeansException(requiredType + "expected single bean but found " + beanNames.size() + ": " + beanNames);
        }

        return getBean(beanNames.get(0), requiredType);
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
        return (T) getBean(beanName);
    }

    /**
     * 获取指定类型的所有bean
     *
     * @param type 指定类型
     * @return 返回所有符合的bean
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new HashMap<>();

        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class<?> beanClass = beanDefinition.getClazz();
            if (type.isAssignableFrom(beanClass)) {
                T bean = (T) getBean(beanName);
                result.put(beanName, bean);
            }
        });

        return result;
    }

    /**
     * 根据BeanDefinition信息创建bean，留给具体子类来实现
     *
     * @param beanName       beanName
     * @param beanDefinition beanDefinition
     * @return bean
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    /**
     * 获取 beanDefinition
     *
     * @param beanName bean名称
     * @return beanDefinition
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }

        return beanDefinition;
    }

    /**
     * 返回定义的所有bean的名称
     *
     * @return 所有bean的名称
     */
    @Override
    public String[] getBeanDefinitionNames() {
        Set<String> beanNames = beanDefinitionMap.keySet();
        return beanNames.toArray(new String[0]);
    }

    /**
     * 添加值解析器
     *
     * @param valueResolver 字符串值解析器
     */
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    /**
     * 解析值
     *
     * @param value 字符串值
     * @return 解析后的字符串值
     */
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }

    /**
     * 获取BeanPostProcessor
     *
     * @return BeanPostProcessor
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    /**
     * 添加bean后置处理器
     *
     * @param beanPostProcessor bean后置处理器
     */
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        //有则覆盖
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public Map<String, Object> getBeanMap() {
        return beanMap;
    }

}
