package com.company.projectdemo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractMapService<T, ID> {


    public Map<ID, T> map = new HashMap<>();

    //save
    T save(ID id, T object) {
        map.put(id, object);
        return object;
    }

    //findAll
    List<T> findAll() {
        return new ArrayList<>(map.values());
    }

    //findbyId
    T findById(ID id) {
        return map.get(id);
    }

    //deleteById
    void deleteById(ID id) {
        map.remove(id);
    }

    //update
    void update(ID id, T object) {
        map.put(id, object);
    }


}
