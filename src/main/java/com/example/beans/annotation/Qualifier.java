package com.example.beans.annotation;

import java.lang.annotation.*;

/**
 * Qualifier
 *
 * @author keemo 2023/7/12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Inherited
@Documented
public @interface Qualifier {

    String value() default "";
}