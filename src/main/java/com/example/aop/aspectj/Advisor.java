package com.example.aop.aspectj;

import org.aopalliance.aop.Advice;

/**
 * 增强器
 *
 * @author keemo 2023/7/11
 */
public interface Advisor {

    Advice getAdvice();
}
