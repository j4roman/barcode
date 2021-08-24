package com.example.j4roman.barcode.controller.utils;

import java.lang.reflect.Field;
import java.util.*;

public final class BCValidator {

    public static void validate(Object obj, String requestMethod) throws BCInvalidFieldFormatException {
        validate(obj, requestMethod, null);
    }

    public static void validate(Object obj, String requestMethod, String objectName) throws BCInvalidFieldFormatException {
        Set<String> result = new LinkedHashSet<>();
        Class clazz = obj.getClass();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(BCCheckNotEmptyAndNull.class)) {
                    BCCheckNotEmptyAndNull checkAnn = field.getDeclaredAnnotation(BCCheckNotEmptyAndNull.class);
                    if (Arrays.asList(checkAnn.requestMethods()).contains(requestMethod)) {
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
                } else if (field.isAnnotationPresent(BCCheckNotNull.class)) {
                    BCCheckNotNull checkAnn = field.getDeclaredAnnotation(BCCheckNotNull.class);
                    if (Arrays.asList(checkAnn.requestMethods()).contains(requestMethod)) {
                        Object fieldObj = field.get(obj);
                        if (fieldObj == null) {
                            result.add(objectName != null ? objectName + "." + field.getName() : field.getName());
                        }
                    }
                }
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
            throw new RuntimeException(e);
        }
        if (!result.isEmpty()) {
            throw new BCInvalidFieldFormatException(result);
        }
    }
}
