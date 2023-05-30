package com.example.beans.eventandlistener.event;

import com.example.beans.applicationcontext.ApplicationContext;

/**
 * 容器刷新事件
 *
 * @author keemo 2023/5/30
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {

    public ContextRefreshedEvent(ApplicationContext source) {
        super(source);
    }

}
