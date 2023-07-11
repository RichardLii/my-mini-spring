package com.example.beans.annotation;

import cn.hutool.core.util.StrUtil;
import com.example.beans.beandefinition.BeanDefinition;
import com.example.beans.beandefinition.BeanDefinitionRegistry;

import java.util.Set;

/**
 * 根据类路径读取BeanDefinition信息
 *
 * @author keemo 2023/7/11
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME = "org.springframework.context.annotation.internalAutowiredAnnotationProcessor";

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 包扫描
     *
     * @param basePackages 扫描路径
     */
    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                // 生成bean的名称
                String beanName = determineBeanName(candidate);
                // 注册BeanDefinition
                registry.registerBeanDefinition(beanName, candidate);
            }
        }

        // 注册处理@Autowired和@Value注解的BeanPostProcessor
        registry.registerBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME, new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    /**
     * 生成bean的名称
     *
     * @param beanDefinition beanDefinition
     * @return beanName
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getClazz();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}
