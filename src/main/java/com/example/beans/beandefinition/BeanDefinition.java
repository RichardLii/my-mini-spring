package com.example.beans.beandefinition;

/**
 * BeanDefinition
 *
 * @author keemo 2023/5/10
 */
public class BeanDefinition {

    private Class<?> clazz;

    public BeanDefinition(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
