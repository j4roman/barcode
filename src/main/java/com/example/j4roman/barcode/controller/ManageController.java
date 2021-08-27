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

/**
 * This controller processes DB manage operations
 */
@RestController
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private BCAlgorithmService bcAlgorithmService;

    @Autowired
    private ClientService clientService;

    /**
     * The method creates algorithm and linked actions entities in DB.
     *
     * @param requestBody request body with algorithm and actions to create
     * @return response body with created instances
     */
    @PostMapping(value = "/algorithm")
    public ResponseEntity<AlgorithmDTO> createAlgorithm(@RequestBody AlgorithmDTO requestBody) {
        BCValidator.validate(requestBody, AlgorithmDTO.REQUEST_METHOD_CREATE);
        AlgorithmDTO responseBody = bcAlgorithmService.create(requestBody);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    /**
     * The method updates algorithm and linked actions entities in DB.
     *
     * @param requestBody request body with algorithm and actions to update
     * @return response body with updated instances
     */
    @PutMapping(value = "/algorithm")
    public ResponseEntity<AlgorithmDTO> updateAlgorithm(@RequestBody AlgorithmDTO requestBody) {
        BCValidator.validate(requestBody, AlgorithmDTO.REQUEST_METHOD_UPDATE);
        AlgorithmDTO responseBody = bcAlgorithmService.update(requestBody);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    /**
     * The method updates algorithm and linked actions entities from DB.
     *
     * @param name the name of the algorithm to delete
     * @return 204 http-code w/o body
     */
    @DeleteMapping(value = "/algorithm/{name}")
    public ResponseEntity<AlgorithmDTO> deleteAlgorithm(@PathVariable String name) {
        bcAlgorithmService.deleteByName(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * The method gets algorithm and linked actions entities from DB.
     *
     * @param name the name of the getting algorithm
     * @return getting algorithm data
     */
    @GetMapping(value = "/algorithm/{name}")
    public ResponseEntity<AlgorithmDTO> getAlgorithm(@PathVariable String name) {
        AlgorithmDTO responseBody = bcAlgorithmService.getByName(name);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    /**
     * The method gets all existing algorithms
     *
     * @return list of existing algorithms
     */
    @GetMapping(value = "/algorithms")
    public ResponseEntity<AlgorithmsListResponseDTO> getAlgorithmList() {
        List<AlgorithmDTO> allAlgorithms = bcAlgorithmService.getAll();
        return new ResponseEntity<>(new AlgorithmsListResponseDTO(allAlgorithms), HttpStatus.OK);
    }

    /**
     * The method creates client and links him with algorithm
     *
     * @param requestBody request body with client data and algorithms allowed for this client
     * @return response body with created instances
     */
    @PostMapping(value = "/client")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO requestBody) {
        BCValidator.validate(requestBody, ClientDTO.REQUEST_METHOD_CREATE);
        ClientDTO responseBody = clientService.create(requestBody);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    /**
     * The method updates client and links to allowed algorithms
     *
     * @param requestBody request body with client data and algorithms allowed for this client
     * @return response body with updated instances
     */
    @PutMapping(value = "/client")
    public ResponseEntity<ClientDTO> updateClient(@RequestBody ClientDTO requestBody) {
        BCValidator.validate(requestBody, ClientDTO.REQUEST_METHOD_UPDATE);
        ClientDTO responseBody = clientService.update(requestBody);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    /**
     * The method deletes client and links to allowed algorithms
     *
     * @param code code of the client to delete
     * @return 204 http-code w/o body
     */
    @DeleteMapping(value = "/client/{code}")
    public ResponseEntity<ClientDTO> deleteClient(@PathVariable String code) {
        clientService.deleteByCode(code);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * The method gets client and links to allowed algorithms
     *
     * @param code code of the client to get
     * @return requested client data
     */
    @GetMapping(value = "/client/{code}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable String code) {
        ClientDTO responseBody = clientService.getByCode(code);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    /**
     * The method gets all clients
     *
     * @return all clients
     */
    @GetMapping(value = "/clients")
    public ResponseEntity<ClientsListResponseDTO> getClientList() {
        List<ClientDTO> allAlgorithms = clientService.getAll();
        return new ResponseEntity<>(new ClientsListResponseDTO(allAlgorithms), HttpStatus.OK);
    }
}
