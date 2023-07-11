package com.example.beans.processor;

import com.example.beans.BeansException;
import com.example.beans.beandefinition.BeanDefinition;
import com.example.beans.beandefinition.PropertyValue;
import com.example.beans.beandefinition.PropertyValues;
import com.example.beans.factory.BeanFactory;
import com.example.resource.DefaultResourceLoader;
import com.example.resource.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * 属性占位符配置器
 *
 * @author keemo 2023/7/11
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String PLACEHOLDER_PREFIX = "${";

    public static final String PLACEHOLDER_SUFFIX = "}";

    private String location;

    @Override
    public void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException {
        // 加载属性配置文件
        Properties properties = loadProperties();

        // 属性值替换占位符
        processProperties(beanFactory, properties);
    }

    /**
     * 加载属性配置文件
     *
     * @return 配置属性
     */
    private Properties loadProperties() {
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            return properties;
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    /**
     * 属性值替换占位符
     *
     * @param beanFactory bean工厂
     * @param properties  配置属性
     */
    private void processProperties(BeanFactory beanFactory, Properties properties) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            resolvePropertyValues(beanDefinition, properties);
        }
    }

    /**
     * 更改BeanDefinition的属性
     *
     * @param beanDefinition BeanDefinition
     * @param properties     配置属性
     */
    private void resolvePropertyValues(BeanDefinition beanDefinition, Properties properties) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValueList()) {
            Object value = propertyValue.getValue();
            if (value instanceof String) {
                //TODO 仅简单支持一个占位符的格式
                String strVal = (String) value;
                StringBuffer buf = new StringBuffer(strVal);
                int startIndex = strVal.indexOf(PLACEHOLDER_PREFIX);
                int endIndex = strVal.indexOf(PLACEHOLDER_SUFFIX);
                if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                    String propKey = strVal.substring(startIndex + 2, endIndex);
                    String propVal = properties.getProperty(propKey);
                    buf.replace(startIndex, endIndex + 1, propVal);
                    propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), buf.toString()));
                }
            }
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
