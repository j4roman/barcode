package com.example.j4roman.barcode.config.data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class UserData {

    private String authName;
    private String role;
    private List<String> ips;

    @NotEmpty
    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    @NotEmpty
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @NotEmpty
    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserData{");
        sb.append("authName='").append(authName).append('\'');
        sb.append(", role='").append(role).append('\'');
        sb.append(", ips=").append(ips);
        sb.append('}');
        return sb.toString();
    }
}
