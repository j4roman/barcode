package com.example.j4roman.barcode.persistance.dao;

import com.example.j4roman.barcode.persistance.dao.GenericDAO;
import com.example.j4roman.barcode.persistance.entities.Client;
import com.example.j4roman.barcode.persistance.entities.Client2algorithm;
import org.springframework.stereotype.Repository;

@Repository
public interface Client2AlgorithmDAO extends GenericDAO<Client2algorithm, Long> {

    void deleteByClient(Client client);
}
