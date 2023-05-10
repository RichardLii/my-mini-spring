package com.example.ioc;

import com.example.beans.factory.BeanFactory;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * BeanFactoryTest
 *
 * @author keemo 2023/5/10
 */
public class BeanFactoryTest {

    @Test
    public void testGetBean() {
        BeanFactory factory = new BeanFactory();
        factory.registerBean("halo", new HelloService());
        HelloService halo = (HelloService) factory.getBean("halo");
        assertThat(halo).isNotNull();
        assertThat(halo.sayHello()).isEqualTo("halo");
    }


    static class HelloService {
        public String sayHello() {
            System.out.println("Halo");
            return "halo";
        }
    }

}
