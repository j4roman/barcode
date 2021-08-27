package com.example.j4roman.barcode.service.impl;

import com.example.j4roman.barcode.service.dto.manage.AlgorithmDTO;
import com.example.j4roman.barcode.persistance.dao.ActionDAO;
import com.example.j4roman.barcode.persistance.dao.BCAlgorithmDAO;
import com.example.j4roman.barcode.service.exceptions.DAOException;
import com.example.j4roman.barcode.service.exceptions.EntityAlreadyExistsException;
import com.example.j4roman.barcode.service.exceptions.EntityDoesNotExistException;
import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import com.example.j4roman.barcode.service.BCAlgorithmService;
import com.example.j4roman.barcode.service.exceptions.UnexpectedDAOException;
import com.example.j4roman.barcode.service.utils.EntityDTOConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
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
        try {
            String upperName = algorithmDTO.getName().toUpperCase();
            BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(upperName);
            if (foundAlgorithm == null) {
                BCAlgorithm bcAlgorithm = EntityDTOConverter.convert(algorithmDTO);
                bcAlgorithmDAO.create(bcAlgorithm);
                return EntityDTOConverter.convert(bcAlgorithm);
            } else {
                throw new EntityAlreadyExistsException(upperName);
            }
        } catch (HibernateException e) {
            throw new UnexpectedDAOException(e);
        }
    }

    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public AlgorithmDTO update(AlgorithmDTO algorithmDTO) {
        try {
            logger.debug("Attempt to update with data {}", algorithmDTO.toString());
            String upperName = algorithmDTO.getName().toUpperCase();
            final BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(upperName);
            if (foundAlgorithm != null) {
                logger.debug("Found algorithm {}", foundAlgorithm.toString());
                boolean isUpd = false;
                if (algorithmDTO.getInPattern() != null) {
                    foundAlgorithm.setInPattern(algorithmDTO.getInPattern());
                    isUpd = true;
                }
                if (algorithmDTO.getOutPattern() != null) {
                    foundAlgorithm.setOutPattern(algorithmDTO.getOutPattern());
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
                throw new EntityDoesNotExistException(upperName);
            }
        } catch (HibernateException e) {
            throw new UnexpectedDAOException(e);
        }
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        try {
            String upperName = name.toUpperCase();
            BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(upperName);
            if (foundAlgorithm != null) {
                bcAlgorithmDAO.delete(foundAlgorithm);
            } else {
                throw new EntityDoesNotExistException(upperName);
            }
        } catch (HibernateException e) {
            throw new UnexpectedDAOException(e);
        }
    }

    @Override
    @Transactional
    public AlgorithmDTO getByName(String name) {
        try {
            String upperName = name.toUpperCase();
            BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(upperName);
            if (foundAlgorithm != null) {
                return EntityDTOConverter.convert(foundAlgorithm);
            } else {
                throw new EntityDoesNotExistException(upperName);
            }
        } catch (HibernateException e) {
            throw new UnexpectedDAOException(e);
        }
    }

    @Override
    @Transactional
    public List<AlgorithmDTO> getAll() {
        try {
            List<BCAlgorithm> allList = bcAlgorithmDAO.getAll();
            return new ArrayList<>(
                    allList.stream()
                            .map(EntityDTOConverter::convert)
                            .collect(Collectors.toList())
            );
        } catch (HibernateException e) {
            throw new UnexpectedDAOException(e);
        }
    }
}
