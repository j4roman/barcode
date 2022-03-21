package com.example.j4roman.barcode.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToBarcodeResponseDTO {

    private static final String DESCR_CLIENT_ALGORITHM_NOT_BIND = "Client with code [%s] can\'t use algorithm [%s]";
    private static final String DESCR_CLIENT_NOT_FOUND = "Client with code [%s] is not found";
    private static final String DESCR_ALGORITHM_NOT_FOUND = "Algorithm with name [%s] is not found";

    private String errorDescription;
    private Long count;
    private List<ToBarcodeResponseItemDTO> results;

    public ToBarcodeResponseDTO() {
    }

    public ToBarcodeResponseDTO(String errorDescription) {
        this.errorDescription = errorDescription;
        this.count = new Long(0);
        this.results = new ArrayList<>();
    }

    public ToBarcodeResponseDTO(List<ToBarcodeResponseItemDTO> results) {
        this.errorDescription = null;
        this.count = (long)results.size();
        this.results = results;
    }

    public static ToBarcodeResponseDTO clientAlgorithmNotBind(String clientCode, String algorithmName) {
        return new ToBarcodeResponseDTO(String.format(DESCR_CLIENT_ALGORITHM_NOT_BIND, clientCode, algorithmName));
    }

    public static ToBarcodeResponseDTO clientNotFound(String clientCode) {
        return new ToBarcodeResponseDTO(String.format(DESCR_CLIENT_NOT_FOUND, clientCode));
    }

    public static ToBarcodeResponseDTO algorithmNotFound(String algorithmName) {
        return new ToBarcodeResponseDTO(String.format(DESCR_ALGORITHM_NOT_FOUND, algorithmName));
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<ToBarcodeResponseItemDTO> getResults() {
        return results;
    }

    public void setResults(List<ToBarcodeResponseItemDTO> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ToBarcodeResponseDTO{");
        sb.append("errorDescription='").append(errorDescription).append('\'');
        sb.append(", count=").append(count);
        sb.append(", results=").append(results);
        sb.append('}');
        return sb.toString();
    }
}
