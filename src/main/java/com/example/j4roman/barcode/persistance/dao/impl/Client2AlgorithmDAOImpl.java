package com.example.j4roman.barcode.persistance.dao.impl;

import com.example.j4roman.barcode.persistance.dao.Client2AlgorithmDAO;
import com.example.j4roman.barcode.persistance.dao.exceptions.DAOException;
import com.example.j4roman.barcode.persistance.entities.Client;
import com.example.j4roman.barcode.persistance.entities.Client2algorithm;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Client2AlgorithmDAOImpl extends GenericDAOImpl<Client2algorithm, Long> implements Client2AlgorithmDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void deleteByClient(Client client) {
        Session hibernateSession = null;
        try {
            hibernateSession = sessionFactory.getCurrentSession();
            Query deleteQuery = hibernateSession.createQuery("delete from Client2algorithm as c2a where c2a.client = :client");
            deleteQuery.setParameter("client", client);
            deleteQuery.executeUpdate();
        } catch(HibernateException e) {
            throw new DAOException(e);
        }
    }
}
