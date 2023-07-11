package com.example.aop.aspectj;

import org.aopalliance.aop.Advice;

/**
 * AspectJ 切点增强器
 *
 * @author keemo 2023/7/11
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    private AspectJExpressionPointcut pointcut;

    private Advice advice;

    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public Pointcut getPointcut() {
        // 如果设置多次expression的话，只有第一次设置的expression生效
        if (pointcut == null) {
            pointcut = new AspectJExpressionPointcut(expression);
        }

        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
