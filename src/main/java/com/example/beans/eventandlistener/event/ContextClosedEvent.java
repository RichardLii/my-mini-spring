package com.example.beans.eventandlistener.event;

import com.example.beans.applicationcontext.ApplicationContext;

/**
 * 容器关闭事件
 *
 * @author keemo 2023/5/30
 */
public class ContextClosedEvent extends ApplicationContextEvent {

    public ContextClosedEvent(ApplicationContext source) {
        super(source);
    }

}
