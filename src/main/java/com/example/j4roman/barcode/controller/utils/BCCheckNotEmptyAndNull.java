package com.example.j4roman.barcode.controller.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  The annotation is used to mark String or List field for checking it by {@link BCValidator}
 *  Checks for null values and emptiness.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BCCheckNotEmptyAndNull {
    /**
     * Names of request methods to check
     */
    String[] requestMethods() default {};
}
