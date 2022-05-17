package com.example.j4roman.barcode.service.utils.tasks.exceptions;

/**
 * The exceptions occurs when barcode failed to parse
 *
 * General exception.
 *
 * See also {@link CheckNumInvalidException}, {@link ClientNumberInvalidException},
 * {@link FixedNumNotValidException}
 *
 */
public class BarcodeCheckFailedException extends RuntimeException {

    public BarcodeCheckFailedException(String message) {
        super(message);
    }
}
