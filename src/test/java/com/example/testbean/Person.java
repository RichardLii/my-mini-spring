package com.example.testbean;

import com.example.beans.beandefinition.initanddestroy.DisposableBean;
import com.example.beans.beandefinition.initanddestroy.InitializingBean;

/**
 * Person
 *
 * @author keemo 2023/5/25
 */
public class Person implements InitializingBean, DisposableBean {

    private String name;

    private int age;

    // 添加其他bean引用
    private Car car;

    public void personCustomInitMethod() {
        System.out.println("Person 类自定义初始化方法");
    }

    public void personCustomDestroyMethod() {
        System.out.println("Person 类自定义销毁方法");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("实现InitializingBean接口调用初始化方法");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("实现DisposableBean接口调用销毁方法");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", car=" + car +
                '}';
    }
}
