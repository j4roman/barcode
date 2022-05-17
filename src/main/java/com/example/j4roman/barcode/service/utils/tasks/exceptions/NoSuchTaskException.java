package com.example.j4roman.barcode.service.utils.tasks.exceptions;

/**
 * The exception occurs when unknown task name is used
 *
 */
public class NoSuchTaskException extends RuntimeException {

    public NoSuchTaskException(String task) {
        super(String.format("No task with name '%s'", task));
    }
}
