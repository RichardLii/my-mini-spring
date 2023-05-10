package com.example.beans.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * BeanFactory
 *
 * @author keemo 2023/5/10
 */
public class BeanFactory {

    private final Map<String, Object> beanMap = new HashMap<>();

    /**
     * 根据名称获取 bean
     *
     * @param beanName bean的名称
     * @return bean
     */
    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }

    /**
     * 注册 bean
     *
     * @param beanName bean的名称
     * @param bean     bean
     */
    public void registerBean(String beanName, Object bean) {
        beanMap.put(beanName, bean);
    }

}
