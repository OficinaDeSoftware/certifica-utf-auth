package com.OficinaDeSoftware.Auth.service.exception;

import org.springframework.security.core.AuthenticationException;

public class UnknowProviderTokenException extends AuthenticationException {
    public UnknowProviderTokenException() {
        super("Unknow provider token");
    }
}
