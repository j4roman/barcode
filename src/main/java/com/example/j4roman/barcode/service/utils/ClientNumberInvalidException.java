package com.example.j4roman.barcode.service.utils;

import com.example.j4roman.barcode.persistance.entities.Action;

/**
 * The exception occurs when used client number in given barcode is incorrect
 *
 * Generated in {@link CommonTasks#generate(CommonTasks.Type, Action, String, boolean)}
 *
 */
public class ClientNumberInvalidException extends BarcodeCheckFailedException {

    public ClientNumberInvalidException(int expectedValue, int realValue) {
        super(String.format("Invalid client number: real = %d, expected = %d", realValue, expectedValue));
    }
}
