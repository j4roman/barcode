package com.example.j4roman.barcode.persistance.dao;

import com.example.j4roman.barcode.persistance.dao.GenericDAO;
import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ActionDAO extends GenericDAO<Action, Long> {

    /**
     * The method deletes all Actions associated with given <code>algorithm</code>
     * @param algorithm algorithm which actions should be deleted
     */
    void deleteByAlgorithm(BCAlgorithm algorithm);
}
