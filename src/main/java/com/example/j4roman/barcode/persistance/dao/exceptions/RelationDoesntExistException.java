package com.example.j4roman.barcode.persistance.dao.exceptions;

import java.util.Arrays;
import java.util.List;

public class RelationDoesntExistException extends DAOException {
    private String val1;
    private String val2;

    public RelationDoesntExistException(String val1, String val2) {
        super(String.format("Relation (%s-%s) doesnt exist", val1, val2));
        this.val1 = val1;
        this.val2 = val2;
    }

    public String getVal1() {
        return val1;
    }

    public String getVal2() {
        return val2;
    }
}
