package com.example.j4roman.barcode.service.utils;

import com.example.j4roman.barcode.persistance.entities.Action;

/**
 * The exception occurs when unknown task name is used
 *
 * Generated in {@link CommonTasks#generate(Action, String, boolean)}
 *
 */
public class NoSuchTaskException extends RuntimeException {

    public NoSuchTaskException(String task) {
        super(String.format("No task with name [%s]", task));
    }
}
