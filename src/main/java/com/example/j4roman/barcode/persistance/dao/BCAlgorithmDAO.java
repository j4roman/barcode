package com.example.j4roman.barcode.persistance.dao;

import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BCAlgorithmDAO extends GenericDAO<BCAlgorithm, Long> {

    /**
     * Create algorithm entity
     *
     * @param entity algorithm entity to be created
     */
    void create(BCAlgorithm entity);

    /**
     * Update algorithm entity
     *
     * @param entity algorithm entity to be updated
     * @return updated algorithm
     */
    BCAlgorithm change(BCAlgorithm entity);

    /**
     * Get algorithm by its name
     * @param name name of algorithm to be retrieved
     * @return retrieved algorithm
     */
    BCAlgorithm getByName(String name);

    /**
     * Delete algorithm
     * @param algorithm to be deleted
     */
    void delete(BCAlgorithm algorithm);

    /**
     * Get all algorithms
     * @return list of all algorithms
     */
    List<BCAlgorithm> getAll();

    /**
     * Get the list of algorithms having given names
     * @param names list of algorithm names
     * @return the list of algorithms
     */
    List<BCAlgorithm> getByNames(List<String> names);
}
