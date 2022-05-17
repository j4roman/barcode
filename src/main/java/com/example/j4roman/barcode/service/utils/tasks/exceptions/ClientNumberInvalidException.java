package com.example.j4roman.barcode.service.utils.tasks.exceptions;

/**
 * The exception occurs when used client number in given barcode is incorrect
 *
 */
public class ClientNumberInvalidException extends BarcodeCheckFailedException {

    public ClientNumberInvalidException(char expectedValue, char realValue) {
        super(String.format("Invalid client number: real = %c, expected = %c", realValue, expectedValue));
    }
}
