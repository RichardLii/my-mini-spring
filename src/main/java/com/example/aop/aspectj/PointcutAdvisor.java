package com.example.aop.aspectj;

/**
 * 切点增强器
 *
 * @author keemo 2023/7/11
 */
public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();
}
