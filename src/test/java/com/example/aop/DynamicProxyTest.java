package com.example.aop;

import com.example.aop.aspectj.AspectJExpressionPointcut;
import com.example.aop.aspectj.MethodMatcher;
import com.example.aop.proxy.AdvisedSupport;
import com.example.aop.proxy.JdkDynamicAopProxy;
import com.example.aop.proxy.TargetSource;
import com.example.testbean.WorldService;
import com.example.testbean.WorldServiceImpl;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;

/**
 * JDK动态代理测试类
 *
 * @author keemo 2023/7/7
 */
public class DynamicProxyTest {

    @Test
    public void testJdkDynamicProxy() throws Exception {
        // 目标对象
        WorldService worldService = new WorldServiceImpl();

        TargetSource targetSource = new TargetSource(worldService);

        MethodInterceptor methodInterceptor = invocation -> {
            System.out.println("Do something before the earth explodes");
            Object result = invocation.proceed();
            System.out.println("Do something after the earth explodes");
            return result;
        };

        MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* com.example.testbean.WorldService.explode(..))").getMethodMatcher();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodInterceptor(methodInterceptor);
        advisedSupport.setMethodMatcher(methodMatcher);

        WorldService proxy = (WorldService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }
}
