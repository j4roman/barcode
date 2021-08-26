package com.example.j4roman.barcode.service;

import com.example.j4roman.barcode.service.dto.manage.ClientDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClientService {

    ClientDTO create(ClientDTO clientReq);
    ClientDTO update(ClientDTO clientReq);
    void deleteByCode(String code);
    ClientDTO getByCode(String code);
    List<ClientDTO> getAll();
}
