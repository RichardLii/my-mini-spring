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

    /**
     * 初始化方法名称
     */
    private String initMethodName;

    /**
     * 销毁方法名称
     */
    private String destroyMethodName;

    public BeanDefinition(Class<?> clazz) {
        this(clazz, null);
    }

    public BeanDefinition(Class<?> clazz, PropertyValues propertyValues) {
        this.clazz = clazz;

        // 假如默认初始值，防止读取BeanDefinition信息时候的空指针异常
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
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

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }
}
