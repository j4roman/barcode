package com.example.j4roman.barcode.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.stream.Collectors;

public class XAuthCodeAuthenticationToken extends AbstractAuthenticationToken {

    public static final String X_AUTH_NAME_HEADER = "X-Auth-Name";

    private String code;

    public XAuthCodeAuthenticationToken(String code, Details details, XAuthCodeRoles... authorities) {
        super(authorities == null ? null : Arrays.stream(authorities)
                .map(authEntity -> new SimpleGrantedAuthority(authEntity.toString()))
                .collect(Collectors.toList())
        );
        this.code = code;
        setDetails(details);
    }

    public XAuthCodeAuthenticationToken(XAuthCodeAuthenticationToken auth, XAuthCodeRoles... authorities) {
        this((String)auth.getPrincipal(), (Details)auth.getDetails(), authorities);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return code;
    }

    public boolean isHeaderExists() {
        return code != null && !code.isEmpty();
    }

    public static class Details {
        private String ipV4Address;

        public Details(String ipV4Address) {
            this.ipV4Address = ipV4Address;
        }

        public String getIpV4Address() {
            return ipV4Address;
        }

        public void setIpV4Address(String ipV4Address) {
            this.ipV4Address = ipV4Address;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("XAuthCodeAuthenticationToken.Details{");
            sb.append("ipV4Address='").append(ipV4Address).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
