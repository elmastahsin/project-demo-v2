package com.company.projectdemo.service;

import java.util.List;

public interface CrudService<T, ID> {


    T findById(ID id);

    List<T> findByAll(T t);

    T save(T t);

    void update(T t);

    boolean isExist(T t);

    List<T> findByFilter(String search);

    List<T> listAll();

}
