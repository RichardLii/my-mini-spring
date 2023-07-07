package com.example.aop.proxy;

/**
 * AOP代理抽象
 *
 * @author keemo 2023/7/7
 */
public interface AopProxy {

    /**
     * 获取代理对象
     *
     * @return 代理对象
     */
    Object getProxy();
}
