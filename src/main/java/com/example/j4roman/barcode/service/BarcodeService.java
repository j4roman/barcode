package com.example.j4roman.barcode.service;

import com.example.j4roman.barcode.service.dto.ToBarcodeRequestDTO;
import com.example.j4roman.barcode.service.dto.ToBarcodeResponseDTO;
import org.springframework.stereotype.Service;

public interface BarcodeService {
    ToBarcodeResponseDTO toBarcode(ToBarcodeRequestDTO requestBody);
}
