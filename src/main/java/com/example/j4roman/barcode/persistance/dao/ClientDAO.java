package com.example.j4roman.barcode.persistance.dao;

import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import com.example.j4roman.barcode.persistance.entities.Client;
import com.example.j4roman.barcode.persistance.entities.Client2algorithm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientDAO extends GenericDAO<Client, Long> {

    /**
     * Create client entity
     *
     * @param entity entity to be created
     */
    void create(Client entity);

    /**
     * Update client entity
     *
     * @param entity client to be updated
     * @return updated client
     */
    Client change(Client entity);

    /**
     * Get client by code
     *
     * @param code code of client to be retrieved
     * @return client with this code
     */
    Client getByCode(String code);

    /**
     * Delete client
     *
     * @param client client to be deleted
     */
    void delete(Client client);

    /**
     * Get all clients
     *
     * @return all clients
     */
    List<Client> getAll();

    /**
     * Get value of field <code>Client2algorithm.specValue</code>
     * corresponding to given client and algorithm
     *
     * @param client
     * @param algorithm
     * @return specValue from corresponding <code>Client2algorithm.specValue</code>
     */
    Client2algorithm getDataByClientAlgorithm(Client client, BCAlgorithm algorithm);
}
