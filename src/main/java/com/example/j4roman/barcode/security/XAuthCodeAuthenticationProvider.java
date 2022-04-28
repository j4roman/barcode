package com.example.j4roman.barcode.security;

import com.example.j4roman.barcode.config.data.UsersInfo;
import com.example.j4roman.barcode.security.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class XAuthCodeAuthenticationProvider implements AuthenticationProvider {

    private final Logger log = LoggerFactory.getLogger(XAuthCodeAuthenticationProvider.class);

    @Autowired
    private UsersInfo usersInfo;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        log.debug("Initial auth token: {}", auth);
        XAuthCodeAuthenticationToken xAuthCodeToken = (XAuthCodeAuthenticationToken)auth;
        XAuthCodeAuthenticationToken.Details details = (XAuthCodeAuthenticationToken.Details)xAuthCodeToken.getDetails();
        if (!xAuthCodeToken.isHeaderExists()) {
            log.debug("No required header: {}", XAuthCodeAuthenticationToken.X_AUTH_NAME_HEADER);
            throw new XAuthCodeAccessDeniedException("No required header: \'" + XAuthCodeAuthenticationToken.X_AUTH_NAME_HEADER + "\'");
        }
        String code = (String)xAuthCodeToken.getPrincipal();
        String ipV4address = details.getIpV4Address();
        log.debug("Auth code = \'{}\' and ip = {}", code, ipV4address);
        XAuthCodeAuthenticationToken newAuth = SecurityUtils.getUserAuth(usersInfo, code, ipV4address);
        if (newAuth == null) {
            log.debug("Invalid auth code: {} or ip: {}", code, ipV4address);
            throw new XAuthCodeAccessDeniedException("Invalid auth code = \'" + code + "\' or ip = " + ipV4address);
        } else {
            log.debug("Code-ip binding has been found. New token = {}", newAuth);
            return newAuth;
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(XAuthCodeAuthenticationToken.class);
    }
}
