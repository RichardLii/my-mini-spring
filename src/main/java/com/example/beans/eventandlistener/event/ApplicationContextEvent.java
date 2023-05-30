package com.example.beans.eventandlistener.event;

import com.example.beans.applicationcontext.ApplicationContext;
import com.example.beans.eventandlistener.ApplicationEvent;

/**
 * 应用上下文事件
 *
 * @author keemo 2023/5/30
 */
public abstract class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(ApplicationContext source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
