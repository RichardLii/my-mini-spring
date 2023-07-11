package com.example.testbean;

import com.example.beans.annotation.Component;

/**
 * Car
 *
 * @author keemo 2023/5/25
 */
@Component
public class Car {
    private String brand;

    public Car() {

    }

    public Car(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                '}';
    }
}
