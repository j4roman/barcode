package com.example.j4roman.barcode.config;

import com.example.j4roman.barcode.config.data.UserData;
import com.example.j4roman.barcode.config.data.UsersInfo;
import com.example.j4roman.barcode.security.XAuthCodeRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class UsersConfiguration {

    private final static String IPV4_FORMAT = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$";

    private Logger log = LoggerFactory.getLogger(UsersConfiguration.class);

    @Autowired
    private UsersProperties usersProperties;

    @Autowired
    @Bean(name = "usersInfo")
    public UsersInfo getUsersInfo() {
        List<String> roles = Arrays.stream(XAuthCodeRoles.values()).map(role -> role.toString()).collect(Collectors.toList());
        log.debug("Registred roles: {}", roles);
        for (UserData userData : usersProperties.getData()) {
            if (!roles.contains(userData.getRole())) {
                throw new InvalidUserDataException("Invalid role name = \'" + userData.getRole() + "\'. Must be one of " + roles);
            }
            for (String ip : userData.getIps()) {
                if (!ip.matches(IPV4_FORMAT)) {
                    throw new InvalidUserDataException("Invalid ipv4 format = \'" + userData.getRole() + "\'. Must match regex " + IPV4_FORMAT);
                }
            }
        }
        log.debug("usersInfo is initialized with {}", usersProperties);
        return usersProperties;
    }
}
