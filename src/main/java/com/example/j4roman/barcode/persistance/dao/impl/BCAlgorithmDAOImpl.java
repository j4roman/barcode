package com.example.j4roman.barcode.persistance.dao.impl;

import com.example.j4roman.barcode.persistance.dao.BCAlgorithmDAO;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BCAlgorithmDAOImpl extends GenericDAOImpl<BCAlgorithm, Long> implements BCAlgorithmDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(BCAlgorithm entity) {
        super.create(entity);
    }

    @Override
    public BCAlgorithm getByName(String name) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        Query<BCAlgorithm> query = hibernateSession.createQuery("from BCAlgorithm as alg where alg.name = :name");
        query.setParameter("name", name);
        BCAlgorithm algorithm = query.uniqueResult();
        return algorithm;
    }

    @Override
    public void delete(BCAlgorithm algorithm) {
        super.delete(algorithm);
    }

    @Override
    public List<BCAlgorithm> getAll() {
        return super.findAll(BCAlgorithm.class);
    }

    @Override
    public List<BCAlgorithm> getByNames(List<String> names) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        Query<BCAlgorithm> query = hibernateSession.createQuery("from BCAlgorithm as alg where alg.name in :names");
        query.setParameter("names", names);
        List<BCAlgorithm> algorithms = query.list();
        return algorithms;
    }
}
