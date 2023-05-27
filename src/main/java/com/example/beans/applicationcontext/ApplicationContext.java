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

    /**
     * 关闭应用上下文
     */
    void close();

    /**
     * 向虚拟机中注册一个钩子方法，在虚拟机关闭之前执行关闭容器等操作
     */
    void registerShutdownHook();

}
