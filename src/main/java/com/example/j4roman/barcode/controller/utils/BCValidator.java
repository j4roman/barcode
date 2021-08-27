package com.example.j4roman.barcode.controller.utils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Methods of this class do simple request validation.
 * Checks fields annotated with {@link BCCheckList}, {@link BCCheckNotNull}, {@link BCCheckNotEmptyAndNull}
 */
public final class BCValidator {

    /**
     * Validates the object with any request method
     * @param obj object to check
     * @throws BCInvalidFieldFormatException
     */
    public static void validate(Object obj) throws BCInvalidFieldFormatException {
        validate(obj, "", null);
    }

    /**
     * Validates the object marked with <code>requestMethod</code>
     * @param obj object to check
     * @param requestMethod request method to check
     * @throws BCInvalidFieldFormatException
     */
    public static void validate(Object obj, String requestMethod) throws BCInvalidFieldFormatException {
        validate(obj, requestMethod, null);
    }

    /**
     * Validates the object marked with <code>requestMethod</code> with the name <code>objectName</code>
     * @param obj object to check
     * @param requestMethod request method to check
     * @param objectName the actual name of the object variable
     * @throws BCInvalidFieldFormatException
     */
    public static void validate(Object obj, String requestMethod, String objectName) throws BCInvalidFieldFormatException {
        Set<String> result = new LinkedHashSet<>();
        Class clazz = obj.getClass();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                // Checks String and List field for null and emptiness
                if (field.isAnnotationPresent(BCCheckNotEmptyAndNull.class)) {
                    BCCheckNotEmptyAndNull checkAnn = field.getDeclaredAnnotation(BCCheckNotEmptyAndNull.class);
                    List<String> reqMethods = Arrays.asList(checkAnn.requestMethods());
                    if (reqMethods.isEmpty() || reqMethods.contains(requestMethod)) {
                        Object fieldObj = field.get(obj);
                        if (fieldObj == null) {
                            result.add(objectName != null ? objectName + "." + field.getName() : field.getName());
                        } else if (fieldObj instanceof String) {
                            if (fieldObj.equals("")) {
                                result.add(objectName != null ? objectName + "." + field.getName() : field.getName());
                            }
                        } else if (fieldObj instanceof List) {
                            if (((List) fieldObj).isEmpty()) {
                                result.add(objectName != null ? objectName + "." + field.getName() : field.getName());
                            }
                        } else {
                            throw new BCValidateWrongClassException(fieldObj.getClass());
                        }
                    }
                // or checks any object only for null
                } else if (field.isAnnotationPresent(BCCheckNotNull.class)) {
                    BCCheckNotNull checkAnn = field.getDeclaredAnnotation(BCCheckNotNull.class);
                    List<String> reqMethods = Arrays.asList(checkAnn.requestMethods());
                    if (reqMethods.isEmpty() || reqMethods.contains(requestMethod)) {
                        Object fieldObj = field.get(obj);
                        if (fieldObj == null) {
                            result.add(objectName != null ? objectName + "." + field.getName() : field.getName());
                        }
                    }
                }
                // checks List items
                if (field.isAnnotationPresent(BCCheckList.class)) {
                    BCCheckList checkAnn = field.getDeclaredAnnotation(BCCheckList.class);
                    Object fieldObj = field.get(obj);
                    if ((fieldObj != null) && (fieldObj instanceof List)) {
                        ((List)(fieldObj)).forEach(listObj -> {
                            try {
                                validate(listObj, requestMethod, objectName != null ? objectName + "." + checkAnn.fieldName() : checkAnn.fieldName());
                            } catch (BCInvalidFieldFormatException e) {
                                result.addAll(e.getFieldList());
                            }
                        });
                    }
                }
            }
        } catch(IllegalAccessException e) {
            throw new BCWrongAnnotationUseException(e);
        }
        if (!result.isEmpty()) {
            throw new BCInvalidFieldFormatException(result);
        }
    }
}
