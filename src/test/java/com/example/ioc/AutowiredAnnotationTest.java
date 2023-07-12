package com.example.ioc;

import com.example.beans.applicationcontext.ClassPathXmlApplicationContext;
import com.example.testbean.Person;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Autowired 注解测试类
 *
 * @author keemo 2023/7/12
 */
public class AutowiredAnnotationTest {

    @Test
    public void testAutowiredAnnotation() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:autowired-annotation.xml");

        Person person = applicationContext.getBean(Person.class);
        assertThat(person.getCar()).isNotNull();
    }
}
