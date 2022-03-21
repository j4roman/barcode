package com.example.j4roman.barcode.service.dto;

import com.example.j4roman.barcode.controller.utils.BCCheckNotEmptyAndNull;
import com.example.j4roman.barcode.controller.utils.BCCheckNotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = false)
public class ToBarcodeRequestDTO {

    @BCCheckNotEmptyAndNull
    private String clientCode;

    @BCCheckNotEmptyAndNull
    private String algorithm;

    @BCCheckNotEmptyAndNull
    private List<String> values;

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ToBarcodeRequestDTO{");
        sb.append("clientCode='").append(clientCode).append('\'');
        sb.append(", algorithm='").append(algorithm).append('\'');
        sb.append(", values=").append(values);
        sb.append('}');
        return sb.toString();
    }
}
