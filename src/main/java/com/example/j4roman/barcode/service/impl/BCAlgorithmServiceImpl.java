package com.example.j4roman.barcode.service.impl;

import com.example.j4roman.barcode.persistance.dao.ActionDAO;
import com.example.j4roman.barcode.persistance.dao.BCAlgorithmDAO;
import com.example.j4roman.barcode.persistance.dao.exceptions.EntityAlreadyExistsException;
import com.example.j4roman.barcode.persistance.dao.exceptions.EntityNotExistException;
import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import com.example.j4roman.barcode.service.BCAlgorithmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BCAlgorithmServiceImpl implements BCAlgorithmService {

    private static Logger logger = LogManager.getLogger(BCAlgorithmServiceImpl.class);
    private static final int DB_CALL_TIMEOUT = 30;

    @Autowired
    private BCAlgorithmDAO bcAlgorithmDAO;

    @Autowired
    private ActionDAO actionDAO;

    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public void create(BCAlgorithm bcAlgorithm) {
        BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(bcAlgorithm.getName());
        if (foundAlgorithm == null) {
            bcAlgorithmDAO.create(bcAlgorithm);
        } else {
            throw new EntityAlreadyExistsException(bcAlgorithm.getName());
        }
    }

    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public BCAlgorithm update(BCAlgorithm bcAlgorithm) {
        logger.debug("Attempt to update with data {}", bcAlgorithm.toString());
        BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(bcAlgorithm.getName());
        if (foundAlgorithm != null) {
            logger.debug("Found {}", foundAlgorithm.toString());
            boolean isUpd = false;
            if (bcAlgorithm.getPattern() != null) {
                foundAlgorithm.setPattern(bcAlgorithm.getPattern());
                isUpd = true;
            }
            if (bcAlgorithm.getDescription() != null) {
                foundAlgorithm.setDescription(bcAlgorithm.getDescription());
                isUpd = true;
            }
            if (isUpd) {
                foundAlgorithm = bcAlgorithmDAO.change(foundAlgorithm);
            }
            if (bcAlgorithm.getActions() != null) {
                actionDAO.deleteByAlgorithm(foundAlgorithm);
                foundAlgorithm.setActions(bcAlgorithm.getActions());
                for (Action action : foundAlgorithm.getActions()) {
                    action.setBcAlgorithm(foundAlgorithm);
                    actionDAO.create(action);
                }
            } else { //Get actions data into result
                Hibernate.initialize(foundAlgorithm.getActions());
            }
            return foundAlgorithm;
        } else {
            throw new EntityNotExistException(bcAlgorithm.getName());
        }
    }

    @Override
    @Transactional
    public boolean deleteByName(String name) {
        BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(name);
        if (foundAlgorithm != null) {
            bcAlgorithmDAO.delete(foundAlgorithm);
        } else {
            throw new EntityNotExistException(name);
        }
        return false;
    }

    @Override
    @Transactional
    public BCAlgorithm getByName(String name) {
        BCAlgorithm foundAlgorithm = bcAlgorithmDAO.getByName(name);
        if (foundAlgorithm != null) {
            Hibernate.initialize(foundAlgorithm.getActions());
            return foundAlgorithm;
        } else {
            throw new EntityNotExistException(name);
        }
    }

    @Override
    @Transactional
    public List<BCAlgorithm> getAll() {
        List<BCAlgorithm> resultList = bcAlgorithmDAO.getAll();
        resultList.forEach(alg -> Hibernate.initialize(alg.getActions()));
        return bcAlgorithmDAO.getAll();
    }
}
