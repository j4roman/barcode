package com.example.j4roman.barcode.controller.utils;

public class BCValidateWrongClassException extends RuntimeException {

    public BCValidateWrongClassException(Class clazz) {
        super("The method does not work with class: " + clazz.getName());
    }
}
