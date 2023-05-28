package com.example.ioc;

import com.example.beans.applicationcontext.ClassPathXmlApplicationContext;
import com.example.testbean.AwareHelloService;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


/**
 * AwareInterfaceTest
 *
 * @author keemo 2023/5/28
 */
public class AwareInterfaceTest {

    @Test
    public void test() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        AwareHelloService helloService = applicationContext.getBean("awareHelloService", AwareHelloService.class);
        assertThat(helloService.getApplicationContext()).isNotNull();
        assertThat(helloService.getBeanFactory()).isNotNull();
    }
}
