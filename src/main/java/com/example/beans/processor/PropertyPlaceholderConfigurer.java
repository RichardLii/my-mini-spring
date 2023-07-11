package com.example.beans.processor;

import com.example.beans.BeansException;
import com.example.beans.beandefinition.BeanDefinition;
import com.example.beans.beandefinition.PropertyValue;
import com.example.beans.beandefinition.PropertyValues;
import com.example.beans.factory.BeanFactory;
import com.example.resource.DefaultResourceLoader;
import com.example.resource.Resource;
import com.example.util.StringValueResolver;

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

        // 属性值替换占位符（这一步是替换XML中读取到的BeanDefinition的字段中的占位符）
        processProperties(beanFactory, properties);

        // 往容器中添加字符解析器，供解析@Value注解使用
        StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
        beanFactory.addEmbeddedValueResolver(valueResolver);
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
                value = resolvePlaceholder((String) value, properties);
                propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), value));
            }
        }
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        public String resolveStringValue(String strVal) throws BeansException {
            return resolvePlaceholder(strVal, properties);
        }
    }

    /**
     * 解析占位符
     *
     * @param value      占位符
     * @param properties 配置属性
     * @return 占位符对应的属性
     */
    private String resolvePlaceholder(String value, Properties properties) {
        //TODO 仅简单支持一个占位符的格式
        StringBuffer buf = new StringBuffer(value);

        int startIndex = value.indexOf(PLACEHOLDER_PREFIX);
        int endIndex = value.indexOf(PLACEHOLDER_SUFFIX);

        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            String propKey = value.substring(startIndex + 2, endIndex);
            String propVal = properties.getProperty(propKey);
            buf.replace(startIndex, endIndex + 1, propVal);
        }

        return buf.toString();
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
