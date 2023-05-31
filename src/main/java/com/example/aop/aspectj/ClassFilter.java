package com.example.aop.aspectj;

/**
 * 类匹配器
 *
 * @author keemo 2023/5/31
 */
public interface ClassFilter {

    boolean matches(Class<?> clazz);

}
