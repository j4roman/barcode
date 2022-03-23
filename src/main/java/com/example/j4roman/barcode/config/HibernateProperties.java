package com.example.j4roman.barcode.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "hibernate-props")
public class HibernateProperties {

    private Map<String, String> keyValue;

    public Map<String, String> getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(Map<String, String> keyValue) {
        this.keyValue = keyValue;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HibernateProperties{");
        sb.append("keyValue=").append(keyValue);
        sb.append('}');
        return sb.toString();
    }
}
