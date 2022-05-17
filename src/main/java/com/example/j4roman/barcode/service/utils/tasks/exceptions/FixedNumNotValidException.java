package com.example.j4roman.barcode.service.utils.tasks.exceptions;

/**
 * The exception occurs when fixed number in given barcode is incorrect
 *
 */
public class FixedNumNotValidException extends BarcodeCheckFailedException {

    public FixedNumNotValidException(char expectedValue, char realValue) {
        super(String.format("Invalid fixed number: real = %d, expected = %d", realValue, expectedValue));
    }
}
