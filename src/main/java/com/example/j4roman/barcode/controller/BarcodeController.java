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

/**
 * This controller processes barcode operations
 */
@RestController
public class BarcodeController {

    @Autowired
    BarcodeService barcodeService;

    /**
     * Barcode generation method.
     *
     * @param requestBody - request body with the list of values to generate barcode list from them
     * @return response body with results list
     */
    @PostMapping(value = "/tobarcode")
    public ResponseEntity<ToBarcodeResponseDTO> toBarcode(@RequestBody ToBarcodeRequestDTO requestBody) {
        BCValidator.validate(requestBody);
        ToBarcodeResponseDTO response = barcodeService.generate(requestBody, false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Barcode parsing method.
     *
     * @param requestBody - request body with the list of barcodes to parse them into previous values
     * @return response body with results list
     */
    @PostMapping(value = "/frombarcode")
    public ResponseEntity<ToBarcodeResponseDTO> fromBarcode(@RequestBody ToBarcodeRequestDTO requestBody) {
        BCValidator.validate(requestBody);
        ToBarcodeResponseDTO response = barcodeService.generate(requestBody, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
