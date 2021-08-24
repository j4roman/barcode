package com.example.j4roman.barcode.persistance.dao.exceptions;

import java.util.Arrays;
import java.util.List;

public class EntityNotExistException extends DAOException {

    private List<String> values;

    public EntityNotExistException(String... values) {
        super("The entity with values " + Arrays.asList(values) + " does not exist");
        this.values = Arrays.asList(values);
    }

    public List<String> getValues() {
        return values;
    }
}
