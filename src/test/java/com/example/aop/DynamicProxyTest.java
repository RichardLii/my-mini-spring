package com.example.aop;

import com.example.aop.advice.MethodBeforeAdvice;
import com.example.aop.advice.MethodBeforeAdviceInterceptor;
import com.example.aop.aspectj.AspectJExpressionPointcut;
import com.example.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.example.aop.aspectj.ClassFilter;
import com.example.aop.aspectj.MethodMatcher;
import com.example.aop.proxy.*;
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

    /**
     * 代理工厂测试
     */
    @Test
    public void testProxyFactory() throws Exception {
        // 使用JDK动态代理
        advisedSupport.setCglibProxyFlag(false);
        WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();

        // 使用CGLIB动态代理
        advisedSupport.setCglibProxyFlag(true);
        proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();
    }

    /**
     * 测试前置增强
     */
    @Test
    public void testBeforeAdvice() throws Exception {
        // 设置BeforeAdvice
        MethodBeforeAdvice beforeAdvice = (method, args, target) -> System.out.println("BeforeAdvice: do something before the earth explodes");

        advisedSupport.setMethodInterceptor(new MethodBeforeAdviceInterceptor(beforeAdvice));

        WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();
    }

    /**
     * 测试切点增强
     */
    @Test
    public void testPointcutAdvisor() throws Exception {
        // 创建切点增强器
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();

        // 切点（要增强什么方法）
        String expression = "execution(* com.example.testbean.WorldService.explode(..))";
        advisor.setExpression(expression);

        // 增强（找到对应切点之后进行什么样的处理）
        MethodBeforeAdviceInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(
                (method, args, target) -> System.out.println("BeforeAdvice: do something before the earth explodes"));
        advisor.setAdvice(methodInterceptor);

        // 判断是否符合要求
        WorldService worldService = new WorldServiceImpl();

        ClassFilter classFilter = advisor.getPointcut().getClassFilter();

        if (classFilter.matches(worldService.getClass())) {
            AdvisedSupport advisedSupport = new AdvisedSupport();
            advisedSupport.setTargetSource(new TargetSource(worldService));
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
//			advisedSupport.setProxyTargetClass(true);   //JDK or CGLIB

            WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
            proxy.explode();
        }
    }
}
