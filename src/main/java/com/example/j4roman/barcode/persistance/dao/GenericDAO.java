package com.example.j4roman.barcode.persistance.dao;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface GenericDAO<T, ID extends Serializable> {

    void create(T entity);
    T change(T entity);
    void delete(T entity);
    List<T> findAll(Class clazz);
    T getById(Class clazz, ID id);
    T loadById(Class clazz, ID id);
}
