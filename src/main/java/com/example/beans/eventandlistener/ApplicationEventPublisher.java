package com.example.beans.eventandlistener;

/**
 * 事件发布者接口
 *
 * @author keemo 2023/5/30
 */
public interface ApplicationEventPublisher {

    /**
     * 发布事件
     *
     * @param event 事件
     */
    void publishEvent(ApplicationEvent event);

}
