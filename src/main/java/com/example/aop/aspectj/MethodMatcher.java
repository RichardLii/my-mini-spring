package com.example.aop.aspectj;

import java.lang.reflect.Method;

/**
 * 方法匹配器
 *
 * @author keemo 2023/5/31
 */
public interface MethodMatcher {

    boolean matches(Method method, Class<?> targetClass);

}
