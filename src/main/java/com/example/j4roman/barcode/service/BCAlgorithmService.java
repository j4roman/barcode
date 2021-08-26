package com.example.j4roman.barcode.service;

import com.example.j4roman.barcode.service.dto.manage.AlgorithmDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BCAlgorithmService {
    AlgorithmDTO create(AlgorithmDTO bcAlgorithm);
    AlgorithmDTO update(AlgorithmDTO algorithm);
    void deleteByName(String name);
    AlgorithmDTO getByName(String name);
    List<AlgorithmDTO> getAll();
}
