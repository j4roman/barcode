package com.example.j4roman.barcode.service;

import com.example.j4roman.barcode.service.dto.manage.AlgorithmDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The service is used to process operations with algorithms
 * and corresponding actions.
 */
public interface BCAlgorithmService {
    /**
     * Save given algorithm to DB
     *
     * @param bcAlgorithm request body with algorithm to save
     * @return response body with saved algorithm
     */
    AlgorithmDTO create(AlgorithmDTO bcAlgorithm);

    /**
     * Update existing algorithm in DB
     *
     * @param algorithm request body with algorithm to update
     * @return response body with updated algorithm
     */
    AlgorithmDTO update(AlgorithmDTO algorithm);

    /**
     * Remove algorithm from DB
     *
     * @param name name of algorithm to remove
     */
    void deleteByName(String name);

    /**
     * Retrieve algorithm with given name from DB
     *
     * @param name name of algorithm to get
     * @return response body of algorithm with given name
     */
    AlgorithmDTO getByName(String name);

    /**
     * Get all algorithms from DB
     *
     * @return response body with all algorithms in DB
     */
    List<AlgorithmDTO> getAll();
}
