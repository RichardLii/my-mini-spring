package com.example.beans.eventandlistener;

import java.util.EventListener;

/**
 * spring支持的事件监听器接口
 *
 * @author keemo 2023/5/30
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * 处理事件
     *
     * @param event 事件
     */
    void onApplicationEvent(E event);

}