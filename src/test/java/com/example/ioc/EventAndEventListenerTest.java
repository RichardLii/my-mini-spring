package com.example.ioc;

import com.example.beans.applicationcontext.ClassPathXmlApplicationContext;
import com.example.event.CustomEvent;
import org.junit.Test;

/**
 * 事件监听器测试类
 *
 * @author keemo 2023/5/30
 */
public class EventAndEventListenerTest {

    @Test
    public void testEventListener() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:event-and-event-listener.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext));

        // 或者applicationContext.close()主动关闭容器
        applicationContext.registerShutdownHook();
    }
}
