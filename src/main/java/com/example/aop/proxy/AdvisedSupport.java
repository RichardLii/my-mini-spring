package com.example.aop.proxy;

import com.example.aop.aspectj.MethodMatcher;
import lombok.Getter;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * 增强支持
 * 增强（权限校验、日志打印...）
 *
 * @author keemo 2023/7/7
 */
@Getter
@Setter
public class AdvisedSupport {

    private TargetSource targetSource;

    private MethodInterceptor methodInterceptor;

    private MethodMatcher methodMatcher;

    private boolean cglibProxyFlag;
}
