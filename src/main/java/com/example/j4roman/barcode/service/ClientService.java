package com.example.j4roman.barcode.service;

import com.example.j4roman.barcode.persistance.entities.Client;
import com.example.j4roman.barcode.service.dto.AlgorithmDTO;
import com.example.j4roman.barcode.service.dto.ClientDTO;

import java.util.List;

public interface ClientService {

    ClientDTO create(ClientDTO clientReq);
    ClientDTO update(ClientDTO clientReq);
    void deleteByCode(String code);
    ClientDTO getByCode(String code);
    List<ClientDTO> getAll();
}
