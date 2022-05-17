package com.example.j4roman.barcode.service.utils.tasks.exceptions;

/**
 * The exception occurs when check number in given barcode is incorrect
 *
 */
public class CheckNumInvalidException extends BarcodeCheckFailedException {

    public CheckNumInvalidException(int expectedValue, int realValue) {
        super(String.format("Invalid check number: real = %d, expected = %d", realValue, expectedValue));
    }
}
