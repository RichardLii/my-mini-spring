package com.example.beans.applicationcontext;

import com.example.beans.BeansException;
import com.example.beans.beandefinition.reader.XmlBeanDefinitionReader;
import com.example.beans.factory.AbstractBeanFactory;

/**
 * ClassPathXmlApplicationContext
 *
 * @author keemo 2023/5/26
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    /**
     * 文件路径
     */
    private final String location;

    public ClassPathXmlApplicationContext(String location) {
        this.location = location;
        refresh();
    }

    /**
     * 加载BeanDefinition
     *
     * @param beanFactory bean工厂
     */
    @Override
    protected void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws BeansException {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        beanDefinitionReader.loadBeanDefinitions(location);
    }
}
