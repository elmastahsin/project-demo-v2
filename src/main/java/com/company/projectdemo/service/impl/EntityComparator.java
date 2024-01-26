package com.company.projectdemo.service.impl;

import java.lang.reflect.Field;
import java.util.*;
import org.apache.commons.lang3.builder.EqualsBuilder;

public class EntityComparator {



    public static Map<String, Object> findChangedFields(Object oldObject, Object newObject) {
        Map<String, Object> changes = new HashMap<>();

        if (oldObject.getClass() != newObject.getClass()) {
            throw new IllegalArgumentException("Objects must be of the same type");
        }

        Field[] fields = oldObject.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); // to access private fields
            try {
                Object oldValue = field.get(oldObject);
                Object newValue = field.get(newObject);

                if ((oldValue != null && !EqualsBuilder.reflectionEquals(oldValue, newValue))
                        || (newValue != null && !EqualsBuilder.reflectionEquals(newValue, oldValue))) {
                    changes.put(field.getName(), oldValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return changes;
    }

}
