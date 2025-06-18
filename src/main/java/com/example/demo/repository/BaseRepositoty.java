package com.example.demo.repository;

import java.util.List;

public interface BaseRepositoty<T> {
    void save(T entity);
    void update(T entity);
    void delete(Long id, String deletedBy);
    T findById(Long id);
    List<T> findAll();
}
