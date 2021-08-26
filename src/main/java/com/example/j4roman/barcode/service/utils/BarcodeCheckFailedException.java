package com.example.j4roman.barcode.service.utils;

/**
 * The exceptions occurs when barcode failed to parse
 * Genereted in {@link CommonTasks}
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
