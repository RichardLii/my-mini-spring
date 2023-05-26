package com.example.beans.beandefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * PropertyValues 该类主要用于对 PropertyValue 的封装，以便进行更多的操作。不然的话直接在 BeanDefinition 类设置一个 List<PropertyValue> 即可。
 *
 * @author keemo 2023/5/25
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    /**
     * 添加属性
     *
     * @param propertyValue 属性
     */
    public void addPropertyValue(PropertyValue propertyValue) {
        // 因为BeanFactoryPostProcessor接口可以在BeanDefinition信息加载完成之后进行自定义修改，因此该方法要进行变更，后添加的属性值可以覆盖之前的属性值
        for (int i = 0; i < propertyValueList.size(); i++) {
            if (Objects.equals(propertyValueList.get(i).getName(), propertyValue.getName())) {
                propertyValueList.set(i, propertyValue);
                return;
            }
        }

        this.propertyValueList.add(propertyValue);
    }

    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }

}
