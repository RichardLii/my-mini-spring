package com.example.beans.aware;

import com.example.beans.BeansException;
import com.example.beans.factory.BeanFactory;

/**
 * 实现该接口，能感知所属BeanFactory
 *
 * @author keemo 2023/5/28
 */
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
