package com.example.aop;

import com.example.aop.aspectj.AspectJExpressionPointcut;
import com.example.aop.aspectj.MethodMatcher;
import com.example.aop.proxy.AdvisedSupport;
import com.example.aop.proxy.CglibAopProxy;
import com.example.aop.proxy.JdkDynamicAopProxy;
import com.example.aop.proxy.TargetSource;
import com.example.testbean.WorldService;
import com.example.testbean.WorldServiceImpl;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Before;
import org.junit.Test;

/**
 * JDK动态代理测试类
 *
 * @author keemo 2023/7/7
 */
public class DynamicProxyTest {
    private AdvisedSupport advisedSupport;

    @Before
    public void setup() {
        // 目标对象
        WorldService worldService = new WorldServiceImpl();
        TargetSource targetSource = new TargetSource(worldService);

        // 方法拦截器
        MethodInterceptor methodInterceptor = invocation -> {
            System.out.println("Do something before the earth explodes");
            Object result = invocation.proceed();
            System.out.println("Do something after the earth explodes");
            return result;
        };

        // 方法匹配器
        MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* com.example.testbean.WorldService.explode(..))").getMethodMatcher();

        // 代理增强支持
        advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodInterceptor(methodInterceptor);
        advisedSupport.setMethodMatcher(methodMatcher);
    }

    /**
     * JDK动态代理
     */
    @Test
    public void testJdkDynamicProxy() throws Exception {
        WorldService proxy = (WorldService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }

    /**
     * Cglib动态代理
     */
    @Test
    public void testCglibDynamicProxy() throws Exception {
        WorldService proxy = (WorldService) new CglibAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }
}
