package com.example.j4roman.barcode.security;

import org.springframework.security.access.AccessDeniedException;

public class XAuthCodeAccessDeniedException extends AccessDeniedException {

    public XAuthCodeAccessDeniedException(String msg) {
        super(msg);
    }
}
