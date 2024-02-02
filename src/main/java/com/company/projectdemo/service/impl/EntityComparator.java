package com.company.projectdemo.service.impl;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.AccessibleObject.*;
import java.util.HashMap;
import java.util.Map;

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
                if (field.get(oldObject) == null || field.get(newObject) == null) {
                    if (field.get(oldObject)==null) changes.put(field.getName(), "null");
                    continue;
                }
                String oldValue = field.get(oldObject).toString();
                String newValue = field.get(newObject).toString();

                if (newValue != null && !(oldValue.equals(newValue))){
                    changes.put(field.getName(), oldValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        //if no changes, return No updates
        if (changes.isEmpty()) {
            changes.put("All columns", "No updates");
        }
        return changes;
    }

}
