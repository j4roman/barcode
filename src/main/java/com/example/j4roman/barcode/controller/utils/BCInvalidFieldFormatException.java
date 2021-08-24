package com.example.j4roman.barcode.controller.utils;

import java.util.Collection;

public class BCInvalidFieldFormatException extends RuntimeException {

    private Collection<String> fieldList;

    public BCInvalidFieldFormatException(Collection<String> fieldList) {
        super("Fields values should be defined: " + fieldList.toString());
        this.fieldList = fieldList;
    }

    public Collection<String> getFieldList() {
        return this.fieldList;
    }
}
