package com.example.j4roman.barcode.service;

import com.example.j4roman.barcode.service.dto.manage.ClientDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The service is used to process operations with clients
 *
 */
public interface ClientService {

    /**
     * Create client with given data in DB
     *
     * @param clientReq request body with data of client to be created
     * @return response body with created client data
     */
    ClientDTO create(ClientDTO clientReq);

    /**
     * Update client with given data in DB
     *
     * @param clientReq request body with data of client to be updated
     * @return response body with updated client data
     */
    ClientDTO update(ClientDTO clientReq);

    /**
     * Delete client defined by given code
     *
     * @param code code of client to be deleted
     */
    void deleteByCode(String code);

    /**
     * Get specific client by his code
     *
     * @param code code of client to retrieve
     * @return response body with data of found client
     */
    ClientDTO getByCode(String code);

    /**
     * Get all clients from DB
     *
     * @return response body with all clients data
     */
    List<ClientDTO> getAll();
}
