package com.example.j4roman.barcode.controller.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  The annotation is used to mark List field for checking each item by {@link BCValidator}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BCCheckList {
    /**
     * The actual name of list field
     */
    String fieldName();
}
