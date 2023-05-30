package com.example.event;

import com.example.beans.eventandlistener.ApplicationListener;
import com.example.beans.eventandlistener.event.ContextClosedEvent;

/**
 * 自定义容器关闭事件监听器
 *
 * @author keemo 2023/5/30
 */
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("自定义容器关闭监听器: " + this.getClass().getName());
    }

}
