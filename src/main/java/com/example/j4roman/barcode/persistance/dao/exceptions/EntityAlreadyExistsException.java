package com.example.j4roman.barcode.persistance.dao.exceptions;

import java.util.Arrays;
import java.util.List;

public class EntityAlreadyExistsException extends DAOException {

    private List<String> values;

    public EntityAlreadyExistsException(String... values) {
        super("The entity with values " + Arrays.asList(values) + " already exists");
        this.values = Arrays.asList(values);
    }

    public List<String> getValues() {
        return values;
    }
}
