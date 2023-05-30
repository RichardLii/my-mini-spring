package com.example.event;

import com.example.beans.eventandlistener.ApplicationListener;
import com.example.beans.eventandlistener.event.ContextRefreshedEvent;

/**
 * 自定义容器刷新事件监听器
 *
 * @author keemo 2023/5/30
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("自定义容器刷新监听器: " + this.getClass().getName());
    }

}
