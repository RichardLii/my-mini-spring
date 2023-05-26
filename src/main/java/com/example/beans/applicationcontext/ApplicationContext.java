package com.example.beans.applicationcontext;

import com.example.beans.BeansException;
import com.example.beans.factory.BeanFactory;

/**
 * ApplicationContext
 *
 * @author keemo 2023/5/26
 */
public interface ApplicationContext extends BeanFactory {

    /**
     * 刷新容器
     */
    void refresh() throws BeansException;

}
