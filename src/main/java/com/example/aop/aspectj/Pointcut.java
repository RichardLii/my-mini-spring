package com.example.aop.aspectj;

/**
 * 切点抽象
 *
 * @author keemo 2023/5/31
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
