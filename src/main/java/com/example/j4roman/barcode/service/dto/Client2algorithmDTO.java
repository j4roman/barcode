package com.example.j4roman.barcode.service.dto;

import com.example.j4roman.barcode.controller.utils.BCCheckNotEmptyAndNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = false)
public class Client2algorithmDTO {

    @BCCheckNotEmptyAndNull(requestMethods = { ClientDTO.REQUEST_METHOD_CREATE, ClientDTO.REQUEST_METHOD_UPDATE })
    private String algorithmName;

    @BCCheckNotEmptyAndNull(requestMethods = { ClientDTO.REQUEST_METHOD_CREATE, ClientDTO.REQUEST_METHOD_UPDATE })
    private String specValue;

    public Client2algorithmDTO() {
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }
}
