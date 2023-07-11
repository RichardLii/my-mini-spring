package com.example.testbean;

import com.example.aop.advice.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * WorldService 前置增强
 *
 * @author keemo 2023/7/11
 */
public class WorldServiceBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("BeforeAdvice: do something before the earth explodes");
    }
}
