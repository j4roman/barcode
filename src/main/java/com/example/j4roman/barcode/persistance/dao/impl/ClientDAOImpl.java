package com.example.j4roman.barcode.persistance.dao.impl;

import com.example.j4roman.barcode.persistance.dao.ClientDAO;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import com.example.j4roman.barcode.persistance.entities.Client;
import com.example.j4roman.barcode.persistance.entities.Client2algorithm;
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
        Session hibernateSession = sessionFactory.getCurrentSession();
        Query<Client> query = hibernateSession.createQuery("from Client as clt where clt.code = :code");
        query.setParameter("code", code);
        Client algorithm = query.uniqueResult();
        return algorithm;
    }

    @Override
    public List<Client> getAll() {
        return super.findAll(Client.class);
    }

    @Override
    public Client2algorithm getDataByClientAlgorithm(Client client, BCAlgorithm algorithm) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        StringBuilder querySB = new StringBuilder();
        querySB.append("select c2a")
                .append(" from Client as clt")
                .append(" join clt.client2algorithms as c2a")
                .append(" join c2a.bcAlgorithm as alg")
                .append(" where clt = :client and alg = :algorithm");
        Query<Client2algorithm> query = hibernateSession.createQuery(querySB.toString());
        query.setParameter("client", client);
        query.setParameter("algorithm", algorithm);
        Client2algorithm client2alg = query.uniqueResult();
        return client2alg;
    }
}
