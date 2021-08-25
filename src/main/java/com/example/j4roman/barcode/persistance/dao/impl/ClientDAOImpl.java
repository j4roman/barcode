package com.example.j4roman.barcode.persistance.dao.impl;

import com.example.j4roman.barcode.persistance.dao.ClientDAO;
import com.example.j4roman.barcode.persistance.dao.exceptions.DAOException;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import com.example.j4roman.barcode.persistance.entities.Client;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientDAOImpl extends GenericDAOImpl<Client, Long> implements ClientDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(Client entity) {
        super.create(entity);
    }

    @Override
    public Client getByCode(String code) {
        Session hibernateSession = null;
        try {
            hibernateSession = sessionFactory.getCurrentSession();
            Query<Client> query = hibernateSession.createQuery("from Client as clt where clt.code = :code");
            query.setParameter("code", code);
            Client algorithm = query.uniqueResult();
            return algorithm;
        } catch(HibernateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Client> getAll() {
        return super.findAll(Client.class);
    }
}
