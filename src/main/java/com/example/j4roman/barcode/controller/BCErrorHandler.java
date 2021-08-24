package com.example.j4roman.barcode.controller;

import com.example.j4roman.barcode.controller.api.ErrorResponse;
import com.example.j4roman.barcode.controller.utils.BCInvalidFieldFormatException;
import com.example.j4roman.barcode.persistance.dao.exceptions.EntityAlreadyExistsException;
import com.example.j4roman.barcode.persistance.dao.exceptions.EntityNotExistException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class BCErrorHandler {

    private static final String ERRORCODE_COMMON_PART = "Barcode.Error";
    private static final String ERRORCODE_INTERNAL = "InternalError";
    private static final String ERRORCODE_MALFORMED = "BadRequest.MalformedRequest";
    private static final String ERRORCODE_INVALID_FORMAT = "BadRequest.InvalidFieldsFormat";
    private static final String ERRORCODE_INVALID_HTTP_METHOD = "BadRequest.InvalidHttpMethod";
    private static final String ERRORCODE_ALREADY_EXISTS = "BadRequest.Create.AlreadyExists";
    private static final String ERRORCODE_NOT_EXISTS = "BadRequest.ReadUpdateDelete.NotExists";
    private static final String ERRORCODE_NOT_FOUND = "BadRequest.Url.NotFound";

    private static final Logger logger = LogManager.getLogger(BCErrorHandler.class);

    @ExceptionHandler(value = { BCInvalidFieldFormatException.class })
    public ResponseEntity<ErrorResponse> handleFormatError(BCInvalidFieldFormatException e) {
        return getSimpleResponse(e, ERRORCODE_INVALID_FORMAT, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    public ResponseEntity<ErrorResponse> handleMalformedError(HttpMessageNotReadableException e) {
        return getSimpleResponse(e, ERRORCODE_MALFORMED, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<ErrorResponse> handleMalformedError(HttpRequestMethodNotSupportedException e) {
        return getSimpleResponse(e, ERRORCODE_INVALID_HTTP_METHOD, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { EntityAlreadyExistsException.class })
    public ResponseEntity<ErrorResponse> handleMalformedError(EntityAlreadyExistsException e) {
        return getSimpleResponse(e, ERRORCODE_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { EntityNotExistException.class })
    public ResponseEntity<ErrorResponse> handleMalformedError(EntityNotExistException e) {
        return getSimpleResponse(e, ERRORCODE_NOT_EXISTS, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFound(NoHandlerFoundException e) {
        return getSimpleResponse(e, ERRORCODE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ErrorResponse> handleError(Exception e) {
        logger.error(e);
        return getSimpleResponse(e, ERRORCODE_INTERNAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ResponseEntity<ErrorResponse> getSimpleResponse(Exception e, String errorCode, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(ERRORCODE_COMMON_PART + "." + errorCode, e);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
