package com.example.ioc;

import com.example.beans.applicationcontext.ClassPathXmlApplicationContext;
import com.example.testbean.Car;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 包扫描测试类
 *
 * @author keemo 2023/7/11
 */
public class PackageScanTest {

    @Test
    public void testScanPackage() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:package-scan.xml");

        Car car = applicationContext.getBean("car", Car.class);
        assertThat(car).isNotNull();
    }
}
