package com.example.j4roman.barcode.controller;

import com.example.j4roman.barcode.service.ClientService;
import com.example.j4roman.barcode.service.dto.manage.AlgorithmDTO;
import com.example.j4roman.barcode.service.dto.manage.AlgorithmsListResponseDTO;
import com.example.j4roman.barcode.service.dto.manage.ClientDTO;
import com.example.j4roman.barcode.controller.utils.BCValidator;
import com.example.j4roman.barcode.service.dto.manage.ClientsListResponseDTO;
import com.example.j4roman.barcode.service.BCAlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private BCAlgorithmService bcAlgorithmService;

    @Autowired
    private ClientService clientService;

    @PostMapping(value = "/algorithm")
    public ResponseEntity<AlgorithmDTO> createAlgorithm(@RequestBody AlgorithmDTO requestBody) {
        BCValidator.validate(requestBody, AlgorithmDTO.REQUEST_METHOD_CREATE);
        AlgorithmDTO responseBody = bcAlgorithmService.create(requestBody);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping(value = "/algorithm")
    public ResponseEntity<AlgorithmDTO> updateAlgorithm(@RequestBody AlgorithmDTO requestBody) {
        BCValidator.validate(requestBody, AlgorithmDTO.REQUEST_METHOD_UPDATE);
        AlgorithmDTO responseBody = bcAlgorithmService.update(requestBody);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping(value = "/algorithm/{name}")
    public ResponseEntity<AlgorithmDTO> deleteAlgorithm(@PathVariable String name) {
        bcAlgorithmService.deleteByName(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/algorithm/{name}")
    public ResponseEntity<AlgorithmDTO> getAlgorithm(@PathVariable String name) {
        AlgorithmDTO responseBody = bcAlgorithmService.getByName(name);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping(value = "/algorithms")
    public ResponseEntity<AlgorithmsListResponseDTO> getAlgorithmList() {
        List<AlgorithmDTO> allAlgorithms = bcAlgorithmService.getAll();
        return new ResponseEntity<>(new AlgorithmsListResponseDTO(allAlgorithms), HttpStatus.OK);
    }

    @PostMapping(value = "/client")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO requestBody) {
        BCValidator.validate(requestBody, ClientDTO.REQUEST_METHOD_CREATE);
        ClientDTO responseBody = clientService.create(requestBody);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping(value = "/client")
    public ResponseEntity<ClientDTO> updateClient(@RequestBody ClientDTO requestBody) {
        BCValidator.validate(requestBody, ClientDTO.REQUEST_METHOD_UPDATE);
        ClientDTO responseBody = clientService.update(requestBody);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping(value = "/client/{code}")
    public ResponseEntity<ClientDTO> deleteClient(@PathVariable String code) {
        clientService.deleteByCode(code);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/client/{code}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable String code) {
        ClientDTO responseBody = clientService.getByCode(code);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping(value = "/clients")
    public ResponseEntity<ClientsListResponseDTO> getClientList() {
        List<ClientDTO> allAlgorithms = clientService.getAll();
        return new ResponseEntity<>(new ClientsListResponseDTO(allAlgorithms), HttpStatus.OK);
    }
}
