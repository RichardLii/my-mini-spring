package com.example.aop.proxy;

import lombok.Getter;

/**
 * 被代理的目标对象封装
 *
 * @author keemo 2023/7/7
 */
@Getter
public class TargetSource {

    /**
     * 目标对象
     */
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 获取目标对象实现接口
     *
     * @return 目标对象实现接口
     */
    public Class<?>[] getTargetClassInterfaces() {
        return this.target.getClass().getInterfaces();
    }

}
