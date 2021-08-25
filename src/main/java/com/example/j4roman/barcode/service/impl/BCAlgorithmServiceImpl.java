package com.example.j4roman.barcode.service.impl;

import com.example.j4roman.barcode.service.dto.AlgorithmDTO;
import com.example.j4roman.barcode.persistance.dao.ActionDAO;
import com.example.j4roman.barcode.persistance.dao.BCAlgorithmDAO;
import com.example.j4roman.barcode.persistance.dao.exceptions.EntityAlreadyExistsException;
import com.example.j4roman.barcode.persistance.dao.exceptions.EntityDoesNotExistException;
import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import com.example.j4roman.barcode.service.BCAlgorithmService;
import com.example.j4roman.barcode.service.utils.EntityDTOConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BCAlgorithmServiceImpl implements BCAlgorithmService {

    private static final Logger logger = LogManager.getLogger(BCAlgorithmServiceImpl.class);
    private static final int DB_CALL_TIMEOUT = 30;

    @Autowired
    private BCAlgorithmDAO bcAlgorithmDAO;

    @Autowired
    private ActionDAO actionDAO;

    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public AlgorithmDTO create(AlgorithmDTO algorithmDTO) {
        BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(algorithmDTO.getName());
        if (foundAlgorithm == null) {
            BCAlgorithm bcAlgorithm = EntityDTOConverter.convert(algorithmDTO);
            bcAlgorithmDAO.create(bcAlgorithm);
            return EntityDTOConverter.convert(bcAlgorithm);
        } else {
            throw new EntityAlreadyExistsException(algorithmDTO.getName());
        }
    }

    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public AlgorithmDTO update(AlgorithmDTO algorithmDTO) {
        logger.debug("Attempt to update with data {}", algorithmDTO.toString());
        final BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(algorithmDTO.getName());
        if (foundAlgorithm != null) {
            logger.debug("Found algorithm {}", foundAlgorithm.toString());
            boolean isUpd = false;
            if (algorithmDTO.getPattern() != null) {
                foundAlgorithm.setPattern(algorithmDTO.getPattern());
                isUpd = true;
            }
            if (algorithmDTO.getDescription() != null) {
                foundAlgorithm.setDescription(algorithmDTO.getDescription());
                isUpd = true;
            }
            if (algorithmDTO.getActions() != null) {
                actionDAO.deleteByAlgorithm(foundAlgorithm);
                Set<Action> newActions = new HashSet<>(
                        algorithmDTO.getActions()
                                .stream()
                                .map(act -> EntityDTOConverter.convert(act, foundAlgorithm))
                                .collect(Collectors.toSet())
                );
                foundAlgorithm.setActions(newActions);
                isUpd = true;
            }
            if (isUpd) {
                bcAlgorithmDAO.change(foundAlgorithm);
            }
            return EntityDTOConverter.convert(foundAlgorithm);
        } else {
            throw new EntityDoesNotExistException(algorithmDTO.getName());
        }
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(name);
        if (foundAlgorithm != null) {
            bcAlgorithmDAO.delete(foundAlgorithm);
        } else {
            throw new EntityDoesNotExistException(name);
        }
    }

    @Override
    @Transactional
    public AlgorithmDTO getByName(String name) {
        BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(name);
        if (foundAlgorithm != null) {
            return EntityDTOConverter.convert(foundAlgorithm);
        } else {
            throw new EntityDoesNotExistException(name);
        }
    }

    @Override
    @Transactional
    public List<AlgorithmDTO> getAll() {
        List<BCAlgorithm> allList = bcAlgorithmDAO.getAll();
        return new ArrayList<>(
                allList.stream()
                        .map(EntityDTOConverter::convert)
                        .collect(Collectors.toList())
        );
    }
}
