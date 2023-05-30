package com.example.event;

import com.example.beans.eventandlistener.ApplicationListener;

/**
 * 自定义事件监听器
 *
 * @author keemo 2023/5/30
 */
public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("自定义事件监听器: " + this.getClass().getName());
    }

}
