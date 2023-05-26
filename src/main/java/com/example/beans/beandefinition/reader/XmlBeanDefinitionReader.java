package com.example.beans.beandefinition.reader;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.example.beans.BeansException;
import com.example.beans.beandefinition.BeanDefinition;
import com.example.beans.beandefinition.BeanDefinitionRegistry;
import com.example.beans.beandefinition.BeanReference;
import com.example.beans.beandefinition.PropertyValue;
import com.example.resource.Resource;
import com.example.resource.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 * XmlBeanDefinitionReader
 *
 * @author keemo 2023/5/26
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    /**
     * 根据文件位置加载BeanDefinition
     *
     * @param location 文件位置
     */
    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    /**
     * 根据资源加载BeanDefinition
     *
     * @param resource 资源
     */
    private void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            try (InputStream inputStream = resource.getInputStream()) {
                doLoadBeanDefinitions(inputStream);
            }
        } catch (IOException ex) {
            throw new BeansException("IOException parsing XML document from " + resource, ex);
        }
    }

    /**
     * 根据输入流加载BeanDefinition
     *
     * @param inputStream 输入流信息
     */
    protected void doLoadBeanDefinitions(InputStream inputStream) {
        Document document = XmlUtil.readXML(inputStream);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i) instanceof Element && BEAN_ELEMENT.equals(childNodes.item(i).getNodeName())) {
                // 解析bean标签
                Element bean = (Element) childNodes.item(i);
                String id = bean.getAttribute(ID_ATTRIBUTE);
                String name = bean.getAttribute(NAME_ATTRIBUTE);
                String className = bean.getAttribute(CLASS_ATTRIBUTE);

                // 根据类名创建bean对象
                Class<?> clazz;
                try {
                    clazz = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    throw new BeansException("Cannot find class [" + className + "]");
                }

                // id优先于name
                String beanName = StrUtil.isNotEmpty(id) ? id : name;
                if (StrUtil.isEmpty(beanName)) {
                    // 如果id和name都为空，将类名的第一个字母转为小写后作为bean的名称
                    beanName = StrUtil.lowerFirst(clazz.getSimpleName());
                }

                // 创建BeanDefinition，并填充属性信息
                BeanDefinition beanDefinition = new BeanDefinition(clazz);

                for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                    if (bean.getChildNodes().item(j) instanceof Element && PROPERTY_ELEMENT.equals(bean.getChildNodes().item(j).getNodeName())) {
                        // 解析property标签
                        Element property = (Element) bean.getChildNodes().item(j);
                        String nameAttribute = property.getAttribute(NAME_ATTRIBUTE);
                        String valueAttribute = property.getAttribute(VALUE_ATTRIBUTE);
                        String refAttribute = property.getAttribute(REF_ATTRIBUTE);

                        if (StrUtil.isEmpty(nameAttribute)) {
                            throw new BeansException("The name attribute cannot be null or empty");
                        }

                        Object value = valueAttribute;
                        if (StrUtil.isNotEmpty(refAttribute)) {
                            value = new BeanReference(refAttribute);
                        }
                        PropertyValue propertyValue = new PropertyValue(nameAttribute, value);
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }

                // beanName不能重名
                if (getBeanDefinitionRegistry().containsBeanDefinition(beanName)) {
                    throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
                }

                // 注册BeanDefinition
                getBeanDefinitionRegistry().registerBeanDefinition(beanName, beanDefinition);
            }
        }
    }
}
