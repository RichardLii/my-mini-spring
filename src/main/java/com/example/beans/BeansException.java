package com.example.beans;

/**
 * BeanException
 *
 * @author keemo 2023/5/10
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
