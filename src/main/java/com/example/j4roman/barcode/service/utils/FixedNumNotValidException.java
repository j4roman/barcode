package com.example.j4roman.barcode.service.utils;

import com.example.j4roman.barcode.persistance.entities.Action;

/**
 * The exception occurs when fixed number in given barcode is incorrect
 *
 * Generated in {@link CommonTasks#generate(CommonTasks.Type, Action, String, boolean)}
 *
 */
public class FixedNumNotValidException extends BarcodeCheckFailedException {

    public FixedNumNotValidException(int expectedValue, int realValue) {
        super(String.format("Invalid fixed number: real = %d, expected = %d", realValue, expectedValue));
    }
}
