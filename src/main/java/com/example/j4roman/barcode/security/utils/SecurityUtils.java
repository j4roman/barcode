package com.example.j4roman.barcode.security.utils;

import com.example.j4roman.barcode.config.data.UserData;
import com.example.j4roman.barcode.config.data.UsersInfo;
import com.example.j4roman.barcode.security.XAuthCodeAuthenticationToken;
import com.example.j4roman.barcode.security.XAuthCodeRoles;

import java.util.Optional;

public final class SecurityUtils {

    public static XAuthCodeAuthenticationToken getUserAuth(UsersInfo usersInfo, String authName, String ipV4Address) {
        Optional<UserData> userDataOpt = usersInfo.getData().stream().filter(user -> user.getAuthName().equals(authName)).findFirst();
        if (!userDataOpt.isPresent()) {
            return null;
        }
        UserData userData = userDataOpt.get();
        if (!userData.getIps().contains(ipV4Address)) {
            return null;
        }
        return new XAuthCodeAuthenticationToken(userData.getAuthName(), new XAuthCodeAuthenticationToken.Details(ipV4Address), XAuthCodeRoles.valueOf(userData.getRole()));
    }
}
