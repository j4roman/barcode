package com.example.j4roman.barcode.service;

import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;

import java.util.List;

public interface BCAlgorithmService {
    void create(BCAlgorithm bcAlgorithm);
    BCAlgorithm update(BCAlgorithm bcAlgorithm);
    boolean deleteByName(String name);
    BCAlgorithm getByName(String name);
    List<BCAlgorithm> getAll();
}
