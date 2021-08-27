package com.example.j4roman.barcode.service.utils;

import com.example.j4roman.barcode.persistance.entities.Action;

/**
 * The exception occurs when check number in given barcode is incorrect
 *
 * Generated in {@link CommonTasks#generate(CommonTasks.Type, Action, String, boolean)}
 *
 */
public class CheckNumInvalidException extends BarcodeCheckFailedException {

    public CheckNumInvalidException(int expectedValue, int realValue) {
        super(String.format("Invalid check number: real = %d, expected = %d", realValue, expectedValue));
    }
}
