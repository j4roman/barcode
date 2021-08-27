package com.example.j4roman.barcode.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToBarcodeResponseItemDTO {

    public static final String ERROR_DOESNT_MATCH_PATTERN = "Value [%s] doesn\'t match algorithm pattern [%s]";
    public static final String ERROR_UNEXPECTED = "Unexpected error occurs";

    private String generatedValue;
    private Status status;
    private String errorDescription;

    public ToBarcodeResponseItemDTO() {
    }

    public ToBarcodeResponseItemDTO(String generatedValue, Status status, String errorDescription) {
        this.generatedValue = generatedValue;
        this.status = status;
        this.errorDescription = errorDescription;
    }

    public static ToBarcodeResponseItemDTO errorDescr(String errorDescription) {
        return new ToBarcodeResponseItemDTO(null, Status.ERROR, errorDescription);
    }

    public static ToBarcodeResponseItemDTO okResult(String generatedValue) {
        return new ToBarcodeResponseItemDTO(generatedValue, Status.OK, null);
    }

    public static ToBarcodeResponseItemDTO errorDoesntMatch(String value, String pattern) {
        return errorDescr(String.format(ERROR_DOESNT_MATCH_PATTERN, value, pattern));
    }

    public static ToBarcodeResponseItemDTO errorUnexpected() {
        return errorDescr(ERROR_UNEXPECTED);
    }

    public String getGeneratedValue() {
        return generatedValue;
    }

    public void setGeneratedValue(String generatedValue) {
        this.generatedValue = generatedValue;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public static enum Status {
        OK,
        ERROR
    }
}
