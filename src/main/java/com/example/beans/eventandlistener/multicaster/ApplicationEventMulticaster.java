package com.example.beans.eventandlistener.multicaster;

import com.example.beans.eventandlistener.ApplicationEvent;
import com.example.beans.eventandlistener.ApplicationListener;

/**
 * 事件广播中心，该接口实际上是实现了ApplicationEventPublisher接口的功能，并扩展了新增、删除监听器的功能
 *
 * @author keemo 2023/5/30
 */
public interface ApplicationEventMulticaster {

    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    void multicastEvent(ApplicationEvent event);

}
