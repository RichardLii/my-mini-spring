package com.example.beans.annotation;

import java.lang.annotation.*;

/**
 * Component
 *
 * @author keemo 2023/7/11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    String value() default "";
}