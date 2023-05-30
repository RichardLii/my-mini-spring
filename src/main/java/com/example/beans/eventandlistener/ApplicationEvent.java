package com.example.beans.eventandlistener;

import java.util.EventObject;

/**
 * spring支持的事件抽象类
 *
 * @author keemo 2023/5/30
 */
public abstract class ApplicationEvent extends EventObject {

    public ApplicationEvent(Object source) {
        super(source);
    }

}
