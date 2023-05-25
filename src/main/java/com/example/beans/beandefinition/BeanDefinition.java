package com.example.beans.beandefinition;

/**
 * BeanDefinition
 *
 * @author keemo 2023/5/10
 */
public class BeanDefinition {

    private Class<?> clazz;

    /**
     * 新增 bean 属性字段
     */
    private PropertyValues propertyValues;

    public BeanDefinition(Class<?> clazz) {
        this.clazz = clazz;
    }

    public BeanDefinition(Class<?> clazz, PropertyValues propertyValues) {
        this.clazz = clazz;
        this.propertyValues = propertyValues;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}
