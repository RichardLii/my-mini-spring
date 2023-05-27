package com.example.beans.beandefinition.initanddestroy;

/**
 * 实现该接口可以在初始化bean的时候调用接口的方法
 *
 * @author keemo 2023/5/27
 */
public interface InitializingBean {

    /**
     * 在bean初始化的时候调用该方法
     */
    void afterPropertiesSet() throws Exception;
}
