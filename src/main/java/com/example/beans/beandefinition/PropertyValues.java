package com.example.beans.beandefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * PropertyValues 该类主要用于对 PropertyValue 的封装，以便进行更多的操作。不然的话直接在 BeanDefinition 类设置一个 List<PropertyValue> 即可。
 *
 * @author keemo 2023/5/25
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValueList.add(propertyValue);
    }

    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }

}
