package com.wageul.wageul_server.common.exception;

public class LoginException extends RuntimeException {
    public LoginException() {
        super("GOOGLE LOGIN ERROR");
    }
}
