package com.example.j4roman.barcode.service.exceptions;

import java.util.Arrays;
import java.util.List;

public class EntityDoesNotExistException extends DAOException {

    private List<String> values;

    public EntityDoesNotExistException(String... values) {
        super("The entity with value(s) " + Arrays.asList(values) + " does not exist");
        this.values = Arrays.asList(values);
    }

    public List<String> getValues() {
        return values;
    }
}
