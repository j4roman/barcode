package com.example.j4roman.barcode.controller;

import com.example.j4roman.barcode.controller.utils.BCValidator;
import com.example.j4roman.barcode.service.BarcodeService;
import com.example.j4roman.barcode.service.dto.ToBarcodeRequestDTO;
import com.example.j4roman.barcode.service.dto.ToBarcodeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarcodeController {

    @Autowired
    BarcodeService barcodeService;

    @PostMapping(value = "/tobarcode")
    public ResponseEntity<ToBarcodeResponseDTO> toBarcode(@RequestBody ToBarcodeRequestDTO requestBody) {
        BCValidator.validate(requestBody);
        ToBarcodeResponseDTO response = barcodeService.toBarcode(requestBody);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
