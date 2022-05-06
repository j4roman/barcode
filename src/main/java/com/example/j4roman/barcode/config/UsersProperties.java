package com.example.j4roman.barcode.config;

import com.example.j4roman.barcode.config.data.UserData;
import com.example.j4roman.barcode.config.data.UsersInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "users")
@PropertySource(value = "classpath:users.yml", factory = YamlPropertySourceFactory.class)
public class UsersProperties implements UsersInfo {

    private List<UserData> data;

    @Override
    public List<UserData> getData() {
        return data;
    }

    public void setData(List<UserData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UsersProperties{");
        sb.append("data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
