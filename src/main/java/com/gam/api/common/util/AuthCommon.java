package com.gam.api.common.util;

import com.gam.api.common.message.ExceptionMessage;
import org.springframework.context.annotation.Configuration;

import java.security.Principal;

import static java.util.Objects.isNull;
@Configuration
public class AuthCommon {
    public static Long getUser(Principal principal){
        if (isNull(principal.getName())) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_FOUND_USER.getMessage());
        }
        return Long.valueOf(principal.getName());
    }
}
