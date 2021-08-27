package com.example.j4roman.barcode.service;

import com.example.j4roman.barcode.service.dto.ToBarcodeRequestDTO;
import com.example.j4roman.barcode.service.dto.ToBarcodeResponseDTO;

/**
 * The service handles operations with barcode.
 * For example generate barcode from value and parse it back.
 */
public interface BarcodeService {
    /**
     * The method is used to generate barcode from values list
     * and to parse it back to values depends on <code>isParse</code>
     * @param requestBody body of income request
     * @param isParse if <code>true</code> then perform parse barcode to value
     *                else - generate barcode from value
     * @return response body with transformed values
     */
    ToBarcodeResponseDTO generate(ToBarcodeRequestDTO requestBody, boolean isParse);
}
