package com.example.beans.beandefinition;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * BeanDefinition
 *
 * @author keemo 2023/5/10
 */
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BeanDefinition that = (BeanDefinition) o;
        return clazz.equals(that.getClazz());
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz);
    }
}
