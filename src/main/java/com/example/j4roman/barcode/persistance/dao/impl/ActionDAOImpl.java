package com.example.j4roman.barcode.persistance.dao.impl;

import com.example.j4roman.barcode.persistance.dao.ActionDAO;
import com.example.j4roman.barcode.persistance.dao.exceptions.DAOException;
import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class ActionDAOImpl extends GenericDAOImpl<Action, Long> implements ActionDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void deleteByAlgorithm(BCAlgorithm algorithm) {
        Session hibernateSession = null;
        try {
            hibernateSession = sessionFactory.getCurrentSession();
            Query deleteQuery = hibernateSession.createQuery("delete from Action as act where act.bcAlgorithm = :bcAlgorithm");
            deleteQuery.setParameter("bcAlgorithm", algorithm);
            deleteQuery.executeUpdate();
        } catch(HibernateException e) {
            throw new DAOException(e);
        }
    }
}
