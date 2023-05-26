package com.example.beans.beandefinition.reader;

import com.example.beans.BeansException;
import com.example.beans.beandefinition.BeanDefinitionRegistry;
import com.example.resource.ResourceLoader;

/**
 * BeanDefinitionReader  BeanDefinition读取
 *
 * @author keemo 2023/5/26
 */
public interface BeanDefinitionReader {

    /**
     * 获取bean注册表，用于将从文件读取出的BeanDefinition信息注册到bean工厂中（也就是将值填充到Map）
     *
     * @return bean注册表
     */
    BeanDefinitionRegistry getBeanDefinitionRegistry();

    /**
     * 获取资源加载器，可以根据文件位置加载资源
     *
     * @return 资源加载器
     */
    ResourceLoader getResourceLoader();

    /**
     * 根据位置加载bean的定义信息
     *
     * @param location 文件位置
     */
    void loadBeanDefinitions(String location) throws BeansException;

}
