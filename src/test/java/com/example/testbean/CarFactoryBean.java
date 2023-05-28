package com.example.testbean;

import com.example.beans.factorybean.FactoryBean;

/**
 * CarFactoryBean
 *
 * @author keemo 2023/5/28
 */
public class CarFactoryBean implements FactoryBean<Car> {

    private String brand;

    @Override
    public Car getObject() throws Exception {
        Car car = new Car();
        car.setBrand(brand);
        return car;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
