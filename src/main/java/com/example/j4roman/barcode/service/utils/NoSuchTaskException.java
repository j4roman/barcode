package com.example.j4roman.barcode.service.utils;

public class NoSuchTaskException extends RuntimeException {

    public NoSuchTaskException(String task) {
        super(String.format("No task with name [%s]", task));
    }
}
