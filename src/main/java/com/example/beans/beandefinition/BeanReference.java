package com.example.beans.beandefinition;

/**
 * BeanReference 该类的主要作用是标注bean的属性为其他的bean
 *
 * @author keemo 2023/5/25
 */
public class BeanReference {

    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
