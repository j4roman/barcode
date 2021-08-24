package com.example.j4roman.barcode.controller;

import com.example.j4roman.barcode.controller.api.AlgorithmRequest;
import com.example.j4roman.barcode.controller.api.AlgorithmsList;
import com.example.j4roman.barcode.controller.utils.BCValidator;
import com.example.j4roman.barcode.controller.utils.RequestEntityConverter;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import com.example.j4roman.barcode.service.BCAlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private BCAlgorithmService bcAlgorithmService;

    @PostMapping(value = "/algorithm")
    public ResponseEntity<AlgorithmRequest> createAlgorithm(@RequestBody AlgorithmRequest requestBody) {
        BCValidator.validate(requestBody, AlgorithmRequest.REQUEST_METHOD_CREATE);
        BCAlgorithm algorithm = RequestEntityConverter.convert(requestBody);
        bcAlgorithmService.create(algorithm);
        return new ResponseEntity<>(RequestEntityConverter.convert(algorithm), HttpStatus.CREATED);
    }

    @PutMapping(value = "/algorithm")
    public ResponseEntity<AlgorithmRequest> updateAlgorithm(@RequestBody AlgorithmRequest requestBody) {
        BCValidator.validate(requestBody, AlgorithmRequest.REQUEST_METHOD_UPDATE);
        BCAlgorithm algorithm = RequestEntityConverter.convert(requestBody);
        algorithm = bcAlgorithmService.update(algorithm);
        return new ResponseEntity<>(RequestEntityConverter.convert(algorithm), HttpStatus.OK);
    }

    @DeleteMapping(value = "/algorithm/{name}")
    public ResponseEntity<AlgorithmRequest> deleteAlgorithm(@PathVariable String name) {
        bcAlgorithmService.deleteByName(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/algorithm/{name}")
    public ResponseEntity<AlgorithmRequest> getAlgorithm(@PathVariable String name) {
        BCAlgorithm algorithm = bcAlgorithmService.getByName(name);
        AlgorithmRequest algorithmReq = RequestEntityConverter.convert(algorithm);
        return new ResponseEntity<>(algorithmReq, HttpStatus.OK);
    }

    @GetMapping(value = "/algorithms")
    public ResponseEntity<AlgorithmsList> getAlgorithmList() {
        List<BCAlgorithm> allAlgorithms = bcAlgorithmService.getAll();
        List<AlgorithmRequest> reqAlgList = allAlgorithms.stream().map(alg -> RequestEntityConverter.convert(alg)).collect(Collectors.toList());
        return new ResponseEntity<>(new AlgorithmsList(reqAlgList), HttpStatus.OK);
    }
}
