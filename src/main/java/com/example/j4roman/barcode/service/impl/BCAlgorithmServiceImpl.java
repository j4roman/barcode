package com.example.j4roman.barcode.service.impl;

import com.example.j4roman.barcode.persistance.dao.ActionDAO;
import com.example.j4roman.barcode.persistance.dao.BCAlgorithmDAO;
import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import com.example.j4roman.barcode.service.BCAlgorithmService;
import com.example.j4roman.barcode.service.dto.manage.AlgorithmDTO;
import com.example.j4roman.barcode.service.exceptions.EntityAlreadyExistsException;
import com.example.j4roman.barcode.service.exceptions.EntityDoesNotExistException;
import com.example.j4roman.barcode.service.exceptions.UnexpectedDAOException;
import com.example.j4roman.barcode.service.utils.EntityDTOConverter;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(BCAlgorithmServiceImpl.class);
    private static final int DB_CALL_TIMEOUT = 30;

    @Autowired
    private BCAlgorithmDAO bcAlgorithmDAO;

    @Autowired
    private ActionDAO actionDAO;

    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public AlgorithmDTO create(AlgorithmDTO algorithmDTO) {
        log.debug("Algorithm '{}' is creating from {}", algorithmDTO.getName(), algorithmDTO);
        try {
            String upperName = algorithmDTO.getName().toUpperCase();
            BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(upperName);
            if (foundAlgorithm == null) {
                BCAlgorithm bcAlgorithm = EntityDTOConverter.convert(algorithmDTO);
                bcAlgorithmDAO.create(bcAlgorithm);
                log.debug("Algorithm '{}' is created", bcAlgorithm.getName());
                return EntityDTOConverter.convert(bcAlgorithm);
            } else {
                log.error("Algorithm '{}' already exists", algorithmDTO.getName());
                throw new EntityAlreadyExistsException(upperName);
            }
        } catch (HibernateException e) {
            log.error("DB processing error while creating algorithm '{}'", algorithmDTO.getName());
            throw new UnexpectedDAOException(e);
        }
    }

    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public AlgorithmDTO update(AlgorithmDTO algorithmDTO) {
        try {
            log.debug("Algorithm '{}' is updating from {}", algorithmDTO.getName(), algorithmDTO);
            String upperName = algorithmDTO.getName().toUpperCase();
            final BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(upperName);
            if (foundAlgorithm != null) {
                log.debug("Found algorithm '{}'", foundAlgorithm.toString());
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
                    log.debug("Algorithm '{}' is updated", algorithmDTO.getName());
                } else {
                    log.debug("No need to update algorithm '{}'", algorithmDTO.getName());
                }
                return EntityDTOConverter.convert(foundAlgorithm);
            } else {
                log.error("Algorithm '{}' does not exist", algorithmDTO.getName());
                throw new EntityDoesNotExistException(upperName);
            }
        } catch (HibernateException e) {
            log.error("DB processing error while updating algorithm '{}'", algorithmDTO.getName());
            throw new UnexpectedDAOException(e);
        }
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        log.debug("Algorithm '{}' is deleting", name);
        try {
            String upperName = name.toUpperCase();
            BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(upperName);
            if (foundAlgorithm != null) {
                log.debug("Found algorithm '{}'", foundAlgorithm.toString());
                bcAlgorithmDAO.delete(foundAlgorithm);
                log.debug("Algorithm '{}' is deleted", name);
            } else {
                log.error("Algorithm '{}' does not exist", name);
                throw new EntityDoesNotExistException(upperName);
            }
        } catch (HibernateException e) {
            log.error("DB processing error while deleting algorithm '{}'", name);
            throw new UnexpectedDAOException(e);
        }
    }

    @Override
    @Transactional
    public AlgorithmDTO getByName(String name) {
        log.debug("Algorithm '{}' is getting", name);
        try {
            String upperName = name.toUpperCase();
            BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(upperName);
            if (foundAlgorithm != null) {
                log.debug("Found algorithm {}", foundAlgorithm.toString());
                return EntityDTOConverter.convert(foundAlgorithm);
            } else {
                log.error("Algorithm '{}' does not exist", name);
                throw new EntityDoesNotExistException(upperName);
            }
        } catch (HibernateException e) {
            log.error("DB processing error while getting algorithm '{}'", name);
            throw new UnexpectedDAOException(e);
        }
    }

    @Override
    @Transactional
    public List<AlgorithmDTO> getAll() {
        log.debug("Get all the algorithms");
        try {
            List<BCAlgorithm> allList = bcAlgorithmDAO.getAll();
            return new ArrayList<>(
                    allList.stream()
                            .map(EntityDTOConverter::convert)
                            .collect(Collectors.toList())
            );
        } catch (HibernateException e) {
            log.error("DB processing error while getting the algorithms");
            throw new UnexpectedDAOException(e);
        }
    }
}
