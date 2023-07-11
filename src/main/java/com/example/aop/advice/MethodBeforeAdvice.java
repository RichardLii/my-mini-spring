package com.example.aop.advice;

import java.lang.reflect.Method;

/**
 * 方法前置增强
 *
 * @author keemo 2023/7/11
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    void before(Method method, Object[] args, Object target) throws Throwable;
}
