package com.example.beans.factorybean;

/**
 * FactoryBean是一种特殊的bean，当向容器获取该bean时，容器不是返回其本身，而是返回其FactoryBean#getObject方法的返回值，可通过编码方式定义复杂的bean。
 *
 * @author keemo 2023/5/28
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

}
