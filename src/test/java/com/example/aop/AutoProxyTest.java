package com.example.aop;

import com.example.beans.applicationcontext.ClassPathXmlApplicationContext;
import com.example.testbean.WorldService;
import org.junit.Test;

/**
 * 自动代理测试类
 *
 * @author keemo 2023/7/11
 */
public class AutoProxyTest {

    @Test
    public void testAutoProxy() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:auto-proxy.xml");

        //获取代理对象
        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.explode();
    }
}
