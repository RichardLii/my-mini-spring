package com.example.beans.annotation;

import cn.hutool.core.util.ClassUtil;
import com.example.beans.beandefinition.BeanDefinition;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 根据类路径扫描@Component注解的类
 *
 * @author keemo 2023/7/11
 */
public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();

        // 扫描有org.springframework.stereotype.Component注解的类
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            candidates.add(beanDefinition);
        }

        return candidates;
    }
}
