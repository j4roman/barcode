package com.example.j4roman.barcode.persistance.dao;

import com.example.j4roman.barcode.persistance.entities.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientDAO extends GenericDAO<Client, Long> {

    void create(Client entity);
    Client getByCode(String code);
    void delete(Client client);
    List<Client> getAll();
}
