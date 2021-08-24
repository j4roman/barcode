package com.example.j4roman.barcode.persistance.dao;

import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BCAlgorithmDAO extends GenericDAO<BCAlgorithm, Long> {

    void create(BCAlgorithm entity);
    BCAlgorithm getByName(String name);
    void delete(BCAlgorithm algorithm);
    List<BCAlgorithm> getAll();
}
