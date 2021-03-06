package com.example.j4roman.barcode.service.exceptions;

import java.util.Arrays;
import java.util.List;

public class EntityAlreadyExistsException extends DAOException {

    private List<String> values;

    public EntityAlreadyExistsException(String... values) {
        super("The entity with value(s) " + Arrays.asList(values) + " already exists");
        this.values = Arrays.asList(values);
    }

    public List<String> getValues() {
        return values;
    }
}
