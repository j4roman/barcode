package com.example.j4roman.barcode.controller.api;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorResponse {

    private String errorCode;
    private String errorDescr;
    private String errorTrace;

    public ErrorResponse(String errorCode, Throwable e) {
        this.errorCode = errorCode;
        this.errorDescr = e.getClass().getSimpleName() + " : " + e.getMessage();
        this.errorTrace = getStackTrace(e);
    }

    public ErrorResponse(String errorCode, String description, Throwable e) {
        this.errorCode = errorCode;
        this.errorDescr = description;
        this.errorTrace = getStackTrace(e);
    }

    private static String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescr() {
        return errorDescr;
    }

    public void setErrorDescr(String errorDescr) {
        this.errorDescr = errorDescr;
    }

    public String getErrorTrace() {
        return errorTrace;
    }

    public void setErrorTrace(String errorTrace) {
        this.errorTrace = errorTrace;
    }
}
