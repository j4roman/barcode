package com.example.j4roman.barcode.persistance.dao.impl;

import com.example.j4roman.barcode.persistance.dao.GenericDAO;
import com.example.j4roman.barcode.service.exceptions.DAOException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

@Repository
public abstract class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(T entity) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        hibernateSession.saveOrUpdate(entity);
    }

    @Override
    public T change(T entity) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        return (T)hibernateSession.merge(entity);
    }

    @Override
    public void delete(T entity) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        hibernateSession.delete(entity);
    }

    @Override
    public List<T> findAll(Class clazz) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        Query query = hibernateSession.createQuery("from " + clazz.getSimpleName());
        List<T> list = (List<T>) query.getResultList();
        return list;
    }

    @Override
    public T getById(Class clazz, ID id) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        T entity = (T) hibernateSession.get(clazz, id);
        return entity;
    }

    @Override
    public T loadById(Class clazz, ID id) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        T entity = (T) hibernateSession.load(clazz, id);
        return entity;
    }
}
