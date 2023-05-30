package com.example.event;

import com.example.beans.applicationcontext.ApplicationContext;
import com.example.beans.eventandlistener.event.ApplicationContextEvent;

/**
 * 自定义事件
 *
 * @author keemo 2023/5/30
 */
public class CustomEvent extends ApplicationContextEvent {

    public CustomEvent(ApplicationContext source) {
        super(source);
    }

}
