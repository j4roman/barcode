package com.example.j4roman.barcode.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToBarcodeResponseItemDTO {

    public static final String ERROR_DOESNT_MATCH_PATTERN = "Value [%s] doesn\'t match algorithm pattern [%s]";
    public static final String ERROR_UNEXPECTED = "Unexpected error occurs";

    private String barcode;
    private Status status;
    private String errorDescription;

    public ToBarcodeResponseItemDTO() {
    }

    public ToBarcodeResponseItemDTO(String barcode, Status status, String errorDescription) {
        this.barcode = barcode;
        this.status = status;
        this.errorDescription = errorDescription;
    }

    public static ToBarcodeResponseItemDTO errorDescr(String errorDescription) {
        return new ToBarcodeResponseItemDTO(null, Status.ERROR, errorDescription);
    }

    public static ToBarcodeResponseItemDTO okResult(String barcode) {
        return new ToBarcodeResponseItemDTO(barcode, Status.OK, null);
    }

    public static ToBarcodeResponseItemDTO errorDoesntMatch(String value, String pattern) {
        return errorDescr(String.format(ERROR_DOESNT_MATCH_PATTERN, value, pattern));
    }

    public static ToBarcodeResponseItemDTO errorUnexpected() {
        return errorDescr(ERROR_UNEXPECTED);
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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
