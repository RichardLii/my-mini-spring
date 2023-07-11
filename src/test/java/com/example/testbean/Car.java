package com.example.testbean;

import com.example.beans.annotation.Component;
import com.example.beans.annotation.Value;

/**
 * Car
 *
 * @author keemo 2023/5/25
 */
@Component
public class Car {
    @Value("${brand}")
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
