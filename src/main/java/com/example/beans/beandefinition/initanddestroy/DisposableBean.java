package com.example.beans.beandefinition.initanddestroy;

/**
 * 待销毁的bean，实现该接口将会在虚拟机关闭之前调用 destroy() 方法
 *
 * @author keemo 2023/5/27
 */
public interface DisposableBean {

    /**
     * 销毁方法
     */
    void destroy() throws Exception;
}
