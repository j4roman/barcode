package com.example.j4roman.barcode.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XAuthCodeAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(XAuthCodeAuthenticationProcessingFilter.class);

    public XAuthCodeAuthenticationProcessingFilter(String filterUrl) {
        super(filterUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        String xAuthNameHeader = httpServletRequest.getHeader(XAuthCodeAuthenticationToken.X_AUTH_NAME_HEADER);
        String ipV4Address = httpServletRequest.getRemoteAddr();
        XAuthCodeAuthenticationToken authToken = new XAuthCodeAuthenticationToken(xAuthNameHeader, new XAuthCodeAuthenticationToken.Details(ipV4Address));
        return authToken;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
}
