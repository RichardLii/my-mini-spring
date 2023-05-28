package com.example.beans.aware;

import com.example.beans.BeansException;
import com.example.beans.applicationcontext.ApplicationContext;

/**
 * 实现该接口，能感知所属ApplicationContext
 *
 * @author keemo 2023/5/28
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
