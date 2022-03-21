package com.example.j4roman.barcode.service.dto.manage;

import com.example.j4roman.barcode.controller.utils.BCCheckNotEmptyAndNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = false)
public class Client2algorithmDTO {

    @BCCheckNotEmptyAndNull
    private String algorithmName;

    @BCCheckNotEmptyAndNull
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client2algorithmDTO{");
        sb.append("algorithmName='").append(algorithmName).append('\'');
        sb.append(", specValue='").append(specValue).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
