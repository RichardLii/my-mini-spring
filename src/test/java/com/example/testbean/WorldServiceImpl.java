package com.example.testbean;

/**
 * WorldServiceImpl
 *
 * @author keemo 2023/7/7
 */
public class WorldServiceImpl implements WorldService {

    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void explode() {
        System.out.println("The " + name + " is going to explode");
    }
}
