package com.company.projectdemo.service;

import com.company.projectdemo.dto.TransactionDTO;

import java.util.List;

public interface CrudService<T, ID> {


    T findById(ID id);

    List<T> findByAll(T t);

    T save(T t);


    boolean isExist(T t);

    List<T> findByFilter(String search);

    List<T> listAll();

//    void delete(T t);
    void delete(ID id);
}
